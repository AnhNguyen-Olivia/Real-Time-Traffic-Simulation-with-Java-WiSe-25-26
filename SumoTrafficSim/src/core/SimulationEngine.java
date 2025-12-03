package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationEngine {

    public double simTime = 0;
    private boolean running = false;
    private boolean usingSumoTraCI = false;

    public final List<Vehicle> vehicles = new ArrayList<>();
    public final List<TrafficLight> trafficLights = new ArrayList<>();
    public List<Node> nodes = new ArrayList<>();
    public List<Road> roads = new ArrayList<>();
    
    // TraCI connector for real SUMO simulation
    private SumoTraCIConnector sumoConnector;
    private Map<String, TrafficLight> trafficLightMap = new HashMap<>();
    
    // Coordinate transformation (loaded from SumoNetworkLoader)
    private double scale = 1.0;
    private double offsetX = 0;
    private double offsetY = 0;
    
    public List<Vehicle> getVehicles() { return vehicles; }
    public List<TrafficLight> getTrafficLights() { return trafficLights; }
    
    public List<Intersection> intersections = new ArrayList<>();

    public SimulationEngine() {
    	// Empty initialization - will connect to SUMO via TraCI
    }
    
    // Load static network topology from SUMO XML
    public void loadFromSumo(String netXmlPath) throws Exception {
        nodes.clear();
        roads.clear();
        trafficLights.clear();
        vehicles.clear();
        intersections.clear();
        
        SumoNetworkLoader.NetworkData data = SumoNetworkLoader.loadNetwork(netXmlPath);
        
        nodes.addAll(data.nodes);
        roads.addAll(data.roads);
        trafficLights.addAll(data.trafficLights);
        
        // Store transformation parameters for vehicle position sync
        this.scale = data.scale;
        this.offsetX = data.offsetX;
        this.offsetY = data.offsetY;
        
        // Build traffic light map for quick lookup
        trafficLightMap.clear();
        for (TrafficLight tl : trafficLights) {
            trafficLightMap.put(tl.id, tl);
        }
        
        System.out.println("SUMO network loaded: " + nodes.size() + " nodes, " + 
                           roads.size() + " roads, " + trafficLights.size() + " traffic lights");
    }
    
    // Connect to SUMO via TraCI for real-time simulation
    public void connectToSumo(String sumoExe, String configFile) throws Exception {
        if (sumoConnector != null && sumoConnector.isConnected()) {
            System.out.println("Already connected to SUMO");
            return;
        }
        
        sumoConnector = new SumoTraCIConnector();
        sumoConnector.connect(sumoExe, configFile);
        usingSumoTraCI = true;
        
        System.out.println("Connected to SUMO via TraCI!");
    }
    
    public void start() { running = true; }
    public void stop() { running = false; }
    
    // Add vehicle manually (for user interaction)
    public void addVehicle() {
        if (usingSumoTraCI && sumoConnector != null && sumoConnector.isConnected()) {
            // Add vehicle to SUMO simulation
            try {
                // Generate unique vehicle ID
                String vehID = "veh_" + System.currentTimeMillis();
                
                // Get available routes from SUMO
                List<String> routes = sumoConnector.getRouteIDs();
                if (routes.isEmpty()) {
                    // No routes defined, try to get edges and create a simple route
                    List<String> edges = sumoConnector.getEdgeIDs();
                    if (edges.isEmpty()) {
                        System.err.println("No routes or edges available in SUMO network");
                        return;
                    }
                    // Use first edge as route (simplified)
                    String edgeID = edges.get((int)(Math.random() * edges.size()));
                    System.out.println("Using edge as route: " + edgeID);
                    sumoConnector.addVehicleToSumoWithRandomColor(vehID, edgeID);
                } else {
                    // Pick random route
                    String routeID = routes.get((int)(Math.random() * routes.size()));
                    System.out.println("Adding vehicle to route: " + routeID);
                    sumoConnector.addVehicleToSumoWithRandomColor(vehID, routeID);
                }
                
                System.out.println("✓ Vehicle added to SUMO: " + vehID);
            } catch (Exception e) {
                System.err.println("Error adding vehicle to SUMO: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            // Add vehicle to internal simulation (manual mode)
            if (roads.isEmpty()) return;

            int idx = (int)(Math.random() * roads.size());
            java.awt.Color c = new java.awt.Color(
                (float)Math.random(), 
                (float)Math.random(), 
                (float)Math.random()
            );

            vehicles.add(new Vehicle(idx, 120, c));
        }
    }

    // Main update loop - sync with SUMO if connected, otherwise use internal simulation
    public void update() {
        if (!running) return;
        
        if (usingSumoTraCI && sumoConnector != null && sumoConnector.isConnected()) {
            try {
                // Advance SUMO simulation by one step
                sumoConnector.doSimulationStep();
                simTime = sumoConnector.getCurrentTime();
                
                // Sync vehicles from SUMO
                syncVehiclesFromSumo();
                
                // Sync traffic lights from SUMO
                syncTrafficLightsFromSumo();
                
            } catch (Exception e) {
                System.err.println("Error syncing with SUMO: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            // Fallback: internal simulation (for manual vehicle control)
            double dt = 0.02;
            simTime += dt;
            updateVehiclesInternal(dt);
            updateLightsInternal(dt);
        }
    }
    
    // Internal vehicle update (when not using SUMO)
    private void updateVehiclesInternal(double dt) {
        for (Vehicle v : vehicles) {
            if (v.roadIndex < 0 || v.roadIndex >= roads.size()) continue;
            
            Road r = roads.get(v.roadIndex);
            double L = r.length();

            // Check for traffic light at the end
            TrafficLight tl = getTrafficLightNearRoadEnd(r);
            boolean mustStop = false;

            if (tl != null && tl.state.equals("R")) {
                double distanceToEnd = (1 - v.t) * L;
                if (distanceToEnd < v.stopDistance) {
                    mustStop = true;
                }
            }

            // Move vehicle
            if (!mustStop) {
                v.t += (v.speed * dt) / L;
            }

            // Vehicle reaches end of road
            if (v.t >= 1.0) {
                Road next = getNextRoad(r.end, r);

                if (next != null) {
                    v.roadIndex = roads.indexOf(next);
                    v.t = 0;
                    r = next;
                    L = r.length();
                } else {
                    v.t = 1;
                }
            }
            v.x = r.start.x + (r.end.x - r.start.x) * v.t;
            v.y = r.start.y + (r.end.y - r.start.y) * v.t;
        }
    }
    
    // Internal traffic light update (when not using SUMO)
    private void updateLightsInternal(double dt) {
        for (TrafficLight t : trafficLights) {
            t.update(dt);
        }
    }
    
    // Helper methods
    private TrafficLight getTrafficLightNearRoadEnd(Road r) {
        Node end = r.end;
        for (TrafficLight t : trafficLights) {
            if (Math.hypot(t.x - end.x, t.y - end.y) < 40) {
                return t;
            }
        }
        return null;
    }
    
    private Road getNextRoad(Node currentNode, Road currentRoad) {
        for (Road r : roads) {
            if (r != currentRoad && r.start == currentNode) {
                return r;
            }
        }
        return null;
    }
    
    // Sync vehicle positions and colors from SUMO via TraCI
    private void syncVehiclesFromSumo() throws Exception {
        List<String> vehicleIDs = sumoConnector.getVehicleIDs();
        vehicles.clear();
        
        for (String vehID : vehicleIDs) {
            double[] pos = sumoConnector.getVehiclePosition(vehID);
            
            // Transform SUMO coordinates to screen coordinates
            double screenX = pos[0] * scale + offsetX;
            double screenY = offsetY - pos[1] * scale; // Y-axis inverted
            
            // Create vehicle color (use safe default if color fetch fails)
            java.awt.Color color;
            try {
                double[] colorRGB = sumoConnector.getVehicleColor(vehID);
                // Clamp values to 0-255 range and convert to 0.0-1.0
                float r = Math.max(0, Math.min(255, (float)colorRGB[0])) / 255.0f;
                float g = Math.max(0, Math.min(255, (float)colorRGB[1])) / 255.0f;
                float b = Math.max(0, Math.min(255, (float)colorRGB[2])) / 255.0f;
                color = new java.awt.Color(r, g, b);
            } catch (Exception e) {
                // Use random color if color fetch fails
                color = new java.awt.Color(
                    (float)Math.random(), 
                    (float)Math.random(), 
                    (float)Math.random()
                );
            }
            
            Vehicle v = new Vehicle(-1, 0, color);
            v.x = screenX;
            v.y = screenY;
            v.id = vehID;
            
            vehicles.add(v);
        }
    }
    
    // Sync traffic light states from SUMO via TraCI
    private void syncTrafficLightsFromSumo() throws Exception {
        List<String> tlIDs = sumoConnector.getTrafficLightIDs();
        
        for (String tlID : tlIDs) {
            String state = sumoConnector.getTrafficLightState(tlID);
            TrafficLight tl = trafficLightMap.get(tlID);
            
            if (tl != null && state != null && !state.isEmpty()) {
                // SUMO uses 'r'=red, 'g'=green, 'y'=yellow, 'G'=green_priority
                char firstSignal = state.charAt(0);
                if (firstSignal == 'r' || firstSignal == 'R') {
                    tl.state = "R";
                } else if (firstSignal == 'g' || firstSignal == 'G') {
                    tl.state = "G";
                } else if (firstSignal == 'y' || firstSignal == 'Y') {
                    tl.state = "Y"; // Yellow state
                }
            }
        }
    }
    
    // Utility to find node near coordinates (for UI interactions)
    public Node getClosestNode(int x, int y) {
        Node closest = null;
        double minDist = 20;

        for (Node n : nodes) {
            double d = Math.hypot(n.x - x, n.y - y);
            if (d < minDist) {
                minDist = d;
                closest = n;
            }
        }
        return closest;
    }
    
    // Clean up SUMO connection
    public void disconnect() {
        if (sumoConnector != null) {
            try {
                sumoConnector.close();
                usingSumoTraCI = false;
                System.out.println("Disconnected from SUMO");
            } catch (Exception e) {
                System.err.println("Error disconnecting: " + e.getMessage());
            }
        }
    }
}


