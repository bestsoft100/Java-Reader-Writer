package b100.utils;

public class InvalidCharacterException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	private StringReader reader;
	
	public InvalidCharacterException(StringReader stringReader) {
		this.reader = stringReader;
	}
	
	public String getMessage() {
		String msg = "\n\nInvalid character "+getPrintChar(reader.get(), false)+" at index "+reader.position();
		
		msg += "\n\n";
		
		String string = reader.string();
		
		String preview = "";
		String arrowLine = "";
		
		for(int i=0; i < string.length(); i++) {
			String print = getPrintChar(string.charAt(i), true);
			preview += print;
			for(int j=0; j < print.length(); j++) {
				if(i == reader.position()) {
					arrowLine += "^";
				}else {
					arrowLine += " ";
				}
			}
		}
		
		msg += preview + "\n" + arrowLine + "\n";
		
		return msg;
	}
	
	public static String getPrintChar(char c, boolean a) {
		if(c == '\\') return a ? "\\" : "\\\\";
		if(c == '\n') return a ? " " : "\\n";
		if(c == '\t') return a ? " " : "\\t";
		return "" + c;
	}
	
}
