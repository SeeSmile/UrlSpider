package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.Constant;
import data.CwqDBObj;
import entitys.CwqWXEntity;
import entitys.WbyWXEntity;
import entitys.WbyWXEntity2;

public class SpiderWxDB extends BaseSqlDB {
	
	private final static String ip = "192.168.0.196";
	private final static String dbname = "media";
	private final static String name = "media";
	private final static String password = "mediamedia";
	
	public SpiderWxDB() {
		super(ip, dbname, name, password);
	}
	
	/**
	 * 添加城外圈微信信息到数据库
	 * @param entity 数据
	 * @param type_push 推广类型
	 * @param type_area 地区编号
	 * @param type_type 分类
	 * @throws SQLException Sql 执行错误
	 */
	public void insertInfo(CwqWXEntity entity, String type_push, String type_area, String type_type) throws SQLException {
		String id = getAccountId(entity.getBs_weixinhao());
		if(id == null) {
			insertBaseInfo(entity, type_push, type_area, type_type);
		} else {
			System.out.println("存在账号:" + id);
		}
	}
	
	public void insertInfo(WbyWXEntity entity, String type_type) {
		ArrayList<DbParams> list = new ArrayList<>();
		list.add(new DbParams(CwqDBObj.KEY_ACCOUNT, entity.getWeibo_id()));
		list.add(new DbParams(CwqDBObj.KEY_NAME, entity.getWeibo_name()));
		list.add(new DbParams(CwqDBObj.KEY_UID, Constant.UID_WBY));
		list.add(new DbParams(CwqDBObj.KEY_FANS, formatFans(entity.getFollowers_count())));
		list.add(new DbParams(CwqDBObj.KEY_AVATAR, entity.getFace_url()));
		list.add(new DbParams(CwqDBObj.KEY_PROVINCE, formatArea(entity.getArea_id())));
		//判断存入数据的类型
		list.add(new DbParams(CwqDBObj.KEY_CATEGORY, type_type));
		list.add(new DbParams(CwqDBObj.KEY_TIME, System.currentTimeMillis() / 1000 + ""));
		list.add(new DbParams(CwqDBObj.KEY_QR_CODE, entity.getScreen_shot_qr_code()));
		
		if(entity.getReference_price().getSingle() != null) {
			list.add(new DbParams(CwqDBObj.KEY_TOP, entity.getReference_price().getMulti_top().getQuote()));
			list.add(new DbParams(CwqDBObj.KEY_R_TOP, getRealPrice(entity.getReference_price().getSingle().getQuote() + "")));
		}
		if(entity.getReference_price().getMulti_top() != null) {
			list.add(new DbParams(CwqDBObj.KEY_ONE, entity.getReference_price().getMulti_top().getQuote()));
			
			list.add(new DbParams(CwqDBObj.KEY_R_ONE, getRealPrice(entity.getReference_price().getMulti_top().getQuote() + "")));
		}
		if(entity.getReference_price().getMulti_second() != null) {
			list.add(new DbParams(CwqDBObj.KEY_TWO, entity.getReference_price().getMulti_other().getQuote()));
			list.add(new DbParams(CwqDBObj.KEY_R_TWO, getRealPrice(entity.getReference_price().getMulti_second().getQuote() + "")));
			
		}
		if(entity.getReference_price().getMulti_other() != null) {
			list.add(new DbParams(CwqDBObj.KEY_THREE, entity.getReference_price().getMulti_other().getQuote()));
			list.add(new DbParams(CwqDBObj.KEY_R_THREE, getRealPrice(entity.getReference_price().getMulti_other().getQuote() + "")));
		}
		String sql = createInsertSql(CwqDBObj.TABLE_WEIXIN, list);
		PreparedStatement pst;
		try {
			pst = getPrepared(sql);
			initPst(pst, list);
			pst.execute();
		} catch (SQLException e) {
			System.out.println("1 fans: " + entity.getFollowers_count());
		}
		
	}
	
