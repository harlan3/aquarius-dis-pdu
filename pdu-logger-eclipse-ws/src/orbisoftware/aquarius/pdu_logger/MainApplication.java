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

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import jargs.gnu.CmdLineParser;

public class MainApplication {
   
   private static MainApplication instance = null;
   public HashMap<String, String> xmlMap = new HashMap<String, String>();

   private void parseElements(Element root) {

      String name = "";
   
      if (root != null) {
   
         NodeList nl = root.getChildNodes();
   
         if (nl != null) {
   
            for (int i = 0; i < nl.getLength(); i++) {
               Node node = nl.item(i);
   
               if (node.getNodeName().equalsIgnoreCase("setting")) {
   
                  NodeList childNodes = node.getChildNodes();
   
                  for (int j = 0; j < childNodes.getLength(); j++) {
   
                     Node child = childNodes.item(j);
   
                     if (child.getNodeName().equalsIgnoreCase("name"))
                        name = child.getTextContent();
                     else if (child.getNodeName().equalsIgnoreCase("value"))
                        MainApplication.getInstance().xmlMap.put(name,
                              child.getTextContent());
                  }
               }
            }
         }
      }
   }
   
   public static MainApplication getInstance() {

      if (instance == null) {
         instance = new MainApplication();
      }
      return instance;
   }
   
   private static void printUsage() {

      System.out.println("Usage: pdu_logger [OPTION]...");
      System.out
            .println("Log DIS PDU network traffic based on specified options.");
      System.out.println();
      System.out.println("   -h, --help         show this help message");

   }

   /**
    * @param args
    *           the command line arguments
    */
   public static void main(String[] args) {

      MainApplication mainApplication = MainApplication.getInstance();
      ProcessDatagramThread processDatagramThread = new ProcessDatagramThread();
      CmdLineParser parser = new CmdLineParser();

      try {
         // Process XML
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder db = dbf.newDocumentBuilder();
         Document doc = db.parse("settings.xml");
         Element rootElem = doc.getDocumentElement();
         
         if (rootElem != null) {
            mainApplication.parseElements(rootElem);
         }
      }  catch (Exception e) {
         e.printStackTrace();
      }
      
      CmdLineParser.Option help = parser.addBooleanOption('h', "help");

      Boolean helpValue;

      try {
         parser.parse(args);
      } catch (CmdLineParser.OptionException e) {
         System.out.println(e.getMessage());
         printUsage();
         System.exit(0);
      }

      helpValue = (Boolean) parser.getOptionValue(help);

      if (helpValue != null) {
         printUsage();
         System.exit(0);
      }
      
      boolean useMulticast = Boolean.parseBoolean(MainApplication.getInstance().xmlMap.get("UseMulticast"));
      String multicastAddress = MainApplication.getInstance().xmlMap.get("MulticastAddress");
      String multicastDeviceAddress = MainApplication.getInstance().xmlMap.get("MulticastDeviceAddress");
      String broadcastAddress = MainApplication.getInstance().xmlMap.get("BroadcastAddress");
      String port = MainApplication.getInstance().xmlMap.get("PortValue");
      int exerciseID = Integer.parseInt(MainApplication.getInstance().xmlMap.get("ExcerciseID"));
            
      System.out.println("  Listening on port: " + port);

      if (useMulticast) {
         
         System.out.println("  Multicast Address: " + multicastAddress);
         System.out.println("   Multicast Device: " + multicastDeviceAddress);
      } else {
         
         System.out.println("  Broadcast Address: " + broadcastAddress);
      }
         
      if (exerciseID != 0)
         System.out.println(" Filter on Exercise: " + exerciseID);

      System.out.println();

      processDatagramThread.start();

   }
}
