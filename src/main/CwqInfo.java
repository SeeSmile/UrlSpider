package main;

import spider.CwqSpider;
import data.Constant;

public class CwqInfo {
	
	public static void main(String[] args) {
		new CwqInfo().startThreadSpider();
	}
	
	public void startThreadSpider() {
//		new SpiderThread(1, 3).start();
//		new SpiderThread(4, 10).start();
//		new SpiderThread(11, 20).start();
		new SpiderThread(1, Constant.LIST_AREA.length).start();
	}
	
	private class SpiderThread extends Thread {
		
		private int start;
		private int end;
		
		public SpiderThread(int start, int end) {
			this.start = start;
			this.end = end;
		}
		
		@Override
		public void run() {
			getAllWBInfoFamous(start, end);
//			getAllWBInfo(start, end);
//			getAllWXInfo(start, end);
		}
	}
	
	private static void getAllWXInfo(int start, int end) {
		
		String type_area = "";
		String type_type = "";
		CwqSpider spider = new CwqSpider();
		
		if(!spider.login()) {
			System.out.println("登录特么就失败了，还获取毛的数据啊");
			return;
		}
		
		for(int area = start; area <= end; area++) {
			if(area == 1) {
				type_area = "3412";
			} else {
				type_area = area + "";
			}
			spider.setArea(type_area);
			for(int type = 1; type <= Constant.LIST_TYPE_ID_WB_CWQ.length; type++) {
				type_type = type + "";
				spider.setTypeId(type_type);
//				for(int push = 1; push < 3; push++) {
					spider.setType("");
					spider.setPage(1);
					System.out.println(Thread.currentThread().getName() + ":获取数据: area" + " = " + type_area + 
							", type = " + type_type);
					spider.getWxData();
//				}
			}
		}
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
			for(int type = 1; type <= Constant.LIST_TYPE_ID_WB_CWQ.length; type++) {
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
	
	private static void getAllWBInfo(int start, int end) {
		String type_area = "";
		String type_type = "";
		CwqSpider spider = new CwqSpider();
		
		if(!spider.login()) {
			System.out.println("登录特么就失败了，还获取毛的数据啊");
			return;
		}
		for(int area = start; area <= end; area++) {
			if(area == 1) {
				type_area = "3412";
			} else {
				type_area = area + "";
			}
			spider.setArea(type_area);
			for(int type = 1; type <= Constant.LIST_TYPE_ID_WB_CWQ.length; type++) {
				type_type = type + "";
				spider.setTypeId(type_type);
				spider.setPage(1);
				spider.getWbData();
			}
		}
	}
	
	private static void getAllWBInfoFamous(int start, int end) {
		String type_area = "";
		String type_type = "";
		CwqSpider spider = new CwqSpider();
		
		if(!spider.login()) {
			System.out.println("登录特么就失败了，还获取毛的数据啊");
			return;
		}
		for(int area = start; area <= end; area++) {
			if(area == 1) {
				type_area = "3412";
			} else {
				type_area = area + "";
			}
			spider.setArea(type_area);
			for(int type = 1; type <= Constant.LIST_TYPE_ID_WB_CWQ.length; type++) {
				type_type = type + "";
				spider.setTypeId(type_type);
				spider.setPage(1);
				spider.getWbDataFamous();
			}
		}
	}
	
	private static void getAllWBInfo() {
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
			for(int type = 1; type <= Constant.LIST_TYPE_ID_WB_CWQ.length; type++) {
				type_type = type + "";
				spider.setTypeId(type_type);
				spider.setPage(1);
				spider.getWbData();
			}
		}
	}
}
