package b100.rw;

import java.io.File;
import java.io.InputStream;

import b100.utils.StringUtils;

public abstract class Parser<E> {
	
	public abstract E parse(String string);
	
	public E parse(InputStream inputStream) {
		if(inputStream == null) return null;
		String content = StringUtils.readInputStream(inputStream);
		if(content.length() == 0) return null;
		return parse(content);
	}
	
	public E parseFile(File file) {
		String content = StringUtils.loadFromFile(file);
		
		return parse(content);
	}
	
	public E parseFile(String path) {
		StringUtils.validateNotEmpty(path);
		
		return parseFile(new File(path));
	}
	
}
