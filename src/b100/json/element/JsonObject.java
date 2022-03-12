package b100.json.element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import b100.utils.InvalidCharacterException;
import b100.utils.StringReader;
import b100.utils.StringWriter;

public class JsonObject implements JsonElement, Iterable<JsonEntry>{
	
	private List<JsonEntry> entries;
	
	private boolean compact = false;
	
	public JsonObject() {
		entries = new ArrayList<>();
	}
	
	public JsonObject(StringReader reader) {
		this();
		
		reader.skipWhitespace();
		reader.expectAndSkip('{');
		
		while(true) {
			reader.skipWhitespace();
			if(reader.get() == '"') {
				//Read ID
				String id = new JsonString(reader).value;
				
				reader.skipWhitespace();
				reader.expectAndSkip(':');
				
				//Read element
				JsonElement element = JsonElement.readElement(reader);
				
				//Insert element
				set(id, element);
				
				//Read next element in this object or leave
				reader.skipWhitespace();
				if(reader.get() == ',') {
					reader.next();
					continue;
				}else if(reader.get() == '}') {
					reader.next();
					break;
				}else {
					throw new InvalidCharacterException(reader);
				}
			}else if(reader.get() == '}') {
				reader.next();
				break;
			}else {
				throw new InvalidCharacterException(reader);
			}
		}
	}

	public void write(StringWriter writer) {
		writer.write("{");
		writer.addTab();
		
		int i=0;
		for(JsonEntry entry : entries) {
			if(!isCompact()) writer.write('\n');
			else writer.write(' ');
			new JsonString(entry.name).write(writer);
			writer.write(": ");
			entry.value.write(writer);
			if(i < entries.size() - 1) writer.write(',');
			i++;
		}
		
		if(i > 0) {
			if(!isCompact()) {
				writer.write('\n');
			}else{
				writer.write(' ');
			}
			
		}
		writer.removeTab();
		writer.write("}");
	}

	public JsonObject getObject(String id) {
		JsonElement element = get(id);
		return element != null ? element.getAsObject() : null;
	}

	public JsonArray getArray(String id) {
		JsonElement element = get(id);
		return element != null ? element.getAsArray() : null;
	}

	public JsonNumber getNumber(String id) {
		JsonElement element = get(id);
		return element != null ? element.getAsNumber() : null;
	}

	public JsonString getString(String id) {
		JsonElement element = get(id);
		return element != null ? element.getAsString() : null;
	}

	public JsonBoolean getBoolean(String id) {
		JsonElement element = get(id);
		return element != null ? element.getAsBoolean() : null;
	}

	public int getInt(String id) {
		return getNumber(id).getInteger();
	}

	public long getLong(String id) {
		return getNumber(id).getLong();
	}

	public double getDouble(String id) {
		return getNumber(id).getDouble();
	}

	public float getFloat(String id) {
		return getNumber(id).getFloat();
	}

	public byte getByte(String id) {
		return getNumber(id).getByte();
	}

	public short getShort(String id) {
		return getNumber(id).getShort();
	}
	
	public int getInt(String id, int defaultValue) {
		JsonEntry entry = getOrCreateEntry(id);
		if(entry.value != null && entry.value instanceof JsonNumber) {
			JsonNumber jsonNumber = (JsonNumber) entry.value;
			
			return jsonNumber.getInteger();
		}else {
			entry.value = new JsonNumber(defaultValue);
			return defaultValue;
		}
	}
	
	public JsonElement get(String id) {
		JsonEntry entry = getEntry(id);
		return entry != null ? entry.value : null;
	}
	
	public JsonEntry getOrCreateEntry(String string) {
		JsonEntry entry = getEntry(string);
		if(entry == null) {
			entry = new JsonEntry(string, null);
			entries.add(entry);
		}
		return entry;
	}
	
	public JsonEntry getEntry(String string) {
		for(JsonEntry e : entries) {
			if(e.equalsId(string)) {
				return e;
			}
		}
		return null;
	}
	
	public JsonObject set(String id, JsonElement element) {
		getOrCreateEntry(id).value = element;
		return this;
	}
	
	public JsonObject set(String id, String s) {
		return set(id, new JsonString(s));
	}
	
	public JsonObject set(String id, Number n) {
		return set(id, new JsonNumber(n));
	}
	
	public JsonObject set(String id, boolean b) {
		return set(id, new JsonBoolean(b));
	}
	
	public List<JsonElement> elementList() {
		List<JsonElement> elements = new ArrayList<>();
		for(JsonEntry entry : entries) {
			elements.add(entry.value);
		}
		return elements;
	}
	
	public List<String> idList() {
		List<String> elements = new ArrayList<>();
		for(JsonEntry entry : entries) {
			elements.add(entry.name);
		}
		return elements;
	}
	
	public List<JsonEntry> entryList() {
		return entries;
	}
	
	public String toString() {
		StringWriter writer = new StringWriter();
		write(writer);
		return writer.toString();
	}

	public boolean has(String id) {
		return getEntry(id) != null;
	}

	public Iterator<JsonEntry> iterator() {
		return entries.iterator();
	}

	public void setCompact(boolean b) {
		compact = true;
	}
	
	public boolean isCompact() {
		return compact;
	}

	public boolean has(String name, JsonElement element) {
		JsonEntry entry = getEntry(name);
		if(entry != null) {
			return entry.value.equals(element);
		}else {
			return false;
		}
	}

	public boolean has(String name, String stringtextures) {
		JsonEntry entry = getEntry(name);
		if(entry != null) {
			if(entry.value.isString()) {
				return entry.value.getAsString().value.equals(stringtextures);
			}
		}
		return false;
	}
	
}
