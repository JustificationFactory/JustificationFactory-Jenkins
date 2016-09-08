package fr.axonic.avek.graph;

import com.mxgraph.view.mxGraph;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import fr.axonic.avek.engine.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import fr.axonic.avek.engine.evidence.EvidenceRole;
import org.apache.log4j.Logger;
import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.Edge;
import org.jgrapht.DirectedGraph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.ext.JGraphModelAdapter;

/**
 * Created by nathael on 07/09/16.
 */
public class ArgumentationDiagram extends JFrame {

    private final SimpleGraph<String, MyEdge> graph;

    private ArgumentationDiagram(Step step) {
        graph = new SimpleGraph<>(MyEdge.class);
        set(step);

        JGraphModelAdapter adapter = new JGraphModelAdapter<>(graph);
        JGraph jGraph = new JGraph(adapter);

        JScrollPane jsp = new JScrollPane(jGraph);
        this.setContentPane(jsp);

        this.setSize(500, 500);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null); // center the screen
        this.setVisible(true);
    }

    public static void show(Step step) {
        new Thread(() -> new ArgumentationDiagram(step)).start();
    }

    private void set(Step step) {
        String conclusion =   step.getConclusion().getName();
        String strategy = step.getPattern().getName();

        graph.addVertex(conclusion);
        graph.addVertex(strategy);
        MyEdge e = new MyEdge("");
        graph.addEdge(strategy, conclusion, e);

        for(EvidenceRole evidence : step.getEvidences()) {
            String name = evidence.getEvidence().getName();
            graph.addVertex(name);
            e = new MyEdge("");
            graph.addEdge(name, strategy, e);
        }
    }

    private class MyEdge extends DefaultEdge {
        private String text;

        MyEdge(String s) {
            this.text = "";
        }

        @Override
        public String toString() {
            return text;
        }
    }
}