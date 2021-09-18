package test;

import tijos.framework.util.json.JSONObject;
import tijos.framework.util.json.JSONTokener;

public class jsonTest {
	public static void main(String[] args) {
		String jsonCmd = "{\"interval\":10}";
		JSONTokener jsonTokener = new JSONTokener(jsonCmd);
		JSONObject command = (JSONObject) jsonTokener.nextValue();
		if (command.has("interval")) {
			int interval = command.getInt("interval");
			System.out.println("interval " +  interval);
		}
		
		
		JSONObject jobj = new JSONObject();
		jobj.put("key1", JSONObject.NULL);
		
		System.out.println(jobj.toString());
	}
}
