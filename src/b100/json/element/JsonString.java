package b100.json.element;

import b100.json.JsonReader;
import b100.json.JsonWriter;

public class JsonString implements JsonElement{
	
	private String value;

	public JsonString(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public String toString() {
		return value;
	}
	
	public static JsonString read(JsonReader reader) {
		String string = "";
		
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

	public void write(JsonWriter writer) {
		writer.write("\"" + value + "\"");
	}
	
}
