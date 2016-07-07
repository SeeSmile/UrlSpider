package spider;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import entitys.WeiBoEntity;

public class WeiBoSpider {
	
	/**
	 * 获取微博数据
	 * @param time 第几次调用此方法（用户判断获取此url的次数）
	 * @param url 微博地址
	 * @return 微博信息的实体类
	 * @throws JSONException
	 * @throws IOException
	 */
	public static WeiBoEntity getWeiBoInfo(int time, String url) throws JSONException, IOException {
		Document doc = getDoc(url);
		Document doc_html = getHtml(doc);
		try{
			WeiBoEntity entity = getBaseInfo(doc_html, formatUrl(doc.baseUri()));		
			setAttrInfo(doc_html, entity);
			return entity;
		} catch (IndexOutOfBoundsException e) {
			System.out.println(Thread.currentThread().getName() + ", " + time + ", 信息有点问题");
			//尝试三次，如果都失败了就放弃获取此数据
			if(time > 3) {
				return null;
			}
			try {
				Thread.sleep(2 * 1000);
			} catch (InterruptedException e1) {
				
			}
			int t = time + 1;
			return getWeiBoInfo(t, url);
		}
	}
	
	/**
	 * 获取指定微博地址的网页源码
	 */
	private static Document getDoc(String url) throws IOException {
		int p_url = url.indexOf("http");
		url = url.substring(p_url);
		//从网页端登录微博后，复制cookie的信息到此处
		String cookie = "SUB=_2A256f2M2DeTxGeVG4lUV8C3OyTmIHXVZDdP-rDV8PUNbmtBeLXD8kW8JQeE4i39eRpS4g24g1b4OnRhqqA..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WW-iE2zhBy2gzpd96qOMhlG5JpX5K2hUgL.FoeR1KMXeheEeo-2dJLoI7yKx--_T-iHx7tt; UOR=blog.csdn.net,widget.weibo.com,www.baidu.com; SINAGLOBAL=12122185316.828516.1457322353113; ULV=1467683607758:18:2:2:1837449900359.9565.1467683607731:1467617063643; SUHB=0FQrIiDQlr0XrL; YF-Ugrow-G0=1eba44dbebf62c27ae66e16d40e02964; YF-V5-G0=d8480b079e226c170ff763917f70c4e7";
		Document doc = Jsoup.connect(url).header("Cookie", cookie).
				header("Accept", "*/*").
				header("Accept-Encoding", "gzip, deflate, sdch").
				header("Accept-Language","zh-CN,zh;q=0.8").
				header("Connection","keep-alive").
				header("Host","weibo.com").
				header("Upgrade-Insecure-Requests","1").
				header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.111 Safari/537.36").
				get();
		return doc;
	}
	
	/**
	 * 根据网页源码提取 Html 的信息
	 * @param doc
	 * @return
	 * @throws JSONException
	 */
	private static Document getHtml(Document doc) throws JSONException {
		Elements eles_sc = doc.getElementsByTag("script");
		Elements eles_html = new Elements();
		for(Element e : eles_sc) {
			if(e.toString().contains("FM.view({") && e.toString().contains("html")) {
				eles_html.add(e);
			}
		}
		String tag = "FM.view";
		StringBuffer str_html = new StringBuffer();
		for(Element e : eles_html) {
			int p = e.toString().indexOf(tag);
			String text = e.toString().substring(p + tag.length() + 1, e.toString().length() - 1);
			JSONObject json = new JSONObject(text);
			str_html.append(json.getString("html"));
		}
		Document doc_html = Jsoup.parse(str_html.toString());
		return doc_html;
	}
	
	/**
	 * 获取最基本的信息
	 * @param doc_html
	 * @param url
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	private static WeiBoEntity getBaseInfo (Document doc_html, String url) throws IndexOutOfBoundsException{
		Elements eles_avatar = doc_html.select("p.photo_wrap");
		String avatar = eles_avatar.select("img").attr("src");
		Elements eles_name = doc_html.select("div.pf_username");
		String name = eles_name.select("h1").text();
		WeiBoEntity entity = new WeiBoEntity();
		if(eles_name.select("em").size() > 0) {
			entity.setIsVip("1");
		} else {
			entity.setIsVip("0");
		}
		String sex = doc_html.select("span.icon_bed").select("i").attr("class");
		Elements eles_num = doc_html.select("table.tb_counter").select("strong");
		entity.setUrl(formatUrl(url));
		entity.setAvatar(avatar);
		entity.setName(name);
		entity.setFans(eles_num.get(1).text());
		entity.setWbnum(eles_num.get(2).text());
		entity.setGender(formatGender(sex));
		return entity;
	}
	
	/**
	 * 设置是否有认证的信息
	 * @param doc_html
	 * @param entity
	 */
	private static void setAttrInfo(Document doc_html, WeiBoEntity entity) {
		Element ele_info = doc_html.getElementsByClass("PCD_person_info").get(0);
		//是否为认证用户
		if(ele_info.children().size() > 2) {
			Element ele_top = ele_info.getElementsByTag("div").get(0);
			String level = ele_top.select("a").get(1).text();
			String attr_info = ele_top.select("p.info").text();
			String attr_type = ele_info.select("a").get(0).attr("class");
			entity.setAtt_type(formatAtt(attr_type));
			entity.setLevel(formatLevel(level));
			entity.setAtt_info(attr_info);
			getDetailInfo(ele_info, entity);
		} else {
			getDetailInfo(ele_info, entity);
		}
	}
	
	/**
	 * 设置详情信息
	 * @param element
	 * @param entity
	 */
	private static void getDetailInfo(Element element, WeiBoEntity entity) {
		Elements eles_info = element.select("ul.ul_detail").select("li");
		for(Element ele : eles_info) {
			if(ele.select("em").toString().contains("starmark")) {
				String level = ele.select("a").text();
				entity.setLevel(formatLevel(level));
			} else if(ele.select("em").toString().contains("cd_place")) {
				String area = ele.select("span").get(1).text();
				entity.setArea(area);
			} else if(ele.select("em").toString().indexOf("pinfo") > -1) {
				String pinfo = ele.select("span").get(1).text();
				entity.setIntro(pinfo);
			} else if(ele.select("em").toString().indexOf("coupon") > -1) {
				String tag = ele.select("span").get(1).select("a").text();
				entity.setTag(tag);
			}
		}
	}
	
	/**
	 * 判断性别
	 * @param text
	 * @return
	 */
	private static String formatGender(String text) {
		if(text.contains("female")) {
			return "2";
		} else {
			return "1";
		}
	}
	
	/**
	 * 判断认证的信息(1.黄v, 2蓝v, 3达人)
	 * @param text
	 * @return
	 */
	private static String formatAtt(String text) {
		if(text.contains("co_v")) {
			return "2";
		} else if(text.contains("club")){
			return "3";
		} else {
			return "1";
		}
	}
	
	/**
	 * 格式化得到等级字符串(Lv.13)
	 * @param text
	 * @return
	 */
	private static String formatLevel(String text) {
		int p = text.indexOf(".");
		return text.substring(p + 1, text.length());
	}
	
	/**
	 * 将带？号的url简化
	 * @param text
	 * @return
	 */
	private static String formatUrl(String text) {
		int p = text.indexOf("?");
		if(p > -1)
			return text.substring(0, p);
		return text;
	}
}
