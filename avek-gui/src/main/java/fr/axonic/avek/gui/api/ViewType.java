package fr.axonic.avek.gui.api;

import fr.axonic.avek.gui.view.*;
import fr.axonic.avek.gui.view.etablisheffect.EstablishEffectView;
import fr.axonic.avek.gui.view.generalize.GeneralizeView;
import fr.axonic.avek.gui.view.strategyselection.StrategySelectionView;
import fr.axonic.avek.gui.view.treat.TreatView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by cduffau on 12/08/16.
 */
public enum ViewType {
    TREAT_VIEW(TreatView.class, ComponentType.MONITORED_SYSTEM, ComponentType.EXPERIMENTATION_PARAMETERS, ComponentType.COMPLEMENTARY_FILES, ComponentType.COMMENTS),
    ESTABLISH_EFFECT_VIEW(EstablishEffectView.class,ComponentType.MONITORED_SYSTEM, ComponentType.EXPERIMENTATION_PARAMETERS, ComponentType.COMPLEMENTARY_FILES, ComponentType.COMMENTS, ComponentType.EFFECTS),
    GENERALIZE_VIEW(GeneralizeView.class, ComponentType.MONITORED_SYSTEM, ComponentType.EXPERIMENTATION_PARAMETERS, ComponentType.COMPLEMENTARY_FILES, ComponentType.COMMENTS, ComponentType.EFFECTS),
    STRATEGY_SELECTING_VIEW(StrategySelectionView.class,ComponentType.SELECTION);

    List<ComponentType> compatibleComponents;
    Class viewClass;

    <T extends AbstractView> ViewType( Class<T> viewClass, ComponentType ... compatibleComponents) {
        this.compatibleComponents = Arrays.asList(compatibleComponents);
        this.viewClass = viewClass;
    }

    public List<ComponentType> getCompatibleComponents() {
        return compatibleComponents;
    }

    public Class getViewClass() {
        return viewClass;
    }

    public boolean isContentCompatible(Map<ComponentType,Object> content){
        for(ComponentType componentType : content.keySet()){
            if(!compatibleComponents.contains(componentType)){
                return false;
            }
            else {
                if(!componentType.isCompatibleObject(content.get(componentType))){
                    return false;
                }
            }
        }
        return true;
    }
}
