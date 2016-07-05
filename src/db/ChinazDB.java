package db;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import entitys.ChinazEntity;

public class ChinazDB extends BaseMonGoDB {
	
	/**
	 * collection的名字
	 */
	private static final String CL_NAME = "wlf_website_info";
	private static final String CL_CHINAZ = "wlf_chinaz_info";
	private static ChinazDB mDB;
	
	private ChinazDB() {
		super();
	}
	
	public static ChinazDB getInstance() {
		if(mDB == null) {
			mDB = new ChinazDB();
		}
		return mDB;
	}
	
	public void addInfo(ChinazEntity entity) {
		addInfo(entity, CL_NAME);
	}
	
	public void addRankInfo(ChinazEntity entity) {
		addInfo(entity, CL_CHINAZ);
	}
	
	private void addInfo(ChinazEntity entity, String key) {
		deleteAccount(entity);
		Document document = new Document();
        document.putAll(BasicDBObject.parse(entity.toString()));  
        document.put("from", "chinaz");
		getDataBase().getCollection(key).insertOne(document);
	}
	
	public void deleteAccount(ChinazEntity entity) {
		deleteByHref(entity.getHref());
	}
	
	public void deleteByHref(String href) {
		BasicDBObject data = new BasicDBObject();  
        data.put("href", href);  
        getDataBase().getCollection(CL_NAME).deleteMany(data);
	}
	
	
	public List<String> getAllInfo() {
		List<String> list = new ArrayList<String>();
		BasicDBObject fifter = new BasicDBObject();
		MongoCursor<Document> cursor = getDataBase().getCollection(CL_NAME).find(fifter).iterator();
		while(cursor.hasNext()) {
			list.add(cursor.next().toJson());
		}
		return list;
	}
}
