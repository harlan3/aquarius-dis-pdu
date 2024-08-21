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

public class StartResume {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(20);

      byte pduType = 0;
      short length = 0;
      short origEntIDSite = 0;
      short origEntIDApp = 0;
      short origEntIDEntity = 0;
      short recEntIDSite = 0;
      short recEntIDApp = 0;
      short recEntIDEntity = 0;
      int rwtHour = 0;
      int rwtTimePastHour = 0;
      int simTimeHour = 0;
      int simTimeTimePastHour = 0;
      int requestID = 0;

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
         origEntIDSite = din.readShort();
         origEntIDApp = din.readShort();
         origEntIDEntity = din.readShort();
         recEntIDSite = din.readShort();
         recEntIDApp = din.readShort();
         recEntIDEntity = din.readShort();
         rwtHour = din.readInt();
         rwtTimePastHour = din.readInt();
         simTimeHour = din.readInt();
         simTimeTimePastHour = din.readInt();
         requestID = din.readInt();

         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("origEntIDSite") + origEntIDSite);
         System.out.println(LoggerUtil.prettyPrintField("origEntIDApp") + origEntIDApp);
         System.out.println(LoggerUtil.prettyPrintField("origEntIDEntity") + origEntIDEntity);
         System.out.println(LoggerUtil.prettyPrintField("recEntIDSite") + recEntIDSite);
         System.out.println(LoggerUtil.prettyPrintField("recEntIDApp") + recEntIDApp);
         System.out.println(LoggerUtil.prettyPrintField("recEntIDEntity") + recEntIDEntity);
         System.out.println(LoggerUtil.prettyPrintField("rwtHour") + rwtHour);
         System.out.println(LoggerUtil.prettyPrintField("rwtTimePastHour") + rwtTimePastHour);
         System.out.println(LoggerUtil.prettyPrintField("simTimeHour") + simTimeHour);
         System.out.println(LoggerUtil.prettyPrintField("simTimeTimePastHour") + simTimeTimePastHour);
         System.out.println(LoggerUtil.prettyPrintField("requestID") + requestID);

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
