package gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(245, 246, 250);
    private static final Color BUTTON_COLOR = new Color(255, 255, 255);
    private static final Color BUTTON_HOVER_COLOR = new Color(240, 242, 245);
    private static final Color PRIMARY_COLOR = new Color(59, 130, 246);
    private static final Color SUCCESS_COLOR = new Color(34, 197, 94);
    private static final Color DANGER_COLOR = new Color(239, 68, 68);
    private static final Font SECTION_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 13);

    private JButton selectedToolButton = null;

    public ControlPanel(MapPanel map) {
        setPreferredSize(new Dimension(300, 850));
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Simulation Controls Section
        add(createSection("Simulation Controls"));
        add(Box.createRigidArea(new Dimension(0, 10)));
        
        JButton start = createActionButton("Start", SUCCESS_COLOR);
        JButton stop = createActionButton("Stop", DANGER_COLOR);
        JButton addVehicle = createActionButton("Add Vehicle", PRIMARY_COLOR);
        JButton toggleTl = createActionButton("Toggle Traffic Lights", PRIMARY_COLOR);

        start.addActionListener(e -> map.getEngine().start());
        stop.addActionListener(e -> map.getEngine().stop());
        addVehicle.addActionListener(e -> map.getEngine().addVehicle());
        toggleTl.addActionListener(e -> map.getEngine().toggleLights());

        add(start);
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(stop);
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(addVehicle);
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(toggleTl);

        add(Box.createRigidArea(new Dimension(0, 25)));
        add(createSeparator());
        add(Box.createRigidArea(new Dimension(0, 15)));

        // Map Editor Tools Section
        add(createSection("Map Editor Tools"));
        add(Box.createRigidArea(new Dimension(0, 10)));

        JButton toolRoad = createToolButton("Road Tool");
        JButton toolIntersection = createToolButton("Intersection");
        JButton toolTl = createToolButton("Traffic Light");
        JButton toolSelect = createToolButton("Select/Move");
        JButton toolDelete = createToolButton("Delete");

        toolRoad.addActionListener(e -> {
            map.setTool(MapPanel.Tool.ROAD);
            setSelectedTool(toolRoad);
        });
        toolIntersection.addActionListener(e -> {
            map.setTool(MapPanel.Tool.INTERSECTION);
            setSelectedTool(toolIntersection);
        });
        toolTl.addActionListener(e -> {
            map.setTool(MapPanel.Tool.TL);
            setSelectedTool(toolTl);
        });
        toolSelect.addActionListener(e -> {
            map.setTool(MapPanel.Tool.SELECT);
            setSelectedTool(toolSelect);
        });
        toolDelete.addActionListener(e -> {
            map.setTool(MapPanel.Tool.DELETE);
            setSelectedTool(toolDelete);
        });

        
        add(toolRoad);
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(toolIntersection);
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(toolTl);
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(toolSelect);
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(toolDelete);

        add(Box.createVerticalGlue());
    }

    private JLabel createSection(String title) {
        JLabel label = new JLabel(title);
        label.setFont(SECTION_FONT);
        label.setForeground(new Color(71, 85, 105));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JSeparator createSeparator() {
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setForeground(new Color(226, 232, 240));
        return separator;
    }

    private JButton createActionButton(String text, Color accentColor) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(accentColor);
        button.setBorder(new CompoundBorder(
            new LineBorder(accentColor, 1, true),
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });
        
        return button;
    }

    private JButton createToolButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(new Color(51, 65, 85));
        button.setBorder(new CompoundBorder(
            new LineBorder(new Color(203, 213, 225), 1, true),
            BorderFactory.createEmptyBorder(10, 16, 10, 16)
        ));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button != selectedToolButton) {
                    button.setBackground(BUTTON_HOVER_COLOR);
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button != selectedToolButton) {
                    button.setBackground(BUTTON_COLOR);
                }
            }
        });
        
        return button;
    }

    private void setSelectedTool(JButton button) {
        if (selectedToolButton != null) {
            selectedToolButton.setBackground(BUTTON_COLOR);
            selectedToolButton.setBorder(new CompoundBorder(
                new LineBorder(new Color(203, 213, 225), 1, true),
                BorderFactory.createEmptyBorder(10, 16, 10, 16)
            ));
        }
        
        selectedToolButton = button;
        button.setBackground(new Color(219, 234, 254));
        button.setBorder(new CompoundBorder(
            new LineBorder(PRIMARY_COLOR, 1, true),
            BorderFactory.createEmptyBorder(10, 16, 10, 16)
        ));
    }
}