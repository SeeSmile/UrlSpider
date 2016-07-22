package test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exception.AccountException;
import utils.SFileUtil;
import utils.SFileUtil.ReadListener;
import weixin.WeiXinUtil;

public class TestBiz {

	private static List<String> list_account;
	private static int current_index = 50000;
	private static int all_count;
	private static File file_cwq;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new TestBiz().getInfo();

	}
	
	public void getInfo() {
		String path = SFileUtil.getDataFile("data.txt");
		final File file_data = new File(path);
		list_account = new ArrayList<>();
		SFileUtil.readFileLine(file_data, new ReadListener() {
			
			@Override
			public void onRead(int index, String text) {
				System.out.println(index + " 读取中: " + text);
				list_account.add(text);
			}
			
			@Override
			public void onFinish() {
				all_count = list_account.size();
				System.out.println("读取内容到内存完毕!!! size = " + all_count);
				file_cwq = new File(file_data.getParent(), "cwq.txt");
				if(!file_cwq.exists()) {
					try {
						file_cwq.createNewFile();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
			}
			
			@Override
			public void onFail() {
				System.out.println("读取文件失败!!!");
			}
		});
		for(int i = 0; i < 5; i++) {
			new CwqBizThread().start();
		}
	}
	
	private synchronized int getCurrentIndex() {
		return current_index++;
	}
	
	private class CwqBizThread extends Thread {
		
		@Override
		public void run() {
			int index = getCurrentIndex();
			while(index != all_count) {
				String account = list_account.get(index);
				System.out.println("current_index = " + index + ", account = " + account);
				try {
					String id = WeiXinUtil.getBizFCwq(account);
					String biz = WeiXinUtil.getBizByCwqId(id);
					if(biz.trim().length() > 0) {
						System.out.println(biz);
						SFileUtil.writeText2File(file_cwq.getAbsolutePath(), account + "||" + biz);
					}
				} catch (AccountException e) {
					System.out.println("account not exist");
				}

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				index = getCurrentIndex();
			}
			
		}
	}

}
