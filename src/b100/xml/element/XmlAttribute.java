package b100.xml.element;

import b100.rw.Reader;
import b100.rw.UnexpectedCharacterException;
import b100.rw.Writer;

public class XmlAttribute{
	
	private String id;
	private String value;
	
	public XmlAttribute(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}
	
	public static XmlAttribute read(Reader reader) {
		String id = "";
		String value = "";
		
		while(true) {
			if(reader.get() == '=') {
				reader.skip();
				break;
			}
			if(reader.get() == ' ') {
				throw new UnexpectedCharacterException(reader);
			}
			
			id += reader.get();
			reader.skip();
		}
		
		reader.expectSkip("\"");
		
		while(true) {
			if(reader.get() == '"') {
				reader.skip();
				break;
			}else {
				value += reader.get();
				reader.skip();
			}
		}
		
		return new XmlAttribute(id, value);
	}

	public Writer write(Writer writer) {
		writer.write(id + "=\"" + value + "\"");
		return writer;
	}
	
	
	
}
