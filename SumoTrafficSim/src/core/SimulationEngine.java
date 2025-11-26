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
    	// Four corners of the cross
        Node left  = new Node(200, 300);
        Node right = new Node(900, 300);
        Node top   = new Node(550, 100);
        Node bottom= new Node(550, 580);

        // Add them
        nodes.add(left);
        nodes.add(right);
        nodes.add(top);
        nodes.add(bottom);

        // Horizontal road
        roads.add(new Road(left, right));

        // Vertical road
        roads.add(new Road(top, bottom));

//        // Add intersection traffic lights
//        trafficLights.add(new TrafficLight(550, 300));
    	// One traffic light at intersection
    	trafficLights.add(new TrafficLight(600, 350));
    }

    public void start() { running = true; }
    public void stop() { running = false; }

    public void addVehicle() {
        if (roads.isEmpty()) return;

        int idx = (int)(Math.random() * roads.size());
        Color c = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());

        vehicles.add(new Vehicle(idx, 2.5, c));
    }
    
    public void updateVehicles() {
        for (Vehicle v : vehicles) {

            if (v.roadIndex < 0 || v.roadIndex >= roads.size())
                continue;

            Road r = roads.get(v.roadIndex);
            double L = r.length();

            // Move along the road
            v.t += v.speed / L;

            // End of road → stop for now
            if (v.t > 1) {
                v.t = 1;
            }

            // Compute actual coordinates ON the road (not below it)
            v.x = r.start.x + (r.end.x - r.start.x) * v.t;
            v.y = r.start.y + (r.end.y - r.start.y) * v.t;
        }
    }

    public void toggleLights() {
        for (TrafficLight t : trafficLights) {
            t.state = t.state.equals("R") ? "G" : "R";
        }
    }

    public void update() {
        if (!running) return;

        simTime += 0.02;

        updateVehicles();
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
    

    

}


