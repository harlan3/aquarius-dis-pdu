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

public class IsPartOf {

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
      short origSimIDSiteNum = 0;
      short origSimIDAppNum = 0;
      short origSimIDRefNum = 0;
      short recEntityIDSiteNum = 0;
      short recEntityIDAppNum = 0;
      short recEntityIDEntNum = 0;
      short relationShipNature = 0;
      short relationShipPosition = 0;
      float partLocationCompA = 0.0f;
      float partLocationCompB = 0.0f;
      float partLocationCompC = 0.0f;
      short namedLocationIDStatName = 0;
      short namedLocationIDStatNum = 0;
      byte partTypeEntKind = 0;
      byte partTypeDomain = 0;
      short partTypeCountry = 0;
      byte partTypeCategory = 0;
      byte partTypeSubCategory = 0;
      byte partTypeSpecific = 0;
      byte partTypeExtra = 0;
   
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
         
         origSimIDSiteNum = din.readShort();
         origSimIDAppNum = din.readShort();
         origSimIDRefNum = din.readShort();
         recEntityIDSiteNum = din.readShort();
         recEntityIDAppNum = din.readShort();
         recEntityIDEntNum = din.readShort();
         relationShipNature = din.readShort();
         relationShipPosition = din.readShort();
         partLocationCompA = din.readFloat();
         partLocationCompB = din.readFloat();
         partLocationCompC = din.readFloat();
         namedLocationIDStatName = din.readShort();
         namedLocationIDStatNum = din.readShort();
         partTypeEntKind = din.readByte();
         partTypeDomain = din.readByte();
         partTypeCountry = din.readShort();
         partTypeCategory = din.readByte();
         partTypeSubCategory = din.readByte();
         partTypeSpecific = din.readByte();
         partTypeExtra = din.readByte();
         
         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("origSimIDSiteNum") + origSimIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("origSimIDAppNum") + origSimIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("origSimIDRefNum") + origSimIDRefNum);
         System.out.println(LoggerUtil.prettyPrintField("recEntityIDSiteNum") + recEntityIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("recEntityIDAppNum") + recEntityIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("recEntityIDEntNum") + recEntityIDEntNum);
         System.out.println(LoggerUtil.prettyPrintField("relationShipNature") + relationShipNature);
         System.out.println(LoggerUtil.prettyPrintField("relationShipPosition") + relationShipPosition);
         System.out.println(LoggerUtil.prettyPrintField("partLocationCompA") + partLocationCompA);
         System.out.println(LoggerUtil.prettyPrintField("partLocationCompB") + partLocationCompB);
         System.out.println(LoggerUtil.prettyPrintField("partLocationCompC") + partLocationCompC);
         System.out.println(LoggerUtil.prettyPrintField("namedLocationIDStatName") + namedLocationIDStatName);
         System.out.println(LoggerUtil.prettyPrintField("namedLocationIDStatNum") + namedLocationIDStatNum);
         System.out.println(LoggerUtil.prettyPrintField("partTypeEntKind") + partTypeEntKind);
         System.out.println(LoggerUtil.prettyPrintField("partTypeDomain") + partTypeDomain);
         System.out.println(LoggerUtil.prettyPrintField("partTypeCountry") + partTypeCountry);
         System.out.println(LoggerUtil.prettyPrintField("partTypeCategory") + partTypeCategory);
         System.out.println(LoggerUtil.prettyPrintField("partTypeSubCategory") + partTypeSubCategory);
         System.out.println(LoggerUtil.prettyPrintField("partTypeSpecific") + partTypeSpecific);
         System.out.println(LoggerUtil.prettyPrintField("partTypeExtra") + partTypeExtra);
         
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
