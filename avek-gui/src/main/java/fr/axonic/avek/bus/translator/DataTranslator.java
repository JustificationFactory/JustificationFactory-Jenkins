package fr.axonic.avek.bus.translator;

import org.apache.log4j.Logger;

import java.util.HashSet;

/**
 * Created by Nathaël N on 28/07/16.
 */
public abstract class DataTranslator<T,S> {
    private final static Logger LOGGER = Logger.getLogger(DataTranslator.class);
    private static HashSet<DataTranslator> toGUI = new HashSet<>();
    private static HashSet<DataTranslator> toEngine = new HashSet<>();

    static {
        toGUI.add(new EffectEnumToGuiEffect());
        toGUI.add(new EffectToGuiEffect());
        toEngine.add(new GUIEffectToEffect());
    }

    protected abstract S translate(T t);

    public static Object translateForGUI(Object engineObject) {
        return translateFor(engineObject, toGUI);
    }

    public static Object translateForEngine(Object guiObject) {
        return translateFor(guiObject, toEngine);
    }

    private static Object translateFor(Object object, HashSet<DataTranslator> destination) {
        for(DataTranslator translator : destination) {
            try {
                Object ret = translator.translate(object);
                LOGGER.debug("Translated using "+translator.getClass().getSimpleName());
                return ret;
            }catch(ClassCastException ignored) {
                // Cannot translateForGUI using this translator
            }
        }

        LOGGER.debug("Not translated "+object.getClass().getSimpleName());
        return object;
    }
}
