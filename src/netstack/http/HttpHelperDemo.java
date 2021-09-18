package netstack.http;

import tijos.framework.networkcenter.http.HttpClient;
import tijos.framework.platform.lte.TiLTE;

public class HttpHelperDemo {

	public static void main(String[] args) {
				
		HttpHelper httpHelper = new HttpHelper(HttpClient.getInstance());
		
		try {
			TiLTE.getInstance().startup(30);

			HttpClient.getInstance().start(httpHelper);
		
			String url = "http://thingmodeltsl.xhemu.cn/productKey/getThingModelTsl?simple=true&productKey=a1ghOPG4ADO";
			HttpResult response = httpHelper.httpGet(url, null, 10000);
			
			System.out.println(response.content);

			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		

	}

}
