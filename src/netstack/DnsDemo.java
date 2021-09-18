package netstack;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import tijos.framework.networkcenter.dns.TiDNS;
import tijos.framework.platform.lan.TiLAN;
import tijos.framework.platform.lpwan.TiNBIoT;
import tijos.framework.platform.lte.TiLTE;
import tijos.framework.platform.network.NetworkInterfaceManager;
import tijos.framework.util.Delay;

/**
 * 本例程演示如何获取系统信息以及通过java标准InetAddress访问TiJOS的DNS服务，获取指定域名IP地址
 *
 * @author TiJOS
 */

public class DnsDemo {

    public static void main(String[] args) {

        try {
        	
        	NetworkInterfaceManager.getNetworkInstance().startup(30);
      
   // 		System.out.println(TiLAN.getInstance().getLocalIP());

   // 		Delay.msDelay(5000);
            //System properties
//            System.out.println("java.vm.name = " + System.getProperty("java.vm.name"));
//            System.out.println("java.vm.version = " + System.getProperty("java.vm.version"));
//
//            System.out.println("os.name = " + System.getProperty("os.name"));
//            System.out.println("os.version = " + System.getProperty("os.version"));
//            System.out.println("os.arch = " + System.getProperty("os.arch"));
//
//            System.out.println("host.name = " + System.getProperty("host.name"));
//            System.out.println("host.sn = " + System.getProperty("host.sn"));
//
            //DNS
      //      System.out.println(" dmp ip: " + InetAddress.getByName("153.35.119.75"));
               	
            System.out.println(" dmp ip: " + InetAddress.getByName("dmp-coap.cuiot.cn"));
            System.out.println(" 163 ip: " + InetAddress.getByName("www.163.com"));
            System.out.println(" baidu ip: " + InetAddress.getByName("www.baidu.com"));
            System.out.println(" google ip: " + InetAddress.getByName("www.google.com"));
            System.out.println(" qq ip: " + InetAddress.getByName("www.qq.com"));
            System.out.println(" udp cloud ip: " + InetAddress.getByName("udp.tijcloud.com"));

            
        } catch (UnknownHostException e) {
            System.out.println("UnknownHostException");
            e.printStackTrace();

        } catch (IOException ie) {
            ie.printStackTrace();
        }


    }

}
