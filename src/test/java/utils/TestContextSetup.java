package utils;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pageObjects.PageObjectManager;

public class TestContextSetup {
    private static final Logger logger = LogManager.getLogger(TestContextSetup.class);

	public WebDriver driver;
	public PageObjectManager pageObjectManager;
	public TestBase testBase;

	public TestContextSetup() throws IOException {
		testBase = new TestBase();
		driver = testBase.initializeDriver();
        logger.info("Driver initialized successfully from TestContextSetup.");
		pageObjectManager = new PageObjectManager(driver);						
	}
}