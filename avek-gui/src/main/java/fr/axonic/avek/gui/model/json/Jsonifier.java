package fr.axonic.avek.gui.model.json;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import fr.axonic.base.ANumber;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AVar;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by Nathaël N on 12/07/16.
 */
public class Jsonifier<T> {
	private static final Logger logger = Logger.getLogger(Jsonifier.class);
	private final Class<T> tClass;

	public Jsonifier(Class<T> tClass) {
		this.tClass = tClass;
	}

	public static String toJson(Object o) {
		logger.debug("Object to JSON:" + o);
		return correct(new GsonBuilder()
				.setPrettyPrinting()
				.create()
				.toJson(o));
	}
	public T fromJson(String s) {
		logger.debug("Creating new " + tClass.getTypeName() + " from Json");
		return new GsonBuilder()
				.create()
				.fromJson(s, tClass);
	}

	public static String fromAEntity(AEntity entity) {
		JsonObject json = new JsonObject();
		json.addProperty("class_name", entity.getClass().toString());

		if(entity instanceof AList) {
			AList<AEntity> list = (AList<AEntity>) entity;
			JsonArray array = new JsonArray();

			for(AEntity ae : list.getList())
				array.add(new JsonParser().parse(fromAEntity(ae)));

			json.add("value", array);
			if(list.getLabel() != null)
				json.addProperty("label", list.getLabel());
		}
		else
			json.add("value", new JsonParser().parse(toJson(entity)).getAsJsonObject());

		return json.toString();
	}
	public static AEntity toAEntity(String src) {
		JsonObject element = new JsonParser().parse(src).getAsJsonObject();
		String type = element.get("class_name").getAsString();

		if(type.equals(AList.class.toString())) {
			JsonArray list = element.get("value").getAsJsonArray();
			AList alAE = new AList();

			for(int i=0; i<list.size(); i++)
				alAE.add(toAEntity(list.get(i).toString()));

			if(element.has("label"))
				alAE.setLabel(element.get("label").getAsString());
			return alAE;
		} else {
			JsonObject object = element.get("value").getAsJsonObject();

			if(object.has("format"))
				return new Jsonifier<>(AVar.class).fromJson(element.get("value").toString());
		}

		return null;
	}

	private static String correct(String json) {
		String s = json.replaceAll("([0-9]*)\\.0([^0-9])", "$1$2");
		return s;
	}

}
