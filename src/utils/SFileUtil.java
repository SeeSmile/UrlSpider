package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class SFileUtil {
	public static void readFileLine(File file, final ReadListener listener){
		FileInputStream fis = null;
		InputStreamReader isr = null;
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			listener.onFail();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			listener.onFail();
		}   
		final BufferedReader br = new BufferedReader(isr);   
//			  new Thread(new Runnable() {
				
//				@Override
//				public void run() {
					String line;
					int index = 0;
					boolean isRun = true;
					while(isRun) {
						index++;
						try {
							line = br.readLine();
							if(line != null) {
								listener.onRead(index, line);
							} else {
								isRun = false;
							}
						} catch (IOException e) {
							listener.onFail();
						}
						
					}
					listener.onFinish();
//				}
//			}).start();
	}
	
	public interface ReadListener {
		public void onRead(int index, String text);
		public void onFinish();
		public void onFail();
	}
	
	private static String getRootFile() {
		return new File("").getAbsolutePath();
	}
	
	public static String getProFile(String name) {
		return new File(getRootFile(), name).getAbsolutePath();
	}
	
	public static String getDataFile(String name) {
		File file = new File(getProFile("data"), name);
		if(!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file.getAbsolutePath();
	}
}
