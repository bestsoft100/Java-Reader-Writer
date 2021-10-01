package b100.json;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import b100.json.element.JsonArray;
import b100.json.element.JsonElement;
import b100.json.element.JsonNumber;
import b100.json.element.JsonObject;
import b100.json.element.JsonString;

public abstract class JsonParser {
	
	public static JsonObject parseUrl(String url) {
		URL u = null;
		
		try{
			u = new URL(url);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return parse(u);
	}
	
	public static JsonObject parseFile(File file) {
		return readObject(new JsonReader(Utils.readFile(file)));
	}
	
	public static JsonObject parse(URL url) {
		String content = Utils.readAll(url);
		
		if(content.length() == 0) return null;
		
		return parse(content);
	}
	
	public static JsonObject parse(String string) {
		return readObject(new JsonReader(string));
	}
	
	public static JsonObject readObject(JsonReader reader) {
		JsonObject object = new JsonObject();
		
		reader.expect('{');
		
		reader.skip();
		reader.skipWhitespace();

		if(reader.get() == '}') {
			reader.skip();
			return object;
		}
		
		while(true) {
			String id = readID(reader);
			
			reader.skipWhitespace();
			reader.expect(':');
			
			reader.skip();
			reader.skipWhitespace();
			
			JsonElement element = readElement(reader);
			object.add(id, element);
			
			reader.skipWhitespace();
			
			if(reader.get() == ',') {
				reader.skip();
				reader.skipWhitespace();
				continue;
			}else if(reader.get() == '}') {
				reader.skip();
				return object;
			}else {
				throw new JsonException(reader);
			}
		}
			
	}
	
	private static String readID(JsonReader reader) {
		String name = "";
		
		if(reader.get() != '"') throw new JsonException(reader);
		
		if(reader.get() == '"') {
			while(true) {
				reader.skip();
				
				char c = reader.get();
				
				if(c == '"') {
					reader.skip();
					return name;
				}
				else {
					name += c;
				}
			}
		}
		
		throw new JsonException(reader);
	}

	private static JsonElement readElement(JsonReader reader) {
		JsonElement element = null;
		char c = reader.get();
		
		if(c == '"') element = JsonString.read(reader);
		if(c == '[') element = readArray(reader);
		if(c == '{') element = readObject(reader);
		if(isNumber(c)) element = JsonNumber.read(reader);
		
		if(element == null) {
			throw new JsonException(reader);
		}
		
		return element;
	}
	
	private static boolean isNumber(char c) {
		return c >= '0' && c <= '9';
	}
	
	public static JsonArray readArray(JsonReader reader) {
		reader.skip();
		reader.skipWhitespace();
		List<JsonElement> elements = new ArrayList<>();
		
		while(true) {
			elements.add(readElement(reader));
			reader.skipWhitespace();
			if(reader.get() == ',') {
				continue;
			}else if(reader.get() == ']') {
				reader.skip();
				return new JsonArray(elements);
			}else {
				throw new JsonException(reader);
			}
		}
	}
	
}
