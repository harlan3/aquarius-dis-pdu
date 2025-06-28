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

public class LinearObjectState {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(35);

      byte protocolVersion = 0;
      byte exercise = 0;
      byte pduType = 0;
      byte family = 0;
      short length = 0;
      byte pduStatus = 0;
      short objIDSiteNum = 0;
      short objIDAppNum = 0;
      short objIDObjNum = 0;
      short refObjIDSiteNum = 0;
      short refObjIDAppNum = 0;
      short refObjIDObjNum = 0;
      short updateNum = 0;
      byte forceID = 0;
      byte numLinearSegments = 0;
      short reqSimIDSiteNum = 0;
      short reqSimIDAppNum = 0;
      short recSimIDSiteNum = 0;
      short recSimIDAppNum = 0;
      byte objTypeDomain = 0;
      byte objTypeObjKind = 0;
      byte objTypeCat = 0;
      byte objTypeSubcat = 0;
   
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
         
         objIDSiteNum = din.readShort();
         objIDAppNum = din.readShort();
         objIDObjNum = din.readShort();
         refObjIDSiteNum = din.readShort();
         refObjIDAppNum = din.readShort();
         refObjIDObjNum = din.readShort();
         updateNum = din.readShort();
         forceID = din.readByte();
         numLinearSegments = din.readByte();
         reqSimIDSiteNum = din.readShort();
         reqSimIDAppNum = din.readShort();
         recSimIDSiteNum = din.readShort();
         recSimIDAppNum = din.readShort();
         objTypeDomain = din.readByte();
         objTypeObjKind = din.readByte();
         objTypeCat = din.readByte();
         objTypeSubcat = din.readByte();
         
         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("objIDSiteNum") + objIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("objIDAppNum") + objIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("objIDObjNum") + objIDObjNum);
         System.out.println(LoggerUtil.prettyPrintField("refObjIDSiteNum") + refObjIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("refObjIDAppNum") + refObjIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("refObjIDObjNum") + refObjIDObjNum);
         System.out.println(LoggerUtil.prettyPrintField("updateNum") + updateNum);
         System.out.println(LoggerUtil.prettyPrintField("forceID") + forceID);
         System.out.println(LoggerUtil.prettyPrintField("numLinearSegments") + numLinearSegments);
         System.out.println(LoggerUtil.prettyPrintField("reqSimIDSiteNum") + reqSimIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("reqSimIDAppNum") + reqSimIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("recSimIDSiteNum") + recSimIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("recSimIDAppNum") + recSimIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("objTypeDomain") + objTypeDomain);
         System.out.println(LoggerUtil.prettyPrintField("objTypeObjKind") + objTypeObjKind);
         System.out.println(LoggerUtil.prettyPrintField("objTypeCat") + objTypeCat);
         System.out.println(LoggerUtil.prettyPrintField("objTypeSubcat") + objTypeSubcat);
         
         for (int i=0; i < numLinearSegments; i++) {
            
            byte segmentNumber = 0;
            byte segmentMod = 0;
            short generalSegAppear = 0;
            int specificSegmentAppearance = 0;
            double segmentLocX = 0.0;
            double segmentLocY = 0.0;
            double segmentLocZ = 0.0;
            float segmentOrientPsi = 0.0f;
            float segmentOrientTheta = 0.0f;
            float segmentOrientPhi = 0.0f;
            float segmentLength = 0.0f;
            float segmentWidth = 0.0f;
            float segmentHeight = 0.0f;
            float segmentDepth = 0.0f;
            int padding;
            
            segmentNumber = din.readByte();
            segmentMod = din.readByte();
            generalSegAppear = din.readShort();
            specificSegmentAppearance = din.readInt();
            segmentLocX = din.readDouble();
            segmentLocY = din.readDouble();
            segmentLocZ = din.readDouble();
            segmentOrientPsi = din.readFloat();
            segmentOrientTheta = din.readFloat();
            segmentOrientPhi = din.readFloat();
            segmentLength = din.readFloat();
            segmentWidth = din.readFloat();
            segmentHeight = din.readFloat();
            segmentDepth = din.readFloat();
            padding = din.readInt();
            
            System.out.println(LoggerUtil.prettyPrintField("segmentNumber") + segmentNumber);
            System.out.println(LoggerUtil.prettyPrintField("segmentMod") + segmentMod);
            System.out.println(LoggerUtil.prettyPrintField("generalSegAppear") + segmentMod);
            System.out.println(LoggerUtil.prettyPrintField("specificSegmentAppearance") + specificSegmentAppearance);
            System.out.println(LoggerUtil.prettyPrintField("segmentLocX") + segmentLocX);
            System.out.println(LoggerUtil.prettyPrintField("segmentLocY") + segmentLocY);
            System.out.println(LoggerUtil.prettyPrintField("segmentLocZ") + segmentLocZ);
            System.out.println(LoggerUtil.prettyPrintField("segmentOrientPsi") + segmentOrientPsi);
            System.out.println(LoggerUtil.prettyPrintField("segmentOrientTheta") + segmentOrientTheta);
            System.out.println(LoggerUtil.prettyPrintField("segmentOrientPhi") + segmentOrientPhi);
            System.out.println(LoggerUtil.prettyPrintField("segmentLength") + segmentLength);
            System.out.println(LoggerUtil.prettyPrintField("segmentWidth") + segmentWidth);
            System.out.println(LoggerUtil.prettyPrintField("segmentHeight") + segmentHeight);
            System.out.println(LoggerUtil.prettyPrintField("segmentDepth") + segmentDepth);
            
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
