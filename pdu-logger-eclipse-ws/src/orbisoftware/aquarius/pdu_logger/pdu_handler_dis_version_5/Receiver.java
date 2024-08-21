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

package orbisoftware.aquarius.pdu_logger.pdu_handler_dis_version_5;

import orbisoftware.aquarius.pdu_logger.*;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Date;

import java.net.*;

public class Receiver {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(30);

      byte pduType = 0;
      short length = 0;
      short site = 0;
      short application = 0;
      short entity = 0;
      short radioID = 0;
      short receiverState = 0;
      @SuppressWarnings("unused")
      short padding = 0;
      float receivedPower = 0;
      short transEntityIDSite = 0;
      short transEntityIDApp = 0;
      short transEntityIDEntity = 0;
      short transRadioID = 0;

      try {

         /* Start Message Header */
         din.skipBytes(2);
         pduType = din.readByte();
         din.reset();

         din.skipBytes(8);
         length = din.readShort();
         din.reset();
         /* End Message Header */

         din.skipBytes(12);
         site = din.readShort();
         application = din.readShort();
         entity = din.readShort();
         radioID = din.readShort();
         receiverState = din.readShort();
         padding = din.readShort();
         receivedPower = din.readFloat();
         transEntityIDSite = din.readShort();
         transEntityIDApp = din.readShort();
         transEntityIDEntity = din.readShort();
         transRadioID = din.readShort();

         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("site") + site);
         System.out.println(LoggerUtil.prettyPrintField("application") + application);
         System.out.println(LoggerUtil.prettyPrintField("entity") + entity);
         System.out.println(LoggerUtil.prettyPrintField("radioID") + radioID);
         System.out.println(LoggerUtil.prettyPrintField("receiverState") + receiverState);
         System.out.println(LoggerUtil.prettyPrintField("receivedPower") + receivedPower);
         System.out.println(LoggerUtil.prettyPrintField("transEntityIDSite") + transEntityIDSite);
         System.out.println(LoggerUtil.prettyPrintField("transEntityIDApp") + transEntityIDApp);
         System.out.println(LoggerUtil.prettyPrintField("transEntityIDEntity") + transEntityIDEntity);
         System.out.println(LoggerUtil.prettyPrintField("transRadioID") + transRadioID);

         /* Verify that the length defined in PDU matches what was received */
         if (length != packet.getLength()) {
            System.out.println("\nWARNING: Reported PDU length is incorrect!");
            System.out.println("         Read " + packet.getLength() + "     " + "Reported: " + length);
         }

         /* The following code is required for pdu logger */
         System.out.println();
         System.out.println("      PDU counter: " + pduCounter);
         System.out.println("Local packet time: " + date.getTime());
         System.out.println(HexDump.dump(packet.getData(), 0, 0, packet.getLength()));

      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
