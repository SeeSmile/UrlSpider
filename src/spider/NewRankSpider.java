package spider;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import utils.WebUtil;

public class NewRankSpider extends BaseSpider {

	private static final String TAG_LIST = "info_detail_article_lastest";
	private static final String URL_DETAIL = "http://www.newrank.cn/public/info/detail.html?account=";
	
	public NewRankSpider(Type type) {
		super(Type.NEWRANK);
	}
	
	public static void getRank() throws Exception {
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("uuid", "A1E3865ECA68AE4A5F49A4C36AB85BD0"));
		params.add(new BasicNameValuePair("nonce", "980f37860"));
		params.add(new BasicNameValuePair("xyz", "723ffa9d968dfaa08127050dc0ecc90d"));
		String result = WebUtil.sendPOST("http://www.newrank.cn/xdnphb/data/getAccountArticle", params);
//		JSONObject json = new JSONObject(result);
		System.out.println(result);
	}
	
}
