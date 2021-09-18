
package netstack;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import tijos.framework.platform.TiSettings;
import tijos.framework.platform.lpwan.TiNBIoT;
import tijos.framework.platform.lte.TiLTE;
import tijos.framework.platform.network.NetworkInterface;
import tijos.framework.platform.network.NetworkInterfaceManager;
import tijos.framework.util.Delay;

/**
 * UDP 例程, 运行之前请设置正确的IP地址
 *
 * @author TiJOS
 */

class UdpThread extends Thread {

	int svrPort = 0;

	public UdpThread(int port) {
		this.svrPort = port;
	}

	@Override
	public void run() {
		DatagramSocket udpSocket = null;
		DatagramPacket sdp = null;
		byte[] buffer = new byte[1024];
		try {
			udpSocket = new DatagramSocket();
			String host = "tcp.ticloud.io";
			int port = svrPort;

			String msg = "{\"dkey\":\"869098043450224\",\"msgtype\":\"device\",\"rssi\":17,\"temperature\":31.1,\"duration\":0,\"switch\":0,\"voltageB\":0,\"currentC\":0,\"voltageC\":0,\"hardware\":2,\"ci\":33957673,\"imsi\":\"460046428406403\",\"currentB\":0,\"ver\":2,\"lasterr\":\"0(ok)\",\"energy\":36.52,\"current\":0.001,\"txerr\":1,\"power\":0,\"time\":\"20200910151710\",\"rsrp\":-86,\"rst\":0,\"voltage\":237.4,\"snr\":12,\"tx\":14,\"pkey\":\"tielemeter\",\"sn\":\"200725148502\"}";
			
			System.out.println(msg);
			sdp = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getByName(host), port);
			udpSocket.send(sdp);

			while (true) {
				DatagramPacket rdp = new DatagramPacket(buffer, buffer.length);
				try {
					udpSocket.receive(rdp);
					String info = new String(rdp.getData(), 0, rdp.getLength());
					System.out.println("port " + port + " Received: " + info);
					System.out.println("port " + port + " Remote :" + rdp.getAddress().getHostAddress());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				udpSocket.send(sdp);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			udpSocket.close();
		}
	}
}

public class UdpDemo {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.out.println("UdpDemo start...");
		try {
			System.out.println("start lte.");
		
			NetworkInterface ni = NetworkInterfaceManager.getNetworkInstance();
			ni.startup(30);

			
			new UdpThread(9876).start();
			//new UdpThread(9877).start();

			Delay.msDelay(20000);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}