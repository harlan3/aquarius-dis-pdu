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

public class Transmitter {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(45);

      byte protocolVersion = 0;
      byte exercise = 0;
      byte pduType = 0;
      byte family = 0;
      short length = 0;
      byte pduStatus = 0;
      short radioRefIDSiteNum = 0;
      short radioRefIDAppNum = 0;
      short radioRefIDRefNum = 0;
      short radioNumber = 0;
      byte radioTypeEntityKind = 0;
      byte radioTypeEntityDomain = 0;
      short radioTypeEntityCountry = 0;
      byte radioTypeEntityCategory = 0;
      byte radioTypeEntitySubcategory = 0;
      byte radioTypeEntitySpecific = 0;
      byte radioTypeEntityExtra = 0;
      byte transmitState = 0;
      byte inputSource = 0;
      short numberVariableTransmitterParamRecords = 0;
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
      short modTypeRadioSystem = 0;
      short cryptoSystem = 0;
      short cryptoKeyID = 0;
      byte lengthOfModParams = 0;
      byte padding2;
      short padding3;
      byte modulationParametersRecord = 0;
      byte antennaPatternRecord = 0;

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
         radioRefIDSiteNum = din.readShort();
         radioRefIDAppNum = din.readShort();
         radioRefIDRefNum = din.readShort();
         radioNumber = din.readShort();
         radioTypeEntityKind = din.readByte();
         radioTypeEntityDomain = din.readByte();
         radioTypeEntityCountry = din.readShort();
         radioTypeEntityCategory = din.readByte();
         radioTypeEntitySubcategory = din.readByte();
         radioTypeEntitySpecific = din.readByte();
         radioTypeEntityExtra = din.readByte();
         transmitState = din.readByte();
         inputSource = din.readByte();
         numberVariableTransmitterParamRecords = din.readShort();
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
         modTypeRadioSystem = din.readShort();
         cryptoSystem = din.readShort();
         cryptoKeyID = din.readShort();
         lengthOfModParams = din.readByte();
         padding2 = din.readByte();
         padding3 = din.readShort();
         modulationParametersRecord = din.readByte();
         antennaPatternRecord = din.readByte();

         System.out.println(LoggerUtil.prettyPrintField("protocolVersion") + protocolVersion);
         System.out.println(LoggerUtil.prettyPrintField("exercise") + exercise);
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("radioRefIDSiteNum") + radioRefIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("radioRefIDAppNum") + radioRefIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("radioRefIDRefNum") + radioRefIDRefNum);
         System.out.println(LoggerUtil.prettyPrintField("radioNumber") + radioNumber);
         System.out.println(LoggerUtil.prettyPrintField("radioTypeEntityKind") + radioTypeEntityKind);
         System.out.println(LoggerUtil.prettyPrintField("radioTypeEntityDomain") + radioTypeEntityDomain);
         System.out.println(LoggerUtil.prettyPrintField("radioTypeEntityCountry") + radioTypeEntityCountry);
         System.out.println(LoggerUtil.prettyPrintField("radioTypeEntityCategory") + radioTypeEntityCategory);
         System.out.println(LoggerUtil.prettyPrintField("radioTypeEntitySubcategory") + radioTypeEntitySubcategory);
         System.out.println(LoggerUtil.prettyPrintField("radioTypeEntitySpecific") + radioTypeEntitySpecific);
         System.out.println(LoggerUtil.prettyPrintField("radioTypeEntityExtra") + radioTypeEntityExtra);
         System.out.println(LoggerUtil.prettyPrintField("transmitState") + transmitState);
         System.out.println(LoggerUtil.prettyPrintField("inputSource") + inputSource);
         System.out.println(LoggerUtil.prettyPrintField("numberVariableTransmitterParamRecords") + numberVariableTransmitterParamRecords);
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
         System.out.println(LoggerUtil.prettyPrintField("modTypeRadioSystem") + modTypeRadioSystem);
         System.out.println(LoggerUtil.prettyPrintField("cryptoSystem") + cryptoSystem);
         System.out.println(LoggerUtil.prettyPrintField("cryptoKeyID") + cryptoKeyID);
         System.out.println(LoggerUtil.prettyPrintField("lengthOfModParams") + lengthOfModParams);
         System.out.println(LoggerUtil.prettyPrintField("modulationParametersRecord") + modulationParametersRecord);
         System.out.println(LoggerUtil.prettyPrintField("antennaPatternRecord") + antennaPatternRecord);

         for (int i=0; i<numberVariableTransmitterParamRecords; i++) {
            
            int recordType = 0;
            short recordLength = 0;
            byte recordSpecificFields = 0;
            byte padding4 = 0;
            
            recordType = din.readInt();
            recordLength = din.readShort();
            recordSpecificFields = din.readByte();
            padding4 = din.readByte();
            
            System.out.println(LoggerUtil.prettyPrintField("recordType") + recordType);
            System.out.println(LoggerUtil.prettyPrintField("recordLength") + recordLength);
            System.out.println(LoggerUtil.prettyPrintField("recordSpecificFields") + recordSpecificFields);
            
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
