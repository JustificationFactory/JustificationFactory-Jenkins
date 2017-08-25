package fr.axonic.avek.instance.jenkins.conclusion;

import fr.axonic.base.engine.AEnumItem;

/**
 * Created by cduffau on 24/08/17.
 */
public enum JenkinsStatusTrackerEnum implements AEnumItem{
    BUILD,
    UNIT_TEST,
    INTEGRATION_TEST,
    TDD,
    STRESS_TEST;

    @Override
    public String getLabel() {
        return name();
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getPath() {
        return "fr.axonic.avek.jenkins.status.tracker";
    }

    @Override
    public int getIndex() {
        return ordinal();
    }
}
