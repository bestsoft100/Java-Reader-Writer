package b100.json.element;

import b100.utils.InvalidCharacterException;
import b100.utils.StringReader;
import b100.utils.StringWriter;

public class JsonNumber implements JsonElement{
	
	private String numberString;
	
	public JsonNumber(Number n) {
		this.numberString = "" + n;
	}
	
	public JsonNumber(StringReader reader) {
		numberString = "";
		
		reader.skipWhitespace();
		
		while(true) {
			if(reader.get() >= '0' && reader.get() <= '9') {
				numberString += reader.get();
			}
			else if((reader.get() >= 'a' && reader.get() <= 'z') || (reader.get() >= 'A' && reader.get() <= 'Z')) {
				numberString += reader.get();
			}
			else if(reader.get() == '.') {
				numberString += reader.get();
			}
			else if(reader.get() == ',' || reader.isWhitespace(reader.get())) {
				break;
			}
			else {
				throw new InvalidCharacterException(reader);
			}
			reader.next();
		}
	}
	
	public String toString() {
		return numberString;
	}
	
	public int getInteger() {
		return Integer.parseInt(numberString);
	}
	
	public long getLong() {
		return Long.parseLong(numberString);
	}
	
	public double getDouble() {
		return Double.parseDouble(numberString);
	}
	
	public float getFloat() {
		return Float.parseFloat(numberString);
	}
	
	public byte getByte() {
		return Byte.parseByte(numberString);
	}
	
	public short getShort() {
		return Short.parseShort(numberString);
	}

	public void write(StringWriter writer) {
		writer.write(numberString);
	}
	
	
}
