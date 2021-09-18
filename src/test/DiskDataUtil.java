package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tijos.framework.platform.util.KeyValueStorage;
import tijos.framework.util.logging.Logger;

/**
 * 磁盘数据帮助类
 */
public class DiskDataUtil {
	/**
	 * 清空所有缓存数据，清空失败后并不进行回滚
	 */
	public static boolean clearAllData() {
		KeyValueStorage KeyValue = KeyValueStorage.getInstance();
		synchronized (KeyValue) {
			List<String> deleteAllStorage = getGroupKeys();
			for (String storage : deleteAllStorage) {
				try {
					KeyValue.deleteGroup(storage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// 只要有一个错就是
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * 写入数据
	 * 
	 * @param groupKey
	 * @param key
	 * @param jsonObject
	 * @return
	 */
	public static boolean writeData(String groupKey, String key, String data) {
		KeyValueStorage KeyValue = KeyValueStorage.getInstance();
		synchronized (KeyValue) {
			try {
				KeyValue.writeValue(groupKey, key, data.getBytes());
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	}

	/**
	 * 删除某个数据，通过组合键和组合键中的键
	 * 
	 * @param groupKey
	 * @param key
	 * @return
	 */
	public static boolean deleteDataByGroupKeyAndKey(String groupKey, String key) {
		KeyValueStorage KeyValue = KeyValueStorage.getInstance();
		synchronized (KeyValue) {
			try {
				KeyValue.deleteKey(groupKey, key);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	}

	/**
	 * 读取存储的数据---这边就不允许存array数据了
	 * 
	 * @param groupKey
	 * @param key
	 * @return
	 */
	public static String readDataByGroupKeyAndKey(String groupKey, String key) {
		KeyValueStorage KeyValue = KeyValueStorage.getInstance();
		synchronized (KeyValue) {
			try {
				byte[] bytes = KeyValue.readValue(groupKey, key);
				String data = new String(bytes);
				return data;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				System.out.println("failed to readDataByGroupKeyAndKey group " + groupKey + " key " + key);
				System.out.println("default will be used ");
			}
			return null;
		}
	}

	/**
	 * 直接删除所有组合键的的数据
	 * 
	 * @param groupKey
	 * @param key
	 * @return
	 */
	public static boolean deleteDataByGroupKey(String groupKey) {
		KeyValueStorage KeyValue = KeyValueStorage.getInstance();
		synchronized (KeyValue) {
			try {
				KeyValue.deleteGroup(groupKey);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	}

	/**
	 * 获取所有的groupKeys 设置线程同步
	 * 
	 * @return
	 */
	public static List<String> getGroupKeys() {
		KeyValueStorage KeyValue = KeyValueStorage.getInstance();
		synchronized (KeyValue) {
			List<String> groupKeys = new ArrayList<String>();
			try {
				KeyValue.queryBegin();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
				Logger.info("error：", "queryBegin error！");
			}

			while (true) {
				String groupKey = null;
				try {
					groupKey = KeyValue.queryNextGroup();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Logger.info("error：", "queryNextGroup error！");
				}
				if (groupKey == null) {
					break;
				} else {
					groupKeys.add(groupKey);
				}
				Logger.info("groupKey:", groupKey);
			}
			try {
				KeyValue.queryEnd();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				Logger.info("error：", "queryEnd error！");
			}
			return groupKeys;
		}
	}

	/**
	 * 通过groupKey获取keys
	 * 
	 * @param gk
	 * @return
	 */
	public static List<String> getKeysByGroupKey(String gk) {
		KeyValueStorage KeyValue = KeyValueStorage.getInstance();
		synchronized (KeyValue) {
			List<String> keys = new ArrayList<String>();
			boolean isFindKey = false;
			try {
				KeyValue.queryBegin();
			} catch (IOException e2) {
				// TODO Auto-generated catch block---时间需要30分钟更新一次
				e2.printStackTrace();
				Logger.info("error：", "queryBegin error！");
			}

			while (true) {
				String groupKey = null;
				try {
					groupKey = KeyValue.queryNextGroup();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Logger.info("error：", "queryNextGroup error！");
				}
				Logger.info("groupKey:", groupKey);
				if (groupKey == null) {
					break;
				} else if (gk.equals(groupKey)) {
					isFindKey = true;
					break;
				}
			}
			while (isFindKey) {
				String key = null;
				try {
					key = KeyValue.queryNextKey();
				} catch (Exception e) {
					Logger.info("error：", "queryNextKey error！");
				}
				if (key == null) {
					break;
				}
				keys.add(key);

			}
			try {
				KeyValue.queryEnd();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				Logger.info("error：", "queryEnd error！");
			}
			return keys;
		}
	}
	
	public static void main(String[] args) throws IOException {
		KeyValueStorage keyValue = KeyValueStorage.getInstance();
		
		//List<String> items  = getKeysByGroupKey("a");
		List<String>  items =  getGroupKeys();
		System.out.println("getGroupKeys " + Arrays.toString(items.toArray()));


		keyValue.writeValue("a", "7b", new byte[] {0x03,0x20,0x01,0x00,0x02});
		keyValue.writeValue("a", "7b", new byte[] {0x03,0x20,0x01,0x00,0x02});
		keyValue.writeValue("a", "10", new byte[] {0x03,0x20,0x01,0x00,0x02});
//		
//		items = getKeysByGroupKey("a");
//		System.out.println(Arrays.toString(items.toArray()));

		
	}
	
	public static void main2(String[] args) throws IOException {
		System.out.println("start");

//		clearAllData();
		
		
		KeyValueStorage keyValue = KeyValueStorage.getInstance();
		for(int i = 0; i < 10; i ++)
		{
			keyValue.writeValue("testgroup", "key" + i, ("value" + i).getBytes());			
		}

		for(int i = 0; i < 10; i ++)
		{
			keyValue.writeValue("settings", "key" + i, ("settings" + i).getBytes());			
		}

//		for(int i = 0; i < 10; i ++)
//		{
//			System.out.println(new String(keyValue.readValue("testgroup", "key" + i)));		
//		}
//
//		for(int i = 0; i < 10; i ++)
//		{
//			System.out.println(new String(keyValue.readValue("settings", "key" + i)));		
//		}
//
//		List<String> groups = getGroupKeys();
//		System.out.println(Arrays.toString(groups.toArray()));
//		//clearAllData();
		
//		for(int i = 0; i < 100; i ++)
//		{
//			writeData("kvgroup" + i, "item" + i, "testsetestset"+i*10);
//		}
		System.out.println(new String(keyValue.readValue("settings", "key0")));		
		
		List<String> items  = getGroupKeys();
		System.out.println("getGroupKeys " + Arrays.toString(items.toArray()));
		
		
		items = getKeysByGroupKey("a");
		System.out.println(Arrays.toString(items.toArray()));
		
//		for(int i = 0; i < 100; i ++)
//		{
//			System.out.println(new String(keyValue.readValue("kvgroup" + i, "item" + i)));
//		}
		
		
//		for(int i = 0; i< 100; i ++)
//		{
//			List<String> keys = getKeysByGroupKey("kvgroup"+i);
//			System.out.println(Arrays.toString(keys.toArray()));
//			
//		}
	}
}
