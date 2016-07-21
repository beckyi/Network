package kr.ac.sungkyul.network.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {
	private final static int SERVER_PORT = 1004;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null;
		
		try {
			//서버소켓생성
			serverSocket = new ServerSocket();
			
			//Time-wait 상태에서 포트 재할당을 가능하게 하기 위하여
			serverSocket.setReuseAddress(true);
			
			//바인딩
			InetAddress inetAddress = InetAddress.getLocalHost();
			String serverAddress = inetAddress.getHostAddress();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(serverAddress,SERVER_PORT);
			
			serverSocket.bind(inetSocketAddress);
			System.out.println("[EchoServer bind]: "+serverAddress+": "+SERVER_PORT);
			
			Socket socket = serverSocket.accept();
			InetSocketAddress remoteHostAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
			int remoteHostPort = remoteHostAddress.getPort();
			
			System.out.println("[EchoServer] 연결 성공 from " + remoteHostAddress.getHostName() + ": " + remoteHostPort);
			
			try{
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				
				while(true){
					byte[] buffer = new byte[256];
					int readbytes = is.read(buffer);
					
					if(readbytes <= -1){
						System.out.println("[EchoServer] closed by client");
						return;
					}
					
					String data = new String(buffer,0,readbytes,"UTF-8");
					System.out.println("[EchoServer] received: "+data);
					
					os.write(data.getBytes("UTF-8"));
					
				}
								
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			}

		}catch(SocketException e){
			System.out.println("[EchoServer] 비정상적으로 클라이언트가 연결을 끊었습니다.");
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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
