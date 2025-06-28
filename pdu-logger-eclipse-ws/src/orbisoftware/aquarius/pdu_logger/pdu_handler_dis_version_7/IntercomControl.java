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

public class IntercomControl {

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
      byte controlType = 0;
      byte commChannelType = 0;
      short sourceIntercomRefIDSiteNum = 0;
      short sourceIntercomRefIDAppNum = 0;
      short sourceIntercomRefIDRefNum = 0;
      short sourceIntercomNum = 0;
      byte sourceLineID = 0;
      byte transmitPriority = 0;
      byte transmitLineState = 0;
      byte command = 0;
      short masterIntercomRefIDSiteNum = 0;
      short masterIntercomRefIDAppNum = 0;
      short masterIntercomRefIDRefNum = 0;
      short masterIntercomNumber = 0;
      short masterChannelID = 0;
      int intercomParamsLength = 0;
      
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
         
         controlType = din.readByte();
         commChannelType = din.readByte();
         sourceIntercomRefIDSiteNum = din.readShort();
         sourceIntercomRefIDAppNum = din.readShort();
         sourceIntercomRefIDRefNum = din.readShort();
         sourceIntercomNum = din.readShort();
         sourceLineID = din.readByte();
         transmitPriority = din.readByte();
         transmitLineState = din.readByte();
         command = din.readByte();
         masterIntercomRefIDSiteNum = din.readShort();
         masterIntercomRefIDAppNum = din.readShort();
         masterIntercomRefIDRefNum = din.readShort();
         masterIntercomNumber = din.readShort();
         masterChannelID = din.readShort();
         intercomParamsLength = din.readInt();
         
         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("controlType") + controlType);
         System.out.println(LoggerUtil.prettyPrintField("commChannelType") + commChannelType);
         System.out.println(LoggerUtil.prettyPrintField("sourceIntercomRefIDSiteNum") + sourceIntercomRefIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("sourceIntercomRefIDAppNum") + sourceIntercomRefIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("sourceIntercomRefIDRefNum") + sourceIntercomRefIDRefNum);
         System.out.println(LoggerUtil.prettyPrintField("sourceIntercomNum") + sourceIntercomNum);
         System.out.println(LoggerUtil.prettyPrintField("sourceLineID") + sourceLineID);
         System.out.println(LoggerUtil.prettyPrintField("transmitPriority") + transmitPriority);
         System.out.println(LoggerUtil.prettyPrintField("transmitLineState") + transmitLineState);
         System.out.println(LoggerUtil.prettyPrintField("command") + command);
         System.out.println(LoggerUtil.prettyPrintField("masterIntercomRefIDSiteNum") + masterIntercomRefIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("masterIntercomRefIDAppNum") + masterIntercomRefIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("masterIntercomRefIDRefNum") + masterIntercomRefIDRefNum);
         System.out.println(LoggerUtil.prettyPrintField("masterIntercomNumber") + masterIntercomNumber);
         System.out.println(LoggerUtil.prettyPrintField("masterChannelID") + masterChannelID);
         System.out.println(LoggerUtil.prettyPrintField("intercomParamsLength") + intercomParamsLength);
         
         for (int i=0; i < intercomParamsLength; i++) {
            
            short intercomParamsRecordType = 0;
            short intercomParamsRecordLenth = 0;
            
            intercomParamsRecordType = din.readShort();
            intercomParamsRecordLenth = din.readShort();
            
            System.out.println(LoggerUtil.prettyPrintField("intercomParamsRecordType") + intercomParamsRecordType);
            System.out.println(LoggerUtil.prettyPrintField("intercomParamsRecordLenth") + intercomParamsRecordLenth);
            
            for (int j=0; j < intercomParamsRecordLenth; j++) {
               
               byte intercomParamsRecordSpecFields = 0;
               
               intercomParamsRecordSpecFields = din.readByte();
               
               System.out.println(LoggerUtil.prettyPrintField("intercomParamsRecordSpecFields") + intercomParamsRecordSpecFields);
               
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
