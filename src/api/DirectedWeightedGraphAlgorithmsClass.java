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
    private DirectedWeighted invertedGraph;
    private Hashtable<Integer, Double> mapDist;
    private Hashtable<Integer, Integer> mapPrev;
    private static final int VISITED = 1;
    private static final int NOT_VISITED = 0;
    public static final double INFINITY = Double.POSITIVE_INFINITY;

    /**
     * this method is the constructor of DirectedWeightedGraphAlgorithm
     */
    public DirectedWeightedGraphAlgorithmsClass() {
        this.graph = new DirectedWeightedClass();
        this.invertedGraph = new DirectedWeightedClass();
        this.mapDist = new Hashtable<>();
        this.mapPrev = new Hashtable<>();
    }

    /**
     * this method initiate the graph on which the algorithms are executed to be the given graph.
     *
     * @param g
     */
    @Override
    public void init(DirectedWeighted g) {
        this.graph = g;
        this.invertedGraph = invertGraph();
    }

    /**
     * this method returns the graph on which the algorithms are executed.
     *
     * @return
     */
    @Override
    public DirectedWeighted getGraph() {
        return this.graph;
    }

    /**
     * this method creates a deep copy of the graph.
     *
     * @return
     */
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

    /**
     * this method returns a boolean value whether the graph is connected ot not.
     *
     * @return
     */
    @Override
    public boolean isConnected() {

        Iterator<NodeData> graphNodeIter = graph.nodeIter();
        NodeData graphFirstNode = graphNodeIter.next();

        return isConnected(graph, graphFirstNode) && isConnected(invertedGraph, graphFirstNode);
    }

    private boolean isConnected(DirectedWeighted g, NodeData nodeFirst) {
        setAllTags(g, NOT_VISITED);
        BFS(nodeFirst, g);
        Iterator<NodeData> nodesIter = g.nodeIter();
        while (nodesIter.hasNext()) {
            NodeData currentNode = nodesIter.next();
            if (currentNode.getTag() == NOT_VISITED) {
                return false;
            }
        }
        return true;
    }

    private void BFS(NodeData node, DirectedWeighted g) {
        NodeData firstNode = node;
        Queue<NodeData> queue = new LinkedList<>();
        firstNode.setTag(VISITED);
        queue.add(firstNode);
        while (!queue.isEmpty()) {
            firstNode = queue.poll();
            Iterator<EdgeData> currentNodeEdgesIter = g.edgeIter(firstNode.getKey());
            while (currentNodeEdgesIter.hasNext()) {
                EdgeData currentEdge = currentNodeEdgesIter.next();
                int destNodeId = currentEdge.getDest();
                NodeData destNode = g.getNode(destNodeId);
                if (destNode.getTag() == NOT_VISITED) {
                    destNode.setTag(VISITED);
                    queue.add(destNode);
                }
            }
        }
    }

    private void setAllTags(DirectedWeighted graph, int value) {
        Iterator<NodeData> nodesIter = graph.nodeIter();
        while (nodesIter.hasNext()) {
            NodeData currentNode = nodesIter.next();
            currentNode.setTag(value);
        }
    }

    /**
     * this method calculates the shortest path between the two given vertices, using Dijkstra's algorithm.
     * in case at least one of the nodes does not exist in the graph, the method will return -1.
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
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
        return (dist == INFINITY) ? -1 : dist;
    }

    /**
     * this method returns a list of the shortest path between the two given vertices, using Dijkstra's algorithm.
     * in case at least one of the nodes does not exist in the graph, the method will return null.
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
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
        Collections.reverse(path);
        return path;
    }

    /**
     * this method return the vertex for which the distance from the farthest vertex from it is minimal.
     *
     * @return
     */
    @Override
    public NodeData center() {
        Iterator<NodeData> graphVerticesIter = graph.nodeIter();
        int keyofCenter = -1;
        double minMaxDist = INFINITY;
        while (graphVerticesIter.hasNext()) {
            NodeData currentVertex = graphVerticesIter.next();
            calculateShortestPath(currentVertex.getKey());
            double maxValue = findMaxValue();
            if (maxValue < minMaxDist) {
                minMaxDist = maxValue;
                keyofCenter = currentVertex.getKey();
            }

        }
        if (keyofCenter == -1) {
            NodeData first = getGraph().nodeIter().next();
            return first;
        }
        NodeData centerVertex = this.graph.getNode(keyofCenter);
        return centerVertex;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        if (cities.size() == 1) {
            return cities;
        }
        List<NodeData> toReturn = new ArrayList<>();
        HashSet<Integer> unvisited = new HashSet<>();
        initiateSetForTSP(cities, unvisited);
        int currentVertex = cities.get(0).getKey();
        while (unvisited.size() > 0) {
            calculateShortestPath(currentVertex);
            int nextVertex = findIdOfMinDistForTSP(unvisited);
            List<NodeData> pathFromCurrentToNext = shortestPath(currentVertex, nextVertex);
            if (pathFromCurrentToNext == null) {
                unvisited.remove(currentVertex);
                currentVertex = nextVertex;
                continue;
            }
            for (NodeData vertex : pathFromCurrentToNext) {
                int pathVertexId = vertex.getKey();
                if (unvisited.contains(pathVertexId)) {
                    unvisited.remove(pathVertexId);
                }
            }
            toReturn.addAll(pathFromCurrentToNext);
            unvisited.remove(currentVertex);
            currentVertex = nextVertex;
        }
        return toReturn;
    }

    /**
     * this method saves the graph into the given json file.
     *
     * @param file - the file name (may include a relative path).
     * @return
     */
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


    /**
     * this method initates the graph from a given json file.
     *
     * @param file - file name of JSON file
     * @return
     */
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
            init(deserializedGraph);
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * this method calculates the shortest path from the given vertex to each of the graph's vertices.
     *
     * @param src
     */
    public void calculateShortestPath(int src) {
        NodeData source = graph.getNode(src);
        mapDist.clear();
        mapPrev.clear();
        initiateMapDist();
        mapPrev.put(src, -1);
        Queue<NodeData> queue = new LinkedList<>();
        mapDist.put(src, 0.0);
        queue.add(source);
        while (!queue.isEmpty()) {
            NodeData currentNode = queue.poll();
            Iterator<EdgeData> currentNodeNeighbors = graph.edgeIter(currentNode.getKey());
            while (currentNodeNeighbors.hasNext()) {
                EdgeData currentEdge = currentNodeNeighbors.next();
                NodeData destNode = graph.getNode(currentEdge.getDest());
                double weight = currentEdge.getWeight();
                double totalWeight = mapDist.get(currentNode.getKey()) + weight;
                if (totalWeight < mapDist.get(destNode.getKey())) {
                    mapDist.put(destNode.getKey(), totalWeight);
                    mapPrev.put(destNode.getKey(), currentNode.getKey());
                    queue.add(destNode);
                }

            }
        }
    }

    /**
     * this method returns the max value of mapDist.
     *
     * @return
     */
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

    /**
     * this method returns the index of the vertex with the min dist from src for TSP.
     *
     * @param unvisited
     * @return
     */
    public int findIdOfMinDistForTSP(HashSet<Integer> unvisited) {
        Iterator<Integer> unvisitedIter = unvisited.iterator();
        int idOfMinValue = -1;
        double minDist = INFINITY;
        while (unvisitedIter.hasNext()) {
            int currentVertexId = unvisitedIter.next();
            double distToCurrentVertex = mapDist.get(currentVertexId);
            if (distToCurrentVertex < minDist) {
                minDist = distToCurrentVertex;
                idOfMinValue = currentVertexId;
            }
        }
        if (idOfMinValue == -1) {
            Iterator<Integer> newIter = unvisited.iterator();
            int idToReturn = newIter.next();
            return idToReturn;
        }
        return idOfMinValue;
    }

    /**
     * this method serialize the graph into a json file.
     *
     * @return
     */
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

    /**
     * this method deserialize the graph from a json file.
     *
     * @param jsonFile
     * @return
     */
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

    /**
     * this method sets mapDist values to be infinity.
     *
     * @return
     */
    void initiateMapDist() {
        Iterator<NodeData> nodeIter = graph.nodeIter();
        while (nodeIter.hasNext()) {
            NodeData currentVertex = nodeIter.next();
            int vertexId = currentVertex.getKey();
            mapDist.put(vertexId, INFINITY);
        }
    }

    /**
     * this method initiates the set for TSP algorithm.
     *
     * @param nodeList
     * @param unvisited
     */
    public void initiateSetForTSP(List<NodeData> nodeList, HashSet<Integer> unvisited) {
        for (NodeData node : nodeList) {
            int idOfCurrentVertex = node.getKey();
            unvisited.add(idOfCurrentVertex);
        }
    }

    /**
     * this method returns an inverted graph of the class' graph.
     *
     * @return
     */
    public DirectedWeighted invertGraph() {
        DirectedWeighted inverted = new DirectedWeightedClass();
        Iterator<NodeData> nodesIter = getGraph().nodeIter();
        Iterator<EdgeData> edgesIter = getGraph().edgeIter();
        while (nodesIter.hasNext()) {
            NodeData currentNode = nodesIter.next();
            inverted.addNode(new NodeDataClass(currentNode));
        }
        while (edgesIter.hasNext()) {
            EdgeData currentEdge = edgesIter.next();
            inverted.connect(currentEdge.getDest(), currentEdge.getSrc(), currentEdge.getWeight());
        }
        return inverted;
    }


}




