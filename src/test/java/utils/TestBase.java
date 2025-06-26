package utils;

import java.io.FileInputStream;
import java.io.FileReader;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import org.json.simple.parser.ParseException;
import java.util.List;
import java.util.Map;
//import java.util.Map.Entry;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; //Log4j
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
// **convert to static because of new BaseTest() in public void onTestFailure
	public static WebDriver driver;
	public static Actions action;
	public static final Logger logger = LogManager.getLogger(TestBase.class);

	public HandleToastMessage handleToastMessage;
	public JavascriptExecutor javascriptExecutor;
	public Properties propertiesFile;

	public WebDriver getDriver() {
		return driver;
	}

	public WebDriver initializeDriver() throws IOException {
		if (driver == null) {
			FileInputStream fileReader = new FileInputStream("src/test/resources/global.properties");
			propertiesFile = new Properties();
			propertiesFile.load(fileReader);

			String getBrowser = propertiesFile.getProperty("browser").toLowerCase();

			switch (getBrowser) {
			case "chrome":
				WebDriverManager.chromedriver().setup();

				Map<String, Object> chromePrefs = new HashMap<>();
				chromePrefs.put("profile.default_content_setting_values.media_stream_mic", 1); // Allow mic
				chromePrefs.put("profile.default_content_setting_values.media_stream_camera", 1); // Allow camera
				chromePrefs.put("profile.default_content_setting_values.notifications", 1); // Allow notifications

				ChromeOptions chromeOptions = new ChromeOptions();
				
				chromeOptions.addArguments("--disable-notifications");
				chromeOptions.addArguments("--use-fake-ui-for-media-stream");		//Auto-allow mic
				chromeOptions.addArguments("--use-fake-device-for-media-stream");	//Fake mic input
				 
				chromeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
				driver = new ChromeDriver(chromeOptions);
				break;

			case "edge":
				WebDriverManager.edgedriver().setup();
				EdgeOptions edgeOptions = new EdgeOptions();
				edgeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
				driver = new EdgeDriver(edgeOptions);
				break;

			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				FirefoxOptions fireOptions = new FirefoxOptions();
				fireOptions.addPreference("dom.webdriver.enabled", false);
				fireOptions.addPreference("useAutomationExtension", false);
				driver = new FirefoxDriver(fireOptions);
				break;

			default:
				throw new IllegalArgumentException("Invalid browser name: " + getBrowser);
			}

			driver.get(propertiesFile.getProperty("Astroyogi_Demo_URL"));
			driver.manage().window().maximize();

			Logger logger = LogManager.getLogger(this.getClass());
			logger.info("\n<========================================= NEW EXECUTION STARTED =========================================>");
			logger.info("Driver initialized and application launched in {}", getBrowser);
		}
		
		return driver;
	}
	
	
	public static void quitDriver() {
		try {
			driver.quit();
			logger.info("Driver quit successfully in @After hook.");
			logger.info("\n<============================================ EXECUTION ENDED ============================================>\n\n");
		} catch (Exception e) {
			logger.error("Failed to quit WebDriver in @After hook: {}", e.getMessage(), e);
		}
	}


	public static void executionDelay(int seconds) {
		try {
			Thread.sleep(seconds * 1000L);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // restore interrupt status
			logger.warn("Static wait interrupted", e);
		}
	}

//------------------------WebDriverWait Methods(Not Use)------------------------->>
	public WebElement waitForElementToBeVisible(WebElement element, int seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
			return wait.until(ExpectedConditions.visibilityOf(element));
		} catch (TimeoutException e) {
			logger.info("Element not visible within {} seconds.", seconds);
		} catch (NoSuchElementException e) {
			logger.info("Element not found in the DOM.");
		} catch (StaleElementReferenceException e) {
			logger.info("Element is stale (detached from the DOM).");
		} catch (Exception e) {
			logger.error("Unexpected error while waiting for element visibility: {}", e.getMessage(), e);
		}
		return null;
	}
