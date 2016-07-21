package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class BaseSqlDB {
	
	public static final String KEY_TIME = "create_time";
	public static final String KEY_MID = "mid";
	public static final String KEY_UID = "uid";
	public static final String KEY_PRICE = "media_price";
	public static final String KEY_CREATE_UID = "create_uid";	
	
	public static final String TYPE_DB_NAME = "com.mysql.jdbc.Driver";
	private String name;
	private String password;
	private String ip;
	private String dbname;
	public static Connection conn = null;
	
	public BaseSqlDB(String ip, String dbname,String name, String password) {
		
		this.ip = ip;
		this.dbname = dbname;
		this.name = name;
		this.password = password;
		
		try {
			Class.forName(TYPE_DB_NAME);
			if(conn != null && !conn.isClosed()) {
				
			} else {
				String uri = "jdbc:mysql://" + ip + "/" + dbname + "?characterEncoding=utf8";
				conn = DriverManager.getConnection(uri, name, password);
			}
		} catch (Exception e) {
			System.out.println("connect db failed");
		}
	}
	
	public void connectDB() {
		try {
			Class.forName(TYPE_DB_NAME);
			if(conn != null && !conn.isClosed()) {
				
			} else {
				String uri = "jdbc:mysql://" + ip + "/" + dbname + "?characterEncoding=utf8";
				conn = DriverManager.getConnection(uri, name, password);
			}
		} catch (Exception e) {
			System.out.println("链接数据库报错了");
		}
	}
	
	/**
	 * ȡ��SQL��������
	 * @param sql Ҫִ�е�SQL���
	 * @return SQL��������
	 * @throws SQLException ִ��SQL����쳣
	 */
	public PreparedStatement getPrepared(String sql) throws SQLException {
		connectDB();
		if(conn != null) {
			return conn.prepareStatement(sql);
		}
		return null;
	}
	
	/**
	 * �ر���ݿ�����
	 */
	public void close() {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("close error");
		}
	}
	
	public static String createInsertSql(String table, ArrayList<DbParams> list) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ").append(table).append(" (");
		for(int i = 0; i < list.size(); i++) {
			sql.append(list.get(i).getKey());
			if(i != list.size()- 1) {
				sql.append(",");
			}
		}
		sql.append(") VALUES (");
		for(int i = 0; i < list.size(); i++) {
			sql.append("?");
			if(i != list.size() - 1) {
				sql.append(",");
			}
		}
		sql.append(")");
		return sql.toString();
	}
	
	public static void initPst(PreparedStatement pst, ArrayList<DbParams> list) throws SQLException {
		DbParams param;
		for(int i = 0; i < list.size(); i++) {
			param = list.get(i);
			if(param.getValue() instanceof Float) {
				pst.setFloat(i + 1, (Float)param.getValue());
			} else if(param.getValue() instanceof Integer) {
				pst.setInt(i + 1, (Integer) param.getValue());
			} else {
				pst.setString(i + 1, (String) param.getValue());
			}
		}
	}
	
	public DbParams getTime() {
		
		DbParams p = new DbParams(KEY_TIME, System.currentTimeMillis() / 1000 + "");
//		DbParams p = new DbParams(KEY_TIME, getTime2());
		return p;
	}
	
	public String getTime2() {
		return "1460304000";
	}
	
	/**
	 * 根据价格规则得到调整后的价格值
	 * @param price 原始价格
	 * @return 最终存入数据库的价格
	 */
	public String getRealPrice(String price) {
		try{
			float real_price = Float.valueOf(price);
			if(real_price <= 300) {
				real_price = (float) (real_price * 1.5);
			} else {
				real_price = (float) (real_price * 1.35);
			}
			return real_price + "";
		} catch(Exception e) {
			return "0";
		}
	}
}
