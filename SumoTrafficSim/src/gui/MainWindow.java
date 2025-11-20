package gui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("Traffic Simulator");
        setSize(1400, 850);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(new Color(245, 246, 250));

        // Create panels
        MapPanel mapPanel = new MapPanel();
        ControlPanel controlPanel = new ControlPanel(mapPanel);
        DashboardPanel dashboard = new DashboardPanel(mapPanel);

        // Add components
        add(controlPanel, BorderLayout.WEST);
        add(mapPanel, BorderLayout.CENTER);
        add(dashboard, BorderLayout.EAST);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}

