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

package orbisoftware.aquarius.pdu_heartbeat_generator;

public class HeartbeatGeneratorData {

   private static HeartbeatGeneratorData instance = null;
   private byte[] datagramData = null;
   private Boolean generatorActive = false;
   private int heartbeatInterval = 5000;
   
   protected HeartbeatGeneratorData() {

   }

   public static HeartbeatGeneratorData getInstance() {
      
      if (instance == null) {
         instance = new HeartbeatGeneratorData();
      }
      return instance;
   }

   public void setGeneratorActive(Boolean newValue) {
      
      generatorActive = newValue;
   }

   public Boolean getGeneratorActive() {
      return generatorActive;
   }
   
   public void setHeartbeatInterval(int newValue) {
      
      heartbeatInterval = newValue;
   }

   public int getHeartbeatInterval() {
      
      return heartbeatInterval;
   }

   public Boolean datagramDataValid() {

      Boolean isValid = false;

      if (datagramData != null) {
         if (datagramData.length > 0)
            isValid = true;
      }

      return isValid;
   }

   public void setDatagramData(byte[] newDatagramData) {
      
      datagramData = new byte[(newDatagramData.length)];
      datagramData = newDatagramData;
   }

   public byte[] getDatagramData() {
      
      return datagramData;
   }
}
