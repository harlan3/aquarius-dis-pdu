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

public class ElectromagneticEmission {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(30);

      byte pduType = 0;
      short length = 0;
      short emitEntIDSite = 0;
      short emitEntIDApp = 0;
      short emitEntIDEntity = 0;
      short eventIDSite = 0;
      short eventIDApp = 0;
      short eventIDEventNum = 0;
      byte stateUpdateIndicator = 0;
      byte numberOfSystems = 0;
      @SuppressWarnings("unused")
      short padding1 = 0;
      byte systemDataLength = 0;
      byte numberOfBeams = 0;
      @SuppressWarnings("unused")
      short padding2 = 0;
      short emitterName = 0;
      byte emitterFunction = 0;
      byte emitterIDNumber = 0;
      float emitterLocationX = 0.0f;
      float emitterLocationY = 0.0f;
      float emitterLocationZ = 0.0f;
      byte beamDataLength = 0;
      byte beamIDNumber = 0;
      short beamParamIndex = 0;
      float frequency = 0.0f;
      float frequencyRange = 0.0f;
      float effectiveRadiatedPower = 0.0f;
      float pulseRepetitionFrequency = 0.0f;
      float pulseWidth = 0.0f;
      float beamAzimuthCenter = 0.0f;
      float beamAzimuthSweep = 0.0f;
      float beamElevationCenter = 0.0f;
      float beamElevationSweep = 0.0f;
      float beamSweepSync = 0.0f;
      byte beamFunction = 0;
      byte numberTargetsInTrackJam = 0;
      byte highDensityTrackJam = 0;
      @SuppressWarnings("unused")
      byte padding3 = 0;
      int jammingModeSequence = 0;
      short trackJamSite = 0;
      short trackJamApp = 0;
      short trackJamEntity = 0;
      byte trackJamEmitterID = 0;
      byte trackJamBeamID = 0;

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
         emitEntIDSite = din.readShort();
         emitEntIDApp = din.readShort();
         emitEntIDEntity = din.readShort();
         eventIDSite = din.readShort();
         eventIDApp = din.readShort();
         eventIDEventNum = din.readShort();
         stateUpdateIndicator = din.readByte();
         numberOfSystems = din.readByte();
         padding1 = din.readShort();

         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("emitEntIDSite") + emitEntIDSite);
         System.out.println(LoggerUtil.prettyPrintField("emitEntIDApp") + emitEntIDApp);
         System.out.println(LoggerUtil.prettyPrintField("emitEntIDEntity") + emitEntIDEntity);
         System.out.println(LoggerUtil.prettyPrintField("eventIDSite") + eventIDSite);
         System.out.println(LoggerUtil.prettyPrintField("eventIDApp") + eventIDApp);
         System.out.println(LoggerUtil.prettyPrintField("eventIDEventNum") + eventIDEventNum);
         System.out.println(LoggerUtil.prettyPrintField("stateUpdateIndicator") + stateUpdateIndicator);
         System.out.println(LoggerUtil.prettyPrintField("numberOfSystems") + numberOfSystems);

         for (int i = 0; i < numberOfSystems; i++) {

            systemDataLength = din.readByte();
            numberOfBeams = din.readByte();
            padding2 = din.readShort();
            emitterName = din.readShort();
            emitterFunction = din.readByte();
            emitterIDNumber = din.readByte();
            emitterLocationX = din.readFloat();
            emitterLocationY = din.readFloat();
            emitterLocationZ = din.readFloat();

            System.out.println(LoggerUtil.prettyPrintField("(Emission System Record)") + i);
            System.out.println(LoggerUtil.prettyPrintField("systemDataLength") + systemDataLength);
            System.out.println(LoggerUtil.prettyPrintField("numberOfBeams") + numberOfBeams);
            System.out.println(LoggerUtil.prettyPrintField("emitterName") + emitterName);
            System.out.println(LoggerUtil.prettyPrintField("emitterFunction") + emitterFunction);
            System.out.println(LoggerUtil.prettyPrintField("emitterIDNumber") + emitterIDNumber);
            System.out.println(LoggerUtil.prettyPrintField("emitterLocationX") + emitterLocationX);
            System.out.println(LoggerUtil.prettyPrintField("emitterLocationY") + emitterLocationY);
            System.out.println(LoggerUtil.prettyPrintField("emitterLocationZ") + emitterLocationZ);

            for (int j = 0; j < numberOfBeams; j++) {

               beamDataLength = din.readByte();
               beamIDNumber = din.readByte();
               beamParamIndex = din.readShort();
               frequency = din.readFloat();
               frequencyRange = din.readFloat();
               effectiveRadiatedPower = din.readFloat();
               pulseRepetitionFrequency = din.readFloat();
               pulseWidth = din.readFloat();
               beamAzimuthCenter = din.readFloat();
               beamAzimuthSweep = din.readFloat();
               beamElevationCenter = din.readFloat();
               beamElevationSweep = din.readFloat();
               beamSweepSync = din.readFloat();
               beamFunction = din.readByte();
               numberTargetsInTrackJam = din.readByte();
               highDensityTrackJam = din.readByte();
               padding3 = din.readByte();

               System.out.println(LoggerUtil.prettyPrintField("(Beam Record)") + j);
               System.out.println(LoggerUtil.prettyPrintField("beamDataLength") + beamDataLength);
               System.out.println(LoggerUtil.prettyPrintField("beamIDNumber") + beamIDNumber);
               System.out.println(LoggerUtil.prettyPrintField("beamParamIndex") + beamParamIndex);
               System.out.println(LoggerUtil.prettyPrintField("frequency") + frequency);
               System.out.println(LoggerUtil.prettyPrintField("frequencyRange") + frequencyRange);
               System.out.println(LoggerUtil.prettyPrintField("effectiveRadiatedPower") + effectiveRadiatedPower);
               System.out.println(LoggerUtil.prettyPrintField("pulseRepetitionFrequency") + pulseRepetitionFrequency);
               System.out.println(LoggerUtil.prettyPrintField("pulseWidth") + pulseWidth);
               System.out.println(LoggerUtil.prettyPrintField("beamAzimuthCenter") + beamAzimuthCenter);
               System.out.println(LoggerUtil.prettyPrintField("beamAzimuthSweep") + beamAzimuthSweep);
               System.out.println(LoggerUtil.prettyPrintField("beamElevationCenter") + beamElevationCenter);
               System.out.println(LoggerUtil.prettyPrintField("beamElevationSweep") + beamElevationSweep);
               System.out.println(LoggerUtil.prettyPrintField("beamSweepSync") + beamSweepSync);
               System.out.println(LoggerUtil.prettyPrintField("beamFunction") + beamFunction);
               System.out.println(LoggerUtil.prettyPrintField("numberTargetsInTrackJam") + numberTargetsInTrackJam);
               System.out.println(LoggerUtil.prettyPrintField("highDensityTrackJam") + highDensityTrackJam);

               for (int k = 0; k < numberTargetsInTrackJam; k++) {

                  jammingModeSequence = din.readInt();
                  trackJamSite = din.readShort();
                  trackJamApp = din.readShort();
                  trackJamEntity = din.readShort();
                  trackJamEmitterID = din.readByte();
                  trackJamBeamID = din.readByte();

                  System.out.println(LoggerUtil.prettyPrintField("(Track/Jam Record)") + k);
                  System.out.println(LoggerUtil.prettyPrintField("jammingModeSequence") + jammingModeSequence);
                  System.out.println(LoggerUtil.prettyPrintField("trackJamSite") + trackJamSite);
                  System.out.println(LoggerUtil.prettyPrintField("trackJamApp") + trackJamApp);
                  System.out.println(LoggerUtil.prettyPrintField("trackJamEntity") + trackJamEntity);
                  System.out.println(LoggerUtil.prettyPrintField("trackJamEmitterID") + trackJamEmitterID);
                  System.out.println(LoggerUtil.prettyPrintField("trackJamBeamID") + trackJamBeamID);

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
