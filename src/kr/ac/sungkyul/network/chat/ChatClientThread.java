package kr.ac.sungkyul.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ChatClientThread extends Thread {

	private BufferedReader br;

	// Socket socket;

	public ChatClientThread(BufferedReader br) {
		this.br = br;
		// this.socket = socket;
	}

	@Override
	public void run() {

		// message 처리
		try {

			// br = new BufferedReader(new
			// InputStreamReader(socket.getInputStream(), "utf-8"));
			while(true) {
				String text = br.readLine();

				if (text == null) {
					// 클라이언트로 부터 정상 종료
					System.out.println("closed by client");
					return;
				}

				// 출력
				System.out.println(text);
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
