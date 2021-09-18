package nbiot;

import java.util.Arrays;

import tijos.framework.platform.lpwan.TiNBIoT;

public class NbPropTest {

	public static void main(String[] args) {

		try {

			String [] apns = TiNBIoT.getInstance().getAPN();
			
			System.out.println("apns: " + Arrays.toString(apns));
			
//			TiNBIoT.getInstance().resetAPN();

//			TiNBIoT.getInstance().setAPN("IPV4V6", "CMNBIOT1", "","");
			
			TiNBIoT.getInstance().startup(60);
			
			String name = System.getProperty("host.network");
			System.out.println("network name " + name);
			System.out.println("apns: " + Arrays.toString(apns));
			
			System.out.println("band: " + TiNBIoT.getInstance().getBand());
			System.out.println("ci: " + TiNBIoT.getInstance().getCI());
			System.out.println("ecl: " + TiNBIoT.getInstance().getECL());
			String [] edrx =  TiNBIoT.getInstance().geteDRX();
			System.out.println("edrx: " + Arrays.toString(edrx));
				
			System.out.println("rsrp level: " + TiNBIoT.getInstance().getRSRPLevel());
			System.out.println("rsrp: " + TiNBIoT.getInstance().getRSRP());

			System.out.println("rsrq level: " + TiNBIoT.getInstance().getRSRQLevel());
			System.out.println("rsrq: " + TiNBIoT.getInstance().getRSRQ());
			
			System.out.println("rssi: " + TiNBIoT.getInstance().getRSSI());
			
		
			apns = TiNBIoT.getInstance().getAPN();
			System.out.println("apns: " + Arrays.toString(apns));

			String [] drx = TiNBIoT.getInstance().getDynamicPSM();
			System.out.println("drx: " + Arrays.toString(drx));
			

			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
