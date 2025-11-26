package test;

import de.tudresden.sumo.cmd.Simulation;
import it.polito.appeal.traci.SumoTraciConnection;
import wrapper.*;

public class SumoConnectionTest {

    public static void main(String[] args) throws Exception {

        // Path to SUMO binary (GUI recommended for testing)
        String sumoBinary = "SumoConfig/sumo.exe";

        // Path to your SUMO config file
        String configFile = "SumoConfig/testing.sumocfg";

        // Create TraCI connection
        SumoTraciConnection conn = new SumoTraciConnection(sumoBinary, configFile);
        conn.addOption("start", "true");
        conn.addOption("quit-on-end", "true");

        System.out.println("Starting SUMO...");
        conn.runServer();

        System.out.println("Connected to SUMO successfully!");

        //Use the wrapper
        TrafficLightManagerWrapper trafficlightManager = new TrafficLightManagerWrapper(conn);
        VehicleManagerWrapper vehicleManager = new VehicleManagerWrapper(conn);

        // Run simulation steps
        for (int step = 0; step < 1000; step++) {
            conn.do_timestep();

            double simulationTime = (Double) conn.do_job_get(Simulation.getTime());
            System.out.println("Step: " + step + " | simulation time = " + simulationTime);

            // print out the traffic light 
            for(var trafficLight: trafficlightManager.getAllTrafficLights()){
                System.out.println(" Traffic Light: " + trafficLight.getId() + " -> state = " + trafficLight.getState());
            }

            // print out the vehicles
            for(var vehicles : vehicleManager.getAllVehicle()){
                System.out.println(" Vehicle " + vehicles.getId() + " -> position = " + vehicles.getPosition() + ", speed = " + vehicles.getSpeed());
            }

            Thread.sleep(500);
            System.out.println();
        }

        conn.close();
        System.out.println("Simulation finished and SUMO closed.");

    }
}
