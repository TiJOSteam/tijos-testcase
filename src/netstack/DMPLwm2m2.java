package netstack;


import java.io.IOException;

import tijos.framework.networkcenter.lwm2m.ILwM2MMessageListener;
import tijos.framework.networkcenter.lwm2m.LwM2MClient;
import tijos.framework.networkcenter.lwm2m.ResourceValue;
import tijos.framework.platform.lpwan.TiNBIoT;
import tijos.framework.platform.lte.TiLTE;
import tijos.framework.util.Delay;
import tijos.framework.util.Formatter;

class DMPMessageListener2 implements ILwM2MMessageListener {

	@Override
	public int writeRequestArrived(String uri, boolean instance, ResourceValue[] rvs) throws IOException {
		System.out.println("writeRequestArrived " + " uri:" + uri + " instance:" + instance);

		return LwM2MClient.MSG_CODE_204_CHANGED;
	}

	@Override
	public int executeRequestArrived(String uri, boolean instance, ResourceValue[] rvs) throws IOException {

		System.out.println("executeRequestArrived " + " uri:" + uri + " instance:" + instance);

		return LwM2MClient.MSG_CODE_204_CHANGED;
	}

	@Override
	public ResourceValue[] readRequestArrived(String uri, boolean instance) throws IOException {

		System.out.println("readRequestArrived " + " uri:" + uri + " instance:" + instance);
		
		return null;
	}

	@Override
	public int rebootRequestArrived() {
		System.out.println("rebootRequestArrived ");

		return 0;
	}

	@Override
	public void transactionEventArrived(String uri, boolean result) throws IOException {
		System.out.println("transactionEventArrived " + " uri:" + uri + " result:" + result);

	}

	
	@Override
	public void registerEventArrived(boolean registered) throws IOException {

		System.out.println("registerEventArrived " + " registered:" + registered);

	}

	@Override
	public void observeEventArrived(String uri, boolean observed) throws IOException {
		System.out.println("observeEventArrived " + " uri " + uri + " observed:" + observed);
	}

}

public class DMPLwm2m2 {

	public static void main(String[] args) {
		try {
			
			String URI_REPORT = "/19/0/0";

			System.out.println("starting.. v8");
			
			TiNBIoT.getInstance().startup(30);
			

			System.out.println("nbiot ready");

			String endpoint = TiNBIoT.getInstance().getIMEI();
			String imsi = "";
			try {
				imsi = TiNBIoT.getInstance().getIMSI();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			System.out.println("endpoint : " + endpoint);
	
			
			LwM2MClient.getInstance().connect("coap://dmp-coap.cuiot.cn:5683", 1, endpoint, 0, 86400,
					new DMPMessageListener2());
			
			LwM2MClient.getInstance().addResource(URI_REPORT, 8);

			String str = "{\"params\":{\"data\":[{\"value\":\"20\",\"key\":\"batteryLevel\"},{\"value\":\"200.1\",\"key\":\"batteryVoltage\"}]}}\r\n";
				
			byte [] data  = str.getBytes(); // Formatter.hexStringToByte("0000ff41bb3333000773756363657373000141bb3333");
			
			System.out.println(Formatter.toHexString(data));
			
			LwM2MClient.getInstance().setResourceValue(URI_REPORT,data);

			
			LwM2MClient.getInstance().start();

			int loop = 20;
			while (LwM2MClient.getInstance().getRegisterStatus() != 5 && loop-- > 0) {
				Delay.msDelay(1000);
			}

			if (LwM2MClient.getInstance().getRegisterStatus() != 5)
				throw new IOException("Not registered.");

			loop = 60;
			while (!LwM2MClient.getInstance().isResourceObserved(URI_REPORT) && loop-- > 0) {
				Delay.msDelay(1000);
			}

			if (!LwM2MClient.getInstance().isResourceObserved(URI_REPORT)) {
				throw new IOException("Not observed.");
			}

			System.out.println("connected.");

			String url = LwM2MClient.getInstance().getServerUrl();
			System.out.println("Server Url " + url);

			int mode = LwM2MClient.getInstance().getServerStartMode();
			System.out.println("start mode " + mode);

			loop = 20;
			while (loop-- > 0) {
				
				LwM2MClient.getInstance().setResourceValue(URI_REPORT, data);

				Delay.msDelay(5000);
			}
			
			int count = 0;
			while(count ++ < 4) {
				Delay.msDelay(2000);
				System.out.println("running");
			}

//			LwM2MClient.getInstance().disconnect();
//			
//			System.out.println("exiting");
//			Delay.msDelay(3000);
//			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Delay.msDelay(5000);
			System.exit(0);
		}

	}
}
