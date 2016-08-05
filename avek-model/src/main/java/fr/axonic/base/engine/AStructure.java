package fr.axonic.base.engine;

import fr.axonic.observation.AEntityListener;
import fr.axonic.observation.binding.BindingParametersException;
import fr.axonic.observation.binding.BindingTypesException;
import fr.axonic.observation.event.AEntityChanged;
import fr.axonic.observation.event.ChangedEventType;
import fr.axonic.validation.exception.VerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cduffau on 11/07/16.
 */
@XmlRootElement
public abstract class AStructure extends AElement {

    private static Logger LOGGER = LoggerFactory.getLogger(AStructure.class);
    @XmlTransient
    private final Map<String, AEntity> fieldsContainer = new HashMap<>();
    @XmlTransient
    private final AEntityListener internalListener;

    public AStructure(){
        internalListener = new AEntityListener() {

            @Override
            public void changed(AEntityChanged events) {
                fireEvent(events);
            }

            @Override
            public boolean listenOnlyEffectiveChanges() {
                return false;
            }

            @Override
            public boolean acceptChangedType(ChangedEventType changedEventType) {
                return true;
            }
        };
    }

    /**
     * Initializes AStructure. This method is being called just after
     * object construction if @AStructureInit annotation has been defined.
     */
    protected void init(){

        try {
            Field[] fields = this.getClass().getDeclaredFields();

            for (Field field : fields){

                if(AStructure.class.isAssignableFrom(field.getType())
                        || AVar.class.isAssignableFrom(field.getType()) || AList.class.isAssignableFrom(field.getType())){

                    field.setAccessible(true);
                    AEntity entity = (AEntity) field.get(this);

                    fieldsContainer.put(field.getName(), entity);
                }
            }
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage(), e);
        }

        fieldsContainer.values().forEach(aEntity -> aEntity.addListener(internalListener));
    }

    @Override
    public <S extends AEntity> void bind(S entity) throws BindingTypesException {

        if(entity == null){
            throw new NullPointerException();
        }

        if(!this.getClass().equals(entity.getClass())){
            throw new BindingTypesException("Given entity type doesn't match to current structure type");
        }

        for(Map.Entry<String, AEntity> entry : fieldsContainer.entrySet()) {
            entry.getValue().bind(((AStructure) entity).getFieldsContainer().get(entry.getKey()));
        }

        setBindElement(entity);
    }

    @Override
    public <S extends AEntity> void bindBiDirectional(S entity)
            throws BindingTypesException, VerificationException, BindingParametersException {

        for(Map.Entry<String, AEntity> entry : fieldsContainer.entrySet()) {
            entry.getValue().bindBiDirectional(((AStructure) entity).getFieldsContainer().get(entry.getKey()));
        }

        this.setBindElement(entity);
        entity.setBindElement(this);
    }

    @Override
    public void unbind() {
        if(getBindElement() != null) {

            AStructure bindElt = (AStructure) getBindElement();
            setBindElement(null);

            fieldsContainer.values().forEach(AEntity::unbind);

            if (bindElt.isBindWith(this)) {
                bindElt.unbind();
            }
        }
    }

    @Override
    public <S extends AEntity> void setValues(S entity) throws VerificationException, BindingTypesException {
        if(this.getClass().equals(entity.getClass())){
            for(Map.Entry<String, AEntity> entry : fieldsContainer.entrySet()){
                entry.getValue().setValues(((AStructure) entity).getFieldsContainer().get(entry.getKey()));
            }
        } else {
            throw new BindingTypesException("Current entity cannot be set with an entity of type " + entity.getClass().getName());
        }
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();

        builder.append("AStructure{");

        fieldsContainer.entrySet().forEach(entry ->
                builder.append(entry.getKey() + "=").append(entry.getValue().toString()).append(", "));

        if(fieldsContainer.size() > 1){
            builder.delete(builder.length() - 2, builder.length());
        }

        return builder.append("}").toString();
    }

    @XmlTransient
    public Map<String, AEntity> getFieldsContainer() {
        return fieldsContainer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof AStructure))
            return false;
        if (!super.equals(o))
            return false;

        AStructure that = (AStructure) o;

        return getFieldsContainer() != null ?
                getFieldsContainer().equals(that.getFieldsContainer()) :
                that.getFieldsContainer() == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getFieldsContainer() != null ? getFieldsContainer().hashCode() : 0);
        return result;
    }

}
