package api.GUI;

import api.DirectedWeightedGraphAlgorithmsClass;
import api.EdgeData;
import api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;

public class Panel extends JPanel {
    DirectedWeightedGraphAlgorithmsClass graph;

    public Panel(DirectedWeightedGraphAlgorithmsClass g) {
        super();
        this.graph=g;
    }

    public void draw(){
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        while ( this.graph.getGraph().nodeIter().hasNext()){
            NodeData point=this.graph.getGraph().nodeIter().next();
            g.setColor(Color.BLACK);
            g.fillOval((int)point.getLocation().x(),(int)point.getLocation().y(),5,5);
            g.drawString(point.getLocation().toString(),(int)point.getLocation().x()+1,(int)point.getLocation().y()+1);
            while (this.graph.getGraph().edgeIter(point.getKey()).hasNext()){
                EdgeData line= this.graph.getGraph().edgeIter(point.getKey()).next();
                NodeData p2=this.graph.getGraph().getNode(line.getDest());
                g.setColor(Color.RED);
                g.drawLine((int)point.getLocation().x(),(int)point.getLocation().y(),(int)p2.getLocation().x(),(int)p2.getLocation().y());


            }
        }
    }
}
