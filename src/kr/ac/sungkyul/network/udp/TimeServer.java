package kr.ac.sungkyul.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeServer {
	private static final int PORT = 3001;
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
				socket.receive(receivePacket); // blocking

				// 3.데이터 수신
//				String data = new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);

				// 4. 데이터 송신
				SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss a" ); //시간
				String time = format.format( new Date());
				
				byte[] sendData = time.getBytes(StandardCharsets.UTF_8);
				
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, new InetSocketAddress(receivePacket.getAddress(), receivePacket.getPort()));

				socket.send(sendPacket);
			}
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(socket != null && socket.isClosed() == false){
				socket.close();
			}
		}
	}

}
