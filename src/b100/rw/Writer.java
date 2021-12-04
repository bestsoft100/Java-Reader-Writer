package b100.rw;

import java.io.File;

import b100.utils.StringUtils;

public class Writer {
	
	public static String tabs = "\t";
	
	private String content;
	private int column;
	private Character last;
	
	public Writer() {
		content = "";
		column = 0;
	}
	
	public void write(String string) {
		char[] ca = string.toCharArray();
		
		for(char c : ca) {
			if(last != null && last == '\n') {
				for(int i=0; i < column; i++) {
					content += tabs;
				}
			}
			
			content += c;
			last = c;
		}
	}
	
	public void writeln(String string) {
		write(string + "\n");
	}
	
	public void writeln() {
		writeln("");
	}
	
	public void add() {
		column++;
	}
	
	public void dec() {
		column--;
		if(column < 0) {
			throw new RuntimeException();
		}
	}
	
	public String toString() {
		return content;
	}
	
	public void saveToFile(File file) {
		StringUtils.saveToFile(file, content);
	}
	
	public void saveToFile(String path) {
		saveToFile(new File(path));
	}
	
}
