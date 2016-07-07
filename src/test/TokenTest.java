package test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import db.WeiBoDB;

import entitys.WeiBoEntity;

import spider.CwqSpider;
import spider.WeiBoSpider;
import sun.security.krb5.Config;
import utils.ConfigUtil;
import utils.SFileUtil;
import utils.SFileUtil.ReadListener;
import utils.WebUtil;

public class TokenTest {

	public static void main(String[] args) throws Exception {
		String url = "http://www.cwq.com/Owner/Weibo/getAccountInfo/";
		CwqSpider sp = new CwqSpider();
		sp.login();
		sp.getWbData();
		
	}
	

	
}
