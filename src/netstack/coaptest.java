package netstack;

import tijos.framework.networkcenter.coap.CoAPClient;
import tijos.framework.networkcenter.coap.ICoAPMessageListener;
import tijos.framework.platform.TiPower;
import tijos.framework.platform.TiSettings;
import tijos.framework.platform.lan.TiLAN;
import tijos.framework.platform.lpwan.TiNBIoT;
import tijos.framework.platform.lte.TiLTE;
import tijos.framework.platform.network.NetworkInterface;
import tijos.framework.platform.network.NetworkInterfaceManager;
import tijos.framework.platform.util.SharedBuffer;
import tijos.framework.util.Delay;
import tijos.framework.util.LittleBitConverter;


class CoAPMessageListener implements ICoAPMessageListener
{
	@Override
	public void getResponseArrived(String uri, int msgid,  boolean result, int msgCode, byte[] payload) {
		System.out.println("getResponseArrived: " + " uri " + uri + " msgid " + msgid + " result " + result + " msgCode " + msgCode);
		if(payload != null)
			System.out.println(new String(payload));
	}

	@Override
	public void postResponseArrived(String uri, int msgid, boolean result, int msgCode) {
		System.out.println("postResponseArrived: " + " uri " + uri + " msgid " + msgid + " result " + result + " msgCode " + msgCode);
	
	}

	@Override
	public void putResponseArrived(String uri, int msgid, boolean result, int msgCode) {
		System.out.println("putResponseArrived: " + " uri " + uri + " msgid " + msgid + " result " + result + " msgCode " + msgCode);
	
	}
	
	@Override
	public void deleteResponseArrived(String uri, int msgid, boolean result, int msgCode) {
		System.out.println("deleteResponseArrived: " + " uri " + uri + " msgid " + msgid + " result " + result + " msgCode " + msgCode);
		
	}

	@Override
	public void pingResponseArrvied(int msgid, boolean result, int msgCode) {
		System.out.println("pingResponseArrvied: " + " msgid " + msgid + " result " + result + " msgCode " + msgCode);
		
	}
}


public class coaptest {

	public static void main(String[] args) {
		
		try {
			
			String uri_data = "/topic/product_testing/device_testing/data";
			String uri_cmd = "/topic/product_testing/device_testing/cmd";
			String uri_notify = "/topic/product_testing/device_testing/notify";

			
			NetworkInterface network = NetworkInterfaceManager.getNetworkInstance();
			network.startup(30);
			
		//	TiSettings.getInstance().resetKernelLoggerLevel(0);
			
			String url = "coap://47.92.248.3:5683";
		
			CoAPClient coapClient = CoAPClient.getInstance();
					
			coapClient.connect(url);
			
			coapClient.setMessageListener(new CoAPMessageListener());
			
			coapClient.ping();
			
			//coapClient.observe(uri_notify, CoAPClient.APPLICATION_JSON);
			
			//Wait for all messages are processed.
			int count = 10;
			while(count -- > 0) {
				System.out.println("post " + count ) ;
				coapClient.post(uri_data, CoAPClient.APPLICATION_JSON, "this is a test".getBytes());
								
				System.out.println("get " + count ) ;
				coapClient.get(uri_data, CoAPClient.APPLICATION_JSON);
				
				Delay.msDelay(100);
								
				System.out.println("Busy .. " + coapClient.isBusy());
			}
			
			System.out.println("ok, disconnect and enter standby");
			
			Delay.msDelay(5000);
			coapClient.disconnect();
			
			//TiPower.getInstance().standby(0); //ignored if PSM is enabled

		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
	}

}
