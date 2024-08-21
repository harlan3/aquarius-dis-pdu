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

public class LiveEntityFire {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(45);

      byte pduType = 0;
      byte family = 0;
      short length = 0;
      byte pduStatus = 0;
      byte firingLiveEntIDSiteNumber = 0;
      byte firingLiveEntIDAppNumber = 0;
      short firingLiveEntIDEntityNumber = 0;
      byte flags = 0;
      byte targetLiveEntityIDSiteNum = 0;
      byte targetLiveEntityIDAppNum = 0;
      short targetLiveEntityIDEntityNum = 0;
      byte munitionLiveEntityIDSiteNum = 0;
      byte munitionLiveEntityIDAppNum = 0;
      short munitionLiveEntityIDEntityNum = 0;
      byte eventIDSiteNum = 0;
      byte eventIDAppNum = 0;
      short eventIDEventNum = 0;
      short locationRefPoint = 0;
      short locationDeltaX = 0;
      short locationDeltaY = 0;
      short locationDeltaZ = 0;
      byte munitionDescriptorMunitionEntKind = 0;
      byte munitionDescriptorMunitionDomain = 0;
      short munitionDescriptorMunitionCountry = 0;
      byte munitionDescriptorMunitionCategory = 0;
      byte munitionDescriptorMunitionSubCategory = 0;
      byte munitionDescriptorMunitionSpecific = 0;
      byte munitionDescriptorMunitionExtra = 0;
      short munitionDescriptorWarhead = 0;
      short munitionDescriptorFuse = 0;
      short munitionDescriptorQuantity = 0;
      short munitionDescriptorRate = 0;
      short velocityXComponent = 0;
      short velocityYComponent = 0;
      short velocityZComponent = 0;
      short range = 0;
      
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
         
         firingLiveEntIDSiteNumber = din.readByte();
         firingLiveEntIDAppNumber = din.readByte();
         firingLiveEntIDEntityNumber = din.readShort();
         flags = din.readByte();
         targetLiveEntityIDSiteNum = din.readByte();
         targetLiveEntityIDAppNum = din.readByte();
         targetLiveEntityIDEntityNum = din.readShort();
         munitionLiveEntityIDSiteNum = din.readByte();
         munitionLiveEntityIDAppNum = din.readByte();
         munitionLiveEntityIDEntityNum = din.readShort();
         eventIDSiteNum = din.readByte();
         eventIDAppNum = din.readByte();
         eventIDEventNum = din.readShort();
         locationRefPoint = din.readShort();
         locationDeltaX = din.readShort();
         locationDeltaY = din.readShort();
         locationDeltaZ = din.readShort();
         munitionDescriptorMunitionEntKind = din.readByte();
         munitionDescriptorMunitionDomain = din.readByte();
         munitionDescriptorMunitionCountry = din.readShort();
         munitionDescriptorMunitionCategory = din.readByte();
         munitionDescriptorMunitionSubCategory = din.readByte();
         munitionDescriptorMunitionSpecific = din.readByte();
         munitionDescriptorMunitionExtra = din.readByte();
         munitionDescriptorWarhead = din.readShort();
         munitionDescriptorFuse = din.readShort();
         munitionDescriptorQuantity = din.readShort();
         munitionDescriptorRate = din.readShort();
         velocityXComponent = din.readShort();
         velocityYComponent = din.readShort();
         velocityZComponent = din.readShort();
         range = din.readShort();
         
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("firingLiveEntIDSiteNumber") + firingLiveEntIDSiteNumber);
         System.out.println(LoggerUtil.prettyPrintField("firingLiveEntIDAppNumber") + firingLiveEntIDAppNumber);
         System.out.println(LoggerUtil.prettyPrintField("firingLiveEntIDEntityNumber") + firingLiveEntIDEntityNumber);
         System.out.println(LoggerUtil.prettyPrintField("flags") + LoggerUtil.toBitString(flags));
         System.out.println(LoggerUtil.prettyPrintField("targetLiveEntityIDSiteNum") + targetLiveEntityIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("targetLiveEntityIDAppNum") + targetLiveEntityIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("targetLiveEntityIDEntityNum") + targetLiveEntityIDEntityNum);
         System.out.println(LoggerUtil.prettyPrintField("munitionLiveEntityIDSiteNum") + munitionLiveEntityIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("munitionLiveEntityIDAppNum") + munitionLiveEntityIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("munitionLiveEntityIDEntityNum") + munitionLiveEntityIDEntityNum);
         System.out.println(LoggerUtil.prettyPrintField("eventIDSiteNum") + eventIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("eventIDAppNum") + eventIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("eventIDEventNum") + eventIDEventNum);
         System.out.println(LoggerUtil.prettyPrintField("locationRefPoint") + locationRefPoint);
         System.out.println(LoggerUtil.prettyPrintField("locationDeltaX") + locationDeltaX);
         System.out.println(LoggerUtil.prettyPrintField("locationDeltaY") + locationDeltaY);
         System.out.println(LoggerUtil.prettyPrintField("locationDeltaZ") + locationDeltaZ);         
         System.out.println(LoggerUtil.prettyPrintField("munitionDescriptorMunitionEntKind") + munitionDescriptorMunitionEntKind);
         System.out.println(LoggerUtil.prettyPrintField("munitionDescriptorMunitionDomain") + munitionDescriptorMunitionDomain);
         System.out.println(LoggerUtil.prettyPrintField("munitionDescriptorMunitionCountry") + munitionDescriptorMunitionCountry);
         System.out.println(LoggerUtil.prettyPrintField("munitionDescriptorMunitionCategory") + munitionDescriptorMunitionCategory);
         System.out.println(LoggerUtil.prettyPrintField("munitionDescriptorMunitionSubCategory") + munitionDescriptorMunitionSubCategory);
         System.out.println(LoggerUtil.prettyPrintField("munitionDescriptorMunitionSpecific") + munitionDescriptorMunitionSpecific);
         System.out.println(LoggerUtil.prettyPrintField("munitionDescriptorMunitionExtra") + munitionDescriptorMunitionExtra);
         System.out.println(LoggerUtil.prettyPrintField("munitionDescriptorWarhead") + munitionDescriptorWarhead);
         System.out.println(LoggerUtil.prettyPrintField("munitionDescriptorFuse") + munitionDescriptorFuse);
         System.out.println(LoggerUtil.prettyPrintField("munitionDescriptorQuantity") + munitionDescriptorQuantity);
         System.out.println(LoggerUtil.prettyPrintField("munitionDescriptorRate") + munitionDescriptorRate);
         System.out.println(LoggerUtil.prettyPrintField("velocityXComponent") + velocityXComponent);
         System.out.println(LoggerUtil.prettyPrintField("velocityYComponent") + velocityYComponent);
         System.out.println(LoggerUtil.prettyPrintField("velocityZComponent") + velocityZComponent);
         System.out.println(LoggerUtil.prettyPrintField("range") + range);
         
         
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
