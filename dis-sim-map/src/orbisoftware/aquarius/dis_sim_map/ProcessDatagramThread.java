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

package orbisoftware.aquarius.dis_sim_map;

import java.net.*;
import java.beans.*;

import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import uk.ac.leeds.ccg.geotools.DisLayer;
import uk.ac.leeds.ccg.geotools.DisObject;

import java.beans.PropertyChangeListener;

public class ProcessDatagramThread extends Thread implements PropertyChangeListener {

	private ReceiveDatagramThread receiveDatagramThread = new ReceiveDatagramThread();
	private BlockingQueue<DatagramPacket> queue = new LinkedBlockingQueue<DatagramPacket>();
	private boolean threadIsActive = false;
	
	public void propertyChange(PropertyChangeEvent evt) {

		byte[] buffer = new byte[((DatagramPacket) evt.getNewValue()).getLength()];

		if (evt.getPropertyName().toString().equals("datagramReceived")) {

			synchronized (queue) {

				// Make a copy of the datagram packet, as the referenced object
				// is owned by the other thread.
				System.arraycopy(((DatagramPacket) evt.getNewValue()).getData(), 0, buffer, 0, buffer.length);

				DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);

				queue.add(receivedPacket);
				queue.notify();
			}
		}
	}
	
	public void setThreadIsActive(boolean threadIsActive) {
		
		receiveDatagramThread.setThreadIsActive(threadIsActive);
		this.threadIsActive = threadIsActive;
	}

	public void run() {

		PDULoggerConfig pduLoggerConfig = PDULoggerConfig.getInstance();

		receiveDatagramThread.getPropertyChangeSupport().addPropertyChangeListener(this);
		receiveDatagramThread.start();

		while (true) {

			List<DatagramPacket> datagramList = new LinkedList<DatagramPacket>();
			int configuredDISExerciseID = pduLoggerConfig.getDISExerciseID();
			
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
				 * if the logger was configured for a specific DIS exercise then filter
				 * accordingly.
				 */
				if (configuredDISExerciseID != 0) {
					if (configuredDISExerciseID != packetExerciseID)
						continue;
				}

				switch (packetPDUType) {

				case Entity_State:
							
					EntityState entityState = new EntityState();
					DisObject newDisObject = new DisObject();
					DisObject currentDisObject;
					DisLayer disLayer = DisLayer.getInstance();
					entityState.processPDU(packet);
					
					currentDisObject = DisLayer.getInstance().disObjectHashMap.get(entityState.getEntityHash());
					
					newDisObject.entityHashID = entityState.getEntityHash();
					newDisObject.entityForceID = entityState.getEntityForceID();
					newDisObject.entityMarking = entityState.getEntityMarking();
					newDisObject.entityLocation = entityState.getEntityLatLonLocation();
					
					if (currentDisObject != null)
						newDisObject.isSelected = currentDisObject.isSelected;
					
					disLayer.disObjectHashMap.put(newDisObject.entityHashID, newDisObject);
					
					if (!MainApplication.getInstance().model.contains((newDisObject.entityHashID) + "::" + newDisObject.entityMarking))
						MainApplication.getInstance().model.addElement(Integer.toString(newDisObject.entityHashID) + "::" + newDisObject.entityMarking);
					
					break;
					
				default:
					break;
				}
			}
		}
	}
}
