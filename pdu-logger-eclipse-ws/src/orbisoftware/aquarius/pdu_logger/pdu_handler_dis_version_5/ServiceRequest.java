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

package orbisoftware.aquarius.pdu_logger.pdu_handler_dis_version_5;

import orbisoftware.aquarius.pdu_logger.*;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Date;

import java.net.*;

public class ServiceRequest {

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
      short requestEntIDSite = 0;
      short requestEntIDApp = 0;
      short requestEntIDEntity = 0;
      short serviceEntIDSite = 0;
      short serviceEntIDApp = 0;
      short serviceEntIDEntity = 0;
      byte serviceTypeReq = 0;
      byte numberSupplyTypes = 0;
      short padding = 0;
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
         protocolVersion = din.readByte();
         exercise = din.readByte();
         pduType = din.readByte();
         family = din.readByte();
         din.reset();

         din.skipBytes(8);
         length = din.readShort();
         din.reset();
         /* End Message Header */

         din.skipBytes(12);
         requestEntIDSite = din.readShort();
         requestEntIDApp = din.readShort();
         requestEntIDEntity = din.readShort();
         serviceEntIDSite = din.readShort();
         serviceEntIDApp = din.readShort();
         serviceEntIDEntity = din.readShort();
         serviceTypeReq = din.readByte();
         numberSupplyTypes = din.readByte();
         padding = din.readShort();

         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("requestEntIDSite") + requestEntIDSite);
         System.out.println(LoggerUtil.prettyPrintField("requestEntIDApp") + requestEntIDApp);
         System.out.println(LoggerUtil.prettyPrintField("requestEntIDEntity") + requestEntIDEntity);
         System.out.println(LoggerUtil.prettyPrintField("serviceEntIDSite") + serviceEntIDSite);
         System.out.println(LoggerUtil.prettyPrintField("serviceEntIDApp") + serviceEntIDApp);
         System.out.println(LoggerUtil.prettyPrintField("serviceEntIDEntity") + serviceEntIDEntity);
         System.out.println(LoggerUtil.prettyPrintField("serviceTypeReq") + serviceTypeReq);
         System.out.println(LoggerUtil.prettyPrintField("numberSupplyTypes") + numberSupplyTypes);

         for (int y = 0; y < numberSupplyTypes; y++) {

            supplyEntityKind = din.readByte();
            supplyDomain = din.readByte();
            supplyCountry = din.readShort();
            supplyCategory = din.readByte();
            supplySubcategory = din.readByte();
            supplySpecific = din.readByte();
            supplyExtra = din.readByte();
            supplyQuantity = din.readFloat();

            System.out.println(LoggerUtil.prettyPrintField("(Supply Record)") + y);
            System.out.println(LoggerUtil.prettyPrintField("supplyEntityKind") + supplyEntityKind);
            System.out.println(LoggerUtil.prettyPrintField("supplyDomain") + supplyDomain);
            System.out.println(LoggerUtil.prettyPrintField("supplyCountry") + supplyCountry);
            System.out.println(LoggerUtil.prettyPrintField("supplyCategory") + supplyCategory);
            System.out.println(LoggerUtil.prettyPrintField("supplySubcategory") + supplySubcategory);
            System.out.println(LoggerUtil.prettyPrintField("supplySpecific") + supplySpecific);
            System.out.println(LoggerUtil.prettyPrintField("supplyExtra") + supplyExtra);
            System.out.println(LoggerUtil.prettyPrintField("supplyQuantity") + supplyQuantity);
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
