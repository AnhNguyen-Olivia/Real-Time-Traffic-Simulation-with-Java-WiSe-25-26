package wrapper;

import it.polito.appeal.traci.SumoTraciConnection;
import java.util.ArrayList;
import java.util.List;


public class LaneManagerWrapper {
    private final SumoTraciConnection connection;
    
    public LaneManagerWrapper(SumoTraciConnection connection) {
        this.connection = connection;
    }

    public LaneWrapper getLane(String id) {
        return new LaneWrapper(id, connection);
    }

    @SuppressWarnings("unchecked")
    public List<String> getAllLaneIds() throws Exception {
        return (List<String>) connection.do_job_get(
            de.tudresden.sumo.cmd.Lane.getIDList());
    }

    public List<LaneWrapper> getAllLanes() throws Exception {
        List<String> laneIds = getAllLaneIds();
        List<LaneWrapper> lanes = new ArrayList<>();
        
        for (String laneId : laneIds) {
            lanes.add(getLane(laneId));
        }
        
        return lanes;
    }

    public List<LaneWrapper> getCongestedLanes(double speedThreshold) throws Exception {
        if (speedThreshold < 0) {
            throw new IllegalArgumentException("Speed threshold must be non-negative");
        }
        
        List<LaneWrapper> congestedLanes = new ArrayList<>();
        List<String> laneIds = getAllLaneIds();
        
        for (String laneId : laneIds) {
            LaneWrapper lane = getLane(laneId);
            if (lane.isCongested(speedThreshold)) {
                congestedLanes.add(lane);
            }
        }
        
        return congestedLanes;
    }

    public List<LaneWrapper> getBusyLanes(int vehicleCountThreshold) throws Exception {
        if (vehicleCountThreshold < 0) {
            throw new IllegalArgumentException("Vehicle count threshold must be non-negative");
        }
        
        List<LaneWrapper> busyLanes = new ArrayList<>();
        List<String> laneIds = getAllLaneIds();
        
        for (String laneId : laneIds) {
            LaneWrapper lane = getLane(laneId);
            if (lane.getVehicleCount() >= vehicleCountThreshold) {
                busyLanes.add(lane);
            }
        }
        
        return busyLanes;
    }

    public List<LaneWrapper> getHighOccupancyLanes(double occupancyThreshold) throws Exception {
        if (occupancyThreshold < 0 || occupancyThreshold > 1) {
            throw new IllegalArgumentException("Occupancy threshold must be between 0 and 1");
        }
        
        List<LaneWrapper> highOccupancyLanes = new ArrayList<>();
        List<String> laneIds = getAllLaneIds();
        
        for (String laneId : laneIds) {
            LaneWrapper lane = getLane(laneId);
            if (lane.getOccupancy() >= occupancyThreshold) {
                highOccupancyLanes.add(lane);
            }
        }
        
        return highOccupancyLanes;
    }
}
