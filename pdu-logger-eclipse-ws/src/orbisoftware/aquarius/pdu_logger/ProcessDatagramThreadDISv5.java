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

import orbisoftware.aquarius.pdu_logger.pdu_handler_dis_version_5.*;

import java.net.*;
import java.beans.*;

import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import java.beans.PropertyChangeListener;

public class ProcessDatagramThreadDISv5 extends Thread implements
      PropertyChangeListener {

   private BlockingQueue<DatagramPacket> queue = new LinkedBlockingQueue<DatagramPacket>();

   public void propertyChange(PropertyChangeEvent evt) {

      byte[] buffer = new byte[((DatagramPacket) evt.getNewValue()).getLength()];

      if (evt.getPropertyName().toString().equals("datagramReceived")) {

         synchronized (queue) {

            // Make a copy of the datagram packet, as the referenced object
            // is owned by the other thread.
            System.arraycopy(((DatagramPacket) evt.getNewValue()).getData(), 0,
                  buffer, 0, buffer.length);

            DatagramPacket receivedPacket = new DatagramPacket(buffer,
                  buffer.length);

            queue.add(receivedPacket);
            queue.notify();
         }
      }
   }

   public void run() {

      ReceiveDatagramThread receiveDatagramThread = new ReceiveDatagramThread();
      PDULoggerConfig pduLoggerConfig = PDULoggerConfig.getInstance();
      int configuredDISExerciseID = pduLoggerConfig.getDISExerciseID();
      int pduCounter = 0;

      receiveDatagramThread.getPropertyChangeSupport()
            .addPropertyChangeListener(this);
      receiveDatagramThread.start();

      while (true) {

         List<DatagramPacket> datagramList = new LinkedList<DatagramPacket>();

         try {

            // Block until notified that more datagrams packets have been
            // received or a timeout occurs.
            synchronized (queue) {
               queue.wait(20);
               queue.drainTo(datagramList);
            }
         } catch (InterruptedException e) {
         }

         for (DatagramPacket packet : datagramList) {

            int packetExerciseID = packet.getData()[1];
            PDU_Type packetPDUType = PDU_Type.values()[packet.getData()[2]];

            /*
             * if the logger was configured for a specific DIS exercise then
             * filter accordingly.
             */
            if (configuredDISExerciseID != 0) {
               if (configuredDISExerciseID != packetExerciseID)
                  continue;
            }

            if (pduLoggerConfig.loggingPDUtype(packetPDUType)
                  || pduLoggerConfig
                        .loggingPDUtype(PDU_Type.Other_Log_All_PDU_Types)) {

               pduCounter++;

               switch (packetPDUType) {

               case Acknowledge:
                  Acknowledge.processPDU(packet, pduCounter);
                  break;
                  
               case Action_Request:
                  ActionRequest.processPDU(packet, pduCounter);
                  break;
                  
               case Action_Response:
                  ActionResponse.processPDU(packet, pduCounter);
                  break;

               case Collision:
                  Collision.processPDU(packet, pduCounter);
                  break;

               case Comment:
                  Comment.processPDU(packet, pduCounter);
                  break;
                  
               case Create_Entity:
                  CreateEntity.processPDU(packet, pduCounter);
                  break;

               case Data:
                  Data.processPDU(packet, pduCounter);
                  break;
                  
               case Data_Query:
                  DataQuery.processPDU(packet, pduCounter);
                  break;
         
               case Designator:
                  Designator.processPDU(packet, pduCounter);
                  break;

               case Detonation:
                  Detonation.processPDU(packet, pduCounter);
                  break;

               case Electromagnetic_Emission:
                  ElectromagneticEmission.processPDU(packet, pduCounter);
                  break;

               case Entity_State:
                  EntityState.processPDU(packet, pduCounter);
                  break;

               case Event_Report:
                  EventReport.processPDU(packet, pduCounter);
                  break;

               case Fire:
                  Fire.processPDU(packet, pduCounter);
                  break;
                  
               case Receiver:
                  Receiver.processPDU(packet, pduCounter);
                  break;

               case Remove_Entity:
                  RemoveEntity.processPDU(packet, pduCounter);
                  break;

               case Repair_Complete:
                  RepairComplete.processPDU(packet, pduCounter);
                  break;

               case Repair_Response:
                  RepairResponse.processPDU(packet, pduCounter);
                  break;

               case Resupply_Cancel:
                  ResupplyCancel.processPDU(packet, pduCounter);
                  break;

               case Resupply_Offer:
                  ResupplyOffer.processPDU(packet, pduCounter);
                  break;

               case Resupply_Received:
                  ResupplyReceived.processPDU(packet, pduCounter);
                  break;

               case Service_Request:
                  ServiceRequest.processPDU(packet, pduCounter);
                  break;

               case Set_Data:
                  SetData.processPDU(packet, pduCounter);
                  break;

               case Signal:
                  Signal.processPDU(packet, pduCounter);
                  break;

               case Start_Resume:
                  StartResume.processPDU(packet, pduCounter);
                  break;

               case Stop_Freeze:
                  StopFreeze.processPDU(packet, pduCounter);
                  break;

               case Transmitter:
                  Transmitter.processPDU(packet, pduCounter);
                  break;

               default:
                  Default.processPDU(packet, pduCounter);
                  break;
               }
            }
         }
      }
   }
}
