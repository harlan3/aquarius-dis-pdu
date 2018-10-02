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

   public static String toBitString(byte intValue) {

      String stringRep = Integer.toBinaryString(intValue);
      int leadZeros = 8 - stringRep.length();

      return "00000000".substring(0, leadZeros) + stringRep;
   }

   public static String toBitString(short intValue) {

      String stringRep = Integer.toBinaryString(intValue);
      int leadZeros = 16 - stringRep.length();

      return "0000000000000000".substring(0, leadZeros) + stringRep;
   }

   public static String toBitString(int intValue) {

      String stringRep = Integer.toBinaryString(intValue);
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
}
