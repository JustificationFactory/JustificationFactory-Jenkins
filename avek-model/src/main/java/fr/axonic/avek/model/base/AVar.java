package fr.axonic.avek.model.base;

import fr.axonic.avek.model.verification.Verifiable;
import fr.axonic.avek.model.verification.Verify;
import fr.axonic.avek.model.verification.exception.VerificationException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;


/**
 * This abstract class defines variables. All variables should inherit from this
 * class.
 *
 *
 * @author bgrabiec
 *
 */
// TODO cursor, binding, format, multi-thread, describe the procedure to add
// properties
@XmlRootElement
@XmlSeeAlso({ ANumber.class, ABoolean.class, ADate.class, AString.class })
public /*abstract*/ class AVar implements Comparable<AVar>, Cloneable, Verifiable, Serializable {

	private Object value;

	private String code;

	private String path;

	private boolean editable = true;

	// TODO
	private Format format;

	private boolean mandatory = false;

	private String label;

	private transient PropertyChangeSupport changeSupport;

	public static AVar create(Format format) {

		AVar result = null;

		switch (format.getType()) {
			case BOOLEAN: {
				result = new ABoolean();
			}
			break;
			case DATE: {
				result = new ADate();
			}
			break;
			case NUMBER: {
				result = new ANumber();
			}
			break;
			case STRING: {
				result = new AString();
			}
			break;
			default: {
				throw new IllegalArgumentException("Variable cannot be initialized. Unknown format.");
			}
		}

		result.setFormat(format);

		return result;
	}

	protected AVar() {
		this(null, null);
	}

	protected AVar(Format format) {
		this(format, null);
	}

	protected AVar(Format format, Object value) {
		this.format = format;
		this.value = value;
	}
	protected AVar(String label, Format format, Object value) {
		this.format = format;
		this.value = value;
		this.label=label;
	}

	@XmlElement
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) throws VerificationException {
		setProperty(AVarProperty.VALUE.name(), value);
	}

	@XmlElement
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		setUnverifiableProperty(AVarProperty.CODE.name(), code);
	}

	@XmlElement
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		setUnverifiableProperty(AVarProperty.PATH.name(), path);
	}

	@XmlTransient
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		setUnverifiableProperty(AVarProperty.EDITABLE.name(), editable);
	}

	@XmlTransient
	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		setUnverifiableProperty(AVarProperty.FORMAT.name(), format);
	}

	@XmlTransient
	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		setUnverifiableProperty(AVarProperty.MANDATORY.name(), mandatory);
	}

	@XmlTransient
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		setUnverifiableProperty(AVarProperty.LABEL.name(), label);
	}

	@XmlTransient
	public FormatType getType() {
		return this.getFormat() == null ? null : this.getFormat().getType();
	}

	@Override
	public int compareTo(AVar var) {
		// TODO
		return 0;
	}

	public int compareToValue(Object value) {
		// TODO
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (obj instanceof AVar) {
			AVar other = (AVar) obj;
			if (!(this.getType() == null && other.getType() == null) && this.getType() != other.getType())
				return false;

			if (this.getValue() == null && other.getValue() == null)
				return true;
			if (this.getValue() == null)
				return false;
			if (other.getValue() == null)
				return false;

			// TODO
			// return this.getFormat()..equalsValue(this.getValue(),
			// other.getValue());
			return other.getValue().equals(this.getValue());
		}

		return false;
	}

	public boolean equalsValue(Object obj) {
		// TODO
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return this.getValue() != null ? this.getValue().hashCode() : 0;
	}

	public String toString(Format format) {
		// TODO
		return super.toString();
	}

	public String toText() {
		if (this.value == null)
			return "";
		return this.value.toString();
	}

	public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
		if (listener == null)
			return;
		if (changeSupport == null)
			changeSupport = new PropertyChangeSupport(this);
		changeSupport.addPropertyChangeListener(listener);
	}

	public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
		if (listener == null || changeSupport == null)
			return;
		changeSupport.removePropertyChangeListener(listener);
	}

	public synchronized PropertyChangeListener[] getPropertyChangeListeners() {
		if (changeSupport == null)
			return new PropertyChangeListener[0];
		return changeSupport.getPropertyChangeListeners();
	}

	public synchronized void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		if (listener == null)
			return;
		if (changeSupport == null)
			changeSupport = new PropertyChangeSupport(this);
		changeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public synchronized void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		if (listener == null || changeSupport == null)
			return;
		changeSupport.removePropertyChangeListener(propertyName, listener);
	}

	protected void firePropertyChange(Enum<?> propertyEnum, Object oldValue, Object newValue) {
		firePropertyChange(propertyEnum.name(), oldValue, newValue);
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {

		if (oldValue != null && newValue != null && oldValue.equals(newValue)) {
			return;
		}

		PropertyChangeSupport changeSupport = this.changeSupport;
		if (changeSupport == null) {
			return;
		}
		changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	@Override
	public AVar clone() throws CloneNotSupportedException {
		AVar result = AVar.create(format);

		result.setCode(code);
		result.setEditable(editable);

		// TODO clone format
		result.setFormat(format);
		result.setLabel(label);
		result.setMandatory(mandatory);
		result.setPath(path);

		// TODO clone value
		result.value = value;

		return result;
	}

	protected void setPropertyValue(String propertyName, Object newPropertyValue) {
		switch (AVarProperty.valueOf(propertyName)) {
			case CODE: {
				code = (String) newPropertyValue;
			}
			break;
			case EDITABLE: {
				editable = (boolean) newPropertyValue;
			}
			break;
			case FORMAT: {
				format = (Format) newPropertyValue;
			}
			break;
			case LABEL: {
				label = (String) newPropertyValue;
			}
			break;
			case MANDATORY: {
				mandatory = (boolean) newPropertyValue;
			}
			break;
			case PATH: {
				path = (String) newPropertyValue;
			}
			break;
			case VALUE: {
				value = newPropertyValue;
			}
			break;
			default: {
				throw new UnknownPropertyException();
			}
		}

	}

	protected Object getPropertyValue(String propertyName) {
		Object result;
		switch (AVarProperty.valueOf(propertyName)) {
			case CODE: {
				result = code;
			}
			break;
			case EDITABLE: {
				result = editable;
			}
			break;
			case FORMAT: {
				result = format;
			}
			break;
			case LABEL: {
				result = label;
			}
			break;
			case MANDATORY: {
				result = mandatory;
			}
			break;
			case PATH: {
				result = path;
			}
			break;
			case VALUE: {
				result = value;
			}
			break;
			default: {
				throw new UnknownPropertyException();
			}

		}
		return result;
	}

	protected boolean isPropertyVerifiable(String propertyName) {
		boolean result;
		switch (AVarProperty.valueOf(propertyName)) {
			case VALUE: {
				result = true;
			}
			break;
			default: {
				result = false;
			}
		}
		return result;
	}

	/**
	 * Set the value of a property with a given property name. There are to
	 * cases:
	 * <ol>
	 * <li>The property is verifiable - the modification is performed in the
	 * following steps: the property value is changed internally, the method
	 * {@link Verifiable#verify(boolean)} is called. If the the modification is
	 * correct, the appropriate listeners are notified. Otherwise modification
	 * is reverted and the verification exception is passed.</li>
	 * <li>The property is unverifiable</li>
	 * </ol>
	 *
	 * @param propertyName
	 *            A valid property of this AVar
	 * @param newValue
	 *            A new value of the property
	 * @throws VerificationException
	 *             The exception is thrown only for the verifiable properties in
	 *             case when their modification does not satisfy the
	 *             verification procedure of this AVar (see
	 *             {@link Verifiable#verify(boolean)}).
	 */
	public void setProperty(String propertyName, Object newValue) throws VerificationException {

		Object oldValue = getPropertyValue(propertyName);

		setPropertyValue(propertyName, newValue);

		try {

			if (isPropertyVerifiable(propertyName)) {
				verify(false);
			}

			this.firePropertyChange(propertyName, oldValue, newValue);

		} catch (VerificationException e) {

			setPropertyValue(propertyName, oldValue);
			throw e;

		}
	}

	public void setUnverifiableProperty(String propertyName, Object newValue) {

		if (isPropertyVerifiable(propertyName)) {
			throw new IllegalArgumentException("The property " + propertyName + " is verifiable");
		}

		Object oldValue = getPropertyValue(propertyName);

		setPropertyValue(propertyName, newValue);

		this.firePropertyChange(propertyName, oldValue, newValue);
	}

	@Override
	@Verify
	public void verify(boolean verifyConsistency) throws VerificationException {
		// DO NOTHING
		// There are no constraints to be verified in AVar.
	}

	@Override
	public String toString() {
		return "AVar{" + "value=" + value + ", code='" + code + '\'' + ", path='" + path + '\'' + '}';
	}
}
