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

public class Detonation {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(
            packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();

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
      float xVelocity = 0.0f;
      float yVelocity = 0.0f;
      float zVelocity = 0.0f;
      double xLocation = 0.0;
      double yLocation = 0.0;
      double zLocation = 0.0;
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
         xVelocity = din.readFloat();
         yVelocity = din.readFloat();
         zVelocity = din.readFloat();
         xLocation = din.readDouble();
         yLocation = din.readDouble();
         zLocation = din.readDouble();
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

         System.out.println("              pduType : "
               + PDU_Type.values()[pduType]);
         System.out.println("               length : " + length);
         System.out.println("        firingEntSite : " + firingEntSite);
         System.out.println("         firingEntApp : " + firingEntApp);
         System.out.println("          firingEntID : " + firingEntID);
         System.out.println("        targetEntSite : " + targetEntSite);
         System.out.println("         targetEntApp : " + targetEntApp);
         System.out.println("          targetEntID : " + targetEntID);
         System.out.println("       munitionIDSite : " + munitionIDSite);
         System.out.println("        munitionIDApp : " + munitionIDApp);
         System.out.println("        munitionEntID : " + munitionEntID);
         System.out.println("            eventSite : " + eventSite);
         System.out.println("             eventApp : " + eventApp);
         System.out.println("             eventNum : " + eventNum);
         System.out.println("            xVelocity : " + xVelocity);
         System.out.println("            yVelocity : " + yVelocity);
         System.out.println("            zVelocity : " + zVelocity);
         System.out.println("            xLocation : " + xLocation);
         System.out.println("            yLocation : " + yLocation);
         System.out.println("            zLocation : " + zLocation);
         System.out.println("  burstDescEntityKind : " + burstDescEntityKind);
         System.out.println("      burstDescDomain : " + burstDescDomain);
         System.out.println("     burstDescCountry : " + burstDescCountry);
         System.out.println("    burstDescCategory : " + burstDescCategory);
         System.out.println("      burstDescSubCat : " + burstDescSubCat);
         System.out.println("        burstDescSpec : " + burstDescSpec);
         System.out.println("       burstDescExtra : " + burstDescExtra);
         System.out.println("         burstWarhead : " + burstWarhead);
         System.out.println("            burstFuse : " + burstFuse);
         System.out.println("        burstQuantity : " + burstQuantity);
         System.out.println("            burstRate : " + burstRate);

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
