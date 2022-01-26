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
			
			if(c == '\\') {
				reader.skip();
				c = reader.get();

				if(c == 'n') {
					string += '\n';
				}
				if(c == 't') {
					string += '\t';
				}
				if(c == '\\') {
					string += '\\';
				}
				if(c == '"') {
					string += '"';
				}
			}else
			if(c == '"') {
				reader.skip();
				return new JsonString(string);
			}else {
				string += c;
			}
		}
	}

	public void write(Writer writer) {
		if(value == null) {
			writer.write("\"\"");
			return;
		}
		
		writer.write("\"");
		
		for(int i=0; i < value.length(); i++) {
			char c = value.charAt(i);

			if(c == '\n') {
				writer.write("\\n");
			}else
			if(c == '\t') {
				writer.write("\\t");
			}else
			if(c == '\"') {
				writer.write("\\\"");
			}else
			if(c == '\\') {
				writer.write("\\\\");
			}else {
				writer.write(c);
			}
		}
		
		writer.write("\"");
	}
	
	public static void main(String[] args) {
		String testString = "\"\\\"Hello World!\\\"\"";
		
		System.out.println("TestString: "+testString);
		
		Reader reader = new Reader(testString);
		
		JsonString jsonString = read(reader);
		
		System.out.println(jsonString.getAsString());
	}
	
}
