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

public class TimeSpacePositionInformation {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(50);
      
      byte pduType = 0;
      byte family = 0;
      short length = 0;
      byte subProtocolNumber = 0;
      byte liveEntityIDsiteNumber = 0;
      byte liveEntityIDappNumber = 0;
      short liveEntityIDentNumber = 0;
      byte tspiFlag = 0;
      short entLocationRefPoint = 0;
      short entLocationDeltaX = 0;
      short entLocationDeltaY = 0;
      short entLocationDeltaZ = 0;
      short entLinearVelXComp = 0;
      short entLinearVelYComp = 0;
      short entLinearVelZComp = 0;
      byte entOrientPsi = 0;
      byte entOrientTheta = 0;
      byte entOrientPhi = 0;
      short positionErrorHorz = 0;
      short positionErrorVert = 0;
      short orientErrorAzimuth = 0;
      short orientErrorElev = 0;
      short orientErrorRotation = 0;
      byte deadReckoningParamsAlgor = 0;
      byte deadRockingParamsEntityLinearAccelerationFirst = 0;
      byte deadRockingParamsEntityLinearAccelerationSecond = 0;
      byte deadRockingParamsEntityLinearAccelerationThird = 0;
      byte deadRockingParamsEntityAngularVelocityFirst = 0;
      byte deadRockingParamsEntityAngularVelocitySecond = 0;
      byte deadRockingParamsEntityAngularVelocityThird = 0;
      short measuredSpeed = 0;
      byte systemSpecificDataLength = 0;
      
      try {

         /* Start Message Header */
         din.skipBytes(2);
         pduType = din.readByte();
         family = din.readByte();
         din.reset();

         din.skipBytes(8);
         length = din.readShort();
         subProtocolNumber = din.readByte();
         din.reset();
         /* End Message Header */

         din.skipBytes(12);
         
         liveEntityIDsiteNumber = din.readByte();
         liveEntityIDappNumber = din.readByte();
         liveEntityIDentNumber = din.readShort();
         tspiFlag = din.readByte();
         entLocationRefPoint = din.readShort();
         entLocationDeltaX = din.readShort();
         entLocationDeltaY = din.readShort();
         entLocationDeltaZ = din.readShort();
         entLinearVelXComp = din.readShort();
         entLinearVelYComp = din.readShort();
         entLinearVelZComp = din.readShort();
         entOrientPsi = din.readByte();
         entOrientTheta = din.readByte();
         entOrientPhi = din.readByte();
         positionErrorHorz = din.readShort();
         positionErrorVert = din.readShort();
         orientErrorAzimuth = din.readShort();
         orientErrorElev = din.readShort();
         orientErrorRotation = din.readShort();
         deadReckoningParamsAlgor = din.readByte();
         deadRockingParamsEntityLinearAccelerationFirst = din.readByte();
         deadRockingParamsEntityLinearAccelerationSecond = din.readByte();
         deadRockingParamsEntityLinearAccelerationThird = din.readByte();
         deadRockingParamsEntityAngularVelocityFirst = din.readByte();
         deadRockingParamsEntityAngularVelocitySecond = din.readByte();
         deadRockingParamsEntityAngularVelocityThird = din.readByte();
         measuredSpeed = din.readShort();
         systemSpecificDataLength = din.readByte();

         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("subProtocolNumber") + subProtocolNumber);
         System.out.println(LoggerUtil.prettyPrintField("liveEntityIDsiteNumber") + liveEntityIDsiteNumber);
         System.out.println(LoggerUtil.prettyPrintField("liveEntityIDappNumber") + liveEntityIDappNumber);
         System.out.println(LoggerUtil.prettyPrintField("liveEntityIDentNumber") + liveEntityIDentNumber);
         System.out.println(LoggerUtil.prettyPrintField("tspiFlag") + LoggerUtil.toBitString(tspiFlag));
         System.out.println(LoggerUtil.prettyPrintField("entLocationRefPoint") + entLocationRefPoint);
         System.out.println(LoggerUtil.prettyPrintField("entLocationDeltaX") + entLocationDeltaX);
         System.out.println(LoggerUtil.prettyPrintField("entLocationDeltaY") + entLocationDeltaY);
         System.out.println(LoggerUtil.prettyPrintField("entLocationDeltaZ") + entLocationDeltaZ);
         System.out.println(LoggerUtil.prettyPrintField("entLinearVelXComp") + entLinearVelXComp);
         System.out.println(LoggerUtil.prettyPrintField("entLinearVelYComp") + entLinearVelYComp);
         System.out.println(LoggerUtil.prettyPrintField("entLinearVelZComp") + entLinearVelZComp);
         System.out.println(LoggerUtil.prettyPrintField("entOrientPsi") + entOrientPsi);
         System.out.println(LoggerUtil.prettyPrintField("entOrientTheta") + entOrientTheta);
         System.out.println(LoggerUtil.prettyPrintField("entOrientPhi") + entOrientPhi);
         System.out.println(LoggerUtil.prettyPrintField("positionErrorHorz") + positionErrorHorz);
         System.out.println(LoggerUtil.prettyPrintField("positionErrorVert") + positionErrorVert);
         System.out.println(LoggerUtil.prettyPrintField("orientErrorAzimuth") + orientErrorAzimuth);
         System.out.println(LoggerUtil.prettyPrintField("orientErrorElev") + orientErrorElev);
         System.out.println(LoggerUtil.prettyPrintField("orientErrorRotation") + orientErrorRotation);
         System.out.println(LoggerUtil.prettyPrintField("deadReckoningParamsAlgor") + deadReckoningParamsAlgor);
         System.out.println(LoggerUtil.prettyPrintField("deadRockingParamsEntityLinearAccelerationFirst") + deadRockingParamsEntityLinearAccelerationFirst);
         System.out.println(LoggerUtil.prettyPrintField("deadRockingParamsEntityLinearAccelerationSecond") + deadRockingParamsEntityLinearAccelerationSecond);
         System.out.println(LoggerUtil.prettyPrintField("deadRockingParamsEntityLinearAccelerationThird") + deadRockingParamsEntityLinearAccelerationThird);
         System.out.println(LoggerUtil.prettyPrintField("deadRockingParamsEntityAngularVelocityFirst") + deadRockingParamsEntityAngularVelocityFirst);
         System.out.println(LoggerUtil.prettyPrintField("deadRockingParamsEntityAngularVelocitySecond") + deadRockingParamsEntityAngularVelocitySecond);
         System.out.println(LoggerUtil.prettyPrintField("deadRockingParamsEntityAngularVelocityThird") + deadRockingParamsEntityAngularVelocityThird);
         System.out.println(LoggerUtil.prettyPrintField("measuredSpeed") + measuredSpeed);
         System.out.println(LoggerUtil.prettyPrintField("systemSpecificDataLength") + systemSpecificDataLength);
         
         for (int i=0; i < systemSpecificDataLength; i++) {
            
            byte systemSpecificData = 0;
            
            systemSpecificData = din.readByte();
            
            System.out.println(LoggerUtil.prettyPrintField("systemSpecificData") + systemSpecificData);
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
