package b100.json.element;

import b100.rw.Writer;
import b100.rw.Reader;

public class JsonNumber implements JsonElement{
	
	private String numString;
	
	public JsonNumber(int i) {
		this.numString = "" + i;
	}
	
	public JsonNumber(long i) {
		this.numString = "" + i;
	}
	
	private JsonNumber(String numString) {
		this.numString = numString;
	}
	
	public int getInt() {
		return Integer.parseInt(numString);
	}
	
	public long getLong() {
		return Long.parseLong(numString);
	}
	
	public double getDouble() {
		return Double.parseDouble(numString);
	}
	
	public float getFloat() {
		return Float.parseFloat(numString);
	}
	
	public byte getByte() {
		return Byte.parseByte(numString);
	}
	
	public short getShort() {
		return Short.parseShort(numString);
	}
	
	public static JsonNumber read(Reader reader) {
		String numString = "";
		
		while(true) {
			char c = reader.get();
			
			if(c == ',' || c == ']' || c == '}') {
				break;
			}else {
				numString += c;
			}
			
			reader.skip();
		}
		
		return new JsonNumber(numString);
	}
	
	public String toString() {
		return "" + numString;
	}

	public void write(Writer writer) {
		writer.write(numString);
	}
	
}
