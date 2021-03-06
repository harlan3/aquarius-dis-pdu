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

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Date;

import java.net.*;

public class ArealObjectState {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(
            packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();

      byte pduType = 0;
      short length = 0;
      short siteNum = 0;
      short appNum = 0;
      short objNum = 0;
      short refSiteNum = 0;
      short refAppNum = 0;
      short refObjNum = 0;
      short updateNum = 0;
      byte forceID = 0;
      byte modifications = 0;
      byte objTypeDomain = 0;
      byte objTypeKind = 0;
      byte objTypeCat = 0;
      byte objTypeSubCat = 0;
      int specObjAppearance = 0;
      short generalObjAppearance = 0;
      short numberOfPoints = 0;
      short reqSimIDSiteNum = 0;
      short reqSimIDAppNum = 0;
      short recSimIDSiteNum = 0;
      short recSimIDAppNum = 0;

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
         siteNum = din.readShort();
         appNum = din.readShort();
         objNum = din.readShort();
         refSiteNum = din.readShort();
         refAppNum = din.readShort();
         refObjNum = din.readShort();
         updateNum = din.readShort();
         forceID = din.readByte();
         modifications = din.readByte();
         objTypeDomain = din.readByte();
         objTypeKind = din.readByte();
         objTypeCat = din.readByte();
         objTypeSubCat = din.readByte();
         specObjAppearance = din.readInt();
         generalObjAppearance = din.readShort();
         numberOfPoints = din.readShort();
         reqSimIDSiteNum = din.readShort();
         reqSimIDAppNum = din.readShort();
         recSimIDSiteNum = din.readShort();
         recSimIDAppNum = din.readShort();

         System.out.println("                 pduType : "
               + PDU_Type.values()[pduType]);
         System.out.println("                  length : " + length);
         System.out.println("                 siteNum : " + siteNum);
         System.out.println("                  appNum : " + appNum);
         System.out.println("                  objNum : " + objNum);
         System.out.println("              refSiteNum : " + refSiteNum);
         System.out.println("               refAppNum : " + refAppNum);
         System.out.println("               refObjNum : " + refObjNum);
         System.out.println("               updateNum : " + updateNum);
         System.out.println("                 forceID : " + forceID);
         System.out.println("           modifications : " + modifications);
         System.out.println("           objTypeDomain : " + objTypeDomain);
         System.out.println("             objTypeKind : " + objTypeKind);
         System.out.println("              objTypeCat : " + objTypeCat);
         System.out.println("           objTypeSubCat : " + objTypeSubCat);
         System.out.println("       specObjAppearance : " + specObjAppearance);
         System.out.println("        genObjAppearance : "
               + generalObjAppearance);
         System.out.println("          numberOfPoints : " + numberOfPoints);
         System.out.println("         reqSimIDSiteNum : " + reqSimIDSiteNum);
         System.out.println("          reqSimIDAppNum : " + reqSimIDAppNum);
         System.out.println("         recSimIDSiteNum : " + recSimIDSiteNum);
         System.out.println("          recSimIDAppNum : " + recSimIDAppNum);

         double objLocationXComp[] = new double[numberOfPoints];
         double objLocationYComp[] = new double[numberOfPoints];
         double objLocationZComp[] = new double[numberOfPoints];

         for (int x = 0; x < numberOfPoints; x++) {

            objLocationXComp[x] = din.readDouble();
            objLocationYComp[x] = din.readDouble();
            objLocationZComp[x] = din.readDouble();

            System.out.println("     objLocationXComp[" + x + "] : "
                  + objLocationXComp[x]);
            System.out.println("     objLocationYComp[" + x + "] : "
                  + objLocationYComp[x]);
            System.out.println("     objLocationZComp[" + x + "] : "
                  + objLocationZComp[x]);
         }

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
