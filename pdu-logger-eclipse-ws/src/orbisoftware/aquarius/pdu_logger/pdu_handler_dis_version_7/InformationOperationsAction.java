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

public class InformationOperationsAction {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(35);

      byte protocolVersion = 0;
      byte exercise = 0;
      byte pduType = 0;
      byte family = 0;
      short length = 0;
      byte pduStatus = 0;
      short origSimIDSiteNum = 0;
      short origSimIDAppNum = 0;
      short origSimIDRefNum = 0;
      short recSimIDSiteNum = 0;
      short recSimIDAppNum = 0;
      short recSimIDRefNum = 0;
      int requestID = 0;
      short ioWarfareType = 0;
      short ioSimSource = 0;
      short ioActionType = 0;
      short ioActionPhase = 0;
      int padding1;
      short ioAttackEntIDSiteNum = 0;
      short ioAttackEntIDAppNum = 0;
      short ioAttackEntIDEntNum = 0;
      short ioPrimaryTargEntIDSiteNum = 0;
      short ioPrimaryTargEntIDAppNum = 0;
      short ioPrimaryTargEntIDEntNum = 0;
      short padding2;
      short numberIORecs = 0;
      
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
         
         origSimIDSiteNum = din.readShort();
         origSimIDAppNum = din.readShort();
         origSimIDRefNum = din.readShort();
         recSimIDSiteNum = din.readShort();
         recSimIDAppNum = din.readShort();
         recSimIDRefNum = din.readShort();
         requestID = din.readInt();
         ioWarfareType = din.readShort();
         ioSimSource = din.readShort();
         ioActionType = din.readShort();
         ioActionPhase = din.readShort();
         padding1 = din.readInt();
         ioAttackEntIDSiteNum = din.readShort();
         ioAttackEntIDAppNum = din.readShort();
         ioAttackEntIDEntNum = din.readShort();
         ioPrimaryTargEntIDSiteNum = din.readShort();
         ioPrimaryTargEntIDAppNum = din.readShort();
         ioPrimaryTargEntIDEntNum = din.readShort();
         padding2 = din.readShort();
         numberIORecs = din.readShort();
         
         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("origSimIDSiteNum") + origSimIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("origSimIDAppNum") + origSimIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("origSimIDRefNum") + origSimIDRefNum);
         System.out.println(LoggerUtil.prettyPrintField("recSimIDSiteNum") + recSimIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("recSimIDAppNum") + recSimIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("recSimIDRefNum") + recSimIDRefNum);
         System.out.println(LoggerUtil.prettyPrintField("requestID") + requestID);
         System.out.println(LoggerUtil.prettyPrintField("ioWarfareType") + ioWarfareType);
         System.out.println(LoggerUtil.prettyPrintField("ioSimSource") + ioSimSource);
         System.out.println(LoggerUtil.prettyPrintField("ioActionType") + ioActionType);
         System.out.println(LoggerUtil.prettyPrintField("ioActionPhase") + ioActionPhase);
         System.out.println(LoggerUtil.prettyPrintField("ioAttackEntIDSiteNum") + ioAttackEntIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("ioAttackEntIDAppNum") + ioAttackEntIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("ioAttackEntIDEntNum") + ioAttackEntIDEntNum);
         System.out.println(LoggerUtil.prettyPrintField("ioPrimaryTargEntIDSiteNum") + ioPrimaryTargEntIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("ioPrimaryTargEntIDAppNum") + ioPrimaryTargEntIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("ioPrimaryTargEntIDEntNum") + ioPrimaryTargEntIDEntNum);
         System.out.println(LoggerUtil.prettyPrintField("numberIORecs") + numberIORecs);
         
         for (int i=0; i<numberIORecs; i++) {
            
            int recordType = 0;
            short recordLength = 0;
            
            recordType = din.readInt();
            recordLength = din.readShort();
            
            System.out.println(LoggerUtil.prettyPrintField("recordType") + recordType);
            System.out.println(LoggerUtil.prettyPrintField("recordLength") + recordLength);
            
            for (int j=0; j<recordLength; j++) {
               
               byte recordSpecFields = 0;
               
               recordSpecFields = din.readByte();
               
               System.out.println(LoggerUtil.prettyPrintField("recordSpecFields") + recordSpecFields);
            }
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
