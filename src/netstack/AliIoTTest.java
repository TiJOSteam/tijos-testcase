package netstack;

import tijos.framework.networkcenter.alibaba.AliLinkSubDevice;
import tijos.framework.networkcenter.alibaba.AliYunIoT;
import tijos.framework.networkcenter.alibaba.IDataModelEventListener;
import tijos.framework.networkcenter.alibaba.ISubDeviceEventListener;
import tijos.framework.platform.lan.TiLAN;
import tijos.framework.platform.lpwan.TiNBIoT;
import tijos.framework.platform.lte.TiLTE;
import tijos.framework.platform.network.NetworkInterface;
import tijos.framework.platform.network.NetworkInterfaceManager;
import tijos.framework.util.Delay;

class AliListener implements IDataModelEventListener {
	@Override
	public void onGenericReplyArrived(String product, String deviceName, long msgId, int code, String message,
			byte[] data) {
		System.out.println("onGenericReplyArrived  " + product + " " + deviceName + " mid " + msgId + " code " + code
				+ " message " + message + " " + data);
	}

	@Override
	public void onPropertySetArrived(String product, String deviceName, long msgId, String params) {
		System.out.println("onPropertySetArrived ");

	}

	@Override
	public void onAsyncServiceInvokeArrived(String product, String deviceName, long msgId, String serviceId,
			String params) {
		System.out.println("onAsyncServiceInvokeArrived ");

		AliYunIoT aliiot = AliYunIoT.getInstance();
	}

	@Override
	public void onSyncServiceInvokeArrived(String product, String deviceName, long msgId, String rrpdId,
			String serviceId, String params) {
		System.out.println("onSyncServiceInvokeArrived ");

	}

	@Override
	public void onRawDataArrived(String product, String deviceName, byte[] rawData) {
		System.out.println("onRawDataArrived ");

	}

	@Override
	public void onRawServiceInvokeArrived(String product, String deviceName, String rrpcId, byte[] data) {
		System.out.println("onRawServiceInvokeArrived ");

	}

}


class SubDevEvent implements ISubDeviceEventListener
{

	@Override
	public void onTopoGetReply(String message, long msgId, int code, AliLinkSubDevice[] devices) {
		
		System.out.println("onTopoGetReply");
		
		for (AliLinkSubDevice dev : devices) {
			System.out.println("------");

			System.out.println(dev.productKey);
			System.out.println(dev.productSecret);
			System.out.println(dev.deviceName);
			System.out.println(dev.deviceSecret);
		}		
	}

	@Override
	public void onTopoAddReply(String message, long msgId, int code, AliLinkSubDevice[] devices) {
		System.out.println("onTopoAddReply code " + code);		
		
	}

	@Override
	public void onTopoDeleteReply(String message, long msgId, int code, AliLinkSubDevice[] devices) {
		System.out.println("onTopoDeleteReply code " + code);		
		
	}

	@Override
	public void onBatchLoginReply(String message, long msgId, int code, AliLinkSubDevice[] devices) {
		System.out.println("onBatchLoginReply code " + code);		
		
	}

	@Override
	public void onBatchLogoutReply(String message, long msgId, int code, AliLinkSubDevice[] devices) {
		System.out.println("onBatchLogoutReply code " + code);		
		
	}

	@Override
	public void onRegisterReply(String message, long msgId, int code, AliLinkSubDevice[] devices) {
		System.out.println("onRegisterReply code " + code);		
		for (AliLinkSubDevice dev : devices) {
			System.out.println("------");

			System.out.println(dev.productKey);
			System.out.println(dev.productSecret);
			System.out.println(dev.deviceName);
			System.out.println(dev.deviceSecret);
		}		
	}

	@Override
	public void onTopoChangeEvent(long msgId, int status, AliLinkSubDevice[] devices) {
		System.out.println("onDeviceTopologyChangeEvent type " + " status " + status);

		for (AliLinkSubDevice dev : devices) {
			System.out.println("------");
			System.out.println(dev.productKey);
			System.out.println(dev.productSecret);
			System.out.println(dev.deviceName);
			System.out.println(dev.deviceSecret);

		}
	}
	
}

public class AliIoTTest {
	public static void main(String[] args) {

		try {

			NetworkInterface network = NetworkInterfaceManager.getNetworkInstance();
			network.startup(30);
			
			AliYunIoT aliiot = AliYunIoT.getInstance();

			String serverUrl = "mqtt://a1QJjmusiPI.iot-as-mqtt.cn-shanghai.aliyuncs.com:443"; /* 阿里云平台上海站点的域名后缀 */
			
			String ProductKey = "a1QJjmusiPI";
			String productSecret = "RfGwt2F0050gC7CV";
			String DeviceName = "gwdemo2";
			String DeviceSecret = "71fab7d64714233e8757181ab6c9e28b";
			
			aliiot.start(serverUrl, ProductKey, DeviceName, DeviceSecret, new AliListener());
			aliiot.setSubDeviceEventListener(new SubDevEvent());
			

			
			AliLinkSubDevice devs [] = new AliLinkSubDevice[1];
			devs[0]= new AliLinkSubDevice();
			devs[0].productKey = "a1rSbMByXZK";
			devs[0].productSecret = "JUbAKYOXNYQpPpJc";
			devs[0].deviceName = "subdev3";
			
			aliiot.registerSubDevices(devs);
			
			int mid = aliiot.propertyPost("{\"LightStatus\": 0}");
			
			aliiot.getDesiredRequest("[\"LightSwitch\"]");
			aliiot.deleteDesiredRequset("{\"LightSwitch\":{}}");
//			System.out.println("mid " + mid);
//			mid = aliiot.eventPost("Error", "{\"ErrorCode\": 0}");
//			System.out.println("mid " + mid);
			
			aliiot.getSubDevices();

			 while(true)
			{
				// mid = aliiot.propertyPost("{\"LightSwitch\": 0}");
				// System.out.println("mid " + mid);
				Delay.msDelay(10000);
			}

//			
//			aliiot.stop();

//			System.exit(0);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
