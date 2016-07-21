package kr.ac.sungkyul.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class EchoServer {
	private static final int PORT = 1000;
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatagramSocket socket = null;
		
		try {
			//1.소켓 생성
			socket = new DatagramSocket(PORT);

			while (true) {
				// 2. 수신대기
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE); // 보낸  사람의 주소랑 포트번호 담고 있음
//				System.out.println("<<<수신대기>>>");
				socket.receive(receivePacket); // blocking

				// 3.데이터 수신
				String data = new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);
				// System.out.println("<<<데이터 수신>>>");
				System.out.println("수신: " + data);

				// 4. 데이터 송신
				byte[] sendData = data.getBytes(StandardCharsets.UTF_8);
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, new InetSocketAddress(receivePacket.getAddress(), receivePacket.getPort()));

				socket.send(sendPacket);
			}
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(socket != null && socket.isClosed() == false){
				socket.close();
			}
		}
	}

}
