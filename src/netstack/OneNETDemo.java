package netstack;

import java.io.IOException;
import java.util.Arrays;

import tijos.framework.networkcenter.onenet.IOneNetMqttEventListener;
import tijos.framework.networkcenter.onenet.OneJSONEvent;
import tijos.framework.networkcenter.onenet.OneJSONProperty;
import tijos.framework.networkcenter.onenet.OneNetMqttClient;
import tijos.framework.platform.lan.TiLAN;
import tijos.framework.platform.lpwan.TiNBIoT;
import tijos.framework.platform.lte.TiLTE;
import tijos.framework.util.Delay;
import tijos.framework.util.json.JSONObject;

class OnenetEventListner implements IOneNetMqttEventListener {

	OneNetMqttClient onenet;
	
	public OnenetEventListner(OneNetMqttClient onenet)
	{
		this.onenet = onenet;
	}
	
	@Override
	public void onMqttConnected() {
		// TODO Auto-generated method stub
		System.out.println("onMqttConnected ");

	}

	@Override
	public void onMqttDisconnected(int error) {
		System.out.println("onMqttDisconnected " + error);

	}

	@Override
	public void onPropertyReportReply(String msgId, int code, String message) {
		System.out.println("onPropertyReportReply " + msgId + " " + code + " " + message);

	}

	@Override
	public void onEventReportReply(String msgId, int code, String message) {
		System.out.println("onEventReportReply " + msgId + " " + code + " " + message);

	}

	@Override
	public void onPropertySetArrived(String msgId, JSONObject params) {
		System.out.println("msgId " + msgId + " " + params.toString());

		try {
			onenet.propertySetReply(msgId, 200, "OK");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onServiceInvokeArrived(String msgId, String serviceId, JSONObject params) {
		System.out.println("onServiceInvokeArrived " + "msgId " + msgId + " serviceId " + serviceId + " "
				+ params.toString());

		JSONObject response = new JSONObject();

		try {
			onenet.serviceInvokeReply(msgId, serviceId, 200, "OK", response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDesiredPropertySetArrived(String msgId, JSONObject params) {
		// TODO Auto-generated method stub
		System.out.println("onDesiredPropertySetArrived " + params.toString());
	}

	@Override
	public void onDesiredDeleteReply(String msgId, int code, String message) {
		System.out.println("onDesiredDeleteReply " + msgId + " " + code + " " + message);
	}

	@Override
	public void onPropertyGetArrived(String msgId, String[] params) {
		System.out.println("onPropertyGetArrived " + Arrays.toString(params));
		
	}

}

public class OneNETDemo {

	public static void main(String[] args) {
		System.out.println("Start ...");
		try {
			TiNBIoT.getInstance().startup(30);
			//TiLAN.getInstance().startup(30);
				
			//设备密钥信息	
			String productId = "g2WNOwc4bO";
			//设备名称使用IMEI
			String deviceName = "867435057486946";// TiLTE.getInstance().getIMEI();
			
			//产品密钥
			String productKey = "ef9NubfZlQeTEe8dizlngQvFAUFzo/OHiT1JmdMNM/8=";

			//OneNET Studio平台客户端
			OneNetMqttClient onenet = new OneNetMqttClient(productId, deviceName, productKey);

			//启动连接并设置事件监听
			onenet.connect(new OnenetEventListner(onenet));
			System.out.println("OneNETDEMO connected");
			

			//上报当前设备地理位置， 需要在物模型中加入基站定位系统功能点$OneNET_LBS
			onenet.lbsCellLocationReport();

			//每10秒上报属性值
			OneJSONProperty property = new OneJSONProperty();

			//获取实际设备数据设置相应属性
			property.setProperty("liquid_level", System.currentTimeMillis(), 1000);

		    onenet.propertyReport(property.toJSON());
		    
		    OneJSONEvent event  = new OneJSONEvent();
		    event.setTimeStamp(System.currentTimeMillis());
		    event.setProperty("alarm_type", 1);
		    event.setProperty("alarm_value", 2000);
			    
		    onenet.eventReport("liquid_level_state_alarm", event.toJSON());
		    
		    onenet.getDesiredProperty("regrpt", "liquid_threshold_min", "liquid_threshold_max", "liquid_threshold_dynamic");
		
		    onenet.deleteDesiredProperty("regrpt", "liquid_threshold_min", "liquid_threshold_max", "liquid_threshold_dynamic");	    
		    
		    System.out.println("Disconnect and exit");
		    System.out.println("done");
		    Delay.msDelay(15000);
		    onenet.disconnect();

		} catch (Exception ex) {
			ex.printStackTrace();
	
			
		}

	}

}
