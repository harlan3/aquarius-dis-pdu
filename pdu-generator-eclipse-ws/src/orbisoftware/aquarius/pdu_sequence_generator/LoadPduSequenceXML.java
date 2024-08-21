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

package orbisoftware.aquarius.pdu_sequence_generator;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class LoadPduSequenceXML {

	public void loadXML(String fileName) {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
      PduSequenceEntry pduSeqEntry;
      
      int timeStampCount = 0;
      
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(fileName));

			Element element = doc.getDocumentElement();
			NodeList nodes = element.getChildNodes();

			for (int i = 0; i < nodes.getLength(); i++) {
			   
				if (nodes.item(i).getNodeName().equals("messageGroup")) {

					NodeList childNodes = nodes.item(i).getChildNodes();

					for (int j = 0; j < childNodes.getLength(); j++) {

						if (childNodes.item(j).getNodeName().equals("disPdu")) {

						   pduSeqEntry = new PduSequenceEntry();
						   
						   NodeList disFields = childNodes.item(j).getChildNodes();
						   
						   for (int k = 0; k < disFields.getLength(); k++) {
						      
						      if (disFields.item(k).getNodeName().equals("name")) {
						         
						         pduSeqEntry.disPduName = disFields.item(k).getTextContent();
						      }
						      
                        if (disFields.item(k).getNodeName().equals("filePath")) {
                           
                           pduSeqEntry.filePath = disFields.item(k).getTextContent();
                        }
						      
                        if (disFields.item(k).getNodeName().equals("postDelayMilliseconds")) {
                           
                           pduSeqEntry.postDelayMilliseconds = Integer.parseInt(disFields.item(k).getTextContent());
                           pduSeqEntry.timeStamp = Integer.toString(timeStampCount);
                                 
                           timeStampCount += pduSeqEntry.postDelayMilliseconds;
                        }
						   }
						   
						   SharedData.getInstance().pduSeqMsgs.add(pduSeqEntry);				
						}
					}
				}
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
