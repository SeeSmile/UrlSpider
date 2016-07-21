package spider;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import data.SoGouWX;

import utils.WebUtil;

public class WeiXinSpider extends BaseSpider {

	private final static String URL_SEARCH = "http://chuanbo.weiboyi.com/hworder/weixin/filterlist/source/all?";
	private final static String URL_ARTICLE_LIST = "http://chuanbo.weiboyi.com/single/api/getnewestarticleslist?";
	
	
	public WeiXinSpider() {
		super(Type.WEIXIN);
	}

	public static boolean login(String code) {
		return WbySpider.login(code);
	}
	
	private static String getUrl(String account) {
		StringBuffer buffer = new StringBuffer(URL_SEARCH);
		buffer.append("keyword_only=").append("1").append("&");
		buffer.append("limit=").append("20").append("&");
		buffer.append("price_keys[other]=").append("1").append("&");
		buffer.append("price_keys[second]=").append("1").append("&");
		buffer.append("price_keys[single]=").append("1").append("&");
		buffer.append("price_keys[top]=").append("1").append("&");
		buffer.append("price_keys[writing]=").append("1").append("&");
		buffer.append("query=").append(account).append("&");
		buffer.append("start=").append("0").append("&");
		buffer.append("web_csrf_token=").append(WbySpider.mToken);
		return buffer.toString();
	}
	
	private static String getArticleUrl(int p, String sign, String account) {
		StringBuffer buffer = new StringBuffer(URL_ARTICLE_LIST);
		buffer.append("limit=").append("10").append("&");
		buffer.append("offset=").append(p * 10).append("&");
		buffer.append("sign=").append(sign).append("&");
		buffer.append("web_csrf_token=").append(WbySpider.mToken).append("&");
		buffer.append("weibo_id=").append(account).append("&");
		buffer.append("weibo_type=").append("9");
		return buffer.toString();
	}
	
	public static List<SoGouWX> getArticleList(String account, String sign) throws IOException{
		List<SoGouWX> list = new ArrayList<>();
		for(int p = 0; p < 3; p++) {
			String url = getArticleUrl(p, sign, account);
			String result = WebUtil.sendGET(url);
			JSONObject json;
			try {
				json = new JSONObject(result);
				JSONArray array = json.getJSONArray("data");
				for(int i = 0; i < array.length(); i++) {
					SoGouWX wx = new SoGouWX();
					JSONObject info = array.getJSONObject(i);
					wx.setFileid(info.optString("id"));
					wx.setLike_num(info.optString("msg_like_num"));
					wx.setRead_num(info.optString("msg_read_num"));
					wx.setSubtitle(info.optString("digest"));
					wx.setTime(System.currentTimeMillis() / 1000 + "");
					wx.setTitle(info.optString("title"));
					wx.setType("1");
					wx.setUrl(info.optString("url"));
					list.add(wx);
				}
			} catch (JSONException e) {
				break;
			}
		}
		return list;
	}
	
	public static String getResarchSign(String account) throws JSONException {
		String url = getUrl(account);
		String result = "";
		try {
			result = WebUtil.sendGET(url);
		} catch (IOException e) {
			try {
				Thread.sleep(2 * 1000);
			} catch (InterruptedException e1) {
			}
			return getResarchSign(account);
		}
		JSONObject json = new JSONObject(result);
		JSONObject json_data = json.getJSONObject("data");
		int total = json_data.optInt("total");
		if(total > 0) {
			JSONObject json_cell = json_data.getJSONArray("rows").getJSONObject(0).getJSONObject("cells");
			String link = json_cell.getString("url");
			int p = link.indexOf("id=");
			int p_end = link.indexOf("&", p);
			String name = link.substring(p + 3, p_end);
			if(account.contains(name)) {				
				p_end = link.lastIndexOf("=");
				String sign = link.substring(p_end + 1);
				return sign;
			}
		} else {
			System.out.println("没有搜索结果");
		}
		return null;
	}
}
