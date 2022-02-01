package b100.json;

import b100.json.element.JsonObject;
import b100.utils.StringParser;
import b100.utils.StringReader;

public class JsonParser extends StringParser<JsonObject> {
	
	public JsonObject parse(String string) {
		return new JsonObject(new StringReader(string));
	}
	
}
