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

package orbisoftware.aquarius.pdu_logger.pdu_handler_dis_version_7;

import orbisoftware.aquarius.pdu_logger.*;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Date;

import java.net.*;

public class StopFreezeR {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(20);
      
      byte protocolVersion = 0;
      byte exercise = 0;
      byte pduType = 0;
      byte family = 0;
      short length = 0;
      byte pduStatus = 0;
      short origIDSite = 0;
      short origIDApp = 0;
      short origIDRef = 0;
      short recIDSite = 0;
      short recIDApp = 0;
      short recIDRef = 0;
      int rwtHour = 0;
      int rwtTimePastHour = 0;
      byte reason = 0;
      byte frozenBehavior = 0;
      byte reqReliabilityServ = 0;
      byte padding = 0;
      int requestID = 0;

      try {

         /* Start Message Header */
         protocolVersion = din.readByte();
         exercise = din.readByte();
         pduType = din.readByte();
         family = din.readByte();
         din.reset();

         din.skipBytes(8);
         length = din.readShort();
         pduStatus = din.readByte();
         din.reset();
         /* End Message Header */

         din.skipBytes(12);
         origIDSite = din.readShort();
         origIDApp = din.readShort();
         origIDRef = din.readShort();
         recIDSite = din.readShort();
         recIDApp = din.readShort();
         recIDRef = din.readShort();
         rwtHour = din.readInt();
         rwtTimePastHour = din.readInt();
         reason = din.readByte();
         frozenBehavior = din.readByte();
         reqReliabilityServ = din.readByte();
         padding = din.readByte();
         requestID = din.readInt();

         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("origIDSite") + origIDSite);
         System.out.println(LoggerUtil.prettyPrintField("origIDApp") + origIDApp);
         System.out.println(LoggerUtil.prettyPrintField("origIDRef") + origIDRef);
         System.out.println(LoggerUtil.prettyPrintField("recIDSite") + recIDSite);
         System.out.println(LoggerUtil.prettyPrintField("recIDApp") + recIDApp);
         System.out.println(LoggerUtil.prettyPrintField("recIDRef") + recIDRef);
         System.out.println(LoggerUtil.prettyPrintField("rwtHour") + rwtHour);
         System.out.println(LoggerUtil.prettyPrintField("rwtTimePastHour") + rwtTimePastHour);
         System.out.println(LoggerUtil.prettyPrintField("reason") + reason);
         System.out.println(LoggerUtil.prettyPrintField("frozenBehavior") + frozenBehavior);
         System.out.println(LoggerUtil.prettyPrintField("reqReliabilityServ") + reqReliabilityServ);
         System.out.println(LoggerUtil.prettyPrintField("requestID") + requestID);

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
