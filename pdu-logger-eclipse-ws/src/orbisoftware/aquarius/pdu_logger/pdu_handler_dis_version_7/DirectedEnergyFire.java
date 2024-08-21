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

public class DirectedEnergyFire {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(35);
      
      byte pduType = 0;
      byte family = 0;
      short length = 0;
      byte pduStatus = 0;
      short firingEntIDSiteNum = 0;
      short firingEntIDAppNum = 0;
      short firingEntIDEntNum = 0;
      short eventIDSiteNum = 0;
      short eventIDAppNum = 0;
      short eventIDEventNum = 0;
      long munitionType = 0;
      int shotStartTimeHour = 0;
      int shotStartTimeTimePastHour = 0;
      float cumulativeShotTime = 0.0f;
      float apertureEmitterLocECXcomp = 0.0f;
      float apertureEmitterLocECYcomp = 0.0f;
      float apertureEmitterLocECZcomp = 0.0f;
      float apertureDiameter = 0.0f;
      float waveLength = 0.0f;
      int padding1 = 0;
      float pulseRepFreq = 0.0f;
      float pulseWidth = 0.0f;
      short flags = 0;
      byte pulseShape = 0;
      byte padding2;
      int padding3;
      short padding4;
      short numberDERecords = 0;
      
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
         firingEntIDSiteNum = din.readShort();
         firingEntIDAppNum = din.readShort();
         firingEntIDEntNum = din.readShort();
         eventIDSiteNum = din.readShort();
         eventIDAppNum = din.readShort();
         eventIDEventNum = din.readShort();
         munitionType = din.readLong();
         shotStartTimeHour = din.readInt();
         shotStartTimeTimePastHour = din.readInt();
         cumulativeShotTime = din.readFloat();
         apertureEmitterLocECXcomp = din.readFloat();
         apertureEmitterLocECYcomp = din.readFloat();
         apertureEmitterLocECZcomp = din.readFloat();
         apertureDiameter = din.readFloat();
         waveLength = din.readFloat();
         padding1 = din.readInt();
         pulseRepFreq = din.readFloat();
         pulseWidth = din.readFloat();
         flags = din.readShort();
         pulseShape = din.readByte();
         padding2 = din.readByte();
         padding3 = din.readInt();
         padding4 = din.readShort();
         numberDERecords = din.readShort();
         
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("firingEntIDSiteNum") + firingEntIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("firingEntIDAppNum") + firingEntIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("firingEntIDEntNum") + firingEntIDEntNum);
         System.out.println(LoggerUtil.prettyPrintField("eventIDSiteNum") + eventIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("eventIDAppNum") + eventIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("eventIDEventNum") + eventIDEventNum);
         System.out.println(LoggerUtil.prettyPrintField("munitionType") + munitionType);
         System.out.println(LoggerUtil.prettyPrintField("shotStartTimeHour") + shotStartTimeHour);
         System.out.println(LoggerUtil.prettyPrintField("shotStartTimeTimePastHour") + shotStartTimeTimePastHour);
         System.out.println(LoggerUtil.prettyPrintField("cumulativeShotTime") + cumulativeShotTime);
         System.out.println(LoggerUtil.prettyPrintField("apertureEmitterLocECXcomp") + apertureEmitterLocECXcomp);
         System.out.println(LoggerUtil.prettyPrintField("apertureEmitterLocECYcomp") + apertureEmitterLocECYcomp);
         System.out.println(LoggerUtil.prettyPrintField("apertureEmitterLocECZcomp") + apertureEmitterLocECZcomp);
         System.out.println(LoggerUtil.prettyPrintField("apertureDiameter") + apertureDiameter);
         System.out.println(LoggerUtil.prettyPrintField("waveLength") + waveLength);
         System.out.println(LoggerUtil.prettyPrintField("pulseRepFreq") + pulseRepFreq);
         System.out.println(LoggerUtil.prettyPrintField("pulseWidth") + pulseWidth);
         System.out.println(LoggerUtil.prettyPrintField("flags") + flags);
         System.out.println(LoggerUtil.prettyPrintField("pulseShape") + pulseShape);
         System.out.println(LoggerUtil.prettyPrintField("numberDERecords") + numberDERecords);
         
         for (int i = 0; i < numberDERecords; i++) {

            int recordType = 0;
            short recordLength = 0;
            byte recordSpecFields[] = new byte[1];
            
            recordType = din.readInt();
            recordLength = din.readShort();
            recordSpecFields = din.readNBytes(recordLength);

            System.out.println(LoggerUtil.prettyPrintField("recordType") + recordType);
            System.out.println(LoggerUtil.prettyPrintField("recordLength") + recordLength);
            System.out.println(LoggerUtil.prettyPrintField("recordSpecFields") + recordSpecFields[i]);
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
