package fr.axonic.avek.gui.util;

import fr.axonic.avek.gui.view.AbstractView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Nathaël N on 27/07/16.
 */
public class ViewOrchestrator {
	private AbstractView currentView;
	private List<ViewOrchestrator> following;

	public ViewOrchestrator(AbstractView view) {
		this.currentView = view;
		following = new LinkedList<>();
	}

	public void addFollowing(ViewOrchestrator follow) {
		following.add(follow);
	}
	public List<ViewOrchestrator> getFollowing() {
		return following;
	}

	@Override
	public String toString() {
		return currentView.getClass().getSimpleName();
	}

	public AbstractView getView() {
		return currentView;
	}
}
