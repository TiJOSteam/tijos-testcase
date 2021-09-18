package lan;

import java.io.IOException;

import tijos.framework.platform.lan.TiLAN;
import tijos.framework.platform.network.INetworkEventListener;
import tijos.framework.platform.network.NetworkException;
import tijos.framework.util.Delay;


 class NetworkEvent implements INetworkEventListener {

	@Override
	public void onConnected() {
		// TODO Auto-generated method stub
		System.out.println("OnConnected");
	}

	@Override
	public void onDisconnected(int code, String message) {
		System.out.println("OnDisConnected");
		
	}
	 
 }

public class LanTest {

	public static void main(String[] args) throws IOException {

		TiLAN.getInstance().startup(30, new NetworkEvent());
		
		while(true) {
			System.out.println(TiLAN.getInstance().getLocalIP());
			Delay.msDelay(5000);
		}
		
		
		
		
	}

}
