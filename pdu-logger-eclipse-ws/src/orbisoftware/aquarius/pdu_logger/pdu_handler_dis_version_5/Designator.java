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

public class Designator {

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
      short padding0 = 0;
      short designatingEntitySite = 0;
      short designatingEntityApp = 0;
      short designatingEntityID = 0;
      short codeName = 0;
      short designatedEntitySite = 0;
      short designatedEntityApp = 0;
      short designatedEntityID = 0;
      short designatorCode = 0;
      float designatorPower = 0.0f;
      float designatorWavelength = 0.0f;
      float designatorSpotX = 0.0f;
      float designatorSpotY = 0.0f;
      float designatorSpotZ = 0.0f;
      double designatorSpotLocX = 0.0;
      double designatorSpotLocY = 0.0;
      double designatorSpotLocZ = 0.0;
      byte deadReckonAlgorithm = 0;
      short padding1 = 0;
      byte padding2 = 0;
      float entityLinearAccelX = 0.0f;
      float entityLinearAccelY = 0.0f;
      float entityLinearAccelZ = 0.0f;

      try {

         /* Start Message Header */
         protocolVersion = din.readByte();
         exercise = din.readByte();
         pduType = din.readByte();
         family = din.readByte();
         din.reset();

         din.skipBytes(8);
         length = din.readShort();
         din.reset();
         /* End Message Header */

         din.skipBytes(12);
         designatingEntitySite = din.readShort();
         designatingEntityApp = din.readShort();
         designatingEntityID = din.readShort();
         codeName = din.readShort();
         designatedEntitySite = din.readShort();
         designatedEntityApp = din.readShort();
         designatedEntityID = din.readShort();
         designatorCode = din.readShort();
         designatorPower = din.readFloat();
         designatorWavelength = din.readFloat();
         designatorSpotX = din.readFloat();
         designatorSpotY = din.readFloat();
         designatorSpotZ = din.readFloat();
         designatorSpotLocX = din.readDouble();
         designatorSpotLocY = din.readDouble();
         designatorSpotLocZ = din.readDouble();
         deadReckonAlgorithm = din.readByte();
         padding1 = din.readShort();
         padding2 = din.readByte();
         entityLinearAccelX = din.readFloat();
         entityLinearAccelY = din.readFloat();
         entityLinearAccelZ = din.readFloat();

         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("designatingEntitySite") + designatingEntitySite);
         System.out.println(LoggerUtil.prettyPrintField("designatingEntityApp") + designatingEntityApp);
         System.out.println(LoggerUtil.prettyPrintField("designatingEntityID") + designatingEntityID);
         System.out.println(LoggerUtil.prettyPrintField("codeName") + codeName);
         System.out.println(LoggerUtil.prettyPrintField("designatedEntitySite") + designatedEntitySite);
         System.out.println(LoggerUtil.prettyPrintField("designatedEntityApp") + designatedEntityApp);
         System.out.println(LoggerUtil.prettyPrintField("designatedEntityID") + designatedEntityID);
         System.out.println(LoggerUtil.prettyPrintField("designatorCode") + designatorCode);
         System.out.println(LoggerUtil.prettyPrintField("designatorPower") + designatorPower);
         System.out.println(LoggerUtil.prettyPrintField("designatorWavelength") + designatorWavelength);
         System.out.println(LoggerUtil.prettyPrintField("designatorSpotX") + designatorSpotX);
         System.out.println(LoggerUtil.prettyPrintField("designatorSpotY") + designatorSpotY);
         System.out.println(LoggerUtil.prettyPrintField("designatorSpotZ") + designatorSpotZ);
         System.out.println(LoggerUtil.prettyPrintField("designatorSpotLocX") + designatorSpotLocX);
         System.out.println(LoggerUtil.prettyPrintField("designatorSpotLocY") + designatorSpotLocY);
         System.out.println(LoggerUtil.prettyPrintField("designatorSpotLocZ") + designatorSpotLocZ);
         System.out.println(LoggerUtil.prettyPrintField("deadReckonAlgorithm") + deadReckonAlgorithm);
         System.out.println(LoggerUtil.prettyPrintField("entityLinearAccelX") + entityLinearAccelX);
         System.out.println(LoggerUtil.prettyPrintField("entityLinearAccelY") + entityLinearAccelY);
         System.out.println(LoggerUtil.prettyPrintField("entityLinearAccelZ") + entityLinearAccelZ);

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
