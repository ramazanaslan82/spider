package spider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.LineListener;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import request.Request;
import request.param.Action;
import request.param.Activity;
import request.param.Element;
import request.param.ElementType;
import request.param.Login;
import request.param.ResponseActivity;
import request.param.Url;
import response.Response;

public class Processor {

	private static final long PAGE_LOAD_TIMEOUT_MILIS = 150000l;

	public static void setSystemProperties() {
		System.setProperty("webdriver.chrome.driver", "/Users/ramazana/Developer/chromedriver");
	}

	public Response fail(WebDriver driver) {
		if (null != driver) {
			driver.close();
			driver.quit();
			driver = null;
		}
		return new Response();
	}

	public Response success(WebDriver driver, Response response) {
		if (null != driver) {
			driver.close();
			driver.quit();
			driver = null;
		}
		return response;
	}

	public Response process(Request request) throws InterruptedException {
		System.out.println("Basladik");
		setSystemProperties();

		// read all params
		Url url = request.getUrl();
		Login login = request.getLogin();
		List<Activity> steps = request.getSteps();
		List<ResponseActivity> responseSteps = request.getResponseSteps();
		

		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT_MILIS, TimeUnit.MILLISECONDS);

		goHome(driver, url);

		if (!isAlreadyLoggedIn(driver, url)) {
			login(driver, login);
			Thread.sleep(5000);

			if (!isLogoutExists(driver, login)) {
				return fail(driver );
			}
			System.out.println("LOGİN BAŞARILI:)");
		}
		
		processSteps(driver, steps);

		Object data = processResponseSteps(driver, responseSteps);

		Response response = new Response();
		response.setSuccess(true);
		response.setData(data);

		return success(driver, response);
	}
	
	private Map<String, Object> processResponseSteps(WebDriver driver, List<ResponseActivity> responseSteps)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		for(ResponseActivity step : responseSteps)
		{
			processResponseStep(driver, step, data);
		}
		return data;
	}
	
	private void processResponseStep(WebDriver driver, ResponseActivity responseStep, Map<String, Object> data)
	{
		WebElement webElement = findWebElement(driver, responseStep);

		if (Action.READ_TEXT == responseStep.getAction()) {
			String text = webElement.getText();
			data.put(responseStep.getResponseKey(), text);
			
		}
		if(Action.READ_LINK == responseStep.getAction())
		{
			String href = webElement.getAttribute("href");
			data.put(responseStep.getResponseKey(), href);
		}
		if(Action.READ_PDF == responseStep.getAction())
		{
			// @TODO : To be done
		}
	}
	
	private void processSteps(WebDriver driver, List<Activity> steps) throws InterruptedException
	{
		for(Activity step : steps)
		{
			processStep(driver, step);
		}
	}
	
	private void processStep(WebDriver driver, Activity step) throws InterruptedException
	{
		if(ElementType.REDIRECT == step.getType())
		{
			driver.get(step.getTextValue());
		}
		if(ElementType.WAIT == step.getType())
		{
			Thread.sleep(step.getIntValue());
		}
		if(ElementType.ID == step.getType() || ElementType.NAME == step.getType() || ElementType.CSS_SELECTOR == step.getType())
		{
			processSelectorStep(driver, step);
		}
	}
	
	private void processSelectorStep(WebDriver driver, Activity step) throws InterruptedException
	{
		WebElement webElement = findWebElement(driver, step);
		if( Action.CLICK == step.getAction() )
		{
			webElement.click();
		}
		if(Action.SEND_TEXT == step.getAction())
		{
			webElement.sendKeys(step.getTextValue());
		}
		
		if(Action.SELECT_BY_VALUE == step.getAction())
		{
			Select select = new Select(webElement);
			//select.deselectAll();
			select.selectByVisibleText(step.getTextValue());
		}
	}

	private boolean goHome(WebDriver driver, Url url) throws InterruptedException {
		driver.get(url.getHomepageUrl());
		Thread.sleep(1000);
		return true;
	}

	private boolean isAlreadyLoggedIn(WebDriver driver, Url url) {
		Element loggedInCheckElement = url.getLoggedInCheckElement();

		if (null != loggedInCheckElement && Action.EXISTS == loggedInCheckElement.getAction()) {

			WebElement webElement = findWebElement(driver, loggedInCheckElement);
			return (null != webElement);
		}

		return false;
	}

	private boolean isLogoutExists(WebDriver driver, Login login) {
		Element logoutCheckElement = login.getLogoutCheckElement();

		if (null != logoutCheckElement && Action.EXISTS == logoutCheckElement.getAction()) {

			WebElement webElement = findWebElement(driver, logoutCheckElement);
			return (null != webElement);

		}
		return false;
	}

	private void login(WebDriver driver, Login login) throws InterruptedException {
		String loginUrl = login.getLoginUrl();
		String username = login.getUsername();
		String password = login.getPassword();
		Element usernameElement = login.getUsernameElement();
		Element passwordElement = login.getPasswordElement();
		Element submitElement = login.getSubmitElement();

		driver.get(loginUrl);
		Thread.sleep(5000);

		WebElement usernameWebElement = findWebElement(driver, usernameElement);
		WebElement passwordWebElement = findWebElement(driver, passwordElement);
		WebElement submitWebElement = findWebElement(driver, submitElement);

		usernameWebElement.sendKeys(username);
		passwordWebElement.sendKeys(password);

		if (Action.CLICK == submitElement.getAction()) {
			submitWebElement.click();
		}
		if (Action.SUBMIT == submitElement.getAction()) {
			submitWebElement.submit();
		}
		Thread.sleep(1000);
	}

	// -- *helper
	private WebElement findWebElement(WebDriver driver, Element searchElement) {
		WebElement webElement = null;
		if (ElementType.ID == searchElement.getType()) {
			try {
				webElement = driver.findElement(By.id(searchElement.getKey()));
			} catch (NoSuchElementException e) {
				return null;
			}

		}
		if (ElementType.NAME == searchElement.getType()) {
			try {
				webElement = driver.findElement(By.name(searchElement.getKey()));
			} catch (NoSuchElementException e) {
				return null;
			}
		}

		if (ElementType.CLASS == searchElement.getType()) {
			try {
				webElement = driver.findElement(By.className(searchElement.getKey()));
			} catch (NoSuchElementException e) {
				return null;
			}
		}
		
		if (ElementType.CSS_SELECTOR == searchElement.getType()) {
			try {
				webElement = driver.findElement(By.cssSelector(searchElement.getKey()));
			} catch (NoSuchElementException e) {
				return null;
			}
		}
		return webElement;
	}
	

	private WebElement findWebElement(WebDriver driver, Activity step) {
		Element searchElement = new Element(step.getType(), step.getKey(), step.getAction());
		return findWebElement(driver, searchElement);
	}
	

	private WebElement findWebElement(WebDriver driver, ResponseActivity step) {
		Element searchElement = new Element(step.getType(), step.getKey(), step.getAction());
		return findWebElement(driver, searchElement);
	}
	
	// -- *helper -- end
}
