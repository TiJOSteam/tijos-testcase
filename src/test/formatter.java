package test;

import java.net.URI;
import java.net.URISyntaxException;

import tijos.framework.util.Formatter;
import tijos.framework.util.json.JSONObject;
import tijos.framework.util.json.JSONTokener;

public class formatter {

	public static void main(String[] args) {

		System.out.println(Formatter.format(3.543, ".#"));
		System.out.println(Formatter.format(3.597, ".##"));
		System.out.println(Formatter.format(3.507, ".##"));
		
		JSONObject jObject = new JSONObject();

        jObject.put("total", 236.8f);
        
    	String RootCloud = "coap://47.92.248.3:5683";  

    	URI uri = null;
        try {
			uri = new URI(RootCloud);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	System.out.println("uri " + uri.getHost() + " " + uri.getPort());

        System.out.print(jObject.toString());
        
        String jsonCmd = "{\"interval\":10}";
        
        JSONTokener jsonTokener = new JSONTokener(jsonCmd);
		JSONObject command = (JSONObject) jsonTokener.nextValue();
	
		System.out.println("has interval " + command.has("interval"));
		
		command.get("url");
		
		System.out.println(command);
	}
}
