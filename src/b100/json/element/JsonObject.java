package b100.json.element;

import java.util.ArrayList;
import java.util.List;

import b100.rw.Writer;
import b100.rw.Reader;
import b100.rw.UnexpectedCharacterException;

public class JsonObject implements JsonElement{
	
	private List<JsonObjectEntry> data = new ArrayList<>();
	private boolean compact = false;
	
	public JsonObjectEntry getEntry(String id) {
		for(JsonObjectEntry entry : data) {
			if(entry.id.equals(id)) {
				return entry;
			}
		}
		return null;
	}
	
	public JsonObject set(String id, JsonElement element) {
		JsonObjectEntry entry = getEntry(id);
		if(entry != null) {
			entry.value = element;
		}else {
			data.add(new JsonObjectEntry(id, element));
		}
		return this;
	}
	
	public JsonObject set(String id, String string) {
		return this.set(id, new JsonString(string));
	}
	
	public JsonObject set(String id, int val) {
		return this.set(id, new JsonNumber(val));
	}
	
	public JsonObject set(String id, long val) {
		return this.set(id, new JsonNumber(val));
	}
	
	public JsonElement get(String id) {
		JsonObjectEntry entry = getEntry(id);
		
		if(entry != null)
			return entry.value;
		
		throw new NullPointerException("Missing Property: "+id);
	}

	public String getString(String string) {
		return get(string).getAsString().getValue();
	}

	public JsonArray getArray(String string) {
		JsonArray jsonString = (JsonArray) get(string);
		return jsonString;
	}

	public boolean has(String id) {
		try {
			return get(id) != null;
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
		return new Writer(this).toString();
	}
	
	public void setCompact(boolean compact) {
		this.compact = compact;
	}
	
	public boolean isCompact() {
		return compact;
	}
	
	public void write(Writer writer) {
		if(this.data.size() == 0) {
			writer.write("{}");
			return;
		}
		
		if(!compact) {
			writer.writeln("{");
			writer.add();
			
			for(int i=0; i < data.size(); i++) {
				JsonObjectEntry e = data.get(i);
				
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
		if(compact) {
			writer.write("{ ");
			for(int i=0; i < data.size(); i++) {
				JsonObjectEntry e = data.get(i);
				
				writer.write("\"" + e.id + "\": ");
				e.value.write(writer);

				if(i < data.size() - 1) {
					writer.write(", ");
				}
			}
			
			writer.write(" }");
		}
	}
	
	public static JsonObject read(Reader reader) {
		JsonObject object = new JsonObject();
		
		reader.expect('{');
		
		reader.skip();
		reader.skipWhitespace();

		if(reader.get() == '}') {
			reader.skip();
			return object;
		}
		
		while(true) {
			JsonString id = JsonString.read(reader);
			
			reader.skipWhitespace();
			reader.expect(':');
			
			reader.skip();
			reader.skipWhitespace();
			
			JsonElement element = JsonElement.read(reader);
			object.set(id.toString(), element);
			
			reader.skipWhitespace();
			
			if(reader.get() == ',') {
				reader.skip();
				reader.skipWhitespace();
				continue;
			}else if(reader.get() == '}') {
				reader.skip();
				return object;
			}else {
				throw new UnexpectedCharacterException(reader);
			}
		}
	}
	
}
