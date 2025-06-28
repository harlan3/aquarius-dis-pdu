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

public class SupplementalEmissionEntityState {

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
      byte pduStatus = 0;
      short origEntIDSite = 0;
      short origEntIDApp = 0;
      short origEntIDEnt = 0;
      short infraredSigRepIndex = 0;
      short acousticSigRepIndex = 0;
      short radarCrossSectSigRepIndex = 0;
      short numPropulsionSystems = 0;
      short numVectorNozzleSystems = 0;

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
         origEntIDSite = din.readShort();
         origEntIDApp = din.readShort();
         origEntIDEnt = din.readShort();
         infraredSigRepIndex = din.readShort();
         acousticSigRepIndex = din.readShort();
         radarCrossSectSigRepIndex = din.readShort();
         numPropulsionSystems = din.readShort();
         numVectorNozzleSystems = din.readShort();

         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("origEntIDSite") + origEntIDSite);
         System.out.println(LoggerUtil.prettyPrintField("origEntIDApp") + origEntIDApp);
         System.out.println(LoggerUtil.prettyPrintField("origEntIDEnt") + origEntIDEnt);
         System.out.println(LoggerUtil.prettyPrintField("infraredSigRepIndex") + infraredSigRepIndex);
         System.out.println(LoggerUtil.prettyPrintField("acousticSigRepIndex") + acousticSigRepIndex);
         System.out.println(LoggerUtil.prettyPrintField("radarCrossSectSigRepIndex") + radarCrossSectSigRepIndex);
         System.out.println(LoggerUtil.prettyPrintField("numPropulsionSystems") + numPropulsionSystems);
         System.out.println(LoggerUtil.prettyPrintField("numVectorNozzleSystems") + numVectorNozzleSystems);

         for (int i=0; i < numPropulsionSystems; i++) {
            
            float powerSetting = 0.0f;
            float engineRPM = 0.0f;
            
            powerSetting = din.readFloat();
            engineRPM = din.readFloat();
            
            System.out.println(LoggerUtil.prettyPrintField("powerSetting") + powerSetting);
            System.out.println(LoggerUtil.prettyPrintField("engineRPM") + engineRPM);
         }
         
         for (int j=0; j < numVectorNozzleSystems; j++) {
            
            float horzDeflectionAngle = 0.0f;
            float vertDeflectionAngle = 0.0f;
            
            horzDeflectionAngle = din.readFloat();
            vertDeflectionAngle = din.readFloat();
          
            System.out.println(LoggerUtil.prettyPrintField("horzDeflectionAngle") + horzDeflectionAngle);
            System.out.println(LoggerUtil.prettyPrintField("vertDeflectionAngle") + vertDeflectionAngle);
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
