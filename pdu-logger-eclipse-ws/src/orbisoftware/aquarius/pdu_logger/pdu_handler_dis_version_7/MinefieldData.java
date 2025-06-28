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

public class MinefieldData {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(45);

      byte protocolVersion = 0;
      byte exercise = 0;
      byte pduType = 0;
      byte family = 0;
      short length = 0;
      byte pduStatus = 0;
      short minefieldIDSiteNum = 0;
      short minefieldIDAppNum = 0;
      short minefieldIDMinefieldNum = 0;
      short requestSimIDSiteNum = 0;
      short requestSimIDAppNum = 0;
      short requestSimIDRefNum = 0;
      short minefieldSeqNum = 0;
      byte requestID = 0;
      byte pduSeqNum = 0;
      byte numPDUs = 0;
      byte numMines = 0;
      byte numSensorTypes = 0;
      byte padding1 = 0;
      int dataFilter = 0;
      byte mineTypeEntKind = 0;
      byte mineTypeDomain = 0;
      short mineTypeCountry = 0;
      byte mineTypeCategory = 0;
      byte mineTypeSubCat = 0;
      byte mineTypeSpecific = 0;
      byte mineTypeExtra = 0;
      
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
         
         minefieldIDSiteNum = din.readShort();
         minefieldIDAppNum = din.readShort();
         minefieldIDMinefieldNum = din.readShort();
         requestSimIDSiteNum = din.readShort();
         requestSimIDAppNum = din.readShort();
         requestSimIDRefNum = din.readShort();
         minefieldSeqNum = din.readShort();
         requestID = din.readByte();
         pduSeqNum = din.readByte();
         numPDUs = din.readByte();
         numMines = din.readByte();
         numSensorTypes = din.readByte();
         padding1 = din.readByte();
         dataFilter = din.readInt();
         mineTypeEntKind = din.readByte();
         mineTypeDomain = din.readByte();
         mineTypeCountry = din.readShort();
         mineTypeCategory = din.readByte();
         mineTypeSubCat = din.readByte();
         mineTypeSpecific = din.readByte();
         mineTypeExtra = din.readByte();
         
         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("minefieldIDSiteNum") + minefieldIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("minefieldIDAppNum") + minefieldIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("minefieldIDMinefieldNum") + minefieldIDMinefieldNum);
         System.out.println(LoggerUtil.prettyPrintField("requestSimIDSiteNum") + requestSimIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("requestSimIDAppNum") + requestSimIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("requestSimIDRefNum") + requestSimIDRefNum);
         System.out.println(LoggerUtil.prettyPrintField("minefieldSeqNum") + minefieldSeqNum);
         System.out.println(LoggerUtil.prettyPrintField("requestID") + requestID);
         System.out.println(LoggerUtil.prettyPrintField("pduSeqNum") + pduSeqNum);
         System.out.println(LoggerUtil.prettyPrintField("numPDUs") + numPDUs);
         System.out.println(LoggerUtil.prettyPrintField("numMines") + numMines);
         System.out.println(LoggerUtil.prettyPrintField("numSensorTypes") + numSensorTypes);
         System.out.println(LoggerUtil.prettyPrintField("dataFilter") + dataFilter);
         System.out.println(LoggerUtil.prettyPrintField("mineTypeEntKind") + mineTypeEntKind);
         System.out.println(LoggerUtil.prettyPrintField("mineTypeDomain") + mineTypeDomain);
         System.out.println(LoggerUtil.prettyPrintField("mineTypeCountry") + mineTypeCountry);
         System.out.println(LoggerUtil.prettyPrintField("mineTypeCategory") + mineTypeCategory);
         System.out.println(LoggerUtil.prettyPrintField("mineTypeSubCat") + mineTypeSubCat);
         System.out.println(LoggerUtil.prettyPrintField("mineTypeSpecific") + mineTypeSpecific);
         System.out.println(LoggerUtil.prettyPrintField("mineTypeExtra") + mineTypeExtra);
         
         for (int i=0; i<numSensorTypes; i++) {
            
            short sensorType = 0;
            
            sensorType = din.readShort();
            System.out.println(LoggerUtil.prettyPrintField("sensorType") + sensorType);
         }
         
         short padding2 = din.readShort();
         
         for (int j=0; j<numMines; j++) {
            
            float mineLocXComponent = 0.0f;
            float mineLocYComponent = 0.0f;
            float mineLocZComponent = 0.0f;
            
            mineLocXComponent = din.readFloat();
            mineLocYComponent = din.readFloat();
            mineLocZComponent = din.readFloat();
            
            System.out.println(LoggerUtil.prettyPrintField("mineLocXComponent") + mineLocXComponent);
            System.out.println(LoggerUtil.prettyPrintField("mineLocYComponent") + mineLocYComponent);
            System.out.println(LoggerUtil.prettyPrintField("mineLocZComponent") + mineLocZComponent);
         }
         
         for (int j=0; j<numMines; j++) {
            
            float groundBurialDepthOffset = 0.0f;
            
            groundBurialDepthOffset = din.readFloat();
            
            System.out.println(LoggerUtil.prettyPrintField("groundBurialDepthOffset") + groundBurialDepthOffset);
         }
         
         for (int j=0; j<numMines; j++) {
            
            float waterBurialDepthOffset = 0.0f;
            
            waterBurialDepthOffset = din.readFloat();
            
            System.out.println(LoggerUtil.prettyPrintField("waterBurialDepthOffset") + waterBurialDepthOffset);
         }
         
         for (int j=0; j<numMines; j++) {
            
            float snowBurialDepthOffset = 0.0f;
            
            snowBurialDepthOffset = din.readFloat();
            
            System.out.println(LoggerUtil.prettyPrintField("snowBurialDepthOffset") + snowBurialDepthOffset);
         }
         
         for (int j=0; j<numMines; j++) {
            
            float mineOrientPsi = 0.0f;
            float mineOrientTheta = 0.0f;
            float mineOrientPhi = 0.0f;
            
            mineOrientPsi = din.readFloat();
            mineOrientTheta = din.readFloat();
            mineOrientPhi = din.readFloat();
            
            System.out.println(LoggerUtil.prettyPrintField("mineOrientPsi") + mineOrientPsi);
            System.out.println(LoggerUtil.prettyPrintField("mineOrientTheta") + mineOrientTheta);
            System.out.println(LoggerUtil.prettyPrintField("mineOrientPhi") + mineOrientPhi);
         }
            
         for (int j=0; j<numMines; j++) {
            
            float thermalContrast = 0.0f;
            
            thermalContrast = din.readFloat();
            
            System.out.println(LoggerUtil.prettyPrintField("thermalContrast") + thermalContrast);  
         }
         
         for (int j=0; j<numMines; j++) {
            
            float reflectance = 0.0f;
            
            reflectance = din.readFloat();
            
            System.out.println(LoggerUtil.prettyPrintField("reflectance") + reflectance);
         }
         
         for (int j=0; j<numMines; j++) {
            
            int mineEmplacementTimeHour = 0;
            int mineEmplacementTimePastHour = 0;
            
            mineEmplacementTimeHour = din.readInt();
            mineEmplacementTimePastHour = din.readInt();
            
            System.out.println(LoggerUtil.prettyPrintField("mineEmplacementTimeHour") + mineEmplacementTimeHour);
            System.out.println(LoggerUtil.prettyPrintField("mineEmplacementTimePastHour") + mineEmplacementTimePastHour);
         }
         
         for (int j=0; j<numMines; j++) {
            
            short mineEntityNum = 0;
            
            mineEntityNum = din.readShort();
            
            System.out.println(LoggerUtil.prettyPrintField("mineEntityNum") + mineEntityNum);
         }
         
         for (int j=0; j<numMines; j++) {
            
            short fusing = 0;
            
            fusing = din.readShort();
            
            System.out.println(LoggerUtil.prettyPrintField("fusing") + fusing);
         }
         
         for (int j=0; j<numMines; j++) {
            
            byte scalarDetectionCoefficient = 0;
            
            scalarDetectionCoefficient = din.readByte();
            
            System.out.println(LoggerUtil.prettyPrintField("scalarDetectionCoefficient") + scalarDetectionCoefficient);
         }
         
         for (int j=0; j<numMines; j++) {
            
            byte paintScheme = 0;
            
            paintScheme = din.readByte();
            
            System.out.println(LoggerUtil.prettyPrintField("paintScheme") + paintScheme);
         }
         
         int padding3 = din.readInt();
         
         for (int j=0; j<numMines; j++) {
            
            byte numTripDetonWires = 0;
            
            numTripDetonWires = din.readByte();
            
            System.out.println(LoggerUtil.prettyPrintField("numTripDetonWires") + numTripDetonWires);
         }
         
         int padding4 = din.readInt();
         
         for (int j=0; j<numMines; j++) {
            
            byte numVertices = 0;
            
            numVertices = din.readByte();
            
            System.out.println(LoggerUtil.prettyPrintField("numVertices") + numVertices);
         }
         
         int padding5 = din.readInt();
         
         for (int j=0; j<numMines; j++) {
            
            float vertexXComp = 0.0f;
            float vertexYComp = 0.0f;
            float vertexZComp = 0.0f;
            
            vertexXComp = din.readFloat();
            vertexYComp = din.readFloat();
            vertexZComp = din.readFloat();
            
            System.out.println(LoggerUtil.prettyPrintField("vertexXComp") + vertexXComp);
            System.out.println(LoggerUtil.prettyPrintField("vertexYComp") + vertexYComp);
            System.out.println(LoggerUtil.prettyPrintField("vertexZComp") + vertexZComp);
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
