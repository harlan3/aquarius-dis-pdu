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

public class RepairResponse {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(25);
      
      byte pduType = 0;
      short length = 0;
      short receiveEntIDSite = 0;
      short receiveEntIDApp = 0;
      short receiveEntIDEntity = 0;
      short repairEntIDSite = 0;
      short repairEntIDApp = 0;
      short repairEntIDEntity = 0;
      byte repairResult = 0;
      @SuppressWarnings("unused")
      short padding1 = 0;
      @SuppressWarnings("unused")
      byte padding2 = 0;

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
         receiveEntIDSite = din.readShort();
         receiveEntIDApp = din.readShort();
         receiveEntIDEntity = din.readShort();
         repairEntIDSite = din.readShort();
         repairEntIDApp = din.readShort();
         repairEntIDEntity = din.readShort();
         repairResult = din.readByte();
         padding1 = din.readShort();
         padding2 = din.readByte();

         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("receiveEntIDSite") + receiveEntIDSite);
         System.out.println(LoggerUtil.prettyPrintField("receiveEntIDApp") + receiveEntIDApp);
         System.out.println(LoggerUtil.prettyPrintField("receiveEntIDEntity") + receiveEntIDEntity);
         System.out.println(LoggerUtil.prettyPrintField("repairEntIDSite") + repairEntIDSite);
         System.out.println(LoggerUtil.prettyPrintField("repairEntIDApp") + repairEntIDApp);
         System.out.println(LoggerUtil.prettyPrintField("repairEntIDEntity") + repairEntIDEntity);
         System.out.println(LoggerUtil.prettyPrintField("repairResult") + repairResult);

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
