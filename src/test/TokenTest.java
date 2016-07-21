package test;

import org.json.JSONObject;

import spider.CwqSpider;
import utils.WebUtil;

public class TokenTest {

	public static void main(String[] args) throws Exception {
		String url = "http://www.yunduimedia.com/dmp/rankInfo.do?signal=sogaad&action=select_account_view&_=1468401741357";
		String result = WebUtil.request(url);
		System.out.println(result);
		int p = result.lastIndexOf("\"");
		String ooo = result.substring(1, p);
		System.out.println(ooo.replace("\\", ""));
		JSONObject json = new JSONObject(ooo);
		System.out.println(json);
	}

}
