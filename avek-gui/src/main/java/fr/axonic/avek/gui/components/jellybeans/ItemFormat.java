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

    final Collection<U> originalCollection;

    ItemFormat(final T linkedObject, final Collection<U> states) {
        super(linkedObject);
        originalCollection = states;

        for(U state : originalCollection) {
            this.states.add(new ItemStateFormat<>(state));
        }
    }

    public void setGetStateLabelMethod(Function<U,String> getStateLabelMethod) {
        for(ItemStateFormat<U> state : states) {
            state.setGetLabelMethod(getStateLabelMethod);
        }
    }

    public Collection<ItemStateFormat<U>> getStates() {
        return Collections.unmodifiableCollection(states);
    }
}

