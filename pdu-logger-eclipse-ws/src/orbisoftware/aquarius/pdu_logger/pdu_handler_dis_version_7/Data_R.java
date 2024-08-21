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

public class Data_R {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(50);
      
      byte pduType = 0;
      byte family = 0;
      short length = 0;
      byte pduStatus = 0;
      short origIDSite = 0;
      short origIDApp = 0;
      short origIDRef = 0;
      short recIDSite = 0;
      short recIDApp = 0;
      short recIDRef = 0;
      int requestID = 0;
      byte reqRelServ = 0;
      byte padding1 = 0;
      short padding2 = 0;
      int numberFixedDatumRecs = 0;
      int numberVariableDatumRecs = 0;
      int fixedDatumID = 0;
      int fixedDatumValue = 0;
      int variableDatumID = 0;
      int variableDatumLength = 0;
      byte variableDatumValue = 0;
      
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
         origIDSite = din.readShort();
         origIDApp = din.readShort();
         origIDRef = din.readShort();
         recIDSite = din.readShort();
         recIDApp = din.readShort();
         recIDRef = din.readShort();
         requestID = din.readInt();
         reqRelServ = din.readByte();
         padding1 = din.readByte();
         padding2 = din.readShort();
         numberFixedDatumRecs = din.readInt();
         numberVariableDatumRecs = din.readInt();

         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("origIDSite") + origIDSite);
         System.out.println(LoggerUtil.prettyPrintField("origIDApp") + origIDApp);
         System.out.println(LoggerUtil.prettyPrintField("origIDRef") + origIDRef);
         System.out.println(LoggerUtil.prettyPrintField("recIDSite") + recIDSite);
         System.out.println(LoggerUtil.prettyPrintField("recIDApp") + recIDApp);
         System.out.println(LoggerUtil.prettyPrintField("recIDRef") + recIDRef);
         System.out.println(LoggerUtil.prettyPrintField("requestID") + requestID);
         System.out.println(LoggerUtil.prettyPrintField("reqRelServ") + reqRelServ);
         System.out.println(LoggerUtil.prettyPrintField("numberFixedDatumRecs") + numberFixedDatumRecs);
         System.out.println(LoggerUtil.prettyPrintField("numberVariableDatumRecs") + numberVariableDatumRecs);

         for (int i = 0; i < numberFixedDatumRecs; i++) {

            fixedDatumID = din.readInt();
            fixedDatumValue = din.readInt();

            System.out.println(LoggerUtil.prettyPrintField("fixedDatumID") + fixedDatumID);
            System.out.println(LoggerUtil.prettyPrintField("fixedDatumValue") + fixedDatumValue);
         }

         for (int j = 0; j < numberVariableDatumRecs; j++) {

            variableDatumID = din.readInt();
            variableDatumLength = din.readInt();
            variableDatumValue = din.readByte();
            
            System.out.println(LoggerUtil.prettyPrintField("variableDatumID") + variableDatumID);
            System.out.println(LoggerUtil.prettyPrintField("variableDatumLength") + variableDatumLength);
            System.out.println(LoggerUtil.prettyPrintField("variableDatumValue") + variableDatumValue);
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
