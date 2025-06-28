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

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Date;

import orbisoftware.aquarius.pdu_logger.*;

public class Appearance {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(30);

      byte protocolVersion = 0;
      byte exercise = 0;
      byte pduType = 0;
      byte family = 0;
      short length = 0;
      byte subprotolNum = 0;
      byte liveEntIDSiteNumber = 0;
      byte liveEntIDAppNumber = 0;
      short liveEntIDEntNumber = 0;
      byte appearanceFlag1;
      byte appearanceFlag2;
      byte forceID;
      byte entTypeEntKind = 0;
      byte entTypeDomain = 0;
      short entTypeCountry = 0;
      byte entTypeCategory = 0;
      byte entTypeSubCategory = 0;
      byte entTypeSpecific = 0;
      byte entTypeExtra = 0;
      byte altEntityTypeEntKind = 0;
      byte altEntityTypeDomain = 0;
      short altEntityTypeCountry = 0;
      byte altEntityTypeCategory = 0;
      byte altEntityTypeSubCat = 0;
      byte altEntityTypeSpecific = 0;
      byte altEntityTypeExtra = 0;
      byte entityMarkCharSet = 0;
      byte entityMarkData[] = new byte[11];
      int capabilities = 0;
      int appearanceVisual = 0;
      int appearanceIR = 0;
      int appearanceEM = 0;
      int appearanceAudio = 0;
      
      try {

         /* Start Message Header */
         protocolVersion = din.readByte();
         exercise = din.readByte();
         pduType = din.readByte();
         family = din.readByte();
         din.reset();

         din.skipBytes(8);
         length = din.readShort();
         subprotolNum = din.readByte();
         din.reset();
         /* End Message Header */

         din.skipBytes(12);
         liveEntIDSiteNumber = din.readByte();
         liveEntIDAppNumber = din.readByte();
         liveEntIDEntNumber = din.readShort();
         appearanceFlag1 = din.readByte();
         appearanceFlag2 = din.readByte();
         forceID = din.readByte();
         entTypeEntKind = din.readByte();
         entTypeDomain = din.readByte();
         entTypeCountry = din.readShort();
         entTypeCategory = din.readByte();
         entTypeSubCategory = din.readByte();
         entTypeSpecific = din.readByte();
         entTypeExtra = din.readByte();
         altEntityTypeEntKind = din.readByte();
         altEntityTypeDomain = din.readByte();
         altEntityTypeCountry = din.readShort();
         altEntityTypeCategory = din.readByte();
         altEntityTypeSubCat = din.readByte();
         altEntityTypeSpecific = din.readByte();
         altEntityTypeExtra = din.readByte();
         entityMarkCharSet = din.readByte();
         entityMarkData = din.readNBytes(11);
         capabilities = din.readInt();
         appearanceVisual = din.readInt();
         appearanceIR = din.readInt();
         appearanceEM = din.readInt();
         appearanceAudio = din.readInt();
         
         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("subprotolNum") + subprotolNum);
         System.out.println(LoggerUtil.prettyPrintField("liveEntIDSiteNumber") + liveEntIDSiteNumber);
         System.out.println(LoggerUtil.prettyPrintField("liveEntIDAppNumber") + liveEntIDAppNumber);
         System.out.println(LoggerUtil.prettyPrintField("liveEntIDEntNumber") + liveEntIDEntNumber);
         System.out.println(LoggerUtil.prettyPrintField("appearanceFlag1") + LoggerUtil.toBitString(appearanceFlag1));
         System.out.println(LoggerUtil.prettyPrintField("appearanceFlag2") + LoggerUtil.toBitString(appearanceFlag2));
         System.out.println(LoggerUtil.prettyPrintField("forceID") + forceID);
         System.out.println(LoggerUtil.prettyPrintField("entTypeEntKind") + entTypeEntKind);
         System.out.println(LoggerUtil.prettyPrintField("entTypeDomain") + entTypeDomain);
         System.out.println(LoggerUtil.prettyPrintField("entTypeCountry") + entTypeCountry);
         System.out.println(LoggerUtil.prettyPrintField("entTypeCategory") + entTypeCategory);
         System.out.println(LoggerUtil.prettyPrintField("entTypeSubCategory") + LoggerUtil.unsignedByteToInt(entTypeSubCategory));
         System.out.println(LoggerUtil.prettyPrintField("entTypeSpecific") + entTypeSpecific);
         System.out.println(LoggerUtil.prettyPrintField("entTypeExtra") + entTypeExtra);
         System.out.println(LoggerUtil.prettyPrintField("altEntityTypeEntKind") + altEntityTypeEntKind);
         System.out.println(LoggerUtil.prettyPrintField("altEntityTypeDomain") + altEntityTypeDomain);
         System.out.println(LoggerUtil.prettyPrintField("altEntityTypeCountry") + altEntityTypeCountry);
         System.out.println(LoggerUtil.prettyPrintField("altEntityTypeCategory") + altEntityTypeCategory);
         System.out.println(LoggerUtil.prettyPrintField("altEntityTypeSubCat") + altEntityTypeSubCat);
         System.out.println(LoggerUtil.prettyPrintField("altEntityTypeSpecific") + altEntityTypeSpecific);
         System.out.println(LoggerUtil.prettyPrintField("altEntityTypeExtra") + altEntityTypeExtra);
         System.out.println(LoggerUtil.prettyPrintField("entityMarkCharSet") + entityMarkCharSet);
         System.out.println(LoggerUtil.prettyPrintField("entityMarkData") + entityMarkData);
         System.out.println(LoggerUtil.prettyPrintField("capabilities") + LoggerUtil.toBitString(capabilities));
         System.out.println(LoggerUtil.prettyPrintField("appearanceVisual") + LoggerUtil.toBitString(appearanceVisual));
         System.out.println(LoggerUtil.prettyPrintField("appearanceIR") + LoggerUtil.toBitString(appearanceIR));
         System.out.println(LoggerUtil.prettyPrintField("appearanceEM") + LoggerUtil.toBitString(appearanceEM));
         System.out.println(LoggerUtil.prettyPrintField("appearanceAudio") + LoggerUtil.toBitString(appearanceAudio));
         
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
