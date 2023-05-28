package com.rutter;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class RadarCatalog extends JFrame{
	
	private static final long serialVersionUID = 1L;
	public static final String RADAR_CATALOG_FILE = "radarCatalog.txt";

	private RadarStation radarTypeA;
	private JPanel catalogPanel;
	private JPanel newRadarPanel;
	
	private ArrayList<RadarStation> radarCatalog;
	
	public RadarCatalog() {
		setTitle("Radar Catalog");
		setLayout(new GridLayout(0,1));

		radarCatalog = new ArrayList<RadarStation>();
		loadRadarCatalog();

//		radarTypeA = new RadarStation("Type A");
//		radarCatalog.add(radarTypeA);

		catalogPanel = new JPanel();
		catalogPanel.setLayout(new GridLayout(0,1));
		updateCatalogPanel();
		
		add(catalogPanel);
		
		newRadarPanel = new JPanel();
		newRadarPanel.setLayout(new GridLayout(0, 1));
		JLabel newRadarLabel = new JLabel("Define a New Radar Type");
		newRadarPanel.add(newRadarLabel);
		JTextField newRadarTextField = new JTextField();
		newRadarPanel.add(newRadarTextField);
		JButton newRadarButton = new JButton("Add New Radar");
		
		newRadarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newRadarType = newRadarTextField.getText();
				RadarStation newRadar = new RadarStation(newRadarType);
				radarCatalog.add(newRadar);
				saveRadarCatalog(newRadar);
				newRadarTextField.setText("");
				updateCatalogPanel();
			}
		});

		newRadarPanel.add(newRadarButton);

		add(newRadarPanel);

		setSize(300,400);
		setVisible(true);
	}

	private void updateCatalogPanel() {
		catalogPanel.removeAll(); // remove old labels

		for(RadarStation radar: radarCatalog) {
			String newRadarType = radar.getType();

			JLabel label = new JLabel(newRadarType);
			catalogPanel.add(label);
		}

		catalogPanel.revalidate(); // tell panel to layout components again
		catalogPanel.repaint(); // repaint the panel to see changes
	}
	
	public void loadRadarCatalog() {
		radarCatalog.clear();
		File file = new File(RADAR_CATALOG_FILE);
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String radarType = scanner.nextLine();
				radarCatalog.add(new RadarStation(radarType));
			}
		} catch (FileNotFoundException e) {
			// if File does not exist, do nothing.
		}
	}

	private void saveRadarCatalog(RadarStation newRadar) {
		try (PrintWriter out = new PrintWriter(new FileWriter(RADAR_CATALOG_FILE, true))) {
			out.println(newRadar.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<RadarStation> getRadarCatalog(){
		return radarCatalog;
	}
}
