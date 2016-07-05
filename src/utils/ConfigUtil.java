package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigUtil {
	
	/**
	 * 设置token
	 * @param token
	 * @throws IOException 
	 */
	public static void setToken(String token) throws IOException {
		String path = SFileUtil.getDataFile("config");
		File config_file = new File(path);
		FileWriter writer = new FileWriter(config_file, false);
		writer.write(token);
		writer.close();
	}
	
	/**
	 * 设置登录时间
	 * @param time
	 */
	public static void setTokenTime(long time) {
		
	}
}
