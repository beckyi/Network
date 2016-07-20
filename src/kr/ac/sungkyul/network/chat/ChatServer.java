package kr.ac.sungkyul.network.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	private final static int SERVER_PORT = 3000;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		List<Writer> listWriters = new ArrayList<Writer>();

		try {
			serverSocket = new ServerSocket();

			InetAddress inetAddress = InetAddress.getLocalHost();
			String serverAddress = inetAddress.getHostAddress();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(serverAddress, SERVER_PORT);

			serverSocket.bind(inetSocketAddress);
			System.out.println("[Server bind] - " + serverAddress + ": " + SERVER_PORT);

			while (true) {
				Socket socket = serverSocket.accept();

				ChatServerThread thread = new ChatServerThread(socket, listWriters);
				thread.start();
			}

		} catch (SocketException e) {
			System.out.println("[Server] 비정상적으로 클라이언트가 연결을 끊었습니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				// 서버 소켓 닫기
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
