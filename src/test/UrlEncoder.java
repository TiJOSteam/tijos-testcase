package test;

import java.net.URLDecoder;
import java.net.URLEncoder;

import tijos.framework.util.json.JSONObject;

public class UrlEncoder {

	public static void main(String[] args) {
	
		String txt = "version=2018-10-31&res=products"+URLEncoder.encode("/dafdfadfafdaf/devices/che1") + "&et=1537255523&method=sha1&sign=ZjA1NzZlMmMxYzIOTg3MjBzNjYTI2MjA4Yw=";
		

		System.out.println(txt);

		
		String json = URLDecoder.decode("%7B%22ud%22%3A%222021-08-04%2016%3A12%3A16%22%2C%22equid%22%3A%22WT01%22%7D");
		System.out.println(json);
		
		
	}

}
