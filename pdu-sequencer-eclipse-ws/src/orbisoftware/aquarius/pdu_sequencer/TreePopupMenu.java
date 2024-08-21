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

import java.awt.event.*;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class TreePopupMenu {

	JTree jTree = null;
	DefaultTreeModel treeModel = null;

	JMenuItem addMessageGroup;
	JMenuItem deleteNode;

	public TreePopupMenu(JTree jTree) {
		this.jTree = jTree;
		this.treeModel = (DefaultTreeModel) jTree.getModel();
	}

	public void setupPopupMenu() {

		final JPopupMenu editMenu = new JPopupMenu("Edit");

		addMessageGroup = new JMenuItem("Add Message Group");
		addMessageGroup.setActionCommand("add");

		deleteNode = new JMenuItem("Delete Node");
		deleteNode.setActionCommand("delete");

		MenuItemListener menuItemListener = new MenuItemListener();

		addMessageGroup.addActionListener(menuItemListener);
		deleteNode.addActionListener(menuItemListener);

		editMenu.add(addMessageGroup);
		editMenu.add(deleteNode);

		jTree.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent e) {

				if (e.isMetaDown()) {

					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();

					if (selectedNode != null) {

						if (selectedNode.toString().equals("Composition Root")) {
							addMessageGroup.setVisible(true);
							deleteNode.setVisible(false);
						} else {
							addMessageGroup.setVisible(false);
							deleteNode.setVisible(true);
						}

						editMenu.show(jTree, e.getX(), e.getY());
					}
				}
			}
		});
	}

	class MenuItemListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			// obtain the model from JTree in case it has changed through serialization
			treeModel = (DefaultTreeModel) jTree.getModel();

			if (e.getActionCommand().equals("add")) {

				int childIndex = 0;

				// create a new node to represent the data and insert it into the model
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("Message Group");
				DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) treeModel.getRoot();

				treeModel.insertNodeInto(newNode, parentNode, childIndex);

				TreeNode[] path = newNode.getPath();
				TreePath tp = new TreePath(path);

				// make the new node visible and scroll so that it's visible
				jTree.makeVisible(tp);

				System.out.println(e.getActionCommand() + " MenuItem clicked.");

			} else if (e.getActionCommand().equals("delete")) {

				TreePath[] paths = jTree.getSelectionPaths();
				if (paths != null) {
					for (TreePath path : paths) {
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
						if (node.getParent() != null) {
							treeModel.removeNodeFromParent(node);
						}
					}
				}
			}

		}
	}
}