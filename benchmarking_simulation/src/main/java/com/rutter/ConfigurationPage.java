package com.rutter;

import static com.rutter.RadarCatalog.RADAR_CATALOG_FILE;
import static com.rutter.ConsumerClientCatalog.CONSUMER_CATALOG_FILE;

import javax.swing.*;
import com.rutter.ConsumerClientCatalog;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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

<<<<<<< HEAD
	private JCheckBox[] consumerClientCheckBoxes;
	private JTextField[] consumerClientQuantityFields;

	private ArrayList<JCheckBox> radarCheckboxes = new ArrayList<JCheckBox>();
	private ArrayList<String> selectedRadars = new ArrayList<String>();
	private ArrayList<JCheckBox> consumerCheckboxes = new ArrayList<JCheckBox>();
	private ArrayList<Integer> selectedRadarsIndexList = new ArrayList<Integer>();
	private ArrayList<RadarStation> radarList = new ArrayList<RadarStation>();
	private ArrayList<ConsumerClient> consumerList = new ArrayList<ConsumerClient>();

//	private RadarCatalog radars;
//	private ConsumerClientCatalog consumersCatalog;
	private ArrayList<RadarStation> radarStations;
=======
	
	private JCheckBox[] consumerClientCheckBoxes;
	private JTextField[] consumerClientFields;
	
	private ArrayList<JCheckBox> radarCatalog = new ArrayList<JCheckBox>();
	private ArrayList<JCheckBox> selectedRadars = new ArrayList<JCheckBox>();
	private ArrayList<JCheckBox> consumerCatalog = new ArrayList<JCheckBox>();
	
	private RadarCatalog radars;
	private ConsumerClientCatalog consumersCatalog;
	private ArrayList<RadarStation> radarsCatalog;
>>>>>>> 82d31632fe1a3a0b13187c1feb8567ac1ae89679
	private ArrayList<ConsumerClient> consumerClientsCatalog;
	private ArrayList<String> selectedConsumers = new ArrayList<String>();
	private ArrayList<Integer> selectedConsumerClientIndexList = new ArrayList<Integer>();

	private HashMap<RadarStation, Integer> selectedRadarCountMap = new HashMap<>();;
	private ArrayList<HashMap<RadarStation, Integer>> radarsSimulationConfigurationCatalog = new ArrayList<HashMap<RadarStation, Integer>>();
	private ArrayList<HashMap<ConsumerClient, Integer>> consumerClientSimulationConfigurationCatalog = new ArrayList<HashMap<ConsumerClient, Integer>>();

	private static long configId;

	public ConfigurationPage() {
//		radarCatalog.clear();
		setTitle("Radar Configuration");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Set the JFrame layout
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		add(radarCatalogPanel);
		add(consumerClientCatalogPanel);
		add(simulationPanel);
<<<<<<< HEAD
=======
		
		Dimension windowSize = new Dimension(650, 350);
		setPreferredSize(windowSize);
		
		radarCatalogPanel.setLayout(new GridLayout(0, 3));
>>>>>>> 82d31632fe1a3a0b13187c1feb8567ac1ae89679

		Dimension windowSize = new Dimension(650, 350);
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

		ArrayList<ConsumerClient> consumerClients = loadConsumerClients();
//		consumerCatalog = consumerClients;
		int consumerClientsCatalogSize = consumerClients.size();

		radarLabels = new JLabel[radarCatalogSize];
		radarQuantityFields = new JTextField[radarCatalogSize];
		dataTransmitionIntervalFields = new JTextField[radarCatalogSize];
		radarCheckBoxes = new JCheckBox[radarCatalogSize];

		// Add the checkbox to the frame
		for (int i = 0; i < radarStations.size(); i++) {
			String checkBoxLabel = (radarStations.get(i).getType() + ":").toString();
			radarCheckBoxes[i] = new JCheckBox(checkBoxLabel);
			radarCatalogPanel.add(radarCheckBoxes[i]);
			radarCheckboxes.add(radarCheckBoxes[i]);

			radarQuantityFields[i] = new JTextField();
			radarCatalogPanel.add(radarQuantityFields[i]);

			dataTransmitionIntervalFields[i] = new JTextField();
			radarCatalogPanel.add(dataTransmitionIntervalFields[i]);
		}

		// consumer client panel
		consumerClientCatalogPanel.setLayout(new GridLayout(0, 2));

		consumerClientTypeLabel = new JLabel("Select Consumer Client Type");
		consumerClientCatalogPanel.add(consumerClientTypeLabel);

		consumerClientQuantityLabel = new JLabel("Enter Consumer Client Quantities");
		consumerClientCatalogPanel.add(consumerClientQuantityLabel);

<<<<<<< HEAD
		ArrayList<ConsumerClient> consumerClientsCatalog = loadConsumerClients();
=======
		consumersCatalog = new ConsumerClientCatalog();
		consumersCatalog.loadConsumerClientCatalog();
		consumerClientsCatalog = consumersCatalog.getConsumerClientCatalog();

>>>>>>> 82d31632fe1a3a0b13187c1feb8567ac1ae89679

		int consumerClientCatalogSize = consumerClientsCatalog.size();

//		consumerClientLabels = new JLabel[consumerClientCatalogSize];
<<<<<<< HEAD
		consumerClientQuantityFields = new JTextField[consumerClientCatalogSize];
=======
		consumerClientFields = new JTextField[consumerClientCatalogSize];
>>>>>>> 82d31632fe1a3a0b13187c1feb8567ac1ae89679
		consumerClientCheckBoxes = new JCheckBox[consumerClientCatalogSize];

		// Add the checkbox to the frame
		for (int i = 0; i < consumerClientsCatalog.size(); i++) {
			String checkBoxLabel = (consumerClientsCatalog.get(i).getType() + ":").toString();
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
		add(simulationPanel);

		pack();
		setVisible(true);
	}

	// here should implement two scenraios: 1.save new settings
	// 2.if such simulation has run before: return results
	public ActionListener saveConfiguration() {
		return new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Handle the button click event here
				getSelectedRadarsTypes();
				getRadarsPropertiesForSimulationCatalog();
				
				getSelectedConsumerClientTypes();
				getConsumerClientsPropertiesForSimulationCatalog();
				
				for (HashMap<RadarStation, Integer> map : radarsSimulationConfigurationCatalog) {
					for (RadarStation radar : map.keySet()) {
						System.out.println(radar.toString());
					}
				}
				
				for (HashMap<ConsumerClient, Integer> map : consumerClientSimulationConfigurationCatalog) {
					for (ConsumerClient consumerClient : map.keySet()) {
						System.out.println(consumerClient.toString());
					}
				}
				
				System.out.println(getSimulationPeriod()+" minutes");

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

			int quantity = Integer.parseInt(radarQuantityFields[selectedRadarsIndexList

					.get(i)].getText());

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

			
			int quantity = Integer.parseInt(consumerClientQuantityFields[selectedConsumerClientIndexList

					.get(i)].getText());

			HashMap<ConsumerClient, Integer> selectedConsumerQuantityMap = new HashMap<>();

			selectedConsumerQuantityMap.put(consumerClient, quantity);

			consumerClientSimulationConfigurationCatalog.add(selectedConsumerQuantityMap);

		}
	}
	
	public int getSimulationPeriod() {
		return Integer.parseInt(simulationtionPeriodField.getText());
	}
	

}
