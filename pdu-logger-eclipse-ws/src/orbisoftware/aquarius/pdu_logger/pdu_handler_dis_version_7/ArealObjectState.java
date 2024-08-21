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

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Date;

import orbisoftware.aquarius.pdu_logger.*;

public class ArealObjectState {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
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
      short refObjIDSiteNum = 0;
      short refObjIDAppNum = 0;
      short refObjIDObjNum = 0;
      short updateNum = 0;
      byte forceID = 0;
      byte modifications = 0;
      byte objTypeDomain = 0;
      byte objTypeObjKind = 0;
      byte objTypeCat = 0;
      byte objTypeSubCat = 0;
      int specObjAppear = 0;
      short genObjAppear = 0;
      short numOfPoints = 0;
      short requestSimIDSiteNum = 0;
      short requestSimIDAppNum = 0;
      short receiveSimIDSiteNum = 0;
      short receiveSimIDAppNum = 0;
      
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
         refObjIDSiteNum = din.readShort();
         refObjIDAppNum = din.readShort();
         refObjIDObjNum = din.readShort();
         updateNum = din.readShort();
         forceID = din.readByte();
         modifications = din.readByte();
         objTypeDomain = din.readByte();
         objTypeObjKind = din.readByte();
         objTypeCat = din.readByte();
         objTypeSubCat = din.readByte();
         specObjAppear = din.readInt();
         genObjAppear = din.readShort();
         numOfPoints = din.readShort();
         requestSimIDSiteNum = din.readShort();
         requestSimIDAppNum = din.readShort();
         receiveSimIDSiteNum = din.readShort();
         receiveSimIDAppNum = din.readShort();
         
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("status") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("objectIDSiteNum") + objectIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("objectIDAppNum") + objectIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("objectIDObjNum") + objectIDObjNum);
         System.out.println(LoggerUtil.prettyPrintField("refObjIDSiteNum") + refObjIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("refObjIDAppNum") + refObjIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("refObjIDObjNum") + refObjIDObjNum);
         System.out.println(LoggerUtil.prettyPrintField("updateNum") + updateNum);
         System.out.println(LoggerUtil.prettyPrintField("forceID") + forceID);
         System.out.println(LoggerUtil.prettyPrintField("modifications") + modifications);
         System.out.println(LoggerUtil.prettyPrintField("objTypeDomain") + objTypeDomain);
         System.out.println(LoggerUtil.prettyPrintField("objTypeObjKind") + objTypeObjKind);
         System.out.println(LoggerUtil.prettyPrintField("objTypeCat") + objTypeCat);
         System.out.println(LoggerUtil.prettyPrintField("objTypeSubCat") + objTypeSubCat);
         System.out.println(LoggerUtil.prettyPrintField("specObjAppear") + specObjAppear);
         System.out.println(LoggerUtil.prettyPrintField("genObjAppear") + genObjAppear);
         System.out.println(LoggerUtil.prettyPrintField("numOfPoints") + numOfPoints);
         System.out.println(LoggerUtil.prettyPrintField("requestSimIDSiteNum") + requestSimIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("requestSimIDAppNum") + requestSimIDAppNum);  
         System.out.println(LoggerUtil.prettyPrintField("receiveSimIDSiteNum") + receiveSimIDSiteNum);  
         System.out.println(LoggerUtil.prettyPrintField("receiveSimIDAppNum") + receiveSimIDAppNum);  
         
         for (int i = 0; i < numOfPoints; i++) {

            double pointNumXComponent = din.readDouble();
            double pointNumYComponent = din.readDouble();
            double pointNumZComponent = din.readDouble();

            System.out.println(LoggerUtil.prettyPrintField("pointNumXComponent") + pointNumXComponent);
            System.out.println(LoggerUtil.prettyPrintField("pointNumYComponent") + pointNumYComponent);
            System.out.println(LoggerUtil.prettyPrintField("pointNumZComponent") + pointNumZComponent);
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
