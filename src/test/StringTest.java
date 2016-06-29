package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;

import db.UrlDB;
import entitys.ChinazEntity;

import spider.ChinaZSpider;
import utils.SFileUtil;
import utils.SwebUtil;

public class StringTest {
	
	private static int mPage;
	private static final String URL_FIRST = "http://top.chinaz.com/hangye/";
	private static final String URL_PAGE = "http://top.chinaz.com/hangye/index_";
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		getAll();
	}
	
	private static void getZhiding() {
		UrlDB db = UrlDB.getInstance();
		List<String> list = db.getAllInfo();
		List<Map<String, String>> list_title = new ArrayList<Map<String,String>>();
		JSONObject json = new JSONObject();
		for(String t : list) {
			try {
				json = new JSONObject(t);
				String from = json.optString("from");
//				System.out.println("from:" + from);
				if(from == null || from.trim().length() == 0) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("title", json.optString("title"));
					map.put("href", json.optString("href"));
					list_title.add(map);
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		System.out.println("list size is " + list_title.size());
		ChinaZSpider spider = new ChinaZSpider("腾讯");
//		System.out.println(spider.getSearchSingle().getHref());
		int index = 1;
		for(Map<String, String> map : list_title) {
			String title = map.get("title");
			System.out.println(index + " : " + title);
			spider.setKeyWord(title);
			ChinazEntity entity = spider.getSearchSingle();
			if(entity != null) {
				db.addInfo(entity);
			} else {
				db.deleteByHref(map.get("href"));
			}
			try {
				Thread.sleep((new Random().nextInt(10) + 1) * 500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			index++;
		}
	}

	
	private static void getAll() {
		int startPage = 2;
		String url = "";
		ChinaZSpider spider = new ChinaZSpider("");
		List<ChinazEntity> list = new ArrayList<ChinazEntity>();
		UrlDB db = UrlDB.getInstance();
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
