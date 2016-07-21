package kr.ac.sungkyul.network.chat;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ChatServerThread extends Thread {
	private String nickname;
	private Socket socket;
	List <Writer> listWriters;
	PrintWriter pw;
	
	public ChatServerThread(Socket csocket, List<Writer> listWriters) {
		this.socket = csocket;
		this.listWriters = listWriters;
	}
	
	public ChatServerThread(String nickname, Socket socket){
		this.nickname = nickname;
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			
			// IOStream 받아오기
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
			
			//요청 처리
			while (true) {
				// 데이터 읽기
				String request = br.readLine(); //클라이언트가  pw로 println 할 때까지 대기
			
				if (request == null) {
					// 클라이언트로 부터 정상 종료
					System.out.println("closed by client");
					break;
				}
				
				//프로토콜 분석
				String[] tokens = request.split(":");
				
				if("join".equals(tokens[0])){
					doJoin(tokens[1],pw);
				}  else if("message".equals(tokens[0])){
					doMessage(tokens[1]);
				} else if("quit".equals(tokens[0])){
					doQuit(pw);
				} else{
					System.out.println("에러: 알 수 없는 요청("+tokens[0]+")");
				}

				// 출력
				System.out.println(request);


			}
		} catch (SocketException e) {
			System.out.println("비정상적으로 클라이언트가 연결을 끊었습니다." + e);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 데이터 통신 소켓 닫기
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void doJoin(String nickname, Writer writer) {
		this.nickname = nickname;
		
		String data = nickname + "님이 입장하였습니다.";
		broadcast(data);
		
		addWriter(writer);	//나중에 추가하여 나 제외한 사람들에게 뿌려줌

		//ack
		pw.println("join:ok");
		pw.flush();
	}
	
	private void doQuit(Writer writer) {
		removeWriter(writer);
		
		String data = nickname + "님이 퇴장하였습니다.";
		broadcast(data);
	}
	
	private void addWriter(Writer writer) {
		synchronized(listWriters){
			listWriters.add(writer);
		}
	}
	
	private void removeWriter(Writer writer) {
		synchronized(listWriters){
			listWriters.remove(writer);
		}
	}

	private void doMessage(String message) {
		//pw.println("message:"+message);
		//pw.flush();
		
		String data = nickname + ":" + message;
		broadcast(data);
	}
	
	private void broadcast(String data) {
		synchronized(listWriters){
			for(Writer writer: listWriters){
				PrintWriter printWriter = (PrintWriter)writer;
				printWriter.println(data);
				printWriter.flush();
			}
		}
	}
}
