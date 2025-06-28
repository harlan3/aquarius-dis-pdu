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

public class Detonation {

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
      short firingEntSite = 0;
      short firingEntApp = 0;
      short firingEntID = 0;
      short targetEntSite = 0;
      short targetEntApp = 0;
      short targetEntEntity = 0;
      short munitionIDSite = 0;
      short munitionIDApp = 0;
      short munitionEntity = 0;
      short eventSite = 0;
      short eventApp = 0;
      short eventNum = 0;
      float xVelocity = 0.0f;
      float yVelocity = 0.0f;
      float zVelocity = 0.0f;
      double locWorldCoordXLocation = 0.0;
      double locWorldCoordYLocation = 0.0;
      double locWorldCoordZLocation = 0.0;
      byte burstDescEntityKind = 0;
      byte burstDescDomain = 0;
      short burstDescCountry = 0;
      byte burstDescCategory = 0;
      byte burstDescSubCat = 0;
      byte burstDescSpec = 0;
      byte burstDescExtra = 0;
      short burstWarhead = 0;
      short burstFuse = 0;
      short burstQuantity = 0;
      short burstRate = 0;
      float locEntityCoordXLocation = 0.0f;
      float locEntityCoordYLocation = 0.0f;
      float locEntityCoordZLocation = 0.0f;
      byte detonationResult = 0;
      byte numberArticulationParams = 0;
      short padding;
      
      try {

         /* Start Message Header */
         protocolVersion = din.readByte();
         exercise = din.readByte();
         pduType = din.readByte();
         family = din.readByte();
         din.reset();

         din.skipBytes(8);
         length = din.readShort();
         din.reset();
         /* End Message Header */

         din.skipBytes(12);
         firingEntSite = din.readShort();
         firingEntApp = din.readShort();
         firingEntID = din.readShort();
         targetEntSite = din.readShort();
         targetEntApp = din.readShort();
         targetEntEntity = din.readShort();
         munitionIDSite = din.readShort();
         munitionIDApp = din.readShort();
         munitionEntity = din.readShort();
         eventSite = din.readShort();
         eventApp = din.readShort();
         eventNum = din.readShort();
         xVelocity = din.readFloat();
         yVelocity = din.readFloat();
         zVelocity = din.readFloat();
         locWorldCoordXLocation = din.readDouble();
         locWorldCoordYLocation = din.readDouble();
         locWorldCoordZLocation = din.readDouble();
         burstDescEntityKind = din.readByte();
         burstDescDomain = din.readByte();
         burstDescCountry = din.readShort();
         burstDescCategory = din.readByte();
         burstDescSubCat = din.readByte();
         burstDescSpec = din.readByte();
         burstDescExtra = din.readByte();
         burstWarhead = din.readShort();
         burstFuse = din.readShort();
         burstQuantity = din.readShort();
         burstRate = din.readShort();
         locEntityCoordXLocation = din.readFloat();
         locEntityCoordYLocation = din.readFloat();
         locEntityCoordZLocation = din.readFloat();
         detonationResult = din.readByte();
         numberArticulationParams = din.readByte();
         padding = din.readShort();
         
         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("firingEntSite") + firingEntSite);
         System.out.println(LoggerUtil.prettyPrintField("firingEntApp") + firingEntApp);
         System.out.println(LoggerUtil.prettyPrintField("firingEntID") + firingEntID);
         System.out.println(LoggerUtil.prettyPrintField("targetEntSite") + targetEntSite);
         System.out.println(LoggerUtil.prettyPrintField("targetEntApp") + targetEntApp);
         System.out.println(LoggerUtil.prettyPrintField("targetEntEntity") + targetEntEntity);
         System.out.println(LoggerUtil.prettyPrintField("munitionIDSite") + munitionIDSite);
         System.out.println(LoggerUtil.prettyPrintField("munitionIDApp") + munitionIDApp);
         System.out.println(LoggerUtil.prettyPrintField("munitionEntity") + munitionEntity);
         System.out.println(LoggerUtil.prettyPrintField("eventSite") + eventSite);
         System.out.println(LoggerUtil.prettyPrintField("eventApp") + eventApp);
         System.out.println(LoggerUtil.prettyPrintField("eventNum") + eventNum);
         System.out.println(LoggerUtil.prettyPrintField("xVelocity") + xVelocity);
         System.out.println(LoggerUtil.prettyPrintField("yVelocity") + yVelocity);
         System.out.println(LoggerUtil.prettyPrintField("zVelocity") + zVelocity);
         System.out.println(LoggerUtil.prettyPrintField("locWorldCoordXLocation") + locWorldCoordXLocation);
         System.out.println(LoggerUtil.prettyPrintField("locWorldCoordYLocation") + locWorldCoordYLocation);
         System.out.println(LoggerUtil.prettyPrintField("locWorldCoordZLocation") + locWorldCoordZLocation);
         System.out.println(LoggerUtil.prettyPrintField("burstDescEntityKind") + burstDescEntityKind);
         System.out.println(LoggerUtil.prettyPrintField("burstDescDomain") + burstDescDomain);
         System.out.println(LoggerUtil.prettyPrintField("burstDescCountry") + burstDescCountry);
         System.out.println(LoggerUtil.prettyPrintField("burstDescCategory") + burstDescCategory);
         System.out.println(LoggerUtil.prettyPrintField("burstDescSubCat") + burstDescSubCat);
         System.out.println(LoggerUtil.prettyPrintField("burstDescSpec") + burstDescSpec);
         System.out.println(LoggerUtil.prettyPrintField("burstDescExtra") + burstDescExtra);
         System.out.println(LoggerUtil.prettyPrintField("burstWarhead") + burstWarhead);
         System.out.println(LoggerUtil.prettyPrintField("burstFuse") + burstFuse);
         System.out.println(LoggerUtil.prettyPrintField("burstQuantity") + burstQuantity);
         System.out.println(LoggerUtil.prettyPrintField("burstRate") + burstRate);
         System.out.println(LoggerUtil.prettyPrintField("locEntityCoordXLocation") + locEntityCoordXLocation);
         System.out.println(LoggerUtil.prettyPrintField("locEntityCoordYLocation") + locEntityCoordYLocation);
         System.out.println(LoggerUtil.prettyPrintField("locEntityCoordZLocation") + locEntityCoordZLocation);
         System.out.println(LoggerUtil.prettyPrintField("detonationResult") + detonationResult);
         System.out.println(LoggerUtil.prettyPrintField("numberArticulationParams") + numberArticulationParams);
         
         for (int i=0; i<numberArticulationParams; i++) {
            
            byte parameterTypeDesign = 0;
            byte change = 0;
            short partAttachedToID = 0;
            int parameterType = 0;
            long parameterValue = 0;
            
            parameterTypeDesign = din.readByte();
            change = din.readByte();
            partAttachedToID = din.readShort();
            parameterType = din.readInt();
            parameterValue = din.readLong();
            
            System.out.println(LoggerUtil.prettyPrintField("parameterTypeDesign") + parameterTypeDesign);
            System.out.println(LoggerUtil.prettyPrintField("change") + change);
            System.out.println(LoggerUtil.prettyPrintField("partAttachedToID") + partAttachedToID);
            System.out.println(LoggerUtil.prettyPrintField("parameterType") + parameterType);
            System.out.println(LoggerUtil.prettyPrintField("parameterValue") + parameterValue);
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
