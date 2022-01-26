package b100.json.element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import b100.rw.Reader;
import b100.rw.UnexpectedCharacterException;
import b100.rw.Writer;
import b100.utils.ArrayIterator;

public class JsonArray implements JsonElement, Iterable<JsonElement>{
	
	private JsonElement[] data;
	
	public JsonArray(int length) {
		this.data = new JsonElement[length];
	}
	
	public JsonArray(List<? extends JsonElement> elements) {
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
	
	public int length() {
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

	public void write(Writer writer) {
		//writer.writeln(toString());
		writer.writeln("[");
		writer.add();
		
		for(int i=0; i < data.length; i++) {
			JsonElement element = data[i];
			
			element.write(writer);
			
			if(i < data.length - 1) {
				writer.writeln(",");
			}else {
				writer.writeln();
			}
		}

		writer.dec();
		writer.write("]");
	}
	
	public static JsonArray read(Reader reader) {
		reader.skip();
		reader.skipWhitespace();
		
		if(reader.get() == ']') {
			reader.skip();
			reader.skipWhitespace();
			return new JsonArray(0);
		}
		
		List<JsonElement> elements = new ArrayList<>();
		
		while(true) {
			elements.add(JsonElement.read(reader));
			reader.skipWhitespace();
			if(reader.get() == ',') {
				reader.skip();
				reader.skipWhitespace();
				continue;
			}else if(reader.get() == ']') {
				reader.skip();
				return new JsonArray(elements);
			}else {
				throw new UnexpectedCharacterException(reader);
			}
		}
	}

	public Iterator<JsonElement> iterator() {
		return new ArrayIterator<>(data);
	}
	
}
