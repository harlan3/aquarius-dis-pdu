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

public class RecordQueryR {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(30);

      byte pduType = 0;
      byte family = 0;
      short length = 0;
      byte pduStatus = 0;
      short origIDSiteNum = 0;
      short origIDAppNum = 0;
      short origIDRefNum = 0;
      short recIDSiteNum = 0;
      short recIDAppNum = 0;
      short recIDRefNum = 0;
      int requestID = 0;
      byte requiredReliabilityServ = 0;
      byte padding = 0;
      short eventType = 0;
      int time = 0;
      int numRecords = 0;

      try {

         /* Start Message Header */
         din.skipBytes(2);
         pduType = din.readByte();
         family = din.readByte();
         din.reset();

         din.skipBytes(8);
         length = din.readShort();
         pduStatus = din.readByte();
         din.reset();
         /* End Message Header */

         din.skipBytes(12);
         origIDSiteNum = din.readShort();
         origIDAppNum = din.readShort();
         origIDRefNum = din.readShort();
         recIDSiteNum = din.readShort();
         recIDAppNum = din.readShort();
         recIDRefNum = din.readShort();
         requestID = din.readInt();
         requiredReliabilityServ = din.readByte();
         padding = din.readByte();
         eventType = din.readShort();
         time = din.readInt();
         numRecords = din.readInt();

         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("origIDSiteNum") + origIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("origIDAppNum") + origIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("origIDRefNum") + origIDRefNum);
         System.out.println(LoggerUtil.prettyPrintField("recIDSiteNum") + recIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("recIDAppNum") + recIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("recIDRefNum") + recIDRefNum);
         System.out.println(LoggerUtil.prettyPrintField("requestID") + requestID);
         System.out.println(LoggerUtil.prettyPrintField("requiredReliabilityServ") + requiredReliabilityServ);
         System.out.println(LoggerUtil.prettyPrintField("eventType") + eventType);
         System.out.println(LoggerUtil.prettyPrintField("time") + time);
         System.out.println(LoggerUtil.prettyPrintField("numRecords") + numRecords);

         for (int i=0; i < numRecords; i++) {
            
            int recordID = 0;
            
            recordID = din.readInt();
            
            System.out.println(LoggerUtil.prettyPrintField("recordID") + recordID);
         }
         
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
