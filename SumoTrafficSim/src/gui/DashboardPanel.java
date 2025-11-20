package gui;

import core.SimulationEngine;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    private final JLabel timeLbl = new JLabel("Time: 0.0");
    private final JLabel vehLbl = new JLabel("Vehicles: 0");

    public DashboardPanel(MapPanel map) {

        setPreferredSize(new Dimension(180, 850));
        setBackground(new Color(245, 246, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        title(timeLbl);
        title(vehLbl);

        add(timeLbl);
        add(vehLbl);

        new Timer(150, e -> update(map.getEngine())).start();
    }

    private void update(SimulationEngine engine) {
        timeLbl.setText("Time: " + String.format("%.1f", engine.simTime));
        vehLbl.setText("Vehicles: " + engine.getVehicles().size());
    }

    private void title(JLabel lbl) {
        lbl.setFont(new Font("Arial", Font.BOLD, 20));
    }
}
