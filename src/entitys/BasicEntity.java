package entitys;

import com.google.gson.Gson;

public class BasicEntity {
	
	public String toJson() {
		return new Gson().toJson(this);
	}
	
}
