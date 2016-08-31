package fr.axonic.avek.gui.model;

import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;

/**
 * Created by nathael on 31/08/16.
 */
public class GUIExperimentParameter {
    private final AList<AEntity> list;

    public GUIExperimentParameter(AList<AEntity> list) {
        this.list = list;
    }

    public AList<AEntity> getAList() {
        return list;
    }

    @Override
    public String toString() {
        return "GUIExperimentParameter{"+list.getLabel()+" : "+list.getList()+"}";
    }
}
