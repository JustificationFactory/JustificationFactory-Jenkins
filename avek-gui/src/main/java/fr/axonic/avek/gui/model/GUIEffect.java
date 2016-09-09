package fr.axonic.avek.gui.model;

import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;

import java.util.*;

/**
 * Created by nathael on 31/08/16.
 */
public class GUIEffect<T> {
    private final Collection<T> bindCollection;
    private final Set<JellyBeanItem> addedItems = new LinkedHashSet<>();
    private final Map<JellyBeanItem, Runnable> onAddBindings = new HashMap<>();
    private final Map<JellyBeanItem, Runnable> onRemoveBindings = new HashMap<>();

    public GUIEffect() {
        super();
        this.bindCollection = null;
    }
    public GUIEffect(Collection<T> bindCollection) {
        super();
        this.bindCollection = bindCollection;
    }

    public void addAndBind(JellyBeanItem jbi, Runnable onAdd, Runnable onRemove) {
        onAddBindings.put(jbi, onAdd);
        onRemoveBindings.put(jbi, onRemove);
    }

    public void add(JellyBeanItem choice) {
        addedItems.add(choice);
        if(bindCollection != null) {
            if (onAddBindings.containsKey(choice)) {
                onAddBindings.get(choice).run();
            } else {
                throw new RuntimeException("Choice out of range");
            }
        }
    }
    public void remove(JellyBeanItem choice) {
        addedItems.remove(choice);
        if(bindCollection != null) {
            if (onRemoveBindings.containsKey(choice)) {
                onRemoveBindings.get(choice).run();
            } else {
                throw new RuntimeException("Choice out of range");
            }
        }
    }

    public Set<JellyBeanItem> getAddedItems() {
        return addedItems;
    }
    public Set<JellyBeanItem> getJellyBeanItemList() {
        return bindCollection!=null?onAddBindings.keySet():getAddedItems();
    }

}
