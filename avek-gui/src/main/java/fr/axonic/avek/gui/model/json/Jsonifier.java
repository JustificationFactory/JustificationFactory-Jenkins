package fr.axonic.avek.gui.model.json;

import com.google.gson.GsonBuilder;

/**
 * Created by Nathaël N on 12/07/16.
 */
public class Jsonifier {
	public static String toJson(Object o) {
		return new GsonBuilder()
				.setPrettyPrinting()
				.create()
				.toJson(o);
	}

	public static <T> T fromJson(String s, Class<T> c) {
		GsonBuilder gsonBuilder = new GsonBuilder();

		return gsonBuilder.create().fromJson(s, c);
	}
}
