package b100.json.element;

import b100.rw.Writer;
import b100.rw.Reader;

public class JsonString implements JsonElement{
	
	private String value;

	public JsonString(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	public String toString() {
		return value;
	}
	
	public static JsonString read(Reader reader) {
		String string = "";
		
		reader.expect('"');
		
		while(true) {
			reader.skip();
			char c = reader.get();
			if(c == '"') {
				reader.skip();
				return new JsonString(string);
			}else {
				string += c;
			}
		}
	}

	public void write(Writer writer) {
		writer.write("\"" + value + "\"");
	}
	
}
