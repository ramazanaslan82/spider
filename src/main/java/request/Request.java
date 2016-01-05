package request;

import java.util.ArrayList;
import java.util.List;

import request.param.Activity;
import request.param.Login;
import request.param.ResponseActivity;
import request.param.Url;

public class Request {

	private Url url;
	private Login login;
	private List<Activity> steps = new ArrayList<Activity>();
	private List<ResponseActivity> responseSteps = new ArrayList<ResponseActivity>();

	public Request() {
		super();
	}

	public Url getUrl() {
		return url;
	}

	public void setUrl(Url url) {
		this.url = url;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public List<Activity> getSteps() {
		return steps;
	}

	public void setSteps(List<Activity> steps) {
		this.steps = steps;
	}

	public List<ResponseActivity> getResponseSteps() {
		return responseSteps;
	}

	public void setResponseSteps(List<ResponseActivity> responseSteps) {
		this.responseSteps = responseSteps;
	}

}
