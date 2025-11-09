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

import orbisoftware.aquarius.pdu_common.MainApplication;

import java.io.IOException;

public class SendDatagramHeartbeatThread extends Thread {

   private static DatagramSocket datagramSocket = null;
   private static MulticastSocket multicastSocket = null;
   
   private int portNumber = 0;
   private boolean useMulticast = false;
   
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

      HeartbeatGeneratorData packetGeneratorData = HeartbeatGeneratorData
            .getInstance();
      InetAddress ipAddress = null;

      initSocket();

      while (true) {

         try {
            if (packetGeneratorData.getGeneratorActive()) {

               if (useMulticast)
                  ipAddress = InetAddress.getByName(MainApplication.getInstance().xmlMap.get("MulticastAddress"));
               else
                  ipAddress = InetAddress.getByName(MainApplication.getInstance().xmlMap.get("BroadcastAddress"));

               DatagramPacket datagram = new DatagramPacket(
                     packetGeneratorData.getDatagramData(),
                     packetGeneratorData.getDatagramData().length, ipAddress,
                     portNumber);

               if (useMulticast)
                  multicastSocket.send(datagram);
               else
                  datagramSocket.send(datagram);

               System.out.println("\nSending "
                     + packetGeneratorData.getDatagramData().length + " bytes");
            }
         } catch (IOException exception) {
            System.out.println("Error sending datagram packet");
         }

         try {
            Thread.sleep(packetGeneratorData.getHeartbeatInterval());
         } catch (InterruptedException exception) {
            // Sleep Interrupted Exception occurred
         }
      }
   }
}
