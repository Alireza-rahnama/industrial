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

public class ConsumerClientCatalog extends JFrame {

    private static final long serialVersionUID = 1L;
    public static final String CONSUMER_CATALOG_FILE = "consumerClientCatalog.txt";

    private ConsumerClient seaView;
    private JPanel catalogPanel;
    private JPanel newConsumerPanel;
    private ArrayList<ConsumerClient> consumerClientCatalog;
    private StartUpPage startUpPage;

    public ConsumerClientCatalog(StartUpPage startUpPage) {
        this.startUpPage = startUpPage;
        setLayout(new GridLayout(0, 1));

        consumerClientCatalog = new ArrayList<ConsumerClient>();
        loadConsumerClientCatalog();

        seaView = new ConsumerClient("Sea View");
        consumerClientCatalog.add(seaView);

        catalogPanel = new JPanel();
        catalogPanel.setLayout(new GridLayout(0, 1));
        updateCatalogPanel();

        add(catalogPanel);

        newConsumerPanel = new JPanel();
        newConsumerPanel.setLayout(new GridLayout(0, 1));
        JLabel newConsumerLabel = new JLabel("Define a New Client Type");
        newConsumerPanel.add(newConsumerLabel);
        JTextField newConsumerTextField = new JTextField();
        newConsumerPanel.add(newConsumerTextField);
        JButton newConsumerButton = new JButton("Add New Consumer Client");
        newConsumerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newConsumerType = newConsumerTextField.getText();
                ConsumerClient newConsumerClient = new ConsumerClient(newConsumerType);
                consumerClientCatalog.add(newConsumerClient);
                saveConsumerClientCatalog(newConsumerClient);
                newConsumerTextField.setText("");
                updateCatalogPanel();
            }
        });

        newConsumerPanel.add(newConsumerButton);

        JButton returnButton = new JButton("Return to Start Up Page");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the ConsumerClientCatalog JFrame
                startUpPage.setVisible(true); // Show the StartUpPage JFrame
            }
        });

        newConsumerPanel.add(returnButton);
        add(newConsumerPanel);
    }

    private void updateCatalogPanel() {
        catalogPanel.removeAll();

        for (ConsumerClient client : consumerClientCatalog) {
            String newConsumerType = client.getType();

            JLabel label = new JLabel(newConsumerType);
            catalogPanel.add(label);
        }

        catalogPanel.revalidate();
        catalogPanel.repaint();
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

    public ArrayList<ConsumerClient> getConsumerClientCatalog() {
        return consumerClientCatalog;
    }
}
