package fr.axonic.avek.gui.model.json;

import com.google.gson.GsonBuilder;
import fr.axonic.avek.model.base.engine.AEntity;
import fr.axonic.avek.model.base.engine.AList;
import fr.axonic.avek.model.base.engine.AVar;
import org.apache.log4j.Logger;

/**
 * Created by Nathaël N on 12/07/16.
 */
public class Jsonifier<T> {
	private static final Logger logger = Logger.getLogger(Jsonifier.class);
	private final Class<T> tClass;

	public Jsonifier(Class<T> c) {
		this.tClass = c;
	}

	public static String toJson(Object o) {
		logger.debug("Object to JSON:" + o);
		return new GsonBuilder()
				.setPrettyPrinting()
				.create()
				.toJson(o);
	}

	public T fromJson(String s) {
		logger.debug("Creating new " + tClass.getName() + " from Json");
		return new GsonBuilder().create().fromJson(s, tClass);
	}

	public static AList<AEntity> toAListofAListAndAVar(String src) {
		AList<AEntity> alAE = new AList<>();
		AList<AEntity> al = new Jsonifier<>(AList.class).fromJson(src);

		for (Object o : al.getEntities()) {
			String s = Jsonifier.toJson(o);

			try {
				AVar av = new Jsonifier<>(AVar.class).fromJson(s);
				if (av.getFormat() == null)
					throw new ClassCastException("Cannot cast " + s + " to AVar");

				alAE.addEntity(av);
			} catch (ClassCastException cce) {
				try {
					AList<AEntity> av = toAListofAListAndAVar(s);
					alAE.addEntity(av);
				} catch (ClassCastException cce2) {
					logger.error("Impossible to load AVar", cce);
					logger.fatal("Impossible to load AList", cce2);
				}
			}
		}

		al.setEntities(alAE.getEntities());
		return al;
	}
}
