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

package orbisoftware.aquarius.pdu_sequence_generator;

public class SequenceGeneratorData {

   private static SequenceGeneratorData instance = null;
   private String ipAddress = null;
   private boolean playerActive = false;
   private boolean loopPlayback;
   private int currentPDUnumber = 0;
   private int port = 0;

   protected SequenceGeneratorData() {
      setDefaultIpAddress();
      port = 3000;
   }

   public static SequenceGeneratorData getInstance() {
      if (instance == null) {
         instance = new SequenceGeneratorData();
      }
      return instance;
   }

   public void setPlayerActive(boolean newValue) {
      playerActive = newValue;
   }

   public boolean getPlayerActive() {
      return playerActive;
   }

   public void setIPAddress(String newIPAddress) {
      ipAddress = newIPAddress;
   }

   public String getIPAddress() {
      return ipAddress;
   }

   public void setPort(int newPort) {
      port = newPort;
   }

   public int getPort() {
      return port;
   }
   
   public void setLoopPlayback(boolean newLoopPlayback) {
      loopPlayback = newLoopPlayback;
   }
   
   public boolean getLoopPlayback() {
      return loopPlayback;
   }

   public void setCurrentPDUnumber(int currentPDUnumber) {
      this.currentPDUnumber = currentPDUnumber;
   }

   public int getCurrentPDUnumber() {
      return currentPDUnumber;
   }

   private void setDefaultIpAddress() {
         ipAddress = "127.0.0.1";
   }
}
