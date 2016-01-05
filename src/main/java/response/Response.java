package response;

public class Response {

	private boolean success;
	private Object data;

	public Response() {
		super();
	}

	public Response(boolean success, Object data) {
		super();
		this.success = success;
		this.data = data;
	}

	public Response(boolean success) {
		super();
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
