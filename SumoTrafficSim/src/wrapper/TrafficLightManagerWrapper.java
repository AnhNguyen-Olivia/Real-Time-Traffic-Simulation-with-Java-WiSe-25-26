package wrapper;

import it.polito.appeal.traci.SumoTraciConnection;
import java.util.ArrayList;
import java.util.List;

public class TrafficLightManagerWrapper {
    private final SumoTraciConnection connection;

    public TrafficLightManagerWrapper(SumoTraciConnection connection) {
        this.connection = connection;
    }

    /**
     * Returns a wrapper for a specific traffic light ID.
     */
    public TrafficLightWrapper getTrafficLight(String id) {
        return new TrafficLightWrapper(id, connection);
    }

    /**
     * Returns all traffic light IDs from SUMO via TraCI.
     */
    @SuppressWarnings("unchecked")
    public List<String> getAllTrafficLightIds() throws Exception {
        return (List<String>) connection.do_job_get(
            de.tudresden.sumo.cmd.Trafficlight.getIDList());
    }

    /**
     * Returns TrafficLightWrapper instances for all traffic lights.
     */
    public List<TrafficLightWrapper> getAllTrafficLights() throws Exception {
        List<String> trafficLightIds = getAllTrafficLightIds();
        List<TrafficLightWrapper> trafficLights = new ArrayList<>();
        
        for (String tlId : trafficLightIds) {
            trafficLights.add(getTrafficLight(tlId));
        }
        
        return trafficLights;
    }

    /**
     * Sets the phase for all traffic lights to the given index.
     */
    public void setAllTrafficLightsPhase(int phaseIndex) throws Exception {
        if (phaseIndex < 0) {
            throw new IllegalArgumentException("Phase index cannot be negative");
        }       
        List<String> trafficLightIds = getAllTrafficLightIds();
        for (String tlId : trafficLightIds) {
            TrafficLightWrapper tl = getTrafficLight(tlId);
            tl.setPhase(phaseIndex);
        }
    }
}