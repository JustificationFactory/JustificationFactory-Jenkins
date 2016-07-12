package fr.axonic.avek.gui.model.json;

import com.google.gson.GsonBuilder;

/**
 * Created by Nathaël N on 12/07/16.
 */
public class Jsonifier {
	public static String toJson(Object o) {
		String s = new GsonBuilder()
				.setPrettyPrinting()
				.create()
				.toJson(o);
		return s;
	}

	public static <T> T fromJson(String s, Class<T> c) {
		GsonBuilder gsonBuilder = new GsonBuilder();

		T t = gsonBuilder.create().fromJson(s, c);
		return t;
	}
}
