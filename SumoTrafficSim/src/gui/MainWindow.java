package gui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("Traffic Simulator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1400, 850);
        setLocationRelativeTo(null); // center on screen

        setLayout(new BorderLayout());

        // Create map
        MapPanel mapPanel = new MapPanel();
        mapPanel.getEngine().start();
        mapPanel.getEngine().addVehicle();  // add at least one vehicle


        // Left control panel
        ControlPanel controlPanel = new ControlPanel(mapPanel);

        // Dashboard on top
        DashboardPanel dashboardPanel = new DashboardPanel(mapPanel);

        add(controlPanel, BorderLayout.WEST);   // sidebar
        add(mapPanel, BorderLayout.CENTER);     // main drawing area
        add(dashboardPanel, BorderLayout.NORTH);// top bar

        JLabel statusBar = new JLabel(" Ready");
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(statusBar, BorderLayout.SOUTH);

        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}



