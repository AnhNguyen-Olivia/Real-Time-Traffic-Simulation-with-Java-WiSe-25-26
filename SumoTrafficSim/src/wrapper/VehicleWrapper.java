package wrapper;

import it.polito.appeal.traci.SumoTraciConnection;
import de.tudresden.sumo.cmd.Vehicle;

public class VehicleWrapper {
    private String id;
    private SumoTraciConnection conn;

    public VehicleWrapper(String id, SumoTraciConnection conn){
        this.id = id;
        this.connection = connection;
    }

    public double getSpeed() throws Exception {
        return (double) connection.do_job_get(Vehicle.getSpeed(id));
    }

    public void setSpeed(double speed) throws Exception {
        if (speed < 0) {
            throw new IllegalArgumentException("Speed cannot be negative");
        }
        connection.do_job_set(Vehicle.setSpeed(id, speed));
    }

    public double[] getPosition() throws Exception {
        return (double[]) connection.do_job_get(Vehicle.getPosition(id));
    }

    public String getRoadId() throws Exception {
        return (String) connection.do_job_get(Vehicle.getRoadID(id));
    }

    public String getRouteId() throws Exception {
        return (String) connection.do_job_get(Vehicle.getRouteID(id));
    }

    public void setRouteId(String routeId) throws Exception {
        if (routeId == null || routeId.isEmpty()) {
            throw new IllegalArgumentException("Route ID cannot be null or empty");
        }
        connection.do_job_set(Vehicle.setRouteID(id, routeId));
    }

    public String getTypeId() throws Exception {
        return (String) connection.do_job_get(Vehicle.getTypeID(id));
    }

    public String getLaneId() throws Exception {
        return (String) connection.do_job_get(Vehicle.getLaneID(id));
    }
    
    public int[] getColor() throws Exception {
        return (int[]) connection.do_job_get(Vehicle.getColor(id));
    }

    public void setColor(int red, int green, int blue, int alpha) throws Exception {
        if (red < 0 || red > 255 || green < 0 || green > 255 || 
            blue < 0 || blue > 255 || alpha < 0 || alpha > 255) {
            throw new IllegalArgumentException("Color values must be between 0 and 255");
        }
        connection.do_job_set(Vehicle.setColor(id, red, green, blue, alpha));
    }

    public double getAngle() throws Exception {
        return (double) connection.do_job_get(Vehicle.getAngle(id));
    }

    public void changeTarget(String edgeId) throws Exception {
        if (edgeId == null || edgeId.isEmpty()) {
            throw new IllegalArgumentException("Edge ID cannot be null or empty");
        }
        connection.do_job_set(Vehicle.changeTarget(id, edgeId));
    }

    public String getId() {
        return id;
    }

    public String toString() {
        return "Vehicle[id=" + id + "]";
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vehicle)) return false;
        Vehicle other = (Vehicle) obj;
        return id.equals(other.id);
    }

    public int hashCode() {
        return id.hashCode();
    }
}
