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

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.net.DatagramPacket;

import uk.ac.leeds.ccg.geotools.GeocentricPoint;
import uk.ac.leeds.ccg.geotools.LatLonPoint;

public class EntityState {

	private short entIDSiteNum = 0;
	private short entIDAppNum = 0;
	private short entIDEntNum = 0;
	private byte forceID = 0;
	double entityLocationX = 0;
	double entityLocationY = 0;
	double entityLocationZ = 0;
	
	private StringBuffer entityMarking = new StringBuffer(11);

	public int getEntityHash() {

		return cantorFunction(entIDSiteNum, cantorFunction(entIDAppNum, entIDEntNum));
	}
	
	public int getEntityForceID() {
		
		return forceID;
	}

	public String getEntityMarking() {
		
		return entityMarking.toString();
	}
	
	public LatLonPoint getEntityLatLonLocation() {
		
		GeocentricPoint geocentricPoint = new GeocentricPoint(entityLocationX, entityLocationY, entityLocationZ);
		
		return geocentricPoint.getLatLonPoint();
	}
	
	private int cantorFunction(int a, int b) {
		
		return (a + b + 1) * (a + b) / 2 + b;
	}

	public void processPDU(DatagramPacket packet) {

		ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(packet.getData());
		DataInputStream din = new DataInputStream(byteArrayStream);

		byte pduType = 0;
		byte family = 0;
		short length = 0;
		byte pduStatus = 0;
		byte numberVariableParams = 0;
		byte entTypeEntKind = 0;
		byte entTypeDomain = 0;
		short entTypeCountry = 0;
		byte entTypeCategory = 0;
		byte entTypeSubCategory = 0;
		byte entTypeSpecific = 0;
		byte entTypeExtra = 0;
		byte altEntityTypeEntKind = 0;
		byte altEntityTypeDomain = 0;
		short altEntityTypeCountry = 0;
		byte altEntityTypeCategory = 0;
		byte altEntityTypeSubCat = 0;
		byte altEntityTypeSpecific = 0;
		byte altEntityTypeExtra = 0;
		float entityLinearVelocityX = 0;
		float entityLinearVelocityY = 0;
		float entityLinearVelocityZ = 0;
		float entityOrientationPsi = 0;
		float entityOrientationTheta = 0;
		float entityOrientationPhi = 0;
		int entityAppearance = 0;
		byte deadReckParamsAlgor = 0;
		byte deadReckParamsOthers[] = new byte[15];
		float deadReckonEntityLinearAccelX = 0;
		float deadReckonEntityLinearAccelY = 0;
		float deadReckonEntityLinearAccelZ = 0;
		float deadReckonEntityAngularVelocityX = 0;
		float deadReckonEntityAngularVelocityY = 0;
		float deadReckonEntityAngularVelocityZ = 0;
		byte entityMarkingCharacterSet = 0;
		int capabilities = 0;

		try {

			/* Start Message Header */
			din.skipBytes(2);
			pduType = din.readByte();
			family = din.readByte();
			din.reset();

			din.skipBytes(8);
			length = din.readShort();
			pduStatus = din.readByte();
			din.reset();
			/* End Message Header */

			din.skipBytes(12);
			entIDSiteNum = din.readShort();
			entIDAppNum = din.readShort();
			entIDEntNum = din.readShort();
			forceID = din.readByte();
			numberVariableParams = din.readByte();
			entTypeEntKind = din.readByte();
			entTypeDomain = din.readByte();
			entTypeCountry = din.readShort();
			entTypeCategory = din.readByte();
			entTypeSubCategory = din.readByte();
			entTypeSpecific = din.readByte();
			entTypeExtra = din.readByte();
			altEntityTypeEntKind = din.readByte();
			altEntityTypeDomain = din.readByte();
			altEntityTypeCountry = din.readShort();
			altEntityTypeCategory = din.readByte();
			altEntityTypeSubCat = din.readByte();
			altEntityTypeSpecific = din.readByte();
			altEntityTypeExtra = din.readByte();
			entityLinearVelocityX = din.readFloat();
			entityLinearVelocityY = din.readFloat();
			entityLinearVelocityZ = din.readFloat();
			entityLocationX = din.readDouble();
			entityLocationY = din.readDouble();
			entityLocationZ = din.readDouble();
			entityOrientationPsi = din.readFloat();
			entityOrientationTheta = din.readFloat();
			entityOrientationPhi = din.readFloat();
			entityAppearance = din.readInt();
			deadReckParamsAlgor = din.readByte();
			deadReckParamsOthers = din.readNBytes(15);
			deadReckonEntityLinearAccelX = din.readFloat();
			deadReckonEntityLinearAccelY = din.readFloat();
			deadReckonEntityLinearAccelZ = din.readFloat();
			deadReckonEntityAngularVelocityX = din.readFloat();
			deadReckonEntityAngularVelocityY = din.readFloat();
			deadReckonEntityAngularVelocityZ = din.readFloat();
			entityMarkingCharacterSet = din.readByte();

			for (int x = 0; x < 11; x++) {

				byte currByte = din.readByte();

				if ((currByte >= ' ') && (currByte < 127))
					entityMarking.append((char) currByte);
			}

			capabilities = din.readInt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
