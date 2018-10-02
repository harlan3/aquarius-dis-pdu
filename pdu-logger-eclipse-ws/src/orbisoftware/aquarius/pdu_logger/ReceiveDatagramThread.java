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

package orbisoftware.aquarius.pdu_logger;

import java.net.*;
import java.io.IOException;
import java.beans.*;

public class ReceiveDatagramThread extends Thread {

   private final int MAX_PACKET_SIZE = 1500;
   private InetAddress multicastGroupAddress;

   private DatagramSocket dataGramSocket = null;
   private MulticastSocket multicastSocket = null;
   private PropertyChangeSupport propertyChangeSupport;

   public ReceiveDatagramThread() {
      propertyChangeSupport = new PropertyChangeSupport(this);
   }

   public PropertyChangeSupport getPropertyChangeSupport() {
      return propertyChangeSupport;
   }

   private void initSocket() {

      PDULoggerConfig pduLoggerConfig = PDULoggerConfig.getInstance();

      if (pduLoggerConfig.getUseMulticast()) {

         try {
            multicastGroupAddress = InetAddress.getByName(pduLoggerConfig
                  .getMulticastGroupAddress());
            multicastSocket = new MulticastSocket(
                  pduLoggerConfig.getPortNumber());
            multicastSocket.joinGroup(multicastGroupAddress);
         } catch (Exception e) {
            e.printStackTrace();
         }

      } else {

         try {
            dataGramSocket = new DatagramSocket(pduLoggerConfig.getPortNumber());
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

   public void run() {

      PDULoggerConfig pduLoggerConfig = PDULoggerConfig.getInstance();
      byte[] buffer = new byte[MAX_PACKET_SIZE];

      initSocket();

      while (true) {

         DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);

         try {
            if (pduLoggerConfig.getUseMulticast()) {
               multicastSocket.receive(incoming);
            } else {
               dataGramSocket.receive(incoming);
            }

            propertyChangeSupport.firePropertyChange("datagramReceived", 0,
                  incoming);

         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
}
