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

public class Fire {

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
      byte descriptorEntityKind = 0;
      byte descriptorDomain = 0;
      short descriptorCountry = 0;
      byte descriptorCategory = 0;
      byte descriptorSubcategory = 0;
      byte descriptorSpecific = 0;
      byte descriptorExtra = 0;
      short descriptorRecordWarhead = 0;
      short descriptorRecordFuse = 0;
      short descriptorRecordQuantity = 0;
      short descriptorRecordRate = 0;
      float xVelocity = 0.0f;
      float yVelocity = 0.0f;
      float zVelocity = 0.0f;
      float range = 0.0f;

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
         descriptorEntityKind = din.readByte();
         descriptorDomain = din.readByte();
         descriptorCountry = din.readShort();
         descriptorCategory = din.readByte();
         descriptorSubcategory = din.readByte();
         descriptorSpecific = din.readByte();
         descriptorExtra = din.readByte();
         descriptorRecordWarhead = din.readShort();
         descriptorRecordFuse = din.readShort();
         descriptorRecordQuantity = din.readShort();
         descriptorRecordRate = din.readShort();
         xVelocity = din.readFloat();
         yVelocity = din.readFloat();
         zVelocity = din.readFloat();
         range = din.readFloat();

         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
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
         System.out.println(LoggerUtil.prettyPrintField("descriptorEntityKind") + descriptorEntityKind);
         System.out.println(LoggerUtil.prettyPrintField("descriptorDomain") + descriptorDomain);
         System.out.println(LoggerUtil.prettyPrintField("descriptorCountry") + descriptorCountry);
         System.out.println(LoggerUtil.prettyPrintField("descriptorCategory") + descriptorCategory);
         System.out.println(LoggerUtil.prettyPrintField("descriptorSubcategory") + descriptorSubcategory);
         System.out.println(LoggerUtil.prettyPrintField("descriptorSpecific") + descriptorSpecific);
         System.out.println(LoggerUtil.prettyPrintField("descriptorExtra") + descriptorExtra);
         System.out.println(LoggerUtil.prettyPrintField("descriptorRecordWarhead") + descriptorRecordWarhead);
         System.out.println(LoggerUtil.prettyPrintField("descriptorRecordFuse") + descriptorRecordFuse);
         System.out.println(LoggerUtil.prettyPrintField("descriptorRecordQuantity") + descriptorRecordQuantity);
         System.out.println(LoggerUtil.prettyPrintField("descriptorRecordRate") + descriptorRecordRate);
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
