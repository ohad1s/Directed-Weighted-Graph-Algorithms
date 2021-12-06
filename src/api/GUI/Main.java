package api.GUI;

import api.DirectedWeightedClass;
import api.DirectedWeightedGraphAlgorithmsClass;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;

public class Main {
    public static void main(String[]args){
        DirectedWeightedGraphAlgorithmsClass d= new DirectedWeightedGraphAlgorithmsClass();
        d.load("data/G1.json");
        Panel p=new Panel(d);
        graphWindow g = new graphWindow(p);



    }
}
