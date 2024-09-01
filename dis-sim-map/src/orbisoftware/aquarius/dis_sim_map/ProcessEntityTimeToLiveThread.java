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

import java.util.Map;

import uk.ac.leeds.ccg.geotools.DisLayer;
import uk.ac.leeds.ccg.geotools.DisObject;

public class ProcessEntityTimeToLiveThread extends Thread {

	public void run() {

		while (true) {

			DisLayer disLayer = DisLayer.getInstance();

			for (Map.Entry<Integer, DisObject> entry : disLayer.disObjectHashMap.entrySet()) {

				Integer key = entry.getKey();
				DisObject disObject = entry.getValue();

				// Remove entity when time to live reaches 0
				disObject.timeToLive--;

				if (disObject.timeToLive <= 0) {

					disLayer.disObjectHashMap.remove(key);
					
					if (MainApplication.getInstance().model.contains((disObject.entityHashID) + "::" + disObject.entityMarking))
						MainApplication.getInstance().model.removeElement((disObject.entityHashID) + "::" + disObject.entityMarking);

				} else
					disLayer.disObjectHashMap.put(key, disObject);
			}

			// Request repaint once a second so that it will scale better with many entities
			disLayer.requestRepaint();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) { }
		}
	}
}
