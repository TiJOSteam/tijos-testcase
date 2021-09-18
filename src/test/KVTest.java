package test;

import java.io.IOException;

import tijos.framework.platform.util.KeyValueStorage;

public class KVTest {

	public static void main(String[] args) throws IOException {

		System.out.println("start");

		KeyValueStorage storage = KeyValueStorage.getInstance();
		
		try {
			storage.deleteKey("testgroup", "KEY-01-999");
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		System.out.println("write ");
		
//		storage.writeValue("testgroup", "KEY-01",
//				"{\"msDelayAndtimeOuts\":[[3100,3000],[3100,3000],[3100,3000]],\"mode\":\"rtu\",\"validaMethod\":\"CrcStandardModBus\"}"
//						.getBytes());
//
//		System.out.println("read:");
		System.out.println(" group value " + new String(storage.readValue("testgroup", "KEY-01")));

		//storage.deleteKey("testgroup", "KEY-01");

	}
}
