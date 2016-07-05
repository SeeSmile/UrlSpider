package spider;

public class BaseSpider {
	
	public static enum Type{
		CHINAZ, CWQ, WBY
	}
	
	private Type mType;
	
	public BaseSpider(Type type) {
		mType = type;
	}
	
	protected Type getType() {
		return mType;
	}
}
