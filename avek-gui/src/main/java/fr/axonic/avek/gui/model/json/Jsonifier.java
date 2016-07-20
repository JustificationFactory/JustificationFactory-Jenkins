package fr.axonic.avek.gui.model.json;

import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;

/**
 * Created by Nathaël N on 12/07/16.
 */
public abstract class Jsonifier {
	private static final Logger logger = Logger.getLogger(Jsonifier.class);

	public static String toJson(Object o) {
		logger.info("Object to JSON:"+ o);
		return new GsonBuilder()
				.setPrettyPrinting()
				.create()
				.toJson(o);
	}

	public static <T> T fromJson(String s, Class<T> c) {
		logger.info("Creating new "+c.getName()+" from Json");
		return new GsonBuilder().create().fromJson(s, c);
	}
}
