package fr.axonic.avek.gui.model.results;

/**
 * Created by Nathaël N on 07/07/16.
 */
public class ExpEffect {
	private Class<? extends IColorState> stateClass;
	private String name;

	public ExpEffect(Class<? extends IColorState> stateClass, String name) {
		this.stateClass = stateClass;
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public Class<? extends IColorState> getStateClass() {
		return stateClass;
	}

	@Override
	public String toString() {
		return getName();
	}
}
