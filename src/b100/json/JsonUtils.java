package b100.json;

import b100.json.element.JsonArray;

public abstract class JsonUtils {
	
	public static String[] toStringArray(JsonArray array) {
		String[] stringArray = new String[array.length()];
		
		for(int i=0; i < stringArray.length; i++) {
			stringArray[i] = array.get(i).toString();
		}
		
		return stringArray;
	}
	
}
