package b100.xml.element;

import java.util.ArrayList;

import b100.rw.Reader;
import b100.rw.Writer;

public class XmlAttributeList extends ArrayList<XmlAttribute>{

	private static final long serialVersionUID = 1L;
	
	public static XmlAttributeList read(Reader reader) {
		XmlAttributeList list = new XmlAttributeList();
		
		reader.skipWhitespace();
		while(true) {
			if(reader.get() == '>' || reader.get() == '?') {
				break;
			}
			
			list.add(XmlAttribute.read(reader));
			reader.skipWhitespace();
		}
		
		return list;
	}
	
	public Writer write(Writer writer) {
		for(XmlAttribute attribute : this) {
			writer.write(" ");
			attribute.write(writer);
		}
		
		return writer;
	}

}
