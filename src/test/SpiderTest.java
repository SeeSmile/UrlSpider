package test;

import java.io.IOException;
import java.util.List;

import org.jsoup.select.Elements;

import entitys.ChinazEntity;

import spider.ChinaZSpider;

public class SpiderTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ChinaZSpider spider = new ChinaZSpider("腾讯网");
		ChinazEntity entity;
		entity = spider.getSearchSingle();
		System.out.println(entity);
	}

}
