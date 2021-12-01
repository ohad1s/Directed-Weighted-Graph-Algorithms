package api;

import java.util.*;

public class DirectedWeightedClass implements DirectedWeighted {

    private Hashtable<Integer, NodeData> vertices;
    private Hashtable<String, EdgeData> edges;
    private HashSet<NodeData> iterableNodes;
    private HashSet<EdgeData> iterableEdges;
    private Hashtable<Integer, HashSet<EdgeData>> edgesFromSpecificVertex;
    private int MC;

    /**
     * this method is the constructor that initiates the DirectedWeightedGraph.
     */
    public DirectedWeightedClass() {
        this.vertices = new Hashtable<>();
        this.edges = new Hashtable<>();
        this.iterableNodes = new HashSet<>();
        this.iterableEdges = new HashSet<>();
        this.edgesFromSpecificVertex = new Hashtable<>();
        this.MC = 0;
    }

    /**
     * this method returns a vertex by given key. returns null in case this vertex does not exist in the graph.
     *
     * @param key - the node_id
     * @return
     */
    @Override
    public NodeData getNode(int key) {
        if (vertices.containsKey(key)) {
            NodeData toReturn = vertices.get(key);
            return toReturn;
        } else {
            return null;
        }
    }

    /**
     * this method returns an edge by given source vertex and destination vertex.
     * returns null in case this edge does not exist in the graph.
     *
     * @param src
     * @param dest
     * @return
     */
    @Override
    public EdgeData getEdge(int src, int dest) {
        String key = "" + src + "," + dest;
        if (edges.containsKey(key)) {
            EdgeData toReturn = edges.get(key);
            return toReturn;
        } else {
            return null;
        }
    }

    /**
     * this method returns the graph's vertexes HashTable.
     *
     * @return
     */
    public Hashtable<Integer, NodeData> getVertices() {
        return vertices;
    }

    /**
     * this method returns the graph's edges HashTable.
     *
     * @return
     */
    public Hashtable<String, EdgeData> getEdges() {
        return edges;
    }

    /**
     * this method returns the graph's HashTable of the HashSets of edges leaving all nodes.
     *
     * @return
     */
    public Hashtable<Integer, HashSet<EdgeData>> getEdgesFromSpecificVertex() {
        return edgesFromSpecificVertex;
    }

    public void setVertices(Hashtable<Integer, NodeData> vertices) {
        this.vertices = vertices;
    }

    public void setEdges(Hashtable<String, EdgeData> edges) {
        this.edges = edges;
    }

    public void setIterableNodes(HashSet<NodeData> iterableNodes) {
        this.iterableNodes = iterableNodes;
    }

    public void setIterableEdges(HashSet<EdgeData> iterableEdges) {
        this.iterableEdges = iterableEdges;
    }

    public void setEdgesFromSpecificVertex(Hashtable<Integer, HashSet<EdgeData>> edgesFromSpecificVertex) {
        this.edgesFromSpecificVertex = edgesFromSpecificVertex;
    }

    /**
     * this method receives a vertex, and adds it to the graph in case it does not exist already.
     *
     * @param n
     */
    @Override
    public void addNode(NodeData n) {
        int idKey = n.getKey();
        if (!vertices.containsKey(idKey)) {
            vertices.put(idKey, n);
            iterableNodes.add(n);
            edgesFromSpecificVertex.put(n.getKey(), new HashSet<>());
            MC++;
        }

    }

    /**
     * this method connects two vertexes by given source vertex and destination vertex.
     *
     * @param src    - the source vertex of the edge.
     * @param dest   - the destination vertex of the edge.
     * @param weight
     */
    @Override
    public void connect(int src, int dest, double weight) {
        EdgeData toConnect = new EdgeDataClass(src, dest, weight);
        String key = "" + src + "," + dest;
        if (!edges.containsKey(key)) {
            edges.put(key, toConnect);
            iterableEdges.add(toConnect);
            HashSet<EdgeData> srcNodeEdges = edgesFromSpecificVertex.get(src);
            srcNodeEdges.add(toConnect);
            MC++;
        }
    }

    /**
     * this method returns an iterator over the graph's vertexes.
     *
     * @return
     */
    @Override
    public Iterator<NodeData> nodeIter() {
        return iterableNodes.iterator();
    }

