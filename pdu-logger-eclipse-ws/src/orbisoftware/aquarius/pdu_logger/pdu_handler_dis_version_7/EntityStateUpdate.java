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

public class EntityStateUpdate {

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
      short entIDSiteNum = 0;
      short entIDAppNum = 0;
      short entIDEntNum = 0;
      byte padding = 0;
      byte numberVariableParams = 0;
      float entityLinearVelocityX = 0;
      float entityLinearVelocityY = 0;
      float entityLinearVelocityZ = 0;
      double entityLocationX = 0;
      double entityLocationY = 0;
      double entityLocationZ = 0;
      float entityOrientationPsi = 0;
      float entityOrientationTheta = 0;
      float entityOrientationPhi = 0;
      int entityAppearance = 0;

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
         entIDSiteNum = din.readShort();
         entIDAppNum = din.readShort();
         entIDEntNum = din.readShort();
         padding = din.readByte();
         numberVariableParams = din.readByte();
         entityLinearVelocityX = din.readFloat();
         entityLinearVelocityY = din.readFloat();
         entityLinearVelocityZ = din.readFloat();
         entityLocationX = din.readDouble();
         entityLocationY = din.readDouble();
         entityLocationZ = din.readDouble();
         entityOrientationPsi = din.readFloat();
         entityOrientationTheta = din.readFloat();
         entityOrientationPhi = din.readFloat();
         entityAppearance = din.readInt();

         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("siteNum") + entIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("appNum") + entIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("entNum") + entIDEntNum);
         System.out.println(LoggerUtil.prettyPrintField("numberVariableParams") + numberVariableParams);
         System.out.println(LoggerUtil.prettyPrintField("entityLinearVelocityX") + entityLinearVelocityX);
         System.out.println(LoggerUtil.prettyPrintField("entityLinearVelocityY") + entityLinearVelocityY);
         System.out.println(LoggerUtil.prettyPrintField("entityLinearVelocityZ") + entityLinearVelocityZ);
         System.out.println(LoggerUtil.prettyPrintField("entityLocationX") + entityLocationX);
         System.out.println(LoggerUtil.prettyPrintField("entityLocationY") + entityLocationY);
         System.out.println(LoggerUtil.prettyPrintField("entityLocationZ") + entityLocationZ);
         System.out.println(LoggerUtil.prettyPrintField("entityOrientationPsi") + entityOrientationPsi);
         System.out.println(LoggerUtil.prettyPrintField("entityOrientationTheta") + entityOrientationTheta);
         System.out.println(LoggerUtil.prettyPrintField("entityOrientationPhi") + entityOrientationPhi);
         System.out.println(LoggerUtil.prettyPrintField("entityAppearance") + LoggerUtil.toBitString(entityAppearance));

         for (int y = 0; y < numberVariableParams; y++) {

            byte variableParamRecordType = din.readByte();
            byte variableParamRecordRecSpecFields[] = new byte[15];
            
            variableParamRecordRecSpecFields = din.readNBytes(15);
            
            System.out.println(LoggerUtil.prettyPrintField("variableParamRecordType") + variableParamRecordType);
            System.out.print(LoggerUtil.prettyPrintField("variableParamRecordRecSpecFields"));
            
            for (int i = 0; i < 15; i++) {
               
               System.out.print(variableParamRecordRecSpecFields[i] + " ");
            }
            
            System.out.println();
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
