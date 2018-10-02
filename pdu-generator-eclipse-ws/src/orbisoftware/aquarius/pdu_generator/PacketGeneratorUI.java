/*
 *  Aquarius DIS PDU Suite
 *
 *  Copyright (C) 2011 Harlan Murphy
 *  Orbis Software - orbisoftware@gmail.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package orbisoftware.aquarius.pdu_generator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

import org.xml.sax.*;

public class PacketGeneratorUI implements ActionListener {

   private JTextField ipAddress = null;
   private JTextField port = null;
   private JButton fileSelectButton = null;
   private JButton startButton = null;
   private JButton stopButton = null;
   private String disPDUDir = "dis_pdus";
   private String fileNameDefault = "entity_state_pdu.xml";
   private JTextField fileName = null;
   private JFileChooser fileChooser = null;
   private FileExtensionFilter filter = null;

   public void addComponentsToPane(Container pane) {

      PacketGeneratorData packetGeneratorData = PacketGeneratorData
            .getInstance();

      JLabel label;
      pane.setLayout(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      c.weightx = 1.0;
      c.fill = GridBagConstraints.HORIZONTAL;

      label = new JLabel("IP Address:");
      c.gridx = 0;
      c.gridy = 0;
      c.insets = new Insets(20, 10, 0, 10);
      pane.add(label, c);

      ipAddress = new JTextField();
      ipAddress.setText(packetGeneratorData.getIPAddress());
      c.gridx = 1;
      c.gridy = 0;
      c.insets = new Insets(20, 10, 0, 10);
      pane.add(ipAddress, c);

      label = new JLabel("Port:");
      c.gridx = 0;
      c.gridy = 1;
      c.insets = new Insets(20, 10, 0, 10);
      pane.add(label, c);

      port = new JTextField();
      port.setText(Integer.toString(packetGeneratorData.getPort()));
      c.gridx = 1;
      c.gridy = 1;
      c.insets = new Insets(20, 10, 0, 10);
      pane.add(port, c);

      label = new JLabel("File Name:");
      c.gridx = 0;
      c.gridy = 2;
      c.insets = new Insets(20, 10, 0, 10);
      pane.add(label, c);

      fileSelectButton = new JButton("File Select");
      c.gridx = 1;
      c.gridy = 2;
      c.insets = new Insets(20, 10, 0, 10);
      pane.add(fileSelectButton, c);
      fileSelectButton.setEnabled(true);
      fileSelectButton.addActionListener(this);

      fileName = new JTextField();
      fileName.setText(System.getProperty("user.dir")
            + System.getProperty("file.separator") + disPDUDir
            + System.getProperty("file.separator") + fileNameDefault);
      c.gridx = 0;
      c.gridy = 3;
      c.gridwidth = 2;
      c.insets = new Insets(20, 10, 0, 10);
      pane.add(fileName, c);

      startButton = new JButton("Start");
      c.gridx = 0;
      c.gridy = 4;
      c.gridwidth = 1;
      c.insets = new Insets(20, 10, 10, 10);
      pane.add(startButton, c);
      startButton.setEnabled(true);
      startButton.addActionListener(this);

      stopButton = new JButton("Stop");
      c.gridx = 1;
      c.gridy = 4;
      c.insets = new Insets(20, 10, 10, 10);
      pane.add(stopButton, c);
      stopButton.setEnabled(false);
      stopButton.addActionListener(this);
   }

   /**
    * Create the GUI and show it. For thread safety, this method should be
    * invoked from the event-dispatching thread.
    */
   public void createAndShowGUI() {

      // Create and set up the window.
      JFrame frame = new JFrame("Packet Generator");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      filter = new FileExtensionFilter();

      // Make the file chooser readonly to disallow file renaming
      UIManager.put("FileChooser.readOnly", true);

      // Instantiate file chooser
      fileChooser = new JFileChooser(System.getProperty("user.dir")
            + System.getProperty("file.separator") + disPDUDir);

      filter.addExtension("xml");
      filter.setDescription("XML Datagram Files");

      fileChooser.setFileFilter(filter);

      // Set up the content pane.
      addComponentsToPane(frame.getContentPane());

      // Display the window.
      frame.setSize(300, 260);
      frame.setVisible(true);
   }

   public void actionPerformed(ActionEvent e) {

      PacketGeneratorData packetGeneratorData = PacketGeneratorData
            .getInstance();

      if (e.getSource() == startButton) {

         try {
            XMLReader parser = org.xml.sax.helpers.XMLReaderFactory
                  .createXMLReader();

            // Create a new instance and register it with the parser
            XMLPacketHandler packetHandler = new XMLPacketHandler();
            parser.setContentHandler(packetHandler);

            // Parse packet
            parser.parse(fileName.getText());

            // Set datagram data
            packetGeneratorData.setDatagramData(packetHandler.getPacketData());

            packetGeneratorData.setIPAddress(ipAddress.getText());
            packetGeneratorData.setPort(Integer.parseInt(port.getText()));
            packetGeneratorData.setGeneratorActive(true);

            startButton.setEnabled(false);
            stopButton.setEnabled(true);
         } catch (Exception exception) {
            System.out.println("\nCould not open file");
         }
      } else if (e.getSource() == stopButton) {

         packetGeneratorData.setGeneratorActive(false);

         startButton.setEnabled(true);
         stopButton.setEnabled(false);
      } else if (e.getSource() == fileSelectButton) {

         int returnVal = fileChooser.showOpenDialog(null);

         if (returnVal == JFileChooser.APPROVE_OPTION) {
            fileName.setText(fileChooser.getSelectedFile().getPath());
         }
      }
   }
}
