package test;

import java.io.IOException;

import utils.WebUtil;

public class TestBizWby {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String URL_RESARCH_WBY = "http://chuanbo.weiboyi.com/hworder/weixin/filterlist/source/all?web_csrf_token=5791918bc5888&price_keys%5Btop%5D=1&price_keys%5Bsingle%5D=1&price_keys%5Bsecond%5D=1&price_keys%5Bother%5D=1&price_keys%5Bwriting%5D=1&keyword_only=1&start=0&limit=20&query=";
		String result = WebUtil.sendGET(URL_RESARCH_WBY + "ssddrenren");
		System.out.println(result);
	}

}
