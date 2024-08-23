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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;

public class LoadDISPdus {
	
	private OSValidator osValidator = new OSValidator();

	public void addActionListener(JComboBox loadMsgButton, DefaultListModel<String> listModel) {

		loadMsgButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				loadDISPdus(listModel, loadMsgButton.getSelectedItem().toString());
			}
		});
	}

	public void loadDISPdus(DefaultListModel<String> listModel, String selectedItem) {

		listModel.clear();

		try {

			File[] pduFiles = new File("../pdu-generator-eclipse-ws" + File.separator + selectedItem).listFiles(File::isFile);
			
			for (int i=0; i<pduFiles.length; i++) {
			
				if (pduFiles[i].toString().endsWith(".xml")) {
					
					DISPdu disPdu = new DISPdu();
					
					String strTokens[] = pduFiles[i].toString().split(Pattern.quote(File.separator));
					disPdu.pduName = strTokens[strTokens.length - 1].replaceAll(".xml", "");
					String filePath = pduFiles[i].toString();
					filePath = filePath.replaceAll(".*pdu-generator-eclipse-ws.*dis_version", "dis_version");
					// Use forward slash for paths in XML file
					if (osValidator.isWindows())
						filePath = filePath.replaceAll(Pattern.quote(File.separator), "/");
					disPdu.filePath = filePath;
					
					SharedData.getInstance().disPdus.put(disPdu.pduName, disPdu);
					listModel.addElement(disPdu.pduName);
				}
			}
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
