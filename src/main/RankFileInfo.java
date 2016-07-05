package main;

import java.io.File;
import java.util.Random;
import db.ChinazDB;
import entitys.ChinazEntity;
import spider.ChinaZSpider;
import utils.SFileUtil;
import utils.SFileUtil.ReadListener;

public class RankFileInfo {
	
	private static int count = 0;
	private static int START_LINE = 1;
	
	public static void main(String[] args) {
		//从data目录读取name.txt文件，必须要先有这个文件
		File file = new File(SFileUtil.getDataFile("name.txt"));
		SFileUtil.readFileLine(file, new ReadListener() {
			ChinazDB db = ChinazDB.getInstance();
			ChinaZSpider spider = new ChinaZSpider("");
			@Override
			public void onRead(int index, String text) {
				System.out.println("index:" + index + ", " + text);
				if(index >= START_LINE) {
					long t_start = System.currentTimeMillis();
					spider.setKeyWord(text);
					ChinazEntity entity = spider.getSearchSingle(); 
					if(entity != null) {
						System.out.println(entity.getTitle() + ", " + entity.getHref());
						db.addInfo(entity);
					} 
					try {
						Thread.sleep((new Random().nextInt(10) + 1) * 500);
					} catch (InterruptedException e) {
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
				System.out.println("读取文件失败");
			}
		});
	}
}
