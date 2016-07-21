package test;

import java.io.IOException;
import java.sql.SQLException;

import utils.WebUtil;

import db.SpiderWxDB;

public class TestChange {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String result = WebUtil.sendGET("http://chuanbo.weiboyi.com/hworder/sina?category_filter=01&price_keys[tweet]=1&price_keys[retweet]=1&price_keys[reservation]=1&start=0&limit=20&web_csrf_token=577f011f9b01e");
		System.out.println(result);
	}

}
