package kr.ac.sungkyul.network.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer {
	private final static int SERVER_PORT = 1000;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			// 1.서버 소켓 생성
			serverSocket = new ServerSocket();

			// 2.바인딩
			InetAddress inetAddress = InetAddress.getLocalHost(); // local
																	// address
			String serverAddress = inetAddress.getHostAddress();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(serverAddress, SERVER_PORT); // IP,
																										// Port

			serverSocket.bind(inetSocketAddress);
			System.out.println("[server] bind - " + serverAddress + ": " + SERVER_PORT);

			// 3.accept 클라이언트로 부터 연결(요청) 대기
			Socket socket = serverSocket.accept(); // blocking

			InetSocketAddress remoteHostAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			int remoteHostPort = remoteHostAddress.getPort();
			// System.out.println("[server] 연결 성공 from "+remoteHostAddress.getHostName()+": "+remoteHostAddress.getAddress().getHostAddress());
			System.out.println("[server] 연결 성공 from " + remoteHostAddress.getHostName() + ": " + remoteHostPort);

			try {

				// 5.IOStream
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				// 6.데이터 읽기
				byte[] buffer = new byte[256];
				int readbytes = is.read(buffer); // blocked
				if (readbytes <= -1) { // 클라이언트가 연결을 끊었을 경우 (정상종료)
					System.out.println("[server] closed by client");
					return;
				}

				String data = new String(buffer, 0, readbytes, "utf-8");
				System.out.println("[server] received: " + data);

				// 7.데이터쓰기
				os.write(data.getBytes("utf-8")); // client에게 출력
				
			} catch(SocketException e){
				System.out.println("[server] 비정상적으로 클라이언트가 연결을 끊었습니다.");
			}catch (IOException e) {
				e.printStackTrace();
			}finally{
				// 8.데이터 통신 소켓 닫기
				if (socket != null && socket.isClosed() == false) {
					socket.close(); // stream도 같이 close됨
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				//서버 소켓 닫기
				if (serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
