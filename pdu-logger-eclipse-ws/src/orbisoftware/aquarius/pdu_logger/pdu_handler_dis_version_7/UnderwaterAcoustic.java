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

public class UnderwaterAcoustic {

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
      short emittingEntityIDSiteNum = 0;
      short emittingEntityIDAppNum = 0;
      short emittingEntityIDEntityNum = 0;
      short eventIDSiteNum = 0;
      short eventIDAppNum = 0;
      short eventIDEventNum = 0;
      byte stateChangeUpdateIndicator = 0;
      byte padding1 = 0;
      short passiveParameterIndex = 0;
      byte propulsionPlantConfig = 0;
      byte numberOfShafts = 0;
      byte numberOfAPAs = 0;
      byte numberOfUAEmitterSystems = 0;

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
         
         emittingEntityIDSiteNum = din.readShort();
         emittingEntityIDAppNum = din.readShort();
         emittingEntityIDEntityNum = din.readShort();
         eventIDSiteNum = din.readShort();
         eventIDAppNum = din.readShort();
         eventIDEventNum = din.readShort();
         stateChangeUpdateIndicator = din.readByte();
         padding1 = din.readByte();
         passiveParameterIndex = din.readShort();
         propulsionPlantConfig = din.readByte();
         numberOfShafts = din.readByte();
         numberOfAPAs = din.readByte();
         numberOfUAEmitterSystems = din.readByte();

         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("emittingEntityIDSiteNum") + emittingEntityIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("emittingEntityIDAppNum") + emittingEntityIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("emittingEntityIDEntityNum") + emittingEntityIDEntityNum);
         System.out.println(LoggerUtil.prettyPrintField("eventIDSiteNum") + eventIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("eventIDAppNum") + eventIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("eventIDEventNum") + eventIDEventNum);
         System.out.println(LoggerUtil.prettyPrintField("stateChangeUpdateIndicator") + stateChangeUpdateIndicator);
         System.out.println(LoggerUtil.prettyPrintField("padding1") + padding1);
         System.out.println(LoggerUtil.prettyPrintField("passiveParameterIndex") + passiveParameterIndex);
         System.out.println(LoggerUtil.prettyPrintField("propulsionPlantConfig") + propulsionPlantConfig);
         System.out.println(LoggerUtil.prettyPrintField("numberOfShafts") + numberOfShafts);
         System.out.println(LoggerUtil.prettyPrintField("numberOfAPAs") + numberOfAPAs);
         System.out.println(LoggerUtil.prettyPrintField("numberOfUAEmitterSystems") + numberOfUAEmitterSystems);
         
         for (int i=0; i < numberOfShafts; i++) {
            
            short currentShaftRPM = 0;
            short orderedShaftRPM = 0;
            int shaftRPMRateOfChange = 0;
            
            currentShaftRPM = din.readShort();
            orderedShaftRPM = din.readShort();
            shaftRPMRateOfChange = din.readInt();
            
            System.out.println(LoggerUtil.prettyPrintField("currentShaftRPM") + currentShaftRPM);
            System.out.println(LoggerUtil.prettyPrintField("orderedShaftRPM") + orderedShaftRPM);
            System.out.println(LoggerUtil.prettyPrintField("shaftRPMRateOfChange") + shaftRPMRateOfChange);
            
         }
         
         for (int j=0; j < numberOfAPAs; j++) {
         
            short apaParameterIndex = 0;
            short apaValue = 0;
            
            apaParameterIndex = din.readShort();
            apaValue = din.readShort();
            
            System.out.println(LoggerUtil.prettyPrintField("apaParameterIndex") + apaParameterIndex);
            System.out.println(LoggerUtil.prettyPrintField("apaValue") + apaValue);
         }
         
         byte numberOfBeams = 0;
         
         for (int k=0; k < numberOfUAEmitterSystems; k++) {
         
            byte emmiterSystemDataLength = 0;
            short padding2 = 0;
            short acousticEmmiterSysemName = 0;
            byte acousticEmmiterSysemFuntion = 0;
            byte acousticEmmiterSysemIDNumber = 0;
            float locationXComponent = 0.0f;
            float locationYComponent = 0.0f;
            float locationZComponent = 0.0f;
     
            emmiterSystemDataLength = din.readByte();
            numberOfBeams = din.readByte();
            padding2 = din.readShort();
            acousticEmmiterSysemName = din.readShort();
            acousticEmmiterSysemFuntion = din.readByte();
            acousticEmmiterSysemIDNumber = din.readByte();
            locationXComponent = din.readFloat();
            locationYComponent = din.readFloat();
            locationZComponent = din.readFloat();
            
            System.out.println(LoggerUtil.prettyPrintField("emmiterSystemDataLength") + emmiterSystemDataLength);
            System.out.println(LoggerUtil.prettyPrintField("numberOfBeams") + numberOfBeams);
            System.out.println(LoggerUtil.prettyPrintField("padding2") + padding2);
            System.out.println(LoggerUtil.prettyPrintField("acousticEmmiterSysemName") + acousticEmmiterSysemName);
            System.out.println(LoggerUtil.prettyPrintField("acousticEmmiterSysemFuntion") + acousticEmmiterSysemFuntion);
            System.out.println(LoggerUtil.prettyPrintField("acousticEmmiterSysemIDNumber") + acousticEmmiterSysemIDNumber);
            System.out.println(LoggerUtil.prettyPrintField("locationXComponent") + locationXComponent);
            System.out.println(LoggerUtil.prettyPrintField("locationYComponent") + locationYComponent);
            System.out.println(LoggerUtil.prettyPrintField("locationZComponent") + locationZComponent);
         }
         
         for (int l=0; l < numberOfBeams; l++) {
            
            byte beamDataLength = 0;
            byte beamIDNumber = 0;
            short padding = 0;
            short fundamentalDataActiveEmissionParameterIndex = 0;
            short fundamentalDataScanPattern = 0;
            float fundamentalDataBeamCenterAzimuth = 0;
            float fundamentalDataAzimuthBeamwidth = 0;
            float fundamentalDataBeamCenter = 0.0f;
            float fundamentalDataDEBeamwidth = 0.0f;
            
            beamDataLength = din.readByte();
            beamIDNumber = din.readByte();
            padding = din.readShort();
            fundamentalDataActiveEmissionParameterIndex = din.readShort();
            fundamentalDataScanPattern = din.readShort();
            fundamentalDataBeamCenterAzimuth = din.readFloat();
            fundamentalDataAzimuthBeamwidth = din.readFloat();
            fundamentalDataBeamCenter = din.readFloat();
            fundamentalDataDEBeamwidth = din.readFloat();
            
            System.out.println(LoggerUtil.prettyPrintField("beamDataLength") + beamDataLength);
            System.out.println(LoggerUtil.prettyPrintField("beamIDNumber") + beamIDNumber);
            System.out.println(LoggerUtil.prettyPrintField("activeEmissionParameterIndex") + fundamentalDataActiveEmissionParameterIndex);
            System.out.println(LoggerUtil.prettyPrintField("scanPattern") + fundamentalDataScanPattern);
            System.out.println(LoggerUtil.prettyPrintField("beamCenterAzimuth") + fundamentalDataBeamCenterAzimuth);
            System.out.println(LoggerUtil.prettyPrintField("azimuthBeamwidth") + fundamentalDataAzimuthBeamwidth);
            System.out.println(LoggerUtil.prettyPrintField("beamCenter") + fundamentalDataBeamCenter);
            System.out.println(LoggerUtil.prettyPrintField("deBeamwidth") + fundamentalDataDEBeamwidth);
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
