package wrapper;

import it.polito.appeal.traci.SumoTraciConnection;
import de.tudresden.sumo.cmd.Trafficlight;

public class TrafficLightWrapper {
    private final String id;
    private final SumoTraciConnection connection;

    public TrafficLightWrapper(String id, SumoTraciConnection connection) {
        this.id = id;
        this.connection = connection;
    }

    public String getState() throws Exception {
        return (String) connection.do_job_get(Trafficlight.getRedYellowGreenState(id));
    }

    public void setState(String state) throws Exception {
        if (state == null || state.isEmpty()) {
            throw new IllegalArgumentException("State cannot be null or empty");
        }
        connection.do_job_set(Trafficlight.setRedYellowGreenState(id, state));
    }

    public int getPhase() throws Exception {
        return (int) connection.do_job_get(Trafficlight.getPhase(id));
    }

    public void setPhase(int phaseIndex) throws Exception {
        if (phaseIndex < 0) {
            throw new IllegalArgumentException("Phase index cannot be negative");
        }
        connection.do_job_set(Trafficlight.setPhase(id, phaseIndex));
    }

    public double getPhaseDuration() throws Exception {
        return (double) connection.do_job_get(Trafficlight.getPhaseDuration(id));
    }

    public void setPhaseDuration(double duration) throws Exception {
        if (duration < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }
        connection.do_job_set(Trafficlight.setPhaseDuration(id, duration));
    }

    public String getProgram() throws Exception {
        return (String) connection.do_job_get(Trafficlight.getProgram(id));
    }

    public void setProgram(String programId) throws Exception {
        if (programId == null || programId.isEmpty()) {
            throw new IllegalArgumentException("Program ID cannot be null or empty");
        }
        connection.do_job_set(Trafficlight.setProgram(id, programId));
    }

    public String getId() {
        return id;
    }

    public String toString() {
        return "trafficlight[id=" + id + "]";
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TrafficLightWrapper)) return false;
        TrafficLightWrapper other = (TrafficLightWrapper) obj;
        return id.equals(other.id);
    }

    public int hashCode() {
        return id.hashCode();
    }
}
