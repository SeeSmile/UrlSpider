package test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.SFileUtil;
import utils.SFileUtil.ReadListener;

public class FileFifter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
//			new FileFifter().quChong("cwq.txt", "cwq3.txt");
			new FileFifter().quChong(SFileUtil.getDataFile("data.txt"), 
					SFileUtil.getDataFile("cwq2.txt"), SFileUtil.getDataFile("test234.txt"));
//			new FileFifter().formatFile("cwq.txt", "cwq2.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void quChong(String fromName, String toName) throws IOException {
		String path = SFileUtil.getDataFile(fromName);
		final File file = new File(path);
		String root = file.getParent();
		final File filedata = new File(root, toName);
		if(!filedata.exists()) {
			filedata.createNewFile();
		}
		SFileUtil.readFileLine(file, new ReadListener() {
			List<String> list = new ArrayList<>();
			int all_num = 0;
			@Override
			public void onRead(int index, String text) {
				all_num++;
				if(!list.contains(text)) {
					list.add(text);
					SFileUtil.writeText2File(filedata.getAbsolutePath(), text);
				} else {
					System.out.println(text);
				}
			}
			
			@Override
			public void onFinish() {
				System.out.println("all_num = " + all_num + ", size = " + list.size());
			
			}
			
			@Override
			public void onFail() {
				
			}
		});
	}
	
	public void quChong(String path, String path2, String toPath) throws IOException {
		List<String> list_data = SFileUtil.readFileToList(new File(path));
		List<String> list_data2 = SFileUtil.readFileToList(new File(path2));
		System.out.println(list_data.size() + ", " + list_data2.size());
		for(String str : list_data) {
			boolean isCon = false;
			for(String str2 : list_data2) {
				if(getAccount(str2).equals(str)) {
					isCon = true;
				}
			}
			System.out.println("isCon:" + isCon);
			if(!isCon) {
				SFileUtil.writeText2File(toPath, str);
			}
		}
	}
	
	public void formatFile(String fromName, String toName) throws IOException {
		String path = SFileUtil.getDataFile(fromName);
		final File file = new File(path);
		String root = file.getParent();
		final File filedata = new File(root, toName);
		if(!filedata.exists()) {
			filedata.createNewFile();
		}
		SFileUtil.readFileLine(file, new ReadListener() {
			List<String> list = new ArrayList<>();
			int all_num = 0;
			@Override
			public void onRead(int index, String text) {
				all_num++;
				if(!list.contains(text)) {
					list.add(text);
					String str = "";
					try {
//						int p = text.indexOf("||");
						int p_end = text.indexOf("==");
						str = text.substring(0, p_end + 2);
						SFileUtil.writeText2File(filedata.getAbsolutePath(), str);
					} catch (StringIndexOutOfBoundsException e) {
						System.out.println("str:" + str);
						e.printStackTrace();
					}
				} else {
					System.out.println(text);
				}
			}
			
			@Override
			public void onFinish() {
				System.out.println("all_num = " + all_num + ", size = " + list.size());
			}
			
			@Override
			public void onFail() {
				
			}
		});
	}
	
	private String getAccount(String text) {
		try {
			int p = text.indexOf("||");
			return text.substring(0, p);
		} catch (StringIndexOutOfBoundsException e) {
			return "";
		}
	}
}
