package core;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.awt.Color;

public class SimulationEngine {

    public double simTime = 0;
    private boolean running = false;

    public double scale = 1.0;
    public int offsetX = 0;
    public int offsetY = 0;

    public List <Node> nodes = new ArrayList<>();
    public List<Road> roads = new ArrayList<>();
    public List<TrafficLight> trafficLights = new ArrayList<>();
    public List<Vehicle> vehicles = new ArrayList<>();

    // -------------------------------------------------------
    // CONSTRUCTOR
    // -------------------------------------------------------
    public SimulationEngine() {}

    // -------------------------------------------------------
    // LOAD SUMO NETWORK
    // -------------------------------------------------------
    public void loadSumoMap(String netPath) {
        try {
            SumoNetworkLoader loader = new SumoNetworkLoader(scale, offsetX, offsetY);
            loader.load(netPath);

            nodes = new ArrayList<>(loader.nodes.values());
            roads = loader.roads;

            trafficLights.clear();
            for (String jId : loader.tlJunctionIds) {
                Node n = loader.nodes.get(jId);
                if (n != null)
                    trafficLights.add(new TrafficLight(n.x, n.y));
            }

            System.out.println("✔ SUMO map imported: " +
                    nodes.size() + " nodes, " +
                    roads.size() + " roads, " +
                    trafficLights.size() + " traffic lights");

        } catch (Exception e) {
            System.err.println("❌ Error loading SUMO map: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------
    // SIM CONTROL
    // -------------------------------------------------------
    public void start() { running = true; }
    public void stop() { running = false; }

    // -------------------------------------------------------
    // VEHICLE SPAWNING
    // -------------------------------------------------------
    public void addVehicle() {

        // Choose any road with at least one lane
        List<Road> valid = new ArrayList<>();
        for (Road r : roads)
            if (!r.lanes.isEmpty())
                valid.add(r);

        if (valid.isEmpty()) return;

        Road chosen = valid.get(new Random().nextInt(valid.size()));

        int roadIndex = roads.indexOf(chosen);

        Vehicle v = new Vehicle(roadIndex, 150, randomColor());
        v.t = 0;
        vehicles.add(v);
    }

    private Color randomColor() {
        return new Color(
                (float)Math.random(),
                (float)Math.random(),
                (float)Math.random()
        );
    }

    // -------------------------------------------------------
    // MAIN UPDATE LOOP
    // -------------------------------------------------------
    public void update() {
        if (!running) return;

        double dt = 0.02;

        simTime += dt;
        updateLights(dt);
        updateVehicles(dt);
    }

    private void updateLights(double dt) {
        for (TrafficLight tl : trafficLights)
            tl.update(dt);
    }

    // -------------------------------------------------------
    // VEHICLE MOTION: FOLLOW LANE POLYLINE
    // -------------------------------------------------------
    public void updateVehicles(double dt) {
        for (Vehicle v : vehicles) {

            Road r = roads.get(v.roadIndex);

            if (r.lanes.isEmpty())
                continue;

            Lane lane = r.lanes.get(0); // simple case: use first lane
            double length = lane.length;

            // Traffic light logic
            boolean mustStop = false;
            TrafficLight tl = getTrafficLightNearLaneEnd(lane);

            if (tl != null && tl.state.equals("R")) {
                double distToEnd = (1 - v.t) * length;
                if (distToEnd < v.stopDistance)
                    mustStop = true;
            }

            if (!mustStop)
                v.t += (v.speed * dt) / length;

            if (v.t >= 1.0) {
                Road next = findNextRoad(r);
                if (next != null) {
                    v.roadIndex = roads.indexOf(next);
                    v.t = 0;
                    lane = next.lanes.get(0);
                    length = lane.length;
                } else {
                    v.t = 1;
                }
            }

            // Compute lane coordinates
            int[] xs = lane.xs;
            int[] ys = lane.ys;

            int segmentCount = xs.length - 1;

            double dist = v.t * length;
            double covered = 0;

            for (int i = 0; i < segmentCount; i++) {

                double segLen = Math.hypot(xs[i+1] - xs[i], ys[i+1] - ys[i]);

                if (covered + segLen >= dist) {
                    double tSeg = (dist - covered) / segLen;

                    v.x = xs[i] + (xs[i+1] - xs[i]) * tSeg;
                    v.y = ys[i] + (ys[i+1] - ys[i]) * tSeg;

                    break;
                }

                covered += segLen;
            }
        }
    }

    // -------------------------------------------------------
    // TRAFFIC LIGHTS & NEXT ROAD LOGIC
    // -------------------------------------------------------
    public TrafficLight getTrafficLightNearLaneEnd(Lane lane) {
        int lx = lane.xs[lane.xs.length - 1];
        int ly = lane.ys[lane.ys.length - 1];

        for (TrafficLight tl : trafficLights) {
            if (Math.hypot(tl.x - lx, tl.y - ly) < 35)
                return tl;
        }
        return null;
    }

    public Road findNextRoad(Road current) {
        Node end = current.end;
        for (Road r : roads)
            if (r.start == end && r != current)
                return r;
        return null;
    }

    // -------------------------------------------------------
    // HELPERS
    // -------------------------------------------------------
    public Node getClosestNode(int x, int y) {
        Node best = null;
        double min = 20;

        for (Node n : nodes) {
            double d = Math.hypot(n.x - x, n.y - y);
            if (d < min) {
                min = d;
                best = n;
            }
        }
        return best;
    }
}