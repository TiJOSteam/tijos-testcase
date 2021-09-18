package lte;

import java.io.IOException;

import tijos.framework.platform.sound.TiTTS;

public class Speaker {

	public static void main(String[] args) throws IOException {

		System.out.println("start");
		System.out.println(System.getProperty("hardware.oem"));
		System.out.println(System.getProperty("host.network"));
		
		System.out.println(System.getProperty("host.sn"));
		
	}

}
