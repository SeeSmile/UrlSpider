package spider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.google.gson.Gson;
import data.Constant;
import db.SpiderWbDB;
import db.SpiderWxDB;
import entitys.WbyWBEntity;
import entitys.WbyWXEntity;
import entitys.WbyWXEntity2;
import utils.SFileUtil;
import utils.WebUtil;

public class WbySpider extends BaseSpider{
	
	private final static int PAGE_LIMIT = 20;
	private final static String ACCOUNT = "seesmile";
	private final static String PASSWORD = "970782573WBY";
	public static final String URL_WBY_CODE = "http://chuanbo.weiboyi.com/hwauth/index/captcha";
	
	/**
	 * 获取指定区域的城市列表
	 */
	public static final String URL_WBY_CITY = "http://chuanbo.weiboyi.com/hwreservation/account/getlowerregion";
	
	/**
	 * 获取区域信息
	 */
	public static final String URL_WBY_AREA = "http://chuanbo.weiboyi.com/hworder/sina/allcategore";
	
	/**
	 * 获取token
	 */
	private static final String URL_TOKEN = "http://chuanbo.weiboyi.com/hworder/weixin";
	private static final String URL_GET_ALL = "http://chuanbo.weiboyi.com/hworder/weixin/filterlist/source/all?";

	private static final String URL_GET_ALL_WB = "http://chuanbo.weiboyi.com/hworder/sina/filterlist/source/all?";
	
	public static String mToken;
	
	public WbySpider() {
		super(BaseSpider.Type.WBY);
	}
	
	/**
	 * 获取验证码
	 * @return 验证码文件
	 * @throws Exception 
	 */
	public static File getCode() throws Exception {
		String result = WebUtil.sendGET(URL_WBY_CODE);
//		result = StringUtil.getSimpleString(result);
		String url = "http://chuanbo.weiboyi.com"
				+ new JSONObject(result).optString("url");
		File file = SFileUtil.createCodeFile();
		WebUtil.downImage(url, file.getName(), file.getParent());
		return file;
	}
	
