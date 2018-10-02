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

public class CreateEntity {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(
            packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();

      byte pduType = 0;
      short length = 0;
      short origEntIDSite = 0;
      short origEntIDApp = 0;
      short origEntIDEntity = 0;
      short recEntIDSite = 0;
      short recEntIDApp = 0;
      short recEntIDEntity = 0;
      int requestID = 0;

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
         origEntIDSite = din.readShort();
         origEntIDApp = din.readShort();
         origEntIDEntity = din.readShort();
         recEntIDSite = din.readShort();
         recEntIDApp = din.readShort();
         recEntIDEntity = din.readShort();
         requestID = din.readInt();

         System.out.println("           pduType : "
               + PDU_Type.values()[pduType]);
         System.out.println("            length : " + length);
         System.out.println("     origEntIDSite : " + origEntIDSite);
         System.out.println("      origEntIDApp : " + origEntIDApp);
         System.out.println("   origEntIDEntity : " + origEntIDEntity);
         System.out.println("      recEntIDSite : " + recEntIDSite);
         System.out.println("       recEntIDApp : " + recEntIDApp);
         System.out.println("    recEntIDEntity : " + recEntIDEntity);
         System.out.println("         requestID : " + requestID);

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
