package b100.json.element;

public abstract class JsonPrimitive<E> implements JsonElement{
	
	private E value;
	
	public JsonPrimitive(E val) {
		this.value = val;
	}
	
	public E getValue() {
		return value;
	}
	
	public void setValue(E value) {
		this.value = value;
	}
	
	public String toString() {
		return value.toString();
	}
	
}
