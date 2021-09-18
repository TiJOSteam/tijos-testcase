package test;

import java.net.URI;

public class uriTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		URI uri = URI.create("coap://183.230.40.40:5683/test.html?test=1");
		
		System.out.println(uri.getQuery());
		
	}

}
