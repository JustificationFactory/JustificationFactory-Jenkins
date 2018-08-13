package fr.axonic.avek.engine.pattern.type;

import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Element;

import javax.xml.bind.annotation.XmlRootElement;
import java.lang.reflect.InvocationTargetException;

@XmlRootElement
public class OutputType<T extends Conclusion> extends SupportType<T> {

    private String name;

    public OutputType() {
        super();
    }

    public OutputType(Class<T> conclusionClass) {
        super(conclusionClass);
    }

    public OutputType(String name, Class<T> conclusionClass) {
        super(conclusionClass);
        this.name = name;
    }

    public Conclusion create(String name, Element o) throws StepBuildingException {
        try {
            if (name == null) {
                return (Conclusion) getType().getClassType().getDeclaredConstructor(o.getClass()).newInstance(o);

            } else {
                return (Conclusion) getType().getClassType().getDeclaredConstructor(String.class, o.getClass()).newInstance(name, o);

            }

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new StepBuildingException("Impossible to create conclusion from " + o, e);
        }
    }

    public InputType<T> transformToInput() {
        return new InputType<>(getType().getClassType().getName(), getType());
    }
}
