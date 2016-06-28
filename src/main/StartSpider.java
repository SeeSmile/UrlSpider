package main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import db.UrlDB;

import entitys.ChinazEntity;

import spider.ChinaZSpider;
import utils.SFileUtil;
import utils.SFileUtil.ReadListener;

public class StartSpider {
	
	private static int count = 0;
	
	public static void main(String[] args) {
		File file = new File(SFileUtil.getDataFile("name.txt"));
		final ChinaZSpider spider = new ChinaZSpider("");
		final UrlDB db = UrlDB.getInstance();
		SFileUtil.readFileLine(file, new ReadListener() {
			
			@Override
			public void onRead(int index, String text) {
				System.out.println("index:" + index + ", " + text);
				if(index >= 971) {
					long t_start = System.currentTimeMillis();
					spider.setKeyWord(text);
					ChinazEntity entity = spider.getSearchSingle();
					if(entity != null) {
						System.out.print(entity.getTitle() + ", " + entity.getHref());
						db.addInfo(entity);
					} 
					try {
						Thread.sleep((new Random().nextInt(10) + 1) * 300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						long t_end = System.currentTimeMillis();
						System.out.println("time:" + (t_end - t_start) / 1000 + "." +
								(t_end - t_start) % 1000 / 10 + "s");
					}
					
				}
				count++;
			}
			
			@Override
			public void onFinish() {
				System.out.println("count:" + count);
			}
			
			@Override
			public void onFail() {
				
			}
		});
	}
}
