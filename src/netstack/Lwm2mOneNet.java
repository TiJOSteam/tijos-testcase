package netstack;

import java.io.IOException;

import tijos.framework.networkcenter.lwm2m.ILwM2MMessageListener;
import tijos.framework.networkcenter.lwm2m.LwM2MClient;
import tijos.framework.networkcenter.lwm2m.ResourceValue;
import tijos.framework.platform.TiPower;
import tijos.framework.platform.lpwan.TiNBIoT;
import tijos.framework.util.Delay;

class OneNetMessageListener implements ILwM2MMessageListener {

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
		
//		if (instance) {
//			ResourceValue[] rvs = new ResourceValue[2];
//			double dval = Math.random();
//			rvs[0] = new ResourceValue(5603, dval);
//			rvs[1] = new ResourceValue(5604, dval + 200);
//
//			return rvs;
//		} else {
//			ResourceValue[] rvs = new ResourceValue[1];
//			double dval = Math.random() * 100;
//
//			if (uri.equals("/3301/1/5603")) {
//				rvs[0] = new ResourceValue(5603, dval);
//			} else {
//				rvs[0] = new ResourceValue(5604, dval + 200);
//			}
//			return rvs;
//		}

//		ResourceValue [] rvs = new ResourceValue[1];
//		rvs[0] = new ResourceValue(0, new byte[] { 0, 1, 1, 6, 0, 1 });
//		return rvs;
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

public class Lwm2mOneNet {

	public static void main(String[] args) {
		try {
			//String URI_REPORT = "/19/0/0";
			String URI_REPORT = "/3300/0/5750";
			
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
			
			System.out.println("imsi " + imsi);

			endpoint = endpoint + ";" + imsi;

			System.out.println("endpoint : " + endpoint);
	
			LwM2MClient.getInstance().connect("coap://183.230.40.40:5683", 0, endpoint, 0, 86400,
					new LWM2MMessageListener());

			LwM2MClient.getInstance().addResource(URI_REPORT, 8);
//			LwM2MClient.getInstance().addResource("/19/0/0", 8);
//			LwM2MClient.getInstance().addResource("/19/1/0", 8);

			String data = "{\"msg\":\"ok\",\"ver\":0,\"tx\":1,\"wakeup\":1,\"ip\":\"100.95.40.205\",\"imsi\":\"460040519009267\",\"battery\":0,\"duration\":0,\"dkey\":\"869531030020436\",\"alarm\":1,\"arming\":1,\"sensor\":1,\"interval\":1,\"sn\":\"869531030020436\",\"time\":\"2019/9/16 17:26:11\",\"txerr\":1,\"state\":1,\"open\":0,\"retry\":1,\"lasterr\":\"lasterror\"}";

			
			LwM2MClient.getInstance().setResourceValue(URI_REPORT, data);
//			LwM2MClient.getInstance().addStdObject(3);
//			LwM2MClient.getInstance().setResourceValue("/3/0/0", "ticloud");
//			LwM2MClient.getInstance().setResourceValue("/3/0/1", "ticover");
//			LwM2MClient.getInstance().setResourceValue("/3/0/2", "SN12345678");
//			LwM2MClient.getInstance().setResourceValue("/3/0/3", "v1.0");
//			LwM2MClient.getInstance().setResourceValue("/4/0/8", TiNBIoT.getInstance().getCI());

			LwM2MClient.getInstance().start();

			int loop = 40;
			while (LwM2MClient.getInstance().getRegisterStatus() != 5 && loop-- > 0) {
				Delay.msDelay(1000);
			}

			if (LwM2MClient.getInstance().getRegisterStatus() != 5)
				throw new IOException("Not registered.");

			loop = 10;
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

			LwM2MClient.getInstance().disconnect();
		//	Delay.msDelay(6000);
			
			System.out.println("exiting");
		//	System.exit(0);
			Delay.msDelay(3000);
			TiPower.getInstance().shutdown(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Delay.msDelay(5000);
			System.exit(0);
		}

	}
}
