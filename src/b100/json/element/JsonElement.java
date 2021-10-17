package b100.json.element;

import b100.rw.Writer;
import b100.rw.Reader;
import b100.rw.UnexpectedCharacterException;

public interface JsonElement {
	
	public default JsonObject getAsObject() {
		return (JsonObject) this;
	}
	
	public default JsonArray getAsArray() {
		return (JsonArray) this;
	}
	
	public default JsonNumber getAsNumber() {
		return (JsonNumber) this;
	}
	
	public default JsonString getAsString() {
		return (JsonString) this;
	}
	
	public void write(Writer writer);

	public static JsonElement read(Reader reader) {
		JsonElement element = null;
		char c = reader.get();
		
		if(c == '"') element = JsonString.read(reader);
		if(c == '[') element = JsonArray.read(reader);
		if(c == '{') element = JsonObject.read(reader);
		if(c >= '0' && c <= '9') element = JsonNumber.read(reader);
		if(reader.isNext("true") || reader.isNext("false")) element = JsonBoolean.read(reader);
		
		if(element == null) {
			throw new UnexpectedCharacterException(reader);
		}
		
		return element;
	}
	
}
