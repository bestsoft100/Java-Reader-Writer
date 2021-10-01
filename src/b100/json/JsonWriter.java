package b100.json;

import b100.json.element.JsonObject;

public class JsonWriter {
	
	public static String tabs = "    ";
	
	private String content;
	private int column;
	private Character last;
	
	public JsonWriter() {
		content = "";
		column = 0;
	}
	
	public JsonWriter(JsonObject object) {
		this();
		object.write(this);
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
	
}
