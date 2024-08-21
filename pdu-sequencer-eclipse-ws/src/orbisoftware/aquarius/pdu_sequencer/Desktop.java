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

package orbisoftware.aquarius.pdu_sequencer;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class Desktop {

	private Path currentWorkingDir = Paths.get("").toAbsolutePath().getParent();
	private LoadDISPdus loadDISPdus = new LoadDISPdus();
	private TreeDragAndDrop treeDragAndDrop = new TreeDragAndDrop();
	private CompositionFile compositionFile = new CompositionFile();

	private JTree jTree = null;

	private ArrayList<String> disVersions = new ArrayList<String>();
	
	void createMenuBar(JFrame frame) {

		JMenuBar menuBar;
		JMenu menu;
		JMenuItem openCompositionTree, saveCompositionTree;
		JMenuItem saveCompositionXML, exitMenuItem;
		JMenuItem editApplicationSettings;

		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the first menu.
		menu = new JMenu("File");
		menuBar.add(menu);

		openCompositionTree = new JMenuItem("Open Composition Tree");
		menu.add(openCompositionTree);
		openCompositionTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Composition Tree Binary", "ctb");
				fileChooser.setFileFilter(filter);
				fileChooser.setCurrentDirectory(new File(currentWorkingDir.toString() +
			            System.getProperty("file.separator") + "pdu-sequencer-eclipse-ws"));
				int result = fileChooser.showOpenDialog(fileChooser);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					// System.out.println("Selected file: " + selectedFile.getAbsolutePath());
					treeDragAndDrop.deserializationTree(selectedFile);
				}
			}
		});

		saveCompositionTree = new JMenuItem("Save Composition Tree");
		menu.add(saveCompositionTree);
		saveCompositionTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Composition Tree Binary", "ctb");
				fileChooser.setFileFilter(filter);
				fileChooser.setCurrentDirectory(new File(currentWorkingDir.toString() +
			            System.getProperty("file.separator") + "pdu-sequencer-eclipse-ws"));
				int result = fileChooser.showSaveDialog(fileChooser);
				if (result == JFileChooser.APPROVE_OPTION) {
					String selectedFile = fileChooser.getSelectedFile().getAbsolutePath().toString();
					if (!selectedFile.endsWith(".ctb"))
						selectedFile += ".ctb";
					// System.out.println("Selected file: " + selectedFile);
					treeDragAndDrop.serializeTree(new File(selectedFile));
				}
			}
		});

		saveCompositionXML = new JMenuItem("Save Composition XML");
		menu.add(saveCompositionXML);
		saveCompositionXML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Composition XML File", "xml");
				fileChooser.setFileFilter(filter);
				fileChooser.setCurrentDirectory(new File(currentWorkingDir.toString() +
			            System.getProperty("file.separator") + "pdu-sequencer-eclipse-ws"));
				int result = fileChooser.showSaveDialog(fileChooser);
				if (result == JFileChooser.APPROVE_OPTION) {
					String selectedFile = fileChooser.getSelectedFile().getAbsolutePath().toString();
					if (!selectedFile.endsWith(".xml"))
						selectedFile += ".xml";
					// System.out.println("Selected file: " + selectedFile);
					compositionFile.saveCompositionXMLFile(new File(selectedFile));
				}
			}
		});

		menu.addSeparator();
		exitMenuItem = new JMenuItem("Exit");
		menu.add(exitMenuItem);
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		});

		// Build second menu in the menu bar.
		menu = new JMenu("Settings");
		editApplicationSettings = new JMenuItem("Edit Application Settings");
		menu.add(editApplicationSettings);
		editApplicationSettings.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent ae) {

				JFrame popupFrame = new JFrame("Application Settings");
				popupFrame.setLayout(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();
		        
		        JLabel label1 = new JLabel("Default interval between DIS PDUs (milliseconds):");
		        label1.setHorizontalAlignment(JTextField.CENTER);
				c.fill = GridBagConstraints.NONE;
				c.weightx = 0.15;
				c.weighty = 0.01;
				c.gridx = 0;
				c.gridy = 0;
				popupFrame.add(label1, c);
		         
				JTextField delayTextField = new JTextField();
				delayTextField.setHorizontalAlignment(JTextField.LEFT);
				delayTextField.setEditable(true);
				delayTextField.setText(Integer.toString(SharedData.getInstance().delayBetweenMessages));
				c.fill = GridBagConstraints.HORIZONTAL;
				c.weightx = 0.85;
				c.weighty = 0.01;
				c.gridx = 1;
				c.gridy = 0;
				popupFrame.add(delayTextField, c);
				
				JButton updateSettingsButton = new JButton("Update Settings");
				updateSettingsButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
						SharedData.getInstance().delayBetweenMessages = Integer.parseInt(delayTextField.getText());
						popupFrame.hide();
						} catch (Exception ex) {
						}
					}
				});
				c.fill = GridBagConstraints.NONE;
				c.weightx = 0.75;
				c.weighty = 0.025;
				c.gridx = 0;
				c.gridy = 1;
				popupFrame.add(updateSettingsButton, c);
		        
		        // set the size of the frame
				popupFrame.setSize(375, 200);
		        popupFrame.show();
		        
			}
		});
		menuBar.add(menu);

		frame.setJMenuBar(menuBar);
	}

	public void createGUI(JFrame frame) {

		JList<String> list;
		JTextArea textArea;

		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// 1st Column Start
		JTextField msgListTextField = new JTextField();
		msgListTextField.setText("DIS PDUs");
		msgListTextField.setHorizontalAlignment(JTextField.CENTER);
		msgListTextField.setEditable(false);
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.weighty = 0.01;
		c.gridx = 0;
		c.gridy = 0;
		frame.add(msgListTextField, c);

		DefaultListModel<String> listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		list.setDragEnabled(true);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		JScrollPane listView = new JScrollPane(list);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		frame.add(listView, c);

		JComboBox disVersionListComboBox = new JComboBox(disVersions.toArray());
		loadDISPdus.addActionListener(disVersionListComboBox, listModel);
		loadDISPdus.loadDISPdus(listModel, disVersionListComboBox.getSelectedItem().toString());
		disVersionListComboBox.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	resetJTreeModel();
		    }
		});
		
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.weighty = 0.025;
		c.gridx = 0;
		c.gridy = 1;
		frame.add(disVersionListComboBox, c);

		// 2nd Column Start
		JTextField compositionTreeTextField = new JTextField();
		compositionTreeTextField.setText("Composition Tree - Drag and drop DIS PDUs into Message Group");
		compositionTreeTextField.setHorizontalAlignment(JTextField.CENTER);
		compositionTreeTextField.setEditable(false);
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.weighty = 0.01;
		c.gridx = 1;
		c.gridy = 0;
		frame.add(compositionTreeTextField, c);

		JButton resetTreeButton = new JButton("Reset Composition Tree");
		resetTreeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetJTreeModel();
			}
		});
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.weighty = 0.025;
		c.gridx = 1;
		c.gridy = 1;
		frame.add(resetTreeButton, c);

		// Create composition tree
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Composition Root");
		DefaultMutableTreeNode msgGroupNode = new DefaultMutableTreeNode("Message Group");
		DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
		treeModel.insertNodeInto(msgGroupNode, rootNode, 0);
		jTree = new JTree(treeModel);
		treeDragAndDrop.setupTree(jTree);

		JScrollPane treeView = new JScrollPane(jTree);
		treeView.setPreferredSize(new Dimension(300, 100));
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 2;
		frame.add(treeView, c);

		// 3rd Column Start
		JTextField compositionFileTextField = new JTextField();
		compositionFileTextField.setText("Composition XML File - Generate file from Composition Tree");
		compositionFileTextField.setHorizontalAlignment(JTextField.CENTER);
		compositionFileTextField.setEditable(false);
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.weighty = 0.01;
		c.gridx = 2;
		c.gridy = 0;
		frame.add(compositionFileTextField, c);

		// Create a scrolled text area.
		textArea = new JTextArea(5, 30);
		JScrollPane scrollPane = new JScrollPane(textArea);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 2;
		c.gridy = 2;
		frame.add(scrollPane, c);

		JButton generateCompositionButton = new JButton("Generate Composition File");
		generateCompositionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compositionFile.generateComposition(jTree, textArea);
			}
		});
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.weighty = 0.025;
		c.gridx = 2;
		c.gridy = 1;
		frame.add(generateCompositionButton, c);
	}

	private void resetJTreeModel() {

		DefaultTreeModel model = (DefaultTreeModel) jTree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		root.removeAllChildren();

		DefaultMutableTreeNode msgGroupNode = new DefaultMutableTreeNode("Message Group");
		model.insertNodeInto(msgGroupNode, root, 0);
		model.reload();
	}

	private void populateDISVersionInfo() {
		
		//System.out.println("Working Directory = " + System.getProperty("user.dir"));
		File[] directories = new File("../pdu-generator-eclipse-ws/").listFiles(File::isDirectory);
		
		for (int i=0; i<directories.length; i++) {
		
			if (directories[i].toString().contains("dis_version")) {
				String versionPathArray[] = directories[i].toString().split(Pattern.quote(File.separator));
				String versionInfo = versionPathArray[versionPathArray.length-1];
				disVersions.add(versionInfo);
			}
		}
	}
	
	public static void main(String[] args) {

		Desktop desktop = new Desktop();
		JFrame frame = new JFrame("PDU Sequencer");
		desktop.populateDISVersionInfo();
		desktop.createMenuBar(frame);
		desktop.createGUI(frame);
		frame.setVisible(true);
		frame.setSize(1024, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
