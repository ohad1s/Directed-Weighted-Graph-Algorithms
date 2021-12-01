package api.Tests;

import api.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class DirectedWeightedGraphAlgorithmsClassTest {
    DirectedWeightedGraphAlgorithms algorithmsTest = new DirectedWeightedGraphAlgorithmsClass();
    DirectedWeighted algoGraph = algorithmsTest.getGraph();
    @Test
    void init() {
        DirectedWeighted graphToInit = DirectedWeightedClassTest.generateGraph();
        algorithmsTest.init(graphToInit);
        DirectedWeighted toCheck = algorithmsTest.getGraph();
        Iterator<NodeData> verticesIterator = toCheck.nodeIter();
        while (verticesIterator.hasNext()){
            System.out.println(verticesIterator.next().getInfo());
        }
    }


    @Test
    void copy() {
        DirectedWeighted graphToInit = DirectedWeightedClassTest.generateGraph();
        algorithmsTest.init(graphToInit);
        DirectedWeighted duplicatedGraph = algorithmsTest.copy();
        Iterator<NodeData> verticesIterator = algorithmsTest.getGraph().nodeIter();
        while (verticesIterator.hasNext()){
            NodeData currentVertex = verticesIterator.next();
            int key = currentVertex.getKey();
            NodeData duplicatedGraphVortex = duplicatedGraph.getNode(key);
            assertEquals(currentVertex.getLocation().x(), duplicatedGraphVortex.getLocation().x());
            assertEquals(currentVertex.getLocation().y(), duplicatedGraphVortex.getLocation().y());
            assertEquals(currentVertex.getLocation().z(), duplicatedGraphVortex.getLocation().z());
            assertNotEquals(currentVertex, duplicatedGraphVortex);
        }
    }

    @Test
    void isConnected() {

        NodeData toAdd1 = new NodeDataClass(0, 1,2,3);
        NodeData toAdd2 = new NodeDataClass(1, 2,3,4);
        NodeData toAdd3 = new NodeDataClass(2, 3,4,5);
        NodeData toAdd4 = new NodeDataClass(3, 4,5,6);
        algoGraph.addNode(toAdd1);
        algoGraph.addNode(toAdd2);
        algoGraph.addNode(toAdd3);
        algoGraph.addNode(toAdd4);
        algoGraph.connect(0,1,0);
        algoGraph.connect(0,3,0);
        algoGraph.connect(1,2,0);
        algoGraph.connect(2,0,0);
        algoGraph.connect(3,2,0);
        assertTrue(algorithmsTest.isConnected());
        algoGraph.removeEdge(0,1);
        assertFalse(algorithmsTest.isConnected());
        algoGraph.connect(2,3,0);
        algoGraph.connect(2,1,0);
        algoGraph.connect(0,2,0);
        assertTrue(algorithmsTest.isConnected());

    }

    @Test
    void shortestPathDist() {
    }

    @Test
    void shortestPath() {
    }

    @Test
    void center() {
    }

    @Test
    void tsp() {
    }

    @Test
    void save() {
        NodeData toAdd1 = new NodeDataClass(0, 1,2,3);
        NodeData toAdd2 = new NodeDataClass(1, 2,3,4);
        NodeData toAdd3 = new NodeDataClass(2, 3,4,5);
        NodeData toAdd4 = new NodeDataClass(3, 4,5,6);
        algoGraph.addNode(toAdd1);
        algoGraph.addNode(toAdd2);
        algoGraph.addNode(toAdd3);
        algoGraph.addNode(toAdd4);
        algoGraph.connect(0,1,0);
        algoGraph.connect(0,3,0);
        algoGraph.connect(1,2,0);
        algoGraph.connect(2,0,0);
        algoGraph.connect(3,2,0);
        GsonBuilder gson = new GsonBuilder();
        gson.setPrettyPrinting();
        gson.create();

    }

    @Test
    void load() {
    }

    @Test
    void dfs() {
    }
}