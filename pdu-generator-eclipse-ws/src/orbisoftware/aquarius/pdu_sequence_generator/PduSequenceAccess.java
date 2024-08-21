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

import java.beans.PropertyChangeSupport;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.ArrayList;

public class PduSequenceAccess {

   private static PduSequenceAccess instance = null;

   private int numberPDUs = 0;
   private PropertyChangeSupport propertyChangeSupport;

   protected PduSequenceAccess() {
      propertyChangeSupport = new PropertyChangeSupport(this);
   }

   public PropertyChangeSupport getPropertyChangeSupport() {
      return propertyChangeSupport;
   }

   public static PduSequenceAccess getInstance() {
      if (instance == null) {
         instance = new PduSequenceAccess();
      }
      return instance;
   }
   
   public PduSequenceEntry getPDU(int pduNumber) {
      
      return SharedData.getInstance().pduSeqMsgs.get(pduNumber);
   }

   public boolean getNextPDU(PduSequenceEntry pduEntry) {

      SequenceGeneratorData pduPlayerData = SequenceGeneratorData.getInstance();
      SequenceGeneratorUI pduPlayerUI = SequenceGeneratorUI.getInstance();

      // Update currentPDUnumber
      int currentPDUnumber = pduPlayerData.getCurrentPDUnumber();
      PduSequenceEntry currentPduEntry;
      numberPDUs = SharedData.getInstance().pduSeqMsgs.size();
      
      // If pdu sequence entries haven't been loaded or we have played all the PDUs,
      // stop the player and return false.
      if ((numberPDUs == 0) || (currentPDUnumber == numberPDUs)) {

         pduPlayerUI.pushStopButton();
         return false;
      }

      currentPduEntry = SharedData.getInstance().pduSeqMsgs.get(currentPDUnumber);

      try {
         
         pduEntry.disPduName = currentPduEntry.disPduName;
         pduEntry.filePath = currentPduEntry.filePath;
         pduEntry.postDelayMilliseconds = currentPduEntry.postDelayMilliseconds;
         pduEntry.timeStamp = currentPduEntry.timeStamp;
         
      } catch (Exception E) {
         System.out.println("Exception when accessing PDU Packet content.");
         return false;
      }

      return true;
   }

   public int getNumberPDUs() {

      return numberPDUs;
   }
}