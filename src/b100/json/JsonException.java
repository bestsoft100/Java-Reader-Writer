package b100.json;

public class JsonException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private JsonReader reader;
	
	public JsonException(JsonReader reader) {
		this.reader = reader;
	}
	
	public String getMessage() {
		String[] lines = reader.getLines();
		
		String string = "Unexpected Character '"+reader.get()+"' at line "+reader.getLine()+" column "+reader.getColumn()+"\n";
		
		for(int i=0; i < lines.length; i++) {
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
