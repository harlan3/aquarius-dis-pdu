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

public class CollisionElastic {

   static public void processPDU(DatagramPacket packet, int pduCounter) {

      ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(
            packet.getData());
      DataInputStream din = new DataInputStream(byteArrayStream);
      Date date = new Date();
      LoggerUtil.setPrettyPrintColumnWidth(30);
      
      byte pduType = 0;
      byte family = 0;
      short length = 0;
      byte pduStatus = 0;
      short issuingEntIDSite = 0;
      short issuingEntIDApp = 0;
      short issuingEntIDEntity = 0;
      short collidingEntIDSite = 0;
      short collidingEntIDApp = 0;
      short collidingEntIDEntity = 0;
      short eventIDSite = 0;
      short eventIDApp = 0;
      short eventIDEventNum = 0;
      @SuppressWarnings("unused")
      short padding = 0;
      float contactVelocityX = 0.0f;
      float contactVelocityY = 0.0f;
      float contactVelocityZ = 0.0f;
      float mass = 0.0f;
      float locationImpactX = 0.0f;
      float locationImpactY = 0.0f;
      float locationImpactZ = 0.0f;
      float collisionIntermediateResultXX = 0.0f;
      float collisionIntermediateResultXY = 0.0f;
      float collisionIntermediateResultXZ = 0.0f;
      float collisionIntermediateResultYY = 0.0f;
      float collisionIntermediateResultYZ = 0.0f;
      float collisionIntermediateResultZZ = 0.0f;  
      
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
         issuingEntIDSite = din.readShort();
         issuingEntIDApp = din.readShort();
         issuingEntIDEntity = din.readShort();
         collidingEntIDSite = din.readShort();
         collidingEntIDApp = din.readShort();
         collidingEntIDEntity = din.readShort();
         eventIDSite = din.readShort();
         eventIDApp = din.readShort();
         eventIDEventNum = din.readShort();
         padding = din.readShort();
         contactVelocityX = din.readFloat();
         contactVelocityY = din.readFloat();
         contactVelocityZ = din.readFloat();
         mass = din.readFloat();
         locationImpactX = din.readFloat();
         locationImpactY = din.readFloat();
         locationImpactZ = din.readFloat();
         collisionIntermediateResultXX = din.readFloat();
         collisionIntermediateResultXY = din.readFloat(); 
         collisionIntermediateResultXZ = din.readFloat(); 
         collisionIntermediateResultYY = din.readFloat(); 
         collisionIntermediateResultYZ = din.readFloat(); 
         collisionIntermediateResultZZ = din.readFloat(); 

         System.out.println(LoggerUtil.prettyPrintField("pduType") + PDU_Type.values()[pduType]);
         System.out.println(LoggerUtil.prettyPrintField("family") + family);
         System.out.println(LoggerUtil.prettyPrintField("length") + length);
         System.out.println(LoggerUtil.prettyPrintField("pduStatus") + pduStatus);
         System.out.println(LoggerUtil.prettyPrintField("issuingEntIDSite") + issuingEntIDSite);
         System.out.println(LoggerUtil.prettyPrintField("issuingEntIDApp") + issuingEntIDApp);
         System.out.println(LoggerUtil.prettyPrintField("issuingEntIDEntity") + issuingEntIDEntity);
         System.out.println(LoggerUtil.prettyPrintField("collidingEntIDSite") + collidingEntIDSite);
         System.out.println(LoggerUtil.prettyPrintField("collidingEntIDApp") + collidingEntIDApp);
         System.out.println(LoggerUtil.prettyPrintField("collidingEntIDEntity") + collidingEntIDEntity);
         System.out.println(LoggerUtil.prettyPrintField("eventIDSite") + eventIDSite);
         System.out.println(LoggerUtil.prettyPrintField("eventIDApp") + eventIDApp);
         System.out.println(LoggerUtil.prettyPrintField("eventIDEventNum") + eventIDEventNum);
         System.out.println(LoggerUtil.prettyPrintField("contactVelocityX") + contactVelocityX);
         System.out.println(LoggerUtil.prettyPrintField("contactVelocityY") + contactVelocityY);
         System.out.println(LoggerUtil.prettyPrintField("contactVelocityZ") + contactVelocityZ);
         System.out.println(LoggerUtil.prettyPrintField("mass") + mass);
         System.out.println(LoggerUtil.prettyPrintField("locationImpactX") + locationImpactX);
         System.out.println(LoggerUtil.prettyPrintField("locationImpactY") + locationImpactY);
         System.out.println(LoggerUtil.prettyPrintField("locationImpactZ") + locationImpactZ);
         System.out.println(LoggerUtil.prettyPrintField("collisionIntermediateResultXX") + collisionIntermediateResultXX);
         System.out.println(LoggerUtil.prettyPrintField("collisionIntermediateResultXY") + collisionIntermediateResultXY);
         System.out.println(LoggerUtil.prettyPrintField("collisionIntermediateResultXZ") + collisionIntermediateResultXZ);
         System.out.println(LoggerUtil.prettyPrintField("collisionIntermediateResultYY") + collisionIntermediateResultYY);
         System.out.println(LoggerUtil.prettyPrintField("collisionIntermediateResultYZ") + collisionIntermediateResultYZ);
         System.out.println(LoggerUtil.prettyPrintField("collisionIntermediateResultZZ") + collisionIntermediateResultZZ);
         
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
