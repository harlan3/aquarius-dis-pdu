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

public class Receiver {

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
      short radioRefIDSiteNum = 0;
      short radioRefIDAppNum = 0;
      short radioRefIDRefNum = 0;
      short radioNumber = 0;
      short receiverState = 0;
      short padding = 0;
      float receivedPower = 0;
      short transRadioRefIDSiteNum = 0;
      short transRadioRefIDAppNum = 0;
      short transRadioRefIDRefNum = 0;
      short transRadioNum = 0;

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
         
         radioRefIDSiteNum = din.readShort();
         radioRefIDAppNum = din.readShort();
         radioRefIDRefNum = din.readShort();
         radioNumber = din.readShort();
         receiverState = din.readShort();
         padding = din.readShort();
         receivedPower = din.readFloat();
         transRadioRefIDSiteNum = din.readShort();
         transRadioRefIDAppNum = din.readShort();
         transRadioRefIDRefNum = din.readShort();
         transRadioNum = din.readShort();

         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("radioRefIDSiteNum") + radioRefIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("radioRefIDAppNum") + radioRefIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("radioRefIDRefNum") + radioRefIDRefNum);
         System.out.println(LoggerUtil.prettyPrintField("radioNumber") + radioNumber);
         System.out.println(LoggerUtil.prettyPrintField("receiverState") + receiverState);
         System.out.println(LoggerUtil.prettyPrintField("receivedPower") + receivedPower);
         System.out.println(LoggerUtil.prettyPrintField("transRadioRefIDSiteNum") + transRadioRefIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("transRadioRefIDAppNum") + transRadioRefIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("transRadioRefIDRefNum") + transRadioRefIDRefNum);
         System.out.println(LoggerUtil.prettyPrintField("transRadioNum") + transRadioNum);

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
