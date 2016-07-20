package kr.ac.sungkyul.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class EchoClient2 {
	private static final String SERVER_IP = "220.67.115.231";
	private final static int SERVER_PORT = 2000;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket socket = null;
		Scanner scanner = null;

		try {
			scanner = new Scanner(System.in);
			
			socket = new Socket();

			InetSocketAddress serverSocketAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);
			socket.connect(serverSocketAddress);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
			PrintWriter pw= new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"),true);
			
			while (true) {
								
				System.out.print(">> ");
				String data = scanner.nextLine();
				
				if("exit".equals(data)){
					break;
				}
								
				//Send Message
				pw.println(data);
				
				//Receive Message again
				String messageEcho = br.readLine();
				
				if (messageEcho == null) {
					System.out.println("[EchoClient] close by server");
					break;
				}

				System.out.println("<< " + messageEcho);
				
			}
		} catch (SocketException e) {
			System.out.println("[EchoClient] 비정상적으로 서버로 부터 연결이 끊어졌습니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
				
				if(scanner != null){
					scanner.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
