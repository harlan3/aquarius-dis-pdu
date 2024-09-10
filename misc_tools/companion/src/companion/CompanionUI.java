package companion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CompanionUI {

	JTextField latitudeField;
	JTextField longitudeField;
	JTextField xField;
	JTextField yField;
	JTextField zField;
	JTextField septupleField;
	JTextField septupleOutput;

	Computations computations = new Computations();

	void generateUI(JFrame jFrame) {

		// Create a JTabbedPane
		JTabbedPane tabbedPane = new JTabbedPane();

		// Create the first tab with GridBagLayout
		JPanel panel1 = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.ipadx = 1;
		gbc.ipady = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 0, 0, 0);
		JLabel label = new JLabel("Geodetic to Geocentric Conversion");
		panel1.add(label, gbc);

		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		JButton calculateConversion = new JButton("Convert");
		panel1.add(calculateConversion, gbc);

		calculateConversion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					xField.setText("");
					yField.setText("");
					zField.setText("");

					double values[] = computations.convertGeodeticToGeocentric(
							Double.parseDouble(latitudeField.getText()), Double.parseDouble(longitudeField.getText()),
							0.0);
					xField.setText(computations.doubleToHexWithSpaces(values[0]));
					yField.setText(computations.doubleToHexWithSpaces(values[1]));
					zField.setText(computations.doubleToHexWithSpaces(values[2]));
				} catch (Exception ee) {
				}
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.weighty = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		label = new JLabel("Latitude:");
		panel1.add(label, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		latitudeField = new JTextField();
		panel1.add(latitudeField, gbc);

		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;
		label = new JLabel("X:");
		panel1.add(label, gbc);

		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 100;
		gbc.insets = new Insets(10, 10, 10, 50);
		xField = new JTextField();
		xField.setEditable(false);
		panel1.add(xField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.NONE;
		gbc.ipadx = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.EAST;
		label = new JLabel("Longitude:");
		panel1.add(label, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
		longitudeField = new JTextField();
		panel1.add(longitudeField, gbc);

		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;
		label = new JLabel("Y:");
		panel1.add(label, gbc);

		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 100;
		gbc.insets = new Insets(10, 10, 10, 50);
		yField = new JTextField();
		yField.setEditable(false);
		panel1.add(yField, gbc);

		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.ipadx = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		label = new JLabel("Z:");
		panel1.add(label, gbc);

		gbc.gridx = 3;
		gbc.gridy = 3;
		gbc.insets = new Insets(10, 10, 10, 50);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 100;
		zField = new JTextField();
		zField.setEditable(false);
		panel1.add(zField, gbc);

		JScrollPane scrollPane1 = new JScrollPane(panel1);
		tabbedPane.addTab("Misc Conversions", scrollPane1);

		gbc.gridx = 2;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.ipadx = 1;
		gbc.ipady = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		label = new JLabel("DIS Septuple Conversion");
		panel1.add(label, gbc);

		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.ipadx = 1;
		gbc.ipady = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 3;
		gbc.gridy = 5;
		JButton convertSeptuple = new JButton("Convert");
		panel1.add(convertSeptuple, gbc);

		convertSeptuple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					septupleOutput.setText("");
					String septupleHex = computations.convertSeptupleToHex(septupleField.getText());
					septupleOutput.setText(septupleHex);
				} catch (Exception ee) {
				}
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.weighty = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		label = new JLabel("DIS Septuple:");
		panel1.add(label, gbc);

		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		septupleField = new JTextField();
		septupleField.setText("11:15:263:20:25:30:40");
		panel1.add(septupleField, gbc);

		gbc.gridx = 2;
		gbc.gridy = 6;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;
		label = new JLabel("Hex:");
		panel1.add(label, gbc);

		gbc.gridx = 3;
		gbc.gridy = 6;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 100;
		gbc.insets = new Insets(10, 10, 10, 50);
		septupleOutput = new JTextField();
		septupleOutput.setEditable(false);
		panel1.add(septupleOutput, gbc);

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.ipadx = 1;
		gbc.ipady = 150;
		label = new JLabel("");
		panel1.add(label, gbc);

		// **************************************************************************************

		// Create the second tab with GridBagLayout
		JPanel panel2 = new JPanel(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.ipadx = 1;
		gbc.ipady = 1;
		gbc.insets = new Insets(0, 115, 0, 0);
		label = new JLabel("Float to Hex (4 bytes):");
		panel2.add(label, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 40;
		gbc.ipady = 1;
		gbc.insets = new Insets(10, 10, 10, 50);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JTextField inputField1 = new JTextField();
		panel2.add(inputField1, gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 40;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JTextField hexOutput1 = new JTextField();
		hexOutput1.setEditable(false);
		panel2.add(hexOutput1, gbc);

		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.insets = new Insets(10, 10, 10, 50);
		gbc.fill = GridBagConstraints.NONE;
		JButton convertButton1 = new JButton("Convert");
		panel2.add(convertButton1, gbc);

		convertButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					hexOutput1.setText("");
					hexOutput1.setText(computations.floatToHexWithSpaces(Float.parseFloat(inputField1.getText())));
				} catch (Exception e2) {
				}
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 105, 0, 0);
		label = new JLabel("Double to Hex (8 bytes):");
		panel2.add(label, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 40;
		gbc.ipady = 1;
		gbc.insets = new Insets(10, 10, 10, 50);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JTextField inputField2 = new JTextField();
		panel2.add(inputField2, gbc);

		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.weightx = 40;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JTextField hexOutput2 = new JTextField();
		hexOutput2.setEditable(false);
		panel2.add(hexOutput2, gbc);

		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.insets = new Insets(10, 10, 10, 50);
		gbc.fill = GridBagConstraints.NONE;
		JButton convertButton2 = new JButton("Convert");
		panel2.add(convertButton2, gbc);

		convertButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					hexOutput2.setText("");
					hexOutput2.setText(computations.doubleToHexWithSpaces(Double.parseDouble(inputField2.getText())));
				} catch (Exception e2) {
				}
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(0, 165, 0, 0);
		label = new JLabel("ASCII to Hex:");
		panel2.add(label, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 40;
		gbc.ipady = 1;
		gbc.insets = new Insets(10, 10, 10, 50);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JTextField inputField3 = new JTextField();
		panel2.add(inputField3, gbc);

		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.weightx = 40;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JTextField hexOutput3 = new JTextField();
		hexOutput3.setEditable(false);
		panel2.add(hexOutput3, gbc);

		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.insets = new Insets(10, 10, 10, 50);
		JButton convertButton3 = new JButton("Convert");
		panel2.add(convertButton3, gbc);

		convertButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					hexOutput3.setText("");
					hexOutput3.setText(computations.asciiToHexWithSpaces(inputField3.getText()));
				} catch (Exception e2) {
				}
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(0, 125, 0, 0);
		label = new JLabel("Byte to Hex (1 byte):");
		panel2.add(label, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.weightx = 40;
		gbc.ipady = 1;
		gbc.insets = new Insets(10, 10, 10, 50);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JTextField inputField4 = new JTextField();
		panel2.add(inputField4, gbc);

		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.weightx = 40;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JTextField hexOutput4 = new JTextField();
		hexOutput4.setEditable(false);
		panel2.add(hexOutput4, gbc);

		gbc.gridx = 3;
		gbc.gridy = 3;
		gbc.weightx = 1;
		gbc.insets = new Insets(10, 10, 10, 50);
		JButton convertButton4 = new JButton("Convert");
		panel2.add(convertButton4, gbc);

		convertButton4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					hexOutput4.setText("");
					int value = Integer.parseInt(inputField4.getText());
					hexOutput4.setText(computations.byteToHex(value));
				} catch (Exception e2) {
				}
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(0, 115, 0, 0);
		label = new JLabel("Short to Hex (2 bytes):");
		panel2.add(label, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.weightx = 40;
		gbc.ipady = 1;
		gbc.insets = new Insets(10, 10, 10, 50);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JTextField inputField5 = new JTextField();
		panel2.add(inputField5, gbc);

		gbc.gridx = 2;
		gbc.gridy = 4;
		gbc.weightx = 40;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JTextField hexOutput5 = new JTextField();
		hexOutput5.setEditable(false);
		panel2.add(hexOutput5, gbc);

		gbc.gridx = 3;
		gbc.gridy = 4;
		gbc.weightx = 1;
		gbc.insets = new Insets(10, 10, 10, 50);
		JButton convertButton5 = new JButton("Convert");
		panel2.add(convertButton5, gbc);

		convertButton5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					hexOutput5.setText("");
					hexOutput5.setText(computations.shortToHexWithSpaces(Integer.parseInt(inputField5.getText())));
				} catch (Exception e2) {
				}
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.insets = new Insets(0, 105, 0, 0);
		label = new JLabel("Integer to Hex (4 bytes):");
		panel2.add(label, gbc);

		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.weightx = 40;
		gbc.ipady = 1;
		gbc.insets = new Insets(10, 10, 10, 50);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JTextField inputField6 = new JTextField();
		panel2.add(inputField6, gbc);

		gbc.gridx = 2;
		gbc.gridy = 5;
		gbc.weightx = 40;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JTextField hexOutput6 = new JTextField();
		hexOutput6.setEditable(false);
		panel2.add(hexOutput6, gbc);

		gbc.gridx = 3;
		gbc.gridy = 5;
		gbc.weightx = 1;
		gbc.insets = new Insets(10, 10, 10, 50);
		JButton convertButton6 = new JButton("Convert");
		panel2.add(convertButton6, gbc);

		convertButton6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					hexOutput6.setText("");
					hexOutput6.setText(computations.intToHexWithSpaces(Long.parseLong(inputField6.getText())));
				} catch (Exception e2) {
				}
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.insets = new Insets(0, 75, 0, 0);
		label = new JLabel("Long Integer to Hex (8 bytes):");
		panel2.add(label, gbc);

		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.weightx = 40;
		gbc.ipady = 1;
		gbc.insets = new Insets(10, 10, 10, 50);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JTextField inputField7 = new JTextField();
		panel2.add(inputField7, gbc);

		gbc.gridx = 2;
		gbc.gridy = 6;
		gbc.weightx = 40;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JTextField hexOutput7 = new JTextField();
		hexOutput7.setEditable(false);
		panel2.add(hexOutput7, gbc);

		gbc.gridx = 3;
		gbc.gridy = 6;
		gbc.weightx = 1;
		gbc.insets = new Insets(10, 10, 10, 50);
		JButton convertButton7 = new JButton("Convert");
		panel2.add(convertButton7, gbc);

		convertButton7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					hexOutput7.setText("");
					hexOutput7.setText(computations.longToHexWithSpaces(Long.parseLong(inputField7.getText())));
				} catch (Exception e2) {
				}
			}
		});

		JScrollPane scrollPane2 = new JScrollPane(panel2);
		tabbedPane.addTab("Type Conversions", scrollPane2);

		// Add the tabbedPane to the frame
		jFrame.add(tabbedPane);

		// Set the frame to be visible
		jFrame.setVisible(true);
	}
}
