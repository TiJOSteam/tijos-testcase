package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import tijos.framework.platform.TiSettings;
import tijos.framework.platform.lte.TiLTE;
import tijos.framework.platform.network.NetworkException;
import tijos.framework.platform.network.NetworkInterfaceManager;
import tijos.framework.util.TimeZone;

public class TimeZoneTest {

	public static void main(String[] args) throws ParseException, NetworkException {
		// TODO Auto-generated method stub		
		NetworkInterfaceManager.getNetworkInstance().startup(30);

		System.out.println("utc ms " + System.currentTimeMillis());
		
		System.out.println("id " + TimeZone.getID());
		System.out.println("hours " + TimeZone.getHours());
		System.out.println(TimeZone.getRawOffset());
		System.out.println("currentUTCTimeSeconds " + TimeZone.currentUTCTimeSeconds());
		System.out.println("currentLocalTimeSeconds " + TimeZone.currentLocalTimeSeconds());
		
		
		String dateStr = "21/07/23 14:12:34";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		sdf.parse(dateStr);
				
		long collectTime = (28800 + TiSettings.getInstance().getDateTime()) * 1000;
		Date date = new Date(collectTime);
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String createdate = sdf1.format(date);
		
		System.out.println(createdate);
		
		
	}

}
