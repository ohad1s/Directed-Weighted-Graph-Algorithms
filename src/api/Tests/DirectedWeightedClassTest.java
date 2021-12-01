package api.Tests;

import api.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class DirectedWeightedClassTest {

    DirectedWeightedClass graphTest = generateGraph();

    @Test
    void getNode() {
        NodeData nodeTest1 = graphTest.getNode(0);
        assertEquals(0, nodeTest1.getKey());
        assertEquals(1, nodeTest1.getLocation().x());
        assertEquals(2, nodeTest1.getLocation().y());
        assertEquals(3, nodeTest1.getLocation().z());
        assertEquals(0, nodeTest1.getWeight());
        NodeData nodeTest2 = graphTest.getNode(25);
        assertEquals(null, nodeTest2);


    }

    @Test
    void getEdge() {
        EdgeData edgeTest1 = graphTest.getEdge(5,6);
        assertEquals(5, edgeTest1.getSrc());
        assertEquals(6, edgeTest1.getDest());
        assertEquals(5, edgeTest1.getWeight());
        EdgeData edgeTest2 = graphTest.getEdge(0, 3);
        assertEquals(null, edgeTest2);

    }

    @Test
    void addNode() {
        NodeData toAdd = new NodeDataClass(10, 0,5,7);
        graphTest.addNode(toAdd);
        NodeData toCompare = graphTest.getNode(10);
        assertEquals(toAdd, toCompare);
    }

    @Test
    void connect() {
        graphTest.connect(0,3, 0);
        EdgeData edgeToCheck = graphTest.getEdge(0,3);
        assertEquals(0, edgeToCheck.getSrc());
        assertEquals(3, edgeToCheck.getDest());
        assertEquals(0, edgeToCheck.getWeight());
        HashSet<EdgeData> edgesOfCurrentSrc = graphTest.getEdgesFromSpecificVertex().get(0);
        assertTrue(edgesOfCurrentSrc.contains(edgeToCheck));

    }

    @Test
    void nodeIter() {
        Iterator <NodeData> nodeIterTest = graphTest.nodeIter();
        while (nodeIterTest.hasNext()) {
            NodeData toCheck = nodeIterTest.next();
            int key = toCheck.getKey();
            Hashtable<Integer, NodeData> graphNodes = graphTest.getVertices();
            NodeData toCompare = graphNodes.get(key);
            assertEquals(toCheck, toCompare);
        }
    }

    @Test
    void edgeIter() {
        Iterator<EdgeData> edgeIterTest = graphTest.edgeIter();
        while (edgeIterTest.hasNext()){
            EdgeData toCheck = edgeIterTest.next();
            String key = "" + toCheck.getSrc() +"," + toCheck.getDest();
            Hashtable<String, EdgeData> graphEdges = graphTest.getEdges();
            EdgeData toCompare = graphEdges.get(key);
            assertEquals(toCheck, toCompare);
        }

    }

    @Test
    void testEdgeIter() {
        int id = 0;
        Iterator <EdgeData> iterForSpecificNodeEdges = graphTest.edgeIter(0);
        EdgeData edge1 = graphTest.getEdge(0,1);
        EdgeData edge2 = graphTest.getEdge(0,3);
        while (iterForSpecificNodeEdges.hasNext()){
            EdgeData toCheck = iterForSpecificNodeEdges.next();
            int src = toCheck.getSrc();
            int dest = toCheck.getDest();
            EdgeData toCompare = graphTest.getEdge(src, dest);
            assertEquals(toCompare, toCheck);
        }
    }

    @Test
    void removeNode() {
        NodeData toCompare = graphTest.getNode(0);
        NodeData removedNode = graphTest.removeNode(0);
        assertEquals(toCompare, removedNode);

    }

    @Test
    void removeEdge() {
        EdgeData toCompare = graphTest.getEdge(1,2);
        EdgeData removedEdge = graphTest.removeEdge(1,2);
        assertEquals(toCompare, removedEdge);
    }

    @Test
    void nodeSize() {
        int size = 10;
        assertEquals(size, graphTest.nodeSize());
    }

    @Test
    void edgeSize() {
        int size = 9;
        assertEquals(size, graphTest.edgeSize());
    }

    @Test
    void getMC() {
        int currentMC = graphTest.getMC();
        graphTest.removeEdge(0,1);
        assertEquals(currentMC  + 1, graphTest.getMC());
        graphTest.removeNode(0);
        assertEquals(currentMC +2, graphTest.getMC());
        graphTest.connect(3,5, 0);
        assertEquals(currentMC + 3, graphTest.getMC());
        NodeData toAdd = new NodeDataClass(0,1,2,3);
        graphTest.addNode(toAdd);
        assertEquals(currentMC + 4, graphTest.getMC());
        graphTest.addNode(toAdd);
        assertEquals(currentMC + 4, graphTest.getMC());



    }

    public static DirectedWeightedClass generateGraph() {
        DirectedWeightedClass graph = new DirectedWeightedClass();
        for (int i = 0; i < 10; i++) {
            NodeData nodeToAdd = new NodeDataClass(i, i + 1, i + 2, i + 3);
            graph.addNode(nodeToAdd);
        }
        for (int i = 0; i < 9; i++) {
            graph.connect(i, i + 1, i);
        }
        return graph;

    }

}
