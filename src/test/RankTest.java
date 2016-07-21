package test;

import java.sql.SQLException;

import db.SpiderWbDB;
import spider.CwqSpider;
import spider.NewRankSpider;

public class RankTest {
	
	public static void main(String[] args) {
		CwqSpider spider = new CwqSpider();
		if(spider.login()) {
			spider.getWbDataFamous();
		}
		
	}
}
