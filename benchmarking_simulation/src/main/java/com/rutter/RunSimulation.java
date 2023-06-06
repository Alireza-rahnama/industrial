package com.rutter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import static com.rutter.ConfigurationPage.SIMULATION_RADARS_CONFIGURATION;
import static com.rutter.ConfigurationPage.SIMULATION_CONSUMERS_CONFIGURATION;



public class RunSimulation {
	
	public RunSimulation() {
		loadSimulationRadarsCatalog();
		loadSimulationConsumersCatalog();
		}
	
    public void loadSimulationRadarsCatalog() {
    	File file = new File(SIMULATION_RADARS_CONFIGURATION);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
            	String radarType = scanner.nextLine();
                int simulationPeriod = Integer.parseInt(scanner.nextLine());
                int radarQuantity = Integer.parseInt(scanner.nextLine());
                
                for(int i=0; i<radarQuantity; i++) {
                	System.out.println(radarType);
                	//we instantiate the radars here	
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void loadSimulationConsumersCatalog() {
    	File file = new File(SIMULATION_CONSUMERS_CONFIGURATION);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
            	String consumerType = scanner.nextLine();
                int ConsumerQuantity = Integer.parseInt(scanner.nextLine());
                
                for(int i=0; i<ConsumerQuantity; i++) {
                	System.out.println(consumerType);
                	//we instantiate the clients here
                	
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
