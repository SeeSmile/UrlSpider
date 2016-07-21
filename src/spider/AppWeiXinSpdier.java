package spider;

import java.io.IOException;
import utils.WebUtil;

public class AppWeiXinSpdier extends BaseSpider {
	
	public static final String URL_LIST = "http://mp.weixin.qq.com/mp/getmasssendmsg?";
	public static final String pass_ticket = "PYPrNO5FrKWiGOzAyW+pjQ7sBZW2DrxaEt39TCdIK6HFqHUk2wtXBf3WVSHrQXzA";
	public static final String MOUDLE = "&devicetype=android-21&version=2603163b&lang=zh_CN&nettype=WIFI";
	public static final String uin = "MjY1Mzk0NTU0MA==";
	public static String key = "";

	public AppWeiXinSpdier(Type type) {
		super(Type.APPWEIXIN);
	}

	public static void main(String[] args) throws IOException {
		//配置key
		key = "77421cf58af4a653d08bd87c0307ee69bedeb6892c6276968ecd546c4d1fe2d9af3d54793c9b14a69aa8ac8d3e5386c7";
		String biz = "MzI3NzMxOTcwNw==";
		String result = getArticleList(biz);
		System.out.println(result);
	}
	
	/**
	 * 获取文章列表
	 * @return
	 * @throws IOException
	 */
	public static String getArticleList(String biz) throws IOException {
		String url = URL_LIST + "__biz=" + biz + "&uin=" + uin + "&key=" + key + MOUDLE + "&pass_ticket=" + pass_ticket;
		String result = WebUtil.sendGET(url);
		int p_start = result.indexOf("msgList");
		int p_end = result.indexOf("if(!!window");
		String str_msg = result.substring(p_start, p_end);
		p_start = str_msg.indexOf("{");
		p_end = str_msg.lastIndexOf("}");
		str_msg = str_msg.substring(p_start, p_end + 1);
		str_msg = str_msg.replaceAll("&quot;", "\"")
				.replaceAll("amp;", "")
				.replaceAll("&nbsp;", " ");
		return str_msg;
	}
}
