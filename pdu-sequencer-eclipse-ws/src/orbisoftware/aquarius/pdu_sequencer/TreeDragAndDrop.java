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

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.DropMode;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class TreeDragAndDrop {

	private JTree jTree = null;

	public TreeDragAndDrop() {

	}

	void serializeTree(File selectedFile) {

		try {
			FileOutputStream file = new FileOutputStream(selectedFile);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(jTree.getModel());
			file.close();
		} catch (Exception e) {
		}
	}

	void deserializationTree(File selectedFile) {

		try {
			FileInputStream file = new FileInputStream(selectedFile);
			ObjectInputStream in = new ObjectInputStream(file);
			jTree.setModel((TreeModel) in.readObject());
			file.close();
		} catch (Exception e) {
		}
	}

	void setupTree(JTree jTree) {

		this.jTree = jTree;

		jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
		jTree.setDropMode(DropMode.ON);

		TreePopupMenu treePopupMenu = new TreePopupMenu(jTree);
		treePopupMenu.setupPopupMenu();

		jTree.setTransferHandler(new TransferHandler() {

			private static final long serialVersionUID = 25369857535L;

			public boolean canImport(TransferHandler.TransferSupport info) {

				// Do not support clipboard paste
				if (!info.isDrop()) {
					return false;
				}

				info.setShowDropLocation(true);

				// we only import Strings
				if (!info.isDataFlavorSupported(DataFlavor.stringFlavor)) {
					return false;
				}

				// fetch the drop location
				JTree.DropLocation dl = (JTree.DropLocation) info.getDropLocation();
				TreePath path = dl.getPath();

				// we don't support invalid paths or descendants of the names folder
				if (path == null) {
					return false;
				}

				// ignore drop for composition root and leaf nodes
				if ((path.getPathCount() == 1) || (path.getPathCount() == 3))
					return false;

				// System.out.println(path.toString());

				return true;
			}

			public boolean importData(TransferHandler.TransferSupport info) {

				// obtain the model from JTree in case it has changed through serialization
				DefaultTreeModel treeModel = (DefaultTreeModel) jTree.getModel();

				// if we can't handle the import, say so
				if (!canImport(info)) {
					return false;
				}

				// fetch the drop location
				JTree.DropLocation dl = (JTree.DropLocation) info.getDropLocation();

				// fetch the path and child index from the drop location
				TreePath path = dl.getPath();
				int childIndex = dl.getChildIndex();

				// fetch the data and bail if this fails
				String data;
				try {
					data = (String) info.getTransferable().getTransferData(DataFlavor.stringFlavor);
				} catch (UnsupportedFlavorException e) {
					return false;
				} catch (IOException e) {
					return false;
				}

				// if child index is -1, the drop was on top of the path, so we'll
				// treat it as inserting at the end of that path's list of children
				if (childIndex == -1) {
					childIndex = jTree.getModel().getChildCount(path.getLastPathComponent());
				}

				// create a new node to represent the data and insert it into the model
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(data);
				DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) path.getLastPathComponent();
				treeModel.insertNodeInto(newNode, parentNode, childIndex);

				// make the new node visible and scroll so that it's visible
				jTree.makeVisible(path.pathByAddingChild(newNode));
				jTree.scrollRectToVisible(jTree.getPathBounds(path.pathByAddingChild(newNode)));

				return true;
			}
		});
	}
}
