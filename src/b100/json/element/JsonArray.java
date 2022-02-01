package b100.json.element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import b100.utils.ArrayIterator;
import b100.utils.InvalidCharacterException;
import b100.utils.StringReader;
import b100.utils.StringWriter;
import b100.utils.Utils;

public class JsonArray implements JsonElement, Iterable<JsonElement>{
	
	private JsonElement[] elements;
	
	public JsonArray(int length) {
		elements = new JsonElement[length];
	}
	
	public JsonArray(StringReader reader) {
		List<JsonElement> elementsList = new ArrayList<>();
		
		reader.skipWhitespace();
		reader.expectAndSkip('[');
		
		while(true) {
			reader.skipWhitespace();
			
			if(reader.get() == ']') {
				reader.next();
				break;
			} else {
				//Read element
				elementsList.add(JsonElement.readElement(reader));
				reader.skipWhitespace();

				//Read next element in this array or leave
				if(reader.get() == ',') {
					reader.next();
					continue;
				}
				else if(reader.get() == ']') {
					reader.next();
					break;
				}
				else {
					throw new InvalidCharacterException(reader);
				}
			}
		}
		
		elements = Utils.toArray(JsonElement.class, elementsList);
	}
	
	public JsonElement query(Condition condition) {
		for(JsonElement e : elements) {
			if(condition.isTrue(e))return e;
		}
		return null;
	}
	
	public String toString() {
		return "JsonArray: "+elements.length+" elements";
	}

	public void write(StringWriter writer) {
		writer.write("[");
		writer.addTab();

		int i=0;
		for(JsonElement element : elements) {
			element.write(writer);
			if(i < elements.length - 1) writer.write(", ");
			i++;
		}
		
		writer.removeTab();
		writer.write("]");
	}

	public Iterator<JsonElement> iterator() {
		return new ArrayIterator<>(elements);
	}
	
	public int length() {
		return elements.length;
	}
	
	public JsonElement get(int i) {
		return elements[i];
	}

	public void set(int i, JsonElement element) {
		elements[i] = element;
	}
	
	public static interface Condition{
		
		public boolean isTrue(JsonElement e);
		
	}

}
