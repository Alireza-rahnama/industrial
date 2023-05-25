package com.rutter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartUpPage extends JFrame {

	protected static final JButton configureSimulation = new JButton("Configure Simulation");
	protected static final JButton radarStationCatalog = new JButton("Radar Catalog");
	protected static final JButton consumerClientCatalog = new JButton("Consumer Client Catalog");
	protected static final JButton viewReports = new JButton("View Reports");
	protected static final JButton newSimulation = new JButton("New Simulation");

	private JMenuBar menuBar = new JMenuBar();
	private Dimension windowSize = new Dimension(800, 300);

	public StartUpPage(String title) throws HeadlessException {
		super(title);

		setPreferredSize(windowSize);

		setJMenuBar(menuBar);
		
		configureSimulation.addActionListener(openConfigPage());
		radarStationCatalog.addActionListener(openRadarCatalog());
		consumerClientCatalog.addActionListener(openConsumerClientCatalog());

		menuBar.add(configureSimulation);
		menuBar.add(radarStationCatalog);
		menuBar.add(consumerClientCatalog);
		menuBar.add(viewReports);
		menuBar.add(newSimulation);

		pack();
		setVisible(true);

	}

	private ActionListener openConfigPage() {
		return new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			    // Handle the button click event here
				new ConfigurationPage();
				}
			};
		}
	
	private ActionListener openRadarCatalog() {
		return new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			    // Handle the button click event here
				new RadarCatalog();
				}
			};
		}
	
	private ActionListener openConsumerClientCatalog() {
		return new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			    // Handle the button click event here
				new ConsumerClientCatalog();
				}
			};
	}
	

	public static void main(String[] args) {
		new StartUpPage("Performance Benchmarking");

	}

}
