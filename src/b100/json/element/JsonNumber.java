package b100.json.element;

import b100.utils.InvalidCharacterException;
import b100.utils.StringReader;
import b100.utils.StringWriter;

public class JsonNumber implements JsonElement{
	
	public Number value;
	
	public JsonNumber(Number number) {
		this.value = number;
	}
	
	public JsonNumber(int i) {
		this.value = i;
	}
	
	public JsonNumber(long l) {
		this.value = l;
	}
	
	public JsonNumber(double d) {
		this.value = d;
	}
	
	public JsonNumber(float f) {
		this.value = f;
	}
	
	public JsonNumber(StringReader reader) {
		StringWriter numberString = new StringWriter();
		boolean isDecimal = false;
		boolean isLong = false;
		
		while(true) {
			char c = reader.get();
			
			if(c >= '0' && c <= '9') {
				numberString.write(c);
				reader.next();
			} else if(c == '.') {
				isDecimal = true;
				numberString.write(c);
				reader.next();
			} else if(c == '-') {
				numberString.write(c);
				reader.next();
			} else if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				if((isDecimal && (c == 'd' || c == 'D')) || (!isDecimal && (c == 'l' || c == 'L'))) {
					isLong = true;
				}
				numberString.write(c);
				reader.next();
			} else if(reader.isWhitespace(c) || c == ',' || c == '}') {
				break;
			} else {
				throw new InvalidCharacterException(reader);
			}
		}
		
		String str = numberString.toString();
		if(isDecimal) {
			if(isLong) {
				value = Double.parseDouble(str);
			}else {
				value = Float.parseFloat(str);
			}
		}else {
			if(isLong) {
				value = Long.parseLong(str);
			}else {
				value = Integer.parseInt(str);
			}
		}
	}

	public void write(StringWriter writer) {
		writer.write(value.toString());
		
		if(value instanceof Float) writer.write('f');
		if(value instanceof Long) writer.write('l');
	}
	
	public int getInteger() {
		return value.intValue();
	}
	
	public double getDouble() {
		return value.doubleValue();
	}
	
	public float getFloat() {
		return value.floatValue();
	}
	
	public long getLong() {
		return value.longValue();
	}
	
	public byte getByte() {
		return value.byteValue();
	}
	
	public short getShort() {
		return value.shortValue();
	}
	
	public void set(Number n) {
		this.value = n;
	}
	
	public void set(int i) {
		this.value = i;
	}
	
	public void set(long l) {
		this.value = l;
	}
	
	public void set(float f) {
		this.value = f;
	}
	
	public void set(double d) {
		this.value = d;
	}
	
}
