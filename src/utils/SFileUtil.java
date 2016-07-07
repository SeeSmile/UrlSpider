package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

public class SFileUtil {
	
	
	public static final String PATH_CODE = "c:\\images\\";

	/**
	 * 一行一行的读取文本文件
	 * @param file 文件
	 * @param listener 读取进度监听
	 */
	public static void readFileLine(File file, final ReadListener listener){
		FileInputStream fis = null;
		InputStreamReader isr = null;
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			listener.onFail();
		} catch (FileNotFoundException e) {
			listener.onFail();
		}   
		final BufferedReader br = new BufferedReader(isr);   
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
	}
	public static String readFile(File file) throws IOException {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		fis = new FileInputStream(file);
		isr = new InputStreamReader(fis, "UTF-8");  
		BufferedReader br = new BufferedReader(isr);   
		StringBuffer buffer = new StringBuffer();
		String line;
		while((line = br.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}
	
	public interface ReadListener {
		public void onRead(int index, String text);
		public void onFinish();
		public void onFail();
	}
	
	private static String getRootFile() {
		return new File("").getAbsolutePath();
	}
	
	
	private static String getProFile(String name) {
		return new File(getRootFile(), name).getAbsolutePath();
	}
	
	/**
	 * 获取data目录的绝对路径
	 * @param name
	 * @return
	 */
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

	
	/**
	 * 下載指定url的文件
	 * @param urlString url
	 * @param filename 文件的名字
	 * @param savePath 保存的path
	 * @throws Exception 
	 */
	public static void downloadFile(String urlString, String filename,
			String savePath) throws IOException {
		// 构造URL  
		URL url = new URL(urlString);
		// 打开连接  
		URLConnection con = url.openConnection();
		//设置请求超时为5s  
		con.setConnectTimeout(5 * 1000);
		// 输入流  
		InputStream is = con.getInputStream();
		// 1K的数据缓冲  
		byte[] bs = new byte[1024];
		// 读取到的数据长度  
		int len;
		// 输出的文件流  
		File sf = new File(savePath);
		if (!sf.exists()) {
			sf.mkdirs();
		}
		OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
		// 开始读取  
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// 完毕，关闭所有链接  
		os.close();
		is.close();
	}
	
	/**
	 * 创建当前要保存的二维码图片的文件
	 * @return 二维码图片文件
	 */
	public static File createCodeFile() {
		return new File(PATH_CODE + System.currentTimeMillis() + ".jpg");
	}

}
