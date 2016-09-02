package fr.axonic.avek.gui.components.jellybeans;

import java.util.List;

/**
 * Created by Nathaël N on 12/08/16.
 */
public class JellyBeanItemSimple<T,U> extends JellyBeanItem<T,U,U> {

    public JellyBeanItemSimple(final T linkedObject, final List<U> stateItemList) {
        super(linkedObject,stateItemList);

        super.setGetStateFromItemListMethod(a -> a);
    }
}