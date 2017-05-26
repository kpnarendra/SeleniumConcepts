//----------------------------------------------------------------------------------------------------------------------------------------------------
//File			: generalFunctions.commonFunctions.class
//Date			: 15 May 2017
//Owner   		: Narendra KP
//Purpose 		: To hold all the selenium general functions
//----------------------------------------------------------------------------------------------------------------------------------------------------

package generalFunctions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class commonFunctions {

	// ----------------------------------------------------------------------------------------------------------------------------------------------------
	// Function name : fetchProperties
	// Purpose : Read data from data.properties file. Return type - VALUES for
	// any KEYS sent as parameter.
	// ----------------------------------------------------------------------------------------------------------------------------------------------------
	public WebDriver driver;
	public static final Logger writeLogFile = LogManager.getLogger(commonFunctions.class);

	private String fetchProperties(String key) {
		writeLogFile.info("Start of function fetchProperties for key = " + key);
		File file = new File(System.getProperty("user.dir") + "/src/main/resources/data.properties");
		FileInputStream fileInput;
		Properties prop = new Properties();
		try {
			fileInput = new FileInputStream(file);
			prop.load(fileInput);
			fileInput.close();
		} catch (IOException e) {
			e.printStackTrace();
			writeLogFile.error(file + " is not found or may have readability issues" + e);
			System.exit(1); // For Jenkins
		}
		writeLogFile.info("Returned : " + "'" + prop.getProperty(key) + "'" + " for Key : " + key);
		writeLogFile.info("End of function fetchProperties for key = " + key);
		return prop.getProperty(key);

	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------------
	// Function name : Invoke Browser
	// Purpose : Invoke any browser as defined in data.properties file.
	// ----------------------------------------------------------------------------------------------------------------------------------------------------
	public WebDriver invokeBrowser() {
		writeLogFile.info("Start of function invokeBrowser");
		String browserTypeChoice = fetchProperties("BROWSERTYPE");
		System.out.println("browserTypeChoice = " + browserTypeChoice);

		if (browserTypeChoice.equals("IE")) {
			invokeIEDriver();
		} else if (browserTypeChoice.equals("CHROME")) {
			invokeChromeDriver();
		} else if (browserTypeChoice.equals("FIREFOX")) {
			invokeFireFoxDriver();
		} else {
			writeLogFile.error("ERROR : Invalid browser type selected in data.properties");
			System.exit(1); // For Jenkins
		}
		
		writeLogFile.info("End of function invokeBrowser");
		return driver;

	}

	public void invokeIEDriver() {
		writeLogFile.info("Start of function invokeIEDriver");
		String proxyChoice = fetchProperties("PROXY");
		System.setProperty("webdriver.ie.driver",
				System.getProperty("user.dir") + "\\IEDriverServer\\IEDriverServer.exe");
		if (proxyChoice.equalsIgnoreCase("yes")) {

		} else if (proxyChoice.equalsIgnoreCase("no")) {
			//DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			//capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			//capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);

			driver = new InternetExplorerDriver();

			//writeLogFile.info("Set following capeabilities for IE: " + capabilities
			//		+ " and INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS status = "
			//		+ capabilities.getCapability("INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS"));
		} else {
			writeLogFile.error("ERROR : Invalid PROXY configured in data.properties");
			System.exit(1);
		}
		driver.manage().window().maximize();
		writeLogFile.info("End of function invokeIEDriver");
	}

	public void invokeChromeDriver() {
		writeLogFile.info("Start of function invokeChromeDriver");
		String proxyChoice = fetchProperties("PROXY");
		if (proxyChoice.equalsIgnoreCase("yes")) {

		} else if (proxyChoice.equalsIgnoreCase("no")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\chromedriver\\chromedriver.exe");
			driver = new ChromeDriver();
		} else {
			writeLogFile.error("ERROR : Invalid PROXY configured in data.properties");
			System.exit(1);
		}
		driver.manage().window().maximize();
		writeLogFile.info("End of function invokeChromeDriver");
	}

	public void invokeFireFoxDriver() {
		writeLogFile.info("Start of function invokeFireFoxDriver");
		String proxyChoice = fetchProperties("PROXY");
		if (proxyChoice.equalsIgnoreCase("yes")) {

		} else if (proxyChoice.equalsIgnoreCase("no")) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "\\GeckoDriver\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else {
			writeLogFile.error("ERROR : Invalid PROXY configured in data.properties");
			System.exit(1);
		}
		driver.manage().window().maximize();
		writeLogFile.info("End of function invokeFireFoxDriver");
	}

}
