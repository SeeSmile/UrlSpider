package test;

import java.io.IOException;

import utils.WebUtil;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String url = "http://mp.weixin.qq.com/mp/getmasssendmsg?__biz=MzAxMTQ3MjI0NQ==#wechat_webview_type=1&wechat_redirect";
		String result = WebUtil.sendGET(url);
		System.out.println(result);
		
	}

}
