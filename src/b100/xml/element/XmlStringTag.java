package b100.xml.element;

import b100.rw.Writer;

public class XmlStringTag extends XmlTag<String>{

	public XmlStringTag(String id, String value) {
		super(id, value);
	}

	public Writer write(Writer writer) {
		writer.write("<"+name);
		attributes.write(writer);
		writer.write(">"+(content!=null?content:"")+"</"+name+">");
		return writer;
	}

	public long getLong() {
		return Long.parseLong(content);
	}

	public int getInt() {
		return Integer.parseInt(content);
	}

}
