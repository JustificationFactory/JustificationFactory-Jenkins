package fr.axonic.base.format;

import fr.axonic.base.engine.AVar;
import org.apache.log4j.Logger;


public abstract class Format<U extends Object, S extends Object> {

    private final static Logger LOGGER = Logger.getLogger(Format.class);

    protected Class formatType;
    protected Class<? extends AVar> aVarType;

    public Format(){
    }

    public <T extends AVar<U>> Format(Class<T> aVarType, Class<U> formatType){
        this.aVarType= aVarType;
        this.formatType=formatType;
        /**try {
            this.formatType=formatType.getDeclaredField("value".getType();
        } catch (NoSuchFieldException e) {
            LOGGER.error(e);
        }*/
    }

    public Class getFormatType() {
        return formatType;
    }

    public Class<? extends AVar> getAVarType() {
        return aVarType;
    }

    private void setFormatType(Class formatType) {
        this.formatType = formatType;
    }

    private void setaVarType(Class<? extends AVar> aVarType) {
        this.aVarType = aVarType;
    }

    public abstract S marshalValue(U value);


    public abstract U unmarshalValue(S prettyValue);

    @Override
    public String toString() {
        return "Format{" +
                "formatType=" + formatType +
                '}';
    }
}
