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

public class IffAtcNavaids {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(25);

      byte protocolVersion = 0;
      byte exercise = 0;
      byte pduType = 0;
      byte family = 0;
      short length = 0;
      byte pduStatus = 0;
      short emitEntIDSite = 0;
      short emitEntIDApp = 0;
      short emitEntIDEntity = 0;
      short eventIDSite = 0;
      short eventIDApp = 0;
      short eventIDEventNum = 0;
      float locationX = 0.0f;
      float locationY = 0.0f;
      float locationZ = 0.0f;
      short sysIDSysType = 0;
      short sysIDSysName = 0;
      byte sysIDSysMode = 0;
      byte sysIDChangeOpt = 0;
      byte systemDesignator = 0;
      byte systemSpecificData = 0;
      byte fodSystemStatus = 0;
      byte fodDataField1 = 0;
      byte fodInfoLayers = 0;
      byte fodDataField2 = 0;
      short fodParm1 = 0;
      short fodParm2 = 0;
      short fodParm3 = 0;
      short fodParm4 = 0;
      short fodParm5 = 0;
      short fodParm6 = 0;

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
         
         emitEntIDSite = din.readShort();
         emitEntIDApp = din.readShort();
         emitEntIDEntity = din.readShort();
         eventIDSite = din.readShort();
         eventIDApp = din.readShort();
         eventIDEventNum = din.readShort();
         locationX = din.readFloat();
         locationY = din.readFloat();
         locationZ = din.readFloat();
         sysIDSysType = din.readShort();
         sysIDSysName = din.readShort();
         sysIDSysMode = din.readByte();
         sysIDChangeOpt = din.readByte();
         systemDesignator = din.readByte();
         systemSpecificData = din.readByte();
         fodSystemStatus = din.readByte();
         fodDataField1 = din.readByte();
         fodInfoLayers = din.readByte();
         fodDataField2 = din.readByte();
         fodParm1 = din.readShort();
         fodParm2 = din.readShort();
         fodParm3 = din.readShort();
         fodParm4 = din.readShort();
         fodParm5 = din.readShort();
         fodParm6 = din.readShort();

         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("emitEntIDSite") + emitEntIDSite);
         System.out.println(LoggerUtil.prettyPrintField("emitEntIDApp") + emitEntIDApp);
         System.out.println(LoggerUtil.prettyPrintField("emitEntIDEntity") + emitEntIDEntity);
         System.out.println(LoggerUtil.prettyPrintField("eventIDSite") + eventIDSite);
         System.out.println(LoggerUtil.prettyPrintField("eventIDApp") + eventIDApp);
         System.out.println(LoggerUtil.prettyPrintField("eventIDEventNum") + eventIDEventNum);
         System.out.println(LoggerUtil.prettyPrintField("locationX") + locationX);
         System.out.println(LoggerUtil.prettyPrintField("locationY") + locationY);
         System.out.println(LoggerUtil.prettyPrintField("locationZ") + locationZ);
         System.out.println(LoggerUtil.prettyPrintField("sysIDSysType") + sysIDSysType);
         System.out.println(LoggerUtil.prettyPrintField("sysIDSysName") + sysIDSysName);
         System.out.println(LoggerUtil.prettyPrintField("sysIDSysMode") + sysIDSysMode);
         System.out.println(LoggerUtil.prettyPrintField("sysIDChangeOpt") + sysIDChangeOpt);
         System.out.println(LoggerUtil.prettyPrintField("systemDesignator") + systemDesignator);
         System.out.println(LoggerUtil.prettyPrintField("systemSpecificData") + systemSpecificData);
         System.out.println(LoggerUtil.prettyPrintField("fodSystemStatus") + fodSystemStatus);
         System.out.println(LoggerUtil.prettyPrintField("fodDataField1") + fodDataField1);
         System.out.println(LoggerUtil.prettyPrintField("fodInfoLayers") + LoggerUtil.toBitString(fodInfoLayers));
         System.out.println(LoggerUtil.prettyPrintField("fodDataField2") + fodDataField2);
         System.out.println(LoggerUtil.prettyPrintField("fodParm1") + fodParm1);
         System.out.println(LoggerUtil.prettyPrintField("fodParm2") + fodParm2);
         System.out.println(LoggerUtil.prettyPrintField("fodParm3") + fodParm3);
         System.out.println(LoggerUtil.prettyPrintField("fodParm4") + fodParm4);
         System.out.println(LoggerUtil.prettyPrintField("fodParm5") + fodParm5);
         System.out.println(LoggerUtil.prettyPrintField("fodParm6") + fodParm6);

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
