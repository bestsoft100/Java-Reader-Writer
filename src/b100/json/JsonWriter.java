package b100.json;

import b100.json.element.JsonObject;
import b100.rw.Writer;

public class JsonWriter extends Writer{
	
	public JsonWriter(JsonObject object) {
		object.write(this);
	}
}
