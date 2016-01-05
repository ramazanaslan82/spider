package request.param;

public class Activity {

	private ElementType type;
	private String key;
	private Action action;
	private String textValue;
	private int intValue;

	public Activity(ElementType type, int intValue) {
		super();
		this.type = type;
		this.intValue = intValue;
	}

	public Activity(ElementType type, String textValue) {
		super();
		this.type = type;
		this.textValue = textValue;
	}
	
	public Activity(ElementType type, String key, Action action) {
		super();
		this.type = type;
		this.key = key;
		this.action = action;
	}

	public Activity(ElementType type, String key, Action action, String textValue) {
		super();
		this.type = type;
		this.key = key;
		this.action = action;
		this.textValue = textValue;
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

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

}
