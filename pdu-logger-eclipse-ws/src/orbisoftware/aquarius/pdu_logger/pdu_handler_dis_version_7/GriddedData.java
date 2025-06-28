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

public class GriddedData {

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
      short envSimIDSiteNum = 0;
      short envSimIDAppNum = 0;
      short envSimIDRefNum = 0;
      short fieldNum = 0;
      short pduNum = 0;
      short pduTotal = 0;
      short coordSystem = 0;
      byte numberGridAxes = 0;
      byte constantGrid = 0;
      byte envTypeKind = 0;
      byte envTypeDomain = 0;
      short envTypeCountry = 0;
      byte envTypeCategory = 0;
      byte envTypeSubcategory = 0;
      byte envTypeSpecific = 0;
      byte envTypeExtra = 0;
      float orientPsi = 0;
      float orientTheta = 0;
      float orientPhi = 0;
      long sampleTime = 0;
      int totalValues = 0;
      byte vectorDim = 0;
      byte padding1 = 0;
      short padding2 = 0;   
    
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
         
         envSimIDSiteNum = din.readShort();
         envSimIDAppNum = din.readShort();
         envSimIDRefNum = din.readShort();
         fieldNum = din.readShort();
         pduNum = din.readShort();
         pduTotal = din.readShort();
         coordSystem = din.readShort();
         numberGridAxes = din.readByte();
         constantGrid = din.readByte();
         envTypeKind = din.readByte();
         envTypeDomain = din.readByte();
         envTypeCountry = din.readShort();
         envTypeCategory = din.readByte();
         envTypeSubcategory = din.readByte();
         envTypeSpecific = din.readByte();
         envTypeExtra = din.readByte();
         orientPsi = din.readFloat();
         orientTheta = din.readFloat();
         orientPhi = din.readFloat();
         sampleTime = din.readLong();
         totalValues = din.readInt();
         vectorDim = din.readByte();
         padding1 = din.readByte();
         padding2 = din.readShort();
         
         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("envSimIDSiteNum") + envSimIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("envSimIDAppNum") + envSimIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("envSimIDRefNum") + envSimIDRefNum);
         System.out.println(LoggerUtil.prettyPrintField("fieldNum") + fieldNum);
         System.out.println(LoggerUtil.prettyPrintField("pduNum") + pduNum);
         System.out.println(LoggerUtil.prettyPrintField("coordSystem") + coordSystem);
         System.out.println(LoggerUtil.prettyPrintField("numberGridAxes") + numberGridAxes);
         System.out.println(LoggerUtil.prettyPrintField("constantGrid") + constantGrid);
         System.out.println(LoggerUtil.prettyPrintField("envTypeKind") + envTypeKind);
         System.out.println(LoggerUtil.prettyPrintField("envTypeDomain") + envTypeDomain);
         System.out.println(LoggerUtil.prettyPrintField("envTypeCountry") + envTypeCountry);
         System.out.println(LoggerUtil.prettyPrintField("envTypeCategory") + envTypeCategory);
         System.out.println(LoggerUtil.prettyPrintField("envTypeSubcategory") + envTypeSubcategory);
         System.out.println(LoggerUtil.prettyPrintField("envTypeSpecific") + envTypeSpecific);
         System.out.println(LoggerUtil.prettyPrintField("envTypeExtra") + envTypeExtra);
         System.out.println(LoggerUtil.prettyPrintField("orientPsi") + orientPsi);
         System.out.println(LoggerUtil.prettyPrintField("orientTheta") + orientTheta);
         System.out.println(LoggerUtil.prettyPrintField("orientPhi") + orientPhi);
         System.out.println(LoggerUtil.prettyPrintField("sampleTime") + sampleTime);
         System.out.println(LoggerUtil.prettyPrintField("totalValues") + totalValues);
         System.out.println(LoggerUtil.prettyPrintField("vectorDim") + vectorDim);
         
         for (int i=0; i<numberGridAxes; i++) {
            
            double gridAxisDescDomainInitial = 0.0;
            double gridAxisDescDomainFinal = 0.0;
            short gridAxisDescDomainPoints = 0;
            byte gridAxisDescInterleafFactor = 0;
            byte gridAxisDescAxisType = 0;
            short gridAxisDescNumberOfPointsOnAxis = 0;
            short gridAxisDescInitialIndex = 0;
            
            gridAxisDescDomainInitial = din.readDouble();
            gridAxisDescDomainFinal = din.readDouble();
            gridAxisDescDomainPoints = din.readShort();
            gridAxisDescInterleafFactor = din.readByte();
            gridAxisDescAxisType = din.readByte();
            gridAxisDescNumberOfPointsOnAxis = din.readShort();
            gridAxisDescInitialIndex = din.readShort();
            
            System.out.println(LoggerUtil.prettyPrintField("gridAxisDescDomainInitial") + gridAxisDescDomainInitial);
            System.out.println(LoggerUtil.prettyPrintField("gridAxisDescDomainFinal") + gridAxisDescDomainFinal);
            System.out.println(LoggerUtil.prettyPrintField("gridAxisDescDomainPoints") + gridAxisDescDomainPoints);
            System.out.println(LoggerUtil.prettyPrintField("gridAxisDescInterleafFactor") + gridAxisDescInterleafFactor);
            System.out.println(LoggerUtil.prettyPrintField("gridAxisDescAxisType") + gridAxisDescAxisType);
            System.out.println(LoggerUtil.prettyPrintField("gridAxisDescNumberOfPointsOnAxis") + gridAxisDescNumberOfPointsOnAxis);
            System.out.println(LoggerUtil.prettyPrintField("gridAxisDescInitialIndex") + gridAxisDescInitialIndex);
            
            for (int j=0; j<gridAxisDescNumberOfPointsOnAxis; j++) {
               
               short gridDataRecSampleType = 0;
               short gridDataRecDataRep = 0;
               short gridDataRecNumOctets = 0;
               
               gridDataRecSampleType = din.readShort();
               gridDataRecDataRep = din.readShort();
               gridDataRecNumOctets = din.readShort();
               
               System.out.println(LoggerUtil.prettyPrintField("gridDataRecSampleType") + gridDataRecSampleType);
               System.out.println(LoggerUtil.prettyPrintField("gridDataRecDataRep") + gridDataRecDataRep);
               System.out.println(LoggerUtil.prettyPrintField("gridDataRecNumOctets") + gridDataRecNumOctets);
               
               for (int k=0; k<gridDataRecNumOctets; k++) {
                  
                  byte gridDataRecDataValue = 0;
                  byte padding3 = 0;
                  
                  gridDataRecDataValue = din.readByte();
                  padding3 = din.readByte();
                  
                  System.out.println(LoggerUtil.prettyPrintField("gridDataRecDataValue") + gridDataRecDataValue);
               }
            }
            
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
