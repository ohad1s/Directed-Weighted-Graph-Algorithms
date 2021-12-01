package api;

import org.junit.jupiter.params.provider.EnumSource;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
        return false;
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
}
