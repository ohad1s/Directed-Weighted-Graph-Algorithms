package api.GUI;

import api.DirectedWeightedClass;
import api.DirectedWeightedGraphAlgorithmsClass;
import api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class graphWindow extends JFrame implements ActionListener {
    DirectedWeightedGraphAlgorithmsClass graph;
    Panel panel;
    final Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    MenuBar menuBar;
    Menu menu, algo;
    MenuItem load, save;
    MenuItem i1, i2, i3, i4, i5;


    public graphWindow(DirectedWeightedGraphAlgorithmsClass graph) {
        super();
        this.graph=graph;
        this.panel = new Panel((DirectedWeightedClass) this.graph.getGraph());
        this.menuBar = new MenuBar();
        this.menu = new Menu("Menu");
        this.load = new MenuItem("load");
        this.save = new MenuItem("save");
        this.algo = new Menu("Choose Algorithm");
        this.i1 = new MenuItem("isConnected");
        this.i3 = new MenuItem("shortestPath");
        this.i4 = new MenuItem("center");
        this.i5 = new MenuItem("tsp");
        this.menu.add(this.load);
        this.menu.add(this.save);
        this.menu.add(this.algo);
        this.algo.add(this.i1);
        this.algo.add(this.i3);
        this.algo.add(this.i4);
        this.algo.add(this.i5);
        this.menuBar.add(this.menu);
        this.setMenuBar(this.menuBar);
        this.add(this.panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(ScreenSize.width, ScreenSize.height);
        this.setVisible(true);
        this.load.addActionListener(this);
        this.save.addActionListener(this);
        this.i1.addActionListener(this);
        this.i3.addActionListener(this);
        this.i4.addActionListener(this);
        this.i5.addActionListener(this);


    }

    @Override
    public void actionPerformed(ActionEvent e) throws HeadlessException{
        if (e.getSource() == this.save) {
            JFileChooser j = new JFileChooser();
             int returnValue = j.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = j.getSelectedFile();
                System.out.println("save....");
                this.graph.save(selectedFile.getAbsolutePath());
            }
        }
        else if (e.getSource() == this.load) {
            JFileChooser j = new JFileChooser();
            int returnValue = j.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = j.getSelectedFile();
                this.graph.load(selectedFile.getAbsolutePath());
            }

            this.remove(this.panel);
            this.panel=new Panel((DirectedWeightedClass) this.graph.getGraph());
            this.add(this.panel);
            this.repaint();
            this.revalidate();

        }
        else if (e.getSource() == this.i1){
            Boolean isCon= (this.graph.isConnected());
            if (isCon==true){
                JOptionPane.showMessageDialog(this.panel,
                        "Yes! the graph is connected!",
                        "Connected!",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this.panel,
                        "No, the graph isn't connected...",
                        "Not Connected!",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (e.getSource() == this.i3){
            String NodesToCheck;
            NodesToCheck = JOptionPane.showInputDialog(this.panel, "Enter 2 Nodes to check\n (Example: 1,5)");
            String[]MyNodes=NodesToCheck.split(",");
            int first=Integer.valueOf(MyNodes[0]);
            int last=Integer.valueOf(MyNodes[1]);
            List<NodeData> Shortest= this.graph.shortestPath(first,last);
            this.remove(this.panel);
            this.panel=new Panel((DirectedWeightedClass) this.graph.getGraph());
            this.panel.shorted=true;
            this.panel.shortedP=Shortest;
            this.add(this.panel);
            this.repaint();
            this.revalidate();
            double dist=this.graph.shortestPathDist(first,last);
            JOptionPane.showMessageDialog(this.panel,
                    "The shortest path distance between "+first+" and "+last+" is:\n"+dist,
                    "You got a shortest path!",
                    JOptionPane.INFORMATION_MESSAGE);

        }
        else if (e.getSource() == this.i4){
            NodeData center=this.graph.center();
            this.remove(this.panel);
            this.panel=new Panel((DirectedWeightedClass) this.graph.getGraph());
            this.panel.isCenter=true;
            this.panel.center=center;
            this.add(this.panel);
            this.repaint();
            this.revalidate();
            JOptionPane.showMessageDialog(this.panel,
                    "The center node is:\n "+center.getKey(),
                    "Center Node!",
                    JOptionPane.INFORMATION_MESSAGE);

        }
        else if (e.getSource() == this.i5){

        }

    }
}
