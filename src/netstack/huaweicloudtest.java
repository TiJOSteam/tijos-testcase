package netstack;

import java.io.IOException;

import tijos.framework.networkcenter.huawei.client.AbstractOceanConnectEventHandler;
import tijos.framework.networkcenter.huawei.client.IotResult;
import tijos.framework.networkcenter.huawei.client.OceanConnect;
import tijos.framework.networkcenter.huawei.device.Command;
import tijos.framework.networkcenter.huawei.device.CommandRsp;
import tijos.framework.networkcenter.huawei.device.DeviceMessage;
import tijos.framework.networkcenter.huawei.device.PropsSet;
import tijos.framework.networkcenter.huawei.device.ServiceProperty;
import tijos.framework.platform.lte.TiLTE;
import tijos.framework.platform.network.NetworkInterfaceManager;
import tijos.framework.util.Delay;


class MyEventHandler extends AbstractOceanConnectEventHandler 
{
	OceanConnect oc;
	
	public MyEventHandler(OceanConnect oc)
	{
		this.oc = oc;
	}
	@Override
	public void onCommand(String requestId, Command command) {
		System.out.println("onCommand " + requestId);	
		
		CommandRsp rsp = new CommandRsp(CommandRsp.SUCCESS);
		System.out.println("rsp " + rsp.toString());
		try {
			oc.respondCommand(requestId, rsp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onPropertiesSet(String requestId, PropsSet propsSet) {
		System.out.println("onPropertiesSet");	
		
		try {
			oc.respondPropsSet(requestId, IotResult.SUCCESS);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onPropertiesGet(String requestId, String deviceId, String serviceId) {
		System.out.println("onPropertiesGet");				
	}
	
}

public class huaweicloudtest {

	public static void main(String[] args) {

		try {
			
			NetworkInterfaceManager.getNetworkInstance().startup(30);
			

			String deviceId = "5fd3232ccbfe2f02ce6d4d42_867435057486946";
			String secret ="867435057486946";

			OceanConnect oc = OceanConnect.getInstance();
			oc.start(deviceId, secret, new MyEventHandler(oc));

//			ServiceProperty sp = new ServiceProperty("Connectivity");
//			sp.setProperty("rssi", TiLTE.getInstance().getRSSI());
//			sp.setProperty("cellId", TiLTE.getInstance().getCellInfo().getCI());
//			sp.setEventTime(System.currentTimeMillis());
//			
//			oc.reportProperties(sp);
			
			oc.reportDeviceMessage(new DeviceMessage("hello"));
			while(true)
			{
				Delay.msDelay(10000);
				System.out.println("running");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
