public class edge {
    private String id;
    private SumoTraciConnection conn;
    
    public edge(String id, SumoTraciConnection conn) {
        this.id = id;
        this.conn = conn;
    }
    
    public int getVehicleCount() throws Exception {         //Gets the number of vehicles currently on this edge
        return (int) conn.do_job_get(de.tudresden.sumo.cmd.Edge.getLastStepVehicleNumber(id));
    }
    

    public double getAverageSpeed() throws Exception {     //Gets average speed of vehicles on this edge
        return (double) conn.do_job_get(de.tudresden.sumo.cmd.Edge.getLastStepMeanSpeed(id));
    }
    
    public double getLength() throws Exception {        //Gets the length of this edge in meters
        return (double) conn.do_job_get(de.tudresden.sumo.cmd.Edge.getLength(id));
    }
    
    public java.util.ArrayList<String> getVehicleIds() throws Exception {   //Gets list of vehicle IDs currently on this edge
        return (java.util.ArrayList<String>) conn.do_job_get(
            de.tudresden.sumo.cmd.Edge.getLastStepVehicleIDs(id));
    }
    
    public double getMaxSpeed() throws Exception {  //Gets maximum allowed speed on this edge
        return (double) conn.do_job_get(de.tudresden.sumo.cmd.Edge.getMaxSpeed(id));
    }
       
    public String getId() {     //Gets the edge ID
        return id;
    }
}