	public void insertInfo(WbyWXEntity2 entity, String type_type) {
		ArrayList<DbParams> list = new ArrayList<>();
		list.add(new DbParams(CwqDBObj.KEY_ACCOUNT, entity.getWeibo_id()));
		list.add(new DbParams(CwqDBObj.KEY_NAME, entity.getWeibo_name()));
		list.add(new DbParams(CwqDBObj.KEY_UID, Constant.UID_WBY));
		list.add(new DbParams(CwqDBObj.KEY_FANS, formatFans(entity.getFollowers_count())));
		list.add(new DbParams(CwqDBObj.KEY_AVATAR, entity.getFace_url()));
		list.add(new DbParams(CwqDBObj.KEY_PROVINCE, formatArea(entity.getArea_id())));
		//判断存入数据的类型
		list.add(new DbParams(CwqDBObj.KEY_CATEGORY, type_type));
		list.add(new DbParams(CwqDBObj.KEY_TIME, System.currentTimeMillis() / 1000 + ""));
		list.add(new DbParams(CwqDBObj.KEY_QR_CODE, entity.getScreen_shot_qr_code()));
		
		if(entity.getGross_deal_price().getSingle_graphic_price() != null) {
			list.add(new DbParams(CwqDBObj.KEY_TOP, entity.getGross_deal_price().getSingle_graphic_price()));
			list.add(new DbParams(CwqDBObj.KEY_R_TOP, getRealPrice(entity.getGross_deal_price().getSingle_graphic_price())));
		}
		if(entity.getGross_deal_price().getMulti_graphic_second_price() != null) {
			list.add(new DbParams(CwqDBObj.KEY_ONE, entity.getGross_deal_price().getMulti_graphic_second_price()));
			
			list.add(new DbParams(CwqDBObj.KEY_R_ONE, getRealPrice(entity.getGross_deal_price().getMulti_graphic_second_price())));
		}
		if(entity.getGross_deal_price().getMulti_graphic_second_price() != null) {
			list.add(new DbParams(CwqDBObj.KEY_TWO, entity.getGross_deal_price().getMulti_graphic_second_price()));
			list.add(new DbParams(CwqDBObj.KEY_R_TWO, getRealPrice(entity.getGross_deal_price().getMulti_graphic_second_price())));
			
		}
		
		String sql = createInsertSql(CwqDBObj.TABLE_WEIXIN, list);
		PreparedStatement pst;
		try {
			pst = getPrepared(sql);
			initPst(pst, list);
			pst.execute();
		} catch (SQLException e) {
			System.out.println("2 fans:" + entity.getFollowers_count());
		}
	
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

	private void insertBaseInfo(CwqWXEntity entity, String type_push, String type_area, String type_type) throws SQLException {
		ArrayList<DbParams> list = new ArrayList<>();
		list.add(new DbParams(CwqDBObj.KEY_ACCOUNT, entity.getBs_weixinhao()));
		list.add(new DbParams(CwqDBObj.KEY_NAME, entity.getBs_account_name()));
		list.add(new DbParams(CwqDBObj.KEY_UID, Constant.UID_CWQ));
		list.add(new DbParams(CwqDBObj.KEY_FANS, entity.getBs_fans_num()));
		list.add(new DbParams(CwqDBObj.KEY_AVATAR, entity.getBs_head_img()));
		if(type_area.equals("3412")) {
			list.add(new DbParams(CwqDBObj.KEY_PROVINCE, "1"));
		} else {
			list.add(new DbParams(CwqDBObj.KEY_PROVINCE, type_area));
		}
		//判断存入数据的类型
		list.add(new DbParams(CwqDBObj.KEY_CATEGORY, type_type));
		list.add(new DbParams(CwqDBObj.KEY_TIME, System.currentTimeMillis() / 1000 + ""));
		list.add(new DbParams(CwqDBObj.KEY_WEEK_READ, entity.getBs_weekly_read_avg()));
		list.add(new DbParams(CwqDBObj.KEY_QR_CODE, entity.getBs_qr_code()));
		
		if(type_push.equals("1")) {
			list.add(new DbParams(CwqDBObj.KEY_D_TOP, entity.getDtwdyt()));
			list.add(new DbParams(CwqDBObj.KEY_D_ONE, entity.getDtwdet()));
			list.add(new DbParams(CwqDBObj.KEY_D_TWO, entity.getDtwqtwz()));
			list.add(new DbParams(CwqDBObj.KEY_D_THREE, entity.getDtwqtwz()));
			
			list.add(new DbParams(CwqDBObj.KEY_DR_TOP, getRealPrice(entity.getDtwdyt())));
			list.add(new DbParams(CwqDBObj.KEY_DR_ONE, getRealPrice(entity.getDtwdet())));
			list.add(new DbParams(CwqDBObj.KEY_DR_TWO, getRealPrice(entity.getDtwqtwz())));
			list.add(new DbParams(CwqDBObj.KEY_DR_THREE, getRealPrice(entity.getDtwqtwz())));
		} else {
			list.add(new DbParams(CwqDBObj.KEY_TOP, getRealPrice(entity.getDtwdyt())));
			list.add(new DbParams(CwqDBObj.KEY_ONE, getRealPrice(entity.getDtwdet())));
			list.add(new DbParams(CwqDBObj.KEY_TWO, getRealPrice(entity.getDtwqtwz())));
			list.add(new DbParams(CwqDBObj.KEY_THREE, getRealPrice(entity.getDtwqtwz())));
			
//			list.add(new DbParams(CwqDBObj.KEY_TOP, entity.getDtwdyt()));
//			list.add(new DbParams(CwqDBObj.KEY_ONE, entity.getDtwdet()));
//			list.add(new DbParams(CwqDBObj.KEY_TWO, entity.getDtwqtwz()));
//			list.add(new DbParams(CwqDBObj.KEY_THREE, entity.getDtwqtwz()));
			
			list.add(new DbParams(CwqDBObj.KEY_R_TOP, getRealPrice(entity.getDtwdyt())));
			list.add(new DbParams(CwqDBObj.KEY_R_ONE, getRealPrice(entity.getDtwdet())));
			list.add(new DbParams(CwqDBObj.KEY_R_TWO, getRealPrice(entity.getDtwqtwz())));
			list.add(new DbParams(CwqDBObj.KEY_R_THREE, getRealPrice(entity.getDtwqtwz())));
		}
		String sql = createInsertSql(CwqDBObj.TABLE_WEIXIN, list);
		PreparedStatement pst = getPrepared(sql);
		initPst(pst, list);
		pst.execute();
		close();
	}
	
	/**
	 * 根据价格规则得到调整后的价格值
	 * @param price 原始价格
	 * @return 最终存入数据库的价格
	 */
	private String getRealPrice(String price) {
		float real_price = Float.valueOf(price);
		if(real_price <= 300) {
			real_price = (float) (real_price * 1.5);
		} else {
			real_price = (float) (real_price * 1.35);
		}
		return real_price + "";
	}
	
	/**
	 * 获取指定微信账号的id
	 * @param account 微信账号
	 * @return 账号的 id，不存在则返回 null
	 * @throws SQLException sql 执行错误
	 */
	public String getAccountId(String account) throws SQLException {
		String sql = "select id from wlf_weixin_media where weixin_account=?";
		PreparedStatement pst = getPrepared(sql);
		pst.setString(1, account);
		ResultSet result = pst.executeQuery();
		if(result.next()) {
			return result.getString("id");
		}
		return null;
	}

}
