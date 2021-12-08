package api.GUI;

import api.*;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class Panel extends JPanel {
    DirectedWeightedClass graph;
    final Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int[]nodesX;
    int[]nodesY;
    boolean shorted;
    List<NodeData>shortedP;
    boolean isCenter;
    NodeData center;
    boolean isTsp;
    List<NodeData>tspList;
    boolean addNode;
    boolean removeNode;


    public Panel(DirectedWeightedClass graph) {
        super();
        this.graph = graph;
        this.setSize(ScreenSize.width, ScreenSize.height);
        this.nodesX=new int[this.graph.nodeSize()];
        this.nodesY=new int[this.graph.nodeSize()];
        this.shorted=false;
        this.isCenter=false;
        this.isTsp=false;
        this.addNode=false;
        this.removeNode=false;

    }

    @Override
    protected void paintComponent(Graphics g) {
        UpdateXY();
        DrawNodes(g);
        DrawEdges(g);
        if (shorted){
            DrawShortestPath(g,this.shortedP);
        }
        if (isCenter){
            boldCenter(g,this.center);
        }
        if (isTsp){
            tspDraw(g,this.tspList);
        }
    }
    private void UpdateXY(){
        if (this.addNode=true){
            this.nodesX=new int[this.graph.nodeSize()+1];
            this.nodesY=new int[this.graph.nodeSize()+1];
        }
//        if (this.removeNode=true){
//            this.nodesX=new int[this.graph.nodeSize()-1];
//            this.nodesY=new int[this.graph.nodeSize()-1];
//        }
        double minX=Double.MAX_VALUE, maxY=Double.MIN_VALUE,maxX=Double.MIN_VALUE, minY=Double.MAX_VALUE;
        Iterator<NodeData> iter=this.graph.nodeIter();
        while (iter.hasNext()) {
            NodeData point =iter.next();
            if (point.getLocation().x()>maxX){
                maxX=point.getLocation().x();
            }
            if (point.getLocation().y()<minY){
                minY=point.getLocation().y();
            }
            if (point.getLocation().x()<minX){
                minX=point.getLocation().x();
            }
            if (point.getLocation().y()>maxY){
                maxY=point.getLocation().y();
            }
        }
        double X_par=(ScreenSize.width)/(maxX-minX)*0.9;
        double Y_par=(ScreenSize.height)/(maxY-minY)*0.8;
        iter=this.graph.nodeIter();
        while (iter.hasNext()) {
            NodeData point = iter.next();
            this.nodesX[point.getKey()]= (int) ((point.getLocation().x()-minX)*X_par)+9;
            this.nodesY[point.getKey()]= (int) ((point.getLocation().y()-minY)*Y_par)+9;
        }
    }

    private void DrawNodes(Graphics g) {
        Iterator<NodeData>iter=this.graph.nodeIter();
        while (iter.hasNext()) {
            NodeData point = iter.next();
            g.setColor(Color.BLACK);
            g.fillOval(this.nodesX[point.getKey()]-9,this.nodesY[point.getKey()]-9,18,18);
//            g.drawString(point.getLocation().toString(),this.nodesX[point.getKey()]+1,this.nodesY[point.getKey()]+1);
            Graphics2D g2d=(Graphics2D)g;
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(12));
            int key= point.getKey();
            String keyS=String.valueOf(key);
            g2d.drawString(keyS,this.nodesX[point.getKey()]-9,this.nodesY[point.getKey()]-9);
        }
    }

    private void DrawEdges(Graphics g) {
        Iterator<EdgeData>iter=this.graph.edgeIter();
        while (iter.hasNext()) {
            EdgeData arrow = iter.next();
            NodeData p1=this.graph.getNode(arrow.getSrc());
            NodeData p2=this.graph.getNode(arrow.getDest());
            int x1=this.nodesX[arrow.getSrc()];
            int y1=this.nodesY[arrow.getSrc()];
            int x2=this.nodesX[arrow.getDest()];
            int y2=this.nodesY[arrow.getDest()];
            Arrow ar=new Arrow(x1,y1,x2,y2,Color.BLUE,1);
            ar.draw(g);
        }
    }

    private void DrawShortestPath(Graphics g, List<NodeData> Shortest){
        Iterator<NodeData>iter=Shortest.listIterator();
        if (iter.hasNext()) {
            NodeData p1 = iter.next();
            while (iter.hasNext()) {
                NodeData p2 = iter.next();
                int x1 = this.nodesX[p1.getKey()];
                int y1 = this.nodesY[p1.getKey()];
                int x2 = this.nodesX[p2.getKey()];
                int y2 = this.nodesY[p2.getKey()];
                Arrow ar = new Arrow(x1, y1, x2, y2, Color.GREEN, 2);
                ar.draw(g);
                p1=p2;
            }
        }
    }

    private void boldCenter(Graphics g, NodeData center) {
        int x1 = this.nodesX[center.getKey()];
        int y1 = this.nodesY[center.getKey()];
        g.setColor(Color.MAGENTA);
        g.fillOval(x1-9,y1-9,18,18);
        Graphics2D g2d=(Graphics2D)g;
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(12));
        int key= center.getKey();
        String keyS=String.valueOf(key);
        g2d.drawString(keyS,x1-9,y1-9);
    }

    private void tspDraw(Graphics g, List<NodeData> tspNodes) {
        Iterator<NodeData>iter=tspNodes.listIterator();
        if (iter.hasNext()) {
            NodeData p1 = iter.next();
            while (iter.hasNext()) {
                NodeData p2 = iter.next();
                int x1 = this.nodesX[p1.getKey()];
                int y1 = this.nodesY[p1.getKey()];
                int x2 = this.nodesX[p2.getKey()];
                int y2 = this.nodesY[p2.getKey()];
                Arrow ar = new Arrow(x1, y1, x2, y2, Color.GREEN, 2);
                ar.draw(g);
                p1=p2;
            }
        }
    }
}

