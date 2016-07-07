package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.BasicHttpContext;

public class WebUtil {
	
	public static SSLClient httpClient;
	public static String cookie = "";
	public static List<String> LIST_SUID = new ArrayList<>();
	public static List<String> LIST_SUIR = new ArrayList<>();
	
	public static String sendGET(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        BasicHttpContext context = new BasicHttpContext();
        httpGet.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        CloseableHttpResponse httpResponse = null;
		httpResponse = getClient().execute(httpGet, context);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent(), "utf-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();
        return response.toString();
    }
 
    public static synchronized String sendPOST(String url, List<NameValuePair> param) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        if(param != null) {
        	HttpEntity postParams = new UrlEncodedFormEntity(param);
            httpPost.setEntity(postParams);
        }
        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        CloseableHttpResponse httpResponse = getClient().execute(httpPost);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent(), "utf-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();
        return response.toString();
    }
    
    public static void downImage(String url, String filename,
			String savePath) throws Exception {
    	HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        CloseableHttpResponse httpResponse = getClient().execute(httpGet);
 		InputStream is = httpResponse.getEntity().getContent();
 		byte[] bs = new byte[1024];
 		int len;
 		File sf = new File(savePath);
 		if (!sf.exists()) {
 			sf.mkdirs();
 		}
 		OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
 		while ((len = is.read(bs)) != -1) {
 			os.write(bs, 0, len);
 		}
 		os.close();
 		is.close();
    }
    
    protected static SSLClient getClient() {
    	if(httpClient == null) {
    		try {
				httpClient = new SSLClient();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	return httpClient;
    }
    
}
