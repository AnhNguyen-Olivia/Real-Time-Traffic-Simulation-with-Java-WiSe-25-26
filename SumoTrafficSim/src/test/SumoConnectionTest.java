package test;

import it.polito.appeal.traci.SumoTraciConnection;

public class SumoConnectionTest {

    public static void main(String[] args) throws Exception {

        // Path to SUMO binary (GUI recommended for testing)
        String sumoBinary = "E:/Sumo/bin/sumo.exe";

        // Path to your SUMO config file
        String configFile = "E:/Real-Time-Traffic-Simulation-with-Java-WiSe-25-26/SumoConfig/testing.sumocfg";

        // Create TraCI connection
        SumoTraciConnection conn = new SumoTraciConnection(sumoBinary, configFile);
        conn.addOption("start", "true");
        conn.addOption("quit-on-end", "true");

        System.out.println("Starting SUMO...");
        conn.runServer();

        System.out.println("Connected to SUMO successfully!");

        // Run simulation steps
        for (int step = 0; step < 1000; step++) {
            conn.do_timestep();
            System.out.println("Step: " + step);
            Thread.sleep(200);
        }

        conn.close();
        System.out.println("Simulation finished and SUMO closed.");

    }
}
