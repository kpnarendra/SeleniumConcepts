package testSuit;

import static org.testng.Assert.assertEquals;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import generalFunctions.commonFunctions;

public class testCases extends commonFunctions {

	commonFunctions commonControl = new commonFunctions();
	public static final Logger writeLogFile = LogManager.getLogger(testCases.class);

	@Test(alwaysRun = true, description = "Open Google home page in the selected browser")
	public void openGoogle() {
		try {
			writeLogFile.info("Start of Test method openGoogle");
			driver = commonControl.invokeBrowser();
			writeLogFile.info(driver);
			driver.get("http://www.google.com");
			writeLogFile.info("End of Test method openGoogle");
		} catch (Exception e) {
			writeLogFile.error("Failed in test method openGoogle with message :" + e.getMessage() + e);
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Test(dependsOnMethods = "openGoogle", description = "check the page title on browser")
	public void recordPageTitle() {
		writeLogFile.info("Start of Test method recordPageTitle");
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			writeLogFile.info("Page title is : " + driver.getTitle());
			writeLogFile.info("Page handler is : " + driver.getWindowHandle());
			writeLogFile.info("Page current URL is : " + driver.getCurrentUrl());
			writeLogFile.info("Page Capeabilities is : " + driver.manage().window());
		} catch (Exception e) {
			writeLogFile.error("Failed in test method recordPageTitle with message " + e.getMessage() + e);
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
