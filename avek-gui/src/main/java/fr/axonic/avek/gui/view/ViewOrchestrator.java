package fr.axonic.avek.gui.view;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Nathaël N on 27/07/16.
 */
public class ViewOrchestrator {
    private AbstractView currentView;
    private List<ViewOrchestrator> following;
    private final String name;

    public ViewOrchestrator(/*Nullable*/ AbstractView view, String name) {
        this.currentView = view;
        following = new LinkedList<>();
        this.name = name;
    }

    public void addFollowing(ViewOrchestrator follow) {
        following.add(follow);
    }

    List<ViewOrchestrator> getFollowing() {
        return following;
    }

    @Override
    public String toString() {
        return getName();
    }

    public AbstractView getView() {
        return currentView;
    }

    String getName() {
        return name;
    }
}
