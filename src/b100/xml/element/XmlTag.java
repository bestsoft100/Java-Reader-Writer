package b100.xml.element;

import b100.rw.Reader;
import b100.rw.UnexpectedCharacterException;
import b100.rw.Writer;

public abstract class XmlTag<E> {
	
	protected String id;
	protected XmlAttributeList attributes;
	protected E content;
	
	public XmlTag(String id, E content) {
		if(id == null)
			throw new NullPointerException();
		if(id.length() == 0)
			throw new RuntimeException("Empty String");
		
		this.id = id;
		this.content = content;
	}

	public abstract Writer write(Writer writer);
	
	public E getContent() {
		return content;
	}
	
	public void setContent(E content) {
		this.content = content;
	}
	
	public XmlAttributeList getAttributes() {
		return attributes;
	}
	
	public void setAttributes(XmlAttributeList attributes) {
		this.attributes = attributes;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public static XmlTag<?> read(Reader reader) {
//		System.out.println("Reading Tag");
		
		reader.expectSkip("<");
		
		String id = readId(reader);
		XmlAttributeList attributes = XmlAttributeList.read(reader);
		
		reader.expectSkip(">");
//		System.out.println("Tag: "+id+", Attributes: "+attributes.size());
		
		XmlTag<?> tag;
		String closeTag = "</"+id+">";
		
		reader.skipWhitespace();
		
		if(reader.isNext(closeTag)){ //Tag is empty
			reader.expectSkip(closeTag);
			tag = new XmlStringTag(id, null);
		}else if(reader.get() == '<') { //Tag is content tag
			tag = readContentTag(id, reader);
		}else { //Tag is string tag
			tag = readStringTag(id, reader);
		}
		
		
		
		tag.setAttributes(attributes);
		return tag;
	}
	
	public static XmlContentTag readContentTag(String id, Reader reader) {
		XmlContentTag tag = new XmlContentTag(id);
		String closeTag = "</"+id+">";
		
		while(true) {
			tag.add(read(reader));
			reader.skipWhitespace();
			
			if(reader.isNext("</")) {
				reader.expectSkip(closeTag);
				break;
			}
		}
		
		return tag;
	}
	
	public static XmlStringTag readStringTag(String id, Reader reader) {
		String closeTag = "</"+id+">";
		String value = "";
		
		while(true) {
			if(reader.get() == '<') {
				reader.expectSkip(closeTag);
				break;
			}else {
				value += reader.getSkip();
			}
		}
		
		return new XmlStringTag(id, value);
	}
	
	public static String readId(Reader reader) {
		String id = "";
		
		while(true) {
			if(reader.get() == '/') {
				throw new UnexpectedCharacterException(reader);
			}else if(reader.get() == ' ' || reader.get() == '>') {
				return id;
			}else {
				id += reader.getSkip();
			}
		}
	}
	
	public String toString() {
		return write(new Writer()).toString();
	}
	
	public XmlContentTag getAsContentTag() {
		return (XmlContentTag) this;
	}
	
	public XmlStringTag getAsStringTag() {
		return (XmlStringTag) this;
	}
	
}
