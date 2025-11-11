package orbisoftware.aquarius.pdu_common;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

public class SharedApplicationData {

   private static SharedApplicationData instance = null;
   
   private static DatagramSocket datagramSocket = null;
   private static MulticastSocket multicastSocket = null;
   
   public static SharedApplicationData getInstance() {

      if (instance == null) {
         instance = new SharedApplicationData();
      }
      return instance;
   }
   
   public MulticastSocket getMulticastSocket() {
      
      return multicastSocket;
   }
   
   public DatagramSocket getDatagramSocket() {
      
      return datagramSocket;
   }
   
   public void initSocket() {

      boolean useMulticast = Boolean.parseBoolean(MainApplication.getInstance().xmlMap.get("UseMulticast"));
      int portNumber = Integer.parseInt(MainApplication.getInstance().xmlMap.get("PortValue"));
      
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
}
