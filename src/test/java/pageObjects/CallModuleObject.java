package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.TestBase;

public class CallModuleObject extends TestBase {
	public WebDriver driver;

	public CallModuleObject(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div//button[@class='profile_gray_btn'][2][contains(.,'Offline')]")
	private WebElement check_offline_cta_on_call;

	@FindBy(xpath = "//button[contains(.,'Call')]")
	private WebElement click_call_cta_on_profile;

	public void click_on_call_button_if_astrologer_is_not_offline() {
		try {
			if (isElementVisible(check_offline_cta_on_call, 3)) {
				String offlineMsg = check_offline_cta_on_call.getText();
				logger.info("Astrologer is offline on call" + offlineMsg);
				if (offlineMsg.contains("Offline")) {
					logger.info("Astrologer is offline. Cannot proceed with call.");
//					throw new RuntimeException("Astrologer is offline. Cannot proceed with call.");
				} else {
					logger.info("Astrologer is online. Proceeding to click Call CTA.");
				}
			} else if (isElementVisible(click_call_cta_on_profile, 3)) {
				waitForElementToBeClickable(click_call_cta_on_profile, 2).click();
				logger.info("Clicked on Call CTA. Proceeding with call initiation.");
			} else {
				logger.warn("Neither offline message nor call CTA is visible.");
				throw new RuntimeException(
						"Neither offline message nor call CTA is visible. Cannot proceed with Call.");
			}
		} catch (Exception e) {
			logger.error("Error while checking call status or clicking CTA.", e);
			throw new RuntimeException("Failed to handle astrologer call CTA.");
		}
	}

	@FindBy(xpath = "//button[@class = 'call-astrologer-button' and contains(text(),'Call')]")
	private WebElement click_call_cta_for_initiate_call;

	public void click_call_cta_for_initiate_call() {
		if (isElementVisible(click_call_cta_for_initiate_call, 3)) {
			waitForElementToBeClickable(click_call_cta_for_initiate_call, 2).click();
			logger.info("Clicked on Call Now CTA to initiate call.");
		} else {
			logger.warn("Call Now CTA is not visible. Cannot initiate call.");
			throw new RuntimeException("Call Now CTA is not visible. Cannot initiate call.");
		}

	}

	@FindBy(xpath = "//h1[contains(text(),'Thank You!')]")
	private WebElement thank_you_message_after_call_initiated;

	@FindBy(xpath = "//h3[contains(text(),'NOW REDIAL WITHIN 60 SECONDS')]")
	private WebElement check_redial_text_available;

	public void thanks_you_page_open_after_call_initiated() {
		if (isElementVisible(thank_you_message_after_call_initiated, 5)) {
			String thankYouMsg = thank_you_message_after_call_initiated.getText();
			logger.info("Thank You message after call: " + thankYouMsg);
			if (isElementVisible(check_redial_text_available, 5)) {
				String redialText = check_redial_text_available.getText();
				logger.info("Redial text available: " + redialText);
				executionDelay(10);
			} else {
				logger.warn("Redial text is not visible.");
				throw new RuntimeException("Redial text is not visible.");
			}
		} else {
			logger.warn("Thank You message is not visible after call.");
			throw new RuntimeException("Thank You message is not visible after call.");
		}
	}

}
