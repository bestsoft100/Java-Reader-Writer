package b100.rw;

public class Reader {
	
	protected String[] lines;
	
	protected int line = 0;
	protected int column = 0;
	
	public Reader(String string) {
		if(string == null)
			throw new NullPointerException();
		if(string.length() == 0)
			throw new RuntimeException("Empty String");
		
		string = string.replace("\t", "    ");
		this.lines = string.split("\n");
//		for(int i=0; i < lines.length-1; i++) {
//			lines[i] = lines[i] + "\n";
//		}
	}
	
	public char get() {
		return lines[line].charAt(column);
	}
	
	public char getSkip() {
		char c = get();
		skip();
		return c;
	}
	
	public void skip() {
		column++;
		if(column >= lines[line].length()) {
			column = 0;
			line++;
		}
	}
	
	public void expectSkip(char c) {
		expect(c);
		skip();
	}
	
	public void expectSkip(String string) {
		for(int i=0; i < string.length(); i++) {
			expectSkip(string.charAt(i));
		}
	}
	
	public boolean isNext(String string) {
		String str = lines[line].substring(column);
		return str.startsWith(string);
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
	
	public void skipWhitespace() {
		while(true) {
			if(available() == 0)return;
			char c = get();
			boolean ws = c == '\n' || c == ' '; 
			if(!ws) return;
			skip();
		}
	}
	
	public void expect(char c) {
		if(get() != c) throw new UnexpectedCharacterException(this);
	}
	
	public int available() {
		int av = 0;
		for(int i=line; i < lines.length; i++) {
			int j=0;
			if(i == 0)j = column;
			
			for(;j < lines[i].length(); j++) {
				av++;
			}
		}
		return av;
	}
	
	public int availableInLine() {
		return lines[line].length() - (column+1);
	}
	
}
