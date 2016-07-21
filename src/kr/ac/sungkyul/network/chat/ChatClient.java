package kr.ac.sungkyul.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ChatClient {
	private static final String SERVER_IP = "220.67.115.231";
	private final static int SERVER_PORT = 3000;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket socket = null;
		Scanner scanner = null;
		BufferedReader br = null;
		PrintWriter pw = null;

		try {
			scanner = new Scanner(System.in);

			socket = new Socket();

			InetSocketAddress serverSocketAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);
			socket.connect(serverSocketAddress);

			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);

			System.out.print("닉네임>> ");
			String nickname = scanner.nextLine();
			pw.println("join:" + nickname);
			pw.flush();

			ChatClientThread cct = new ChatClientThread(br);
			cct.start();
			
			while (true) {

				// System.out.print(">> ");
				String data = scanner.nextLine();

				if (data == null) {
					System.out.println("[Client] close by server");
					break;
				}

				if ("quit".equals(data) == true) {
					pw.println("quit:");
					pw.flush();
					break;
				} else {
					// 메세지처리				// Send Message
					pw.println("message:" + data);
					pw.flush();

				}



			}

		} catch (SocketException e) {
			System.out.println("[Client] 비정상적으로 서버로 부터 연결이 끊어졌습니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (scanner != null) {
					scanner.close();
				}
				if (br != null) {
					br.close();
				}
				if (pw != null) {
					pw.close();
				}
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
