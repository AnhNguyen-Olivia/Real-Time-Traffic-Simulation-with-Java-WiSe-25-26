package wrapper;
import it.polito.appeal.traci.SumoTraciConnection;
import java.util.List;

import de.tudresden.sumo.cmd.Edge;

public class EdgeWrapper {
    private final String id;
    private final SumoTraciConnection connection;
    
    public EdgeWrapper(String id, SumoTraciConnection connection) {
        this.id = id;
        this.connection = connection;
    }
    public int getVehicleCount() throws Exception {
        return (int) connection.do_job_get(Edge.getLastStepVehicleNumber(id));
    }
    public double getAverageSpeed() throws Exception {
        return (double) connection.do_job_get(Edge.getLastStepMeanSpeed(id));
    }
    // public double getLength() throws Exception {
    //     return (double) connection.do_job_get(Edge.getLength(id));
    // }

    @SuppressWarnings("unchecked")
    public List<String> getVehicleIds() throws Exception {
        return (List<String>) connection.do_job_get(Edge.getLastStepVehicleIDs(id));
    }

    // public double getMaxSpeed() throws Exception {
    //     return (double) connection.do_job_get(Edge.getMaxSpeed(id));
    // }

    public boolean isCongested(double speedThreshold) throws Exception {
        return getAverageSpeed() < speedThreshold;
    }

    public String getId() {
        return id;
    }

    public String toString() {
        return "Edge[id=" + id + "]";
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof EdgeWrapper)) return false;
        EdgeWrapper other = (EdgeWrapper) obj;
        return id.equals(other.id);
    }

    public int hashCode() {
        return id.hashCode();
    }
}
