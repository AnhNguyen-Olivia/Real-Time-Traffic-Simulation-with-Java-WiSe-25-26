package core;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SimulationEngine {

    public double simTime = 0;
    private boolean running = false;

    public final List<Vehicle> vehicles = new ArrayList<>();
    public final List<TrafficLight> trafficLights = new ArrayList<>();
    public List<Node> nodes = new ArrayList<>();
    public List<Road> roads = new ArrayList<>();
    
    public List<Vehicle> getVehicles() { return vehicles; }
    public List<TrafficLight> getTrafficLights() { return trafficLights; }
    
    public List<Intersection> intersections = new ArrayList<>();

    public SimulationEngine() {
    	// Four corners
        Node left   = new Node(200, 300);
        Node right  = new Node(900, 300);
        Node top    = new Node(550, 100);
        Node bottom = new Node(550, 580);

        // REAL intersection
        Node center = new Node(550, 300);

        // Must add ALL nodes including center ❗
        nodes.add(left);
        nodes.add(right);
        nodes.add(top);
        nodes.add(bottom);
//        nodes.add(center);

        // Build connected roads
        roads.add(new Road(left, center));
        roads.add(new Road(center, right));
        roads.add(new Road(top, center));
        roads.add(new Road(center, bottom));

        // Add traffic light at intersection ❗
//        trafficLights.add(new TrafficLight(center.x, center.y));
        trafficLights.add(new TrafficLight(center.x + 25, center.y + 25));
        trafficLights.add(new TrafficLight(center.x - 25, center.y - 25));
        trafficLights.add(new TrafficLight(center.x + 25, center.y - 25));
        trafficLights.add(new TrafficLight(center.x - 25, center.y + 25));
 
//    	    // Place traffic lights on four corners around the intersection center
//    	    trafficLights.add(new TrafficLight(cx - offset, cy - offset)); // top-left corner
//    	    trafficLights.add(new TrafficLight(cx + offset, cy - offset)); // top-right corner
//    	    trafficLights.add(new TrafficLight(cx - offset, cy + offset)); // bottom-left corner
//    	    trafficLights.add(new TrafficLight(cx + offset, cy + offset)); // bottom-right corner
    }

    public void start() { running = true; }
    public void stop() { running = false; }

    public void addVehicle() {

        List<Road> entryRoads = getEntryRoads();
        if (entryRoads.isEmpty()) return;

        Road r = entryRoads.get((int)(Math.random() * entryRoads.size()));

        // Get index
        int roadIndex = roads.indexOf(r);

        // Create a vehicle at the START of that road
        Color c = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
        Vehicle v = new Vehicle(roadIndex, 120, c);

        v.t = 0;  // force start at start of road

        vehicles.add(v);
    }

    public void updateVehicles(double dt) {
        for (Vehicle v : vehicles) {

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
                    r = next;   // update the road reference
                    L = r.length();
                } else {
                    v.t = 1;
                }
            }
            v.x = r.start.x + (r.end.x - r.start.x) * v.t;
            v.y = r.start.y + (r.end.y - r.start.y) * v.t;
        }
    }
    
    public TrafficLight getTrafficLightAtNode(Node n) {
        for (TrafficLight t : trafficLights) {
            if (Math.hypot(t.x - n.x, t.y - n.y) < 25)
                return t;
        }
        return null;
    }
    
    public Road getNextRoad(Node currentNode, Road currentRoad) {
        for (Road r : roads) {
            if (r != currentRoad && r.start == currentNode) {
                return r;  // outgoing road from intersection
            }
        }
        return null; // dead end
    }
    
    public TrafficLight getTrafficLightNearRoadEnd(Road r) {
        Node end = r.end;
        for (TrafficLight t : trafficLights) {
            if (Math.hypot(t.x - end.x, t.y - end.y) < 40) {
                return t;
            }
        }
        return null;
    }

    public void toggleLights() {
        for (TrafficLight t : trafficLights) {
            t.state = t.state.equals("R") ? "G" : "R";
        }
    }

    public void update() {
        if (!running) return;

        double dt = 0.02;    // simulation timestep
        simTime += dt;

        updateLights(dt);
        updateVehicles(dt);
    }
    
    public Node getClosestNode(int x, int y) {
        Node closest = null;
        double minDist = 20; // snap distance

        for (Node n : nodes) {
            double d = Math.hypot(n.x - x, n.y - y);
            if (d < minDist) {
                minDist = d;
                closest = n;
            }
        }

        return closest;
    }
    
    private void updateLights(double dt) {
        for (TrafficLight t : trafficLights) {
            t.update(dt);
        }
    }
    
    public List<Road> getEntryRoads() {
        List<Road> entry = new ArrayList<>();

        for (Road r : roads) {
            // Center node is always (550, 300)
            if (!(r.start.x == 550 && r.start.y == 300)) {
                entry.add(r);   // Only add roads that start outside the intersection
            }
        }
        return entry;
    }

}


