package test;

import java.io.IOException;
import java.util.Arrays;

import tijos.framework.platform.TiSettings;
import tijos.framework.platform.util.KeyValueStorage;
import tijos.framework.util.Delay;
import tijos.framework.util.Formatter;
import tijos.kernel.io.disk.Disk1;

public class keyValueStorageTest {

	public static void main(String[] args) throws IOException {
		System.out.println("start");

		TiSettings.getInstance().resetKernelLoggerLevel(0);
		
		KeyValueStorage storage = KeyValueStorage.getInstance();

		//storage.readValue("ars", "rs");
		
		storage.format();
//
//		for (int i = 0; i < 130; i++) {
//			try {
//				System.out.println("delete " + i);
//				storage.deleteKey("testgroup", "KEY-01-" + i);
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//		}
////		
		for (int i = 0; i < 10; i++) {
			System.out.println("write " + i);
			storage.writeValue("testgroup", "KEY-01-" + i,
					"{\"msDelayAndtimeOuts\":[[3100,3000],[3100,3000],[3100,3000]],\"mode\":\"rtu\",\"validaMethod\":\"CrcStandardModBus\"}"
							.getBytes());
			System.out.println(i + " group value " + new String(storage.readValue("testgroup", "KEY-01-" + i)));
			///storage.deleteKey("testgroup", "KEY-01-" + i);			
		}
		
		System.out.println("write start");
		
		storage.writeValue("ars", "rs", "run".getBytes());

		System.out.println("write end");

//		
		for (int i = 0; i < 10; i++) {
			System.out.println("read " + i);
			
			System.out.println(i + " group value " + new String(storage.readValue("testgroup", "KEY-01-" + i)));
		}

//		String data = "{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52}";
//		System.out.println("datalen " + data.length());
//
//		storage.writeValue("modbus", "COM", (data + data).getBytes());
//
//		System.out.println("com " + new String(storage.readValue("modbus", "COM")));

		
		Delay.msDelay(5000);
	}

	public static void main1(String[] args) throws IOException {
		System.out.println("start");

		KeyValueStorage storage = KeyValueStorage.getInstance();

		byte[] value = "static bool find_kv_no_cache(fdb_kvdb_t db, const char *key, fdb_kv_t kv)\r\n".getBytes();
		storage.writeValue("group", "server", value);
		storage.writeValue("group", "data",
				"adfsadfdasfdsafadsfdasfdsafdasfadsfdsafdsafdasfdsafdsafdsafdsafdsafdasfadsfdsafdasf".getBytes());
		storage.writeValue("group", "data2",
				"adfjkasdjlkasfdjlksafdjlkfsadjlk;fdsajlkfdsjlk;fdsajlk;fdsajlk;fdsajlkfdsajkl;fjdsakldsfafdsaaf"
						.getBytes());

		storage.queryBegin();
		System.out.println("group " + storage.queryNextGroup());
		for (int i = 0; i < 10; i++) {
			System.out.println("server " + new String(storage.readValue("group", "server")));

			System.out.println("querykey before " + i);

			System.out.println("key " + i + storage.queryNextKey());
			System.out.println("data " + new String(storage.readValue("group", "data")));
		}

		System.out.println("exit");
	}

	public static void main2(String[] args) throws IOException {
		System.out.println("start");

		String group = "meter.history";
		KeyValueStorage storage = KeyValueStorage.getInstance();

//		String value = new String(storage.readValue("abcdefg", "keyvalue"));
//		System.out.println("value = " + value);

//		
		storage.writeValue("group2", "key1", "this".getBytes());
		System.out.println(new String(storage.readValue("group1", "key1")));
		
		storage.writeValue("group3", "key1", "this1".getBytes());
		System.out.println(new String(storage.readValue("group1", "key1")));
		
		storage.writeValue("group4", "key1", "this2".getBytes());
		System.out.println(new String(storage.readValue("group1", "key1")));

		for (int i = 0; i < 50; i++) {
			String key = "day" + i;
			byte[] value = new byte[512];
			Arrays.fill(value, (byte) i);
			
			System.out.println("write " + key);
			storage.writeValue(group, key, value);
		}
//		
		for (int i = 0; i < 5; i++) {
			String key = "day" + i;
			byte[] value = storage.readValue(group, key);
			
			System.out.println(Formatter.toHexString(value));			
		}
//		

	}
}
