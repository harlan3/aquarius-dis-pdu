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

import jargs.gnu.CmdLineParser;

public class Main {

   private static void printUsage() {

      System.out.println("Usage: pdu_logger [OPTION]...");
      System.out
            .println("Log DIS PDU network traffic based on specified options.");
      System.out.println();
      System.out.println("   -p, --port         port number");
      System.out.println("   -m, --multicast    multicast group");
      System.out.println("   -e, --exercise     DIS exercise ID");
      System.out.println("   -h, --help         show this help message");

   }

   /**
    * @param args
    *           the command line arguments
    */
   public static void main(String[] args) {

      ProcessDatagramThread processDatagramThread = new ProcessDatagramThread();
      CmdLineParser parser = new CmdLineParser();
      PDULoggerConfig pduLoggerConfig;

      CmdLineParser.Option help = parser.addBooleanOption('h', "help");
      CmdLineParser.Option port = parser.addIntegerOption('p', "port");
      CmdLineParser.Option multicastGroup = parser.addStringOption('m',
            "multicast");
      CmdLineParser.Option exerciseID = parser
            .addIntegerOption('e', "exercise");

      Boolean helpValue;
      Integer portValue;
      String multicastGroupAddress;
      Integer exerciseIDValue;

      try {
         parser.parse(args);
      } catch (CmdLineParser.OptionException e) {
         System.out.println(e.getMessage());
         printUsage();
         System.exit(0);
      }

      helpValue = (Boolean) parser.getOptionValue(help);
      portValue = (Integer) parser.getOptionValue(port);
      multicastGroupAddress = (String) parser.getOptionValue(multicastGroup);
      exerciseIDValue = (Integer) parser.getOptionValue(exerciseID);

      if (helpValue != null) {
         printUsage();
         System.exit(0);
      }

      pduLoggerConfig = PDULoggerConfig.getInstance();

      if (portValue != null)
         pduLoggerConfig.setPortNumber(portValue);

      if (multicastGroupAddress != null)
         pduLoggerConfig.setMulticastGroupAddress(multicastGroupAddress);

      if (exerciseIDValue != null)
         pduLoggerConfig.setDISExerciseID(exerciseIDValue);

      System.out.println("  Listening on port: "
            + pduLoggerConfig.getPortNumber());

      if (pduLoggerConfig.getUseMulticast())
         System.out.println("    Multicast Group: "
               + pduLoggerConfig.getMulticastGroupAddress());

      if (pduLoggerConfig.getDISExerciseID() != 0)
         System.out.println(" Filter on exercise: "
               + pduLoggerConfig.getDISExerciseID());

      System.out.println();

      processDatagramThread.start();

   }
}