	/**
	 * 验证登录
	 * @param code
	 * @return 是否登录成功
	 */
	public static boolean login(String code) {
		String url = "http://chuanbo.weiboyi.com/";
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("username", ACCOUNT));
		param.add(new BasicNameValuePair("password", PASSWORD));
		param.add(new BasicNameValuePair("piccode", code));
		param.add(new BasicNameValuePair("typelogin", "1/"));
		try {
			String result = WebUtil.sendPOST(url, param);
			JSONObject json = new JSONObject(result);
			if(json.optBoolean("status")) {
				if(mToken == null) {
					String token = getToken();
					setToken(token);
					
				}
				return true;
			} else {
				System.out.println(json.optString("message"));
				return false;
			}
		} catch (Exception e) {
			System.out.println("呵呵, 登录还报错");
		}
		return false;
	}
	
	public static void setToken(String token) {
		mToken = token;
	}
	
	private static String getToken() throws IOException {
		String token = "";
		String result = WebUtil.sendGET(URL_TOKEN);
		Document doc = Jsoup.parse(result);
		Element ele = doc.getElementById("web_csrf_token");
		token = ele.attr("value");
		return token;
	}
	
	/**
	 * 开始获取微信数据
	 * @throws JSONException 
	 */
	public static void startGetWX() throws JSONException {
		SpiderWxDB db = SpiderWxDB.getInstance();
		for(int category = 3001; category <= 3023; category++) {
			System.out.println("获取第" + category + "种类数据");
			boolean isComplete = false;
			int page = 0;
			while(!isComplete) {
				StringBuffer buffer = new StringBuffer(URL_GET_ALL);
				
				buffer.append("pack_area_id=").append("1012501").append("&");
				buffer.append("category_filter=").append(category).append("&");
				buffer.append("limit=").append(PAGE_LIMIT).append("&");
				buffer.append("price_keys[other]=").append("1").append("&");
				buffer.append("price_keys[second]=").append("1").append("&");
				buffer.append("price_keys[single]=").append("1").append("&");
				buffer.append("price_keys[top]=").append("1").append("&");
				buffer.append("price_keys[writing]=").append("1").append("&");
				buffer.append("start=").append(page * PAGE_LIMIT).append("&");
				buffer.append("web_csrf_token=").append(mToken);
				try {
					System.out.println("获取第 " + (page + 1) + " 页");
					String result = WebUtil.sendGET(buffer.toString());
					JSONObject json = new JSONObject(result);
					JSONObject json_data = json.getJSONObject("data");
					int count_start = json_data.getInt("start");
					int count_all = json_data.getInt("total");
					if(count_all <= count_start + PAGE_LIMIT) {
						System.out.println("获取完毕了，哈哈哈");
						isComplete = true;
					}
					if(count_all == 0) {
						continue;
					}
					List<Object> list = getWXEntityList(json_data);
					if(list != null) {
						System.out.println("size is " + list.size());
						for(int i = 0; i < list.size(); i++) {
							if(list.get(i) instanceof WbyWXEntity) {
								WbyWXEntity entity = (WbyWXEntity) list.get(i);
								System.out.println("entity:\n" + entity.getWeibo_name());
								db.insertInfo(entity, Constant.LIST_TYPE_ID_WX_WBY[category - 3001]);
							} else {
								WbyWXEntity2 entity = (WbyWXEntity2) list.get(i);
								db.insertInfo(entity, Constant.LIST_TYPE_ID_WX_WBY[category - 3001]);
							}
						}
						db.close();
					} else {
						System.out.println("list 为空了");
					}
				} catch (IOException e) {
					System.out.println("io exception:" + e.toString());
					isComplete = true;
				} finally {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						
					}
					page++;
				}
			}
		}
	}
	
	private static List<Object> getWXEntityList(JSONObject json) {
		List<Object> list = new ArrayList<>();
		try {
			JSONArray array_rows = json.getJSONArray("rows");
			Gson gson = new Gson();
			for(int i = 0; i < array_rows.length(); i++) {
				WbyWXEntity entity = gson.fromJson(array_rows.getJSONObject(i).getJSONObject("cells").toString(), WbyWXEntity.class);
				if(entity.getReference_price() != null) {
					list.add(entity);
				} else {
					WbyWXEntity2 en = gson.fromJson(array_rows.getJSONObject(i).getJSONObject("cells").toString(), WbyWXEntity2.class);
					list.add(en);
				}
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
			return null                                                                                                                                            ;
		}
	}
	
	public static void startGetWB() throws JSONException {
		SpiderWbDB db = SpiderWbDB.getInstance();
		for(int category = 1; category <= Constant.LIST_TYPE_ID_WB_WBY.length; category++) {
			System.out.println("获取第" + category + "种类数据");
			boolean isComplete = false;
			int page = 0;
			while(!isComplete) {
				StringBuffer buffer = new StringBuffer(URL_GET_ALL_WB);
				buffer.append("category_filter=").append(category).append("&");
				buffer.append("limit=").append(PAGE_LIMIT).append("&");
				buffer.append("price_keys[reservation]=").append("1").append("&");
				buffer.append("price_keys[retweet]=").append("1").append("&");
				buffer.append("price_keys[tweet]=").append("1").append("&");
				buffer.append("start=").append(page * PAGE_LIMIT).append("&");
				buffer.append("web_csrf_token=").append(mToken);
				try {
					System.out.println("获取第 " + (page + 1) + " 页");
					System.out.println("wocao start");
					String result = WebUtil.sendGET(buffer.toString());
					JSONObject json = new JSONObject(result);
					JSONObject json_data = json.getJSONObject("data");
					int count_start = json_data.getInt("start");
					int count_all = json_data.getInt("total");
					if(count_all <= count_start + PAGE_LIMIT) {
						System.out.println("获取完毕了，哈哈哈");
						isComplete = true;
					}
					List<Object> list = getWBEntityList(json_data);
					if(list != null) {
						System.out.println("size is " + list.size());
						for(int i = 0; i < list.size(); i++) {
							if(list.get(i) instanceof WbyWBEntity) {
								WbyWBEntity entity = (WbyWBEntity) list.get(i);
								db.insertInfo(entity, Constant.LIST_TYPE_ID_WB_WBY[category - 1]);
							}
						}
						db.close();
					} else {
						System.out.println("list 为空了");
					}
				} catch (IOException e) {
					System.out.println("io exception:" + e.toString());
					isComplete = true;
				} catch(Exception e){
					e.printStackTrace();
					System.out.println("exception");
				} finally {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						
					}
					page++;
				}
			}
		}
	}

	private static List<Object> getWBEntityList(JSONObject json) {
		List<Object> list = new ArrayList<>();
		try {
			JSONArray array_rows = json.getJSONArray("rows");
			Gson gson = new Gson();
			for(int i = 0; i < array_rows.length(); i++) {
				WbyWBEntity entity = gson.fromJson(array_rows.getJSONObject(i).getJSONObject("cells").toString(), WbyWBEntity.class);
				list.add(entity);
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
			return null                                                                                                                                            ;
		}
	}
}
