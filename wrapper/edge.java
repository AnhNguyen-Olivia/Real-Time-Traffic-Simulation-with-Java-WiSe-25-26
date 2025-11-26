package wrapper;

import it.polito.appeal.traci.SumoTraciConnection;
import de.tudresden.sumo.cmd.Edge;
import java.util.List;

public class Edge {
    private final String id;
    private final SumoTraciConnection connection;
    
    public Edge(String id, SumoTraciConnection connection) {
        this.id = id;
        this.connection = connection;
    }
    public int getVehicleCount() throws Exception {
        return (int) connection.do_job_get(Edge.getLastStepVehicleNumber(id));
    }
    public double getAverageSpeed() throws Exception {
        return (double) connection.do_job_get(Edge.getLastStepMeanSpeed(id));
    }
    public double getLength() throws Exception {
        return (double) connection.do_job_get(Edge.getLength(id));
    }

    public List<String> getVehicleIds() throws Exception {
        return (List<String>) connection.do_job_get(Edge.getLastStepVehicleIDs(id));
    }

    public double getMaxSpeed() throws Exception {
        return (double) connection.do_job_get(Edge.getMaxSpeed(id));
    }

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
        if (!(obj instanceof Edge)) return false;
        Edge other = (Edge) obj;
        return id.equals(other.id);
    }

    public int hashCode() {
        return id.hashCode();
    }
}
