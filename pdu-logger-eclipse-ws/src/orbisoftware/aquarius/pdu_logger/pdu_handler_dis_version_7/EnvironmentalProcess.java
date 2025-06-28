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

public class EnvironmentalProcess {

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
      short envProcIDSiteNum = 0;
      short envProcIDAppNum = 0;
      short envProcIDObjNum = 0;
      byte entityTypeEntKind = 0;
      byte entityTypeDomain = 0;
      short entityTypeCountry = 0;
      byte entityTypeCategory = 0;
      byte entityTypeSubCat = 0;
      byte entityTypeSpecific = 0;
      byte entityTypeExtra = 0;
      byte modelType = 0;
      byte enviroStatus = 0;
      short numEnvRecords = 0;
      short sequenceNum = 0;

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
         envProcIDSiteNum = din.readShort();
         envProcIDAppNum = din.readShort();
         envProcIDObjNum = din.readShort();
         entityTypeEntKind = din.readByte();
         entityTypeDomain = din.readByte();
         entityTypeCountry = din.readShort();
         entityTypeCategory = din.readByte();
         entityTypeSubCat = din.readByte();
         entityTypeSpecific = din.readByte();
         entityTypeExtra = din.readByte();
         modelType = din.readByte();
         enviroStatus = din.readByte();
         numEnvRecords = din.readShort();
         sequenceNum = din.readShort();
         
         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("envProcIDSiteNum") + envProcIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("envProcIDAppNum") + envProcIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("envProcIDObjNum") + envProcIDObjNum);
         System.out.println(LoggerUtil.prettyPrintField("entityTypeEntKind") + entityTypeEntKind);
         System.out.println(LoggerUtil.prettyPrintField("entityTypeDomain") + entityTypeDomain);
         System.out.println(LoggerUtil.prettyPrintField("entityTypeCountry") + entityTypeCountry);
         System.out.println(LoggerUtil.prettyPrintField("entityTypeCategory") + entityTypeCategory);
         System.out.println(LoggerUtil.prettyPrintField("entityTypeSubCat") + entityTypeSubCat);
         System.out.println(LoggerUtil.prettyPrintField("entityTypeSpecific") + entityTypeSpecific);
         System.out.println(LoggerUtil.prettyPrintField("entityTypeExtra") + entityTypeExtra);
         System.out.println(LoggerUtil.prettyPrintField("modelType") + modelType);
         System.out.println(LoggerUtil.prettyPrintField("enviroStatus") + enviroStatus);
         System.out.println(LoggerUtil.prettyPrintField("numEnvRecords") + numEnvRecords);
         System.out.println(LoggerUtil.prettyPrintField("sequenceNum") + sequenceNum);

         for (int y = 0; y < numEnvRecords; y++) {

            int envRecordType = 0;
            short envRecordLength = 0;
            byte envRecordIndex = 0;
            byte padding;
            byte stateOrGeometryRec[] = new byte[1];
            
            envRecordType = din.readInt();
            envRecordLength = din.readShort();
            envRecordIndex = din.readByte();
            padding = din.readByte();
            stateOrGeometryRec = din.readNBytes(1);
            
            System.out.println(LoggerUtil.prettyPrintField("envRecordType") + envRecordType);
            System.out.println(LoggerUtil.prettyPrintField("envRecordLength") + envRecordLength);
            System.out.println(LoggerUtil.prettyPrintField("envRecordIndex") + envRecordIndex);
            System.out.println(LoggerUtil.prettyPrintField("stateOrGeometryRec") + stateOrGeometryRec[0]);
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
