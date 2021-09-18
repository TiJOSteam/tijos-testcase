package test;

import java.io.IOException;

import tijos.framework.appcenter.TiAPPManager;
import tijos.framework.platform.TiSettings;
import tijos.framework.util.Delay;

public class terminaltest {
	public static void main(String[] args) throws IOException {
		
		System.out.println("this is 1st port");
		
		TiSettings.getInstance().setTerminalUART(4);

		for(int i = 0; i < 10 ; i++)
		{
		System.out.println("this is second port " + i);
		}
		
		Delay.msDelay(5000);
		
		int uart = TiSettings.getInstance().getTerminalUART();
		System.out.println("current uart " + uart);
				
		TiAPPManager.getInstance().getTerminal().execute(true, "");
//		
		System.out.println("current uart 2 " + uart);
			
		
	}
}
