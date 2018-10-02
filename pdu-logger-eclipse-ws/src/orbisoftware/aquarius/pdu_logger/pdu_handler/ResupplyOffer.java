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

public class ResupplyOffer {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(
            packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();

      byte pduType = 0;
      short length = 0;
      short receiveEntIDSite = 0;
      short receiveEntIDApp = 0;
      short receiveEntIDEntity = 0;
      short supplyEntIDSite = 0;
      short supplyEntIDApp = 0;
      short supplyEntIDEntity = 0;
      byte numberSupplyTypes = 0;
      @SuppressWarnings("unused")
      short padding1 = 0;
      @SuppressWarnings("unused")
      byte padding2 = 0;
      byte supplyEntityKind = 0;
      byte supplyDomain = 0;
      short supplyCountry = 0;
      byte supplyCategory = 0;
      byte supplySubcategory = 0;
      byte supplySpecific = 0;
      byte supplyExtra = 0;
      float supplyQuantity = 0.0f;

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
         receiveEntIDSite = din.readShort();
         receiveEntIDApp = din.readShort();
         receiveEntIDEntity = din.readShort();
         supplyEntIDSite = din.readShort();
         supplyEntIDApp = din.readShort();
         supplyEntIDEntity = din.readShort();
         numberSupplyTypes = din.readByte();
         padding1 = din.readShort();
         padding2 = din.readByte();

         System.out.println("           pduType : "
               + PDU_Type.values()[pduType]);
         System.out.println("            length : " + length);
         System.out.println("  receiveEntIDSite : " + receiveEntIDSite);
         System.out.println("   receiveEntIDApp : " + receiveEntIDApp);
         System.out.println("receiveEntIDEntity : " + receiveEntIDEntity);
         System.out.println("   supplyEntIDSite : " + supplyEntIDSite);
         System.out.println("    supplyEntIDApp : " + supplyEntIDApp);
         System.out.println(" supplyEntIDEntity : " + supplyEntIDEntity);
         System.out.println(" numberSupplyTypes : " + numberSupplyTypes);

         for (int y = 0; y < numberSupplyTypes; y++) {

            supplyEntityKind = din.readByte();
            supplyDomain = din.readByte();
            supplyCountry = din.readShort();
            supplyCategory = din.readByte();
            supplySubcategory = din.readByte();
            supplySpecific = din.readByte();
            supplyExtra = din.readByte();
            supplyQuantity = din.readFloat();

            System.out.println("   (Supply Record) : " + y);
            System.out.println("  supplyEntityKind : " + supplyEntityKind);
            System.out.println("      supplyDomain : " + supplyDomain);
            System.out.println("     supplyCountry : " + supplyCountry);
            System.out.println("    supplyCategory : " + supplyCategory);
            System.out.println(" supplySubcategory : " + supplySubcategory);
            System.out.println("    supplySpecific : " + supplySpecific);
            System.out.println("       supplyExtra : " + supplyExtra);
            System.out.println("    supplyQuantity : " + supplyQuantity);
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
