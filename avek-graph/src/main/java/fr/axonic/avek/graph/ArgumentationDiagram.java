package fr.axonic.avek.graph;

import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.engine.pattern.Step;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.strategy.Strategy;
import org.apache.log4j.Logger;
import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.SimpleDirectedGraph;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by nathael on 07/09/16.
 */
public class ArgumentationDiagram extends JFrame {
    private final static Logger LOGGER = Logger.getLogger(ArgumentationDiagram.class);

    private final Map<Object, MyVertex> nodes;
    private final SimpleDirectedGraph<MyVertex, MyEdge> graph;
    private final JGraphModelAdapter<MyVertex, MyEdge> adapter;

    private ArgumentationDiagram(List<Step> steps) {
        nodes = new HashMap<>();
        graph = new SimpleDirectedGraph<>(MyEdge.class);

        steps.forEach(this::computeStep);
        adapter = new JGraphModelAdapter<>(graph);

        JGraph jGraph = new JGraph(adapter);

        JScrollPane jsp = new JScrollPane(jGraph);
        this.setContentPane(jsp);

        this.setSize(500, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // center the screen
        this.setVisible(true);

        for(MyVertex vertex : graph.vertexSet()) {
            try {
                position(vertex);
            } catch(Exception e) {
                LOGGER.error("Impossible to position "+vertex, e);
            }
        }

        // Instantaneous construction
        graph.vertexSet().forEach(this::position);
    }

    public static void show(List<Step> steps) {
        new ArgumentationDiagram(steps);
    }

    private void computeStep(Step step) {
        LOGGER.debug(step);

        Conclusion conclusion = step.getConclusion();
        Strategy strategy = step.getStrategy();
        LOGGER.debug("Step: "+conclusion.getName()+" ← "+strategy.getName());

        addNode(conclusion, conclusion.getName(), VertexType.CONCLUSION);
        addNode(strategy, strategy.getName(), VertexType.STRATEGY);
        linkNodes(strategy, conclusion);

        for(SupportRole supportRole : step.getEvidences()) {
            Support evidence = supportRole.getSupport();
            addNode(evidence, evidence.getName(), VertexType.EVIDENCE);
            linkNodes(evidence, strategy);
        }

        // Progressive construction
        /*new Thread(() -> {
            try {
                Thread.sleep(1000);
                computePlacements(conclusion);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();*/

        // Instantaneous construction
        computePlacements(conclusion);
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
    private void computePlacements(Object motherNode) {
        MyVertex vertex = nodes.get(motherNode);
        computeY(vertex);
        computeX(vertex);
    }
    private List<MyVertex> getChildren(MyVertex motherVertex) {
        return graph.edgeSet().stream()
                .filter(e -> graph.getEdgeTarget(e).equals(motherVertex))
                .map(graph::getEdgeSource)
                .collect(Collectors.toList());
    }
    private void computeY(MyVertex vertex) {
        List<MyVertex> children = getChildren(vertex);

        // Progressive construction
        // position(vertex);

        children.stream()
                .filter(child -> child.y < vertex.y + 1)
                .forEach(child -> {
                    child.y = vertex.y + 1;

                    computeY(child);
                });
    }
    private void computeX(MyVertex vertex) {
        List<MyVertex> children= getChildren(vertex);

        int childSize = 0;
        for(MyVertex child : children) {
            computeX(child);
            if((child.x + child.width/2) < childSize-1) {
                decalX(child, childSize-child.x);
            }
            childSize += child.width + 1;
        }
        vertex.width = childSize>1 ? childSize-1 : 1;
        vertex.x = vertex.width/2;

        // Progressive construction
        //position(vertex);
    }
    private void decalX(MyVertex vertex, int ranks) {
        vertex.x += ranks;

        // Progressive construction
        //position(vertex);

        getChildren(vertex).forEach(child -> decalX(child, ranks));
    }

    private final static int WIDTH_CONSTANT = 150; //px
    private final static int HEIGHT_CONSTANT = 30; //px
    private void position(MyVertex vertex) {
        DefaultGraphCell cell = adapter.getVertexCell( vertex );
        Map attr = cell.getAttributes();

        GraphConstants.setBounds( attr, new AttributeMap.SerializableRectangle2D(
                vertex.x * WIDTH_CONSTANT, // vertex distance from left
                vertex.y * HEIGHT_CONSTANT * 2, // vertex distance from top
                WIDTH_CONSTANT, // Vertex width
                HEIGHT_CONSTANT)); // Vertex height

        Map<DefaultGraphCell,Map> cellAttr = new HashMap<>();
        cellAttr.put( cell, attr );
        adapter.edit( cellAttr, null, null, null );

        // Progressive construction
        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

}