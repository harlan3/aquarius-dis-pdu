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

public class EntityState {

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
      byte forceID = 0;
      byte numberVariableParams = 0;
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
      byte deadReckParamsAlgor = 0;
      byte deadReckParamsOthers[] = new byte[15];
      float deadReckonEntityLinearAccelX = 0;
      float deadReckonEntityLinearAccelY = 0;
      float deadReckonEntityLinearAccelZ = 0;
      float deadReckonEntityAngularVelocityX = 0;
      float deadReckonEntityAngularVelocityY = 0;
      float deadReckonEntityAngularVelocityZ = 0;
      byte entityMarkingCharacterSet = 0;
      StringBuffer entityMarking = new StringBuffer(11);
      int capabilities = 0;

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
         forceID = din.readByte();
         numberVariableParams = din.readByte();
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
         deadReckParamsAlgor = din.readByte();
         deadReckParamsOthers = din.readNBytes(15); 
         deadReckonEntityLinearAccelX = din.readFloat();
         deadReckonEntityLinearAccelY = din.readFloat();
         deadReckonEntityLinearAccelZ = din.readFloat();
         deadReckonEntityAngularVelocityX = din.readFloat();
         deadReckonEntityAngularVelocityY = din.readFloat();
         deadReckonEntityAngularVelocityZ = din.readFloat();
         entityMarkingCharacterSet = din.readByte();
         
         for (int x = 0; x < 11; x++) {

            byte currByte = din.readByte();

            if ((currByte >= ' ') && (currByte < 127))
               entityMarking.append((char) currByte);
         }

         capabilities = din.readInt();

         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("siteNum") + entIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("appNum") + entIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("entNum") + entIDEntNum);
         System.out.println(LoggerUtil.prettyPrintField("forceID") + forceID);
         System.out.println(LoggerUtil.prettyPrintField("numberVariableParams") + numberVariableParams);
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
         System.out.println(LoggerUtil.prettyPrintField("deadReckonAlgor") + deadReckParamsAlgor);
         
         System.out.print(LoggerUtil.prettyPrintField("deadReckParamsOthers"));
         
         for (int i = 0; i < 15; i++) {
            
            System.out.print(deadReckParamsOthers[i] + " ");
         }
         
         System.out.println();
         
         System.out.println(LoggerUtil.prettyPrintField("deadReckonEntityLinearAccelX") + deadReckonEntityLinearAccelX);
         System.out.println(LoggerUtil.prettyPrintField("deadReckonEntityLinearAccelY") + deadReckonEntityLinearAccelY);         
         System.out.println(LoggerUtil.prettyPrintField("deadReckonEntityLinearAccelZ") +  deadReckonEntityLinearAccelZ);
         System.out.println(LoggerUtil.prettyPrintField("deadReckonEntityAngularVelocityX") + deadReckonEntityAngularVelocityX);
         System.out.println(LoggerUtil.prettyPrintField("deadReckonEntityAngularVelocityY") +  deadReckonEntityAngularVelocityY);
         System.out.println(LoggerUtil.prettyPrintField("deadReckonEntityAngularVelocityZ") + deadReckonEntityAngularVelocityZ);
         System.out.println(LoggerUtil.prettyPrintField("entityMarkingCharacterSet") + entityMarkingCharacterSet);
         System.out.println(LoggerUtil.prettyPrintField("entityMarking") + entityMarking);
         System.out.println(LoggerUtil.prettyPrintField("capabilities") + LoggerUtil.toBitString(capabilities));

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
