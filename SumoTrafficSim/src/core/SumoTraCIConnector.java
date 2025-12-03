package core;

import it.polito.appeal.traci.SumoTraciConnection;

import java.util.*;

/**
 * Connector for SUMO via TraCI API
 * Simplified version - uses direct TraCI calls
 */
public class SumoTraCIConnector {
    
    private SumoTraciConnection conn;
    private boolean connected = false;
    
    /**
     * Connect to SUMO via TraCI
     */
    public void connect(String sumoExe, String configFile) throws Exception {
        conn = new SumoTraciConnection(sumoExe, configFile);
        conn.addOption("start", "true");
        conn.addOption("step-length", "0.1"); // 100ms per step
        
        conn.runServer();
        connected = true;
        
        System.out.println("✓ Connected to SUMO via TraCI");
    }
    
    /**
     * Advance simulation by one step
     */
    public void doSimulationStep() throws Exception {
        if (!connected) return;
        conn.do_timestep();
    }
    
    /**
     * Get current simulation time
     */
    public double getCurrentTime() throws Exception {
        if (!connected) return 0;
        Object time = conn.do_job_get(de.tudresden.sumo.cmd.Simulation.getTime());
        if (time instanceof Integer) {
            return ((Integer) time).doubleValue() / 1000.0; // Convert ms to seconds
        } else if (time instanceof Double) {
            return (Double) time;
        }
        return 0;
    }
    
    /**
     * Get all vehicle IDs currently in simulation
     */
    @SuppressWarnings("unchecked")
    public List<String> getVehicleIDs() throws Exception {
        if (!connected) return new ArrayList<>();
        
        try {
            Object result = conn.do_job_get(de.tudresden.sumo.cmd.Vehicle.getIDList());
            if (result instanceof Collection<?>) {
                return new ArrayList<>((Collection<String>) result);
            }
        } catch (Exception e) {
            System.err.println("Warning: Could not get vehicle IDs: " + e.getMessage());
        }
        return new ArrayList<>();
    }
    
    /**
     * Get vehicle position (x, y)
     */
    public double[] getVehiclePosition(String vehicleID) throws Exception {
        if (!connected) return new double[]{0, 0};
        
        try {
            Object pos = conn.do_job_get(de.tudresden.sumo.cmd.Vehicle.getPosition(vehicleID));
            // Try to extract x, y from position object using reflection
            if (pos != null && pos.getClass().getName().contains("Position")) {
                java.lang.reflect.Field xField = pos.getClass().getField("x");
                java.lang.reflect.Field yField = pos.getClass().getField("y");
                double x = ((Number) xField.get(pos)).doubleValue();
                double y = ((Number) yField.get(pos)).doubleValue();
                return new double[]{x, y};
            }
        } catch (Exception e) {
            System.err.println("Warning: Could not get vehicle position for " + vehicleID + ": " + e.getMessage());
        }
        return new double[]{0, 0};
    }
    
    /**
     * Get vehicle color (RGB as array [r, g, b, a])
     */
    public double[] getVehicleColor(String vehicleID) throws Exception {
        if (!connected) return new double[]{0, 0, 255, 255}; // Blue default
        
        try {
            Object color = conn.do_job_get(de.tudresden.sumo.cmd.Vehicle.getColor(vehicleID));
            if (color != null && color.getClass().getName().contains("Color")) {
                java.lang.reflect.Field rField = color.getClass().getField("r");
                java.lang.reflect.Field gField = color.getClass().getField("g");
                java.lang.reflect.Field bField = color.getClass().getField("b");
                java.lang.reflect.Field aField = color.getClass().getField("a");
                
                double r = ((Number) rField.get(color)).doubleValue();
                double g = ((Number) gField.get(color)).doubleValue();
                double b = ((Number) bField.get(color)).doubleValue();
                double a = ((Number) aField.get(color)).doubleValue();
                
                return new double[]{r, g, b, a};
            }
        } catch (Exception e) {
            // Ignore color errors, use default
        }
        
        // Default colors based on vehicle ID hash
        int hash = vehicleID.hashCode();
        int r = (hash & 0xFF0000) >> 16;
        int g = (hash & 0x00FF00) >> 8;
        int b = (hash & 0x0000FF);
        return new double[]{r, g, b, 255};
    }
    
