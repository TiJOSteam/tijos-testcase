package ota;


import java.io.IOException;
import java.net.Socket;

import tijos.framework.appcenter.TiAPP;
import tijos.framework.appcenter.TiAPPManager;
import tijos.framework.platform.lte.TiLTE;
import tijos.framework.platform.network.NetworkInterface;
import tijos.framework.platform.network.NetworkInterfaceManager;
import tijos.framework.util.Delay;
import tijos.framework.util.Formatter;
import tijos.framework.util.logging.Logger;

/**

 * @author
 *
 */
public class OTACrash {

	public static void main(String[] args) {
		try {
			System.out.println("start network ....");

			try {				
				NetworkInterfaceManager.getNetworkInstance().startup(30);
				
			} catch (Exception e1) {
				System.out.println("start network error:" + e1.getMessage());
			}		

			while (NetworkInterfaceManager.getNetworkInstance().getNetworkStatus() != NetworkInterface.NETSTATUS_CONNECTED) {
				Delay.sDelay(30);
			}

			Socket client = new Socket("tcp.ticloud.io", 9876);
			byte[] byteMsg = Formatter.hexStringToByte(
					"f005000c997a0001e001906873905614988700414f54533031412d4130303031323032312d30372d3231445455303141303138363837333930353631343938383738393836303437373031323037303834393930305cfb");
			client.getOutputStream().write(byteMsg, 0, byteMsg.length);
	
			client.close();
			System.out.println("Application is started.");
			
			Delay.msDelay(2000);
		
			
//
//			//OtaApp.upgrade("http://download.raintech.net/device/ots/OTS01A-A0001.tapk");
//			
			//获取当前应用ID
			TiAPP currApp = TiAPPManager.getInstance().getRunningAPP();
			int oldAppId = currApp.getId();
//
			//OTA 应用名称
			String otaAppName = "tijos-http-ota";

			//获取OTA应用
			TiAPP otaApp = TiAPPManager.getInstance().getAPP(otaAppName);
			if (otaApp == null) {
				throw new IOException("OTA App is not found: " + otaAppName);
			}

			String otaAppUrl = "http://img.tijos.net/img/tiwl-aep.tapk";
			String appArgs = oldAppId + " " + otaAppUrl;
	
			//
			otaApp.execute(true, appArgs);

			while(true) {
				Delay.msDelay(1000);
			}
			
//			while (true) {
//				if (SystemConfig.stoped) {
//					OtaApp.upgrade("http://comoss.raintech.net/ota/9001/OTS01A-A0001.tapk");
//				} else {
//					Delay.sDelay(2);
//					SystemConfig.stoped = true;
//				}
//			}
//
		} catch (Exception e) {
			Logger.error("Application error: " + e.getMessage());
		}
	}

}
