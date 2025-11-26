package wrapper;

import it.polito.appeal.traci.SumoTraciConnection;
import de.tudresden.sumo.cmd.Lane;
import java.util.List;

public class Lane {
    private final String id;
    private final SumoTraciConnection connection;
    
    public Lane(String id, SumoTraciConnection connection) {
        this.id = id;
        this.connection = connection;
    }

    public int getVehicleCount() throws Exception {
        return (int) connection.do_job_get(Lane.getLastStepVehicleNumber(id));
    }

    public double getAverageSpeed() throws Exception {
        return (double) connection.do_job_get(Lane.getLastStepMeanSpeed(id));
    }

    public double getLength() throws Exception {
        return (double) connection.do_job_get(Lane.getLength(id));
    }
    
    public List<String> getVehicleIds() throws Exception {
        return (List<String>) connection.do_job_get(Lane.getLastStepVehicleIDs(id));
    }

    public double getMaxSpeed() throws Exception {
        return (double) connection.do_job_get(Lane.getMaxSpeed(id));
    }

    public double getWidth() throws Exception {
        return (double) connection.do_job_get(Lane.getWidth(id));
    }

    public String getEdgeId() throws Exception {
        return (String) connection.do_job_get(Lane.getEdgeID(id));
    }
    
    public int getHaltingVehicleCount() throws Exception {
        return (int) connection.do_job_get(Lane.getLastStepHaltingNumber(id));
    }

    public double getOccupancy() throws Exception {
        return (double) connection.do_job_get(Lane.getLastStepOccupancy(id));
    }

    public boolean isCongested(double speedThreshold) throws Exception {
        return getAverageSpeed() < speedThreshold;
    }

    public String getId() {
        return id;
    }

    public String toString() {
        return "Lane[id=" + id + "]";
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Lane)) return false;
        Lane other = (Lane) obj;
        return id.equals(other.id);
    }

    public int hashCode() {
        return id.hashCode();
    }
}
