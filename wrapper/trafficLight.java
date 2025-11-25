package wrapper;
import it.polito.appeal.traci.SumoTraciConnection;
public class trafficLight {
    private String id;
    private SumoTraciConnection conn;

    public trafficLight(String id, SumoTraciConnection conn){
        this.id = id;
        this.conn = conn;
    }

    public String getState() throws Exception{
        return (String) conn.do_job_get(de.tudresden.sumo.cmd.Trafficlight.getRedYellowGreenState(id));
    }
}
