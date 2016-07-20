package kr.ac.sungkyul.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer2 {
	private final static int SERVER_PORT = 2000;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket();
			
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localHostAddress = inetAddress.getHostAddress();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(localHostAddress,SERVER_PORT);
			
			serverSocket.bind(inetSocketAddress);
			System.out.println("[EchoServer bind]: "+localHostAddress+": "+SERVER_PORT);
			
			Socket socket = serverSocket.accept();
			
			InetSocketAddress remoteAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
			String remoteHostAddress = remoteAddress.getAddress().getHostAddress();
			int remoteHostPort = remoteAddress.getPort();			
			System.out.println("[EchoServer] 연결 성공 from " + remoteHostAddress + ": " + remoteHostPort);
			
			try{
				//IOStream 받아오기
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
				PrintWriter pw= new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"),true);				
				
				while(true){
					// 데이터 읽기
					String data =br.readLine();
					
					if(data ==null){
						// 클라이언트로 부터 정상 종료
						System.out.println("[EchoServer] closed by client");
						return;
					}
					// 출력
					System.out.println("[EchoServer] received: "+data);
					
					//데이터 쓰기(echo)
					pw.println(data);

				}
								
			}catch (SocketException e) {
				System.out.println("[EchoServer] 비정상적으로 클라이언트가 연결을 끊었습니다." + e );
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				if (socket != null && socket.isClosed() == false) {
					socket.close();
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
