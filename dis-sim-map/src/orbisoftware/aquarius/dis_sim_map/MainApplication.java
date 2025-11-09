/*
 *  Aquarius DIS PDU Suite
 *
 *  Copyright (C) 2024 Harlan Murphy
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

package orbisoftware.aquarius.dis_sim_map;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import uk.ac.leeds.ccg.geotools.DisLayer;
import uk.ac.leeds.ccg.geotools.DisObject;

public class MainApplication implements ActionListener, ListSelectionListener {

	private static MainApplication instance = null;
	private JButton startButton = null;
	private JButton stopButton = null;
	public JScrollPane listScrollPane;
	private JPanel panel1 = new JPanel();
	
	public HashMap<String, String> xmlMap = new HashMap<>();

	public DefaultListModel<String> model = new DefaultListModel<>();
	public JList<String> displayList = new JList<String>(model);
	
	private ProcessDatagramThread processDatagramThread;
	private ProcessEntityTimeToLiveThread processEntityTimeToLiveThread;
	private boolean initProcessDatagram = false;

	public static int secondaryGUIWidth;
	public static int bottomBorder;
	public static int xWidthAdjustPrimary;
	public static int xLocationAdjustSecondary;
	
	protected MainApplication() {

		// Suppress system output. This eliminates all System.out.println!
		System.setOut(new java.io.PrintStream(new java.io.OutputStream() {
            public void write(int b) {
                //DO NOTHING
            }
        }));
		
		displayList.addListSelectionListener(this);
	}
	
	private void parseElements(Element root) {

       String name = "";
    
       if (root != null) {
    
          NodeList nl = root.getChildNodes();
    
          if (nl != null) {
    
             for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
    
                if (node.getNodeName().equalsIgnoreCase("setting")) {
    
                   NodeList childNodes = node.getChildNodes();
    
                   for (int j = 0; j < childNodes.getLength(); j++) {
    
                      Node child = childNodes.item(j);
    
                      if (child.getNodeName().equalsIgnoreCase("name"))
                         name = child.getTextContent();
                      else if (child.getNodeName().equalsIgnoreCase("value"))
                         MainApplication.getInstance().xmlMap.put(name,
                               child.getTextContent());
                   }
                }
             }
          }
       }
    }

	public static MainApplication getInstance() {

		if (instance == null) {
			instance = new MainApplication();
		}
		return instance;
	}

	public void valueChanged(ListSelectionEvent e) {

		String selectedEntity = displayList.getSelectedValue();
		
		if (selectedEntity == null) {
			displayList.clearSelection();
			return;
		}
		
		String values[] = selectedEntity.split("::");
		
		DisLayer disLayer = DisLayer.getInstance();

		// Unselect everything
		for (Map.Entry<Integer, DisObject> entry : disLayer.disObjectHashMap.entrySet()) {

			Integer key = entry.getKey();
			DisObject disObject = entry.getValue();

			disObject.isSelected = false;
			disLayer.disObjectHashMap.put(key, disObject);
		}
		
		// Enable selection on selected entity
		if (values.length == 2) {
			
			int key = Integer.parseInt(values[0]);
			DisObject disObject = DisLayer.getInstance().disObjectHashMap.get(key);
			disObject.isSelected = true;
			disLayer.disObjectHashMap.put(key, disObject);
			disLayer.requestRepaint();
		}
	}
	
	public void addComponentsToPane(Container pane) {

		JLabel label;
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;

		startButton = new JButton("Start");
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.insets = new Insets(20, 10, 10, 10);
		pane.add(startButton, c);
		startButton.setEnabled(true);
		startButton.addActionListener(this);

		stopButton = new JButton("Stop");
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(20, 10, 10, 10);
		pane.add(stopButton, c);
		stopButton.setEnabled(false);
		stopButton.addActionListener(this);

		panel1.setLayout(new BorderLayout());

		TitledBorder title1 = BorderFactory.createTitledBorder(panel1.getBorder(), "Received Entity State PDUs");
		title1.setTitleJustification(TitledBorder.CENTER);
		panel1.setBorder(title1);

		panel1.setAlignmentX(Component.BOTTOM_ALIGNMENT);
		listScrollPane = new JScrollPane(displayList);
		panel1.add(listScrollPane);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.ipady = 1000;
		c.anchor = GridBagConstraints.PAGE_END; // bottom of space
		c.weighty = 1.0;
		pane.add(panel1, c);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be invoked
	 * from the event-dispatching thread.
	 */
	public JPanel disSimMapPanel() {

		// Create and set up the window.
		JPanel panel = new JPanel();

		// Set up the content pane.
		addComponentsToPane(panel);

		return panel;
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == startButton) {
			
			startButton.setEnabled(false);
			stopButton.setEnabled(true);

			if (!initProcessDatagram) {
				
				processEntityTimeToLiveThread.start();
				processDatagramThread.start();
				initProcessDatagram = true;
			}
			
			processDatagramThread.setThreadIsActive(true);

		} else if (e.getSource() == stopButton) {

			startButton.setEnabled(true);
			stopButton.setEnabled(false);
			
			processDatagramThread.setThreadIsActive(false);
		}
	}

	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				MainApplication mainApplication = MainApplication.getInstance();
				mainApplication.processDatagramThread = new ProcessDatagramThread();
				mainApplication.processEntityTimeToLiveThread = new ProcessEntityTimeToLiveThread();
				
				try {
					// Process XML
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					DocumentBuilder db = dbf.newDocumentBuilder();
					Document doc = db.parse("settings.xml");
					Element rootElem = doc.getDocumentElement();
					
					if (rootElem != null) {
						mainApplication.parseElements(rootElem);
					}
				}  catch (Exception e) {
					e.printStackTrace();
				}

				String[] fileArray = new String[1];
				fileArray[0] = "shape_files" + File.separator + "world.shp";

				if (OSUtils.isWindows()) {
					
					secondaryGUIWidth = 240;
					bottomBorder = 40;
					xWidthAdjustPrimary = -23;
					xLocationAdjustSecondary = 8;
				} else if (OSUtils.isUnix()) {
					secondaryGUIWidth = 240;
					bottomBorder = 40;
					xWidthAdjustPrimary = 3;
					xLocationAdjustSecondary = 8;
				}
				
				GISViewer gisViewer = new GISViewer(fileArray);
				gisViewer.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}

					public void windowActivated(WindowEvent e) {
					}

					public void windowOpened(WindowEvent e) {
					}
				});

				JFrame jFrame = new JFrame("Aquarius DIS Sim Map");

				JPanel panel = mainApplication.disSimMapPanel();
				jFrame.add(panel);

				// Position panel to the right of map
				int w = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width - secondaryGUIWidth;
				int h = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - bottomBorder;
				
				jFrame.setLocation(w + xLocationAdjustSecondary, 0);
				
				// Close operation when the window is closed
				jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jFrame.setSize(secondaryGUIWidth, h);
				jFrame.setVisible(true);
				
			}
		});
	}
}
