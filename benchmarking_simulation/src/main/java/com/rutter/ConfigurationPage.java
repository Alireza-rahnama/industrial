package com.rutter;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map.Entry;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.FileWriter;
import java.io.IOException;

import static com.rutter.RadarCatalog.RADAR_CATALOG_FILE;
import static com.rutter.ConsumerClientCatalog.CONSUMER_CATALOG_FILE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class ConfigurationPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel radarCatalogPanel = new JPanel();
	private JPanel consumerClientCatalogPanel = new JPanel();
	private JPanel simulationPanel = new JPanel();

	private JLabel radarQuantityLabel;
	private JLabel radarTypeLabel;
	private JLabel dataTransmitionPeriodLabel;

	private JLabel consumerClientQuantityLabel;
	private JLabel consumerClientTypeLabel;

	private JLabel simualtionPeriodLabel;

	private JLabel[] radarLabels;
	private JCheckBox[] radarCheckBoxes;
	private JTextField[] radarQuantityFields;
	private JTextField[] dataTransmitionIntervalFields;
	private JTextField simulationtionPeriodField;

	private JCheckBox[] consumerClientCheckBoxes;
	private JTextField[] consumerClientQuantityFields;

	private ArrayList<JCheckBox> radarCheckboxes = new ArrayList<JCheckBox>();
	private ArrayList<String> selectedRadars = new ArrayList<String>();
	private ArrayList<JCheckBox> consumerCheckboxes = new ArrayList<JCheckBox>();
	private ArrayList<Integer> selectedRadarsIndexList = new ArrayList<Integer>();
	private ArrayList<RadarStation> radarList = new ArrayList<RadarStation>();
	private ArrayList<ConsumerClient> consumerList = new ArrayList<ConsumerClient>();

	private ArrayList<RadarStation> radarStations;
	private ArrayList<String> selectedConsumers = new ArrayList<String>();
	private ArrayList<Integer> selectedConsumerClientIndexList = new ArrayList<Integer>();

	private ArrayList<HashMap<RadarStation, Integer>> radarsSimulationConfigurationCatalog = new ArrayList<HashMap<RadarStation, Integer>>();
	private ArrayList<HashMap<ConsumerClient, Integer>> consumerClientSimulationConfigurationCatalog = new ArrayList<HashMap<ConsumerClient, Integer>>();
	private ArrayList<SimulationConfiguration> simulationConfigurationCatalog;

	private StartUpPage startUpPage;

	private static long configId;

	private SimulationConfiguration simulationConfiguration;
	public static final String SIMULATION_RADARS_CONFIGURATION= "simulationRadarsCatalog.txt";
	public static final String SIMULATION_CONSUMERS_CONFIGURATION= "simulationConsumersCatalog.txt";


	public ConfigurationPage(StartUpPage startUpPage) {
		setTitle("Radar Configuration");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		add(radarCatalogPanel);
		add(consumerClientCatalogPanel);
		add(simulationPanel);

		Dimension windowSize = new Dimension(800, 600);
		setPreferredSize(windowSize);

		radarCatalogPanel.setLayout(new GridLayout(0, 3));

		radarQuantityLabel = new JLabel("Select Radar Type");
		radarCatalogPanel.add(radarQuantityLabel);

		radarQuantityLabel = new JLabel("Enter Radar Quantities");
		radarCatalogPanel.add(radarQuantityLabel);

		dataTransmitionPeriodLabel = new JLabel("Enter Data Transmition Interval");
		radarCatalogPanel.add(dataTransmitionPeriodLabel);

		simulationPanel.setLayout(new GridLayout(0, 1));

		simualtionPeriodLabel = new JLabel("Enter Simulation Length in Minutes");
		simulationPanel.add(simualtionPeriodLabel);

		simulationtionPeriodField = new JTextField();
		simulationPanel.add(simulationtionPeriodField);

		ArrayList<RadarStation> radars = loadRadarStations();
		radarStations = radars;
		int radarCatalogSize = radarStations.size();

		radarLabels = new JLabel[radarCatalogSize];
		radarQuantityFields = new JTextField[radarCatalogSize];
		dataTransmitionIntervalFields = new JTextField[radarCatalogSize];
		radarCheckBoxes = new JCheckBox[radarCatalogSize];

		for (int i = 0; i < radarStations.size(); i++) {
			String checkBoxLabel = (radarStations.get(i).getType()).toString();
			radarCheckBoxes[i] = new JCheckBox(checkBoxLabel);
			radarCatalogPanel.add(radarCheckBoxes[i]);
			radarCheckboxes.add(radarCheckBoxes[i]);

			radarQuantityFields[i] = new JTextField();
			radarCatalogPanel.add(radarQuantityFields[i]);

			dataTransmitionIntervalFields[i] = new JTextField();
			radarCatalogPanel.add(dataTransmitionIntervalFields[i]);
		}

		consumerClientCatalogPanel.setLayout(new GridLayout(0, 2));

		consumerClientTypeLabel = new JLabel("Select Consumer Client Type");
		consumerClientCatalogPanel.add(consumerClientTypeLabel);

		consumerClientQuantityLabel = new JLabel("Enter Consumer Client Quantities");
		consumerClientCatalogPanel.add(consumerClientQuantityLabel);

		ArrayList<ConsumerClient> consumerClientsCatalog = loadConsumerClients();

		int consumerClientCatalogSize = consumerClientsCatalog.size();

		consumerClientQuantityFields = new JTextField[consumerClientCatalogSize];
		consumerClientCheckBoxes = new JCheckBox[consumerClientCatalogSize];

		for (int i = 0; i < consumerClientsCatalog.size(); i++) {
			String checkBoxLabel = (consumerClientsCatalog.get(i).getType()).toString();
			consumerClientCheckBoxes[i] = new JCheckBox(checkBoxLabel);
			consumerClientCatalogPanel.add(consumerClientCheckBoxes[i]);
			consumerCheckboxes.add(consumerClientCheckBoxes[i]);

			consumerClientQuantityFields[i] = new JTextField();
			consumerClientCatalogPanel.add(consumerClientQuantityFields[i]);
		}

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(saveConfiguration());
		consumerClientCatalogPanel.add(saveButton);

		simulationPanel.add(saveButton);

		JButton repaintButton = new JButton("Return to Start Up Page");
		repaintButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				startUpPage.setVisible(true);
			}
		});

		simulationPanel.add(repaintButton);
		add(simulationPanel);
	}

	public ActionListener saveConfiguration() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getSelectedRadarsTypes();
				getRadarsPropertiesForSimulationCatalog();

				getSelectedConsumerClientTypes();
				getConsumerClientsPropertiesForSimulationCatalog();

				for (HashMap<RadarStation, Integer> map : radarsSimulationConfigurationCatalog) {
					for (Entry<RadarStation, Integer> entry : map.entrySet()) {

						String radarType = entry.getKey().getType();
						int RadarDataTransmitionInterval = entry.getKey().getDataTransmitionInterval();
						Integer radarQuantity = entry.getValue();

						System.out.println("Radar Type: " + radarType + ", Radar's Data Transmition Interval: "
								+ RadarDataTransmitionInterval + "minutes, Radar Quantity: " + radarQuantity);

						try (PrintWriter out = new PrintWriter(
								new FileWriter(SIMULATION_RADARS_CONFIGURATION, true))) {

							out.println(radarType);
							out.println(RadarDataTransmitionInterval);
							out.println(radarQuantity);

						} catch (IOException err) {
							err.printStackTrace();
						}
					}
				}

				for (HashMap<ConsumerClient, Integer> map : consumerClientSimulationConfigurationCatalog) {

					for (Entry<ConsumerClient, Integer> entry : map.entrySet()) {

						String clientType = entry.getKey().getType();
						Integer clientQuantity = entry.getValue();

						System.out.println("Client Type: " + clientType + ", Client Quantity: " + clientQuantity);

						try (PrintWriter out = new PrintWriter(
								new FileWriter(SIMULATION_CONSUMERS_CONFIGURATION, true))) {

							out.println(clientType);
							out.println(clientQuantity);

						} catch (IOException er) {
							er.printStackTrace();
						}
					}
				}

				System.out.println(getSimulationPeriod() + " minutes");

