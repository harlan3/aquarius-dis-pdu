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

public class Attribute {

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
      byte status = 0;
      short origSimAddrSiteNum = 0;
      short origSimAddrAppNum = 0;
      int padding1 = 0;
      int padding2 = 0;
      byte attribRecPDUtype = 0;
      byte attribRecProtoVer = 0;
      int masterAttribRecType = 0;
      byte actionCode = 0;
      byte padding3 = 0;
      short numAttribRecSets = 0;
      
      try {

         /* Start Message Header */
         protocolVersion = din.readByte();
         exercise = din.readByte();
         pduType = din.readByte();
         family = din.readByte();
         din.reset();

         din.skipBytes(8);
         length = din.readShort();
         status = din.readByte();
         din.reset();
         /* End Message Header */

         din.skipBytes(12);
         origSimAddrSiteNum = din.readShort();
         origSimAddrAppNum = din.readShort();
         padding1 = din.readInt();
         padding2 = din.readShort();
         attribRecPDUtype = din.readByte();
         attribRecProtoVer = din.readByte();
         masterAttribRecType = din.readInt();
         actionCode = din.readByte();
         padding3 = din.readByte();
         numAttribRecSets = din.readShort();
         
         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("status") + status);
         System.out.println(LoggerUtil.prettyPrintField("origSimAddrSiteNum") + origSimAddrSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("origSimAddrAppNum") + origSimAddrAppNum);
         System.out.println(LoggerUtil.prettyPrintField("attribRecPDUtype") + attribRecPDUtype);
         System.out.println(LoggerUtil.prettyPrintField("attribRecProtoVer") + attribRecProtoVer);
         System.out.println(LoggerUtil.prettyPrintField("masterAttribRecType") + masterAttribRecType);
         System.out.println(LoggerUtil.prettyPrintField("actionCode") + actionCode);
         System.out.println(LoggerUtil.prettyPrintField("numAttribRecSets") + numAttribRecSets);
         
         for (int i = 0; i < numAttribRecSets; i++) {

            short entityObjIDsiteNum = 0;
            short entityObjIDappNum = 0;
            short entityObjIDentObjNum = 0;
            short numAttribRec = 0;
            
            entityObjIDsiteNum = din.readShort();
            entityObjIDappNum = din.readShort();
            entityObjIDentObjNum = din.readShort();
            numAttribRec = din.readShort();
                        
            System.out.println(LoggerUtil.prettyPrintField("entityObjIDsiteNum") + entityObjIDsiteNum);
            System.out.println(LoggerUtil.prettyPrintField("entityObjIDappNum") + entityObjIDappNum);
            System.out.println(LoggerUtil.prettyPrintField("entityObjIDentObjNum") + entityObjIDentObjNum);
            System.out.println(LoggerUtil.prettyPrintField("numAttribRec") + numAttribRec);
            
            for (int j = 0; j < numAttribRec; j++) {
               
               int attribRecRecType = 0;
               short attribRecRecLen = 0;
               
               attribRecRecType = din.readInt();
               attribRecRecLen = din.readShort();
               
               System.out.println(LoggerUtil.prettyPrintField("attribRecRecType") + attribRecRecType);
               System.out.println(LoggerUtil.prettyPrintField("attribRecRecLen") + attribRecRecLen);
               
               for (int k=0; k < attribRecRecLen; k++) {
                  
                  byte recSpecFields = din.readByte();
                  
                  System.out.println(LoggerUtil.prettyPrintField("recSpecFields") + LoggerUtil.unsignedByteToInt(recSpecFields));
               }
            }

            System.out.println();
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
