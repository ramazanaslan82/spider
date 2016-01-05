package spider;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class App {

	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		testMulti();
	}

	private static void testOne() throws InterruptedException {
		System.out.println("Basladi");
		
        System.setProperty("webdriver.chrome.driver", "/path/to/chrome/driver/chromedriver");

		WebDriver driver = new ChromeDriver();
		driver.get("https://secure.bir.site.adresi/login/");
		Thread.sleep(1000);

		WebElement username = driver.findElement(By.id("usernameIdDegeri"));
		username.sendKeys("testusername");
		WebElement password = driver.findElement(By.id("passwordIdDegeri"));
		password.sendKeys("test");
		WebElement submitButton = driver.findElement(By.id("submitButonIdDegeri"));
		submitButton.click();

		Thread.sleep(1000);

		System.out.println(driver.getPageSource());
		driver.quit();
		System.out.println("Bitti");
	}

	private static void testMulti() throws InterruptedException, BrokenBarrierException {

		// thread count plus one
		final CyclicBarrier barrier = new CyclicBarrier(6);
		Runnable runActivation = new Runnable() {

			public void run() {
				try {
					barrier.await();
					testOne();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						barrier.await();
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}

			}
		};

		new Thread(runActivation).start();
		new Thread(runActivation).start();
		new Thread(runActivation).start();
		new Thread(runActivation).start();
		new Thread(runActivation).start();

		// for starting at same time
		barrier.await();

		// for ending at same time
		barrier.await();

	}
}
