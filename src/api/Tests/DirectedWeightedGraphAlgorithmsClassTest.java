package api.Tests;

import api.*;
import api.DirectedWeightedClass;
import api.DirectedWeightedGraphAlgorithmsClass;
import api.NodeDataClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DirectedWeightedGraphAlgorithmsClassTest {
    DirectedWeightedGraphAlgorithms graphAlgoTest = new DirectedWeightedGraphAlgorithmsClass();
    static DirectedWeighted g1 = new DirectedWeightedClass();
    static DirectedWeighted g2 = new DirectedWeightedClass();
    static DirectedWeighted g3 = new DirectedWeightedClass();
    static DirectedWeighted g4 = new DirectedWeightedClass();
    static DirectedWeighted g5 = new DirectedWeightedClass();
    public static final double EPS = 0.1;


    @BeforeAll
    static void Setup() {
        createGraph(10, g1);
        g1.connect(0, 1, 0.5);
        g1.connect(0, 2, 2.5);
        g1.connect(2, 3, 1.98);
        g1.connect(1, 4, 8.3);
        g1.connect(4, 6, 4.1);
        g1.connect(8, 6, 2.4);
        g1.connect(5, 6, 3.1);
        g1.connect(6, 5, 3.1);
        g1.connect(7, 8, 1.8);
        g1.connect(3, 8, 9.6);
        g1.connect(1, 5, 5.6);

        createGraph(10, g2);
        g2.connect(0, 5, 5);
        g2.connect(0, 4, 20);
        g2.connect(0, 3, 20);
        g2.connect(0, 1, 10);
        g2.connect(0, 7, 15);
        g2.connect(1, 3, 10);
        g2.connect(1, 2, 5);
        g2.connect(2, 3, 5);
        g2.connect(2, 1, 15);
        g2.connect(3, 4, 10);
        g2.connect(4, 5, 5);
        g2.connect(6, 5, 10);
        g2.connect(7, 6, 5);
        g2.connect(7, 0, 5);
        g2.connect(7, 1, 20);
        g2.connect(8, 7, 20);
        g2.connect(8, 1, 15);
        g2.connect(8, 9, 10);
        g2.connect(9, 2, 15);
        g2.connect(9, 1, 5);

        createGraph(4, g3);
        g3.connect(0, 1, 5);
        g3.connect(2, 1, 5);
        g3.connect(0, 2, 5);
        g3.connect(3, 0, 5);
        g3.connect(1, 3, 5);
        g3.connect(3, 1, 5);

        createGraph(4, g4);
        g4.connect(0, 1, 5);
        g4.connect(2, 1, 5);
        g4.connect(2, 0, 5);
        g4.connect(0, 3, 5);
        g4.connect(1, 3, 5);
        g4.connect(3, 1, 5);


        createGraph(3, g5);
        g5.connect(0, 1, 5);
        g5.connect(1, 2, 5);
        g5.connect(2, 0, 5);

    }

    @Test
    void init() {
        DirectedWeighted graphToInit = g1;
        graphAlgoTest.init(graphToInit);
        DirectedWeighted algoGraphIter = graphAlgoTest.getGraph();
        Iterator<NodeData> algoGraphVerticesIter = algoGraphIter.nodeIter();
        while (algoGraphVerticesIter.hasNext()) {
            NodeData algoGraphVertex = algoGraphVerticesIter.next();
            NodeData initGraphVertex = g1.getNode(algoGraphVertex.getKey());
            assertEquals(initGraphVertex, algoGraphVertex);
        }
        Iterator<NodeData> initGraphVerticesIter = g1.nodeIter();
        while (initGraphVerticesIter.hasNext()) {
            NodeData initGraphVertex = initGraphVerticesIter.next();
            NodeData algoGraphVertex = graphAlgoTest.getGraph().getNode(initGraphVertex.getKey());
            assertEquals(initGraphVertex, algoGraphVertex);

        }
    }


    @Test
    void copy() {
        DirectedWeighted graphToInit = g5;
        graphAlgoTest.init(graphToInit);
        DirectedWeighted duplicatedGraph = graphAlgoTest.copy();
        Iterator<NodeData> verticesIterator = graphAlgoTest.getGraph().nodeIter();
        while (verticesIterator.hasNext()) {
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
        graphAlgoTest.init(g1);
        System.out.println("g1 = " + graphAlgoTest.isConnected());
        assertFalse(graphAlgoTest.isConnected());

        graphAlgoTest.init(g2);
        System.out.println("g2 = " + graphAlgoTest.isConnected());
        assertFalse(graphAlgoTest.isConnected());

        graphAlgoTest.init(g3);
        System.out.println("g3 = " + graphAlgoTest.isConnected());
        assertTrue(graphAlgoTest.isConnected());

        graphAlgoTest.init(g4);
        System.out.println("g4 = " + graphAlgoTest.isConnected());
        assertFalse(graphAlgoTest.isConnected());

        graphAlgoTest.init(g5);
        System.out.println("g5 = " + graphAlgoTest.isConnected());
        assertTrue(graphAlgoTest.isConnected());


    }

    @Test
    void shortestPathDistG1() {
        DirectedWeighted graphToInit = g1;
        graphAlgoTest.init(graphToInit);
        assertEquals(5.6, graphAlgoTest.shortestPathDist(1, 5), EPS);
        assertEquals(0.5, graphAlgoTest.shortestPathDist(0, 1));
        assertEquals(4.48, graphAlgoTest.shortestPathDist(0, 3));
        assertEquals(7.300000000000001, graphAlgoTest.shortestPathDist(7, 5));
        assertEquals(-1.0, graphAlgoTest.shortestPathDist(8, 3));
        assertEquals(0.0, graphAlgoTest.shortestPathDist(2, 2));
        assertEquals(-1.0, graphAlgoTest.shortestPathDist(20, 20));
        assertEquals(-1.0, graphAlgoTest.shortestPathDist(0, 20));


    }

    @Test
    void shortestPathDistG2() {
        DirectedWeighted graphToInit = g2;
        graphAlgoTest.init(graphToInit);
        assertEquals(25.0, graphAlgoTest.shortestPathDist(1, 5));
        assertEquals(10.0, graphAlgoTest.shortestPathDist(0, 1));
        assertEquals(20.0, graphAlgoTest.shortestPathDist(0, 3));
        assertEquals(10.0, graphAlgoTest.shortestPathDist(7, 5));
        assertEquals(25.0, graphAlgoTest.shortestPathDist(8, 3));
        assertEquals(25.0, graphAlgoTest.shortestPathDist(8, 0));
        assertEquals(-1.0, graphAlgoTest.shortestPathDist(9, 0));
        assertEquals(0.0, graphAlgoTest.shortestPathDist(2, 2));
        assertEquals(-1.0, graphAlgoTest.shortestPathDist(20, 20));
        assertEquals(-1.0, graphAlgoTest.shortestPathDist(1, 20));
        assertEquals(5.0, graphAlgoTest.shortestPathDist(2, 3));
        assertEquals(30.0, graphAlgoTest.shortestPathDist(9, 5));

    }

    @Test
    void center() {
        graphAlgoTest.load("./data/G1.json");
        assertEquals(8, graphAlgoTest.center().getKey());
        graphAlgoTest.load("./data/G2.json");
        assertEquals(0, graphAlgoTest.center().getKey());
        graphAlgoTest.load("./data/G3.json");
        assertEquals(40, graphAlgoTest.center().getKey());


    }

    @Test
    void tsp() {
        DirectedWeightedGraphAlgorithms toTest = new DirectedWeightedGraphAlgorithmsClass();
        toTest.load("./data/G1.json");
        List<NodeData> path = new ArrayList<>();
        Iterator<NodeData> verticesIter = toTest.getGraph().nodeIter();
        while (verticesIter.hasNext()){
            NodeData currentVertex = verticesIter.next();
            path.add(currentVertex);
        }
        List<NodeData> shortestPathAroundTheGraph = toTest.tsp(path);
        System.out.println(shortestPathAroundTheGraph.toString());
        System.out.println(shortestPathAroundTheGraph.size());
        for (NodeData vertex : shortestPathAroundTheGraph){
            System.out.println(vertex.getInfo());
        }
    }
    @Test
    void testCase3(){
        graphAlgoTest.load("./data/G3.json");
        List<NodeData> listToTest = graphAlgoTest.shortestPath(3, 47);
        for(NodeData node : listToTest){
            System.out.println(node.getInfo());
        }
    }

    @Test
    void load_save() {
        DirectedWeightedGraphAlgorithms test2 = new DirectedWeightedGraphAlgorithmsClass();
        String fileNameLoad = "./data/G1.json";
        String fileNameSave = "./out/G3.json";
        assertTrue(graphAlgoTest.load(fileNameLoad));
        assertTrue(graphAlgoTest.save(fileNameSave));
        test2.load(fileNameSave);
        Iterator<NodeData> nodeIter = graphAlgoTest.getGraph().nodeIter();
        Iterator<EdgeData> edgeIter = graphAlgoTest.getGraph().edgeIter();
        while (nodeIter.hasNext()) {
            NodeData currentNode = nodeIter.next();
            NodeData toCompareNode = test2.getGraph().getNode(currentNode.getKey());
            assertEquals(currentNode.getLocation().x(), toCompareNode.getLocation().x());
            assertEquals(currentNode.getLocation().y(), toCompareNode.getLocation().y());
            assertEquals(currentNode.getLocation().z(), toCompareNode.getLocation().z());
            assertNotEquals(currentNode, toCompareNode);
        }
        while (edgeIter.hasNext()){
            EdgeData currentEdge = edgeIter.next();
            EdgeData toCompare = test2.getGraph().getEdge(currentEdge.getSrc(), currentEdge.getDest());
            assertNotEquals(toCompare, null);
            assertNotEquals(currentEdge, toCompare);
        }

    }



    static void createGraph(int size, DirectedWeighted g) {
        for (int i = 0; i < size; i++) {
            NodeData toAdd = new NodeDataClass(i, i, i, i);
            g.addNode(toAdd);
        }
    }

    static DirectedWeightedGraphAlgorithms loadGraph(DirectedWeightedGraphAlgorithms graph, String file){
        graph.load(file);
        return graph;
    }
}