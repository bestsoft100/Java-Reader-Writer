package b100.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import b100.json.element.JsonObject;

public abstract class Utils {
	
	public static String readFile(File file) {
		if(file == null)
			throw new NullPointerException();
		if(!file.exists())
			throw new RuntimeException(file.getAbsolutePath());
		
		try {
			return readAll(new FileInputStream(file));
		}catch (Exception e) {
			throw new RuntimeException(file.toString(), e);
		}
	}
	
	public static String readAll(URL url) {
		try {
			return readAll(url.openStream());
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String readAll(InputStream inputStream) {
		if(inputStream == null)
			throw new NullPointerException();
		
		try {
			InputStreamReader reader = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(reader);
			
			String string = "";
			String line = null;
			boolean first = true;
			
			while(true) {
				line = br.readLine();
				
				if(line == null)
					break;
				
				if(first) {
					first = false;
				}else {
					line += "\n";
				}
				
				string += line;
			}
			
			br.close();
			
			return string;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void saveFile(File file, JsonObject object) {
		saveFile(file, object.toString());
	}
	
	public static void saveFile(String path, String content) {
		saveFile(new File(path), content);
	}
	
	public static void saveFile(File file, String content) {
		if(file == null)
			throw new NullPointerException();
		if(content == null)
			throw new NullPointerException();
		
		try {
			FileWriter fw = new FileWriter(file);
			
			createFile(file);
			fw.write(content);
			
			fw.close();
		}catch (Exception e) {
			throw new RuntimeException(file.getAbsolutePath(), e);
		}
	}
	
	public static boolean createFile(File file) {
		if(file == null)
			throw new NullPointerException();
		try {
			file = file.getAbsoluteFile();
			File parent = file.getParentFile();
			parent.mkdirs();
			return file.createNewFile();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String getWebsiteContent(String url) {
		try {
			return Utils.readAll(new URL(url).openConnection().getInputStream());
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
