package com.rutter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JTextField[] radarFields;
	private JTextField[] dataSimulationtionPeriodField;
	private JTextField simulationtionPeriodField;

	
	private JLabel[] consumerClientLabels;
	private JCheckBox[] consumerClientCheckBoxes;
	private JTextField[] consumerClientFields;
	

	private static ArrayList<RadarStation> radarStationCatalog = new ArrayList<RadarStation>();
	private static ArrayList<String> radarTypes = new ArrayList<String>();
	private static ArrayList<Integer> radarUniqueIds = new ArrayList<Integer>();
	private static ArrayList<JCheckBox> radarCatalog = new ArrayList<JCheckBox>();
	private static ArrayList<JCheckBox> selectedRadars = new ArrayList<JCheckBox>();
	
	private static ArrayList<ConsumerClient> consumerClientCatalog = new ArrayList<ConsumerClient>();
	private static ArrayList<String> consumerClientTypes = new ArrayList<String>();
	private static ArrayList<Long> consumerClientIds = new ArrayList<Long>();
	private static ArrayList<JCheckBox> consumerCatalog = new ArrayList<JCheckBox>();
	private static ArrayList<JCheckBox> selectedConsumers = new ArrayList<JCheckBox>();

	private static long configId;

	public ConfigurationPage() {

		setTitle("Radar Configuration");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    // Set the JFrame layout
	    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		add(radarCatalogPanel);
		add(consumerClientCatalogPanel);
		add(simulationPanel);
		
		Dimension windowSize = new Dimension(500, 350);
		setPreferredSize(windowSize);
		
		radarCatalogPanel.setLayout(new GridLayout(0, 3));


		radarQuantityLabel = new JLabel("Select Radar Type");
		radarCatalogPanel.add(radarQuantityLabel);

		radarQuantityLabel = new JLabel("Enter Radar Quantities");
		radarCatalogPanel.add(radarQuantityLabel);

		dataTransmitionPeriodLabel = new JLabel("Enter Data Transmition Period");
		radarCatalogPanel.add(dataTransmitionPeriodLabel);
		
		simulationPanel.setLayout(new GridLayout(0, 1));

		simualtionPeriodLabel = new JLabel("Enter Simulation Length in Minutes");
		simulationPanel.add(simualtionPeriodLabel);
		
		simulationtionPeriodField = new JTextField();
		simulationPanel.add(simulationtionPeriodField);
			

		radarTypes.add("Radar AA");
		radarTypes.add("Radar BB");
		radarTypes.add("Radar CC");

		radarUniqueIds.add(1);
		radarUniqueIds.add(2);
		radarUniqueIds.add(3);

		for (int i = 0; i < radarTypes.size(); i++) {
			String type = radarTypes.get(i);
			Integer uniqueId = radarUniqueIds.get(i);

			radarStationCatalog.add(new RadarStation(type));
		}

		int radarCatalogSize = radarStationCatalog.size();

		radarLabels = new JLabel[radarCatalogSize];
		radarFields = new JTextField[radarCatalogSize];
		dataSimulationtionPeriodField = new JTextField[radarCatalogSize];
		radarCheckBoxes = new JCheckBox[radarCatalogSize];

		// Add the checkbox to the frame
		for (int i = 0; i < radarStationCatalog.size(); i++) {
			String checkBoxLabel = (radarStationCatalog.get(i).getType() + ":").toString();
			radarCheckBoxes[i] = new JCheckBox(checkBoxLabel);
			radarCatalogPanel.add(radarCheckBoxes[i]);
			radarCatalog.add(radarCheckBoxes[i]);

			radarFields[i] = new JTextField();
			radarCatalogPanel.add(radarFields[i]);

			dataSimulationtionPeriodField[i] = new JTextField();
			radarCatalogPanel.add(dataSimulationtionPeriodField[i]);
		}

//		JButton submitButton = new JButton("Submit");
//		submitButton.addActionListener(saveConfiguration());
//		radarCatalogPanel.add(submitButton);

		
		//consumer client panel
		consumerClientCatalogPanel.setLayout(new GridLayout(0, 2));
		
		consumerClientTypeLabel = new JLabel("Select Consumer Client Type");
		consumerClientCatalogPanel.add(consumerClientTypeLabel);

		consumerClientQuantityLabel = new JLabel("Enter Consumer Client Quantities");
		consumerClientCatalogPanel.add(consumerClientQuantityLabel);


		consumerClientTypes.add("Sea View");
		consumerClientTypes.add("Mobile App");

		consumerClientIds.add(1L);
		consumerClientIds.add(2L);

		for (int i = 0; i < consumerClientTypes.size(); i++) {
			String type = consumerClientTypes.get(i);
			Long id = consumerClientIds.get(i);

			consumerClientCatalog.add(new ConsumerClient(type));
		}

		int consumerClientCatalogSize = consumerClientCatalog.size();

		consumerClientLabels = new JLabel[consumerClientCatalogSize];
		consumerClientFields = new JTextField[consumerClientCatalogSize];
		consumerClientCheckBoxes = new JCheckBox[consumerClientCatalogSize];

		// Add the checkbox to the frame
		for (int i = 0; i < consumerClientCatalog.size(); i++) {
		    String checkBoxLabel = (consumerClientCatalog.get(i).getType() + ":").toString();
		    consumerClientCheckBoxes[i] = new JCheckBox(checkBoxLabel);
		    consumerClientCatalogPanel.add(consumerClientCheckBoxes[i]);
		    consumerCatalog.add(consumerClientCheckBoxes[i]);

		    consumerClientFields[i] = new JTextField();
		    consumerClientCatalogPanel.add(consumerClientFields[i]);
		}
		
		

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(saveConfiguration());
		consumerClientCatalogPanel.add(saveButton);
		
		simulationPanel.add(saveButton);
		add(simulationPanel);

		
		pack();
		setVisible(true);
	}

	//here should implement two scenraios: 1.save new settings 
	// 2.if such simulation has run before: return results
	public ActionListener saveConfiguration() {
		return new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Handle the button click event here
				for (JCheckBox checkBox : radarCatalog) {
					if (checkBox.isSelected()) {
						selectedRadars.add(checkBox);
						System.out.println(checkBox.getText());

					}
				}
			}
		};
	}

}
