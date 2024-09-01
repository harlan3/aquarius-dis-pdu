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

import java.net.*;

import java.net.DatagramPacket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;

import javax.swing.event.ChangeEvent;

import org.xml.sax.XMLReader;

import orbisoftware.aquarius.pdu_heartbeat_generator.XMLPacketHandler;
import orbisoftware.aquarius.pdu_playback_capture.SendDatagramPlaybackCaptureThread;

public class SendDatagramSeqGenThread extends Thread {

   private static Path currentWorkingDir = Paths.get("").toAbsolutePath().getParent();
   private final boolean USE_DATAGRAM_SOCKET = true;

   private DatagramSocket datagramSocket = null;
   private MulticastSocket multicastSocket = null;

   private DatagramPacket datagram = null;
   private static SendDatagramSeqGenThread instance = null;
   private int THREAD_SLEEP_TIME = 500;
   
   public static SendDatagramSeqGenThread getInstance() {
      return instance;
   }

   private void initSocket() {

      if (USE_DATAGRAM_SOCKET) {

         try {
            datagramSocket = new DatagramSocket();
         } catch (Exception e) {
            e.printStackTrace();
         }

      } else {

         try {
            multicastSocket = new MulticastSocket();
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

   public void run() {

      SequenceGeneratorUI pduPlayerUI = SequenceGeneratorUI.getInstance();
      SequenceGeneratorData pduPlayerData = SequenceGeneratorData.getInstance();
      PduSequenceAccess pduSequenceAccess = PduSequenceAccess.getInstance();
      InetAddress ipAddress = null;
      int currentPDUnumber;

      instance = this;
      initSocket();

      while (true) {

         try {
            if (pduPlayerData.getPlayerActive()) {

               PduSequenceEntry pduSequenceEntry = new PduSequenceEntry();

               if (pduSequenceAccess.getNextPDU(pduSequenceEntry)) {

                  // Check to see that player is still active
                  // after the thread finished sleeping.
                  if (pduPlayerData.getPlayerActive()) {
                     
                     XMLReader parser = org.xml.sax.helpers.XMLReaderFactory
                           .createXMLReader();

                     // Create a new instance and register it with the parser
                     XMLPacketHandler packetHandler = new XMLPacketHandler();
                     parser.setContentHandler(packetHandler);

                     // Parse packet
                     parser.parse(currentWorkingDir + File.separator + "pdu-generator-eclipse-ws" + 
                           File.separator + pduSequenceEntry.filePath);

                     ipAddress = InetAddress.getByName(pduPlayerData
                           .getIPAddress());

                     datagram = new DatagramPacket(packetHandler.getPacketData(),
                           packetHandler.getPacketData().length, ipAddress,
                           pduPlayerData.getPort());

                     if (USE_DATAGRAM_SOCKET)
                        datagramSocket.send(datagram);
                     else
                        multicastSocket.send(datagram);
                     
                     try {
                        Thread.sleep(pduSequenceEntry.postDelayMilliseconds);
                     } catch (InterruptedException exception) {
                     }  
                     
                     // Increment and save currentPDUnumber
                     currentPDUnumber = pduPlayerData.getCurrentPDUnumber();
                     
                     // Update
                     if (currentPDUnumber < SharedData.getInstance().pduSeqMsgs.size()-1) {
                        
                        currentPDUnumber++;
                        pduPlayerData.setCurrentPDUnumber(currentPDUnumber);
                        
                        // Generate Change Event to update GUI info
                        ChangeEvent ce = new ChangeEvent(SendDatagramSeqGenThread.class);
                        pduPlayerUI.stateChanged(ce);
                     } else {
                        
                        if (pduPlayerData.getLoopPlayback()) {
                           SequenceGeneratorUI.getInstance().resetStartingDisplay();
                           pduPlayerData.setCurrentPDUnumber(0);
                           
                           // Generate Change Event to update GUI info
                           ChangeEvent ce = new ChangeEvent(SendDatagramSeqGenThread.class);
                           pduPlayerUI.stateChanged(ce);
                        }
                        else {                        
                           pduPlayerData.setPlayerActive(false);
                           SequenceGeneratorUI.getInstance().pushStopButton();
                        }
                     }
                  }
               } else {
                  try {
                     Thread.sleep(THREAD_SLEEP_TIME);
                  } catch (InterruptedException exception) {
                  }
               }
            } else {
               try {
                  Thread.sleep(THREAD_SLEEP_TIME);
               } catch (InterruptedException exception) {
               }
            }
         } catch (Exception exception) {
            System.out.println("Error sending datagram packet");
         }
      }
   }
}
