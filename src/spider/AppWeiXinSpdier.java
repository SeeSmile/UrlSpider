package spider;

import java.io.IOException;
import utils.WebUtil;

public class AppWeiXinSpdier extends BaseSpider {
	
	public static final String URL_LIST = "http://mp.weixin.qq.com/mp/getmasssendmsg?";
	public static final String pass_ticket = "TP9qG0jR14XdBERU5c7oUU7AYLp12UsHc4ojH8LwHKneGvKGKhZ7xRJ2fj7TgonP";
	public static final String MOUDLE = "&devicetype=android-21&version=2603163b&lang=zh_CN&nettype=WIFI";
	public static final String uin = "MjY1Mzk0NTU0MA==";
	public static String key = "";

	public AppWeiXinSpdier(Type type) {
		super(Type.APPWEIXIN);
	}

	public static void main(String[] args) throws IOException {
		//配置key
		key = "77421cf58af4a6535f8551a50eb0b681d80ca70257798168775d53e693feac9091742ea51a022b0eace9b30f00729c42";
		String biz = "MzIyODQzNjg5Ng==";
		String result = getArticleList(biz);
		System.out.println(result);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取文章列表
	 * @return
	 * @throws IOException
	 */
	public static String getArticleList(String biz) throws IOException {
		String url = URL_LIST + "__biz=" + biz + "&uin=" + uin + "&key=" + key + MOUDLE + "&pass_ticket=" + pass_ticket;
		String result = WebUtil.sendGET(url);
		System.out.println(result);
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
	
	public static void getReadNum() throws IOException {
		String url = "http://mp.weixin.qq.com/s?__biz=MzIzODQ3NDE4Mw==&mid=2247483657&idx=1&sn=e28743c896f86908d41bdf9698057f6b&scene=4#wechat_redirect";
		String url2 = "http://mp.weixin.qq.com/mp/getappmsgext?__biz=MzIzODQ3NDE4Mw==&appmsg_type=9&mid=2247483657&sn=e28743c896f86908d41bdf9698057f6b&idx=1&scene=1&title=%E6%B2%A1%E6%9C%89%E9%94%80%E5%94%AE%E5%91%98%EF%BC%81%E8%BF%99%E5%AE%B6%E5%88%9B%E4%B8%9A%E5%85%AC%E5%8F%B8%E7%AB%9F%E7%84%B6%E8%BF%9E%E7%BB%AD10%E5%B9%B4%E7%9B%88%E5%88%A9&ct=1469145478&devicetype=android-21&version=/mmbizwap/zh_CN/htmledition/js/appmsg/index2f3f48.js&f=json&r=0.41275622765533626&is_need_ad=1&comment_id=0&is_need_reward=0&both_ad=1&reward_uin_count=0&uin=MjY1Mzk0NTU0MA%253D%253D&key=77421cf58af4a6539fc1ee5fa64c67f844815b8b1348d65f0cd58f3f8bf5a3b6e300758c3114d6f0c3a1dc102029aef6&pass_ticket=TP9qG0jR14XdBERU5c7oUU7AYLp12UsHc4ojH8LwHKneGvKGKhZ7xRJ2fj7TgonP&wxtoken=3379625391&devicetype=android-21&clientversion=2603163b&x5=1";
		String result = WebUtil.sendGET(url);
		String result2 = WebUtil.sendGET(url2);
		System.out.println(result);
		System.out.println(result2);
	}
}
