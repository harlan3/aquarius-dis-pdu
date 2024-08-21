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

public class LoggerUtil {
   
   private static int prettyPrintColumnWidth = 0;

   public static String toBitString(byte intValue) {

      int unsignedNum = Byte.toUnsignedInt(intValue);
      String stringRep = Integer.toBinaryString(unsignedNum);
      int leadZeros = 8 - stringRep.length();

      return "00000000".substring(0, leadZeros) + stringRep;
   }

   public static String toBitString(short intValue) {

      int unsignedNum = Short.toUnsignedInt(intValue);
      String stringRep = Integer.toBinaryString(unsignedNum);
      int leadZeros = 16 - stringRep.length();

      return "0000000000000000".substring(0, leadZeros) + stringRep;
   }

   public static String toBitString(int intValue) {

      long unsignedNum = Integer.toUnsignedLong(intValue);
      String stringRep = Long.toBinaryString(unsignedNum);
      int leadZeros = 32 - stringRep.length();

      return "00000000000000000000000000000000".substring(0, leadZeros)
            + stringRep;
   }

   public static String integerToIp(int i) {
     
      return ((i >> 24) & 0xFF) + "." + ((i >> 16) & 0xFF) + "."
            + ((i >> 8) & 0xFF) + "." + (i & 0xFF);
   }

   public static int unsignedByteToInt(byte b) {
      
      return (int) b & 0xFF;
   }
   
   public static void setPrettyPrintColumnWidth(int columnWidth) {
      
      prettyPrintColumnWidth = columnWidth;
   }
   
   public static String prettyPrintField(String field) {
      
      String returnString = "";
      
      try {
         int spacesLength = prettyPrintColumnWidth - field.length();
         returnString = String.format("%" + (spacesLength) + "s", "") + field + ": ";
      } catch (Exception e) {
         System.out.println("An exception encountered in prettyPrintField. Try increasing the size of setPrettyPrintColumnWidth.");
      }
      return returnString;
   }
}
