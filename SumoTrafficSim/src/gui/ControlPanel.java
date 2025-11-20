package gui;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    public ControlPanel(MapPanel map) {

        setPreferredSize(new Dimension(300, 850));
        setBackground(new Color(245, 246, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));

        JButton start = createButton("Start");
        JButton stop = createButton("Stop");
        JButton addVehicle = createButton("Add Vehicle");
        JButton toggleTl = createButton("Toggle Lights");

        start.addActionListener(e -> map.getEngine().start());
        stop.addActionListener(e -> map.getEngine().stop());
        addVehicle.addActionListener(e -> map.getEngine().addVehicle());
        toggleTl.addActionListener(e -> map.getEngine().toggleLights());

        add(start);
        add(stop);
        add(addVehicle);
        add(toggleTl);
        
        JButton toolRoad = createButton("Road Tool");
        JButton toolIntersection = createButton("Intersection");
        JButton toolTl = createButton("Add Traffic Light");
        JButton toolSelect = createButton("Select/Move");
        JButton toolDelete = createButton("Delete");

        toolRoad.addActionListener(e -> map.setTool(MapPanel.Tool.ROAD));
        toolIntersection.addActionListener(e -> map.setTool(MapPanel.Tool.INTERSECTION));
        toolTl.addActionListener(e -> map.setTool(MapPanel.Tool.TL));
        toolSelect.addActionListener(e -> map.setTool(MapPanel.Tool.SELECT));
        toolDelete.addActionListener(e -> map.setTool(MapPanel.Tool.DELETE));

        add(toolRoad);
        add(toolIntersection);
        add(toolTl);
        add(toolSelect);
        add(toolDelete);

    }

    private JButton createButton(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBackground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 18));
        return b;
    }
}


