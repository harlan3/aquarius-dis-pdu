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

public class AggregateState {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(40);
      
      byte pduType = 0;
      byte family = 0;
      short length = 0;
      byte pduStatus = 0;
      short aggIDSiteNumber = 0;
      short aggIDAppNumber = 0;
      short aggIDAggNumber = 0;
      byte forceID = 0;
      byte aggState = 0;
      byte aggTypeKind = 0;
      byte aggTypeDomain = 0;
      short aggTypeCountry = 0;
      byte aggTypeCategory = 0;
      byte aggTypeSubCategory = 0;
      byte aggTypeSpecific = 0;
      byte aggTypeExtra = 0;
      int formation;
      byte aggMarkingCharSet = 0;
      byte aggMarkingData[] = new byte[31];
      float dimensionsX = 0.0f;
      float dimensionsY = 0.0f;
      float dimensionsZ = 0.0f;
      float orientationPsi = 0.0f;
      float orientationTheta = 0.0f;
      float orientationPhi = 0.0f;
      double centerOfMassLocX = 0.0;
      double centerOfMassLocY = 0.0;
      double centerOfMassLocZ = 0.0;
      float velocityX = 0.0f;
      float velocityY = 0.0f;
      float velocityZ = 0.0f;
      short numOfAggregateIDs = 0;
      short numOfEntityIDs = 0;
      short numOfSilentAggregateSystems = 0;
      short numOfSilEntEntitySystems = 0;
      short padding = 0;
      int numberVariableDatumRecords = 0;
      
      try {

         /* Start Message Header */
         din.skipBytes(2);
         pduType = din.readByte();
         family = din.readByte();
         din.reset();

         din.skipBytes(8);
         length = din.readShort();
         pduStatus = din.readByte();
         din.reset();
         /* End Message Header */

         din.skipBytes(12);
         aggIDSiteNumber = din.readShort();
         aggIDAppNumber = din.readShort();
         aggIDAggNumber = din.readShort();
         forceID = din.readByte();
         aggState = din.readByte();
         aggTypeKind = din.readByte();
         aggTypeDomain = din.readByte();
         aggTypeCountry = din.readShort();
         aggTypeCategory = din.readByte();
         aggTypeSubCategory = din.readByte();
         aggTypeSpecific = din.readByte();
         aggTypeExtra = din.readByte();
         formation = din.readInt();
         aggMarkingCharSet = din.readByte();
         aggMarkingData = din.readNBytes(31);
         dimensionsX = din.readFloat();
         dimensionsY = din.readFloat();
         dimensionsZ = din.readFloat();
         orientationPsi = din.readFloat();
         orientationTheta = din.readFloat();
         orientationPhi = din.readFloat();
         centerOfMassLocX = din.readDouble();
         centerOfMassLocY = din.readDouble();
         centerOfMassLocZ = din.readDouble();
         velocityX = din.readFloat();
         velocityY = din.readFloat();
         velocityZ = din.readFloat();
         numOfAggregateIDs = din.readShort();
         numOfEntityIDs = din.readShort();
         numOfSilentAggregateSystems = din.readShort();
         numOfSilEntEntitySystems = din.readShort();
         
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("aggIDSiteNumber") + aggIDSiteNumber);
         System.out.println(LoggerUtil.prettyPrintField("aggIDAppNumber") + aggIDAppNumber);
         System.out.println(LoggerUtil.prettyPrintField("aggIDAggNumber") + aggIDAggNumber);
         System.out.println(LoggerUtil.prettyPrintField("forceID") + forceID);
         System.out.println(LoggerUtil.prettyPrintField("aggState") + aggState);
         System.out.println(LoggerUtil.prettyPrintField("aggTypeKind") + aggTypeKind);
         System.out.println(LoggerUtil.prettyPrintField("aggTypeDomain") + aggTypeDomain);
         System.out.println(LoggerUtil.prettyPrintField("aggTypeCountry") + aggTypeCountry);
         System.out.println(LoggerUtil.prettyPrintField("aggTypeCategory") + aggTypeCategory);
         System.out.println(LoggerUtil.prettyPrintField("aggTypeSubCategory") + aggTypeSubCategory);
         System.out.println(LoggerUtil.prettyPrintField("aggTypeSpecific") + aggTypeSpecific);
         System.out.println(LoggerUtil.prettyPrintField("aggTypeExtra") + aggTypeExtra);
         System.out.println(LoggerUtil.prettyPrintField("formation") + formation);
         System.out.println(LoggerUtil.prettyPrintField("aggMarkingCharSet") + aggMarkingCharSet);
         System.out.println(LoggerUtil.prettyPrintField("aggMarkingData") + aggMarkingData);
         System.out.println(LoggerUtil.prettyPrintField("dimensionsX") + dimensionsX);
         System.out.println(LoggerUtil.prettyPrintField("dimensionsY") + dimensionsY);
         System.out.println(LoggerUtil.prettyPrintField("dimensionsZ") + dimensionsZ);
         System.out.println(LoggerUtil.prettyPrintField("orientationPsi") + orientationPsi);
         System.out.println(LoggerUtil.prettyPrintField("orientationTheta") + orientationTheta);
         System.out.println(LoggerUtil.prettyPrintField("orientationPhi") + orientationPhi);
         System.out.println(LoggerUtil.prettyPrintField("centerOfMassLocX") + centerOfMassLocX);
         System.out.println(LoggerUtil.prettyPrintField("centerOfMassLocY") + centerOfMassLocY);
         System.out.println(LoggerUtil.prettyPrintField("centerOfMassLocZ") + centerOfMassLocZ);
         System.out.println(LoggerUtil.prettyPrintField("velocityX") + velocityX);
         System.out.println(LoggerUtil.prettyPrintField("velocityY") + velocityY);
         System.out.println(LoggerUtil.prettyPrintField("velocityZ") + velocityZ);
         System.out.println(LoggerUtil.prettyPrintField("numOfAggregateIDs") + numOfAggregateIDs);
         System.out.println(LoggerUtil.prettyPrintField("numOfEntityIDs") + numOfEntityIDs);
         System.out.println(LoggerUtil.prettyPrintField("numOfSilentAggregateSystems") + numOfSilentAggregateSystems);
         System.out.println(LoggerUtil.prettyPrintField("numOfSilEntEntitySystems") + numOfSilEntEntitySystems);
         
