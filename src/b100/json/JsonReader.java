package b100.json;

public class JsonReader{
	
	private String[] lines;
	
	private int line = 0;
	private int column = 0;
	
	public JsonReader(String string) {
		validate(string);
		
		string = string.replace("\t", "    ");
		this.lines = string.split("\n");
	}
	
	public void skip() {
		column++;
		if(column >= lines[line].length()) {
			column = 0;
			line++;
		}
	}
	
	public void skipWhitespace() {
		while(true) {
			char c = get();
			boolean ws = c == '\t' || c == '\n' || c == ' '; 
			if(!ws) return;
			skip();
		}
	}
	
	public char get() {
		return lines[line].charAt(column);
	}
	
	public void expect(char c) {
		if(get() != c) throw new JsonException(this);
	}
	
	private static void validate(String string) {
		if(string == null) {
			throw new NullPointerException();
		}
		if(string.length() == 0) {
			throw new RuntimeException("Empty String");
		}
	}
	
	public String[] getLines() {
		return lines;
	}
	
	public int getLine() {
		return line;
	}
	
	public int getColumn() {
		return column;
	}
	
}
