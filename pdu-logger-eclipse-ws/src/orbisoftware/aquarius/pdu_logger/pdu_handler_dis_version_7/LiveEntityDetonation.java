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

public class LiveEntityDetonation {

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
      
      byte firingLiveEntIDSiteNumber = 0;
      byte firingLiveEntIDAppNumber = 0;
      short firingLiveEntIDEntityNumber = 0;
      byte detonationFlag1 = 0;
      byte detonationFlag2 = 0;
      byte targetLiveEntityIDSiteNumber = 0;
      byte targetLiveEntityIDAppNumber = 0;
      short targetLiveEntityIDEntityNumber = 0;
      byte munitionLiveEntityIDSiteNumber = 0;
      byte munitionLiveEntityIDAppNumber = 0;
      short munitionLiveEntityIDEntityNumber = 0;
      byte eventIDSiteNumber = 0;
      byte eventIDAppNumber = 0;
      short eventIDEventNumber = 0;
      short locWorldCoordRefPoint = 0;
      short locWorldCoordDeltaX = 0;
      short locWorldCoordDeltaY = 0;
      short locWorldCoordDeltaZ = 0;
      short velocityXComp = 0;
      short velocityYComp = 0;
      short velocityZComp = 0;
      short munitionOrientPsi = 0;
      short munitionOrientTheta = 0;
      short munitionOrientPhi = 0;
      byte munitionDescMunitionEntKind = 0;
      byte munitionDescMunitionDomain = 0;
      short munitionDescMunitionCountry = 0;
      byte munitionDescMunitionCategory = 0;
      byte munitionDescMunitionSubCategory = 0;
      byte munitionDescMunitionSpecific = 0;
      byte munitionDescMunitionExtra = 0;
      short munitionDescMunitionWarhead = 0;
      short munitionDescMunitionFuse = 0;
      short munitionDescMunitionQuantity = 0;
      short munitionDescMunitionRate = 0;
      short locEntityCoordXComp = 0;
      short locEntityCoordYComp = 0;
      short locEntityCoordZComp = 0;
      byte detonationResult = 0;
      
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
         
