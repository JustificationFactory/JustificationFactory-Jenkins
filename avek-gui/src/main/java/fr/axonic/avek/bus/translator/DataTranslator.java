package fr.axonic.avek.bus.translator;

import java.util.HashSet;

/**
 * Created by Nathaël N on 28/07/16.
 */
public abstract class DataTranslator<T,S> {
    private static HashSet<DataTranslator> toGUI = new HashSet<>();
    private static HashSet<DataTranslator> toEngine = new HashSet<>();

    static {
        toGUI.add(new EffectEnumToJellyBeanItem());
        toGUI.add(new EffectToJellyBeanItem());
        toEngine.add(new JellyBeanItemToEffect());
    }

    protected abstract S translate(T t);

    public static Object translateForGUI(Object engineObject) {
        for(DataTranslator translator : toGUI) {
            try {
                return translator.translate(engineObject);
            }catch(ClassCastException ignored) {
                // Cannot translateForGUI v using this translator
            }
        }

        return engineObject;
    }

    public static Object translateForEngine(Object guiObject) {
        for(DataTranslator translator : toEngine) {
            try {
                return translator.translate(guiObject);
            }catch(ClassCastException ignored) {
                // Cannot translateForGUI using this translator
            }
        }

        return guiObject;
    }
}
