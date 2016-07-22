package weixin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mongodb.util.JSON;

import exception.AccountException;

import spider.CwqSpider;
import spider.WbySpider;
import utils.WebUtil;

public class WeiXinUtil {
	//+城外圈微信账号的id.html
	private final static String URL_ID_CWQ = "http://www.cwq.com/weixin/";
	private final static String URL_RESARCH_CWQ = "http://www.cwq.com/Owner/Weixin/get_weixin_list/";
	private final static String URL_RESARCH_WBY = "http://chuanbo.weiboyi.com/hworder/weixin/filterlist/source/all?";
	private final static String URL_MAIN_WBY = "http://chuanbo.weiboyi.com";
	private final static String URL_ID_WBY = "http://chuanbo.weiboyi.com/single/api/gethotarticles?";
	private static boolean isLogin = false;
	
	public static String getBizFCwq(String account) throws AccountException {
		CwqSpider spider = new CwqSpider();
		if(!isLogin) {
			if(spider.login()) {
				isLogin = true;
			} else {
				isLogin = false;
				return "";
			}
		}
		List<NameValuePair> param = new ArrayList<>();
		param.add(new BasicNameValuePair("zfjg_type", "2"));
		param.add(new BasicNameValuePair("account", account));
		String result;
		try {
			result = WebUtil.sendPOST(URL_RESARCH_CWQ, param);
			JSONObject json = new JSONObject(result);
			JSONObject json_data = json.getJSONObject("data");
			int count = json_data.getInt("count");
			if(count > 0) {
				JSONArray array = json_data.getJSONArray("list");
				String bs_account = array.getJSONObject(0).getString("bs_weixinhao");
				if(formatAccount(bs_account).equals(account)) {
					String id = array.getJSONObject(0).getString("bs_id");
					return id;
				}
			}
		} catch (Exception e) {
			
		}
		throw new AccountException();
		
	}
	
	public static String getBizByCwqId(String id) {
		try {
			Document doc = Jsoup.connect(URL_ID_CWQ + id +  ".html").get();
			int p_s = doc.toString().indexOf("__biz=");
			if(p_s > -1) {
				String text = doc.toString().substring(p_s);
				int p_e = text.indexOf("==");
				String biz = text.substring("__biz=".length(), p_e + 2);
				return biz;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	private static String formatAccount(String account) {
		int p_s = account.indexOf(">");
		int p_e = account.lastIndexOf("<");
		return account.substring(p_s + 1, p_e);
	}
	
	public static String getSignFWby(String account) throws AccountException, IOException{
		StringBuffer buffer = new StringBuffer(URL_RESARCH_WBY);
		buffer.append("limit=").append("20").append("&");
		buffer.append("keyword_only=").append("1").append("&");
		buffer.append("price_keys[other]=").append("1").append("&");
		buffer.append("price_keys[second]=").append("1").append("&");
		buffer.append("price_keys[single]=").append("1").append("&");
		buffer.append("price_keys[top]=").append("1").append("&");
		buffer.append("price_keys[writing]=").append("1").append("&");
		buffer.append("start=").append("0").append("&");
		buffer.append("query=").append(account).append("&");
		buffer.append("web_csrf_token=").append(WbySpider.mToken);
		String result = WebUtil.sendGET(buffer.toString());
		try {
			JSONObject json = new JSONObject(result);
			int total = json.getJSONObject("data").getInt("total");
			if(total > 0) {
				JSONArray array = json.getJSONObject("data").getJSONArray("rows");
				for(int i = 0; i < array.length(); i++) {
					JSONObject json_item = array.getJSONObject(i).getJSONObject("cells");
					String weibo_id = json_item.getString("weibo_id");
					if(weibo_id.equals(account)) {
						String url = json_item.getString("url");
						int p = url.indexOf("?");
						return url.substring(p + 1, url.length());
					}
				}
			}
		} catch (JSONException e) {
			System.out.println("解析json出错");
		}
		throw new AccountException();
	}
	
	public static String getBizByWbyUrl(String url) throws IOException, AccountException {
		String get_url = URL_ID_WBY + url + "&web_csrf_token=" + WbySpider.mToken;
		String result = WebUtil.sendGET(get_url);
		JSONObject json = null;
		try {
			json = new JSONObject(result);
			JSONArray array = json.getJSONArray("data");
			if(array.length() > 0) {
				String msg_url = array.getJSONObject(0).getString("url");
				int p_e = msg_url.indexOf("__biz=");
				msg_url = msg_url.substring(p_e);
				p_e = msg_url.indexOf("==");
				String biz = msg_url.substring("__biz=".length(), p_e + 2);
				return biz;
			}
		} catch (JSONException e) {
			
			System.out.println("解析详细信息json错误:" + json.toString());
		}
		throw new AccountException();
	}
	
}
