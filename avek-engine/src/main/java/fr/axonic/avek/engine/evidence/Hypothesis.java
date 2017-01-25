package fr.axonic.avek.engine.evidence;

/**
 * Created by cduffau on 07/11/16.
 */
public class Hypothesis<T extends Element> extends Evidence<T>{
    public Hypothesis(String name) {
        super(name, null);
    }

    public Hypothesis() {
    }
}
