package fr.axonic.avek.gui.model.json;

import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;

/**
 * Created by Nathaël N on 12/07/16.
 */
public class Jsonifier <T> {
	private static final Logger logger = Logger.getLogger(Jsonifier.class);
	private final Class<T> tClass;

	public Jsonifier(Class<T> c) {
		this.tClass = c;
	}

	public static String toJson(Object o) {
		logger.info("Object to JSON:"+ o);
		return new GsonBuilder()
				.setPrettyPrinting()
				.create()
				.toJson(o);
	}

	public T fromJson(String s) {
		return fromJson(s, tClass);
	}

	public static <TT> TT fromJson(String s, Class<TT> c) {
		logger.info("Creating new "+c.getName()+" from Json");
		return new GsonBuilder().create().fromJson(s, c);
	}
}
