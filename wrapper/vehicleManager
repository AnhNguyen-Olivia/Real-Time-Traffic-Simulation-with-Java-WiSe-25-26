package wrapper;
import it.polito.appeal.traci.SumoTraciConnection;

public class vehicleManager{
    private SumoTraciConnection conn;

    public vehicleManager(SumoTraciConnection conn){
        this.conn = conn;
    }

    public vehicle get(String id){
        return new vehicle(id, conn);
    }
}