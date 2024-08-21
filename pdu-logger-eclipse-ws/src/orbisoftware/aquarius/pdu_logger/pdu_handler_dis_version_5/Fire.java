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

public class Fire {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(30);

      byte pduType = 0;
      short length = 0;
      short firingEntSite = 0;
      short firingEntApp = 0;
      short firingEntID = 0;
      short targetEntSite = 0;
      short targetEntApp = 0;
      short targetEntID = 0;
      short munitionIDSite = 0;
      short munitionIDApp = 0;
      short munitionEntID = 0;
      short eventSite = 0;
      short eventApp = 0;
      short eventNum = 0;
      int fireMissionIndex = 0;
      double xLocation = 0.0;
      double yLocation = 0.0;
      double zLocation = 0.0;
      byte burstMunitionEntityKind = 0;
      byte burstMunitionDomain = 0;
      short burstMunitionCountry = 0;
      byte burstMunitionCategory = 0;
      byte burstMunitionSubcategory = 0;
      byte burstMunitionSpecific = 0;
      byte burstMunitionExtra = 0;
      short burstWarhead = 0;
      short burstFuse = 0;
      short burstQuantity = 0;
      short burstRate = 0;
      float xVelocity = 0.0f;
      float yVelocity = 0.0f;
      float zVelocity = 0.0f;
      float range = 0.0f;

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
         firingEntSite = din.readShort();
         firingEntApp = din.readShort();
         firingEntID = din.readShort();
         targetEntSite = din.readShort();
         targetEntApp = din.readShort();
         targetEntID = din.readShort();
         munitionIDSite = din.readShort();
         munitionIDApp = din.readShort();
         munitionEntID = din.readShort();
         eventSite = din.readShort();
         eventApp = din.readShort();
         eventNum = din.readShort();
         fireMissionIndex = din.readInt();
         xLocation = din.readDouble();
         yLocation = din.readDouble();
         zLocation = din.readDouble();
         burstMunitionEntityKind = din.readByte();
         burstMunitionDomain = din.readByte();
         burstMunitionCountry = din.readShort();
         burstMunitionCategory = din.readByte();
         burstMunitionSubcategory = din.readByte();
         burstMunitionSpecific = din.readByte();
         burstMunitionExtra = din.readByte();
         burstWarhead = din.readShort();
         burstFuse = din.readShort();
         burstQuantity = din.readShort();
         burstRate = din.readShort();
         xVelocity = din.readFloat();
         yVelocity = din.readFloat();
         zVelocity = din.readFloat();
         range = din.readFloat();

         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("firingEntSite") + firingEntSite);
         System.out.println(LoggerUtil.prettyPrintField("firingEntApp") + firingEntApp);
         System.out.println(LoggerUtil.prettyPrintField("firingEntID") + firingEntID);
         System.out.println(LoggerUtil.prettyPrintField("targetEntSite") + targetEntSite);
         System.out.println(LoggerUtil.prettyPrintField("targetEntApp") + targetEntApp);
         System.out.println(LoggerUtil.prettyPrintField("targetEntID") + targetEntID);
         System.out.println(LoggerUtil.prettyPrintField("munitionIDSite") + munitionIDSite);
         System.out.println(LoggerUtil.prettyPrintField("munitionIDApp") + munitionIDApp);
         System.out.println(LoggerUtil.prettyPrintField("munitionEntID") + munitionEntID);
         System.out.println(LoggerUtil.prettyPrintField("eventSite") + eventSite);
         System.out.println(LoggerUtil.prettyPrintField("eventApp") + eventApp);
         System.out.println(LoggerUtil.prettyPrintField("eventNum") + eventNum);
         System.out.println(LoggerUtil.prettyPrintField("fireMissionIndex") + fireMissionIndex);
         System.out.println(LoggerUtil.prettyPrintField("xLocation") + xLocation);
         System.out.println(LoggerUtil.prettyPrintField("yLocation") + yLocation);
         System.out.println(LoggerUtil.prettyPrintField("zLocation") + zLocation);
         System.out.println(LoggerUtil.prettyPrintField("burstMunitionEntityKind") + burstMunitionEntityKind);
         System.out.println(LoggerUtil.prettyPrintField("burstMunitionDomain") + burstMunitionDomain);
         System.out.println(LoggerUtil.prettyPrintField("burstMunitionCountry") + burstMunitionCountry);
         System.out.println(LoggerUtil.prettyPrintField("burstMunitionCategory") + burstMunitionCategory);
         System.out.println(LoggerUtil.prettyPrintField("burstMunitionSubcategory") + burstMunitionSubcategory);
         System.out.println(LoggerUtil.prettyPrintField("burstMunitionSpecific") + burstMunitionSpecific);
         System.out.println(LoggerUtil.prettyPrintField("burstMunitionExtra") + burstMunitionExtra);
         System.out.println(LoggerUtil.prettyPrintField("burstWarhead") + burstWarhead);
         System.out.println(LoggerUtil.prettyPrintField("burstFuse") + burstFuse);
         System.out.println(LoggerUtil.prettyPrintField("burstQuantity") + burstQuantity);
         System.out.println(LoggerUtil.prettyPrintField("burstRate") + burstRate);
         System.out.println(LoggerUtil.prettyPrintField("xVelocity") + xVelocity);
         System.out.println(LoggerUtil.prettyPrintField("yVelocity") + yVelocity);
         System.out.println(LoggerUtil.prettyPrintField("zVelocity") + zVelocity);
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
