package fr.axonic.avek.gui.model;

/**
 * Created by Nathaël N on 29/06/16.
 */
public class AVar {
	private final String key;
	private final String valueType;
	private final Object value;
	private final String unit;

	public <TT> AVar(String key, Class<TT> valueType, TT value) {
		this(key, valueType, value, null);
	}
	public <TT> AVar(String key, Class<TT> valueType, TT value, String unit) {
		this.key = key;
		this.valueType = valueType.getName();
		this.value = value;
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
		return key+" : "+value.toString()+(unit==null?"":(" "+unit));
	}
}
