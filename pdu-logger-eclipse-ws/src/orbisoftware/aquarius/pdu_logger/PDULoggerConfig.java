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

package orbisoftware.aquarius.pdu_logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class PDULoggerConfig {

   private final String LOG_FILTER_CFG_FILENAME = "log_filter.cfg";

   private static PDULoggerConfig instance = null;
   private HashMap<PDU_Type, Boolean> pduLogMap = new HashMap<PDU_Type, Boolean>();
   private int portNumber = 3000; // Default to port 3000
   private int exerciseID = 0; // Default to all exercises
   private String multicastGroupAddress = null;
   private boolean useMulticast = false;

   protected PDULoggerConfig() {

      File logFilterConfigFile = new File(LOG_FILTER_CFG_FILENAME);

      /*
       * If a log filter config file is not found, write out a default log
       * filter config file that that logs all PDU types.
       */

      if (!logFilterConfigFile.exists())
         generateDefaultLogFilterConfig();

      populateLogFilterList();

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

   private void generateDefaultLogFilterConfig() {

      try {
         PrintWriter logFilterConfigFile = new PrintWriter(new FileWriter(
               LOG_FILTER_CFG_FILENAME));

         for (PDU_Type pduType : PDU_Type.values()) {

            switch (pduType) {

            case Other_Log_All_PDU_Types:
               logFilterConfigFile.println(pduType);
               break;

            default:
               // Ignore the undefined placeholder messages
               if (pduType.name().startsWith("u"))
                  continue;

               logFilterConfigFile.println("# " + pduType);
               break;
            }
         }
         logFilterConfigFile.close();
      } catch (IOException e) {
         System.out.println("Could not create " + LOG_FILTER_CFG_FILENAME);
      }
   }

   private void populateLogFilterList() {

      BufferedReader bufferedReader;
      String textLine;

      try {
         bufferedReader = new BufferedReader(new FileReader(
               LOG_FILTER_CFG_FILENAME));

         textLine = bufferedReader.readLine();

         while (textLine != null) {

            if (!textLine.contains("#")) {
               // Remove any whitespace
               textLine = textLine.replaceAll(" ", "");
               System.out.println("Logging enabled for: " + textLine);
               pduLogMap.put(PDU_Type.valueOf(textLine), true);
            }

            textLine = bufferedReader.readLine();
         }

         bufferedReader.close();
      } catch (Exception e) {
         System.out.println("Error while reading " + LOG_FILTER_CFG_FILENAME);
      }
   }
}