//				simulationConfiguration = new SimulationConfiguration(getSimulationPeriod(),
//						radarsSimulationConfigurationCatalog, consumerClientSimulationConfigurationCatalog);
//
//				saveToSimulationCatalog(simulationConfiguration);
//
//				System.out.println(simulationConfiguration.toString());

			}
		};
	}

	public ArrayList<String> getSelectedRadars() {
		return selectedRadars;
	}

	public void getSelectedRadarsTypes() {
		for (JCheckBox checkBox : radarCheckboxes) {
			if (checkBox.isSelected()) {
				selectedRadars.add(checkBox.getText());
				selectedRadarsIndexList.add(radarCheckboxes.indexOf(checkBox));
			}
		}
	}

	private ArrayList<RadarStation> loadRadarStations() {
		File file = new File(RADAR_CATALOG_FILE);
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String radarType = scanner.nextLine();
				radarList.add(new RadarStation(radarType));
			}
		} catch (FileNotFoundException e) {
			// if File does not exist, do nothing.
		}
		return radarList;
	}

	public ArrayList<ConsumerClient> loadConsumerClients() {
		File file = new File(CONSUMER_CATALOG_FILE);
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String consumerClientType = scanner.nextLine();
				consumerList.add(new ConsumerClient(consumerClientType));
			}
		} catch (FileNotFoundException e) {
			// File does not exist yet, do nothing.
		}
		return consumerList;
	}

	public void getRadarsPropertiesForSimulationCatalog() {
		for (int i = 0; i < selectedRadars.size(); i++) {
			RadarStation radar = new RadarStation(selectedRadars.get(i));
			Integer transmitionInterval = Integer
					.parseInt(dataTransmitionIntervalFields[selectedRadarsIndexList.get(i)].getText());
			radar.setDataTransmitionInterval(transmitionInterval);
			int quantity = Integer.parseInt(radarQuantityFields[selectedRadarsIndexList.get(i)].getText());
			HashMap<RadarStation, Integer> selectedRadarQuantityMap = new HashMap<>();
			selectedRadarQuantityMap.put(radar, quantity);
			radarsSimulationConfigurationCatalog.add(selectedRadarQuantityMap);
		}
	}

	public void getSelectedConsumerClientTypes() {
		for (JCheckBox checkBox : consumerCheckboxes) {
			if (checkBox.isSelected()) {
				selectedConsumers.add(checkBox.getText());
				selectedConsumerClientIndexList.add(consumerCheckboxes.indexOf(checkBox));
			}
		}
	}

	public void getConsumerClientsPropertiesForSimulationCatalog() {
		for (int i = 0; i < selectedConsumers.size(); i++) {
			ConsumerClient consumerClient = new ConsumerClient(selectedConsumers.get(i));
			int quantity = Integer
					.parseInt(consumerClientQuantityFields[selectedConsumerClientIndexList.get(i)].getText());
			HashMap<ConsumerClient, Integer> selectedConsumerQuantityMap = new HashMap<>();
			selectedConsumerQuantityMap.put(consumerClient, quantity);
			consumerClientSimulationConfigurationCatalog.add(selectedConsumerQuantityMap);
		}
	}

	public int getSimulationPeriod() {
		try {
			return Integer.parseInt(simulationtionPeriodField.getText());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public void saveToSimulationCatalog(SimulationConfiguration simulationConfiguration) {
		try (PrintWriter out = new PrintWriter(new FileWriter(SIMULATION_RADARS_CONFIGURATION, true))) {
			out.println(simulationConfiguration.getSimulationPeriod());
			out.println(simulationConfiguration.getRadarsSimulationConfigurationCatalog());
			out.println(simulationConfiguration.getConsumerClientSimulationConfigurationCatalog());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//    public void loadSimulationCatalog() {
//        simulationConfigurationCatalog.clear();
//        File file = new File(SIMULATION_RADARS_CONFIGURATION);
//        try (Scanner scanner = new Scanner(file)) {
//            while (scanner.hasNextLine()) {
//            	String RadarType = scanner.nextLine();
//                int simulationPeriod = Integer.parseInt(scanner.nextLine());
//                int radarQuantity = Integer.parseInt(scanner.nextLine());
//                
//                for(int i=0; i<radarQuantity; i++) {
//                	
//                }
//            }
//        } catch (FileNotFoundException e) {
//            // If the file does not exist, do nothing.
//        }
//    }

}
