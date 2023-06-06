package com.rutter;

import java.awt.Dimension;
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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RadarCatalog extends JFrame {

    private static final long serialVersionUID = 1L;
    public static final String RADAR_CATALOG_FILE = "radarCatalog.txt";

    private RadarStation radarTypeA;
    private JPanel catalogPanel;
    private JPanel newRadarPanel;

    private StartUpPage startUpPage;

    private ArrayList<RadarStation> radarCatalog;
    private Dimension windowSize = new Dimension(800, 300);

    public RadarCatalog(StartUpPage startUpPage) {
        super();
        setTitle("Radar Catalog");
        setLayout(new GridLayout(0, 1));
        setPreferredSize(windowSize);

        this.startUpPage = startUpPage;

        radarCatalog = new ArrayList<RadarStation>();
        loadRadarCatalog();

        catalogPanel = new JPanel();
        catalogPanel.setLayout(new GridLayout(0, 1));
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

        JButton repaintButton = new JButton("Return to Start Up Page");
        repaintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the RadarCatalog JFrame
                startUpPage.setVisible(true); // Show the StartUpPage JFrame
            }
        });

        newRadarPanel.add(repaintButton);
        add(newRadarPanel);
    }

    private void updateCatalogPanel() {
        catalogPanel.removeAll();

        for (RadarStation radar : radarCatalog) {
            String newRadarType = radar.getType();

            JLabel label = new JLabel(newRadarType);
            catalogPanel.add(label);
        }

        catalogPanel.revalidate();
        catalogPanel.repaint();
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

    public ArrayList<RadarStation> getRadarCatalog() {
        return radarCatalog;
    }
}

