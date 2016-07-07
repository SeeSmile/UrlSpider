package db;

import org.bson.Document;

import entitys.WeiBoEntity;

public class WeiBoDB extends BaseMonGoDB{
	
	private static final String CL_NAME = "wlf_data_wb";
	
	private static WeiBoDB mDb;
	
	private WeiBoDB() {
		
	}
	
	public static synchronized WeiBoDB getInstance() {
		if(mDb == null) {
			mDb = new WeiBoDB();
		}
		return mDb;
	}

	public void insertInfo(WeiBoEntity entity) {
		Document document = Document.parse(entity.toString());
		getDataBase().getCollection(CL_NAME).insertOne(document);
	}
}
