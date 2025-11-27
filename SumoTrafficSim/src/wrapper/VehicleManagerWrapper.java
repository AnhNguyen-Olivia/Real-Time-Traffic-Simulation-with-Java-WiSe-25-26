package wrapper;
import java.util.ArrayList;
import java.util.List;
import it.polito.appeal.traci.SumoTraciConnection;

public class VehicleManagerWrapper{
    private SumoTraciConnection conn;

    public VehicleManagerWrapper(SumoTraciConnection conn){
        this.conn = conn;
    }

    public VehicleWrapper get(String id){
        return new VehicleWrapper(id, conn);
    }

    @SuppressWarnings("unchecked")
    public List<String> getAllVehicleIds() throws Exception {
        return (List<String>) conn.do_job_get(
            de.tudresden.sumo.cmd.Vehicle.getIDList());
    }

    public List<VehicleWrapper> getAllVehicle() throws Exception {
        List<String> vehicleIds = getAllVehicleIds();
        List<VehicleWrapper> vehicles = new ArrayList<>();
        
        for (String vehicleId : vehicleIds) {
            vehicles.add(get(vehicleId));
        }
        
        return vehicles;
    }
}