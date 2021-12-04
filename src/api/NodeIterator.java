package api;

import api.NodeData;
import org.w3c.dom.Node;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class NodeIterator {
    private Iterator<NodeData> iter;
    private RuntimeException RuntimeException;

    public NodeIterator (Iterator <NodeData> iter){
        this.iter = iter;
        this.RuntimeException = new RuntimeException();
    }

    public boolean hadNext(){
        return iter.hasNext();
    }

    public NodeData next() throws Exception {
        try{
            NodeData toReturn = iter.next();
            return toReturn;
        }catch (Exception e){
            throw RuntimeException;
        }
    }
}
