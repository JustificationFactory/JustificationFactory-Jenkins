package fr.axonic.avek.graph;

import fr.axonic.avek.engine.Pattern;
import fr.axonic.avek.engine.Step;
import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.evidence.Evidence;
import fr.axonic.avek.engine.evidence.EvidenceRole;
import org.apache.log4j.Logger;
import org.jgraph.JGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nathael on 07/09/16.
 */
public class ArgumentationDiagram extends JFrame {
    private final static Logger LOGGER = Logger.getLogger(ArgumentationDiagram.class);

    private final Map<Object, MyVertex> nodes;
    private final SimpleDirectedGraph<MyVertex, DefaultEdge> graph;

    private ArgumentationDiagram(List<Step> steps) {
        nodes = new HashMap<>();
        graph = new SimpleDirectedGraph<>(MyEdge.class);

        steps.forEach(this::computeStep);

        JGraphModelAdapter adapter = new JGraphModelAdapter<>(graph);
        JGraph jGraph = new JGraph(adapter);

        JScrollPane jsp = new JScrollPane(jGraph);
        this.setContentPane(jsp);

        this.setSize(500, 500);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null); // center the screen
        this.setVisible(true);
    }

    public static void show(List<Step> steps) {
        new ArgumentationDiagram(steps);
    }

    private void computeStep(Step step) {
        LOGGER.debug(step);

        Conclusion conclusion = step.getConclusion();
        Pattern strategy = step.getPattern();
        LOGGER.debug("Step: "+conclusion.getName()+" ← "+strategy.getName());

        addNode(conclusion, conclusion.getName(), VertexType.CONCLUSION);
        addNode(strategy, strategy.getName(), VertexType.STRATEGY);
        linkNodes(strategy, conclusion);

        for(EvidenceRole evidenceRole : step.getEvidences()) {
            Evidence evidence = evidenceRole.getEvidence();
            addNode(evidence, evidence.getName(), VertexType.EVIDENCE);
            linkNodes(evidence, strategy);
        }
    }

    private void addNode(Object o, String name, VertexType type) {
        if (!nodes.containsKey(o)) {
            MyVertex vertex = new MyVertex(o, name, type);
            graph.addVertex(vertex);
            nodes.put(o, vertex);
        } else {
            nodes.get(o).accumulateType(type);
        }
    }
    private void linkNodes(Object begin, Object end) {
        if(!graph.containsEdge(nodes.get(begin), nodes.get(end))) {
            LOGGER.debug("Linking "+nodes.get(begin)+" → "+nodes.get(end));
            MyEdge e = new MyEdge("");
            graph.addEdge(nodes.get(begin), nodes.get(end), e);
        }
        else
            LOGGER.debug("Already linked "+nodes.get(begin)+" → "+nodes.get(end));
    }

    private class MyVertex {
        final Object linkedObject;
        final String name;
        VertexType type;

        private MyVertex(Object linkedObject, String name, VertexType type) {
            this.linkedObject = linkedObject;
            this.name = name;
            this.type = type;
        }

        @Override
        public String toString() {
            return "[" + type.getValue() + "] " +name;
        }

        void accumulateType(VertexType type) {
            if(type == VertexType.CONCLUSION || this.type == VertexType.CONCLUSION) {
                this.type = VertexType.CONCLUSION;
            } else {
                this.type = type;
            }
        }
    }
    private class MyEdge extends DefaultEdge {
        private String text;

        MyEdge(String s) {
            this.text = s;
        }

        @Override
        public String toString() {
            return text;
        }
    }
    private enum VertexType {
        CONCLUSION("C"), EVIDENCE("E"), STRATEGY("S");

        final String value;
        VertexType(String c) {
            this.value = c;
        }

        public String getValue() {
            return value;
        }
    }
}