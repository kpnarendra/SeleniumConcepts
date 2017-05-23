package testSuit;


import static org.testng.Assert.assertEquals;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import generalFunctions.commonFunctions;
import generalFunctions.loggerClass;



public class testCases extends commonFunctions {

	commonFunctions commonControl = new commonFunctions();

	
	@Test (alwaysRun = true ,description = "Open Google home page in the selected browser")
	public void openGoogle(){
		
		driver = commonControl.invokeBrowser();
		
		loggerClass.writeFile.info(driver);
		driver.get("http://www.google.com");
	}
	
	@Test (dependsOnMethods = "openGoogle" , description = "check the page title on browser")
	public void recordPageTitle(){
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		loggerClass.writeFile.info("Page title is : "+driver.getTitle());
		loggerClass.writeFile.info("Page handler is : "+driver.getWindowHandle());
		loggerClass.writeFile.info("Page current URL is : "+driver.getCurrentUrl());
		loggerClass.writeFile.info("Page Capeabilities is : " + driver.manage().window());
		//System.out.println("Page page Source is : "+driver.getPageSource());
		//WebDriverWait wait = new WebDriverWait(driver, 10);

		
		assertEquals(driver.getTitle(), "Google");
	}
	@AfterClass (alwaysRun = true)
	public void afterMethod()
	{
		try{
		driver.close();
		driver.quit();
		driver=null;
		}
		catch (Exception e)
		{
			loggerClass.writeFile.error("Failed at @AfterClass in Class : "+ this.getClass() + " with Message " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		loggerClass.writeFile.info("END OF TEST EXECUTION");
		
	}


	
	
	
}
