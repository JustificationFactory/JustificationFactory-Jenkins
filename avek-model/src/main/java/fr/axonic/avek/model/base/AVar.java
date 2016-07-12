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
@XmlSeeAlso({ ANumber.class, ABoolean.class, ADate.class, AString.class, AEnum.class, ARangedString.class, ARangedEnum.class, AContinuousNumber.class})
public /*abstract*/ class AVar<T> extends AElement implements Comparable<AVar>, Cloneable, Verifiable, Serializable {

	private T value, defaultValue;

	// TODO
	private Format format;




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
			case RANGED_NUMBER: {
				result = new AContinuousNumber();
			}
			break;
			case STRING: {
				result = new AString();
			}
			break;
			case ENUM: {
				result = new AEnum<>();
			}
			break;
			case RANGED_ENUM: {
				result = new ARangedEnum<>();
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

	protected AVar(Format format, T value) {
		this.format = format;
		this.value = value;
	}
	protected AVar(String label, Format format, T value) {
		this.format = format;
		this.value = value;
		this.label=label;
	}

	@XmlElement
	public T getValue() {
		return value;
	}

	public void setValue(T value) throws VerificationException {
		setProperty(AVarProperty.VALUE.name(), value);
	}


	@XmlTransient
	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		setUnverifiableProperty(AVarProperty.FORMAT.name(), format);
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


	@Override
	public AVar clone() throws CloneNotSupportedException {
		AVar result = AVar.create(format);

		result.code=code;
		result.editable=editable;

		// TODO clone format
		result.format=format;
		result.label=label;
		result.mandatory=mandatory;
		result.path=path;

		// TODO clone value
		result.value = value;
		result.defaultValue = defaultValue;

		return result;
	}

	protected void setPropertyValue(String propertyName, Object newPropertyValue) {
		switch (AVarProperty.valueOf(propertyName)) {
			case FORMAT: {
				format = (Format) newPropertyValue;
			}
			break;
			case DEFAULT_VALUE:
				defaultValue = (T) newPropertyValue;
				break;
			case VALUE: {
				value = (T) newPropertyValue;
			}
			break;

			default: {
				super.setPropertyValue(propertyName,newPropertyValue);
			}
		}

	}

	protected Object getPropertyValue(String propertyName) {
		Object result;
		switch (AVarProperty.valueOf(propertyName)) {
			case FORMAT: {
				result = format;
			}
			break;
			case VALUE: {
				result = value;
			}
			break;
			case DEFAULT_VALUE: {
				result  =defaultValue;
				break;
			}
			default: {
				result = super.getPropertyValue(propertyName);
			}

		}
		return result;
	}

	protected boolean isPropertyVerifiable(String propertyName) {
		boolean result;
		switch (AVarProperty.valueOf(propertyName)) {
			case DEFAULT_VALUE:
			case VALUE: {
				result = true;
			}
			break;
			default: {
				result = super.isPropertyVerifiable(propertyName);
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

	@XmlTransient
	public T getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(T defaultValue) throws VerificationException {
		setProperty(AVarProperty.DEFAULT_VALUE.name(), defaultValue);
		if(getPropertyValue(AVarProperty.VALUE.name())==null){
			setProperty(AVarProperty.VALUE.name(), defaultValue);
		}
	}
}
