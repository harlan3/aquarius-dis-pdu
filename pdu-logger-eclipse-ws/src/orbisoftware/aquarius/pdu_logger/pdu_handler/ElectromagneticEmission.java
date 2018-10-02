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

public class ElectromagneticEmission {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(
            packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();

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

         System.out.println("                 pduType : "
               + PDU_Type.values()[pduType]);
         System.out.println("                  length : " + length);
         System.out.println("           emitEntIDSite : " + emitEntIDSite);
         System.out.println("            emitEntIDApp : " + emitEntIDApp);
         System.out.println("         emitEntIDEntity : " + emitEntIDEntity);
         System.out.println("             eventIDSite : " + eventIDSite);
         System.out.println("              eventIDApp : " + eventIDApp);
         System.out.println("         eventIDEventNum : " + eventIDEventNum);
         System.out.println("    stateUpdateIndicator : "
               + stateUpdateIndicator);
         System.out.println("         numberOfSystems : " + numberOfSystems);

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

            System.out.println("(Emission System Record) : " + i);
            System.out
                  .println("        systemDataLength : " + systemDataLength);
            System.out.println("           numberOfBeams : " + numberOfBeams);
            System.out.println("             emitterName : " + emitterName);
            System.out.println("         emitterFunction : " + emitterFunction);
            System.out.println("         emitterIDNumber : " + emitterIDNumber);
            System.out
                  .println("        emitterLocationX : " + emitterLocationX);
            System.out
                  .println("        emitterLocationY : " + emitterLocationY);
            System.out
                  .println("        emitterLocationZ : " + emitterLocationZ);

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

               System.out.println("              (Beam Record) : " + j);
               System.out.println("             beamDataLength : "
                     + beamDataLength);
               System.out.println("               beamIDNumber : "
                     + beamIDNumber);
               System.out.println("             beamParamIndex : "
                     + beamParamIndex);
               System.out.println("                  frequency : " + frequency);
               System.out.println("             frequencyRange : "
                     + frequencyRange);
               System.out.println("     effectiveRadiatedPower : "
                     + effectiveRadiatedPower);
               System.out.println("   pulseRepetitionFrequency : "
                     + pulseRepetitionFrequency);
               System.out
                     .println("                 pulseWidth : " + pulseWidth);
               System.out.println("          beamAzimuthCenter : "
                     + beamAzimuthCenter);
               System.out.println("           beamAzimuthSweep : "
                     + beamAzimuthSweep);
               System.out.println("        beamElevationCenter : "
                     + beamElevationCenter);
               System.out.println("         beamElevationSweep : "
                     + beamElevationSweep);
               System.out.println("              beamSweepSync : "
                     + beamSweepSync);
               System.out.println("               beamFunction : "
                     + beamFunction);
               System.out.println("    numberTargetsInTrackJam : "
                     + numberTargetsInTrackJam);
               System.out.println("        highDensityTrackJam : "
                     + highDensityTrackJam);

               for (int k = 0; k < numberTargetsInTrackJam; k++) {

                  jammingModeSequence = din.readInt();
                  trackJamSite = din.readShort();
                  trackJamApp = din.readShort();
                  trackJamEntity = din.readShort();
                  trackJamEmitterID = din.readByte();
                  trackJamBeamID = din.readByte();

                  System.out.println("            (Track/Jam Record) : " + k);
                  System.out.println("           jammingModeSequence : "
                        + jammingModeSequence);
                  System.out.println("                  trackJamSite : "
                        + trackJamSite);
                  System.out.println("                   trackJamApp : "
                        + trackJamApp);
                  System.out.println("                trackJamEntity : "
                        + trackJamEntity);
                  System.out.println("             trackJamEmitterID : "
                        + trackJamEmitterID);
                  System.out.println("                trackJamBeamID : "
                        + trackJamBeamID);

               }
            }
         }

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
