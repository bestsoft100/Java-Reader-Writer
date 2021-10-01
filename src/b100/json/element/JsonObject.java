package b100.json.element;

import java.util.ArrayList;
import java.util.List;

import b100.json.JsonWriter;

public class JsonObject implements JsonElement{
	
	private List<Entry> data = new ArrayList<>();
	
	public static class Entry{
		
		private String id;
		private JsonElement value;
		
		public Entry(String id, JsonElement value) {
			this.id = id;
			this.value = value;
		}
		
	}
	
	public JsonObject(Entry...entries) {
		for(Entry entry : entries) {
			data.add(entry);
		}
	}
	
	public JsonObject add(String id, JsonElement element) {
		data.add(new Entry(id, element));
		return this;
	}
	
	public JsonObject add(String id, String string) {
		this.add(id, new JsonString(string));
		return this;
	}
	
	public JsonElement get(String id) {
		for(Entry entry : data) {
			if(entry.id.equals(id)) {
				return entry.value;
			}
		}
		throw new NullPointerException("Missing Property: "+id);
	}

	public String getString(String string) {
		JsonString jsonString = (JsonString) get(string);
		return jsonString.getValue();
	}

	public JsonArray getArray(String string) {
		JsonArray jsonString = (JsonArray) get(string);
		return jsonString;
	}

	public boolean has(String id) {
		try {
			get(id); return true;
		}catch (NullPointerException e) {
			return false;
		}
	}
	
	public boolean has(String id, String value) {
		if(has(id)) {
			return getString(id).equals(value);
		}
		return false;
	}
	
	public String toString() {
		return new JsonWriter(this).toString();
	}
	
	public void write(JsonWriter writer) {
		writer.writeln("{");
		writer.add();
		
		for(int i=0; i < data.size(); i++) {
			Entry e = data.get(i);
			
			writer.write("\"" + e.id + "\": ");
			e.value.write(writer);
			
			if(i < data.size() - 1) {
				writer.writeln(",");
			}else {
				writer.writeln("");
			}
		}
		
		writer.dec();
		writer.write("}");
	}
	
}
