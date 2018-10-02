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

package orbisoftware.aquarius.pdu_logger.pdu_handler;

import orbisoftware.aquarius.pdu_logger.PDU_Type;
import orbisoftware.aquarius.pdu_logger.HexDump;
import orbisoftware.aquarius.pdu_logger.LoggerUtil;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Date;

import java.net.*;

public class IffAtcNavaids {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(
            packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();

      byte pduType = 0;
      short length = 0;
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
      @SuppressWarnings("unused")
      short padding = 0;
      byte fodSystemStatus = 0;
      byte fodAltParm4 = 0;
      byte fodInfoLayers = 0;
      byte fodModifier = 0;
      short fodParm1 = 0;
      short fodParm2 = 0;
      short fodParm3 = 0;
      short fodParm4 = 0;
      short fodParm5 = 0;
      short fodParm6 = 0;

      try {

         /* Start Message Header */
         din.skipBytes(2);
         pduType = din.readByte();
         din.reset();

         din.skipBytes(8);
         length = din.readShort();
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
         padding = din.readShort();
         fodSystemStatus = din.readByte();
         fodAltParm4 = din.readByte();
         fodInfoLayers = din.readByte();
         fodModifier = din.readByte();
         fodParm1 = din.readShort();
         fodParm2 = din.readShort();
         fodParm3 = din.readShort();
         fodParm4 = din.readShort();
         fodParm5 = din.readShort();
         fodParm6 = din.readShort();

         System.out.println("        pduType : " + PDU_Type.values()[pduType]);
         System.out.println("         length : " + length);
         System.out.println("  emitEntIDSite : " + emitEntIDSite);
         System.out.println("   emitEntIDApp : " + emitEntIDApp);
         System.out.println("emitEntIDEntity : " + emitEntIDEntity);
         System.out.println("    eventIDSite : " + eventIDSite);
         System.out.println("     eventIDApp : " + eventIDApp);
         System.out.println("eventIDEventNum : " + eventIDEventNum);
         System.out.println("      locationX : " + locationX);
         System.out.println("      locationY : " + locationY);
         System.out.println("      locationZ : " + locationZ);
         System.out.println("   sysIDSysType : " + sysIDSysType);
         System.out.println("   sysIDSysName : " + sysIDSysName);
         System.out.println("   sysIDSysMode : " + sysIDSysMode);
         System.out.println(" sysIDChangeOpt : " + sysIDChangeOpt);
         System.out.println("fodSystemStatus : " + fodSystemStatus);
         System.out.println("    fodAltParm4 : " + fodAltParm4);
         System.out.println("  fodInfoLayers : "
               + LoggerUtil.toBitString(fodInfoLayers));
         System.out.println("    fodModifier : " + fodModifier);
         System.out.println("       fodParm1 : " + fodParm1);
         System.out.println("       fodParm2 : " + fodParm2);
         System.out.println("       fodParm3 : " + fodParm3);
         System.out.println("       fodParm4 : " + fodParm4);
         System.out.println("       fodParm5 : " + fodParm5);
         System.out.println("       fodParm6 : " + fodParm6);

         /* Verify that the length defined in PDU matches what was received */
         if (length != packet.getLength()) {
            System.out.println("\nWARNING: Reported PDU length is incorrect!");
            System.out.println("         Read " + packet.getLength() + "     "
                  + "Reported: " + length);
         }

         /* The following code is required for pdu logger */
         System.out.println();
         System.out.println("      PDU counter: " + pduCounter);
         System.out.println("Local packet time: " + date.getTime());
         System.out.println(HexDump.dump(packet.getData(), 0, 0,
               packet.getLength()));

      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
