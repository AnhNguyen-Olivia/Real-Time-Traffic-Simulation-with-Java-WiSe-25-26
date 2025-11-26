package wrapper;

import it.polito.appeal.traci.SumoTraciConnection;
import de.tudresden.sumo.cmd.Route;
import java.util.List;

public class Route {
    private final String id;
    private final SumoTraciConnection connection;
    
    public Route(String id, SumoTraciConnection connection) {
        this.id = id;
        this.connection = connection;
    }

    public List<String> getEdges() throws Exception {
        return (List<String>) connection.do_job_get(Route.getEdges(id));
    }

    public void setEdges(List<String> edgeIds) throws Exception {
        if (edgeIds == null || edgeIds.isEmpty()) {
            throw new IllegalArgumentException("Edge list cannot be null or empty");
        }
        connection.do_job_set(Route.setEdges(id, edgeIds));
    }

    public String getId() {
        return id;
    }

    public int getEdgeCount() throws Exception {
        return getEdges().size();
    }

    public boolean containsEdge(String edgeId) throws Exception {
        return getEdges().contains(edgeId);
    }

    public String toString() {
        return "Route[id=" + id + "]";
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Route)) return false;
        Route other = (Route) obj;
        return id.equals(other.id);
    }

    public int hashCode() {
        return id.hashCode();
    }
}
