import it.polito.appeal.traci.SumoTraciConnection;
import de.tudresden.sumo.cmd.Simulation;
import de.tudresden.sumo.cmd.Vehicle;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        int remotePort = 8813;  // Must match SUMO --remote-port
        SumoTraciConnection conn = null;

        try {
            conn = new SumoTraciConnection(remotePort);

            // Connect to SUMO (throws IOException)
            conn.runServer();
            System.out.println("Connected to SUMO!");

            for (int i = 0; i < 100000; i++) {

                Thread.sleep(200); // slow motion

                conn.do_timestep();

                double simTime = (double) conn.do_job_get(Simulation.getTime());
                System.out.println("Step " + i + " | Simulation time: " + simTime);

                @SuppressWarnings("unchecked")
                List<String> vehicleIds = (List<String>) conn.do_job_get(Vehicle.getIDList());

                System.out.println("Vehicles: " + vehicleIds);
            }

        } catch (IOException e) {
            System.err.println("Could not connect to SUMO. Is SUMO running?");
            e.printStackTrace();

        } catch (InterruptedException e) {
            System.err.println("Thread interrupted (sleep).");
            e.printStackTrace();

        } catch (Exception e) {
            System.err.println("Unexpected error:");
            e.printStackTrace();

        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("Disconnected from SUMO.");
                }
            } catch (Exception closeEx) {
                System.err.println("Warning: could not close SUMO connection.");
            }
        }
    }
}
