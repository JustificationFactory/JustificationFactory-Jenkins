package fr.axonic.avek.gui.components.jellybeans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by nathael on 01/09/16.
 */
public class ItemFormat<T,U> extends ItemStateFormat<T> {
    final List<ItemStateFormat<U>> states = new ArrayList<>();

    ItemFormat(final T linkedObject, final Collection<U> states) {
        super(linkedObject);
        for(U state : states) {
            this.states.add(new ItemStateFormat<>(state));
        }
    }

    public void setGetStateLabelMethod(Function<U,String> getLabelMethod) {
        for(ItemStateFormat<U> state : states) {
            state.setGetLabelMethod(getLabelMethod);
        }
    }
    public void setGetStateValueMethod(Function<U,String> getValueMethod) {
        for(ItemStateFormat<U> state : states) {
            state.setGetValueMethod(getValueMethod);
        }
    }

    public List<ItemStateFormat<U>> getStates() {
        return states;
    }

    @Override
    public String toString() {
        return "ItemFormat{'"+getLabel()+"'/'"+getValue()+"}";
    }
}

