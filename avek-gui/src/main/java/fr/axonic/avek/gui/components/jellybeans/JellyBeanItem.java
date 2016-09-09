package fr.axonic.avek.gui.components.jellybeans;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by Nathaël N on 12/08/16.
 */
public class JellyBeanItem<T,U,V> {
    private final T linkedObject;
    private final List<V> stateItemList;

    private Function<V,U> getStateFromItemListMethod;
    private BiConsumer<T,U> setStateMethod;

    private Function<T,String> getLabelMethod;
    private Function<U,String> getStateLabelMethod;
    private Function<U,String> getStateCodeMethod;

    // LastState, NewState
    private final Set<BiConsumer<U, U>> stateChangeListeners;
    private Consumer<Boolean> editableStateChangeListener;

    private V currentStateItem;
    private boolean editable;

    public JellyBeanItem(final T linkedObject, final List<V> stateItemList) {
        this.linkedObject = linkedObject;
        this.stateItemList = stateItemList;

        this.editable = false;
        this.currentStateItem = stateItemList.get(0);
        this.editableStateChangeListener = null;
        this.stateChangeListeners = new HashSet<>();
        this.getStateFromItemListMethod = null;
        this.setStateMethod = null;
        this.getLabelMethod = Object::toString;
        this.getStateLabelMethod = Object::toString;
        this.getStateCodeMethod = Object::toString;
    }

    // Getters  //  //  //

    public T getLinkedObject() {
        return linkedObject;
    }
    public String getLabel() {
        return getLabelMethod.apply(linkedObject);
    }
    String getStateCode(U state) {
        return getStateCodeMethod.apply(state);
    }
    U getCurrentState() {
        return getStateFromItemListMethod.apply(currentStateItem);
    }
    String toPrettyString() {
        String prettyString = "";
        String curStateLabel = "null";
        for(V stateItem : stateItemList) {
            String label = getStateLabelMethod.apply(getStateFromItemListMethod.apply(stateItem));

            if(stateItem.equals(currentStateItem)) {
                prettyString += ", ["+label+"]";
                curStateLabel = label;
            } else {
                prettyString += ", "+label;
            }
        }
        prettyString = prettyString.substring(2); // remove the first ", " of the String

        return "Current state: " + curStateLabel + "\n"
                + "All states: " + prettyString;
    }

    // Setters  //  //  //

    public void setGetStateFromItemListMethod(Function<V, U> getStateFromItemListMethod) {
        this.getStateFromItemListMethod = getStateFromItemListMethod;
    }
    public void setSetStateMethod(BiConsumer<T, U> setStateMethod) {
        this.setStateMethod = setStateMethod;
    }
    public void setGetLabelMethod(Function<T, String> getLabelMethod) {
        this.getLabelMethod = getLabelMethod;
    }
    public void setGetStateLabelMethod(Function<U, String> getStateLabelMethod) {
        this.getStateLabelMethod = getStateLabelMethod;
    }
    public void setGetStateCodeMethod(Function<U, String> getStateCodeMethod) {
        this.getStateCodeMethod = getStateCodeMethod;
    }

    public void setState(V state) {
        if(!stateItemList.contains(state)) {
            throw new RuntimeException("Impossible to set state: "
                    +"Tried to set state to a value out of range.");
        }
        if(getStateFromItemListMethod == null) {
            throw new RuntimeException("Impossible to set state: "
                    +"getStateFromItemList method was not set.");
        }

        U before = getStateFromItemListMethod.apply(currentStateItem);
        currentStateItem = state;

        if(setStateMethod != null) {
            setStateMethod.accept(linkedObject, getStateFromItemListMethod.apply(currentStateItem));
        }
        stateChangeListeners.forEach(
                listener -> listener.accept(before, getStateFromItemListMethod.apply(state)));
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        if(editableStateChangeListener != null) {
            editableStateChangeListener.accept(editable);
        }
    }

    //  Listeners   //  //  //

    void addStateChangeListener(BiConsumer<U, U> listener) {
        stateChangeListeners.add(listener);
        listener.accept(
                getStateFromItemListMethod.apply(currentStateItem),
                getStateFromItemListMethod.apply(currentStateItem));
    }

    void setEditableStateChangeListener(Consumer<Boolean> method) {
        editableStateChangeListener = method;
    }

    //  Active methods  //  //  //

    void nextState() {
        if (editable) {
            int currentStateIndex = stateItemList.indexOf(currentStateItem);
            setState(stateItemList.get((currentStateIndex + 1) % stateItemList.size()));
        }
        // else nothing because readonly
    }
}