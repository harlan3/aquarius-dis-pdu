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

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;

import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;

public class CompositionFile {

	private JTree jTree = null;
	private JTextArea jTextArea = null;

	Stack<MsgContainer> messageStack = null;

	public void generateComposition(JTree jTree, JTextArea jTextArea) {

		this.jTree = jTree;
		this.jTextArea = jTextArea;

		messageStack = new Stack<MsgContainer>();

		buildMsgStack();
		// displayMessageStack();

		generateCompositionFile();
	}

	public void saveCompositionXMLFile(File newFile) {

		try {
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(newFile.toURI()));
			writer.write(jTextArea.getText());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void buildMsgStack() {

		TreeModel model = jTree.getModel();
		if (model != null) {
			Object root = model.getRoot();
			MsgContainer newMsgContainer = new MsgContainer();
			walkCompositionTree(newMsgContainer, model, root);
		} else
			System.out.println("Tree is empty.");
	}

	private void walkCompositionTree(MsgContainer msgContainer, TreeModel model, Object o) {

		int cc;
		cc = model.getChildCount(o);

		for (int i = 0; i < cc; i++) {

			Object child = model.getChild(o, i);

			if (model.isLeaf(child)) {

				try {
					DISPdu disPdu = new DISPdu();
					disPdu.pduName = child.toString();
					disPdu.filePath = SharedData.getInstance().disPdus.get(disPdu.pduName).filePath;
					msgContainer.messageGroup.add(disPdu);
					// System.out.println(child.toString());
				} catch (Exception e) { }
			} else {

				MsgContainer newMsgContainer = new MsgContainer();
				// System.out.println(child.toString() + "--");
				walkCompositionTree(newMsgContainer, model, child);
			}
		}
		messageStack.push(msgContainer);
	}

	@SuppressWarnings("unused")
	private void displayMessageStack() {

		// remove first invalid blank message container
		messageStack.pop();

		System.out.println("Start displayMessageStack");

		while (messageStack.size() > 0) {

			MsgContainer msgContainer = messageStack.pop();

			System.out.println("New Group");

			for (DISPdu disPdu : msgContainer.messageGroup) {

				System.out.println(disPdu.pduName);
				System.out.println(disPdu.filePath);
			}

			System.out.println("End of New Group");
		}
	}

	private void generateCompositionFile() {

		jTextArea.selectAll();
		jTextArea.replaceSelection("");

		jTextArea.append("<?xml version=\"1.0\"?>\n\n");
		jTextArea.append("<!-- Composition of DIS PDUs -->\n");
		jTextArea.append("<composition>\n");

		// remove first invalid blank message container
		messageStack.pop();

		while (messageStack.size() > 0) {

			MsgContainer msgContainer = messageStack.pop();

			jTextArea.append("\n   <!-- DIS PDU Group -->\n");
			jTextArea.append("   <messageGroup>\n");

			for (DISPdu message : msgContainer.messageGroup) {

				jTextArea.append("      <disPdu>\n");
				jTextArea.append("         <name>" + message.pduName + "</name>\n");
				jTextArea.append("         <filePath>" + message.filePath + "</filePath>\n");
				jTextArea.append("         <postDelayMilliseconds>" + SharedData.getInstance().delayBetweenMessages + "</postDelayMilliseconds>\n");
				jTextArea.append("      </disPdu>\n");
			}

			jTextArea.append("   </messageGroup>\n");
		}

		jTextArea.append("\n</composition>\n");
	}

}