         for (int i = 0; i < numOfAggregateIDs; i++) {

            short aggIDNumSiteNumber = din.readShort();
            short aggIDNumAppNumber = din.readShort();
            short aggIDNumAggNumber = din.readShort();

            System.out.println(LoggerUtil.prettyPrintField("aggIDNumSiteNumber") + aggIDNumSiteNumber);
            System.out.println(LoggerUtil.prettyPrintField("aggIDNumAppNumber") + aggIDNumAppNumber);
            System.out.println(LoggerUtil.prettyPrintField("aggIDNumAggNumber") + aggIDNumAggNumber);
         }
         
         for (int i = 0; i < numOfEntityIDs; i++) {
            
            short entIDNumSiteNumber = din.readShort();
            short entIDNumAppNumber = din.readShort();
            short entIDNumEntityNumber = din.readShort();            
            
            System.out.println(LoggerUtil.prettyPrintField("entIDNumSiteNumber") + entIDNumSiteNumber);
            System.out.println(LoggerUtil.prettyPrintField("entIDNumAppNumber") + entIDNumAppNumber);
            System.out.println(LoggerUtil.prettyPrintField("entIDNumEntityNumber") + entIDNumEntityNumber);
         }
         
         padding = din.readShort();
         
         for (int i = 0; i < numOfSilentAggregateSystems; i++) {
            
            short silentAggSysNumAgg = din.readShort();
            short padding2 = din.readShort();
            byte silentAggSysAggregateKind = din.readByte();
            byte silentAggSysDomain = din.readByte();
            short silentAggSysCountry = din.readShort();
            byte silentAggSysCategory = din.readByte();
            byte silentAggSysSubcategory = din.readByte();
            byte silentAggSysSpecific = din.readByte();
            byte silentAggSysExtra = din.readByte();
            
            System.out.println(LoggerUtil.prettyPrintField("silentAggSysNumAgg") + silentAggSysNumAgg);
            System.out.println(LoggerUtil.prettyPrintField("silentAggSysAggregateKind") + silentAggSysAggregateKind);
            System.out.println(LoggerUtil.prettyPrintField("silentAggSysDomain") + silentAggSysDomain);
            System.out.println(LoggerUtil.prettyPrintField("silentAggSysCountry") + silentAggSysCountry);
            System.out.println(LoggerUtil.prettyPrintField("silentAggSysCategory") + silentAggSysCategory);
            System.out.println(LoggerUtil.prettyPrintField("silentAggSysSubcategory") + silentAggSysSubcategory);
            System.out.println(LoggerUtil.prettyPrintField("silentAggSysSpecific") + silentAggSysSpecific);
            System.out.println(LoggerUtil.prettyPrintField("silentAggSysExtra") + silentAggSysExtra);
         }
         
