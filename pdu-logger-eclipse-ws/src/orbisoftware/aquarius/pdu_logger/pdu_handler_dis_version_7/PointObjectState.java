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

public class PointObjectState {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(
            packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(30);
      
      byte pduType = 0;
      byte family = 0;
      short length = 0;
      byte pduStatus = 0;
      short objectIDSiteNum = 0;
      short objectIDAppNum = 0;
      short objectIDObjNum = 0;
      short referencedObjIDSiteNum = 0;
      short referencedObjIDAppNum = 0;
      short referencedObjIDObjNum = 0;
      short updateNumber = 0;
      byte forceID = 0;
      byte modifications = 0;
      byte objectTypeDomain = 0;
      byte objectTypeObjKind = 0;
      byte objectTypeCategory = 0;
      byte objectTypeSubcategory = 0;
      double locXComponent = 0.0;
      double locYComponent = 0.0;
      double locZComponent = 0.0;
      float objectOrientPsi = 0.0f;
      float objectOrientTheta = 0.0f;
      float objectOrientPhi = 0.0f;
      int specificObjAppear = 0;
      short generalObjAppear = 0;
      short padding1 = 0;
      short reqSimIDSiteNum = 0;
      short reqSimIDAppNum = 0;
      short recSimIDSiteNum = 0;
      short recSimIDAppNum = 0;
      int padding2 = 0;
      
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
         
         objectIDSiteNum = din.readShort();
         objectIDAppNum = din.readShort();
         objectIDObjNum = din.readShort();
         referencedObjIDSiteNum = din.readShort();
         referencedObjIDAppNum = din.readShort();
         referencedObjIDObjNum = din.readShort();
         updateNumber = din.readShort();
         forceID = din.readByte();
         modifications = din.readByte();
         objectTypeDomain = din.readByte();
         objectTypeObjKind = din.readByte();
         objectTypeCategory = din.readByte();
         objectTypeSubcategory = din.readByte();
         locXComponent = din.readDouble();
         locYComponent = din.readDouble();
         locZComponent = din.readDouble();
         objectOrientPsi = din.readFloat();
         objectOrientTheta = din.readFloat();
         objectOrientPhi = din.readFloat();
         specificObjAppear = din.readInt();
         generalObjAppear = din.readShort();
         padding1 = din.readShort();
         reqSimIDSiteNum = din.readShort();
         reqSimIDAppNum = din.readShort();
         recSimIDSiteNum = din.readShort();
         recSimIDAppNum = din.readShort();
         padding2 = din.readInt();

         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("objectIDSiteNum") + objectIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("referencedObjIDSiteNum") + referencedObjIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("referencedObjIDAppNum") + referencedObjIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("referencedObjIDObjNum") + referencedObjIDObjNum);
         System.out.println(LoggerUtil.prettyPrintField("updateNumber") + updateNumber);
         System.out.println(LoggerUtil.prettyPrintField("forceID") + forceID);
         System.out.println(LoggerUtil.prettyPrintField("modifications") + modifications);
         System.out.println(LoggerUtil.prettyPrintField("objectTypeDomain") + objectTypeDomain);
         System.out.println(LoggerUtil.prettyPrintField("objectTypeObjKind") + objectTypeObjKind);
         System.out.println(LoggerUtil.prettyPrintField("objectTypeCategory") + objectTypeCategory);
         System.out.println(LoggerUtil.prettyPrintField("objectTypeSubcategory") + objectTypeSubcategory);
         System.out.println(LoggerUtil.prettyPrintField("locXComponent") + locXComponent);
         System.out.println(LoggerUtil.prettyPrintField("locYComponent") + locYComponent);
         System.out.println(LoggerUtil.prettyPrintField("locZComponent") + locZComponent);
         System.out.println(LoggerUtil.prettyPrintField("objectOrientPsi") + objectOrientPsi);
         System.out.println(LoggerUtil.prettyPrintField("objectOrientTheta") + objectOrientTheta);
         System.out.println(LoggerUtil.prettyPrintField("specificObjAppear") + specificObjAppear);
         System.out.println(LoggerUtil.prettyPrintField("generalObjAppear") + generalObjAppear);
         System.out.println(LoggerUtil.prettyPrintField("reqSimIDSiteNum") + reqSimIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("reqSimIDAppNum") + reqSimIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("recSimIDSiteNum") + recSimIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("recSimIDAppNum") + recSimIDAppNum);

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
