package kr.ac.sungkyul.thread;

public class DigitThread extends Thread {

	@Override
	public void run() {
		
		for(int j =0; j<9; j++){
			System.out.print(j);
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
