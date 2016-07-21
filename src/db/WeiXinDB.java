package db;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

import data.SoGouWX;

import entitys.WeiBoEntity;

public class WeiXinDB extends BaseMonGoDB{
	
	private static final String CL_NAME = "wlf_data_wx";
	
	private static WeiXinDB mDb;
	
	private WeiXinDB() {
		
	}
	
	public static synchronized WeiXinDB getInstance() {
		if(mDb == null) {
			mDb = new WeiXinDB();
		}
		return mDb;
	}

	public List<String> getAllAccount() throws JSONException {
		BasicDBObject fifter = new BasicDBObject();
		List<String> list = new ArrayList<>();
		fifter.put("state", 1);
		MongoCursor<Document> cursor =  getDataBase().getCollection(CL_NAME).find().iterator();
		int index = 0;
		while(cursor.hasNext()) {
			System.out.println(++index);
			String text = cursor.next().toJson();
			JSONObject json = new JSONObject(text);
			list.add(json.getString("account"));
		}
		return list;
	}
	
	public void upDateArticle(String account, List<SoGouWX> list) {
		BasicDBObject fifter = new BasicDBObject();
		fifter.put("account", account);
		Document doc = getAccountInfo(account);
		if(doc.containsKey("news")) {			
			doc.remove("news");
		}
		try {
			JSONObject json = new JSONObject(doc.toJson());
			JSONArray array = new JSONArray();
			for(SoGouWX wx : list) {
				array.put(new JSONObject(wx.toString()));
			}
			json.put("news", array);
			json.put("is_new", "1");
			getDataBase().getCollection(CL_NAME).replaceOne(fifter, Document.parse(json.toString()));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public Document getAccountInfo(String account) {
		BasicDBObject fifter = new BasicDBObject();
		fifter.put("account", account);
		MongoCursor<Document> cursor =  getDataBase().getCollection(CL_NAME).find(fifter).iterator();
		if(cursor.hasNext()) {
			return cursor.next();
		} 
		return new Document();
	}
}
