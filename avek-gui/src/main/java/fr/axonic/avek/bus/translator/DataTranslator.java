package fr.axonic.avek.bus.translator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Nathaël N on 28/07/16.
 */
public abstract class DataTranslator<T,S> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataTranslator.class);
    private static final HashSet<DataTranslator> toGUI = new HashSet<>();
    private static final HashSet<DataTranslator> toEngine = new HashSet<>();

    static {
        toGUI.add(new EffectEnumToGuiEffect());
        toGUI.add(new EffectToGuiEffect());
    }

    /**
     * Translate object of type T to object of type S
     * @param t source object
     * @return translated object
     */
    protected abstract S translate(T t);

    /**
     * Translate given object in what GUI need
     * @param engineObject object to translate
     * @return translated object, or same object if GUI want this object as it is
     */
    public static Object translateForGUI(Object engineObject) {
        return translateFor(engineObject, toGUI);
    }

    /**
     * Translate given object in what engine need
     * @param guiObject object to translate
     * @return translated object, or same object if engine want this object as it is
     */
    public static Object translateForEngine(Object guiObject) {
        return translateFor(guiObject, toEngine);
    }

    /**
     * Will try to call translate Method for each dataTranslator set
     * @param object The object to translate
     * @param destination The set of dataTranslator to use
     * @return Translated object, or the object itself if not matched any translator
     */
    private static Object translateFor(Object object, HashSet<DataTranslator> destination) {
        for(DataTranslator translator : destination) {
            try {
                @SuppressWarnings("unchecked")
                Object ret = translator.translate(object);
                LOGGER.debug("Translated using "+translator.getClass().getSimpleName());
                return ret;
            }catch(ClassCastException ignored) {
                // Cannot translateForGUI using this translator
            }
        }

        LOGGER.debug("Not translated "+object.getClass()+" using "+destination);
        return object;
    }

    @Override
    public String toString() {
        return "DataTranslator<"
                + Arrays.toString(((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments())
                +">";
    }
}
