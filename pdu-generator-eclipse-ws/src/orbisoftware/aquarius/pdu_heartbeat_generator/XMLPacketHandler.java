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

import org.xml.sax.*;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;

public class XMLPacketHandler implements ContentHandler {

   private ByteArrayOutputStream byteArrayOutputStream = null;
   private Boolean inDataElement;

   public XMLPacketHandler() {
      byteArrayOutputStream = new ByteArrayOutputStream();
      byteArrayOutputStream.reset();

      inDataElement = false;
   }

   public byte[] getPacketData() {
      return byteArrayOutputStream.toByteArray();
   }

   public void setDocumentLocator(Locator locator) {
   }

   public void startDocument() throws SAXException {
   }

   public void endDocument() throws SAXException {
   }

   public void startPrefixMapping(String prefix, String uri)
         throws SAXException {
   }

   public void endPrefixMapping(String prefix) throws SAXException {
   }

   public void startElement(String uri, String localName, String qName,
         Attributes atts) throws SAXException {
      if (localName.equals("data")) {
         inDataElement = true;
      }
   }

   public void endElement(String uri, String localName, String qName)
         throws SAXException {
      if (localName.equals("data")) {
         inDataElement = false;
      }
   }

   public void characters(char ch[], int start, int length) throws SAXException {

      Boolean processAsLeftHexDigit = true;
      String hexByteValue;
      String leftHexDigit = null;
      String rightHexDigit = null;

      if (inDataElement) {
         for (int x = 0; x < length; x++) {

            char curChar = ch[(start + x)];

            if (curChar != ' ') {

               if (processAsLeftHexDigit) {
                  leftHexDigit = new String(String.valueOf(curChar));
                  processAsLeftHexDigit = false;
               } else {
                  byte[] byteArray;
                  BigInteger byteValue;
                  byte outputByte;

                  rightHexDigit = new String(String.valueOf(curChar));
                  processAsLeftHexDigit = true;

                  hexByteValue = leftHexDigit + rightHexDigit;
                  byteValue = new BigInteger(hexByteValue, 16);

                  byteArray = byteValue.toByteArray();

                  if (byteArray.length > 1) {
                     outputByte = byteArray[1];
                  } else {
                     outputByte = byteArray[0];
                  }

                  byteArrayOutputStream.write(outputByte);
               }
            }
         }
      }
   }

   public void ignorableWhitespace(char ch[], int start, int length)
         throws SAXException {
   }

   public void processingInstruction(String target, String data)
         throws SAXException {
   }

   public void skippedEntity(String name) throws SAXException {
      System.out.println(name);
   }
}
