package tester;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import request.Request;
import request.param.Action;
import request.param.Activity;
import request.param.Element;
import request.param.ElementType;
import request.param.Login;
import request.param.ResponseActivity;
import request.param.Url;
import response.Response;
import spider.Processor;

public class RequestTester {

	public static void main(String[] args) {

		Request request = buildRequest();
		
		Gson gson = new Gson();
		String requestJson = gson.toJson(request);
		System.out.println(requestJson);

		Processor processor = new Processor();

		Response response = null;
		try {
			response = processor.process(request);
			String resultJson = gson.toJson(response);
			System.out.println(resultJson);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Yanit:" + response);
	}

	private static Request buildRequest() {
		Request request = new Request();

		Url url = buildUrl();
		request.setUrl(url);

		Login login = buildLogin();
		request.setLogin(login);

		List<Activity> steps = buildSteps();
		request.setSteps(steps);

		List<ResponseActivity> responseSteps = buildResponseSteps();
		request.setResponseSteps(responseSteps);

		return request;
	}

	private static List<ResponseActivity> buildResponseSteps() {
		List<ResponseActivity> steps = new ArrayList<ResponseActivity>();

		ResponseActivity readAdslNumberStep = buildReadAdslNumberStep();
		steps.add(readAdslNumberStep);

		ResponseActivity readSubscriptionStatusStep = buildReadSubscriptionStatusStep();
		steps.add(readSubscriptionStatusStep);

		return steps;
	}

	private static List<Activity> buildSteps() {
		List<Activity> steps = new ArrayList<Activity>();

		// x sayfasina git
		Activity redirectStep = buildRedirectStep();
		steps.add(redirectStep);

		// talimat düzenle ye tikla
		Activity editSubscriptionStep = buildEditSubscriptionStep();
		steps.add(editSubscriptionStep);

		// form gosterilirken bekle
		Activity sleepBeforeStep = buildSleepStep(5000);
		steps.add(sleepBeforeStep);

		// kredi karti alanini doldur
		Activity ccNumberStep = buildCCNumberStep();
		steps.add(ccNumberStep);

		// kredi karti sahibi alanini doldur
		Activity ccOwnerStep = buildCCOwnerStep();
		steps.add(ccOwnerStep);

		// kredi karti gecerlilik bitis yili alanini doldur
		Activity ccExpirationYearStep = buildCCExpirationYearStep();
		steps.add(ccExpirationYearStep);

		// kredi karti gecerlilik bitis ay alanini doldur
		Activity ccExpirationMonthStep = buildCCExpirationMonthStep();
		steps.add(ccExpirationMonthStep);

		// kredi karti guvenlik kodu alanini doldur
		Activity ccCcvStep = buildCCccvStep();
		steps.add(ccCcvStep);

		// kaydet butonuna bas
		Activity saveSubscriptionStep = buildSaveSubscriptionStep();
		steps.add(saveSubscriptionStep);

		// kayit islemini bekle
		Activity sleepAfterStep = buildSleepStep(10000);
		steps.add(sleepAfterStep);

		return steps;
	}

	private static Activity buildSleepStep(int miliseconds) {
		Activity activity = new Activity(ElementType.WAIT, miliseconds);

		return activity;
	}

	private static Activity buildRedirectStep() {
		Activity activity = new Activity(ElementType.REDIRECT,
				"https://www.dsmart.com.tr/Online-Islemler/Faturalarim/Otomatik-Odeme-Talimati");

		return activity;
	}

	private static Activity buildEditSubscriptionStep() {
		Activity activity = new Activity(ElementType.ID, "ctl00_ContentPlaceHolder1_RptSubscriptions_ctl01_BtnUpdate",
				Action.CLICK);

		return activity;
	}

	private static Activity buildCCNumberStep() {
		Activity activity = new Activity(ElementType.ID, "ctl00_ContentPlaceHolder1_TxtCCCreditCardNumber",
				Action.SEND_TEXT, getCCNumber());

		return activity;
	}

	private static Activity buildCCOwnerStep() {
		Activity activity = new Activity(ElementType.ID, "ctl00_ContentPlaceHolder1_TxtCCNameSurname", Action.SEND_TEXT,
				getCCOwner());

		return activity;
	}

	private static Activity buildCCExpirationMonthStep() {
		Activity activity = new Activity(ElementType.ID, "ctl00_ContentPlaceHolder1_DDLCCDateMonth",
				Action.SELECT_BY_VALUE, getCCExpirationMonth());

		return activity;
	}

	private static Activity buildCCExpirationYearStep() {
		Activity activity = new Activity(ElementType.ID, "ctl00_ContentPlaceHolder1_DDLCCDateYear",
				Action.SELECT_BY_VALUE, getCCExpirationYear());

		return activity;
	}

	private static Activity buildCCccvStep() {
		Activity activity = new Activity(ElementType.ID, "ctl00_ContentPlaceHolder1_TxtCCCVC", Action.SEND_TEXT,
				getCCcvv());

		return activity;
	}

	private static Activity buildSaveSubscriptionStep() {
		Activity activity = new Activity(ElementType.ID, "ctl00_ContentPlaceHolder1_BtnSaveAutoPayment", Action.CLICK);

		return activity;
	}

	private static ResponseActivity buildReadAdslNumberStep() {
		ResponseActivity responseActivity = new ResponseActivity(ElementType.CSS_SELECTOR,
				"#ctl00_ContentPlaceHolder1_UpdatePanel1 td:nth-child(2)", Action.READ_TEXT, "adslNumber");

		return responseActivity;
	}

	private static ResponseActivity buildReadSubscriptionStatusStep() {
		ResponseActivity responseActivity = new ResponseActivity(ElementType.CSS_SELECTOR,
				"#ctl00_ContentPlaceHolder1_UpdatePanel1 td:nth-child(3)", Action.READ_TEXT, "subscriptionStatus");

		return responseActivity;
	}

	private static Url buildUrl() {
		Url url = new Url();

		url.setHomepageUrl("https://www.dsmart.com.tr/default.aspx");

		Element element = buildLoggedInCheckElement();
		url.setLoggedInCheckElement(element);

		return url;
	}

	private static Element buildLoggedInCheckElement() {
		return new Element(ElementType.ID, "ctl00_Header_ctl00_dvlogged", Action.EXISTS);
	}

	private static Login buildLogin() {
		Login login = new Login();

		login.setLoginUrl("https://www.dsmart.com.tr/?login=true");

		Element usernameElement = new Element(ElementType.ID, "ctl00_Login_TxtMEmail", Action.SEND_TEXT);
		login.setUsernameElement(usernameElement);

		Element passwordElement = new Element(ElementType.ID, "ctl00_Login_TxtMPassword", Action.SEND_TEXT);
		login.setPasswordElement(passwordElement);

		login.setUsername(getUsername());
		login.setPassword(getPassword());

		Element submitElement = new Element(ElementType.ID, "btn-login", Action.CLICK);
		login.setSubmitElement(submitElement);

		Element logoutElement = new Element(ElementType.CLASS, "guvenliCikis", Action.EXISTS);
		login.setLogoutCheckElement(logoutElement);

		return login;
	}

	private static String getCCOwner() {
		return "Card Owner";
	}

	private static String getCCNumber() {
		return "4022774022774026";
	}

	private static String getCCExpirationMonth() {
		return "12";
	}

	private static String getCCExpirationYear() {
		return "2016";
	}

	private static String getCCcvv() {
		return "000";
	}

	private static String getUsername() {
		return "mail@example.com";
	}

	private static String getPassword() {
		return "passwordHere";
	}

}
