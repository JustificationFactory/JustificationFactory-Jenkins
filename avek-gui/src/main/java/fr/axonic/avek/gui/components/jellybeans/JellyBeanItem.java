package fr.axonic.avek.gui.components.jellybeans;

import fr.axonic.base.engine.AEnumItem;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by Nathaël N on 12/08/16.
 */
public class JellyBeanItem <T> {
    private final AEnumItem id;
    private final List<T> states;
    private int currentStateIndex;
    private boolean editable;

    public JellyBeanItem(AEnumItem id, List<T> states) {
        this.id = id;
        this.states = states;
        //this.editable = true;
        setState(0);
    }

    List<T> getStates() {
        return states;
    }

    public AEnumItem getIdentifier() {
        return id;
    }

    public String getText() { return id.getLabel(); }

    private void setState(int id) {
        T lastState = getState();
        this.currentStateIndex = id;
        T newState = getState();

        for(BiConsumer<T,T> listener : stateChangeListeners)
            listener.accept(lastState, newState);
    }
    public void setState(T state) {
        setState(states.indexOf(state));
    }
    public T getState() {
        return states.get(currentStateIndex);
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        if(editableStateChangeListener != null)
            editableStateChangeListener.accept(editable);
    }

    private final Set<BiConsumer<T, T>> stateChangeListeners = new HashSet<>(); // LastValue, NewValue
    public void addStateChangeListener(BiConsumer<T, T> listener) {
        stateChangeListeners.add(listener);
        listener.accept(getState(), getState());
    }

    private Consumer<Boolean> editableStateChangeListener;
    void setEditableStateChangeListener(Consumer<Boolean> method) {
        editableStateChangeListener = method;
    }

    void nextState() {
        // ReadOnly
        if (editable) {
            setState((currentStateIndex + 1) % states.size());
        }
    }

    @Override
    public String toString() {
        return "JellyBeanItem{id="+id+", states="+states+", editable="+editable+", current state="+getState()+"}";
    }
}