package wrapper;
import it.polito.appeal.traci.SumoTraciConnection;

public class vehicleManagerWrapper{
    private SumoTraciConnection conn;

    public vehicleManagerWrapper(SumoTraciConnection conn){
        this.conn = conn;
    }

    public VehicleWrapper get(String id){
        return new VehicleWrapper(id, conn);
    }
}