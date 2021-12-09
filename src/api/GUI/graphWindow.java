package api.GUI;

import api.DirectedWeightedGraphAlgorithmsClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class graphWindow extends JFrame implements ActionListener {
    DirectedWeightedGraphAlgorithmsClass graph;
    Panel panel;
    final Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    MenuBar menuBar;
    Menu menu, algo;
    MenuItem load, save, draw;
    MenuItem i1, i2, i3, i4, i5;

    public graphWindow(DirectedWeightedGraphAlgorithmsClass graph) {
        super();
        this.graph=graph;
        this.panel = new Panel(graph);
        this.add(this.panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(ScreenSize.height, ScreenSize.width);
        this.menuBar = new MenuBar();
        this.menu = new Menu("Menu");
        this.load = new MenuItem("load");
        this.save = new MenuItem("save");
        this.draw = new MenuItem("draw");
        this.algo = new Menu("Choose Algorithm");
        this.i1 = new MenuItem("isConnected");
        this.i2 = new MenuItem("shortestPathDist");
        this.i3 = new MenuItem("shortestPath");
        this.i4 = new MenuItem("center");
        this.i5 = new MenuItem("tsp");
        this.menu.add(this.load);
        this.menu.add(this.save);
        this.menu.add(this.draw);
        this.menu.add(this.algo);
        this.algo.add(this.i1);
        this.algo.add(this.i2);
        this.algo.add(this.i3);
        this.algo.add(this.i4);
        this.algo.add(this.i5);
        this.menuBar.add(this.menu);
        this.setMenuBar(this.menuBar);
        this.setVisible(true);
        this.load.addActionListener(this);
        this.save.addActionListener(this);
        this.draw.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.save) {
            JFileChooser j = new JFileChooser();
             int returnValue = j.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = j.getSelectedFile();
                System.out.println("save....");
                this.graph.save(selectedFile.getAbsolutePath());
            }

        }
        if (e.getSource() == this.load) {
            JFileChooser j = new JFileChooser();
            int returnValue = j.showOpenDialog(null);
            // int returnValue = jfc.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = j.getSelectedFile();
                System.out.println("loading....");
                this.graph.load(selectedFile.getAbsolutePath());
            }

        }
        if (e.getSource() == this.draw) {
            this.panel.draw();
        }

    }
}
