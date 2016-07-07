package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import spider.WeiBoSpider;
import utils.SFileUtil;
import utils.SFileUtil.ReadListener;
import db.WeiBoDB;
import entitys.WeiBoEntity;

public class WeiboInfo {
	
	private final int COUNT_THREAD = 20;
	private WeiBoDB mDb = WeiBoDB.getInstance();
	private List<String> mlist;
	private int current_index;
	
	public class SpiderThread extends Thread {
		
		@Override
		public void run() {
			WeiBoEntity entity;
			int index = getCurrentIndex();
			while(index < mlist.size()) {
				try {
					entity = WeiBoSpider.getWeiBoInfo(1, mlist.get(index).replace(" ", ""));
					if(entity != null) {					
						System.out.println(Thread.currentThread().getName() + ", index = " + index + entity.toString());
						mDb.insertInfo(entity);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 
				index = getCurrentIndex();
			}
			System.out.println(Thread.currentThread().getName()+ " end");
		}
	}
	
	public void run() {
		String path = SFileUtil.getDataFile("weibo.txt");
		mlist = new ArrayList<>();
		SFileUtil.readFileLine(new File(path), new ReadListener() {
			
			@Override
			public void onRead(int index, String text) {
				mlist.add(text);
			}
			
			@Override
			public void onFinish() {
				System.out.println("读取完毕");
				current_index = 9651;
				for(int i = 0; i < COUNT_THREAD; i++) {
					SpiderThread thread = new SpiderThread();
					thread.start();
				}
			}
			
			@Override
			public void onFail() {
				System.out.println("获取失败");
			}
		});
	}
	
	private int getCurrentIndex() {
		return current_index++;
	}
	
	public static void main(String[] args) {
		new WeiboInfo().run();
	}
}
