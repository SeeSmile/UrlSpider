package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import data.Constant;
import entitys.CwqWBEntity;
import entitys.WbyWBEntity;

public class SpiderWbDB extends BaseSqlDB {
	
	private final static String ip = "192.168.0.196";
	private final static String dbname = "media";
	private final static String name = "media";
	private final static String password = "mediamedia";
	private final static String TABLE_NAME = "wlf_weibo_media";
	
	private final String KEY_NAME = "media_text";
	private final String KEY_AVATAR = "avatar";
	private final String KEY_CITY = "city";
	private final String KEY_TYPE = "media_type";
	private final String KEY_FANS = "fans";
	private final String KEY_PROVINCE = "province";
	private final String KEY_STRAIGHT = "straight_price";
	private final String KEY_TURN = "turn_price";
	private final String KEY_R_STRAIGHT = "straight_real_price";
	private final String KEY_R_TURN = "turn_real_price";
	private final String KEY_URL = "url";
	private final String KEY_AUTH = "authentication_text";
	private final String KEY_AUTH_TYPE = "authentication_type";
	private final String KEY_CATEGORY = "category";
	
	private static SpiderWbDB mDb;
	
	private SpiderWbDB() {
		super(ip, dbname, name, password);
	}
	
	public static SpiderWbDB getInstance() {
		if(mDb == null) {
			mDb = new SpiderWbDB();
		}
		return mDb;
	}
	
	public void insertInfo(CwqWBEntity entity, String area, String type) throws SQLException {
		ArrayList<DbParams> list = new ArrayList<DbParams>();
		list.add(new DbParams(KEY_NAME, entity.getBs_account_name()));
		list.add(new DbParams(KEY_AVATAR, entity.getBs_head_img()));
		list.add(new DbParams(KEY_CITY, formatAreaName(entity.getPg_area_name())));
		list.add(new DbParams(KEY_FANS,entity.getSy_fans_num()));
		list.add(new DbParams(KEY_TYPE, "2"));
		
		list.add(new DbParams(KEY_STRAIGHT, getRealPrice(entity.getBs_rg_zhifa())));
		list.add(new DbParams(KEY_TURN, getRealPrice(entity.getBs_rg_zhuanfa())));
		list.add(new DbParams(KEY_R_STRAIGHT, getRealPrice(entity.getBs_rg_zhifa())));
		list.add(new DbParams(KEY_R_TURN, getRealPrice(entity.getBs_rg_zhuanfa())));
		list.add(new DbParams(KEY_URL, entity.getBs_weibo_url()));
		list.add(new DbParams(KEY_AUTH, entity.getBs_introduction()));
		list.add(new DbParams(KEY_AUTH_TYPE, entity.getBs_verified()));
		if(area.equals("3412")) {
			list.add(new DbParams(KEY_PROVINCE, "1"));
		} else {
			list.add(new DbParams(KEY_PROVINCE, area));
		}
		list.add(new DbParams(KEY_CATEGORY, Constant.LIST_TYPE_ID_WB_CWQ[Integer.valueOf(type) - 1]));
		list.add(getTime());
		list.add(new DbParams(KEY_UID, Constant.UID_CWQ));
		String sql = createInsertSql(TABLE_NAME, list);
		PreparedStatement pst = getPrepared(sql);
		initPst(pst, list);
		pst.execute();
	}
	
	public void insertInfo2(CwqWBEntity entity, String area, String type) throws SQLException {
		ArrayList<DbParams> list = new ArrayList<DbParams>();
		list.add(new DbParams(KEY_NAME, entity.getBs_account_name()));
		list.add(new DbParams(KEY_AVATAR, entity.getBs_head_img()));
		list.add(new DbParams(KEY_CITY, formatAreaName(entity.getPg_area_name())));
		list.add(new DbParams(KEY_FANS,entity.getSy_fans_num()));
		list.add(new DbParams(KEY_TYPE, "1"));
		
		list.add(new DbParams(KEY_STRAIGHT, getRealPrice(entity.getBs_rg_zhifa())));
		list.add(new DbParams(KEY_TURN, getRealPrice(entity.getBs_rg_zhuanfa())));
		list.add(new DbParams(KEY_R_STRAIGHT, getRealPrice(entity.getBs_rg_zhifa())));
		list.add(new DbParams(KEY_R_TURN, getRealPrice(entity.getBs_rg_zhuanfa())));
		list.add(new DbParams(KEY_URL, entity.getBs_weibo_url()));
		list.add(new DbParams(KEY_AUTH, entity.getBs_introduction()));
		list.add(new DbParams(KEY_AUTH_TYPE, entity.getBs_verified()));
		if(area.equals("3412")) {
			list.add(new DbParams(KEY_PROVINCE, "1"));
		} else {
			list.add(new DbParams(KEY_PROVINCE, area));
		}
		list.add(new DbParams(KEY_CATEGORY, Constant.LIST_TYPE_ID_WB_CWQ[Integer.valueOf(type) - 1]));
		list.add(getTime());
		list.add(new DbParams(KEY_UID, Constant.UID_CWQ));
		String sql = createInsertSql(TABLE_NAME, list);
		PreparedStatement pst = getPrepared(sql);
		initPst(pst, list);
		pst.execute();
	}
	
