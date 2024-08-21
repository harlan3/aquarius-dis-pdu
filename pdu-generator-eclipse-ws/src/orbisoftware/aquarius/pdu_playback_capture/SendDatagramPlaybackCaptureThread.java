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

package orbisoftware.aquarius.pdu_playback_capture;

import java.net.*;

import java.net.DatagramPacket;
import java.io.IOException;

import javax.swing.event.ChangeEvent;

public class SendDatagramPlaybackCaptureThread extends Thread {

   private final boolean USE_DATAGRAM_SOCKET = true;

   private DatagramSocket datagramSocket = null;
   private MulticastSocket multicastSocket = null;

   private DatagramPacket datagram = null;
   private static SendDatagramPlaybackCaptureThread instance = null;
   private int THREAD_SLEEP_TIME = 500;

   public static SendDatagramPlaybackCaptureThread getInstance() {
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

      PlaybackCaptureUI pduPlayerUI = PlaybackCaptureUI.getInstance();
      PlaybackCaptureData pduPlayerData = PlaybackCaptureData.getInstance();
      Manifest manifest = Manifest.getInstance();
      InetAddress ipAddress = null;
      int currentPDUnumber;

      instance = this;
      initSocket();

      while (true) {

         try {
            if (pduPlayerData.getPlayerActive()) {

               PDUEntry pduEntry = new PDUEntry();

               if (manifest.getNextPDU(pduEntry)) {

                  try {
                     Thread.sleep(pduEntry.packetTimeDelta);
                  } catch (InterruptedException exception) {
                  }

                  // Check to see that player is still active
                  // after the thread finished sleeping.
                  if (pduPlayerData.getPlayerActive()) {

                     ipAddress = InetAddress.getByName(pduPlayerData
                           .getIPAddress());

                     datagram = new DatagramPacket(pduEntry.byteBuffer,
                           pduEntry.byteBuffer.length, ipAddress,
                           pduPlayerData.getPort());

                     if (USE_DATAGRAM_SOCKET)
                        datagramSocket.send(datagram);
                     else
                        multicastSocket.send(datagram);

                     // Generate Change Event to update GUI info
                     ChangeEvent ce = new ChangeEvent(SendDatagramPlaybackCaptureThread.class);
                     pduPlayerUI.stateChanged(ce);

                     // Increment and save currentPDUnumber
                     currentPDUnumber = pduPlayerData.getCurrentPDUnumber();
                     currentPDUnumber++;
                     pduPlayerData.setCurrentPDUnumber(currentPDUnumber);
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
         } catch (IOException exception) {
            System.out.println("Error sending datagram packet");
         }
      }
   }
}
