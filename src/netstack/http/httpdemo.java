package netstack.http;

import tijos.framework.networkcenter.http.HttpClient;
import tijos.framework.networkcenter.http.HttpMessage;
import tijos.framework.networkcenter.http.IHttpMessageListener;
import tijos.framework.platform.lan.TiLAN;
import tijos.framework.platform.lpwan.TiNBIoT;
import tijos.framework.platform.lte.TiLTE;
import tijos.framework.platform.network.NetworkInterfaceManager;
import tijos.framework.util.Delay;
import tijos.framework.util.Formatter;


class HttpEventListener implements IHttpMessageListener{
	@Override
	public void onGetResponseArrived(HttpMessage httpMessage) {
		System.out.println("onGetResponseArrived " + httpMessage.url + " msgId " + 
	httpMessage.msgId + " seq " + httpMessage.sequence +
	" result " + httpMessage.result + " code " 
	+ httpMessage.statusCode + " total " + httpMessage.response_len);
		
		if(httpMessage.payload != null)
			System.out.println(new String(httpMessage.payload));

	}

	@Override
	public void onPostResponseArrvied(HttpMessage httpMessage) {
		System.out.println("onPostResponseArrvied " + httpMessage.url + " msgId " + httpMessage.msgId + " seq " + httpMessage.sequence +  " result " + httpMessage.result + " code " + httpMessage.statusCode + " total " + httpMessage.response_len);
		
		if(httpMessage.payload != null)
			System.out.println(new String(httpMessage.payload));
		
	}

	@Override
	public void onPutResponseArrived(HttpMessage httpMessage) {
		System.out.println("onPutResponseArrived " + httpMessage.url + " msgId " + httpMessage.msgId + " seq " + httpMessage.sequence +  " result " + httpMessage.result + " code " + httpMessage.statusCode + " total " + httpMessage.response_len);
		
		if(httpMessage.payload != null)
			System.out.println(new String(httpMessage.payload));
		
	}

	@Override
	public void onDeleteResponseArrived(HttpMessage httpMessage) {
		System.out.println("onDeleteResponseArrived " + httpMessage.url + " msgId " + httpMessage.msgId 
				+" seq " + httpMessage.sequence +  " result " + httpMessage.result + " code " + httpMessage.statusCode + " total " + httpMessage.response_len
				+" content len " + httpMessage.payload.length);
		
		if(httpMessage.payload != null)
			System.out.println(new String(httpMessage.payload));
		
	}
	
}

public class httpdemo {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
				
			System.out.println("start nb.");
			NetworkInterfaceManager.getNetworkInstance().startup(30);
			
			System.out.println("started.");
			
//			String url = "http://img.tijos.net:9999/img/tiwl-aep.tapk";
			
			String url = "http://img.tijos.net/img/version.txt";
			//String url = "http://39.98.164.14:9000/tapk/app.ico?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minioadmin%2F20210901%2F%2Fs3%2Faws4_request&X-Amz-Date=20210901T075645Z&X-Amz-Expires=432000&X-Amz-SignedHeaders=host&X-Amz-Signature=30f9c04f090c219da2c7716f9391e8c4d2b98fe6948c0210876f8aaab23f31cd";
					
			HttpClient.getInstance().start(new HttpEventListener());
			
			HttpClient.getInstance().get(url, HttpClient.APPLICATION_OCTET_STREAM);
						
			Delay.msDelay(15000);
			
			String url2 = "http://api.openweathermap.org//data/2.5/weather?q=shanghai&appid=c592e14137c3471fa9627b44f6649db4&mode=xml&units=metric";
			HttpClient.getInstance().get(url2, HttpClient.APPLICATION_JSON);
			
			//HttpClient.getInstance().get("http://httpbin.org/get", HttpClient.APPLICATION_JSON);

			Delay.msDelay(5000);
			
			{
				//HttpClient.getInstance().get("www.baidu.com", HttpClient.APPLICATION_HTML);
				
				Delay.msDelay(5000);

//				HttpClient.getInstance().get("http://httpbin.org/get", HttpClient.APPLICATION_JSON);
//				Delay.msDelay(5000);
//				HttpClient.getInstance().get("https://httpbin.org/headers",HttpClient.APPLICATION_JSON);
//				Delay.msDelay(5000);
//				HttpClient.getInstance().delete("http://httpbin.org/delete");
//				Delay.msDelay(5000);
//				HttpClient.getInstance().put("http://httpbin.org/put", HttpClient.APPLICATION_JSON, "{\"interval\": 120}".getBytes());
//				Delay.msDelay(5000);
//				HttpClient.getInstance().post("http://httpbin.org/post", HttpClient.APPLICATION_JSON, "{\"interval\": 400}".getBytes());
//				
				Delay.msDelay(15000);
			}
//			
			HttpClient.getInstance().stop();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
