package main;

import spider.CwqSpider;
import data.Constant;

public class CwqWxInfo {
	
	public static void main(String[] args) {
		getAllWXInfo();
	}
	
	private static void getAllWXInfo() {
		String type_area = "";
		String type_type = "";
		CwqSpider spider = new CwqSpider();
		
		if(!spider.login()) {
			System.out.println("登录特么就失败了，还获取毛的数据啊");
			return;
		}
		for(int area = 1; area <= Constant.LIST_AREA.length; area++) {
			if(area == 1) {
				type_area = "3412";
			} else {
				type_area = area + "";
			}
			spider.setArea(type_area);
			for(int type = 1; type <= Constant.LIST_TYPE_ID.length; type++) {
				type_type = type + "";
				spider.setTypeId(type_type);
				for(int push = 1; push < 3; push++) {
					spider.setType(push + "");
					spider.setPage(1);
					System.out.println("获取数据: area" + " = " + type_area + 
							", type = " + type_type + ", push = " + push);
					spider.getWxData();
				}
			}
		}
	}
}
