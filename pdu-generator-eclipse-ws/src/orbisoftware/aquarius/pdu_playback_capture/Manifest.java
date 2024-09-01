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

package orbisoftware.aquarius.pdu_playback_capture;

import java.beans.PropertyChangeSupport;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import javax.swing.event.ChangeEvent;

import orbisoftware.aquarius.pdu_sequence_generator.SendDatagramSeqGenThread;
import orbisoftware.aquarius.pdu_sequence_generator.SequenceGeneratorUI;

import java.util.ArrayList;

public class Manifest {

   private static Manifest instance = null;

   private List<ManifestEntry> manifestList = new ArrayList<ManifestEntry>();
   private long initialTimestamp = 0;
   private int numberPDUs = 0;
   private RandomAccessFile pduContentRAF;
   private PropertyChangeSupport propertyChangeSupport;

   protected Manifest() {
      propertyChangeSupport = new PropertyChangeSupport(this);
   }

   public PropertyChangeSupport getPropertyChangeSupport() {
      return propertyChangeSupport;
   }

   public static Manifest getInstance() {
      if (instance == null) {
         instance = new Manifest();
      }
      return instance;
   }

   public void loadFile(String filename) {

      DataInputStream manifestInputStream;
      String pduContentFilename;

      /* Load the new manifest */
      try {

         manifestInputStream = new DataInputStream(
               new FileInputStream(filename));
         numberPDUs = manifestInputStream.readInt();

         manifestList.clear();
         for (int i = 0; i < numberPDUs; i++) {

            ManifestEntry manifestEntry = new ManifestEntry(
                  manifestInputStream.readLong(),
                  manifestInputStream.readInt(), manifestInputStream.readInt(),
                  manifestInputStream.readShort());

            // If this is the initial manifest entry, initialize the
            // initialTimestamp to make time relative to first packet.
            if (i == 0)
               initialTimestamp = manifestEntry.packetTimeStamp;

            manifestEntry.packetTimeStamp -= initialTimestamp;

            // Add new manifest entry
            manifestList.add(manifestEntry);
         }

         manifestInputStream.close();
      } catch (IOException E) {
         System.out.println("Exception occured when loading " + filename);
         numberPDUs = 0;
         return;
      }

      /* Close existing DataInputStream for PDU content if open */
      try {
         pduContentRAF.close();
      } catch (IOException E) {
      } catch (NullPointerException E) {
      }

      pduContentFilename = filename.replace(".man", ".bin");

      try {
         /* Open up the DataInputStream for PDU content */
         pduContentRAF = new RandomAccessFile(pduContentFilename, "r");
      } catch (IOException E) {
         System.out.println("Exception occured when loading "
               + pduContentFilename);
         numberPDUs = 0;
         return;
      }
   }

   public ManifestEntry getPDU(int pduNumber) {
      return manifestList.get(pduNumber);
   }

   public boolean getNextPDU(PDUEntry pduEntry) {

      PlaybackCaptureData pduPlayerData = PlaybackCaptureData.getInstance();
      PlaybackCaptureUI pduPlayerUI = PlaybackCaptureUI.getInstance();

      // Update currentPDUnumber
      int currentPDUnumber = pduPlayerData.getCurrentPDUnumber();
      ManifestEntry manifestEntry;

      // If manifest hasn't been loaded or we have played all the PDUs,
      // stop the player and return false.
      if ((numberPDUs == 0) || (currentPDUnumber == numberPDUs)) {
         
         if (pduPlayerData.getLoopPlayback() && currentPDUnumber == numberPDUs) {
            
            PlaybackCaptureUI.getInstance().resetStartingDisplay();
            pduPlayerData.setCurrentPDUnumber(0);
            currentPDUnumber = 0;
            
            // Generate Change Event to update GUI info
            ChangeEvent ce = new ChangeEvent(SendDatagramPlaybackCaptureThread.class);
            pduPlayerUI.stateChanged(ce);
            
         }
         else {
            
            pduPlayerUI.pushStopButton();
            return false;
         }
      }

      manifestEntry = manifestList.get(currentPDUnumber);

      try {
         pduEntry.byteBuffer = new byte[manifestEntry.packetSize];
         pduContentRAF.seek(manifestEntry.packetFilePos);
         pduContentRAF.readFully(pduEntry.byteBuffer, 0,
               manifestEntry.packetSize);
         pduEntry.packetTimeDelta = manifestEntry.packetTimeDelta;
      } catch (IOException E) {
         System.out.println("Exception when accessing PDU Packet content.");
         return false;
      }

      return true;
   }

   public int getNumberPDUs() {

      return numberPDUs;
   }
}