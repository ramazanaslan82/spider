package request.param;

public class ResponseActivity {

	private ElementType type;
	private String key;
	private Action action;
	private String responseKey;

	public ResponseActivity(ElementType type, String key, Action action, String responseKey) {
		super();
		this.type = type;
		this.key = key;
		this.action = action;
		this.responseKey = responseKey;
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

	public String getResponseKey() {
		return responseKey;
	}

	public void setResponseKey(String responseKey) {
		this.responseKey = responseKey;
	}

}
