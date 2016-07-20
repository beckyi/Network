package kr.ac.sungkyul.network.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class EchoClient {
	private static final String SERVER_IP = "220.67.115.231";
	private final static int SERVER_PORT = 1004;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket socket = null;

		try {
			socket = new Socket();

			InetSocketAddress serverSocketAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);
			socket.connect(serverSocketAddress);

			while (true) {
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				
				Scanner scanner = new Scanner(System.in);
				System.out.print(">> ");
				String data = scanner.nextLine();
				
				if("exit".equals(data)){
					break;
				}
				
				os.write(data.getBytes("UTF-8"));

				byte[] buffer = new byte[256];
				int readbytes = is.read(buffer);

				if (readbytes <= -1) {
					System.out.println("[EchoClient] close by server");
					;
					break;
				}

				String message = new String(buffer, 0, readbytes, "UTF-8");
				System.out.println("<< " + message);
				
			}
		} catch (SocketException e) {
			System.out.println("[EchoClient] 비정상적으로 서버로 부터 연결이 끊어졌습니다.");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
