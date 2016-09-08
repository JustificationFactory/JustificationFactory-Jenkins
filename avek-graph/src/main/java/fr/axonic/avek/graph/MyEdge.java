package fr.axonic.avek.graph;

import org.jgrapht.graph.DefaultEdge;

/**
 * Created by nathael on 08/09/16.
 */
class MyEdge extends DefaultEdge {
    private String text;

    MyEdge(String s) {
        this.text = s;
    }

    @Override
    public String toString() {
        return text;
    }
}
