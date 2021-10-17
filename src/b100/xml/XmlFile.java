package b100.xml;

import b100.rw.Reader;
import b100.rw.Writer;
import b100.xml.element.XmlAttributeList;
import b100.xml.element.XmlTag;

public class XmlFile{
	
	private XmlAttributeList meta;
	private XmlTag<?> rootElement;
	
	public XmlFile() {
		meta = new XmlAttributeList();
	}

	public static XmlFile read(Reader reader) {
		XmlFile file = new XmlFile();
		
		//Meta
		reader.skipWhitespace();
		reader.expectSkip("<?xml");
		
		file.meta = XmlAttributeList.read(reader);
		reader.expectSkip("?>");
		
		//Content
		file.rootElement = XmlTag.read(reader);
		
		return file;
	}
	
	public Writer write(Writer writer) {
		writer.write("<?xml");
		meta.write(writer);
		writer.writeln("?>");
		rootElement.write(writer);
		return writer;
	}
	
	public String toString() {
		return write(new Writer()).toString();
	}
	
	public XmlTag<?> getRootElement() {
		return rootElement;
	}
	
}