	public void insertInfo(WbyWBEntity entity, String type) throws SQLException {
		ArrayList<DbParams> list = new ArrayList<DbParams>();
		list.add(new DbParams(KEY_NAME, entity.getWeibo_name()));
		list.add(new DbParams(KEY_AVATAR, entity.getFace_url()));
		list.add(new DbParams(KEY_AUTH_TYPE, formatType(entity)));
		list.add(new DbParams(KEY_AUTH, entity.getVerification_info()));
		list.add(new DbParams(KEY_TYPE, entity.getIs_famous()));
		list.add(new DbParams(KEY_CITY, formatAreaName(entity.getArea_name())));
		list.add(new DbParams(KEY_FANS, formatFans(entity.getFollowers_count())));
		list.add(new DbParams(KEY_STRAIGHT, getRealPrice(entity.getTweet_price())));
		list.add(new DbParams(KEY_TURN, getRealPrice(entity.getRetweet_price())));
		list.add(new DbParams(KEY_R_STRAIGHT, getRealPrice(entity.getTweet_price())));
		list.add(new DbParams(KEY_R_TURN, getRealPrice(entity.getRetweet_price())));
		list.add(new DbParams(KEY_URL, entity.getUrl()));
		list.add(new DbParams(KEY_PROVINCE, formatArea(entity.getArea_id())));
		list.add(new DbParams(KEY_CATEGORY, type));
		list.add(getTime());
		list.add(new DbParams(KEY_UID, Constant.UID_WBY));
		String sql = createInsertSql(TABLE_NAME, list);
		PreparedStatement pst = getPrepared(sql);
		initPst(pst, list);
		pst.execute();
	}
	
	private String formatType(WbyWBEntity entity) {
		if(entity.getIs_bluevip() == 1) {
			return 2 + "";
		}
		if(entity.getIs_vip() == 1) {
			return 1 + "";
		}
		if(entity.getIs_daren() == 1) {
			return 3 + "";
		}
		return "";
	}
	
	private String formatAreaName(String name) {
		if(name == null || name.trim().length() == 0) {
			return "";
		}
		String area_name = name;
		area_name = area_name.replace("中国", "");
		for(String area : Constant.LIST_AREA) {
//			if(area.equals("北京") || area.equals("重庆") || area.equals("上海") || area.equals("海南")
//					|| area.equals("上海") || area.equals("香港") || area.equals("澳门") || area.equals("台湾")) {
//				continue;
//			}
			if(area_name.contains(area)) {
				area_name = area_name.replace(area, "");
			}
		}
		return area_name;
		
	}
	
	private String formatArea(int area) {
		try{
			String str_num = (area + "").substring(3, 5);
			if(str_num.equals("99")) {
				str_num = "1";
			} else {
				str_num = Integer.valueOf(str_num) + 1 + "";
			}
			return str_num;
		} catch (Exception e) {
		}
		return "1";
		
	}
	
	private String formatFans(String fans) {
		String number = "";
		for(int i = 0; i < fans.length(); i++) {
			if(Character.isDigit(fans.charAt(i)) || '.' == fans.charAt(i)) {
				number += fans.charAt(i);
			}
		}
		float num = Float.valueOf(number);
		return num * 10000 + "";
	}
	
	public void updateType(String name) throws SQLException {
		String sql = "update " + TABLE_NAME + " set " + KEY_TYPE + "=? where media_text=?";
		PreparedStatement pst = getPrepared(sql);
		pst.setString(1, "1");
		pst.setString(2, name);
		pst.execute();
	}
	
	public boolean isExistAccount(String name) throws SQLException {
		String sql = "select * from " + TABLE_NAME + " where " + KEY_NAME + "=?";
		PreparedStatement pst = getPrepared(sql);
		pst.setString(1, name);
		ResultSet result = pst.executeQuery();
		return result.next();
	}
}
