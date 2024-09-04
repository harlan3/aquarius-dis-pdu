package companion;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Computations {

    // WGS84 ellipsoid constants
    private static final double a = 6378137.0; // Semi-major axis (meters)
    private static final double f = 1 / 298.257223563; // Flattening
    private static final double b = a * (1 - f); // Semi-minor axis (meters)
    
    public double[] convertGeodeticToGeocentric(double latitude, double longitude, double altitude) {
    	
        // Convert latitude and longitude from degrees to radians
        double latRad = Math.toRadians(latitude);
        double lonRad = Math.toRadians(longitude);

        // Calculate the radius of curvature in the prime vertical
        double N = a / Math.sqrt(1 - (f * (2 - f)) * Math.sin(latRad) * Math.sin(latRad));

        // Calculate X, Y, Z
        double X = (N + altitude) * Math.cos(latRad) * Math.cos(lonRad);
        double Y = (N + altitude) * Math.cos(latRad) * Math.sin(lonRad);
        double Z = ((1 - f) * (1 - f) * N + altitude) * Math.sin(latRad);

        return new double[]{X, Y, Z};
    }
    
    public String doubleToHexWithSpaces(double value) {
    	
        // Convert double to long bits
        long bits = Double.doubleToRawLongBits(value);

        // Create a StringBuilder to hold the hex string
        StringBuilder hexString = new StringBuilder();

        // Iterate through each byte of the long
        for (int i = 7; i >= 0; i--) {
            int byteValue = (int) ((bits >> (i * 8)) & 0xFF); // Extract the byte
            hexString.append(String.format("%02X", byteValue)); // Convert to hex and append

            if (i > 0) {
                hexString.append(" "); // Add space between bytes
            }
        }
        
        return hexString.toString();
    }
    
    public String floatToHexWithSpaces(float value) {
    	
        // Convert float to int bits
        int bits = Float.floatToIntBits(value);

        // Create a StringBuilder to hold the hex string
        StringBuilder hexString = new StringBuilder();

        // Iterate through each byte of the int
        for (int i = 3; i >= 0; i--) {
            int byteValue = (bits >> (i * 8)) & 0xFF; // Extract the byte
            hexString.append(String.format("%02X", byteValue)); // Convert to hex and append

            if (i > 0) {
                hexString.append(" "); // Add space between bytes
            }
        }

        return hexString.toString();
    }
    
    public String asciiToHexWithSpaces(String text) {
    	
        StringBuilder hexString = new StringBuilder();

        // Convert each character to hex
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            hexString.append(String.format("%02X", (int) ch)); // Convert to hex and append

            if (i < text.length() - 1) {
                hexString.append(" "); // Add space between bytes
            }
        }

        return hexString.toString();
    }
    
    public String byteToHex(int value) {
    	
        int unsignedValue = value & 0xFF; // Convert the byte to an unsigned int
        return String.format("%02X", unsignedValue);
    }
    
    public String shortToHexWithSpaces(int value) {
    	
        StringBuilder hexString = new StringBuilder();

        // Convert each byte to hex and append
        for (int i = 1; i >= 0; i--) {
            int byteValue = (value >> (i * 8)) & 0xFF; // Extract the byte
            hexString.append(String.format("%02X", byteValue)); // Convert to hex and append

            if (i > 0) {
                hexString.append(" "); // Add space between bytes
            }
        }

        return hexString.toString();
    }
    
    public String intToHexWithSpaces(long value) {
    	
        StringBuilder hexString = new StringBuilder();

        // Convert each byte to hex and append
        for (int i = 3; i >= 0; i--) {
            long byteValue = (value >> (i * 8)) & 0xFF; // Extract the byte
            hexString.append(String.format("%02X", byteValue)); // Convert to hex and append

            if (i > 0) {
                hexString.append(" "); // Add space between bytes
            }
        }

        return hexString.toString();
    }
    
    public String longToHexWithSpaces(long value) {
    	
        // Convert long to BigInteger
        BigInteger bigInt = BigInteger.valueOf(value);

        // Get the hexadecimal string and ensure it has 16 characters by padding with leading zeros
        String hexString = String.format("%016X", bigInt);

        // Insert spaces between each pair of hexadecimal digits
        StringBuilder hexWithSpaces = new StringBuilder();
        for (int i = 0; i < hexString.length(); i += 2) {
            hexWithSpaces.append(hexString, i, i + 2);
            if (i < hexString.length() - 2) {
                hexWithSpaces.append(" ");
            }
        }

        return hexWithSpaces.toString();
    }
    
    public String convertSeptupleToHex(String septuple) {
    	
        String[] numbers = septuple.split(":");
        StringBuilder hexString = new StringBuilder();

        int i = 0;
        
        while (i < numbers.length) {
        	
        	if (i != 2) {
	            int number = Integer.parseInt(numbers[i]); // Convert each part to an integer
	            hexString.append(String.format("%02X", number)); // Convert to hex and append
	
	            if (i < numbers.length - 1) {
	                hexString.append(" "); // Add space between hex values
	            }
	            
        	} else { // two bytes for country code
        		
        		short data[] = new short[2];
        		short number = Short.parseShort(numbers[i]);
        		data[0] = (byte) number;
        		data[1] = (byte) (number >>> 8);
        	
	            hexString.append(String.format("%02X", data[1]));
	            hexString.append(" "); // Add space between hex values
	            hexString.append(String.format("%02X", data[0]));
	            hexString.append(" "); // Add space between hex values
        	}
        	
        	i++;
        }

        return hexString.toString();
    }
}
