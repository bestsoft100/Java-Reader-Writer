package b100.xml;

import b100.utils.StringParser;
import b100.utils.StringReader;

public class XmlParser extends StringParser<XmlFile>{

	public XmlFile parse(String string) {
		return XmlFile.read(new StringReader(string));
	}

}
