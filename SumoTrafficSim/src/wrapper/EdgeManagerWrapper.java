package wrapper;

import it.polito.appeal.traci.SumoTraciConnection;
import java.util.ArrayList;
import java.util.List;


public class EdgeManagerWrapper {
    private final SumoTraciConnection connection;
    
    public EdgeManagerWrapper(SumoTraciConnection connection) {
        this.connection = connection;
    }

    public EdgeWrapper getEdge(String id) {
        return new EdgeWrapper(id, connection);
    }

    @SuppressWarnings("unchecked")
    public List<String> getAllEdgeIds() throws Exception {
        return (List<String>) connection.do_job_get(
            de.tudresden.sumo.cmd.Edge.getIDList());
    }

    public List<EdgeWrapper> getAllEdges() throws Exception {
        List<String> edgeIds = getAllEdgeIds();
        List<EdgeWrapper> edges = new ArrayList<>();
        
        for (String edgeId : edgeIds) {
            edges.add(getEdge(edgeId));
        }
        
        return edges;
    }

    public List<EdgeWrapper> getCongestedEdges(double speedThreshold) throws Exception {
        if (speedThreshold < 0) {
            throw new IllegalArgumentException("Speed threshold must be non-negative");
        }
        
        List<EdgeWrapper> congestedEdges = new ArrayList<>();
        List<String> edgeIds = getAllEdgeIds();
        
        for (String edgeId : edgeIds) {
            EdgeWrapper edge = getEdge(edgeId);
            if (edge.isCongested(speedThreshold)) {
                congestedEdges.add(edge);
            }
        }
        
        return congestedEdges;
    }

    public List<EdgeWrapper> getBusyEdges(int vehicleCountThreshold) throws Exception {
        if (vehicleCountThreshold < 0) {
            throw new IllegalArgumentException("Vehicle count threshold must be non-negative");
        }
        
        List<EdgeWrapper> busyEdges = new ArrayList<>();
        List<String> edgeIds = getAllEdgeIds();
        
        for (String edgeId : edgeIds) {
            EdgeWrapper edge = getEdge(edgeId);
            if (edge.getVehicleCount() >= vehicleCountThreshold) {
                busyEdges.add(edge);
            }
        }
        
        return busyEdges;
    }
}
