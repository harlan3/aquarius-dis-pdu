package orbisoftware.aquarius.pdu_common;
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

import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import orbisoftware.aquarius.pdu_heartbeat_generator.HeartbeatGeneratorUI;
import orbisoftware.aquarius.pdu_heartbeat_generator.SendDatagramHeartbeatThread;
import orbisoftware.aquarius.pdu_playback_capture.PlaybackCaptureUI;
import orbisoftware.aquarius.pdu_playback_capture.SendDatagramPlaybackCaptureThread;
import orbisoftware.aquarius.pdu_sequence_generator.SendDatagramSeqGenThread;
import orbisoftware.aquarius.pdu_sequence_generator.SequenceGeneratorUI;

public class MainApplication {
   
   private static MainApplication instance = null;
   public HashMap<String, String> xmlMap = new HashMap<String, String>();
   
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
   
   public static void main(String[] args) {

      javax.swing.SwingUtilities.invokeLater(new Runnable() {

         public void run() {

            MainApplication mainApplication = MainApplication.getInstance();
            
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
            
            // Initalize application socket
            SharedSocketInterface.getInstance().initSocket();
            
            // Start pdu heartbeat generator send datagram thread
            SendDatagramHeartbeatThread sendDatagramHeartbeatThread = new SendDatagramHeartbeatThread();
            sendDatagramHeartbeatThread.start();
            
            // Start pdu sequence generator datagram thread
            SendDatagramSeqGenThread sendDatagramSeqGenThread = new SendDatagramSeqGenThread();
            sendDatagramSeqGenThread.start();
            
            // Start pdu playback capture thread
            SendDatagramPlaybackCaptureThread sendDatagramPlaybackCaptureThread = new SendDatagramPlaybackCaptureThread();
            sendDatagramPlaybackCaptureThread.start();
            
            JFrame jFrame = new JFrame("Aquarius PDU Generation");
            JTabbedPane tabbedPane = new JTabbedPane();
            
            HeartbeatGeneratorUI packetGeneratorUI = HeartbeatGeneratorUI.getInstance();
            SequenceGeneratorUI sequenceGeneratorUI = SequenceGeneratorUI.getInstance();
            PlaybackCaptureUI playbackCaptureUI = PlaybackCaptureUI.getInstance();
            playbackCaptureUI.setSendDatagramThread(sendDatagramPlaybackCaptureThread);
            
            boolean useMulticast = Boolean.parseBoolean(MainApplication.getInstance().xmlMap.get("UseMulticast"));
            String multicastAddress = MainApplication.getInstance().xmlMap.get("MulticastAddress");
            String multicastDeviceAddress = MainApplication.getInstance().xmlMap.get("MulticastDeviceAddress");
            String broadcastAddress = MainApplication.getInstance().xmlMap.get("BroadcastAddress");
            String port = MainApplication.getInstance().xmlMap.get("PortValue");
            int exerciseID = Integer.parseInt(MainApplication.getInstance().xmlMap.get("ExcerciseID"));
                  
            System.out.println("       Listening on port: " + port);

            if (useMulticast) {
               
               System.out.println("       Multicast Address: " + multicastAddress);
               System.out.println("        Multicast Device: " + multicastDeviceAddress);
            } else {
               
               System.out.println("       Broadcast Address: " + broadcastAddress);
            }
               
            if (exerciseID != 0)
               System.out.println("     Publish on Exercise: " + exerciseID);

            JComponent panel1 = packetGeneratorUI.buildPacketGeneratorPanel();
            JComponent panel2 = sequenceGeneratorUI.buildSequenceGeneratorPanel();
            JComponent panel3 = playbackCaptureUI.buildPlaybackCapturePanel();
            
            // Close operation when the window is closed
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.setSize(325, 400);
            jFrame.setVisible(true);

            jFrame.add(tabbedPane);
            tabbedPane.addTab("Heartbeat", panel1);
            tabbedPane.addTab("Sequence", panel2);
            tabbedPane.addTab("Db Playback", panel3);
            
         }
      });
   }
}