    /**
     * Get all traffic light IDs
     */
    @SuppressWarnings("unchecked")
    public List<String> getTrafficLightIDs() throws Exception {
        if (!connected) return new ArrayList<>();
        
        try {
            Object result = conn.do_job_get(de.tudresden.sumo.cmd.Trafficlight.getIDList());
            if (result instanceof Collection<?>) {
                return new ArrayList<>((Collection<String>) result);
            }
        } catch (Exception e) {
            System.err.println("Warning: Could not get traffic light IDs: " + e.getMessage());
        }
        return new ArrayList<>();
    }
    
    /**
     * Get traffic light state (e.g., "GrGr" - red/green for each lane)
     */
    public String getTrafficLightState(String tlID) throws Exception {
        if (!connected) return "";
        
        try {
            Object state = conn.do_job_get(de.tudresden.sumo.cmd.Trafficlight.getRedYellowGreenState(tlID));
            if (state != null) {
                return state.toString();
            }
        } catch (Exception e) {
            System.err.println("Warning: Could not get traffic light state for " + tlID + ": " + e.getMessage());
        }
        return "";
    }
    
    /**
     * Add a vehicle to SUMO simulation dynamically
     * @param vehicleID Unique ID for the vehicle
     * @param routeID Route ID from SUMO network (e.g., "route0")
     * @param typeID Vehicle type (e.g., "DEFAULT_VEHTYPE" or custom type)
     * @param departTime Departure time in seconds (-1 for immediate/now, 0 for current time)
     */
    public void addVehicleToSumo(String vehicleID, String routeID, String typeID, int departTime) throws Exception {
        if (!connected) {
            System.err.println("Not connected to SUMO. Cannot add vehicle.");
            return;
        }
        
        try {
            // Add vehicle to SUMO using TraCI
            conn.do_job_set(de.tudresden.sumo.cmd.Vehicle.add(vehicleID, typeID, routeID, departTime, 0.0, 0.0, (byte)0));
            System.out.println("✓ Added vehicle '" + vehicleID + "' to SUMO on route '" + routeID + "'");
        } catch (Exception e) {
            System.err.println("Error adding vehicle to SUMO: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Add a vehicle with random color to SUMO simulation
     */
    public void addVehicleToSumoWithRandomColor(String vehicleID, String routeID) throws Exception {
        addVehicleToSumo(vehicleID, routeID, "DEFAULT_VEHTYPE", -1); // -1 = now
        
        // Set random color
        try {
            int r = (int)(Math.random() * 255);
            int g = (int)(Math.random() * 255);
            int b = (int)(Math.random() * 255);
            
            conn.do_job_set(de.tudresden.sumo.cmd.Vehicle.setColor(vehicleID, 
                new de.tudresden.sumo.objects.SumoColor(r, g, b, 255)));
        } catch (Exception e) {
            System.err.println("Warning: Could not set vehicle color: " + e.getMessage());
        }
    }
    
    /**
     * Get list of available routes in SUMO network
     */
    @SuppressWarnings("unchecked")
    public List<String> getRouteIDs() throws Exception {
        if (!connected) return new ArrayList<>();
        
        try {
            Object result = conn.do_job_get(de.tudresden.sumo.cmd.Route.getIDList());
            if (result instanceof Collection<?>) {
                return new ArrayList<>((Collection<String>) result);
            }
        } catch (Exception e) {
            System.err.println("Warning: Could not get route IDs: " + e.getMessage());
        }
        return new ArrayList<>();
    }
    
    /**
     * Get list of available edges in SUMO network
     */
    @SuppressWarnings("unchecked")
    public List<String> getEdgeIDs() throws Exception {
        if (!connected) return new ArrayList<>();
        
        try {
            Object result = conn.do_job_get(de.tudresden.sumo.cmd.Edge.getIDList());
            if (result instanceof Collection<?>) {
                return new ArrayList<>((Collection<String>) result);
            }
        } catch (Exception e) {
            System.err.println("Warning: Could not get edge IDs: " + e.getMessage());
        }
        return new ArrayList<>();
    }
    
    /**
     * Check if connected
     */
    public boolean isConnected() {
        return connected;
    }
    
    /**
     * Close connection
     */
    public void close() throws Exception {
        if (conn != null) {
            conn.close();
            connected = false;
        }
    }
}
