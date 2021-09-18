package test;

import java.io.IOException;

import tijos.framework.platform.peripheral.ITiKeyboardListener;
import tijos.framework.platform.peripheral.TiKeyboard;
import tijos.framework.platform.peripheral.TiLight;
import tijos.framework.util.Delay;


class KeyBoardEvents implements ITiKeyboardListener
{

	@Override
	public void onPressed(int id, long time) {
		System.out.println("onPress " + id + " " + time);
	}

	@Override
	public void onReleased(int id, long time) {
		System.out.println("onReleased " + id + " " + time);		
	}
	
}

public class TiLightTest  {

	public static void main(String[] args) throws IOException {
		
		TiKeyboard.getInstance().setEventListener(new KeyBoardEvents());

		TiLight.getInstance().turnOn(0);
		Delay.sDelay(2);
		TiLight.getInstance().turnOn(1);
		Delay.sDelay(2);
		
		TiLight.getInstance().turnOff(0);
		Delay.sDelay(2);
		TiLight.getInstance().turnOff(1);
		Delay.sDelay(2);
		
		
		
		Delay.msDelay(20000);
		
	}

}
