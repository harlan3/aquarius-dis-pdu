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

public class LinearObjectState {

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
      byte numberSegments = 0;
      short reqSimIDSiteNum = 0;
      short reqSimIDAppNum = 0;
      short recSiteNum = 0;
      short recAppNum = 0;
      byte objTypeDomain = 0;
      byte objTypeObjKind = 0;
      byte objTypeObjCat = 0;
      byte objTypeObjSubCat = 0;

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
         numberSegments = din.readByte();
         reqSimIDSiteNum = din.readShort();
         reqSimIDAppNum = din.readShort();
         recSiteNum = din.readShort();
         recAppNum = din.readShort();
         objTypeDomain = din.readByte();
         objTypeObjKind = din.readByte();
         objTypeObjCat = din.readByte();
         objTypeObjSubCat = din.readByte();

         byte linSegParamSegNum[] = new byte[numberSegments];
         byte linSegParamSegMod[] = new byte[numberSegments];
         byte linSegParamSegAppear[][] = new byte[numberSegments][6];
         double lineSegParamXComp[] = new double[numberSegments];
         double lineSegParamYComp[] = new double[numberSegments];
         double lineSegParamZComp[] = new double[numberSegments];
         float lineSegParamPsi[] = new float[numberSegments];
         float lineSegParamTheta[] = new float[numberSegments];
         float lineSegParamPhi[] = new float[numberSegments];
         short lineSegParamSegLength[] = new short[numberSegments];
         short lineSegParamSegWidth[] = new short[numberSegments];
         short lineSegParamSegHeight[] = new short[numberSegments];
         short lineSegParamSegDepth[] = new short[numberSegments];
         int lineSegParamPadding[] = new int[numberSegments];

         for (int x = 0; x < numberSegments; x++) {
            linSegParamSegNum[x] = din.readByte();
            linSegParamSegMod[x] = din.readByte();

            for (int y = 0; y < 6; y++) {
               linSegParamSegAppear[x][y] = din.readByte();
            }
            lineSegParamXComp[x] = din.readDouble();
            lineSegParamYComp[x] = din.readDouble();
            lineSegParamZComp[x] = din.readDouble();
            lineSegParamPsi[x] = din.readFloat();
            lineSegParamTheta[x] = din.readFloat();
            lineSegParamPhi[x] = din.readFloat();
            lineSegParamSegLength[x] = din.readShort();
            lineSegParamSegWidth[x] = din.readShort();
            lineSegParamSegHeight[x] = din.readShort();
            lineSegParamSegDepth[x] = din.readShort();
            lineSegParamPadding[x] = din.readInt();
         }

         System.out.println("                    pduType : "
               + PDU_Type.values()[pduType]);
         System.out.println("                     length : " + length);
         System.out.println("                    siteNum : " + siteNum);
         System.out.println("                     appNum : " + appNum);
         System.out.println("                     objNum : " + objNum);
         System.out.println("                 refSiteNum : " + refSiteNum);
         System.out.println("                  refAppNum : " + refAppNum);
         System.out.println("                  refObjNum : " + refObjNum);
         System.out.println("                  updateNum : " + updateNum);
         System.out.println("                    forceID : " + forceID);
         System.out.println("             numberSegments : " + numberSegments);
         System.out.println("            reqSimIDSiteNum : " + reqSimIDSiteNum);
         System.out.println("             reqSimIDAppNum : " + reqSimIDAppNum);
         System.out.println("                 recSiteNum : " + recSiteNum);
         System.out.println("                  recAppNum : " + recAppNum);
         System.out.println("              objTypeDomain : " + objTypeDomain);
         System.out.println("             objTypeObjKind : " + objTypeObjKind);
         System.out.println("              objTypeObjCat : " + objTypeObjCat);
         System.out
               .println("           objTypeObjSubCat : " + objTypeObjSubCat);

         for (int x = 0; x < numberSegments; x++) {

            System.out.println("       linSegParamSegNum[" + x + "] : "
                  + linSegParamSegNum[x]);
            System.out.println("       linSegParamSegMod[" + x + "] : "
                  + linSegParamSegMod[x]);

            System.out.print("    linSegParamSegAppear[" + x + "] :");

            for (int y = 0; y < 6; y++) {
               System.out.print(" " + linSegParamSegAppear[x][y]);
            }

            System.out.println();
            System.out.println("       lineSegParamXComp[" + x + "] : "
                  + lineSegParamXComp[x]);
            System.out.println("       lineSegParamYComp[" + x + "] : "
                  + lineSegParamYComp[x]);
            System.out.println("       lineSegParamZComp[" + x + "] : "
                  + lineSegParamZComp[x]);
            System.out.println("         lineSegParamPsi[" + x + "] : "
                  + lineSegParamPsi[x]);
            System.out.println("       lineSegParamTheta[" + x + "] : "
                  + lineSegParamTheta[x]);
            System.out.println("         lineSegParamPhi[" + x + "] : "
                  + lineSegParamPhi[x]);
            System.out.println("   lineSegParamSegLength[" + x + "] : "
                  + lineSegParamSegLength[x]);
            System.out.println("    lineSegParamSegWidth[" + x + "] : "
                  + lineSegParamSegWidth[x]);
            System.out.println("   lineSegParamSegHeight[" + x + "] : "
                  + lineSegParamSegHeight[x]);
            System.out.println("    lineSegParamSegDepth[" + x + "] : "
                  + lineSegParamSegDepth[x]);
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
