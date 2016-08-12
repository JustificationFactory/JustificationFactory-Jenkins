package fr.axonic.avek.gui.components.jellybeans;

import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by Nathaël N on 12/08/16.
 */
public class JellyBeanItem <T> {
    private final static Logger LOGGER = Logger.getLogger(JellyBeanItem.class);

    private final List<T> states;
    private int currentStateIndex;
    private final String text;
    private boolean editable;

    public JellyBeanItem(String text, List<T> states) {
        this.text = text;
        this.states = states;
        //this.editable = true;
        setState(0);
    }

    List<T> getStates() {
        return states;
    }

    public String getText() {
        return text;
    }

    void setState(int id) {
        T lastState = getState();
        this.currentStateIndex = id;
        T newState = getState();

        for(BiConsumer<T,T> listener : stateChangeListeners)
            listener.accept(lastState, newState);
    }
    public void setState(T state) {
        setState(states.indexOf(state));
    }
    T getState() {
        return states.get(currentStateIndex);
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        if(editableStateChangeListener != null)
            editableStateChangeListener.accept(editable);
    }

    private Set<BiConsumer<T, T>> stateChangeListeners = new HashSet<>(); // LastValue, NewValue
    public void addStateChangeListener(BiConsumer<T, T> method) {
        stateChangeListeners.add(method);
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
}
