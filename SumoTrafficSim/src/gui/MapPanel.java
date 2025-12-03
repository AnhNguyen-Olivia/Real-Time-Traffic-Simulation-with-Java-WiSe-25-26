package gui;

import core.SimulationEngine;
import core.Vehicle;
import core.TrafficLight;
import core.Road;
import core.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapPanel extends JPanel {

    private final SimulationEngine engine = new SimulationEngine();

    // Map Editing Tools
    public enum Tool {
        NONE, INTERSECTION, TL, SELECT, DELETE
    }

    private Tool currentTool = Tool.INTERSECTION;

    public void setTool(Tool tool) {
        this.currentTool = tool;
    }

    public MapPanel() {
        setBackground(new Color(220, 220, 220)); // Light gray like SUMO

        // Auto-load SUMO network on startup
        try {
            // Try multiple possible paths
            String[] possiblePaths = {
                "SumoConfig/testing.net.xml",
                "../SumoConfig/testing.net.xml",
                "../../SumoConfig/testing.net.xml"
            };
            
            String netXmlPath = null;
            for (String path : possiblePaths) {
                if (new java.io.File(path).exists()) {
                    netXmlPath = path;
                    break;
                }
            }
            
            if (netXmlPath != null) {
                engine.loadFromSumo(netXmlPath);
                System.out.println("✓ Auto-loaded SUMO network from " + netXmlPath);
            } else {
                System.err.println("⚠ SUMO network file not found. Please load manually.");
            }
        } catch (Exception ex) {
            System.err.println("Could not auto-load SUMO network: " + ex.getMessage());
        }

        // Smooth animation: 30 FPS
        new Timer(33, e -> updateAndRepaint()).start();

        // MOUSE LISTENERS
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMousePress(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                handleMouseRelease(e);
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseDrag(e);
            }
        });
    }

    public SimulationEngine getEngine() {
        return engine;
    }

    private void handleMousePress(MouseEvent e) {
        if (currentTool == Tool.INTERSECTION) {
            // Create a node
            Node n = new Node(e.getX(), e.getY());
            engine.nodes.add(n);

            // Create a traffic light at that node
            engine.trafficLights.add(new TrafficLight(e.getX(), e.getY()));

            repaint();
        } else if (currentTool == Tool.TL) {
            engine.trafficLights.add(new TrafficLight(e.getX(), e.getY()));
            repaint();
        }
    }

    private void handleMouseDrag(MouseEvent e) {
        // Road drawing disabled - will be imported from SUMO
    }

    private void handleMouseRelease(MouseEvent e) {
        // Road drawing disabled - will be imported from SUMO
    }

    private void updateAndRepaint() {
        engine.update();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawRoads(g2);
        drawIntersections(g2);
        drawLights(g2);
        drawVehicles(g2);
    }

    private void drawRoads(Graphics2D g) {
        for (Road r : engine.roads) {
            // Draw road base (dark gray)
            g.setStroke(new BasicStroke(40, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.setColor(new Color(60, 60, 60));
            g.drawLine(r.start.x, r.start.y, r.end.x, r.end.y);
            
            // Draw center dashed line (yellow)
            g.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{10, 10}, 0));
            g.setColor(new Color(255, 200, 0));
            g.drawLine(r.start.x, r.start.y, r.end.x, r.end.y);
            
            // Draw road edges (white lines)
            g.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.setColor(Color.WHITE);
            
            // Calculate perpendicular offset for edge lines
            double dx = r.end.x - r.start.x;
            double dy = r.end.y - r.start.y;
            double len = Math.sqrt(dx*dx + dy*dy);
            if (len > 0) {
                double perpX = -dy / len * 19;
                double perpY = dx / len * 19;
                
                // Left edge
                g.drawLine(
                    (int)(r.start.x + perpX), (int)(r.start.y + perpY),
                    (int)(r.end.x + perpX), (int)(r.end.y + perpY)
                );
                
                // Right edge
                g.drawLine(
                    (int)(r.start.x - perpX), (int)(r.start.y - perpY),
                    (int)(r.end.x - perpX), (int)(r.end.y - perpY)
                );
            }
        }
    }

    private void drawVehicles(Graphics2D g) {
        int carWidth = 24;  
        int carHeight = 14; 

        for (Vehicle v : engine.vehicles) {
            // Draw shadow
            g.setColor(new Color(0, 0, 0, 50));
            g.fillRoundRect((int) v.x - carWidth/2 + 2, (int) v.y - carHeight/2 + 2, carWidth, carHeight, 6, 6);
            
            // Draw car body
            g.setColor(v.color);
            g.fillRoundRect((int) v.x - carWidth/2, (int) v.y - carHeight/2, carWidth, carHeight, 6, 6);
            
            // Draw car outline
            g.setColor(v.color.darker());
            g.setStroke(new BasicStroke(1.5f));
            g.drawRoundRect((int) v.x - carWidth/2, (int) v.y - carHeight/2, carWidth, carHeight, 6, 6);
            
            // Draw windshield
            g.setColor(new Color(100, 150, 200, 150));
            g.fillRoundRect((int) v.x - carWidth/2 + 4, (int) v.y - carHeight/2 + 2, 8, carHeight - 4, 3, 3);
        }
    }

    private void drawLights(Graphics2D g) {
        for (TrafficLight t : engine.trafficLights) {
            // Traffic light pole
            g.setColor(new Color(70, 70, 70));
            g.fillRect(t.x - 2, t.y - 15, 4, 30);
            
            // Traffic light box (black background)
            g.setColor(new Color(30, 30, 30));
            g.fillRoundRect(t.x - 10, t.y - 12, 20, 24, 4, 4);
            
            // Border
            g.setColor(new Color(60, 60, 60));
            g.setStroke(new BasicStroke(1.5f));
            g.drawRoundRect(t.x - 10, t.y - 12, 20, 24, 4, 4);
            
            // Red light (top)
            if (t.state.equals("R")) {
                g.setColor(new Color(255, 50, 50));
                g.fillOval(t.x - 6, t.y - 9, 12, 12);
                // Glow effect
                g.setColor(new Color(255, 0, 0, 80));
                g.fillOval(t.x - 8, t.y - 11, 16, 16);
            } else {
                g.setColor(new Color(80, 30, 30));
                g.fillOval(t.x - 6, t.y - 9, 12, 12);
            }
            
            // Green light (bottom)
            if (t.state.equals("G")) {
                g.setColor(new Color(50, 255, 50));
                g.fillOval(t.x - 6, t.y + 1, 12, 12);
                // Glow effect
                g.setColor(new Color(0, 255, 0, 80));
                g.fillOval(t.x - 8, t.y - 1, 16, 16);
            } else {
                g.setColor(new Color(30, 80, 30));
                g.fillOval(t.x - 6, t.y + 1, 12, 12);
            }
        }
    }

    private void drawIntersections(Graphics2D g) {
        // Draw subtle intersection markers only
        for (Node n : engine.nodes) {
            // Draw a small gray circle for intersection center (subtle)
            g.setColor(new Color(80, 80, 80, 100));
            g.fillOval(n.x - 3, n.y - 3, 6, 6);
        }
    }
}
