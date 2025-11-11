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

import javax.swing.event.ChangeEvent;

import orbisoftware.aquarius.pdu_common.MainApplication;

import java.io.IOException;

public class SendDatagramPlaybackCaptureThread extends Thread {

   private static DatagramSocket datagramSocket = null;
   private static MulticastSocket multicastSocket = null;
   
   private int portNumber = 0;
   private boolean useMulticast = false;
   private int THREAD_SLEEP_TIME = 500;
   
   private void initSocket() {

      useMulticast = Boolean.parseBoolean(MainApplication.getInstance().xmlMap.get("UseMulticast"));
      portNumber = Integer.parseInt(MainApplication.getInstance().xmlMap.get("PortValue"));

      if (useMulticast) {

         try {

            InetAddress multicastAddress = InetAddress.getByName(MainApplication.getInstance().xmlMap.get("MulticastAddress"));
            InetAddress multicastDeviceAddress = InetAddress.getByName(MainApplication.getInstance().xmlMap.get("MulticastDeviceAddress"));
            multicastSocket = new MulticastSocket(portNumber);

            // Explicitly join the group on the specified interface
            NetworkInterface netIf = NetworkInterface.getByInetAddress(multicastDeviceAddress);
            multicastSocket.setNetworkInterface(netIf);
            multicastSocket.joinGroup(new InetSocketAddress(multicastAddress, portNumber), netIf);
         } catch (Exception e) {
            e.printStackTrace();
         }

      } else {

         try {
            datagramSocket = new DatagramSocket(portNumber);
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

                     if (useMulticast)
                        ipAddress = InetAddress.getByName(MainApplication.getInstance().xmlMap.get("MulticastAddress"));
                     else
                        ipAddress = InetAddress.getByName(MainApplication.getInstance().xmlMap.get("BroadcastAddress"));

                     DatagramPacket datagram = new DatagramPacket(pduEntry.byteBuffer,
                           pduEntry.byteBuffer.length, ipAddress, portNumber);

                     if (useMulticast)
                        multicastSocket.send(datagram);
                     else
                        datagramSocket.send(datagram);

                     // Generate Change Event to update GUI info
                     ChangeEvent ce = new ChangeEvent(SendDatagramPlaybackCaptureThread.class);
                     pduPlayerUI.stateChanged(ce);
                     
                     try {
                        Thread.sleep(2000);
                     } catch (InterruptedException e) { }

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
