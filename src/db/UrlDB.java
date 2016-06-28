package db;

import org.bson.Document;

import com.mongodb.BasicDBObject;

import entitys.ChinazEntity;

public class UrlDB extends BaseMonGoDB {
	
	/**
	 * collection的名字
	 */
	private static final String CL_NAME = "wlf_website_info";
	private static UrlDB mDB;
	
	private UrlDB() {
		super();
	}
	
	public static UrlDB getInstance() {
		if(mDB == null) {
			mDB = new UrlDB();
		}
		return mDB;
	}
	
	public void addInfo(ChinazEntity entity) {
		deleteAccount(entity);
		Document document = new Document();
        document.putAll(BasicDBObject.parse(entity.toString()));  
		getDataBase().getCollection(CL_NAME).insertOne(document);
	}
	
	private void deleteAccount(ChinazEntity entity) {
		BasicDBObject data = new BasicDBObject();  
        //删除名称为lucy的记录  
        data.put("href", entity.getHref());  
        //传入[空实例]删除所有  
        getDataBase().getCollection(CL_NAME).deleteMany(data);
	}
}
