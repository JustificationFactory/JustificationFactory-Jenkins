package fr.axonic.avek.gui.model;

/**
 * Created by Nathaël N on 29/06/16.
 */
public class AVar {
	private final String key;
	private final String valueType;
	private final Object value;
	private final String unit;

	public AVar(String key, Class valueType, Object value) {
		this(key, valueType, value, null);
	}
	public AVar(String key, Class valueType, Object value, String unit) {
		this.key = key;
		this.valueType = valueType.getName();
		this.value = valueType.cast(value); // verify class validity
		this.unit = unit;
	}

	public String getKey() {
		return key;
	}

	public Class getValueType() {
		try {
			return Class.forName(valueType);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value.toString()+(unit==null?"":(" "+unit));
	}
}
