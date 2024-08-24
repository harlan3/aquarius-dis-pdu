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

public class Transmitter {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(30);

      byte pduType = 0;
      byte family = 0;
      short length = 0;
      short radioEntitySite = 0;
      short radioEntityApp = 0;
      short radioEntityID = 0;
      short radioID = 0;
      byte radioEntityKind = 0;
      byte radioEntityDomain = 0;
      short radioEntityCountry = 0;
      byte radioEntityCategory = 0;
      byte radioEntityNomenVer = 0;
      short radioEntityNomen = 0;
      byte transmitState = 0;
      byte inputSource = 0;
      short padding1 = 0;
      double antennaLocationX = 0.0;
      double antennaLocationY = 0.0;
      double antennaLocationZ = 0.0;
      float relAntennaLocationX = 0.0f;
      float relAntennaLocationY = 0.0f;
      float relAntennaLocationZ = 0.0f;
      short antennaPatternType = 0;
      short antennaPatternLength = 0;
      long frequency = 0;
      float transmitFreqBandwidth = 0.0f;
      float power = 0.0f;
      short modTypeSpreadSpectrum = 0;
      short modTypeMajor = 0;
      short modTypeDetail = 0;
      short modTypeSystem = 0;
      short cryptoSystem = 0;
      short cryptoKeyID = 0;
      byte lengthOfModParams = 0;
      short padding2;
      byte padding3;

      try {

         /* Start Message Header */
         din.skipBytes(2);
         pduType = din.readByte();
         family = din.readByte();
         din.reset();

         din.skipBytes(8);
         length = din.readShort();
         din.reset();
         /* End Message Header */

         din.skipBytes(12);
         radioEntitySite = din.readShort();
         radioEntityApp = din.readShort();
         radioEntityID = din.readShort();
         radioID = din.readShort();
         radioEntityKind = din.readByte();
         radioEntityDomain = din.readByte();
         radioEntityCountry = din.readShort();
         radioEntityCategory = din.readByte();
         radioEntityNomenVer = din.readByte();
         radioEntityNomen = din.readShort();
         transmitState = din.readByte();
         inputSource = din.readByte();
         padding1 = din.readShort();
         antennaLocationX = din.readDouble();
         antennaLocationY = din.readDouble();
         antennaLocationZ = din.readDouble();
         relAntennaLocationX = din.readFloat();
         relAntennaLocationY = din.readFloat();
         relAntennaLocationZ = din.readFloat();
         antennaPatternType = din.readShort();
         antennaPatternLength = din.readShort();
         frequency = din.readLong();
         transmitFreqBandwidth = din.readFloat();
         power = din.readFloat();
         modTypeSpreadSpectrum = din.readShort();
         modTypeMajor = din.readShort();
         modTypeDetail = din.readShort();
         modTypeSystem = din.readShort();
         cryptoSystem = din.readShort();
         cryptoKeyID = din.readShort();
         lengthOfModParams = din.readByte();
         padding2 = din.readShort();
         padding3 = din.readByte();

         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("radioEntitySite") + radioEntitySite);
         System.out.println(LoggerUtil.prettyPrintField("radioEntityApp") + radioEntityApp);
         System.out.println(LoggerUtil.prettyPrintField("radioEntityID") + radioEntityID);
         System.out.println(LoggerUtil.prettyPrintField("radioID") + radioID);
         System.out.println(LoggerUtil.prettyPrintField("radioEntityKind") + radioEntityKind);
         System.out.println(LoggerUtil.prettyPrintField("radioEntityDomain") + radioEntityDomain);
         System.out.println(LoggerUtil.prettyPrintField("radioEntityCountry") + radioEntityCountry);
         System.out.println(LoggerUtil.prettyPrintField("radioEntityCategory") + radioEntityCategory);
         System.out.println(LoggerUtil.prettyPrintField("radioEntityNomenVer") + radioEntityNomenVer);
         System.out.println(LoggerUtil.prettyPrintField("radioEntityNomen") + radioEntityNomen);
         System.out.println(LoggerUtil.prettyPrintField("transmitState") + transmitState);
         System.out.println(LoggerUtil.prettyPrintField("inputSource") + inputSource);
         System.out.println(LoggerUtil.prettyPrintField("antennaLocationX") + antennaLocationX);
         System.out.println(LoggerUtil.prettyPrintField("antennaLocationY") + antennaLocationY);
         System.out.println(LoggerUtil.prettyPrintField("antennaLocationZ") + antennaLocationZ);
         System.out.println(LoggerUtil.prettyPrintField("relAntennaLocationX") + relAntennaLocationX);
         System.out.println(LoggerUtil.prettyPrintField("relAntennaLocationY") + relAntennaLocationY);
         System.out.println(LoggerUtil.prettyPrintField("relAntennaLocationZ") + relAntennaLocationZ);
         System.out.println(LoggerUtil.prettyPrintField("antennaPatternType") + antennaPatternType);
         System.out.println(LoggerUtil.prettyPrintField("antennaPatternLength") + antennaPatternLength);
         System.out.println(LoggerUtil.prettyPrintField("frequency") + frequency);
         System.out.println(LoggerUtil.prettyPrintField("transmitFreqBandwidth") + transmitFreqBandwidth);
         System.out.println(LoggerUtil.prettyPrintField("power") + power);
         System.out.println(LoggerUtil.prettyPrintField("modTypeSpreadSpectrum") + LoggerUtil.toBitString(modTypeSpreadSpectrum));
         System.out.println(LoggerUtil.prettyPrintField("modTypeMajor") + modTypeMajor);
         System.out.println(LoggerUtil.prettyPrintField("modTypeDetail") + modTypeDetail);
         System.out.println(LoggerUtil.prettyPrintField("modTypeSystem") + modTypeSystem);
         System.out.println(LoggerUtil.prettyPrintField("cryptoSystem") + cryptoSystem);
         System.out.println(LoggerUtil.prettyPrintField("cryptoKeyID") + cryptoKeyID);
         System.out.println(LoggerUtil.prettyPrintField("lengthOfModParams") + lengthOfModParams);

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
