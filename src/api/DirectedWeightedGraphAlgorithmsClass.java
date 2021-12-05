package api;


import java.util.*;

public class DirectedWeightedGraphAlgorithmsClass implements DirectedWeightedGraphAlgorithms{
    private DirectedWeightedClass graph;

    public DirectedWeightedGraphAlgorithmsClass(){
        this.graph = new DirectedWeightedClass();
    }

    @Override
    public void init(DirectedWeighted g){
        Iterator <NodeData> gVerticesIter = g.nodeIter();
        while (gVerticesIter.hasNext()){
            NodeData toAdd = gVerticesIter.next();
            graph.addNode(toAdd);
        }
        Iterator<EdgeData> gEdgesIter = g.edgeIter();
        while (gEdgesIter.hasNext()){
            EdgeData toAdd = gEdgesIter.next();
            int srcToConnect = toAdd.getSrc();
            int destToConnect = toAdd.getDest();
            double weight = toAdd.getWeight();
            graph.connect(srcToConnect, destToConnect, weight);
        }
    }

    @Override
    public DirectedWeighted getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeighted copy() {
        return this.graph.duplicate();
    }

    @Override
    public boolean isConnected() {
        int numOfVertices = graph.nodeSize();
        boolean [] visited = new boolean[numOfVertices];
        Arrays.fill(visited,false);
        Iterator<NodeData> verticesIterator = graph.nodeIter();
        while (verticesIterator.hasNext()){
            NodeData currentVertex = verticesIterator.next();
            dfs(currentVertex.getKey(), visited);

            for (int i = 0; i <visited.length ; i++) {
                if(!visited[i]){
                    return false;
                }
            }
            Arrays.fill(visited, false);
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        Hashtable<Integer, Double> shortestPaths=calculateShortestPathOHAD(src);
        double shortesPathForDst=shortestPaths.get(dest);
        if (shortesPathForDst==Double.MAX_VALUE) {
            return -1;
        }
        else{
            return shortesPathForDst;
        }
    }



    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }

    public boolean[] dfs (int key, boolean[] visited){
        Stack<Integer> stack = new Stack<>();
        stack.push(key);
        visited[key] = true;
        while (!stack.isEmpty()) {
            int currentVertex = stack.pop();
            Iterator<EdgeData> currentVertexEdgesIterator = graph.edgeIter(currentVertex);
            while (currentVertexEdgesIterator.hasNext()) {
                EdgeData currentEdge = currentVertexEdgesIterator.next();
                int edgeDest = currentEdge.getDest();
                if(!visited[edgeDest]){
                    stack.push(edgeDest);
                    visited[edgeDest] = true;
                }
            }
        }
        return visited;
    }

    public void calculateShortestPath(int src, Hashtable<Integer, Double> distFromSrc){
        HashSet<Integer> visited = new HashSet<>();
        HashSet<Integer> unvisited = new HashSet<>();
        Iterator <NodeData> nodeIter = graph.nodeIter();
        while (nodeIter.hasNext()){
            NodeData currentVertex = nodeIter.next();
            int vertexId = currentVertex.getKey();
            unvisited.add(vertexId);
            distFromSrc.put(vertexId, 10000.0);
        }
        distFromSrc.put(src, 0.0);
        Iterator <EdgeData> edgeIter = graph.edgeIter(src);
        while (edgeIter.hasNext()){
            EdgeData currentEdge = edgeIter.next();
            int dest = currentEdge.getDest();
            double weight = currentEdge.getWeight();
            distFromSrc.put(dest, weight);
        }



    }

    /**
     *
     * @param src
     * @return the shortest path distance to every node in the graph
     */
    public Hashtable<Integer, Double> calculateShortestPathOHAD(int src){
        HashSet<Integer> unvisited = new HashSet<>();
        for (int i=0; i<this.graph.nodeSize(); i++){
            if (i!=src){
                unvisited.add(i);
            }
        }
        Hashtable<Integer, Double> distFromSrcFinally=calcDistFromSrc(src,unvisited);
        Hashtable<Integer, Double> distFromSrcSpecific=calcDistFromSrc(src,unvisited);
        while(!unvisited.isEmpty()) {
            int minKey = 0;
            double minDist = Double.MAX_VALUE;
            for (Map.Entry<Integer, Double> entry : distFromSrcSpecific.entrySet()) {
                int Key = entry.getKey();
                double Dist = entry.getValue();
                if (Dist <= minDist) {
                    minKey = Key;
                    minDist = Dist;
                }
            }
            unvisited.remove(minKey);
            distFromSrcSpecific = calcDistFromSrc(minKey, unvisited);
            for (Map.Entry<Integer, Double> entry : distFromSrcSpecific.entrySet()) {
                int Key = entry.getKey();
                double Dist = entry.getValue();
                double distToCheck = distFromSrcFinally.get(minKey) + distFromSrcSpecific.get(Key);
                if (distToCheck <= distFromSrcFinally.get(Key)) {
                    distFromSrcFinally.replace(Key, distToCheck);
                }
            }
        }
        return distFromSrcFinally;
    }

    /**
     *
     * @param src
     * @return hashtable of dists between src to every node in the graph (by one edge if exists)
     */
    public Hashtable<Integer, Double> calcDistFromSrc(int src,HashSet<Integer>unvisited) {
        Hashtable<Integer, Double> dist = new Hashtable<>();
        for (int i: unvisited){
            double distToAdd=calcDistSrcToDst(src,i);
            dist.put(i,distToAdd);
        }
        return dist;
    }


    /**
     *
     * @param src
     * @param dst
     * @return distance between src node to dst node by one edge if exists
     */
    public double calcDistSrcToDst(int src, int dst){
        HashSet<EdgeData> edgePerSrc = this.graph.getEdgesFromSpecificVertex().get(src);
        for (EdgeData edge : edgePerSrc) {
            if (edge.getDest()==dst){
                return edge.getWeight();
            }
        }
        return Double.MAX_VALUE;
    }

}




