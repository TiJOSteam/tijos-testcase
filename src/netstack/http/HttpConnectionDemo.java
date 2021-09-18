package netstack.http;

import tijos.framework.networkcenter.httpclient.HttpConnection;
import tijos.framework.networkcenter.httpclient.HttpResponse;
import tijos.framework.platform.network.NetworkInterfaceManager;

public class HttpConnectionDemo {

	public static void main(String[] args) {

		try {
			System.out.println("start network.");
			NetworkInterfaceManager.getNetworkInstance().startup(30);
		
			
//			String url = "http://img.tijos.net/img/version.txt";

			String url = "http://thingmodeltsl.xhemu.cn/productKey/getThingModelTsl?simple=true&productKey=a1ghOPG4ADO";
			
			HttpConnection httpConnection = new HttpConnection(url);
			
			httpConnection.setContentType(HttpConnection.APPLICATION_JSON);
			httpConnection.setReadTimeout(10000);
			
			HttpResponse resp = httpConnection.get();
			
			System.out.println(new String(resp.payload));
			
			while(resp.hasMoreData()) {
				resp = httpConnection.readMoreHttpResponse();
				System.out.println(new String(resp.payload));				
			}
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
