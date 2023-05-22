package com.rutter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartUpPage extends JFrame {

	protected static final JButton configureSimulation = new JButton("Configure Simulation");
	protected static final JButton addRadarStation = new JButton("Add Radar Station");
	protected static final JButton addClient = new JButton("Add New Client");
	protected static final JButton viewReports = new JButton("View Reports");
	protected static final JButton runSimulation = new JButton("View Reports");

	private JMenuBar menuBar = new JMenuBar();
	private Dimension windowSize = new Dimension(750, 200);

	public StartUpPage(String title) throws HeadlessException {
		super(title);

		setPreferredSize(windowSize);

		setJMenuBar(menuBar);
		
		configureSimulation.addActionListener(openConfigPage());


		menuBar.add(configureSimulation);
		menuBar.add(addRadarStation);
		menuBar.add(addClient);
		menuBar.add(viewReports);
		menuBar.add(runSimulation);

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
	

	public static void main(String[] args) {
		new StartUpPage("Performance Benchmarking");

	}

}
