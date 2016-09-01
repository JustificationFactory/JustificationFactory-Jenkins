package fr.axonic.avek.gui.components.jellybeans;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by Nathaël N on 12/08/16.
 */
public class JellyBeanItem<T,U> {
    private final ItemFormat<T,U> itemFormat;
    private ItemStateFormat<U> currentState;
    private boolean editable;

    public JellyBeanItem(final T linkedObject, final Collection<U> states) {
        itemFormat = new ItemFormat<>(linkedObject, states);
        this.editable = false;
        setState(getStates().get(0));
    }

    // Getters  //  //  //

    public T getIdentifier() {
        return itemFormat.getObject();
    }
    public String getText() {
        return itemFormat.getLabel();
    }
    public List<ItemStateFormat<U>> getStates() {
        return itemFormat.getStates();
    }
    public ItemStateFormat<U> getState() {
        return currentState;
    }

    // Setters  //  //  //

    public void setState(U state) {
        for(ItemStateFormat<U> isfu : getStates()) {
            if(isfu.getObject().equals(state)) {
                setState(isfu);
                return;
            }
        }

        throw new RuntimeException("Tried to set state to a value out of range");
    }
    private void setState(ItemStateFormat<U> isfu) {
        ItemStateFormat<U> before = currentState;
        currentState = isfu;

        stateChangeListeners.forEach(listener -> listener.accept(before, isfu));
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        if(editableStateChangeListener != null)
            editableStateChangeListener.accept(editable);
    }

    //  Listeners   //  //  //

    private final Set<BiConsumer<ItemStateFormat<U>, ItemStateFormat<U>>> stateChangeListeners = new HashSet<>(); // LastValue, NewValue
    public void addStateChangeListener(BiConsumer<ItemStateFormat<U>, ItemStateFormat<U>> listener) {
        stateChangeListeners.add(listener);
        listener.accept(getState(), getState());
    }

    private Consumer<Boolean> editableStateChangeListener;
    void setEditableStateChangeListener(Consumer<Boolean> method) {
        editableStateChangeListener = method;
    }

    //  Active methods  //  //  //

    void nextState() {
        if (editable) {
            int currentStateIndex = getStates().indexOf(currentState);
            setState(getStates().get((currentStateIndex + 1) % itemFormat.getStates().size()));
        }
        // else nothing because readonly
    }

    //  Overridden   //  //  //

    @Override
    public String toString() {
        return "JellyBeanItem{"+itemFormat+" state:'"+getState()+"'" +(!editable?" readonly}":"}");
    }

    public ItemFormat<T,U> getFormat() {
        return itemFormat;
    }
}