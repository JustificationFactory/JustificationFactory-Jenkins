package fr.axonic.base.engine;

import fr.axonic.base.*;
import fr.axonic.base.format.Format;
import fr.axonic.observation.binding.BindingParametersException;
import fr.axonic.observation.binding.BindingTypesException;
import fr.axonic.observation.binding.BindingsBiDirectional;
import fr.axonic.observation.binding.listener.AVarSimpleBindingListener;
import fr.axonic.observation.event.AEntityChangedEvent;
import fr.axonic.observation.event.ChangedEventType;
import fr.axonic.validation.Verifiable;
import fr.axonic.validation.Verify;
import fr.axonic.validation.exception.RuntimeVerificationException;
import fr.axonic.validation.exception.VerificationException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
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
@XmlSeeAlso({ANumber.class, ABoolean.class, ADate.class, AString.class, ARangedString.class, AContinuousNumber.class})
public /*abstract*/ class AVar<T> extends AElement implements Comparable<AVar>, Cloneable, Verifiable, Serializable {

	protected T value, defaultValue;

	// TODO
	private Format format;

	public static AVar create(Format format) {
		try {
			return (AVar)format.getAVarType().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
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
		this.setLabel(label);
		this.format = format;
		this.value = value;
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
	public Class getType() {
		return this.getFormat() == null ? null : this.getFormat().getFormatType();
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
		AVar result = null;
		result = AVar.create(format);

		result.setCode(getCode());
		result.setLabel(getLabel());
		result.setEditable(isEditable());
		result.setMandatory(isMandatory());
		result.setPath(getPath());
		try {
			result.setDefaultValue(getDefaultValue());
			result.setValue(getValue());
		} catch (VerificationException e) {
			e.printStackTrace();
		}

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

	private <S extends AEntity> boolean isBindingCompatibleType(S entity){

		return entity.getClass().equals(format.getAVarType());
	}

	@Override
	public <S extends AEntity> void bind(S entity) throws BindingTypesException {

		try {

			if(entity == null){
				throw new NullPointerException();
			}

			if(!isBindingCompatibleType(entity)){
				throw new BindingTypesException("Given entity isn't an AVar or hasn't compatible value type.");
			}

			AVar<T> bindedAVar = ((AVar<T>) entity);
			setBindElement(bindedAVar);

			setEditable(bindedAVar.isEditable());
			setMandatory(bindedAVar.isMandatory());
			setValue(bindedAVar.getValue());

			AVarSimpleBindingListener<T> listener = new AVarSimpleBindingListener<>(this);

			entity.addListener(listener);
			entity.setBindingListener(listener);

		} catch (VerificationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public <S extends AEntity> void bindBiDirectional(S entity)
			throws BindingTypesException, VerificationException, BindingParametersException {

		BindingsBiDirectional.bindBiDirectional(this, entity);
	}

	@Override
	public void unbind(){
		if(getBindElement() != null) {
			AVar<T> bindElt = (AVar<T>) getBindElement();

			getBindElement().removeListener(getBindElement().getBindingListener());
			getBindElement().setBindingListener(null);

			setBindElement(null);

			if (bindElt.isBindWith(this)) {
				bindElt.unbind();
			}
		}
	}

	@Override
	public <S extends AEntity> void setValues(S entity) throws VerificationException {
		if(this.getClass().equals(entity.getClass())){
			setEditable(((AVar<T>) entity).isEditable());
			setMandatory(((AVar<T>) entity).isMandatory());
			setValue(((AVar<T>) entity).getValue());
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
	 * is reverted and the fr.axonic.validation exception is passed.</li>
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
	 *             fr.axonic.validation procedure of this AVar (see
	 *             {@link Verifiable#verify(boolean)}).
	 */

	public void setProperty(String propertyName, Object newValue) {

		Object oldValue = getPropertyValue(propertyName);

		setPropertyValue(propertyName, newValue);

		try {

			if (isPropertyVerifiable(propertyName)) {
				verify(false);
			}

			this.firePropertyChange(propertyName, oldValue, newValue);
			this.fireEvent(new AEntityChangedEvent(ChangedEventType.CHANGED, this, propertyName, oldValue, newValue));

		} catch (VerificationException e) {

			setPropertyValue(propertyName, oldValue);
			throw new RuntimeVerificationException(e);

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
		return super.toString()+
				", value=" + value +
				", defaultValue=" + defaultValue +
				", format=" + format;
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
