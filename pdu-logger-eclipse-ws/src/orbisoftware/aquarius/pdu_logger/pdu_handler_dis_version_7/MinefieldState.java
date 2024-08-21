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

import javax.swing.text.Utilities;

import java.net.*;

public class MinefieldState {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(45);

      byte pduType = 0;
      byte family = 0;
      short length = 0;
      byte pduStatus = 0;
      short minefieldIDSiteNum = 0;
      short minefieldIDAppNum = 0;
      short minefieldIDMinefieldNum = 0;
      short minefieldSequenceNum = 0;
      byte forceID = 0;
      byte numPerimeterPoints = 0;
      byte mineTypeEntKind = 0;
      byte mineTypeDomain = 0;
      short mineTypeCountry = 0;
      byte mineTypeCategory = 0;
      byte mineTypeSubCat = 0;
      byte mineTypeSpecific = 0;
      byte mineTypeExtra = 0;
      short numMineTypes = 0;
      double mineFieldLocationX = 0.0;
      double mineFieldLocationY = 0.0;
      double mineFieldLocationZ = 0.0;
      float mineFieldLocX = 0.0f;
      float mineFieldLocY = 0.0f;
      float mineFieldLocZ = 0.0f;
      float mineFieldOrientPsi = 0.0f;
      float mineFieldOrientTheta = 0.0f;
      float mineFieldOrientPhi = 0.0f;
      short appearance = 0;
      short protocolMode = 0;
      
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
         
         minefieldIDSiteNum = din.readShort();
         minefieldIDAppNum = din.readShort();
         minefieldIDMinefieldNum = din.readShort();
         minefieldSequenceNum = din.readShort();
         forceID = din.readByte();
         numPerimeterPoints = din.readByte();
         mineTypeEntKind = din.readByte();
         mineTypeDomain = din.readByte();
         mineTypeCountry = din.readShort();
         mineTypeCategory = din.readByte();
         mineTypeSubCat = din.readByte();
         mineTypeSpecific = din.readByte();
         mineTypeExtra = din.readByte();
         numMineTypes = din.readShort();
         mineFieldLocationX = din.readDouble();
         mineFieldLocationY = din.readDouble();
         mineFieldLocationZ = din.readDouble();
         mineFieldOrientPsi = din.readFloat();
         mineFieldOrientTheta = din.readFloat();
         mineFieldOrientPhi = din.readFloat();
         appearance = din.readShort();
         protocolMode = din.readShort();
         
         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("minefieldIDSiteNum") + minefieldIDSiteNum);
         System.out.println(LoggerUtil.prettyPrintField("minefieldIDAppNum") + minefieldIDAppNum);
         System.out.println(LoggerUtil.prettyPrintField("minefieldIDMinefieldNum") + minefieldIDMinefieldNum);
         System.out.println(LoggerUtil.prettyPrintField("minefieldSequenceNum") + minefieldSequenceNum);
         System.out.println(LoggerUtil.prettyPrintField("forceID") + forceID);
         System.out.println(LoggerUtil.prettyPrintField("numPerimeterPoints") + numPerimeterPoints);
         System.out.println(LoggerUtil.prettyPrintField("mineTypeEntKind") + mineTypeEntKind);
         System.out.println(LoggerUtil.prettyPrintField("mineTypeDomain") + mineTypeDomain);
         System.out.println(LoggerUtil.prettyPrintField("mineTypeCountry") + mineTypeCountry);
         System.out.println(LoggerUtil.prettyPrintField("mineTypeCategory") + mineTypeCategory);
         System.out.println(LoggerUtil.prettyPrintField("mineTypeSubCat") + mineTypeSubCat);
         System.out.println(LoggerUtil.prettyPrintField("mineTypeSpecific") + mineTypeSpecific);
         System.out.println(LoggerUtil.prettyPrintField("mineTypeExtra") + mineTypeExtra);
         System.out.println(LoggerUtil.prettyPrintField("numMineTypes") + numMineTypes);
         System.out.println(LoggerUtil.prettyPrintField("mineFieldLocationX") + mineFieldLocationX);
         System.out.println(LoggerUtil.prettyPrintField("mineFieldLocationY") + mineFieldLocationY);
         System.out.println(LoggerUtil.prettyPrintField("mineFieldLocationZ") + mineFieldLocationZ);
         System.out.println(LoggerUtil.prettyPrintField("mineFieldOrientPsi") + mineFieldOrientPsi);
         System.out.println(LoggerUtil.prettyPrintField("mineFieldOrientTheta") + mineFieldOrientTheta);
         System.out.println(LoggerUtil.prettyPrintField("mineFieldOrientPhi") + mineFieldOrientPhi);
         System.out.println(LoggerUtil.prettyPrintField("appearance") + LoggerUtil.toBitString(appearance));
         System.out.println(LoggerUtil.prettyPrintField("protocolMode") + protocolMode);
         
         for (int i=0; i<numPerimeterPoints; i++) {
            
            float xComponent = 0.0f;
            float yComponent = 0.0f;
            
            xComponent = din.readFloat();
            yComponent = din.readFloat();
            
            System.out.println(LoggerUtil.prettyPrintField("xComponent") + xComponent);
            System.out.println(LoggerUtil.prettyPrintField("yComponent") + yComponent);
         }
         
         for (int j=0; j<numMineTypes; j++) {
            
            byte mineTypeNumEntKind = 0;
            byte mineTypeNumDomain = 0;
            short mineTypeNumCountry = 0;
            byte mineTypeNumCategory = 0;
            byte mineTypeNumSubCat = 0;
            byte mineTypeNumSpecific = 0;
            byte mineTypeNumExtra = 0;
            
            mineTypeNumEntKind = din.readByte();
            mineTypeNumDomain = din.readByte();
            mineTypeNumCountry = din.readShort();
            mineTypeNumCategory = din.readByte();
            mineTypeNumSubCat = din.readByte();
            mineTypeNumSpecific = din.readByte();
            mineTypeNumExtra = din.readByte();
            
            System.out.println(LoggerUtil.prettyPrintField("mineTypeNumEntKind") + mineTypeNumEntKind);
            System.out.println(LoggerUtil.prettyPrintField("mineTypeNumDomain") + mineTypeNumDomain);
            System.out.println(LoggerUtil.prettyPrintField("mineTypeNumCountry") + mineTypeNumCountry);
            System.out.println(LoggerUtil.prettyPrintField("mineTypeNumCategory") + mineTypeNumCategory);
            System.out.println(LoggerUtil.prettyPrintField("mineTypeNumSubCat") + mineTypeNumSubCat);
            System.out.println(LoggerUtil.prettyPrintField("mineTypeNumSpecific") + mineTypeNumSpecific);
            System.out.println(LoggerUtil.prettyPrintField("mineTypeNumExtra") + mineTypeNumExtra);
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
