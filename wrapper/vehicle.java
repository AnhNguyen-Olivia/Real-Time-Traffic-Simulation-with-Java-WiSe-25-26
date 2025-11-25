package wrapper;

import it.polito.appeal.traci.SumoTraciConnection;

public class vehicle {
    private String id;
    private SumoTraciConnection conn;

    public vehicle(String id, SumoTraciConnection conn){
        this.id = id;
        this.conn = conn;
    }

    public double getSpeed() throws Exception{
        return (double) conn.do_job_get(de.tudresden.sumo.cmd.Vehicle.getSpeed(id));
    };

    public double setSpeed(double speed) throws Exception{
        conn.do_job_set(de.tudresden.sumo.cmd.Vehicle.setSpeed(id,speed));
        return speed;
    }
}
