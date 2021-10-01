package b100.json.element;

import java.util.List;

import b100.json.JsonWriter;

public class JsonArray implements JsonElement{
	
	private JsonElement[] data;
	
	public JsonArray(int length) {
		this.data = new JsonElement[length];
	}
	
	public JsonArray(List<JsonElement> elements) {
		this.data = new JsonElement[elements.size()];
		for(int i=0; i < data.length; i++) {
			data[i] = elements.get(i);
		}
	}

	public JsonElement get(int i) {
		return data[i];
	}
	
	public JsonElement query(Condition condition) {
		for(JsonElement e : data) {
			if(condition.isTrue(e))return e;
		}
		return null;
	}
	
	public void set(int i, JsonElement element) {
		data[i] = element;
	}
	
	public int getLength() {
		return data.length;
	}
	
	public String toString() {
		String string = "[";
		
		boolean first = true;
		for(JsonElement e : data) {
			if(first) {
				first = false;
			}else {
				string += ",";
			}
			string += e;
		}
		string += "]";
		return string;
	}
	
	public static interface Condition{
		
		public boolean isTrue(JsonElement e);
		
	}

	public void write(JsonWriter writer) {
		writer.writeln(toString());
	}
	
}
