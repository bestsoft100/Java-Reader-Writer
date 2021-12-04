package b100.json.element;

import b100.rw.Writer;

public class JsonObjectEntry{
	
	private String id;
	private JsonElement value;
	
	public JsonObjectEntry(String id, JsonElement value) {
		this.id = id;
		this.value = value;
	}
	
	public String toString() {
		Writer writer = new Writer();
		
		new JsonString(id).write(writer);
		writer.write(": ");
		value.write(writer);
		
		return writer.toString();
	}
	
	public String name() {
		return id;
	}
	
	public JsonElement value() {
		return value;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setValue(JsonElement value) {
		this.value = value;
	}
	
}
