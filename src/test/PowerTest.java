package test;

import tijos.framework.platform.TiPower;
import tijos.framework.util.Delay;

public class PowerTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("reboot test");
		
		Delay.msDelay(2000);
		
		TiPower.getInstance().reboot(0);
		
		
		
		while(true) {
			Delay.msDelay(1000);
		}
		
	}

}
