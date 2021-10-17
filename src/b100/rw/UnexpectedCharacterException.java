package b100.rw;

public class UnexpectedCharacterException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	public static int printRange = 6;
	
	private Reader reader;
	
	public UnexpectedCharacterException(Reader reader) {
		this.reader = reader;
	}
	
	public String getMessage() {
		String[] lines = reader.getLines();
		
		String string = "Unexpected Character '"+reader.get()+"' at line "+reader.getLine()+" column "+reader.getColumn()+"\n";
		
		int startLine = reader.getLine() - printRange;
		if(startLine < 0) startLine = 0;
		int endLine = reader.getLine() + printRange;
		if(endLine > lines.length) endLine = lines.length;
		
		for(int i=startLine; i < endLine; i++) {
			string += lines[i] + "\n";
			if(i == reader.getLine()) {
				for(int j=0; j < reader.getColumn(); j++) {
					string += " ";
				}
				string += "^\n";
			}
		}
		
		return string;
	}
	
}
