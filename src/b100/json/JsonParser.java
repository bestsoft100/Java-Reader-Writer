package b100.json;

import b100.json.element.JsonObject;
import b100.rw.Parser;
import b100.rw.Reader;

public class JsonParser extends Parser<JsonObject>{
	
	public JsonObject parse(String string) {
		return JsonObject.read(new Reader(string));
	}
	
}
