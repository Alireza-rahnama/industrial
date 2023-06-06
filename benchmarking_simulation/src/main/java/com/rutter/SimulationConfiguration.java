package com.rutter;

import java.util.ArrayList;
import java.util.HashMap;

public class SimulationConfiguration {
	private int simulationPeriod;
	//this integer in these maps are the number of each radar and client instances
    private ArrayList<HashMap<RadarStation, Integer>> radarsSimulationConfigurationCatalog;
    private ArrayList<HashMap<ConsumerClient, Integer>> consumerClientSimulationConfigurationCatalog;
    
    private HashMap<RadarStation, Integer> RadarQuantityMap;
    private HashMap<ConsumerClient, Integer> consumerQuantityMap;
    
    
	public SimulationConfiguration(int simulationPeriod,
			ArrayList<HashMap<RadarStation, Integer>> radarsSimulationConfigurationCatalog,
			ArrayList<HashMap<ConsumerClient, Integer>> consumerClientSimulationConfigurationCatalog) {

		this.simulationPeriod = simulationPeriod;
		this.radarsSimulationConfigurationCatalog = radarsSimulationConfigurationCatalog;
		this.consumerClientSimulationConfigurationCatalog = consumerClientSimulationConfigurationCatalog;
	}
	
	public int getSimulationPeriod() {
		return simulationPeriod;
	}
	
	public void setSimulationPeriod(int simulationPeriod) {
		this.simulationPeriod = simulationPeriod;
	}
	
	public ArrayList<HashMap<RadarStation, Integer>> getRadarsSimulationConfigurationCatalog() {
		return radarsSimulationConfigurationCatalog;
	}
	
	public void setRadarsSimulationConfigurationCatalog(
			ArrayList<HashMap<RadarStation, Integer>> radarsSimulationConfigurationCatalog) {
		this.radarsSimulationConfigurationCatalog = radarsSimulationConfigurationCatalog;
	}
	
	public ArrayList<HashMap<ConsumerClient, Integer>> getConsumerClientSimulationConfigurationCatalog() {
		return consumerClientSimulationConfigurationCatalog;
	}
	
	public void setConsumerClientSimulationConfigurationCatalog(
			ArrayList<HashMap<ConsumerClient, Integer>> consumerClientSimulationConfigurationCatalog) {
		this.consumerClientSimulationConfigurationCatalog = consumerClientSimulationConfigurationCatalog;
	}

	@Override
	public String toString() {
		return "SimulationConfiguration [simulationPeriod=" + simulationPeriod
				+ ", radarsSimulationConfigurationCatalog=" + radarsSimulationConfigurationCatalog
				+ ", consumerClientSimulationConfigurationCatalog=" + consumerClientSimulationConfigurationCatalog
				+ "]";
	}
    
    
}
