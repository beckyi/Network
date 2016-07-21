package kr.ac.sungkyul.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TimeClient {
	private static final String SERVER_IP = "220.67.115.231";
	private static final int SERVER_PORT = 3001;
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		DatagramSocket socket = null;
		Scanner scanner = null;
		
		try {
			while (true) {
				scanner = new Scanner(System.in);

				socket = new DatagramSocket();

				System.out.print("입력 꾹: ");
				String message = scanner.nextLine();

				if ("quit".equals(message)) {
					break;
				}
				
				//데이터 송신
				byte[] sendData = message.getBytes(StandardCharsets.UTF_8);
				
				DatagramPacket sendPacket  = new DatagramPacket(sendData, sendData.length,new InetSocketAddress(SERVER_IP, SERVER_PORT));
				
				socket.send(sendPacket); //block
				
				//데이터 수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE],BUFFER_SIZE);

				socket.receive(receivePacket);
				
				String data = new String(receivePacket.getData(),0,receivePacket.getLength(),StandardCharsets.UTF_8 );
				System.out.println( "현재시간: " + data );
			}

		}  catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			if (scanner != null) {
				scanner.close();
			}
			
			if (socket != null && socket.isClosed() == false) {
				socket.close();
			}
		}

	}

}
