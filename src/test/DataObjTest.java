package test;

import java.io.IOException;

import tijos.kernel.io.object.DataObj;
import tijos.kernel.io.object.ItemValue;

public class DataObjTest {

	public static void main(String[] args) throws IOException {

		DataObj obj = DataObj.alloc(8);

		System.out.println("size:" + obj.getSize());
		
		obj.putItem(0, new ItemValue("12345678", 128));
		obj.putItem(1, new ItemValue(128, 2));
		obj.putItem(2, new ItemValue(3.14, 5467));
		obj.putItem(3, new ItemValue(new byte[] {1, 2, 3, 4, 5, 6, 7, 8}, 65535));
		obj.putItem(4, new ItemValue(true, 7654));
		
		System.out.println("item0:" + obj.getItem(0).getObject() + "  " + obj.getItem(0).getId());
		System.out.println("item1:" + obj.getItem(1).getObject() + "  " + obj.getItem(2).getId());
		System.out.println("item2:" + obj.getItem(2).getObject() + "  " + obj.getItem(2).getId());
		System.out.println("item3:" + obj.getItem(3).getObject() + "  " + obj.getItem(3).getId());
		System.out.println("item4:" + obj.getItem(4).getObject() + "  " + obj.getItem(4).getId());

		
	}

}
