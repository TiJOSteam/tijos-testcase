package netstack.http;


import java.io.IOException;

import tijos.framework.networkcenter.http.HttpClient;
import tijos.framework.networkcenter.http.HttpMessage;
import tijos.framework.networkcenter.http.IHttpMessageListener;

class HttpResult {
	public int statusCode;
	public String content;
}

public class HttpHelper implements IHttpMessageListener {

	HttpClient httpClient;

	Object synObj = new Object();

	int statusCode = 0;

	HttpMessage httpMessage;
	
	String resultMessage = "";

	public HttpHelper(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	@Override
	public void onGetResponseArrived(HttpMessage httpMessage) {

		System.out.println("onGetResponseArrived " + httpMessage.url + " msgId " + httpMessage.msgId + " seq "
				+ httpMessage.sequence + " result " + httpMessage.result + " code " + httpMessage.statusCode + " total "
				+ httpMessage.response_len);

//		if (httpMessage.payload != null)
//			System.out.println(new String(httpMessage.payload));
				
		this.resultMessage += new String(httpMessage.payload);

		if (httpMessage.result <= 0 ) {
			synchronized (synObj) {
				this.httpMessage = httpMessage;
				synObj.notify();
			}
		}
	}

	@Override
	public void onPostResponseArrvied(HttpMessage httpMessage) {
		System.out.println("onPostResponseArrvied " + httpMessage.url + " msgId " + httpMessage.msgId + " seq "
				+ httpMessage.sequence + " result " + httpMessage.result + " code " + httpMessage.statusCode + " total "
				+ httpMessage.response_len);

		if (httpMessage.payload != null)
			System.out.println(new String(httpMessage.payload));

		this.resultMessage += new String(httpMessage.payload);

		if (httpMessage.result <= 0 ) {
			synchronized (synObj) {
				this.httpMessage = httpMessage;
				synObj.notify();
			}
		}

	}

	@Override
	public void onPutResponseArrived(HttpMessage httpMessage) {
	}

	@Override
	public void onDeleteResponseArrived(HttpMessage httpMessage) {
	}

	public HttpResult httpPost(String url, String header, String content, int timeout) throws IOException {

		if (header != null) {
			this.httpClient.setCustomHeader(header);
		}

		this.httpClient.post(url, HttpClient.APPLICATION_JSON, content.getBytes());

		synchronized (synObj) {
			try {
				synObj.wait(timeout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(this.httpMessage == null) {
			throw new IOException("Http request timeout");
		}


		if (this.httpMessage.result < 0) {
			throw new IOException("http error " + this.httpMessage.result);
		}

		HttpResult response = new HttpResult();
		response.statusCode = this.httpMessage.statusCode;
		response.content = this.resultMessage;

		return response;
	}

	public HttpResult httpGet(String url, String header, int timeout) throws IOException {
		if (header != null) {
			this.httpClient.setCustomHeader(header);
		}
		resultMessage = "";

		this.httpClient.get(url, HttpClient.APPLICATION_JSON);

		synchronized (synObj) {
			try {
				synObj.wait(timeout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(this.httpMessage == null) {
			throw new IOException("Http request timeout");
		}

		if (this.httpMessage.result < 0) {
			throw new IOException("http error " + this.httpMessage.result);
		}

		HttpResult response = new HttpResult();
		response.statusCode = this.httpMessage.statusCode;
		response.content =  this.resultMessage;

		return response;
	}

}
