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

package orbisoftware.aquarius.pdu_heartbeat_generator;

import java.net.*;

import java.net.DatagramPacket;
import java.io.IOException;

public class SendDatagramHeartbeatThread extends Thread {

   private final boolean USE_DATAGRAM_SOCKET = true;
   public static int DATAGRAM_HEARTBEAT = 5000;

   private DatagramSocket datagramSocket = null;
   private MulticastSocket multicastSocket = null;

   private DatagramPacket datagram = null;

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

      HeartbeatGeneratorData packetGeneratorData = HeartbeatGeneratorData
            .getInstance();
      InetAddress ipAddress = null;

      initSocket();

      while (true) {

         try {
            if (packetGeneratorData.getGeneratorActive()
                  && packetGeneratorData.datagramDataValid()) {

               ipAddress = InetAddress.getByName(packetGeneratorData
                     .getIPAddress());

               datagram = new DatagramPacket(
                     packetGeneratorData.getDatagramData(),
                     packetGeneratorData.getDatagramData().length, ipAddress,
                     packetGeneratorData.getPort());

               if (USE_DATAGRAM_SOCKET)
                  datagramSocket.send(datagram);
               else
                  multicastSocket.send(datagram);

               System.out.println("\nSending "
                     + packetGeneratorData.getDatagramData().length + " bytes");
            }
         } catch (IOException exception) {
            System.out.println("Error sending datagram packet");
         }

         try {
            Thread.sleep(DATAGRAM_HEARTBEAT);
         } catch (InterruptedException exception) {
            // Sleep Interrupted Exception occurred
         }
      }
   }
}
