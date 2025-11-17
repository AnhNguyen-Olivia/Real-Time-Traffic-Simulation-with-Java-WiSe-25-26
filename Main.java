import it.polito.appeal.traci.SumoTraciConnection;
import de.tudresden.sumo.cmd.Simulation;

public class Main {
    public static void main(String[] args) throws Exception {

        // SUMO binary — no spaces in path!
        String sumoBinary = "E:/SUMO/bin/sumo.exe";

        // SUMO config
        String sumoConfig = "E:/Real-Time-Traffic-Simulation-with-Java-WiSe-25-26/testing.sumocfg";

        // TraaS connection
        SumoTraciConnection conn = new SumoTraciConnection(sumoBinary, sumoConfig, "8813");

        // Auto start
        conn.addOption("start", "true");

        // Launch SUMO
        conn.runServer();
        System.out.println("Connected to SUMO!");

        // Run 10 simulation steps
        for (int i = 0; i < 10; i++) {
            conn.do_timestep();
            double t = (double) conn.do_job_get(Simulation.getTime());
            System.out.println("Simulation time: " + t);
        }

        conn.close();
        System.out.println("Disconnected.");
    }
}
