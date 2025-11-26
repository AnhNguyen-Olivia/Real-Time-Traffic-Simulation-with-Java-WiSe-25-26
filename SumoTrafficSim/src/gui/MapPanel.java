package gui;

import core.SimulationEngine;
import core.Vehicle;
import core.TrafficLight;
import core.Road;
import core.Node;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MapPanel extends JPanel {

    private final SimulationEngine engine = new SimulationEngine();

    private Node startNode;
    private Node endNode;

    private int dragX, dragY;
    private boolean isDrawingRoad = false;

    // Map Editing Tools
    public enum Tool {
        NONE, ROAD, INTERSECTION, TL, SELECT, DELETE
    }

    private Tool currentTool = Tool.INTERSECTION;

    // Car image for vehicle rendering
    private BufferedImage carImage;
    private BufferedImage trafficLightImage;

    public void setTool(Tool tool) {
        this.currentTool = tool;
    }

    public MapPanel() {
        setBackground(new Color(102, 255, 102));

        // Load car image once
        try {
            carImage = ImageIO.read(new File("img/car.jpg"));  // Adjust path as needed
        } catch (IOException e) {
            System.err.println("Failed to load car image.");
            e.printStackTrace();
        }
        
        try {
            trafficLightImage = ImageIO.read(new File("img/greenlight.jpg"));
        } catch (IOException | NullPointerException e) {
            System.err.println("Failed to load traffic light image.");
            e.printStackTrace();
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

        if (currentTool == Tool.ROAD) {

            // SNAP start point
            Node snap = engine.getClosestNode(e.getX(), e.getY());

            if (snap != null) {
                startNode = snap;
            } else {
                startNode = new Node(e.getX(), e.getY());
                engine.nodes.add(startNode);
            }

            dragX = e.getX();
            dragY = e.getY();
            isDrawingRoad = true;
        } else if (currentTool == Tool.INTERSECTION) {
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
        if (isDrawingRoad && currentTool == Tool.ROAD) {
            dragX = e.getX();
            dragY = e.getY();
            repaint();
        }
    }

    private void handleMouseRelease(MouseEvent e) {

        if (isDrawingRoad && currentTool == Tool.ROAD) {

            Node snap = engine.getClosestNode(e.getX(), e.getY());

            if (snap != null) {
                endNode = snap;
            } else {
                endNode = new Node(e.getX(), e.getY());
                engine.nodes.add(endNode);
            }

            engine.roads.add(new Road(startNode, endNode));
            isDrawingRoad = false;
            repaint();
        }
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
        drawPreviewRoad(g2);
    }

    private void drawRoads(Graphics2D g) {
        g.setStroke(new BasicStroke(80, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(new Color(40, 40, 40));

        for (Road r : engine.roads) {
            g.drawLine(r.start.x, r.start.y, r.end.x, r.end.y);
        }
    }

    private void drawVehicles(Graphics2D g) {
        int carWidth = 60;  
        int carHeight = 30; 

        for (Vehicle v : engine.vehicles) {
            if (carImage != null) {
                g.drawImage(carImage, (int) v.x - carWidth / 2, (int) v.y - carHeight / 2, carWidth, carHeight, this);
            } else {
                g.setColor(v.color);
                g.fillOval((int) v.x - 6, (int) v.y - 6, 12, 12);
                g.setColor(Color.BLACK);
                g.drawOval((int) v.x - 6, (int) v.y - 6, 12, 12);
            }
        }
    }


    private void drawLights(Graphics2D g) {
        int lightSize = 30; // Adjust size as needed

        for (TrafficLight t : engine.trafficLights) {
            if (trafficLightImage != null) {
                g.drawImage(trafficLightImage, (int)t.x - lightSize / 2, (int)t.y - lightSize / 2, lightSize, lightSize, this);
            } else {
                // fallback: draw colored circles
                g.setColor(t.state.equals("G") ? Color.GREEN : Color.RED);
                g.fillOval((int)t.x - 10, (int)t.y - 10, 20, 20);
            }
        }
    }

    private void drawPreviewRoad(Graphics2D g) {
        if (isDrawingRoad && currentTool == Tool.ROAD) {
            g.setColor(new Color(80, 130, 200));
            g.setStroke(new BasicStroke(8));
            g.drawLine(startNode.x, startNode.y, dragX, dragY);
        }
    }

    private void drawIntersections(Graphics2D g) {
        for (Node n : engine.nodes) {
            g.setColor(Color.WHITE);
            g.fillOval(n.x - 5, n.y - 5, 10, 10);

            g.setColor(Color.BLUE);
            g.drawOval(n.x - 5, n.y - 5, 10, 10);
        }
    }
}
