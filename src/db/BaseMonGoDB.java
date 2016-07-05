package db;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

class BaseMonGoDB {
	
	private static String DB_ADDRESS = "203.195.238.137";
	private static int DB_PORT = 27017;
//	private static String DB_NAME = "360netnews_test";
	private static String DB_NAME = "360netnews";
	
	private MongoClient mClient;
	private MongoDatabase mDatabase;
	
	public BaseMonGoDB() {
		connectDB();
	}
	
	final protected MongoDatabase getDataBase() {
		connectDB();
		return mDatabase;
	}
	
	final public void close() {
		if(mClient != null) {
			mClient.close();
			mClient = null;
			mDatabase = null;
		}
	}

	final private void connectDB() {
		initClient();
		connectDB(DB_NAME);
	}
	
	final private void initClient() {
		if(mClient == null) {
			mClient = new MongoClient(DB_ADDRESS, DB_PORT);
		}
	}
	
	final private void connectDB(String name) {
		if(mDatabase == null) {
			mDatabase = mClient.getDatabase(name);
		} else {
			if(!mDatabase.getName().equals(name)) {
				mDatabase = mClient.getDatabase(name);
			}
		}
	}
	
}
