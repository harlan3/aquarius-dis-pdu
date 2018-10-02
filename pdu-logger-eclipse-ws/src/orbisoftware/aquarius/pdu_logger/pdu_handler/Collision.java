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

public class Collision {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(
            packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();

      byte pduType = 0;
      short length = 0;
      short issuingEntIDSite = 0;
      short issuingEntIDApp = 0;
      short issuingEntIDEntity = 0;
      short collidingEntIDSite = 0;
      short collidingEntIDApp = 0;
      short collidingEntIDEntity = 0;
      short eventIDSite = 0;
      short eventIDApp = 0;
      short eventIDEventNum = 0;
      byte collisionType = 0;
      @SuppressWarnings("unused")
      byte padding = 0;
      float xVelocity = 0.0f;
      float yVelocity = 0.0f;
      float zVelocity = 0.0f;
      float mass = 0.0f;
      double xLocation = 0.0;
      double yLocation = 0.0;
      double zLocation = 0.0;

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
         issuingEntIDSite = din.readShort();
         issuingEntIDApp = din.readShort();
         issuingEntIDEntity = din.readShort();
         collidingEntIDSite = din.readShort();
         collidingEntIDApp = din.readShort();
         collidingEntIDEntity = din.readShort();
         eventIDSite = din.readShort();
         eventIDApp = din.readShort();
         eventIDEventNum = din.readShort();
         collisionType = din.readByte();
         padding = din.readByte();
         xVelocity = din.readFloat();
         yVelocity = din.readFloat();
         zVelocity = din.readFloat();
         mass = din.readFloat();
         xLocation = din.readFloat();
         yLocation = din.readFloat();
         zLocation = din.readFloat();

         System.out.println("             pduType : "
               + PDU_Type.values()[pduType]);
         System.out.println("              length : " + length);
         System.out.println("    issuingEntIDSite : " + issuingEntIDSite);
         System.out.println("     issuingEntIDApp : " + issuingEntIDApp);
         System.out.println("  issuingEntIDEntity : " + issuingEntIDEntity);
         System.out.println("  collidingEntIDSite : " + collidingEntIDSite);
         System.out.println("   collidingEntIDApp : " + collidingEntIDApp);
         System.out.println("collidingEntIDEntity : " + collidingEntIDEntity);
         System.out.println("         eventIDSite : " + eventIDSite);
         System.out.println("          eventIDApp : " + eventIDApp);
         System.out.println("     eventIDEventNum : " + eventIDEventNum);
         System.out.println("       collisionType : " + collisionType);
         System.out.println("           xVelocity : " + xVelocity);
         System.out.println("           yVelocity : " + yVelocity);
         System.out.println("           zVelocity : " + zVelocity);
         System.out.println("                mass : " + mass);
         System.out.println("           xLocation : " + xLocation);
         System.out.println("           yLocation : " + yLocation);
         System.out.println("           zLocation : " + zLocation);

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
