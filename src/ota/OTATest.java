package ota;

import java.io.IOException;

import tijos.framework.appcenter.TiAPP;
import tijos.framework.appcenter.TiAPPManager;
import tijos.framework.platform.lpwan.TiNBIoT;
import tijos.framework.platform.lte.TiLTE;
import tijos.framework.platform.network.NetworkInterfaceManager;

public class OTATest {

	public static void main(String[] args) {

		try {

			NetworkInterfaceManager.getNetworkInstance().startup(30);
			
			//获取当前应用ID
			TiAPP currApp = TiAPPManager.getInstance().getRunningAPP();
			int oldAppId = currApp.getId();

			System.out.println("oldapp " + oldAppId);
			//OTA 应用名称
			String otaAppName = "tijos-http-ota";

			//获取OTA应用
			TiAPP otaApp = TiAPPManager.getInstance().getAPP(otaAppName);
			if (otaApp == null) {
				throw new IOException("OTA App is not found: " + otaAppName);
			}

			String otaAppUrl = "http://img.tijos.net/img/tiwl-cat1-mqtt_v4.tapk";
			
			String appArgs = oldAppId + " " + otaAppUrl;
			
			System.out.println("appaargs "  + appArgs);
			//
			otaApp.execute(true, appArgs);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
