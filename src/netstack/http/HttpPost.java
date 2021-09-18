package netstack.http;

import java.io.IOException;

import tijos.framework.networkcenter.httpclient.HttpConnection;
import tijos.framework.networkcenter.httpclient.HttpResponse;
import tijos.framework.platform.network.NetworkInterfaceManager;
import tijos.framework.util.Delay;

public class HttpPost {

	public static void main(String[] args) throws IOException {
		NetworkInterfaceManager.getNetworkInstance().startup(30);
		
		System.out.println("started.");

		String url = "http://tcp.ticloud.io:8080";
		
		HttpConnection httpConnection = new HttpConnection(url);
		
		httpConnection.setContentType(HttpConnection.APPLICATION_JSON);
		
		HttpResponse resp = httpConnection.post("this is post test".getBytes());
		
		System.out.println(" result " + resp.result);
		System.out.println(" status code " + resp.statusCode);
		
		if(resp.payload != null) {
			System.out.println(new String(resp.payload));
		}

		System.out.println("get ");

		String url2 = "http://tcp.ticloud.io:8080/README.md";
		
		HttpConnection httpConnection2 = new HttpConnection(url2);
		resp = httpConnection2.get();
		
		System.out.println(" result " + resp.result);
		System.out.println(" status code " + resp.statusCode);
		
		if(resp.payload != null) {
			System.out.println(new String(resp.payload));
		}

		

		Delay.msDelay(2000);
		
	}

}
