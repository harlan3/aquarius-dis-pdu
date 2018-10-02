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

package orbisoftware.aquarius.pdu_logger.pdu_handler;

import orbisoftware.aquarius.pdu_logger.PDU_Type;
import orbisoftware.aquarius.pdu_logger.HexDump;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Date;

import java.net.*;

public class Designator {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(
            packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();

      byte pduType = 0;
      short length = 0;
      @SuppressWarnings("unused")
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
      @SuppressWarnings("unused")
      short padding1 = 0;
      @SuppressWarnings("unused")
      byte padding2 = 0;
      float entityLinearAccelX = 0.0f;
      float entityLinearAccelY = 0.0f;
      float entityLinearAccelZ = 0.0f;

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

         System.out.println("              pduType : "
               + PDU_Type.values()[pduType]);
         System.out.println("               length : " + length);
         System.out.println("designatingEntitySite : " + designatingEntitySite);
         System.out.println(" designatingEntityApp : " + designatingEntityApp);
         System.out.println("  designatingEntityID : " + designatingEntityID);
         System.out.println("             codeName : " + codeName);
         System.out.println(" designatedEntitySite : " + designatedEntitySite);
         System.out.println("  designatedEntityApp : " + designatedEntityApp);
         System.out.println("  designatedEntityID  : " + designatedEntityID);
         System.out.println("       designatorCode : " + designatorCode);
         System.out.println("      designatorPower : " + designatorPower);
         System.out.println(" designatorWavelength : " + designatorWavelength);
         System.out.println("      designatorSpotX : " + designatorSpotX);
         System.out.println("      designatorSpotY : " + designatorSpotY);
         System.out.println("      designatorSpotZ : " + designatorSpotZ);
         System.out.println("   designatorSpotLocX : " + designatorSpotLocX);
         System.out.println("   designatorSpotLocY : " + designatorSpotLocY);
         System.out.println("   designatorSpotLocZ : " + designatorSpotLocZ);
         System.out.println("  deadReckonAlgorithm : " + deadReckonAlgorithm);
         System.out.println("   entityLinearAccelX : " + entityLinearAccelX);
         System.out.println("   entityLinearAccelY : " + entityLinearAccelY);
         System.out.println("   entityLinearAccelZ : " + entityLinearAccelZ);

         /* Verify that the length defined in PDU matches what was received */
         if (length != packet.getLength()) {
            System.out.println("\nWARNING: Reported PDU length is incorrect!");
            System.out.println("         Read " + packet.getLength() + "     "
                  + "Reported: " + length);
         }

         /* The following code is required for pdu logger */
         System.out.println();
         System.out.println("      PDU counter: " + pduCounter);
         System.out.println("Local packet time: " + date.getTime());
         System.out.println(HexDump.dump(packet.getData(), 0, 0,
               packet.getLength()));

      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
