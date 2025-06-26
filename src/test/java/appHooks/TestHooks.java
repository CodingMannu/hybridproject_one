package appHooks;

import java.io.IOException;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger; //Log4j
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;

import io.cucumber.java.AfterAll;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.EmailSender;
import utils.ScreenshotUtil;
import utils.TestBase;
import utils.TestContextSetup;

public class TestHooks extends TestBase {
	public static final boolean SCREENSHOT_ENABLED = false;

	TestContextSetup testContextSetup;

	public TestHooks(TestContextSetup testContextSetup) {
		this.testContextSetup = testContextSetup;
	}

	@Before
	public void beforeScenario() {

	}

	@AfterAll
	public static void tearDown() {
		quitDriver();
	}

	@AfterStep
	public void takeScreenshotOnFailure(Scenario scenario) throws IOException {
		WebDriver driver = testContextSetup.testBase.getDriver();

		if (scenario.isFailed() && SCREENSHOT_ENABLED) {
			String screenshotName = scenario.getName().replaceAll(" ", "_");

			byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshotBytes, "image/png", screenshotName);

			ScreenshotUtil.captureScreen(driver, screenshotName);
		}
	}

	@AfterSuite
	public void sendMailAfterExecution() {
		String latestReport = EmailSender.getLatestReportFilePath();
		if (latestReport != null) {
			EmailSender.sendExtentReportByEmail(latestReport, 
					"mk.manojkumar0706@gmail.com", 		// Receiver Email
					"mannu.10.11.1998@gmail.com",		// Sender Email
					"Pagal123" 							// App Password for Gmail
			);
		}
	}
}