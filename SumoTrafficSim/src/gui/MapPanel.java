package gui;

import core.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class MapPanel extends JPanel {

    private final SimulationEngine engine = new SimulationEngine();

    private Node startNode;
    private int dragX, dragY;
    private boolean isDrawingRoad = false;

    public enum Tool { NONE, ROAD, INTERSECTION, TL, SELECT, DELETE }
    private Tool currentTool = Tool.NONE;

    public void setTool(Tool tool) { this.currentTool = tool; }

    public MapPanel() {
        setBackground(new Color(0, 204, 0));

        // ----------- AUTO LOAD SUMO NETWORK --------------
        String path = "C:/Users/ASUS/Documents/Real-time Traffic Simulation Milestone 1/Real-time Traffic Simulation Java Project/SumoConfig/testing.net.xml";

        if (new File(path).exists()) {
            engine.loadSumoMap(path);
            System.out.println("SUMO map loaded: " + path);
        } else {
            System.err.println("SUMO map not found.");
        }
        //--------------------------------------------------

        new Timer(33, e -> repaintTick()).start();

        // MOUSE HANDLERS
        addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) { onPress(e); }
            @Override public void mouseReleased(MouseEvent e) { onRelease(e); }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override public void mouseDragged(MouseEvent e) { onDrag(e); }
        });
    }

    private void repaintTick() {
        engine.update();
        repaint();
    }

    private void onPress(MouseEvent e) {
        int x = e.getX(), y = e.getY();

        switch (currentTool) {

            case ROAD:
                startNode = engine.getClosestNode(x, y);
                if (startNode == null) {
                    startNode = new Node("n" + engine.nodes.size(), x, y);
                    engine.nodes.add(startNode);
                }
                dragX = x;
                dragY = y;
                isDrawingRoad = true;
                break;

            case INTERSECTION:
                Node n = new Node("n" + engine.nodes.size(), x, y);
                engine.nodes.add(n);
                repaint();
                break;

            case TL:
                engine.trafficLights.add(new TrafficLight(x, y)); 
                repaint();
                break;

            default:
                break;
        }
    }

    private void onDrag(MouseEvent e) {
        if (isDrawingRoad && currentTool == Tool.ROAD) {
            dragX = e.getX();
            dragY = e.getY();
            repaint();
        }
    }

    private void onRelease(MouseEvent e) {
        if (isDrawingRoad && currentTool == Tool.ROAD) {

            Node endNode = engine.getClosestNode(e.getX(), e.getY());
            if (endNode == null) {
                endNode = new Node("n" + engine.nodes.size(), e.getX(), e.getY());
                engine.nodes.add(endNode);
            }

            // Manual roads use simple fallback road creation
            Road r = new Road("r" + engine.roads.size(), startNode, endNode);
            engine.roads.add(r);

            isDrawingRoad = false;
            repaint();
        }
    }

    public SimulationEngine getEngine() { return engine; }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawSumoRoads(g2);
        drawNodes(g2);
        drawLights(g2);
        drawVehicles(g2);
        drawPreviewRoad(g2);
    }

    // ---------- SUMO ROADS (LANE SHAPES) ------------------
    private void drawSumoRoads(Graphics2D g) {
        g.setStroke(new BasicStroke(12, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        for (Road r : engine.roads) {
            for (Lane lane : r.lanes) {
                g.setColor(new Color(40, 40, 40));   // asphalt
                g.drawPolyline(lane.xs, lane.ys, lane.xs.length);

                g.setColor(new Color(200, 200, 200, 120)); // centerline
                g.setStroke(new BasicStroke(2));
                g.drawPolyline(lane.xs, lane.ys, lane.xs.length);
            }
        }
    }

    // -------------------- INTERSECTIONS --------------------
    private void drawNodes(Graphics2D g) {
        for (Node n : engine.nodes) {
            g.setColor(Color.WHITE);
            g.fillOval(n.x - 4, n.y - 4, 8, 8);
            g.setColor(Color.BLUE);
            g.drawOval(n.x - 4, n.y - 4, 8, 8);
        }
    }

    // -------------------- TRAFFIC LIGHTS -------------------
    private void drawLights(Graphics2D g) {
        for (TrafficLight t : engine.trafficLights) {

            int lx = t.x, ly = t.y;

            g.setColor(Color.BLACK);
            g.fillRect(lx - 8, ly - 8, 16, 16);

            g.setColor(t.state.equals("G") ? Color.GREEN : Color.RED);
            g.fillOval(lx - 6, ly - 6, 12, 12);
        }
    }

    // --------------------- VEHICLES ------------------------
    private void drawVehicles(Graphics2D g) {
        for (Vehicle v : engine.vehicles) {
            g.setColor(v.color);
            g.fillOval((int)v.x - 6, (int)v.y - 6, 12, 12);
        }
    }

    // ------------------- PREVIEW MANUAL ROAD --------------
    private void drawPreviewRoad(Graphics2D g) {
        if (isDrawingRoad && currentTool == Tool.ROAD) {
            g.setColor(new Color(80, 130, 200));
            g.setStroke(new BasicStroke(6));
            g.drawLine(startNode.x, startNode.y, dragX, dragY);
        }
    }
}