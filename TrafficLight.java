package core.model;

import java.util.Map;
import java.util.logging.Logger;

public class TrafficLight {

    private static final Logger logger = Logger.getLogger(TrafficLight.class.getName());

    private String id;
    private int currentPhase;
    private Map<Integer, Integer> phaseDurations; // <phaseIndex, durationSec>

    public TrafficLight(String id) {
        this.id = id;
    }

    public void update(int phase, Map<Integer, Integer> durations) {
        this.currentPhase = phase;
        this.phaseDurations = durations;
        logger.fine("Updated TL " + id + " (phase=" + phase + ")");
    }

    public String getId() {
        return id;
    }

    public int getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(int currentPhase) {
        this.currentPhase = currentPhase;
    }

    public Map<Integer, Integer> getPhaseDurations() {
        return phaseDurations;
    }

    @Override
    public String toString() {
        return "TrafficLight{id='" + id + "', phase=" + currentPhase + "}";
    }
}
