package gui;

import core.SimulationEngine;
import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    private final JLabel vehLbl = new JLabel("Vehicles: 0");
    private final JLabel timeLbl = new JLabel("Time: 0.0");

    public DashboardPanel(MapPanel map) {
        setPreferredSize(new Dimension(250, 60));
        setOpaque(false);

        setLayout(new FlowLayout(FlowLayout.LEFT, 40, 10)); // Horizontal spacing

        styleLabel(vehLbl);
        styleLabel(timeLbl);

        add(vehLbl);
        add(timeLbl);

        new Timer(150, e -> update(map.getEngine())).start();
    }

    private void update(SimulationEngine engine) {
        timeLbl.setText("Time: " + String.format("%.1f", engine.simTime));
        vehLbl.setText("Vehicles: " + engine.getVehicles().size());
    }

    private void styleLabel(JLabel lbl) {
        lbl.setFont(new Font("Arial", Font.BOLD, 20));
        lbl.setForeground(Color.BLACK);
        lbl.setOpaque(false);
    }
}
