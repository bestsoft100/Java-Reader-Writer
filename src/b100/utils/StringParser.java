package b100.utils;

import java.io.File;
import java.io.InputStream;

public abstract class StringParser<E> {
	
	public abstract E parse(String string);
	
	public E parseFileContent(File file) {
		return parse(StringUtils.getFileContentAsString(file));
	}
	
	public E parseFileContent(String path) {
		return parseFileContent(new File(path));
	}

	public E parseStream(InputStream stream) {
		return parse(StringUtils.readInputString(stream));
	}

	public E parseWebsite(String url) {
		return parse(StringUtils.getWebsiteContentAsString(url));
	}
	
}
