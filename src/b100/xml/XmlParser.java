package b100.xml;

import b100.rw.Parser;
import b100.rw.Reader;

public class XmlParser extends Parser<XmlFile>{

	public XmlFile parse(String string) {
		return XmlFile.read(new Reader(string));
	}

}
