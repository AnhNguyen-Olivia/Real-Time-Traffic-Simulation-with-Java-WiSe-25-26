//package gui;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class MainWindow extends JFrame {
//    public MainWindow() {
//        setTitle("Traffic Simulator");
//        setSize(1400, 850);
//        //setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//
//        // Make a layered pane
//        JLayeredPane layeredPane = new JLayeredPane();
//        layeredPane.setPreferredSize(new Dimension(1400, 850));
//
//        MapPanel mapPanel = new MapPanel();
//        mapPanel.setBounds(0, 0, 1400, 850);
//
//        ControlPanel controlPanel = new ControlPanel(mapPanel);
//        controlPanel.setBounds(0, 0, 220, 850);
//
//        DashboardPanel dashboard = new DashboardPanel(mapPanel);
//        dashboard.setBounds(1000, 20, 300, 60);
//
//        layeredPane.add(mapPanel, Integer.valueOf(0));
//        layeredPane.add(controlPanel, Integer.valueOf(1));
//        layeredPane.add(dashboard, Integer.valueOf(2)); // Topmost
//
//        setContentPane(layeredPane);
//        setVisible(true);
//        setResizable(true);
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(MainWindow::new);
//    }
//}

package gui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("Traffic Simulator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1400, 850);
        setLocationRelativeTo(null); // center on screen

        // ----------------------------
        // MAIN LAYOUT
        // ----------------------------
        setLayout(new BorderLayout());

        // Create map
        MapPanel mapPanel = new MapPanel();
        mapPanel.getEngine().start();
        mapPanel.getEngine().addVehicle();  // add at least one vehicle


        // Left control panel
        ControlPanel controlPanel = new ControlPanel(mapPanel);

        // Dashboard on top
        DashboardPanel dashboardPanel = new DashboardPanel(mapPanel);

        // ----------------------------
        // ADD TO WINDOW
        // ----------------------------
        add(controlPanel, BorderLayout.WEST);   // sidebar
        add(mapPanel, BorderLayout.CENTER);     // main drawing area
        add(dashboardPanel, BorderLayout.NORTH);// top bar

        // ----------------------------
        // OPTIONAL: Status bar at bottom
        // ----------------------------
        JLabel statusBar = new JLabel(" Ready");
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(statusBar, BorderLayout.SOUTH);

        setVisible(true);
<<<<<<< HEAD
        setResizable(false);
=======
>>>>>>> 54dcc5c33631a5885e684e358a4bd1dcc53eb177
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}