         for (int i = 0; i < numOfSilEntEntitySystems; i++) {
            
            short silentEntSysNumEntities = din.readShort();
            short silentEntSysNumAppearRec = din.readShort();
            
            // Entity type record
            byte silentEntSysEntityKind = din.readByte();
            byte silentEntSysDomain = din.readByte();
            short silentEntSysCountry = din.readShort();
            byte silentEntSysCategory = din.readByte();
            byte silentEntSysSubCategory = din.readByte();
            byte silentEntSysSpecific = din.readByte();
            byte silentEntSysExtra = din.readByte();
            
            System.out.println(LoggerUtil.prettyPrintField("silentEntSysNumEntities") + silentEntSysNumEntities);
            System.out.println(LoggerUtil.prettyPrintField("silentEntSysNumAppearRec") + silentEntSysNumAppearRec);     
            System.out.println(LoggerUtil.prettyPrintField("silentEntSysEntityKind") + silentEntSysEntityKind);    
            System.out.println(LoggerUtil.prettyPrintField("silentEntSysDomain") + silentEntSysDomain);
            System.out.println(LoggerUtil.prettyPrintField("silentEntSysCountry") + silentEntSysCountry);
            System.out.println(LoggerUtil.prettyPrintField("silentEntSysCategory") + silentEntSysCategory);
            System.out.println(LoggerUtil.prettyPrintField("silentEntSysSubCategory") + silentEntSysSubCategory);
            System.out.println(LoggerUtil.prettyPrintField("silentEntSysSpecific") + silentEntSysSpecific);
            System.out.println(LoggerUtil.prettyPrintField("silentEntSysDomain") + silentEntSysDomain);
            System.out.println(LoggerUtil.prettyPrintField("silentEntSysExtra") + silentEntSysExtra);
            
            for (int j = 0; j < silentEntSysNumAppearRec; j++) {
               
               // Entity appearance record
               int silentEntSysEntityAppearRec = din.readInt();
               
               System.out.println(LoggerUtil.prettyPrintField("silentEntSysEntityAppearRec") + silentEntSysEntityAppearRec);
            }    
         }
         
         numberVariableDatumRecords = din.readInt();
         
         for (int i = 0; i < numberVariableDatumRecords; i++) {
            
            int variableDatumID = din.readInt();
            int variableDatumLength = din.readInt();
            byte variableDatumValue = din.readByte();
            din.skipBytes(7);
            
            System.out.println(LoggerUtil.prettyPrintField("variableDatumID") + variableDatumID);
            System.out.println(LoggerUtil.prettyPrintField("variableDatumLength") + variableDatumLength);
            System.out.println(LoggerUtil.prettyPrintField("variableDatumValue") + variableDatumValue);
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
