package b100.xml.element;

import java.util.ArrayList;
import java.util.List;

import b100.rw.Writer;

public class XmlContentTag extends XmlTag<List<XmlTag<?>>>{

	public XmlContentTag(String id) {
		super(id, new ArrayList<XmlTag<?>>());
	}

	public Writer write(Writer writer) {
		writer.write("<"+name);
		attributes.write(writer);
		writer.write(">");
		
		if(content.size() > 0)writer.writeln("");
		
		writer.add();

		for(XmlTag<?> tag : content) {
			tag.write(writer);
			writer.writeln();
		}
		
		writer.dec();
		writer.write("</"+name+">");
		
		return writer;
	}
	
	public void add(XmlTag<?> tag) {
		content.add(tag);
	}
	
	public XmlTag<?> get(String id){
		for(XmlTag<?> tag : content) {
			if(tag.name.equals(id)) {
				return tag;
			}
		}
		return null;
	}

}
