package orbisoftware.aquarius.pdu_common;
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

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import orbisoftware.aquarius.pdu_heartbeat_generator.HeartbeatGeneratorUI;
import orbisoftware.aquarius.pdu_heartbeat_generator.SendDatagramHeartbeatThread;
import orbisoftware.aquarius.pdu_playback_capture.PlaybackCaptureUI;
import orbisoftware.aquarius.pdu_playback_capture.SendDatagramPlaybackCaptureThread;
import orbisoftware.aquarius.pdu_sequence_generator.SendDatagramSeqGenThread;
import orbisoftware.aquarius.pdu_sequence_generator.SequenceGeneratorUI;

public class MainApplication {
   
   public static void main(String[] args) {

      // Start pdu heartbeat generator send datagram thread
      SendDatagramHeartbeatThread sendDatagramHeartbeatThread = new SendDatagramHeartbeatThread();
      sendDatagramHeartbeatThread.start();
      
      // Start pdu sequence generator datagram thread
      SendDatagramSeqGenThread sendDatagramSeqGenThread = new SendDatagramSeqGenThread();
      sendDatagramSeqGenThread.start();
      
      // Start pdu playback capture thread
      SendDatagramPlaybackCaptureThread sendDatagramPlaybackCaptureThread = new SendDatagramPlaybackCaptureThread();
      sendDatagramPlaybackCaptureThread.start();

      // Schedule a job for the event-dispatching thread to
      // create and show this application's GUI.

      javax.swing.SwingUtilities.invokeLater(new Runnable() {

         public void run() {

            JFrame jFrame = new JFrame("Aquarius PDU Generation");
            JTabbedPane tabbedPane = new JTabbedPane();
            
            HeartbeatGeneratorUI packetGeneratorUI = HeartbeatGeneratorUI.getInstance();
            SequenceGeneratorUI sequenceGeneratorUI = SequenceGeneratorUI.getInstance();
            PlaybackCaptureUI playbackCaptureUI = PlaybackCaptureUI.getInstance();
            
            JComponent panel1 = packetGeneratorUI.buildPacketGeneratorPanel();
            JComponent panel2 = sequenceGeneratorUI.buildSequenceGeneratorPanel();
            JComponent panel3 = playbackCaptureUI.buildPlaybackCapturePanel();
            
            // Close operation when the window is closed
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.setSize(325, 475);
            jFrame.setVisible(true);

            jFrame.add(tabbedPane);
            tabbedPane.addTab("Heartbeat", panel1);
            tabbedPane.addTab("Sequence", panel2);
            tabbedPane.addTab("Db Playback", panel3);
         }
      });
   }
}
