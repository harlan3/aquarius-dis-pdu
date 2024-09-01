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

package orbisoftware.aquarius.dis_sim_map;

import java.net.InetAddress;
import java.util.HashMap;

public class PDULoggerConfig {

   private static PDULoggerConfig instance = null;
   private HashMap<PDU_Type, Boolean> pduLogMap = new HashMap<PDU_Type, Boolean>();
   private int portNumber = 3000; // Default to port 3000
   private int exerciseID = 0; // Default to all exercises
   private String ipAddress; // Will not be used unless it is multicast
   private String multicastGroupAddress = null;
   private boolean useMulticast = false;

   protected PDULoggerConfig() {

   }

   public static PDULoggerConfig getInstance() {
      if (instance == null) {
         instance = new PDULoggerConfig();
      }
      return instance;
   }

   public Boolean loggingPDUtype(PDU_Type pduType) {

      if (pduLogMap.containsKey(pduType))
         return true;
      else
         return false;
   }

   public void setPortNumber(int portNumber) {
      this.portNumber = portNumber;
   }

   public int getPortNumber() {
      return portNumber;
   }
   
   public void setIPAddress(String ipAddress) {
	   
	  this.ipAddress = ipAddress;
	  
	  try {
		  InetAddress inetAddress = InetAddress.getByName(ipAddress);
		  
		  if (inetAddress.isMulticastAddress())
			  setMulticastGroupAddress(ipAddress);
		  
	  } catch (Exception e) {}
	  
   }

   public String getDefaultIPAddress() {
	   
	   return "127.0.0.1";
   }
   
   public void setMulticastGroupAddress(String multicastGroupAddress) {
	   
      this.multicastGroupAddress = multicastGroupAddress;
      this.useMulticast = true;
   }

   public String getMulticastGroupAddress() {
      return multicastGroupAddress;
   }

   public boolean getUseMulticast() {
      return useMulticast;
   }

   public void setDISExerciseID(int exerciseID) {
      this.exerciseID = exerciseID;
   }

   public int getDISExerciseID() {
      return exerciseID;
   }
}
