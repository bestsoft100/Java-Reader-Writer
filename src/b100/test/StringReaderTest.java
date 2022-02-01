package b100.test;

import b100.json.JsonParser;
import b100.json.element.JsonObject;
import b100.utils.StringUtils;
import b100.utils.StringWriter;

public class StringReaderTest {
	
	public static void main(String[] args) {
		new StringReaderTest();
	}
	
	public StringReaderTest() {
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parseFileContent("test.json");
		StringWriter jsonWriter = new StringWriter();
		jsonObject.write(jsonWriter);
		StringUtils.saveStringToFile("testout.json", jsonWriter.toString());
		jsonObject = jsonParser.parseFileContent("testout.json");
		
		System.out.println(jsonObject);
	}
	
}
