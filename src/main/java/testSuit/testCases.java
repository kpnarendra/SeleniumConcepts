package testSuit;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import generalFunctions.commonFunctions;

public class testCases extends commonFunctions {

	commonFunctions commonControl = new commonFunctions();
	public static final Logger writeLogFile = LogManager.getLogger(testCases.class);

	@Test(alwaysRun = true, description = "testOpenGoogle : Open Google home page in the selected browser")
	public void testOpenGoogle() {
		try {
			writeLogFile.info("Start of Test method testOpenGoogle");
			driver = commonControl.invokeBrowser();
			writeLogFile.info(driver);
			driver.get("http://www.google.com");
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			Thread.sleep(3000);
			writeLogFile.info("End of Test method testOpenGoogle");
		} catch (Exception e) {
			writeLogFile.error("Failed in test method testOpenGoogle with message :" + e.getMessage() + e);
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Test(dependsOnMethods = "testOpenGoogle", description = "testOpenGoogle : check the page title on browser")
	public void testRecordPageTitle() {
		writeLogFile.info("Start of Test method testRecordPageTitle");
		try {
			writeLogFile.info("Page title is : " + driver.getTitle());
			writeLogFile.info("Page handler is : " + driver.getWindowHandle());
			writeLogFile.info("Page current URL is : " + driver.getCurrentUrl());
			writeLogFile.info("Page Capeabilities is : " + driver.manage().window());
			writeLogFile.info("End of try block in Test method testRecordPageTitle");
		} catch (Exception e) {
			writeLogFile.error("Failed in test method testRecordPageTitle with message " + e.getMessage() + e);
			e.printStackTrace();
			System.exit(1);
		}
		// System.out.println("Page page Source is : "+driver.getPageSource());
		// WebDriverWait wait = new WebDriverWait(driver, 10);

		assertEquals(driver.getTitle(), "Google");
		writeLogFile.info("End of Test method recordPageTitle");
	}

	@AfterClass(alwaysRun = true)
	public void afterMethod() {
		writeLogFile.info("Start of method afterMethod");
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			String browserName = (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");
			writeLogFile.info("Browser name : " + browserName);

			if (!(browserName.toLowerCase().contains("firefox"))) {
				writeLogFile.info("Not FireFox browser !!!");
				driver.close();
			}
			driver.quit();
			driver = null;
		} catch (Exception e) {
			writeLogFile.error(
					"Failed at @AfterClass in Class : " + this.getClass() + " with Message " + e.getMessage() + e);
			e.printStackTrace();
			System.exit(1);
		}
		writeLogFile.info("END OF TEST EXECUTION");
		writeLogFile.info("End of method afterMethod");
	}

}
