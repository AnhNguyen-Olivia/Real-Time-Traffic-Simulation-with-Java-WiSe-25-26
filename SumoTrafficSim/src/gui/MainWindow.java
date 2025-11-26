package gui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Traffic Simulator");
        setSize(1400, 850);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Make a layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1400, 850));

        MapPanel mapPanel = new MapPanel();
        mapPanel.setBounds(0, 0, 1400, 850);

        ControlPanel controlPanel = new ControlPanel(mapPanel);
        controlPanel.setBounds(0, 0, 220, 850);

        DashboardPanel dashboard = new DashboardPanel(mapPanel);
        dashboard.setBounds(1000, 20, 300, 60);

        layeredPane.add(mapPanel, Integer.valueOf(0));
        layeredPane.add(controlPanel, Integer.valueOf(1));
        layeredPane.add(dashboard, Integer.valueOf(2)); // Topmost

        setContentPane(layeredPane);
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
