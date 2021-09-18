package netstack;

import tijos.framework.networkcenter.coap.CoAPClient;
import tijos.framework.networkcenter.coap.ICoAPMessageListener;
import tijos.framework.platform.TiPower;
import tijos.framework.platform.lan.TiLAN;
import tijos.framework.platform.lpwan.TiNBIoT;
import tijos.framework.platform.lte.TiLTE;
import tijos.framework.platform.util.SharedBuffer;
import tijos.framework.util.Delay;
import tijos.framework.util.LittleBitConverter;
import tijos.framework.util.json.JSONObject;


class MessageListener implements ICoAPMessageListener
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

public class coapunicom {

	public static void main(String[] args) {
		
		try {
			TiNBIoT.getInstance().startup(60);
			
			String uri_data = "/mqtt/device/869975039463656/up?c=869975039463656&u=869975039463656&p=869975039463656";
			String uri_notify = "/mqtt/device/869975039463656/down?c=869975039463656&u=869975039463656&p=869975039463656";
			String content = "0_183_95_867352040190339_1_0_200721172259_UnicomContact-NB";
			
			JSONObject jobj = new JSONObject();
			jobj.put("reportingData", content);

			String url = "coap://101.71.51.223:5683";
		
			CoAPClient coapClient = CoAPClient.getInstance();
					
			coapClient.connect(url);
			
			coapClient.setMessageListener(new MessageListener());
			
			coapClient.ping();
			
			coapClient.observe(uri_notify, CoAPClient.APPLICATION_JSON);
			
			//Wait for all messages are processed.
			int count = 20;
			while(count -- > 0) {
				coapClient.put(uri_data, CoAPClient.APPLICATION_JSON, jobj.toString().getBytes());
				Delay.msDelay(1000);
				System.out.println("Busy ..");
			}
			
			System.out.println("ok, disconnect and enter standby");
			coapClient.disconnect();
			
			//TiPower.getInstance().standby(0); //ignored if PSM is enabled

		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
	}

}
