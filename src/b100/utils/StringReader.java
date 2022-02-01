package b100.utils;

public class StringReader {
	
	private String string;
	private int i;
	
	public StringReader(String string) {
		this.string = string;
	}
	
	public void skipWhitespace() {
		while(i < string.length() && isWhitespace(get())) i++;
	}
	
	public char get() {
		return string.charAt(i);
	}
	
	public char getAndSkip() {
		char c = get();
		next();
		return c;
	}
	
	public void expectAndSkip(char c) {
		expect(c);
		next();
	}
	
	public void expect(char c) {
		expectOne("" + c);
	}
	
	public void expectOne(String chars) {
		for(int i=0; i < chars.length(); i++) if(chars.charAt(i) == get()) return;
		throw new InvalidCharacterException(this);
	}
	
	public boolean isWhitespace(char c) {
		return c == ' ' || c == '\t' || c == '\n';
	}

	public void next() {
		i++;
	}
	
	public int position() {
		return i;
	}
	
	public String string() {
		return string;
	}
	
	public boolean isNext(String string) {
		return this.string.substring(i, i + string.length()).equals(string);
	}
	
	public void skip(int i) {
		this.i += i;
	}

	public void expectAndSkip(String string) {
		if(!isNext(string)) throw new InvalidCharacterException(this);
		skip(string.length());
	}
	
	public int remainingCharacters() {
		return string.length() - i;
	}
	
	public String readUntilCharacter(char endChar) {
		StringBuilder builder = new StringBuilder();
		while(i < string.length()) {
			char c = get();
			if(c == endChar) {
				break;
			}else {
				builder.append(c);
				next();
			}
		}
		return builder.toString();
	}
	
}
