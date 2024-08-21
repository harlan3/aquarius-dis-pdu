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

public class MinefieldQuery {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(45);

      byte pduType = 0;
      byte family = 0;
      short length = 0;
      byte pduStatus = 0;
      short minefieldIDSiteNum = 0;
      short minefieldIDAppNum = 0;
      short minefieldIDMinefieldNum = 0;
      short requestSimIDSiteNum = 0;
      short requestSimIDAppNum = 0;
      short requestSimIDRefNum = 0;
      byte requestID = 0;
      byte numPerimeterPoints = 0;
      byte padding = 0;
      byte numberSensorTypes = 0;
      int dataFilter = 0;
      byte reqMineEntKind = 0;
      byte reqMineDomain = 0;
      short reqMineCountry = 0;
      byte reqMineCategory = 0;
      byte reqMineSubCat = 0;
      byte reqMineSpecific = 0;
      byte reqMineExtra = 0;
      
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
         
         minefieldIDSiteNum = din.readShort();
         minefieldIDAppNum = din.readShort();
         minefieldIDMinefieldNum = din.readShort();
         requestSimIDSiteNum = din.readShort();
         requestSimIDAppNum = din.readShort();
         requestSimIDRefNum = din.readShort();
         requestID = din.readByte();
         numPerimeterPoints = din.readByte();
         padding = din.readByte();
         numberSensorTypes = din.readByte();
         dataFilter = din.readInt();
         reqMineEntKind = din.readByte();
         reqMineDomain = din.readByte();
         reqMineCountry = din.readShort();
         reqMineCategory = din.readByte();
         reqMineSubCat = din.readByte();
         reqMineSpecific = din.readByte();
         reqMineExtra = din.readByte();
         
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("minefieldIDSiteNum") + minefieldIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("minefieldIDAppNum") + minefieldIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("minefieldIDMinefieldNum") + minefieldIDMinefieldNum);
         System.out.println(LoggerUtil.prettyPrintField("requestSimIDSiteNum") + requestSimIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("requestSimIDAppNum") + requestSimIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("requestSimIDRefNum") + requestSimIDRefNum);
         System.out.println(LoggerUtil.prettyPrintField("requestID") + requestID);
         System.out.println(LoggerUtil.prettyPrintField("numPerimeterPoints") + numPerimeterPoints);
         System.out.println(LoggerUtil.prettyPrintField("numberSensorTypes") + numberSensorTypes);
         System.out.println(LoggerUtil.prettyPrintField("dataFilter") + dataFilter);
         System.out.println(LoggerUtil.prettyPrintField("reqMineEntKind") + reqMineEntKind);
         System.out.println(LoggerUtil.prettyPrintField("reqMineDomain") + reqMineDomain);
         System.out.println(LoggerUtil.prettyPrintField("reqMineCountry") + reqMineCountry);
         System.out.println(LoggerUtil.prettyPrintField("reqMineCategory") + reqMineCategory);
         System.out.println(LoggerUtil.prettyPrintField("reqMineSubCat") + reqMineSubCat);
         System.out.println(LoggerUtil.prettyPrintField("reqMineSpecific") + reqMineSpecific);
         System.out.println(LoggerUtil.prettyPrintField("reqMineExtra") + reqMineExtra);
         
         for (int i=0; i<numPerimeterPoints; i++) {
            
            float xComponent = 0;
            float yComponent = 0;
            
            xComponent = din.readFloat();
            yComponent = din.readFloat();
            
            System.out.println(LoggerUtil.prettyPrintField("xComponent") + xComponent);
            System.out.println(LoggerUtil.prettyPrintField("yComponent") + yComponent);
         }
         
         for (int j=0; j<numberSensorTypes; j++) {
            
            short sensorTypeCat = 0;
            
            sensorTypeCat = din.readShort();
            
            System.out.println(LoggerUtil.prettyPrintField("sensorTypeCat") + LoggerUtil.toBitString(sensorTypeCat));
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
