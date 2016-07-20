package kr.ac.sungkyul.network.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
	         InetAddress inetAddress = InetAddress.getLocalHost();	// IP 주소를 표현
	         String hostname = inetAddress.getHostName();
	         String hostAddress = inetAddress.getHostAddress();
	         byte[] addresses = inetAddress.getAddress();	//byte[]로 표현
	         
	         System.out.println("Hostname: " + hostname);
	         System.out.println("HostAddress: " + hostAddress);
	         
	         
	         for(int i=0; i<addresses.length; i++) {
	        	 System.out.print(addresses[i]& 0x00000ff); //부호 비트 상관없이 테스트 (-제외)
	        	 if(i<addresses.length-1){
		        	 System.out.print(".");
	        	 }
//	            System.out.print(addresses[i]);
	         }
	         
	         
	      } catch (UnknownHostException e) {         
	         e.printStackTrace();
	      }
	}

}
