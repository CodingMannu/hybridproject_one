package utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

	private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);

	public static String captureScreen(WebDriver driver, String screenshotName) throws IOException {
		String timeStamp = new SimpleDateFormat("dd-MMM-YY hh-mm-ss a").format(new Date());
		String targetFilePath = System.getProperty("user.dir") + "/test-output-result/screenshots/";

		File directory = new File(targetFilePath);
		if (!directory.exists()) {
			directory.mkdirs(); // create folder if not exists
		}

		String targetFilePathName = targetFilePath + timeStamp + "_" + screenshotName + ".png";
		File targetFile = new File(targetFilePathName);

		try {
			TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
			File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(sourceFile, targetFile); // more reliable than renameTo
			System.out.println("Failure Screenshot saved to: " + targetFilePathName);
			logger.info("Failure Screenshot saved at: " + targetFilePathName);
		} catch (IOException e) {
			System.err.println("Failed to save screenshot: " + e.getMessage());
			logger.error("Failed to save screenshot: ", e);
		}
		return targetFilePathName;
	}
}
