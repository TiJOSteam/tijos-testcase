package test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import tijos.framework.platform.TiPower;
import tijos.framework.platform.sound.TiTTS;
import tijos.framework.util.BigBitConverter;
import tijos.framework.util.Delay;
import tijos.framework.util.Formatter;

public class Hello {


	public static void main(String[] args) throws IOException {

		System.out.println(System.getProperty("host.sn"));
		
		
//		String str = "{\"params\":{\"data\":[{\"value\":\"20\",\"key\":\"batteryLevel\"},{\"value\":\"200.1\",\"key\":\"batteryVoltage\"}]}}\r\n";
//		
//		byte [] data  = str.getBytes(); // Formatter.hexStringToByte("0000ff41bb3333000773756363657373000141bb3333");
//		
//		System.out.println(Formatter.toHexString(data));

//		System.out.println("oem " + System.getProperty("hardware.oem"));
//		
//		System.out.print("helloworld\n");
//	
		String str = new String(Formatter.hexStringToByte("C80012 7B22636F6D6D616E64223A2274657374227D"));
		
		System.out.println(str);
	}
}