         firingLiveEntIDSiteNumber = din.readByte();
         firingLiveEntIDAppNumber = din.readByte();
         firingLiveEntIDEntityNumber = din.readShort();
         detonationFlag1 = din.readByte();
         detonationFlag2 = din.readByte();
         targetLiveEntityIDSiteNumber = din.readByte();
         targetLiveEntityIDAppNumber = din.readByte();
         targetLiveEntityIDEntityNumber = din.readShort();
         munitionLiveEntityIDSiteNumber = din.readByte();
         munitionLiveEntityIDAppNumber = din.readByte();
         munitionLiveEntityIDEntityNumber = din.readShort();
         eventIDSiteNumber = din.readByte();
         eventIDAppNumber = din.readByte();
         eventIDEventNumber = din.readShort();
         locWorldCoordRefPoint = din.readShort();
         locWorldCoordDeltaX = din.readShort();
         locWorldCoordDeltaY = din.readShort();
         locWorldCoordDeltaZ = din.readShort();
         velocityXComp = din.readShort();
         velocityYComp = din.readShort();
         velocityZComp = din.readShort();
         munitionOrientPsi = din.readShort();
         munitionOrientTheta = din.readShort();
         munitionOrientPhi = din.readShort();
         munitionDescMunitionEntKind = din.readByte();
         munitionDescMunitionDomain = din.readByte();
         munitionDescMunitionCountry = din.readShort();
         munitionDescMunitionCategory = din.readByte();
         munitionDescMunitionSubCategory = din.readByte();
         munitionDescMunitionSpecific = din.readByte();
         munitionDescMunitionExtra = din.readByte();
         munitionDescMunitionWarhead = din.readShort();
         munitionDescMunitionFuse = din.readShort();
         munitionDescMunitionQuantity = din.readShort();
         munitionDescMunitionRate = din.readShort();
         locEntityCoordXComp = din.readShort();
         locEntityCoordYComp = din.readShort();
         locEntityCoordZComp = din.readShort();
         detonationResult = din.readByte();
         
         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("firingLiveEntIDSiteNumber") + firingLiveEntIDSiteNumber);
         System.out.println(LoggerUtil.prettyPrintField("firingLiveEntIDAppNumber") + firingLiveEntIDAppNumber);
         System.out.println(LoggerUtil.prettyPrintField("firingLiveEntIDEntityNumber") + firingLiveEntIDEntityNumber);
         System.out.println(LoggerUtil.prettyPrintField("detonationFlag1") + LoggerUtil.toBitString(detonationFlag1));
         System.out.println(LoggerUtil.prettyPrintField("detonationFlag2") + LoggerUtil.toBitString(detonationFlag2));
         System.out.println(LoggerUtil.prettyPrintField("targetLiveEntityIDSiteNumber") + targetLiveEntityIDSiteNumber);
         System.out.println(LoggerUtil.prettyPrintField("targetLiveEntityIDAppNumber") + targetLiveEntityIDAppNumber);
         System.out.println(LoggerUtil.prettyPrintField("targetLiveEntityIDEntityNumber") + targetLiveEntityIDEntityNumber);
         System.out.println(LoggerUtil.prettyPrintField("munitionLiveEntityIDSiteNumber") + munitionLiveEntityIDSiteNumber);
         System.out.println(LoggerUtil.prettyPrintField("munitionLiveEntityIDAppNumber") + munitionLiveEntityIDAppNumber);
         System.out.println(LoggerUtil.prettyPrintField("munitionLiveEntityIDEntityNumber") + munitionLiveEntityIDEntityNumber);
         System.out.println(LoggerUtil.prettyPrintField("eventIDSiteNumber") + eventIDSiteNumber);
         System.out.println(LoggerUtil.prettyPrintField("eventIDAppNumber") + eventIDAppNumber);
         System.out.println(LoggerUtil.prettyPrintField("eventIDEventNumber") + eventIDEventNumber);
         System.out.println(LoggerUtil.prettyPrintField("locWorldCoordRefPoint") + locWorldCoordRefPoint);
         System.out.println(LoggerUtil.prettyPrintField("locWorldCoordDeltaX") + locWorldCoordDeltaX);
         System.out.println(LoggerUtil.prettyPrintField("locWorldCoordDeltaY") + locWorldCoordDeltaY);
         System.out.println(LoggerUtil.prettyPrintField("locWorldCoordDeltaZ") + locWorldCoordDeltaZ);
         System.out.println(LoggerUtil.prettyPrintField("velocityXComp") + velocityXComp);
         System.out.println(LoggerUtil.prettyPrintField("velocityYComp") + velocityYComp);
         System.out.println(LoggerUtil.prettyPrintField("velocityZComp") + velocityZComp);
         System.out.println(LoggerUtil.prettyPrintField("munitionOrientPsi") + munitionOrientPsi);
         System.out.println(LoggerUtil.prettyPrintField("munitionOrientTheta") + munitionOrientTheta);
         System.out.println(LoggerUtil.prettyPrintField("munitionOrientPhi") + munitionOrientPhi);
         System.out.println(LoggerUtil.prettyPrintField("munitionDescMunitionEntKind") + munitionDescMunitionEntKind);
         System.out.println(LoggerUtil.prettyPrintField("munitionDescMunitionDomain") + munitionDescMunitionDomain);
         System.out.println(LoggerUtil.prettyPrintField("munitionDescMunitionCountry") + munitionDescMunitionCountry);
         System.out.println(LoggerUtil.prettyPrintField("munitionDescMunitionCategory") + munitionDescMunitionCategory);
         System.out.println(LoggerUtil.prettyPrintField("munitionDescMunitionSubCategory") + munitionDescMunitionSubCategory);
         System.out.println(LoggerUtil.prettyPrintField("munitionDescMunitionSpecific") + munitionDescMunitionSpecific);
         System.out.println(LoggerUtil.prettyPrintField("munitionDescMunitionExtra") + munitionDescMunitionExtra);
         System.out.println(LoggerUtil.prettyPrintField("munitionDescMunitionWarhead") + munitionDescMunitionWarhead);
         System.out.println(LoggerUtil.prettyPrintField("munitionDescMunitionFuse") + munitionDescMunitionFuse);
         System.out.println(LoggerUtil.prettyPrintField("munitionDescMunitionQuantity") + munitionDescMunitionQuantity);
         System.out.println(LoggerUtil.prettyPrintField("munitionDescMunitionRate") + munitionDescMunitionRate);
         System.out.println(LoggerUtil.prettyPrintField("locEntityCoordXComp") + locEntityCoordXComp);
         System.out.println(LoggerUtil.prettyPrintField("locEntityCoordYComp") + locEntityCoordYComp);
         System.out.println(LoggerUtil.prettyPrintField("locEntityCoordZComp") + locEntityCoordZComp);
         System.out.println(LoggerUtil.prettyPrintField("detonationResult") + detonationResult);
         
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
