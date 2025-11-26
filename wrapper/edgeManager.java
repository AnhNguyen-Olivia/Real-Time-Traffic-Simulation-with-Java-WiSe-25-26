package wrapper;
import it.polito.appeal.traci.SumoTraciConnection;

public class edgeManager {
    private SumoTraciConnection conn;
    
    public edgeManager(SumoTraciConnection conn) {
        this.conn = conn;
    }
    public edge get(String id) {        //Gets an edge wrapper for the specified edge ID
        return new edge(id, conn);
    }    

    public java.util.ArrayList<String> getAllEdgeIds() throws Exception {       //Gets all edge IDs in the network
        return (java.util.ArrayList<String>) conn.do_job_get(
            de.tudresden.sumo.cmd.Edge.getIDList());
    }   
    public java.util.ArrayList<edge> getCongestedEdges(double speedThreshold) throws Exception {       //Finds edges with average speed below threshold (congested edges)
        java.util.ArrayList<edge> congestedEdges = new java.util.ArrayList<>();     
        java.util.ArrayList<String> edgeIds = getAllEdgeIds();
        
        for (String edgeId : edgeIds) {
            edge e = get(edgeId);
            if (e.isCongested(speedThreshold)) {
                congestedEdges.add(e);
            }
        }
        
        return congestedEdges;
    }
}