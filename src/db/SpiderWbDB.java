package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import data.Constant;
import data.CwqDBObj;
import entitys.CwqWBEntity;

public class SpiderWbDB extends BaseSqlDB {
	
	private final static String ip = "192.168.0.196";
	private final static String dbname = "media";
	private final static String name = "media";
	private final static String password = "mediamedia";
	private final static String TABLE_NAME = "wlf_weibo_media";
	
	private final String KEY_NAME = "media_text";
	private final String KEY_AVATAR = "avatar";
	private final String KEY_CITY = "city";
	private final String KEY_FANS = "fans";
	private final String KEY_PROVINCE = "province";
	private final String KEY_STRAIGHT = "straight_price";
	private final String KEY_TURN = "turn_price";
	private final String KEY_R_STRAIGHT = "straight_real_price";
	private final String KEY_R_TURN = "turn_real_price";
	private final String KEY_URL = "url";
	private final String KEY_AUTH = "authentication_text";
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
		list.add(new DbParams(KEY_CITY, entity.getPg_area_name()));
		list.add(new DbParams(KEY_FANS,entity.getSy_fans_num()));
		list.add(new DbParams(KEY_STRAIGHT, getRealPrice(entity.getBs_rg_zhifa())));
		list.add(new DbParams(KEY_TURN, getRealPrice(entity.getBs_rg_zhuanfa())));
		list.add(new DbParams(KEY_R_STRAIGHT, getRealPrice(entity.getBs_rg_zhifa())));
		list.add(new DbParams(KEY_R_TURN, getRealPrice(entity.getBs_rg_zhuanfa())));
		list.add(new DbParams(KEY_URL, entity.getBs_weibo_url()));
		list.add(new DbParams(KEY_AUTH, entity.getVerified_explain()));
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
}