    /**
     * this method returns an iterator over the graph's edges.
     *
     * @return
     */
    @Override
    public Iterator<EdgeData> edgeIter() {
        return iterableEdges.iterator();
    }

    /**
     * this method returns an iterator over edges leaving a specific vertex.
     * if the vertex does not exist in the graph, the method will return null.
     *
     * @param node_id
     * @return
     */
    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        if (vertices.containsKey(node_id)) {
            HashSet<EdgeData> toIterate = edgesFromSpecificVertex.get(node_id);
            return toIterate.iterator();
        } else {
            return null;
        }
    }

    /**
     * this method removes than returns a vertex from the graph by given key. the method removes all edges connected to
     * this vertex, leaving from it or arriving to it.
     * in case the vertex does not exist in the graph the method will return null.
     *
     * @param key
     * @return
     */
    @Override
    public NodeData removeNode(int key) {
        if (vertices.containsKey(key)) {
            NodeData toRemove = vertices.get(key);
            Iterator<EdgeData> edgeDataIterator = edgeIter();
            ArrayList<String> keysToRemove = new ArrayList<>();
            while (edgeDataIterator.hasNext()) {
                EdgeData edgeToCheck = edgeDataIterator.next();
                int currentSrc = edgeToCheck.getSrc();
                int currentDest = edgeToCheck.getDest();
                if (currentSrc == key || currentDest == key) {
                    String keyToRemove = "" + currentSrc + "," + currentDest;
                    keysToRemove.add(keyToRemove);
                }
            }
            for (String keyToRemove : keysToRemove) {
                EdgeData edgeToRemove = edges.remove(keyToRemove);
                iterableEdges.remove(edgeToRemove);
                if (edgeToRemove.getDest() == key) {
                    int edgeToRemoveSrc = edgeToRemove.getSrc();
                    HashSet<EdgeData> destVertexEdges = edgesFromSpecificVertex.get(edgeToRemoveSrc);
                    destVertexEdges.remove(edgeToRemove);

                }
            }
            edgesFromSpecificVertex.remove(key);
            vertices.remove(key);
            MC++;
            return toRemove;
        } else {
            return null;
        }
    }

    /**
     * this method removes an edge from the graph by given source vertex and destination vertex.
     * the method removes the edge from edges HashTable, and also removing the edge from the HashSet
     * of the source vertex.
     * in case the edge does not exist in the graph, the function will return null;
     *
     * @param src
     * @param dest
     * @return
     */
    @Override
    public EdgeData removeEdge(int src, int dest) {
        String key = "" + src + "," + dest;
        if (edges.containsKey(key)) {
            EdgeData toRemove = edges.remove(key);
            iterableEdges.remove(toRemove);
            HashSet<EdgeData> srcEdges = edgesFromSpecificVertex.get(src);
            srcEdges.remove(toRemove);
            MC++;
            return toRemove;
        } else {
            return null;
        }
    }


    /**
     * this method returns the number of vertexes in the graph.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return vertices.size();
    }

    /**
     * this method returns the number of edges in the graph.
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return edges.size();
    }

    /**
     * this method returns the number of changes done to the graph.
     *
     * @return
     */
    @Override
    public int getMC() {
        return this.MC;
    }


    public DirectedWeighted duplicate() {
        Hashtable<Integer, NodeData> otherVertices = (Hashtable<Integer, NodeData>) this.vertices.clone();
        Hashtable<String, EdgeData> otherEdges = (Hashtable<String, EdgeData>) this.edges.clone();
        HashSet<NodeData> otherIterableNodes = (HashSet<NodeData>) this.iterableNodes.clone();
        HashSet<EdgeData> otherIterableEdges = (HashSet<EdgeData>) this.iterableEdges.clone();
        Hashtable<Integer, HashSet<EdgeData>> otherEdgesFromSpecificVertex = (Hashtable<Integer, HashSet<EdgeData>>) this.edgesFromSpecificVertex.clone();
        DirectedWeightedClass otherGraph = new DirectedWeightedClass();
        otherGraph.setVertices(otherVertices);
        otherGraph.setEdges(otherEdges);
        otherGraph.setIterableEdges(otherIterableEdges);
        otherGraph.setIterableNodes(otherIterableNodes);
        otherGraph.setEdgesFromSpecificVertex(otherEdgesFromSpecificVertex);
        return otherGraph;
    }


}
