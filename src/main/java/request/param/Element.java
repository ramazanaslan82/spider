package request.param;

public class Element {

	private ElementType type;
	private String key;
	private Action action;

	public Element(ElementType type, String key, Action action) {
		super();
		this.type = type;
		this.key = key;
		this.action = action;
	}

	public ElementType getType() {
		return type;
	}

	public void setType(ElementType type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

}
