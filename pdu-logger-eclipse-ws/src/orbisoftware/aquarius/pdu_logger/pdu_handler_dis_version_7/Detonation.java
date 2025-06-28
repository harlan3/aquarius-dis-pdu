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

public class Detonation {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(25);
     
      byte protocolVersion = 0;
      byte exercise = 0;
      byte pduType = 0;
      byte family = 0;
      short length = 0;
      byte pduStatus = 0;
      short sourceEntIDSiteNum = 0;
      short sourceEntIDAppNum = 0;
      short sourceEntIDEntNum = 0;
      short targetEntIDSiteNum = 0;
      short targetEntIDAppNum = 0;
      short targetEntIDEntNum = 0;
      short explodEntIDSiteNum = 0;
      short explodEntIDAppNum = 0;
      short explodEntIDEntNum = 0;
      short eventIDSiteNum = 0;
      short eventIDAppNum = 0;
      short eventIDEventNum = 0;
      float velocityX = 0.0f;
      float velocityY = 0.0f;
      float velocityZ = 0.0f;
      double locationWCX = 0.0;
      double locationWCY = 0.0;
      double locationWCZ = 0.0;
      long descripEntityTypeRec = 0;
      long descripRecordFields = 0;
      float locationECX = 0.0f;
      float locationECY = 0.0f;
      float locationECZ = 0.0f;
      byte detonationRes = 0;
      byte numVarParamRecs = 0;
      short padding;
      
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
         sourceEntIDSiteNum = din.readShort();
         sourceEntIDAppNum = din.readShort();
         sourceEntIDEntNum = din.readShort();
         targetEntIDSiteNum = din.readShort();
         targetEntIDAppNum = din.readShort();
         targetEntIDEntNum = din.readShort();
         explodEntIDSiteNum = din.readShort();
         explodEntIDAppNum = din.readShort();
         explodEntIDEntNum = din.readShort();
         eventIDSiteNum = din.readShort();
         eventIDAppNum = din.readShort();
         eventIDEventNum = din.readShort();
         velocityX = din.readFloat();
         velocityY = din.readFloat();
         velocityZ = din.readFloat();
         locationWCX = din.readDouble();
         locationWCY = din.readDouble();
         locationWCZ = din.readDouble();
         descripEntityTypeRec = din.readLong();
         descripRecordFields = din.readLong();
         locationECX = din.readFloat();
         locationECY = din.readFloat();
         locationECZ = din.readFloat();
         detonationRes = din.readByte();
         numVarParamRecs = din.readByte();
         padding = din.readShort();
         
         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("sourceEntIDSiteNum") + sourceEntIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("sourceEntIDAppNum") + sourceEntIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("sourceEntIDEntNum") + sourceEntIDEntNum);
         System.out.println(LoggerUtil.prettyPrintField("targetEntIDSiteNum") + targetEntIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("targetEntIDAppNum") + targetEntIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("targetEntIDEntNum") + targetEntIDEntNum);
         System.out.println(LoggerUtil.prettyPrintField("explodEntIDSiteNum") + explodEntIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("explodEntIDAppNum") + explodEntIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("explodEntIDEntNum") + explodEntIDEntNum);
         System.out.println(LoggerUtil.prettyPrintField("eventIDSiteNum") + eventIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("eventIDAppNum") + eventIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("eventIDEventNum") + eventIDEventNum);
         System.out.println(LoggerUtil.prettyPrintField("velocityX") + velocityX);
         System.out.println(LoggerUtil.prettyPrintField("velocityY") + velocityY);
         System.out.println(LoggerUtil.prettyPrintField("velocityZ") + velocityZ);
         System.out.println(LoggerUtil.prettyPrintField("locationWCX") + locationWCX);
         System.out.println(LoggerUtil.prettyPrintField("locationWCY") + locationWCY);
         System.out.println(LoggerUtil.prettyPrintField("locationWCZ") + locationWCZ);
         System.out.println(LoggerUtil.prettyPrintField("descripEntityTypeRec") + descripEntityTypeRec);
         System.out.println(LoggerUtil.prettyPrintField("descripRecordFields") + descripRecordFields);
         System.out.println(LoggerUtil.prettyPrintField("locationECX") + locationECX);
         System.out.println(LoggerUtil.prettyPrintField("locationECY") + locationECY);
         System.out.println(LoggerUtil.prettyPrintField("locationECZ") + locationECZ);
         System.out.println(LoggerUtil.prettyPrintField("detonationRes") + detonationRes);
         System.out.println(LoggerUtil.prettyPrintField("numVarParamRecs") + numVarParamRecs);

         for (int i = 0; i < numVarParamRecs; i++) {

            byte recordType = 0;
            byte recordSpecFields[] = new byte[15];
            
            recordType = din.readByte();
            recordSpecFields = din.readNBytes(15);

            System.out.println(LoggerUtil.prettyPrintField("recordType") + recordType);
            System.out.print(LoggerUtil.prettyPrintField("recordSpecificFields"));

            for (int j = 0; j < 14; j++)
               System.out.print(recordSpecFields[j] + " ");
           
            System.out.println(recordSpecFields[14]);
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
