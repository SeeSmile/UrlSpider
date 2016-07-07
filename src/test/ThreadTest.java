package test;

import java.util.ArrayList;
import java.util.List;

public class ThreadTest {
	
	private List<String> mlist;
	private int current;
	
	public class TestThread extends Thread {
		@Override
		public void run() {
			
			int index = finishOut();
			while(index < mlist.size()) {
//				try {
//					Thread.sleep(500);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
				System.out.println(Thread.currentThread().getName() + " current list " + mlist.get(index));
				index = finishOut();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ThreadTest().start();
	}
	
	public int finishOut() {
		return current++;
	}
	
	public void start() {
		current = 0;
		mlist = new ArrayList<>();
		for(int i = 0; i < 100; i++) {
			mlist.add("" + i);
		}
		for(int i = 0; i < 5; i++) {
			new TestThread().start();
		}
	}

}
