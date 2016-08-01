package fr.axonic.avek.gui.model.json;

import com.google.gson.*;
import fr.axonic.base.ANumber;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AVar;
import org.apache.log4j.Logger;

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
	private static String correct(String json) {
		String s = json.replaceAll("([0-9]*)\\.0([^0-9])", "$1$2");
		return s;
	}

	public T fromJson(String s) {
		logger.debug("Creating new " + tClass.getTypeName() + " from Json");
		return new GsonBuilder().create().fromJson(s, tClass);
	}

	public static AEntity toAEntity(String src) {
		JsonElement element = new JsonParser().parse(src);

		if(element.isJsonPrimitive()) {
			return new ANumber(element.getAsDouble());
		}
		else if(element.isJsonArray()) {
			JsonArray list = element.getAsJsonArray();
			AList alAE = new AList();

			for(int i=0; i<list.size(); i++)
				alAE.add(toAEntity(list.get(i).toString()));

			return alAE;
		} else {
			JsonObject object = element.getAsJsonObject();

			if(object.has("format"))
				return new Jsonifier<>(AVar.class).fromJson(src);
			/*else {
				if(object.get("id") != null) {
					MonitoredSystem ms = new MonitoredSystem(object.get("id").getAsInt());

					for(Map.Entry<String, JsonElement> entry: object.entrySet()) {
						ms.addCategory(entry.getKey());
						ms.addAVar(entry.getKey(), (AVar)toAEntity(entry.getValue().toString()));
					}

					return ms;
				}
			}*/
		}

		return null;
	}
}
