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
	
	public default JsonBoolean getAsBoolean() {
		return (JsonBoolean) this;
	}
	
	public default boolean isObject() {
		return this instanceof JsonObject;
	}
	
	public default boolean isArray() {
		return this instanceof JsonArray;
	}
	
	public default boolean isNumber() {
		return this instanceof JsonNumber;
	}
	
	public default boolean isString() {
		return this instanceof JsonString;
	}
	
	public default boolean isBoolean() {
		return this instanceof JsonBoolean;
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
