package api.GUI;
import api.*;
import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class Panel extends JPanel {
    DirectedWeightedClass graph;
    final Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    boolean shorted;
    List<NodeData>shortedP;
    boolean isCenter;
    NodeData center;
    boolean isTsp;
    List<NodeData>tspList;
    double minX,minY,maxX,maxY;
    double X_par, Y_par;



    public Panel(DirectedWeightedClass graph) {
        super();
        this.graph = graph;
        this.setSize(ScreenSize.width, ScreenSize.height);
        this.shorted=false;
        this.isCenter=false;
        this.isTsp=false;
        this.minX=Double.MAX_VALUE;
        this.maxY=Double.MIN_VALUE;
        this.maxX=Double.MIN_VALUE;
        this.minY=Double.MAX_VALUE;
        this.X_par=0;
        this.Y_par=0;
    }

    @Override
    protected void paintComponent(Graphics g) {
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

    private void DrawNodes(Graphics g) {
        Iterator<NodeData> iter1=this.graph.nodeIter();
        while (iter1.hasNext()) {
            NodeData point =iter1.next();
            if (point.getLocation().x()>this.maxX){
                this.maxX=point.getLocation().x();
            }
            if (point.getLocation().y()<this.minY){
                this.minY=point.getLocation().y();
            }
            if (point.getLocation().x()<this.minX){
                this.minX=point.getLocation().x();
            }
            if (point.getLocation().y()>this.maxY){
                this.maxY=point.getLocation().y();
            }
        }
        this.X_par=(ScreenSize.width)/(maxX-minX)*0.9;
        this.Y_par=(ScreenSize.height)/(maxY-minY)*0.8;
        Iterator<NodeData>iter=this.graph.nodeIter();
        while (iter.hasNext()) {
            NodeData point = iter.next();
            g.setColor(Color.BLACK);
            g.fillOval((int) ((point.getLocation().x()-this.minX)*this.X_par),(int) ((point.getLocation().y()-this.minY)*this.Y_par),18,18);
            Graphics2D g2d=(Graphics2D)g;
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(12));
            int key= point.getKey();
            String keyS=String.valueOf(key);
            g2d.drawString(keyS,(int) ((point.getLocation().x()-this.minX)*this.X_par),(int) ((point.getLocation().y()-this.minY)*this.Y_par));
        }
    }

    private void DrawEdges(Graphics g) {
        Iterator<EdgeData>iter=this.graph.edgeIter();
        while (iter.hasNext()) {
            EdgeData arrow = iter.next();
            NodeData p1=this.graph.getNode(arrow.getSrc());
            NodeData p2=this.graph.getNode(arrow.getDest());
            int x1=(int) ((p1.getLocation().x()-this.minX)*this.X_par);
            int y1=(int) ((p1.getLocation().y()-this.minY)*this.Y_par);
            int x2=(int) ((p2.getLocation().x()-this.minX)*this.X_par);
            int y2=(int) ((p2.getLocation().y()-this.minY)*this.Y_par);
            Arrow ar=new Arrow(x1+9,y1+9,x2+9,y2+9,Color.BLUE,1);
            ar.draw(g);
        }
    }

    private void DrawShortestPath(Graphics g, List<NodeData> Shortest){
        Iterator<NodeData>iter=Shortest.listIterator();
        if (iter.hasNext()) {
            NodeData p1 = iter.next();
            while (iter.hasNext()) {
                NodeData p2 = iter.next();
                int x1=(int) ((p1.getLocation().x()-this.minX)*this.X_par);
                int y1=(int) ((p1.getLocation().y()-this.minY)*this.Y_par);
                int x2=(int) ((p2.getLocation().x()-this.minX)*this.X_par);
                int y2=(int) ((p2.getLocation().y()-this.minY)*this.Y_par);
                Arrow ar = new Arrow(x1+9, y1+9, x2+9, y2+9, Color.GREEN, 2);
                ar.draw(g);
                p1=p2;
            }
        }
    }

    private void boldCenter(Graphics g, NodeData center) {
        int x1=(int) ((center.getLocation().x()-this.minX)*this.X_par);
        int y1=(int) ((center.getLocation().y()-this.minY)*this.Y_par);
        g.setColor(Color.MAGENTA);
        g.fillOval(x1,y1,18,18);
        Graphics2D g2d=(Graphics2D)g;
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(12));
        int key= center.getKey();
        String keyS=String.valueOf(key);
        g2d.drawString(keyS,x1,y1);
    }

    private void tspDraw(Graphics g, List<NodeData> tspNodes) {
        Iterator<NodeData>iter=tspNodes.listIterator();
        if (iter.hasNext()) {
            NodeData p1 = iter.next();
            while (iter.hasNext()) {
                NodeData p2 = iter.next();
                int x1=(int) ((p1.getLocation().x()-this.minX)*this.X_par);
                int y1=(int) ((p1.getLocation().y()-this.minY)*this.Y_par);
                int x2=(int) ((p2.getLocation().x()-this.minX)*this.X_par);
                int y2=(int) ((p2.getLocation().y()-this.minY)*this.Y_par);
                Arrow ar = new Arrow(x1+9, y1+9, x2+9, y2+9, Color.GREEN, 2);
                ar.draw(g);
                p1=p2;
            }
        }
    }
}

