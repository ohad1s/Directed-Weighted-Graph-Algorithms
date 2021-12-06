package api.GUI;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;


public class graphWindow extends JFrame implements MenuListener {
    public graphWindow(JPanel p){
        super();

        Dimension ScreenSize=Toolkit.getDefaultToolkit().getScreenSize();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(ScreenSize.height,ScreenSize.width);
        MenuBar menuBar= new MenuBar();
        Menu menu= new Menu("Menu");
        Menu Algo= new Menu("Choose Algorithm");
        MenuItem i1=new MenuItem("Item 1");
        MenuItem i2=new MenuItem("Item 2");
        MenuItem i3=new MenuItem("Item 3");
        MenuItem i4=new MenuItem("Item 4");
        MenuItem i5=new MenuItem("Item 5");
        menu.add(Algo);
        Algo.add(i1);
        Algo.add(i2);
        Algo.add(i3);
        Algo.add(i4);
        Algo.add(i5);
        menuBar.add(menu);
        this.setMenuBar(menuBar);
        this.setVisible(true);
        this.add(p);

    }

    @Override
    public void menuSelected(MenuEvent e) {

    }

    @Override
    public void menuDeselected(MenuEvent e) {
    }

    @Override
    public void menuCanceled(MenuEvent e) {
    }
}
