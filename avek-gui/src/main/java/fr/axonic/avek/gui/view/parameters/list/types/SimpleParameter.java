package fr.axonic.avek.gui.view.parameters.list.types;

import fr.axonic.avek.gui.view.parameters.list.SensitiveParameter;
import fr.axonic.avek.model.base.AString;
import fr.axonic.avek.model.base.engine.AVar;

/**
 * Created by Nathaël N on 21/07/16.
 */
public class SimpleParameter extends SensitiveParameter {
	public SimpleParameter(int level, String value) {
		this(level, new AString(value));
	}
	public SimpleParameter(int level, AVar value) {
		super(level, value);
	}
}
