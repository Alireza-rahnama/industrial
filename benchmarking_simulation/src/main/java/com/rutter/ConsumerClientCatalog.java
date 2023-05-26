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


public class ConsumerClientCatalog extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private static final String CONSUMER_CATALOG_FILE = "consumerClientCatalog.txt";

	private ConsumerClient seaView;
	private JPanel catalogPanel;
	private JPanel newConsumerPanel;
	private static ArrayList<ConsumerClient> consumerClientCatalog;
	
	public ConsumerClientCatalog() {
		setLayout(new GridLayout(0,1));

		consumerClientCatalog = new ArrayList<ConsumerClient>();
		loadConsumerClientCatalog();

		seaView = new ConsumerClient("Sea View");
		consumerClientCatalog.add(seaView);

		catalogPanel = new JPanel();
		catalogPanel.setLayout(new GridLayout(0,1));
		updateCatalogPanel();
		
		add(catalogPanel);
		
		newConsumerPanel = new JPanel();
		newConsumerPanel.setLayout(new GridLayout(0, 1));
		JLabel newRadarLabel = new JLabel("Define a New Client Type");
		newConsumerPanel.add(newRadarLabel);
		JTextField newRadarTextField = new JTextField();
		newConsumerPanel.add(newRadarTextField);
		JButton newRadarButton = new JButton("Add New Consumer Client");
		newRadarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newRadarType = newRadarTextField.getText();
				ConsumerClient newConsumerClient = new ConsumerClient(newRadarType);
				consumerClientCatalog.add(newConsumerClient);
				saveConsumerClientCatalog(newConsumerClient);
				newRadarTextField.setText("");
				updateCatalogPanel();
			}
		});

		newConsumerPanel.add(newRadarButton);

		add(newConsumerPanel);

		setSize(300,400);
		setVisible(true);
	}

	private void updateCatalogPanel() {
		catalogPanel.removeAll(); // remove old labels

		for(ConsumerClient client: consumerClientCatalog) {
			String newRadarType = client.getType();

			JLabel label = new JLabel(newRadarType);
			catalogPanel.add(label);
		}

		catalogPanel.revalidate(); // tell panel to layout components again
		catalogPanel.repaint(); // repaint the panel to see changes
	}
	
	public void loadConsumerClientCatalog() {
		consumerClientCatalog.clear();
		File file = new File(CONSUMER_CATALOG_FILE);
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String consumerClientType = scanner.nextLine();
				consumerClientCatalog.add(new ConsumerClient(consumerClientType));
			}
		} catch (FileNotFoundException e) {
			// File does not exist yet, do nothing.
		}
	}

	private void saveConsumerClientCatalog(ConsumerClient newConsumerClient) {
		try (PrintWriter out = new PrintWriter(new FileWriter(CONSUMER_CATALOG_FILE, true))) {
			out.println(newConsumerClient.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ConsumerClient> getConsumerClientCatalog(){
		return consumerClientCatalog;
	}
}
