package b100.json.element;

import b100.json.JsonWriter;

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
	
	public void write(JsonWriter writer);
	
}
