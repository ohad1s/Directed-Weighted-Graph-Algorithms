package api;


import org.junit.jupiter.params.provider.EnumSource;
import org.w3c.dom.Node;

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
        return 0;
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
}
