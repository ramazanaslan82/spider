package request.param;

public class Login {

	private String loginUrl;

	private String username;
	private String password;

	private Element usernameElement;
	private Element passwordElement;
	private Element submitElement;
	private Element logoutCheckElement;

	public Login() {
		super();
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Element getUsernameElement() {
		return usernameElement;
	}

	public void setUsernameElement(Element usernameElement) {
		this.usernameElement = usernameElement;
	}

	public Element getPasswordElement() {
		return passwordElement;
	}

	public void setPasswordElement(Element passwordElement) {
		this.passwordElement = passwordElement;
	}

	public Element getSubmitElement() {
		return submitElement;
	}

	public void setSubmitElement(Element submitElement) {
		this.submitElement = submitElement;
	}

	public Element getLogoutCheckElement() {
		return logoutCheckElement;
	}

	public void setLogoutCheckElement(Element logoutCheckElement) {
		this.logoutCheckElement = logoutCheckElement;
	}

}
