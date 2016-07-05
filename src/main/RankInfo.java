package main;

import java.util.ArrayList;
import java.util.List;

import spider.ChinaZSpider;
import db.ChinazDB;
import entitys.ChinazEntity;

public class RankInfo {

	private static final String URL_FIRST = "http://top.chinaz.com/hangye/";
	private static final String URL_PAGE = "http://top.chinaz.com/hangye/index_";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		getAll();
	}

	private static void getAll() {
		int startPage = 15911;
		String url = "";
		ChinaZSpider spider = new ChinaZSpider("");
		List<ChinazEntity> list = new ArrayList<ChinazEntity>();
		ChinazDB db = ChinazDB.getInstance();
		for(int i = startPage; i <= 1707; i++) {
			if(i == 1) {
				url = URL_FIRST;
			} else {
				url = URL_PAGE + i + ".html";
			}
			list = spider.getSearchList(url);
			if(list == null || list.size() == 0) {
				System.out.println("没有获取到列表");
			} else {
				for(ChinazEntity entity : list) {
					db.addRankInfo(entity);
				}
				db.close();
				System.out.println("加入了列表：" + list.size());
			}
		}
	}
}
