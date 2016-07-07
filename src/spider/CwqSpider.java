package spider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import sun.security.jca.GetInstance;
import utils.WebUtil;
import com.google.gson.Gson;

import db.SpiderWbDB;
import db.SpiderWxDB;
import entitys.CwqWBEntity;
import entitys.CwqWXEntity;

public class CwqSpider extends BaseSpider {
	
	/**
	 * 登录
	 */
	private final String URL_LOGIN = "http://www.cwq.com/Owner/Account/check_login/";
	
	/**
	 * 获取微信列表
	 */
	private final String URL_WEIXIN = "http://www.cwq.com/Owner/Weixin/get_weixin_list/";
	
	/**
	 * 获取微播列表
	 */
	private final String URL_WEIBO = "http://www.cwq.com/Owner/Weibo/get_grassroots_list/";
	
	/**
	 * 获取详细信息
	 */
	private final String URL_WEIBO_DETAIL = "http://www.cwq.com/Owner/Weibo/getAccountInfo/";
	
	/**
	 * 获取区域
	 */
	private final String URL_AREA = "http://www.cwq.com/Owner/Tool/get_Region_Data";
	private final String USER_NAME = "lengzhifu";
	private final String USER_PWD = "wlf2016";
	
	private int startpage;
	private boolean isRun = false;
	private String type_id;
	private String area;
	private String type_push;
	
	public CwqSpider() {
		super(BaseSpider.Type.CWQ);
	}
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPusthType() {
		return type_push;
	}

	public void setType(String type_push) {
		this.type_push = type_push;
	}

	public void setTypeId(String id) {
		this.type_id = id;
	}
	
	
	public void setPage(int page) {
		this.startpage = page;
	}
	
	public boolean login() {
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("account", USER_NAME));
		urlParameters.add(new BasicNameValuePair("password", USER_PWD));
		urlParameters.add(new BasicNameValuePair("verify", ""));
		urlParameters.add(new BasicNameValuePair("inajax", "1"));
		try {
			String result = WebUtil.sendPOST(URL_LOGIN, urlParameters);
			JSONObject json = new JSONObject(result);
			String status = json.optString("status");
			if("1".equals(status)) {
				System.out.println("login ok");
				return true;
			} else {
				System.out.println("fail to login:" + json.optString("info"));
			}
			
		} catch (Exception e) {
			System.out.println("login exception");
			e.printStackTrace();
		}
		return false;
	}
	
	public void getWxData() {
		String type_area = getAreaByUrl();
		startGetList(getWXParam(type_id, type_area, type_push), URL_WEIXIN, new GetListener() {
			
			@Override
			public void OnFinish(JSONObject json) {
				CwqWXEntity wx = new Gson().fromJson(json.toString(), CwqWXEntity.class);
				try {
					SpiderWxDB.getInstance().insertInfo(wx, type_push, area, type_id);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void getWbData() {
		String type_area = getAreaByUrl();
		startGetList(getWBParam(type_id, type_area), URL_WEIBO, new GetListener() {
		
		@Override
		public void OnFinish(JSONObject json) {
			String id = json.optString("bs_id");
			CwqWBEntity entity = getDetailWB(id);
			System.out.println(entity.toString());
			try {
				SpiderWbDB.getInstance().insertInfo(entity, area, type_id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		});
	}
	
	public CwqWBEntity getDetailWB(String id) {
		List<NameValuePair> param = new ArrayList<>();
		param.add(new BasicNameValuePair("account_id", id));
		param.add(new BasicNameValuePair("is_type", "0"));
		param.add(new BasicNameValuePair("pt_type", "1"));
		CwqWBEntity entity = new CwqWBEntity();
		try {
			String result = WebUtil.sendPOST(URL_WEIBO_DETAIL, param);
			JSONObject json = new JSONObject(result);
			if(json.getInt("status") == 0) {
				json = json.getJSONObject("data");
				entity = new Gson().fromJson(json.toString(), CwqWBEntity.class);
			}
			return entity;
		} catch (Exception e) {
			
		}
		return null;
	}
	
	/**
	 * 获取需要的请求参数
	 * @param id 分类的编号
	 * @param area 地区编号，格式：324,532,6435,777,55
	 * @return 初始化的参数
	 */
	private List<NameValuePair> getWXParam(String type_id, String type_area, String type_push) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		//硬广1，软广2
		params.add(new BasicNameValuePair("flex", type_push));
		//地区id
		params.add(new BasicNameValuePair("dfmr_mt", type_area));
		//类型过滤
		params.add(new BasicNameValuePair("cjfl", type_id));
		params.add(new BasicNameValuePair("is_celebrity", "0"));
		params.add(new BasicNameValuePair("zfjg_type", "2"));
		System.out.println("params:\n" + params.toString());
		return params;
	}
	
	private List<NameValuePair> getWBParam(String type_id, String type_area) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		//地区id
		params.add(new BasicNameValuePair("dfmr_mt", type_area));
		//类型过滤
		params.add(new BasicNameValuePair("cjfl", type_id));
		params.add(new BasicNameValuePair("py_type", "1"));
		params.add(new BasicNameValuePair("is_celebrity", "0"));
		params.add(new BasicNameValuePair("zfjg_type", "1"));
		System.out.println("params:\n" + params.toString());
		return params;
	}
	
	private void startGetList(List<NameValuePair> param, String url, GetListener listener) {
		int page = startpage;
		isRun = true;
		while(isRun) {
			try {
				System.out.println("current page of " + page);
				param.add(new BasicNameValuePair("p", page + ""));
				String result = WebUtil.sendPOST(url, param);
				JSONObject json = new JSONObject(result);
				json = json.optJSONObject("data");
				JSONArray array = json.optJSONArray("list");
				for(int i = 0; i < array.length(); i++) {
					json = array.getJSONObject(i);
					listener.OnFinish(json);
				}
				if(array.length() == 0) {
					isRun = false;
					System.out.println("stoped");
				}
				
			} catch (Exception e) {
				System.out.println("run exception");
				e.printStackTrace();
			}
			page++;
		}
	}
	
	private String getAreaByUrl() {
		List<NameValuePair> param = new ArrayList<>();
		param.add(new BasicNameValuePair("parent_id", area));
		try {
			String result = WebUtil.sendPOST(URL_AREA, param);
			JSONObject json = new JSONObject(result);
			JSONArray array = json.getJSONArray("data");
			String str_area = "";
			for(int i = 0; i < array.length(); i++) {
				str_area += array.getJSONObject(i).getString("region_id") + ",";
			}
			return str_area.substring(0, str_area.length() - 1);
		} catch (Exception e) {
			
		}
		return null;
	}
	
	interface GetListener{
		public void OnFinish(JSONObject json);
	}
}
