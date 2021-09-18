package lte;

import java.io.IOException;

import tijos.framework.platform.lte.TiLTE;
import tijos.framework.platform.lte.TiLTECSQ;
import tijos.framework.platform.lte.TiLTECell;
import tijos.framework.platform.network.INetworkEventListener;
import tijos.framework.platform.peripheral.TiLight;
import tijos.framework.util.Delay;


class LTEEvent implements  INetworkEventListener
{

	@Override
	public void onConnected() {
		System.out.println("LTE onConnected");
	}

	@Override
	public void onDisconnected(int code, String message) {
		System.out.println("LTE onDisconnected " + message);
		
	}
	
}

public class LTETest {

	public static void main(String[] args) {
		try {

			TiLTE lte = TiLTE.getInstance();

			lte.startup(20, new LTEEvent());

			TiLight light = TiLight.getInstance();

			System.out.println("lte status. "  + lte.getNetworkStatus());

			light.turnOn(0);

			System.out.println("imei=" + lte.getIMEI());
			System.out.println("imsi=" + lte.getIMSI());
			System.out.println("iccid=" + lte.getICCID());
			System.out.println("ip=" + lte.getPDPIP());
			System.out.println("apn=" + lte.getAPN());
			System.out.println("rssi=" + lte.getRSSI());

			TiLTECSQ csq = lte.getCSQ();
			System.out.println("csq.rssi=" + csq.getRSSI());
			System.out.println("csq.ber=" + csq.getBER());

			TiLTECell cell = lte.getCellInfo();
			System.out.println("cell.mcc=" + cell.getMCC());
			System.out.println("cell.mnc=" + cell.getMNC());
			System.out.println("cell.lac=" + cell.getLAC());
			System.out.println("cell.ci=" + cell.getCI());

			System.out.println("register status=" + lte.getNetworkStatus());

			lte.shutdown();

			System.out.println("register status=" + lte.getNetworkStatus());

			System.out.println("lte disconnected.");

			Delay.msDelay(1000);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