//------------------------WebDriverWait Methods(Not Use)------------------------->>

// Checks if the element is visible within the specified seconds
	public boolean isElementVisible(WebElement element, int seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
			wait.until(ExpectedConditions.visibilityOf(element));
			return element.isDisplayed();
		} catch (TimeoutException e) {
			logger.info("Timeout: Element not visible within {} seconds => {}", seconds, element);
		} catch (NoSuchElementException e) {
			logger.info("Timeout: Element not visible within {} seconds => {}", seconds, element);
		} catch (StaleElementReferenceException e) {
			logger.info("Element is stale (not attached to DOM anymore)  => {}", element);
		}
		return false;
	}

	public static boolean checkElementToBeClickable(WebElement webelement) {
		boolean flag;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
		try {
			@SuppressWarnings("unused")
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(webelement));
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

//	public WebElement waitForElementToBeClickable(WebElement webelement, int seconds) {
//		WebElement element = null;
//		try {
//			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
//			element = wait.until(ExpectedConditions.elementToBeClickable(webelement));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return element;
//	}

	public WebElement waitForElementToBeClickable(WebElement element, int seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
			WebElement clickableElement = wait.until(ExpectedConditions.elementToBeClickable(element));
			// logger.info("Element is clickable: " + element);
			return clickableElement;
		} catch (TimeoutException e) {
			logger.error("WebElement not clickable within {} seconds => {}", seconds , element);
			throw new RuntimeException("Timeout: Element not clickable within " + seconds + " seconds and xpath is => " + element);
		} catch (Exception e) {
			logger.error("Unexpected error while waiting for element to be clickable => {}" + element);
			throw new RuntimeException("Unexpected error while waiting for element: " + element);
		}
	}

	public WebElement waitForElementToBeClickable(By locator, int timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
		logger.info("Waiting for element to be clickable: " + locator);
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

// Reads data from a JSON file 
	public String readDataFromJson(String filePath, String sectionKey, String objectKey, String nestedKey) {
		String value = "";
		try {
			JSONParser parser = new JSONParser();
			FileReader reader = new FileReader(filePath);
			JSONObject rootObject = (JSONObject) parser.parse(reader);

			// Get JSONArray from the section
			JSONArray sectionArray = (JSONArray) rootObject.get(sectionKey);

			if (sectionArray != null) {
				for (Object obj : sectionArray) {
					JSONObject jsonObject = (JSONObject) obj;

					if (jsonObject.containsKey(objectKey)) {
						Object nestedObject = jsonObject.get(objectKey);

						if (nestedObject instanceof JSONObject) {
							JSONObject nestedJson = (JSONObject) nestedObject;
							if (nestedJson.containsKey(nestedKey)) {
								value = nestedJson.get(nestedKey).toString();
								return value;
							}
						}
					} else if (jsonObject.containsKey(nestedKey)) {
						// If it's a direct key inside the object (not nested)
						value = jsonObject.get(nestedKey).toString();
						return value;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

// Reads a nested list of strings from a JSON file 
	public List<String> readNestedListFromJson(String filePath, String sectionKey, String mainKey, String typeKey) {
		List<String> values = new ArrayList<>();
		try {
			JSONParser parser = new JSONParser();
			FileReader reader = new FileReader(filePath);
			JSONObject rootObject = (JSONObject) parser.parse(reader);
			JSONArray sectionArray = (JSONArray) rootObject.get(sectionKey);

			if (sectionArray != null) {
				for (Object obj : sectionArray) {
					JSONObject jsonObject = (JSONObject) obj;
					if (jsonObject.containsKey(mainKey)) {
						JSONObject toastTypeObj = (JSONObject) jsonObject.get(mainKey);
						if (toastTypeObj.containsKey(typeKey)) {
							JSONArray messagesArray = (JSONArray) toastTypeObj.get(typeKey);
							for (Object message : messagesArray) {
								values.add(message.toString());
							}
							break; // since you found the array, stop looping
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}

//-----------------------------Verify Broken Links and Images----------------------------->>	
	public void VerifyBrokenLinks(List<WebElement> linkTag, String linkAdd) {
		List<WebElement> linkList = linkTag;
		System.out.println("Total links on Page = " + linkList.size());
		logger.info("Total links on the page: " + linkList.size());

		int resCode = 0;
		int brokenLinkCount = 0;
		String resMessage = "";
		for (WebElement link : linkList) {
			String Url = link.getDomAttribute(linkAdd);

			try {
				URL urlLink = URI.create(Url).toURL();
				URLConnection urlconnection = urlLink.openConnection();
				HttpURLConnection huc = (HttpURLConnection) urlconnection;
				huc.setConnectTimeout(5000);
				huc.connect();

				resCode = huc.getResponseCode();
				resMessage = huc.getResponseMessage();

				if (resCode != 200 && resCode != 429) {
					System.err.println(Url + " = " + resCode + "-" + resMessage);
					brokenLinkCount++;
				}
			} catch (MalformedURLException e) {
				System.out.println(Url + "- Anchor tag is available but href is not available");
			} catch (Exception ex) {
				System.out.println(Url + "- unknown Host");
			}
		}
		System.err.println("Broken links = " + brokenLinkCount);
	}

	public void VerifyBrokenImage(List<WebElement> imgTag, String imgAdd) {
		List<WebElement> imgList = imgTag;
		System.out.println("Total images on page - " + imgList.size());

		int brokenImgCount = 0;
		int resCode = 200;
		String resMessage = "";
		for (WebElement image : imgList) {
			String imgSrc = image.getDomAttribute(imgAdd);

			try {
				URL url = new URI(imgSrc).toURL();
				URLConnection urlconnection = url.openConnection();
				HttpURLConnection huc = (HttpURLConnection) urlconnection;
				huc.setConnectTimeout(5000);
				huc.connect();

				resCode = huc.getResponseCode();
				resMessage = huc.getResponseMessage();

				if (resCode != 200 && resCode != 429) {
					System.err.println(imgSrc + "-" + resCode + "-" + resMessage);
					brokenImgCount++;
				}
			} catch (MalformedURLException e) {
				System.out.println(imgSrc + "- Anchor tag is available but href is not available");
			} catch (Exception ex) {
				System.out.println(imgSrc + "- unknown host");
			}
		}
		System.err.println("Broken image count = " + brokenImgCount);
	}
//-----------------------------Verify Broken Links and Images----------------------------->>	

//------------------------JavaScript Executor Methods----------------------------->>
	public static void navigateBack() {
		try {
			driver.navigate().back();
			logger.info("Navigated back successfully.");
		} catch (Exception e) {
			logger.error("Failed to navigate back: " + e.getMessage(), e);
		}
	}

	public static void scrollByPixelSmooth(int totalPixels) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			int scrolled = 0;
			int step = 100; // pixels per step
			int delay = 50; // milliseconds between steps

			while (Math.abs(scrolled) < Math.abs(totalPixels)) {
				int remaining = totalPixels - scrolled;
				int scrollAmount = Math.abs(remaining) < step ? remaining : (totalPixels > 0 ? step : -step);
				js.executeScript("window.scrollBy(0, arguments[0]);", scrollAmount);
				scrolled += scrollAmount;
				Thread.sleep(delay);
			}

			logger.info("Smooth scrolled vertically by " + totalPixels + " pixels.");
		} catch (Exception e) {
			logger.error("scrollByPixelSmooth failed: " + e.getMessage());
		}
	}

	// Scrolls to bring a WebElement into view (centered)
	public static void scrollToElement(WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
			logger.info("Scrolled to element: " + element.toString());
		} catch (Exception e) {
			logger.error("scrollToElement failed: " + e.getMessage());
		}
	}
//------------------------JavaScript Executor Methods----------------------------->>

}
