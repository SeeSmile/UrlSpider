package spider;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.SwebUtil;

import entitys.ChinazEntity;

public class ChinaZSpider {
	
	private static final String URL_SEARCH = "http://search.top.chinaz.com/Search.aspx";
	
	/**
	 * item左边图片
	 */
	private static final String CLASS_LEFT_IMG = "leftImg";
	
	/**
	 * 列表的
	 */
	private static final String CLASS_LIST_NORMAL = "clearfix";
	
	
	/**
	 * 右边文字第一行 
	 */
	private static final String CLASS_TEXT_TOP = "rightTxtHead";
	private static final String CLASS_TEXT_SECOND = "RtCPart";
	private static final String CLASS_TEXT_DATA= "RtCData";
	private static final String CLASS_TEXT_INTRODUCE = "RtCInfo";
	private static final String CLASS_RANK = "TopMain-left";
	private static final String CLASS_STAR = "pstar";
	private static final String CLASS_INFO = "TopMainTag-show";
	
	private static final String ID_LIST = "ltArealist";
	
	private String keyword;
	
	public ChinaZSpider(String keyword) {
		this.keyword = keyword;
	}

	public void setKeyWord(String key) {
		this.keyword = key;
	}
	/**
	 * 获取列表
	 * @return
	 */
	public List<ChinazEntity> getSearchList() {
		List<ChinazEntity> list = new ArrayList<ChinazEntity>();
		String url = URL_SEARCH;
		try {
			Document doc = Jsoup.connect(url).get();
			Element ele = doc.getElementById(ID_LIST);
			if(ele == null) {
				return null;
			}
			Elements eles = ele.select("li." + CLASS_LIST_NORMAL);
			getEntityList(list, eles);
			return list;
		} catch (IOException e) {
			System.out.println("io exception");
		}
		return null;
	}
	
	public ChinazEntity getSearchSingle(){
		String url = URL_SEARCH;
		Document doc;
		try {
			doc = Jsoup.connect(url)
					.timeout(10 * 1000)
					.userAgent("Chrome")
					.data("url", keyword)
					.get();
			Element ele = doc.getElementById(ID_LIST);
			if(ele == null) {
				return null;
			}
			Elements eles = ele.select("li." + CLASS_LIST_NORMAL);
			if(eles == null || eles.size() == 0) {
				return null;
			}
			return getEntity(eles.get(0));
		} catch (IOException e) {
			System.out.println("io ex");
		}
		return null;
	}
	
	private void getEntityList(List<ChinazEntity> list, Elements eles) throws IOException {
		for(Element ele : eles) {
			list.add(getEntity(ele));
		}
	}
	
	private ChinazEntity getEntity(Element ele) throws IOException {
		ChinazEntity entity = new ChinazEntity();
		String left_img = ele.getElementsByClass(CLASS_LEFT_IMG).select("img").attr("src");
		Element ele_top = ele.getElementsByClass(CLASS_TEXT_TOP).select("a").get(0);
		String title = ele_top.attr("title");
		String href = ele_top.attr("href");
		String url = ele_top.select("span").text();
		Element ele_data = ele.getElementsByClass(CLASS_TEXT_SECOND).get(0);
		Elements eles_count = ele_data.select("p." + CLASS_TEXT_DATA);
		String introduce = ele.select("p." + CLASS_TEXT_INTRODUCE).text();
		String count_alexa = "", count_baidu = "", count_pr = "", count_link = "";
		for(int i = 0; i < eles_count.size(); i++) {
			Element ele_count = eles_count.get(i);
			switch (i) {
				case 0:
					count_alexa = ele_count.select("a").text();
					continue;
				case 1:
					count_baidu = getCount(ele_count.select("img").attr("src"));
					continue;
				case 2:
					count_pr = getCount(ele_count.select("img").attr("src"));
					continue;
				case 3:
					count_link = ele_count.select("a").text();
					continue;
				default:
					continue;
			}
		}
		entity.setImage(left_img);
		entity.setTitle(title);
		entity.setHref(href);
		entity.setUrl(url);
		entity.setCount_alexa(count_alexa);
		entity.setCount_baidu(count_baidu);
		entity.setCount_pr(formatCountPr(count_pr));
		entity.setCount_link(count_link);
		entity.setIntroduce(introduce);
		getDetailInfo(entity);
		return entity;
	}
	
	private void getDetailInfo(ChinazEntity entity) throws IOException {
		Document doc = Jsoup.connect(entity.getHref()).get();
		Elements eles_rank = doc.getElementsByClass(CLASS_RANK).select("li");
		String ranking_all = "", ranking_area = "", ranking_net = "";
		for(int i = 0; i < eles_rank.size(); i++) {
			String rank;
			try {
				rank = eles_rank.get(i).select("a").get(0).text();
			} catch (Exception e) {
				//如果没有找到a标签，说明为0
				rank = "0";
			}
			if(i == 0) {
				ranking_all = rank;
			} else if(i == 1) {
				ranking_area = rank;
			} else {
				ranking_net = rank;
			}
		}
		String star = doc.getElementsByClass(CLASS_STAR).select("img").attr("src");
		Elements eles_info = doc.getElementsByClass(CLASS_INFO).select("p");
		String type = eles_info.get(0).select("a").text();
		String area = eles_info.get(1).select("a").text();
		entity.setRanking_all(ranking_all);
		entity.setRanking_area(ranking_area);
		entity.setRanking_net(ranking_net);
		entity.setStar(getStar(star));
		entity.setType(type);
		entity.setArea(area);
	}
	
	private String getCount(String text) {
		int p_end = text.lastIndexOf("/");
		int p_end2 = text.lastIndexOf(".");
		return text.substring(p_end + 1, p_end2); 
	}
	
	private String getStar(String text) {
		try {
			int p_end = text.lastIndexOf("_");
			int p_end2 = text.lastIndexOf(".");
			return text.substring(p_end + 1, p_end2);
		} catch (Exception e) {
			return "0";
		} 
	}
	
	private String formatCountPr(String text) {
		int p = text.indexOf("_");
		if(p != -1) {
			return text.substring(p + 1, text.length());
		} else {
			return "0";
		}
	}
}
