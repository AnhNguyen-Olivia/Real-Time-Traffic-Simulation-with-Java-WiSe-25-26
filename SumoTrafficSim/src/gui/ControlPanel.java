package gui;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    // Create button with center-aligned label
    private JButton createButton(String text, Color bg, Color fg, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 48));
        button.setMinimumSize(new Dimension(180, 48));
        button.setPreferredSize(new Dimension(180, 48));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBackground(bg);
        button.setForeground(fg);
        button.addActionListener(action);
        button.setBorder(BorderFactory.createLineBorder(bg.darker(), 2));
        return button;
    }

    public ControlPanel(MapPanel map) {
        setPreferredSize(new Dimension(220, 650));
        setBackground(Color.LIGHT_GRAY);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Add vertical glue to push everything to the center
        add(Box.createVerticalGlue());

        // Simulation Controls header
        JLabel controlsHeader = new JLabel("Simulation Controls");
        controlsHeader.setFont(new Font("Arial", Font.BOLD, 16));
        controlsHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(controlsHeader);
        add(Box.createRigidArea(new Dimension(0, 18)));

        // Simulation control buttons - spaced out
        add(createButton("START", new Color(35, 217, 83), Color.BLACK, e -> map.getEngine().start()));
        add(Box.createRigidArea(new Dimension(0, 16)));

        add(createButton("STOP", new Color(226, 70, 60), Color.WHITE, e -> map.getEngine().stop()));
        add(Box.createRigidArea(new Dimension(0, 16)));

        add(createButton("ADD VEHICLE", new Color(244, 229, 66), Color.BLACK, e -> map.getEngine().addVehicle()));
        add(Box.createRigidArea(new Dimension(0, 16)));

//        add(createButton("<html><center>TOGGLE<br>TRAFFIC LIGHTS</center></html>", new Color(41, 107, 231), Color.WHITE, e -> map.getEngine().toggleLights()));
        add(Box.createRigidArea(new Dimension(0, 32)));

        // Add glue between major sections
        add(Box.createVerticalGlue());

        // Map Editor Tools header
        JLabel mapToolsHeader = new JLabel("Map Editor Tools");
        mapToolsHeader.setFont(new Font("Arial", Font.BOLD, 16));
        mapToolsHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(mapToolsHeader);
        add(Box.createRigidArea(new Dimension(0, 18)));

        // Map editor tool buttons - spaced out
        add(createButton("ROAD", Color.BLACK, Color.WHITE, e -> map.setTool(MapPanel.Tool.ROAD)));
        add(Box.createRigidArea(new Dimension(0, 12)));

        add(createButton("INTERSECTION", Color.BLACK, Color.WHITE, e -> map.setTool(MapPanel.Tool.INTERSECTION)));
        add(Box.createRigidArea(new Dimension(0, 12)));

        add(createButton("TRAFFIC LIGHT", Color.BLACK, Color.WHITE, e -> map.setTool(MapPanel.Tool.TL)));
        add(Box.createRigidArea(new Dimension(0, 12)));

        add(createButton("SELECT/MOVE", new Color(35, 217, 83), Color.BLACK, e -> map.setTool(MapPanel.Tool.SELECT)));
        add(Box.createRigidArea(new Dimension(0, 12)));

        add(createButton("DELETE", new Color(226, 70, 60), Color.WHITE, e -> map.setTool(MapPanel.Tool.DELETE)));

        // Glue at the bottom for final padding
        add(Box.createVerticalGlue());
    }
}
