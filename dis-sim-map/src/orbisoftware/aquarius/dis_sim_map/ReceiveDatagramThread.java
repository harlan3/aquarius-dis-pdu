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
import java.io.IOException;
import java.beans.*;

public class ReceiveDatagramThread extends Thread {

	private final int MAX_PACKET_SIZE = 1500;
    private static DatagramSocket datagramSocket = null;
    private static MulticastSocket multicastSocket = null;
    private PropertyChangeSupport propertyChangeSupport;
    
    private int portNumber = 0;
	private boolean threadIsActive = false;
	private boolean useMulticast = false;
		
	public ReceiveDatagramThread() {
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}

	public void setThreadIsActive(boolean threadIsActive) {
		this.threadIsActive = threadIsActive;
	}

	private void initSocket() {

		useMulticast = Boolean.parseBoolean(MainApplication.getInstance().xmlMap.get("UseMulticast"));
		portNumber = Integer.parseInt(MainApplication.getInstance().xmlMap.get("PortValue"));
		
		if (useMulticast) {
			
			try {

				InetAddress multicastAddress = InetAddress.getByName(MainApplication.getInstance().xmlMap.get("MulticastAddress"));
				InetAddress multicastDeviceAddress = InetAddress.getByName(MainApplication.getInstance().xmlMap.get("MulticastDeviceAddress"));
				multicastSocket = new MulticastSocket(portNumber);
				
            	// Explicitly join the group on the specified interface
            	NetworkInterface netIf = NetworkInterface.getByInetAddress(multicastDeviceAddress);
            	multicastSocket.setNetworkInterface(netIf);
            	multicastSocket.joinGroup(new InetSocketAddress(multicastAddress, portNumber), netIf);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			try {
				datagramSocket = new DatagramSocket(portNumber);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void run() {

		byte[] buffer = new byte[MAX_PACKET_SIZE];

		initSocket();

		while (true) {

			if (threadIsActive) {
				
				DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);

				try {
					if (useMulticast) {
						multicastSocket.receive(incoming);
					} else {
						datagramSocket.receive(incoming);
					}

					propertyChangeSupport.firePropertyChange("datagramReceived", 0, incoming);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) { }
		}
	}
}
