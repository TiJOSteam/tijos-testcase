package test;

import java.io.IOException;

import tijos.framework.util.Formatter;
import tijos.kernel.io.security.SecurityStorage;

public class SecurityObjectTest {

	public static void main(String[] args) {
		 
		try {
			SecurityStorage.preset(1);
						
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}

}
