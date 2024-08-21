package orbisoftware.aquarius.pdu_heartbeat_generator;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class LoadCompXMLFile {

	public void loadMsgJar() {

		InputStream inputStream = getClass().getResourceAsStream("/JSeriesMsgs.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;

		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputStream);

			Element element = doc.getDocumentElement();
			NodeList nodes = element.getChildNodes();

			for (int i = 0; i < nodes.getLength(); i++) {
				if (nodes.item(i).getNodeName().equals("JMsg")) {

					NodeList childNodes = nodes.item(i).getChildNodes();

					String msgName = "";

					for (int j = 0; j < childNodes.getLength(); j++) {

						if (!childNodes.item(j).getNodeName().equals("#text")) {

							String nodeName = childNodes.item(j).getNodeName();
							String nodeValue = childNodes.item(j).getTextContent();

							if (nodeName.equals("MsgName"))
								msgName = nodeValue;
							else if (nodeName.equals("ClassName")) {
								//JSeriesMsg jseriesMsg = new JSeriesMsg();

								//jseriesMsg.msgName = msgName;
								//jseriesMsg.className = nodeValue;

								//SharedData.getInstance().jseriesMsgs.put(msgName, jseriesMsg);
							}
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
