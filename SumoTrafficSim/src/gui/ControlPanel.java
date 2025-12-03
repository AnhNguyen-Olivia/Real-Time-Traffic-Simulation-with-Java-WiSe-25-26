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
        add(Box.createRigidArea(new Dimension(0, 32)));
        
        // SUMO section
        JLabel sumoHeader = new JLabel("SUMO Integration");
        sumoHeader.setFont(new Font("Arial", Font.BOLD, 16));
        sumoHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(sumoHeader);
        add(Box.createRigidArea(new Dimension(0, 18)));
        
        add(createButton("<html><center>LOAD<br>SUMO NETWORK</center></html>", new Color(0, 128, 128), Color.WHITE, e -> {
            try {
                String netXmlPath = "D:\\\\An\\\\OOP Java\\\\Real-Time-Traffic-Simulation-with-Java-WiSe-25-26\\\\SumoConfig\\\\testing.net.xml";
                map.getEngine().loadFromSumo(netXmlPath);
                map.repaint();
                JOptionPane.showMessageDialog(this, 
                    "✓ SUMO network loaded successfully!\\n✓ Roads and traffic lights imported", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error loading SUMO network:\\n" + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }));
        add(Box.createRigidArea(new Dimension(0, 16)));
        
        add(createButton("<html><center>CONNECT<br>TO SUMO</center></html>", new Color(255, 128, 0), Color.WHITE, e -> {
            try {
                String sumoExe = "sumo-gui"; // or "sumo" for non-GUI
                String configFile = "D:\\\\An\\\\OOP Java\\\\Real-Time-Traffic-Simulation-with-Java-WiSe-25-26\\\\SumoConfig\\\\testing.sumocfg";
                map.getEngine().connectToSumo(sumoExe, configFile);
                JOptionPane.showMessageDialog(this, 
                    "✓ Connected to SUMO via TraCI!\\n✓ Real-time simulation active", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error connecting to SUMO:\\n" + ex.getMessage() + "\\n\\nMake sure SUMO is installed and in PATH.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }));

        // Glue at the bottom for final padding
        add(Box.createVerticalGlue());
    }
}
