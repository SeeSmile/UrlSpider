package db;

public class DbParams {
	private String key;
	
	private Object value;
	
	public DbParams(String key, Object value) {
		this.key = key;
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(String values) {
		this.value = values;
	}
	
	@Override
	public String toString() {
		return "key:" + key + ", value:" + value;
	}
	
}
