package api;


import api.json.EdgeForJson;
import api.json.GraphJson;
import api.json.NodeForJson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DirectedWeightedGraphAlgorithmsClass implements DirectedWeightedGraphAlgorithms {
    private DirectedWeighted graph;
    private Hashtable<Integer, Double> mapDist;
    private Hashtable<Integer, Integer> mapPrev;
    public static final double INFINITY = Double.POSITIVE_INFINITY;

    public DirectedWeightedGraphAlgorithmsClass() {
        this.graph = new DirectedWeightedClass();
        this.mapDist = new Hashtable<>();
        this.mapPrev = new Hashtable<>();
    }

    @Override
    public void init(DirectedWeighted g) {
        this.graph = g;
    }

    @Override
    public DirectedWeighted getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeighted copy() {
        DirectedWeightedClass duplicatedGraph = new DirectedWeightedClass();
        Iterator<NodeData> verticesIterator = this.graph.nodeIter();
        while (verticesIterator.hasNext()) {
            NodeData currentVertex = verticesIterator.next();
            double x = currentVertex.getLocation().x();
            double y = currentVertex.getLocation().y();
            double z = currentVertex.getLocation().z();
            NodeData toAdd = new NodeDataClass(currentVertex.getKey(), x, y, z);
            duplicatedGraph.addNode(toAdd);
        }
        Iterator<EdgeData> edgesIterator = this.graph.edgeIter();
        while (edgesIterator.hasNext()) {
            EdgeData currentEdge = edgesIterator.next();
            int src = currentEdge.getSrc();
            int dest = currentEdge.getDest();
            double weight = currentEdge.getWeight();
            duplicatedGraph.connect(src, dest, weight);
        }

        return duplicatedGraph;
    }

    @Override
    public boolean isConnected() {
        int numOfVertices = graph.nodeSize();
        boolean[] visited = new boolean[numOfVertices];
        Arrays.fill(visited, false);
        Iterator<NodeData> verticesIterator = graph.nodeIter();
        while (verticesIterator.hasNext()) {
            NodeData currentVertex = verticesIterator.next();
            dfs(currentVertex.getKey(), visited);

            for (int i = 0; i < visited.length; i++) {
                if (!visited[i]) {
                    return false;
                }
            }
            Arrays.fill(visited, false);
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if (graph.getNode(src) == null || graph.getNode(dest) == null) {
            return -1.0;
        }
        if (graph.getNode(src) != null && src == dest) {
            return 0;
        }
        calculateShortestPath(src);
        double dist = mapDist.get(dest);
        return (dist == INFINITY) ? -1.0 : dist;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        if (graph.getNode(src) == null || graph.getNode(dest) == null) {
            return null;
        }
        List<NodeData> path = new ArrayList<>();
        if (graph.getNode(src) != null && src == dest) {
            NodeData toAdd = graph.getNode(src);
            path.add(toAdd);
            return path;
        }
        calculateShortestPath(src);
        if (!mapPrev.containsKey(dest)) {
            return null;
        }
        NodeData destVertex = graph.getNode(dest);
        path.add(destVertex);
        int prevVertex = mapPrev.get(dest);
        while (prevVertex != -1) {
            NodeData toAdd = graph.getNode(prevVertex);
            path.add(toAdd);
            prevVertex = mapPrev.get(prevVertex);
        }
        return path;
    }

    @Override
    public NodeData center() {
        if (!isConnected()) {
            return null;
        }
        Iterator<NodeData> graphVerticesIter = graph.nodeIter();
        NodeData currentVertex = graphVerticesIter.next();
        int keyofCenter = currentVertex.getKey();
        double minMaxDist = INFINITY;
        while (graphVerticesIter.hasNext()) {
            calculateShortestPath(currentVertex.getKey());
            double maxValue = findMaxValue();
            if (maxValue < minMaxDist) {
                minMaxDist = maxValue;
                keyofCenter = currentVertex.getKey();
            }
            currentVertex = graphVerticesIter.next();
        }
        NodeData centerVertex = this.graph.getNode(keyofCenter);
        return centerVertex;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        GraphJson serializedGraph = serializeGraph();
        try {
            FileWriter myFile = new FileWriter(file);
            GsonBuilder json = new GsonBuilder();
            String toWrite = json.setPrettyPrinting().create().toJson(serializedGraph);
            myFile.write(toWrite);
            myFile.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean load(String file) {
        try {
            FileReader fileReader = new FileReader(file);
            String jsonFile = "";
            Scanner myScanner = new Scanner(fileReader);
            while (myScanner.hasNextLine()) {
                String lineToAdd = myScanner.nextLine();
                jsonFile += lineToAdd;
            }
            myScanner.close();
            DirectedWeighted deserializedGraph = deserializeGraph(jsonFile);
            this.graph = deserializedGraph;
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean[] dfs(int key, boolean[] visited) {
        Stack<Integer> stack = new Stack<>();
        stack.push(key);
        visited[key] = true;
        while (!stack.isEmpty()) {
            int currentVertex = stack.pop();
            Iterator<EdgeData> currentVertexEdgesIterator = graph.edgeIter(currentVertex);
            while (currentVertexEdgesIterator.hasNext()) {
                EdgeData currentEdge = currentVertexEdgesIterator.next();
                int edgeDest = currentEdge.getDest();
                if (!visited[edgeDest]) {
                    stack.push(edgeDest);
                    visited[edgeDest] = true;
                }
            }
        }
        return visited;
    }

    public void calculateShortestPath(int src) {
        mapPrev.clear();
        HashSet<NodeData> visited = new HashSet<>();
        HashSet<NodeData> unvisited = new HashSet<>();
        initiateUnvisitedAndMapDist(unvisited);
        mapDist.replace(src, 0.0);
        mapPrev.put(src, -1);
        while (!unvisited.isEmpty()) {
            NodeData minDistNode = findMinDist(unvisited);
            int keyOfMinDistNode = minDistNode.getKey();
            double distOfCurrentKey = mapDist.get(keyOfMinDistNode);
            Iterator<EdgeData> edgesLeavingFromCurrentKeyVertex = graph.edgeIter(keyOfMinDistNode);
            while (edgesLeavingFromCurrentKeyVertex.hasNext()) {
                EdgeData currentEdge = edgesLeavingFromCurrentKeyVertex.next();
                int destVertexOfCurrentEdge = currentEdge.getDest();
                double distOfCurrentEdge = currentEdge.getWeight();
                double totalDist = distOfCurrentEdge + distOfCurrentKey;
                double currentDestDistFromSrc = mapDist.get(destVertexOfCurrentEdge);
                if (totalDist < currentDestDistFromSrc) {
                    mapDist.put(destVertexOfCurrentEdge, totalDist);
                    mapPrev.put(destVertexOfCurrentEdge, keyOfMinDistNode);
                }
            }
            unvisited.remove(minDistNode);
            visited.add(minDistNode);

        }
        return;
    }


    private NodeData findMinDist(HashSet<NodeData> unvisited) {
        if (unvisited.size() == 0) {
            return null;
        }
        Iterator<NodeData> unvisitedVerticesIter = unvisited.iterator();
        double minDist = INFINITY;
        NodeData currentVertex = unvisitedVerticesIter.next();
        int keyOfMinVertex = currentVertex.getKey();
        while (unvisitedVerticesIter.hasNext()) {
            int currentKey = currentVertex.getKey();
            double currentDist = mapDist.get(currentKey);
            if (currentDist < minDist) {
                minDist = currentKey;
                keyOfMinVertex = currentKey;
            }
            currentVertex = unvisitedVerticesIter.next();
        }
        return graph.getNode(keyOfMinVertex);
    }

    private double findMaxValue() {
        double maxDist = 0;
        Iterator<Double> distIter = mapDist.values().iterator();
        while (distIter.hasNext()) {
            double currentDist = distIter.next();
            if (currentDist > maxDist) {
                maxDist = currentDist;
            }
        }
        return maxDist;
    }

    private GraphJson serializeGraph() {
        GraphJson serializedGraph = new GraphJson();
        Iterator<NodeData> nodeIter = this.graph.nodeIter();
        Iterator<EdgeData> edgeIter = this.graph.edgeIter();
        while (nodeIter.hasNext()) {
            NodeData currentVertex = nodeIter.next();
            serializedGraph.addNode(currentVertex);
        }
        while (edgeIter.hasNext()) {
            EdgeData currentEdge = edgeIter.next();
            serializedGraph.addEdge(currentEdge);
        }
        return serializedGraph;
    }

    private DirectedWeighted deserializeGraph(String jsonFile) {
        DirectedWeighted loadedGraph = new DirectedWeightedClass();
        GraphJson fromJson = new Gson().fromJson(jsonFile, GraphJson.class);
        for (NodeForJson node : fromJson.Nodes) {
            String[] location = node.pos.split(",");
            double x = Double.parseDouble(location[0]);
            double y = Double.parseDouble(location[1]);
            double z = Double.parseDouble(location[2]);
            int id = node.id;
            NodeData toAdd = new NodeDataClass(id, x, y, z);
            loadedGraph.addNode(toAdd);
        }
        for (EdgeForJson edge : fromJson.Edges) {
            int src = edge.src;
            int dest = edge.dest;
            double weight = edge.w;
            loadedGraph.connect(src, dest, weight);
        }
        return loadedGraph;
    }

    private HashSet<NodeData> initiateUnvisitedAndMapDist(HashSet<NodeData> unvisited){
        Iterator<NodeData> nodeIter = graph.nodeIter();
        while (nodeIter.hasNext()) {
            NodeData currentVertex = nodeIter.next();
            int vertexId = currentVertex.getKey();
            unvisited.add(currentVertex);
            mapDist.put(vertexId, INFINITY);
        }
        return unvisited;
    }

}




