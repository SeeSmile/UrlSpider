package test;

import java.io.IOException;

import org.json.JSONException;

import db.WeiXinDB;

import spider.WbySpider;
import spider.WeiXinSpider;

public class TestToken {
	public static void main(String[] args) {
		try {
			WeiXinDB.getInstance().getAllAccount();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
