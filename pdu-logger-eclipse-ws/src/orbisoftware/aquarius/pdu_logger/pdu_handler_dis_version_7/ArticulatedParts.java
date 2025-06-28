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

public class ArticulatedParts {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(40);
    
      byte protocolVersion = 0;
      byte exercise = 0;
      byte pduType = 0;
      byte family = 0;
      short length = 0;
      byte subprotocolNumber = 0;
      byte liveEntityIDSiteNum = 0;
      byte liveEntityIDAppNum = 0;
      short liveEntityIDEntityNumber = 0;
      byte numberVariableParamRecs = 0;
      
      try {

         /* Start Message Header */
         protocolVersion = din.readByte();
         exercise = din.readByte();
         pduType = din.readByte();
         family = din.readByte();
         din.reset();

         din.skipBytes(8);
         length = din.readShort();
         subprotocolNumber = din.readByte();
         din.reset();
         /* End Message Header */

         din.skipBytes(12);
         liveEntityIDSiteNum = din.readByte();
         liveEntityIDAppNum = din.readByte();
         liveEntityIDEntityNumber = din.readShort();
         numberVariableParamRecs = din.readByte();

         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("subprotocolNumber") + subprotocolNumber);
         System.out.println(LoggerUtil.prettyPrintField("liveEntityIDSiteNum") + liveEntityIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("liveEntityIDAppNum") + liveEntityIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("liveEntityIDEntityNumber") + liveEntityIDEntityNumber);
         System.out.println(LoggerUtil.prettyPrintField("numberVariableParamRecs") + numberVariableParamRecs);
         
         
         for (int i = 0; i < numberVariableParamRecs; i++) {

            byte variableParamRec1RecType = din.readByte();
            byte variableParamRec1RecSpecFields[] = din.readNBytes(15);
            
            System.out.println(LoggerUtil.prettyPrintField("variableParamRec1RecType") + variableParamRec1RecType);
            System.out.print(LoggerUtil.prettyPrintField("variableParamRec1RecSpecFields"));
            
            for (int j = 0; j < 15; j++) {
               System.out.print(variableParamRec1RecSpecFields[j] + " ");
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
