package core;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.*;

public class SumoNetworkLoader {
    
    public static class NetworkData {
        public List<Node> nodes = new ArrayList<>();
        public List<Road> roads = new ArrayList<>();
        public List<TrafficLight> trafficLights = new ArrayList<>();
        public Map<String, Node> nodeMap = new HashMap<>();
        
        // Store transformation parameters for coordinate conversion
        public double scale;
        public double offsetX;
        public double offsetY;
    }
    
    public static NetworkData loadNetwork(String netXmlPath) throws Exception {
        NetworkData data = new NetworkData();
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(netXmlPath));
        doc.getDocumentElement().normalize();
        
        // First pass: find bounds
        NodeList junctions = doc.getElementsByTagName("junction");
        double minX = Double.MAX_VALUE, maxX = -Double.MAX_VALUE;
        double minY = Double.MAX_VALUE, maxY = -Double.MAX_VALUE;
        
        for (int i = 0; i < junctions.getLength(); i++) {
            Element junction = (Element) junctions.item(i);
            String type = junction.getAttribute("type");
            if (type.equals("internal")) continue;
            
            double x = Double.parseDouble(junction.getAttribute("x"));
            double y = Double.parseDouble(junction.getAttribute("y"));
            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
        }
        
        // Calculate scale and offset to fit window (1400x850 with margins)
        double sumoWidth = maxX - minX;
        double sumoHeight = maxY - minY;
        
        double scaleX = (1400 - 300) / sumoWidth;  // Leave 300px margin for controls
        double scaleY = (850 - 100) / sumoHeight;  // Leave 100px margin top/bottom
        double scale = Math.min(scaleX, scaleY) * 0.9; // 90% to add padding
        
        // Center the map on screen
        double centerX = 1400 / 2.0;
        double centerY = 850 / 2.0;
        double sumoCenterX = (minX + maxX) / 2.0;
        double sumoCenterY = (minY + maxY) / 2.0;
        
        double offsetX = centerX - sumoCenterX * scale;
        double offsetY = centerY + sumoCenterY * scale; // Y is inverted
        
        System.out.println("SUMO bounds: x[" + minX + ", " + maxX + "], y[" + minY + ", " + maxY + "]");
        System.out.println("Scale: " + scale + ", Offset: (" + offsetX + ", " + offsetY + ")");
        
        // Store transformation parameters in data
        data.scale = scale;
        data.offsetX = offsetX;
        data.offsetY = offsetY;
        
        for (int i = 0; i < junctions.getLength(); i++) {
            Element junction = (Element) junctions.item(i);
            String id = junction.getAttribute("id");
            String type = junction.getAttribute("type");
            
            // Skip internal junctions
            if (type.equals("internal")) continue;
            
            double x = Double.parseDouble(junction.getAttribute("x"));
            double y = Double.parseDouble(junction.getAttribute("y"));
            
            // Convert SUMO coordinates to screen coordinates
            int screenX = (int)(x * scale + offsetX);
            int screenY = (int)(-y * scale + offsetY); // Invert Y axis
            
            Node node = new Node(screenX, screenY);
            data.nodes.add(node);
            data.nodeMap.put(id, node);
            
            // Add traffic light if junction has traffic light
            if (type.equals("traffic_light")) {
                TrafficLight tl = new TrafficLight(screenX, screenY);
                tl.id = id; // Store SUMO junction ID
                data.trafficLights.add(tl);
            }
        }
        
        // Load edges (roads)
        NodeList edges = doc.getElementsByTagName("edge");
        for (int i = 0; i < edges.getLength(); i++) {
            Element edge = (Element) edges.item(i);
            String id = edge.getAttribute("id");
            
            // Skip internal edges
            if (id.startsWith(":")) continue;
            
            String from = edge.getAttribute("from");
            String to = edge.getAttribute("to");
            
            Node fromNode = data.nodeMap.get(from);
            Node toNode = data.nodeMap.get(to);
            
            if (fromNode != null && toNode != null) {
                data.roads.add(new Road(fromNode, toNode));
            }
        }
        
        System.out.println("Loaded SUMO network:");
        System.out.println("  - " + data.nodes.size() + " nodes");
        System.out.println("  - " + data.roads.size() + " roads");
        System.out.println("  - " + data.trafficLights.size() + " traffic lights");
        
        return data;
    }
}
