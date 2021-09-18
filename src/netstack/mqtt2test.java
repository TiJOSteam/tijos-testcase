package netstack;

import java.io.IOException;
import java.io.InterruptedIOException;

import tijos.framework.networkcenter.mqtt.IMqttMessageListener;
import tijos.framework.networkcenter.mqtt.MqttClient;
import tijos.framework.networkcenter.mqtt.MqttConnectOptions;
import tijos.framework.platform.lan.TiLAN;
import tijos.framework.platform.lpwan.TiNBIoT;
import tijos.framework.platform.network.NetworkException;
import tijos.framework.platform.network.NetworkInterface;
import tijos.framework.platform.network.NetworkInterfaceManager;
import tijos.framework.util.Delay;
import tijos.kernel.bus.iot.mqtt.MqttClientService;

class MqttClientMessage implements IMqttMessageListener {
	@Override
	public void onNetworkDisconnected(int err) {
		System.out.println("onNetworkDisconnected " + err);
	}

	@Override
	public void onMqttConnected() {
		System.out.println("onMqttConnected");
	}

	@Override
	public void publishMessageArrived(String topic, byte[] payload) {
		System.out.println("publishMessageArrived " + topic + " " + new String(payload));

	}

	@Override
	public void publishCompleted(int msgId, String topic, int result) {
		System.out.println("publishCompleted " + " mid " + msgId + " " + topic + " " + " result " + result);

	}

	@Override
	public void subscribeCompleted(int msgId, String topic, int result) {
		System.out.println("subscribeCompleted " + " mid " + msgId + " " + topic + " " + " result " + result);

	}

	@Override
	public void unsubscribeCompleted(int msgId, String topic, int result) {
		System.out.println("unsubscribeCompleted " + " mid " + msgId + " " + topic + " " + " result " + result);

	}

	@Override
	public void onNetworkConnected(boolean isReconnect) {
		// TODO Auto-generated method stub
		System.out.println("onNetworkConnected " + isReconnect);
	}

}

public class mqtt2test {

	public static void main(String[] args) throws InterruptedIOException, IOException {

		String name = System.getProperty("host.network");
		System.out.println("name " + name);
		NetworkInterface ni = NetworkInterfaceManager.getNetworkInstance();
		try {
			ni.startup(30);
		} catch (NetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(TiLAN.getInstance().getLocalIP());

		Delay.msDelay(3000);

		String clientId = MqttClient.generateClientId();

	    // MQTT Server 地址,用户名, 密码
        final String broker = "mqtt://test.mosquitto.org:1883";
//        final String broker = "mqtt://192.168.3.146:1883";
        
        
        final String username = "tijos/dev1";
        final String password = "tWnuCZdmdgqn6uT6oaVjE1NwC9atipvOTxBA0Xn2QFQ=";

		MqttConnectOptions options = new MqttConnectOptions();
		options.setUserName(username);
		options.setPassword(password);

		try {
			System.out.println("start nb.");

			System.out.println("started LTE.");

			MqttClient.getInstance().connect(clientId, broker, 10 * 1000, options, new MqttClientMessage());

			System.out.println("MqttClient connect ");

			String topic = "test";

			int mid = MqttClient.getInstance().subscribe(topic, 1);
			System.out.println("subscribe " + mid);

			int count = 0;
			while (count++ < 10) {
				mid = MqttClient.getInstance().publish(topic, ("count " + count).getBytes(), 1, true);

				System.out.println("publish " + mid);
			}

			while (MqttClient.getInstance().isBusy()) {
				System.out.println("busy");
				Delay.msDelay(1000);

				int msgCount = MqttClientService.getStatus(1);
				int netState = MqttClientService.getStatus(2);
				System.out.println("msgcount " + msgCount + " netState " + netState);

			}

//			count = 100;
//			while(count -- > 0) {
			while (true) {
				System.out.println("running");
				Delay.msDelay(1000);
	
				count ++;
				if(count % 100 == 0) {
					mid = MqttClient.getInstance().publish(topic, ("count " + count).getBytes(), 1, true);
				}

			}

			// System.out.println("disconnect");
			// MqttClient.getInstance().disconnect();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
