package b100.json.element;

import b100.rw.Reader;
import b100.rw.UnexpectedCharacterException;
import b100.rw.Writer;

public class JsonBoolean implements JsonPrimitive{
	
	private boolean value;
	
	public JsonBoolean(boolean value) {
		this.value = value;
	}

	public void write(Writer writer) {
		writer.write("" + value());
	}
	
	public boolean value() {
		return value;
	}
	
	public void setValue(boolean value) {
		this.value = value;
	}

	public static JsonElement read(Reader reader) {
		boolean b = false;
		
		if(reader.isNext("true")) {
			reader.expectSkip("true");
			b = true;
		}else if(reader.isNext("false")) {
			reader.expectSkip("false");
			b = false;
		}else {
			throw new UnexpectedCharacterException(reader);
		}
		
		return new JsonBoolean(b);
	}
	
}
