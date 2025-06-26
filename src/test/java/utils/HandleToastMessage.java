package utils;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class HandleToastMessage extends TestBase {
	
	public HandleToastMessage(WebDriver driver) {
		TestBase.driver = driver;
        PageFactory.initElements(driver, this);
	}
	

	@FindBy(xpath = "//div[contains(@class,'snackbar_main')]")
	public WebElement toastMessageContainer;

	public void validateToastMessageByKeyAndType(String toastKey, String typeKey) {

		List<String> expectedToastMessages = readNestedListFromJson(TestDataPaths.GLOBAL_PATH, "Global", toastKey,
				typeKey);
		
		if (isElementVisible(toastMessageContainer, 10)) {
			String toastMessage = toastMessageContainer.getText().trim();
			logger.info("Toast message: " + toastMessage);

			Assert.assertTrue(expectedToastMessages.contains(toastMessage),
					"Toast message does not match any expected message. Actual: " + toastMessage);

		} else {
			logger.error("Toast message container not visible.");
			Assert.fail("Toast message container not visible.");
		}
	}

	@FindBy(xpath = "//button[contains(@class,'snackbar_close_button')]//img")
	private WebElement toastCloseButton;

	public void closeToastMessage() {
		if (isElementVisible(toastCloseButton, 5)) {
			waitForElementToBeClickable(toastCloseButton, 2).click();
			logger.info("Toast message closed successfully.");
		} else {
			logger.warn("Toast close button not visible or already closed.");
		}
	}

}
