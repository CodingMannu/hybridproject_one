package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.TestBase;

public class MyAccountObject extends TestBase {

	public WebDriver driver;

	public MyAccountObject(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	// ------------------------------------------------------------------------------->feature
	// file
	// TC_001

	@FindBy(id = "myAccount")
	private WebElement user_clicks_on_menu_button;

	public void user_clicks_on_menu_button() {
		try {
			waitForElementToBeClickable(user_clicks_on_menu_button, 5).click();
			logger.info("Clicked on the menu (profile) button successfully.");
		} catch (Exception e) {
			logger.error("Failed to click on the menu (profile) button: " + e.getMessage());
		}
	}

	@FindBy(xpath = "//button[contains(text(), 'My Account')]")
	private WebElement user_clicks_on_my_account_option;

	public void user_clicks_on_my_account_option() {
		executionDelay(3);
		waitForElementToBeClickable(user_clicks_on_my_account_option, 5).click();
	}

	@FindBy(xpath = "//div[contains(@class, 'filter_box_ul')]//li[contains(@class, 'ng-star-inserted')]")
	private List<WebElement> allFilters;

	public void user_clicks_all_filters() {
		executionDelay(3);
		for (WebElement filter : allFilters) {
			waitForElementToBeClickable(filter, 2).click();
		}
	}

	@FindBy(xpath = "//a[contains(text(),'Clear Filter')]")
	private WebElement user_clicks_on_clear_filter;

	public void user_clicks_on_clear_filter() {
		executionDelay(3);
		waitForElementToBeClickable(user_clicks_on_clear_filter, 5).click();
	}

	@FindBy(xpath = "//button[contains(text(),'Logout')]")
	private WebElement user_clicks_on_logout_option;

	public void user_clicks_on_logout_option() {
		executionDelay(3);
		waitForElementToBeClickable(user_clicks_on_logout_option, 5).click();
	}

}
