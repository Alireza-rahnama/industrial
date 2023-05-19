package com.rutter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class ConfigurationPage extends JFrame {
	
	private JLabel radarQuantityLabel;
	private JLabel radarTypeLabel;
	private JLabel dataSimulationPeriodLabel;

	private JLabel[] radarLabels;
	private JCheckBox[] radarCheckBoxes;	
	private JTextField[] radarFields;
	private JTextField[] dataSimulationtionPeriodField;

	
    private static ArrayList<RadarStation> radarStationCatalog = new ArrayList<RadarStation>();
    private static ArrayList<String> types = new ArrayList<String>();
    private static ArrayList<Integer> uniqueIds = new ArrayList<Integer>();
    private static ArrayList<JCheckBox> radarCatalog = new ArrayList<JCheckBox>();
    private static ArrayList<JCheckBox> selectedRadars = new ArrayList<JCheckBox>();
    
	private static HashMap<Integer, String> radarStationAttributePairs;
	
	private static long configId;
	
	public ConfigurationPage() {
		
		setTitle("Radar Configuration");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(0, 3));
	    Dimension windowSize = new Dimension(700, 300);
	    setPreferredSize(windowSize);

		radarQuantityLabel = new JLabel("Select Radar Type");
		add(radarQuantityLabel);

		radarQuantityLabel = new JLabel("Enter Radar Quantities");
		add(radarQuantityLabel);
		
		dataSimulationPeriodLabel = new JLabel("Enter simulation period in minutes");
		add(dataSimulationPeriodLabel);
		
		types.add("Radar AA");
		types.add("Radar BB");
		types.add("Radar CC");
		
		uniqueIds.add(1);
		uniqueIds.add(2);
		uniqueIds.add(3);
		

		for (int i = 0; i < types.size(); i++) {
		    String type = types.get(i);
		    Integer uniqueId = uniqueIds.get(i);
		    
		    radarStationCatalog.add(new RadarStation(uniqueId,type));
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
			add(radarCheckBoxes[i]);
			radarCatalog.add(radarCheckBoxes[i]);

			radarFields[i] = new JTextField();
			add(radarFields[i]);
			
			dataSimulationtionPeriodField[i] = new JTextField();
			add(dataSimulationtionPeriodField[i]); 
		}
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
			    // Handle the button click event here
				for(JCheckBox checkBox: radarCatalog) {
					if(checkBox.isSelected()){
						selectedRadars.add(checkBox);
//						radarStationAttributePairs.put(null, getName())
					    System.out.println(checkBox.getText());
					}
				}
			}
		});
		add(submitButton);

		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ConfigurationPage();
			}
		});
	}
}