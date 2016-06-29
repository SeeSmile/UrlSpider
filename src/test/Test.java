package test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import utils.SwebUtil;

public class Test {

	private static final String api = "http://www.atool.org/httptest.php";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("url", "http://search.top.chinaz.com/Search.aspx?url=%u745E%u4E3D%u65C5%u6E38");
		data.put("method", "GET");
		try {
			String result = SwebUtil.doPortGet("http://search.top.chinaz.com/Search.aspx?url=%u745E%u4E3D%u65C5%u6E38");
			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}

}
