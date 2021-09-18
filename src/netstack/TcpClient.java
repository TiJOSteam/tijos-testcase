package netstack;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.Socket;

import tijos.framework.networkcenter.dns.TiDNS;
import tijos.framework.platform.lan.TiLAN;
import tijos.framework.platform.lpwan.TiNBIoT;
import tijos.framework.platform.lte.TiLTE;
import tijos.framework.platform.network.NetworkInterfaceManager;
import tijos.framework.platform.wlan.TiWiFi;
import tijos.framework.util.Delay;

/**
 * TCP client 例程， 在运行时请设置正确的TCP Server IP地址
 *
 * @author TiJOS
 */

class TCPThread extends Thread {

	int tcpPort ; 
	public TCPThread(int port) {
		this.tcpPort = port;
	}
	
	
	@Override
	public void run() {
		// TCP服务器IP及PORT
		String host = "tcp.ticloud.io";
		int port = this.tcpPort;
		Socket client = null;
		try {
			// Connect to the server with TCP
			client = new Socket(host, port);
			System.out
					.println("Connect : " + client.getRemoteSocketAddress() + " local " + client.getLocalSocketAddress());

			OutputStream output = client.getOutputStream();
			
			String test = "<South><MsgType>1</MsgType><RC>0.198</RC><Dimness>0.1179021</Dimness><PHValue>6.729731</PHValue><AN>12.65</AN><UD>20210908162640</UD><Temperature>29.54486</Temperature><XParam>0.0</XParam><EquID>10000000092</EquID><ChannelID>0</ChannelID><ErrorMsg>0</ErrorMsg></South>";
			output.write(test.getBytes());

			// Send data to the TCP server
			output.write(("Hello, this is client " + this.tcpPort).getBytes());
			output.flush();

			// Get remote data from the server
			InputStream input = client.getInputStream();

			byte[] buffer = new byte[1024];
			while (true) {
				int len = -1;
				len = input.read(buffer);

				if (len > 0) {
					System.out.println("message form server:" + new String(buffer, 0, len) + this.tcpPort);

					// echo to the server
					output.write(buffer, 0, len);
					output.write(test.getBytes());
				}
				Delay.msDelay(1000);
			}

		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}
}

public class TcpClient {

	public static void main(String[] args) {

		try {
			System.out.println("start nb.");
			
			NetworkInterfaceManager.getNetworkInstance().startup(30);
			
			System.out.println(TiLAN.getInstance().getLocalIP());
			
			new TCPThread(9876).start();
			Delay.msDelay(20000);
//			new TCPThread(9877).start();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
