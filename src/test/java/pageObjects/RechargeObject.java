package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.TestBase;
import utils.TestDataPaths;

public class RechargeObject extends TestBase {

	public WebDriver driver;

	public RechargeObject(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "LnkHomeBanner")
	private WebElement the_logged_in_user_is_on_the_home_screen_validate_by_home_banner;

	@FindBy(id = "wallet")
	private WebElement the_user_clicks_on_the_wallet_icon;

	public void the_user_clicks_on_the_wallet_icon() {
//		executionDelay(2);
		waitForElementToBeClickable(the_user_clicks_on_the_wallet_icon, 10).click();
	}

	public void validate_wallet_page_title() {
		executionDelay(2);
		String expectedTitle = "Astroyogi Recharge Pack";
		String actualTitle = driver.getTitle();
		if (expectedTitle.equals(actualTitle)) {
			logger.info(actualTitle + ": Page title is validated successfully.");
		} else {
			logger.error("Page title validation failed. Expected: " + expectedTitle + ", Actual: " + actualTitle);
		}
	}

	@FindBy(xpath = "//button[@id='myAccount']//button[contains(text(), 'Buy Minutes')]")
	private WebElement the_user_clicks_on_buy_minutes_option_from_menu;

	public void the_user_clicks_on_buy_minutes_option_from_menu() {
		waitForElementToBeClickable(the_user_clicks_on_buy_minutes_option_from_menu, 5).click();
		logger.info("Clicked on buy minutes.");
	}

	@FindBy(xpath = "//div[contains(.,'Wallet Balance')]/span")
	private WebElement the_user_checks_the_current_wallet_balance;

	public void the_user_checks_the_current_wallet_balance() {
		executionDelay(1);
		String balanceText = the_user_checks_the_current_wallet_balance.getText().replaceAll("[^0-9.]", "").trim();
		double userBalance = Double.parseDouble(balanceText);
		logger.info("User current wallet balance: ₹" + userBalance);

	}

//Dynamic pack select from Recharge.feature file
	public void the_user_selects_a_recharge_pack(String amountKey) {
		String packName = readDataFromJson(TestDataPaths.RECHARGE_PATH, "Recharge", "rechargePacks", amountKey);
		System.out.println("*Recharge Pack Selected: " + amountKey + " - " + packName);
		String xpath = String.format("//div[contains(@class,'recharge_amount') and normalize-space(text())='₹ %s']",
				amountKey);
		WebElement rechargePack = waitForElementToBeClickable(By.xpath(xpath), 10);
		rechargePack.click();
	}

	@FindBy(xpath = "//div[@class='paypal_mobile']//button")
	private WebElement the_user_clicks_on_the_proceed_to_pay_button;

	public void the_user_clicks_on_the_proceed_to_pay_button() {
//		executionDelay(2);
		waitForElementToBeClickable(the_user_clicks_on_the_proceed_to_pay_button, 10).click();
	}

//After clicks on Proceed to Pay and user gets share details form
	@FindBy(xpath = "//div[@id='cdk-overlay-2']//div[@class='birth-detail']//span")
	private WebElement opens_share_detail_from;

	@FindBy(xpath = "//div[@id='cdk-overlay-2']//form//mat-label[contains(.,'Full Name')]")
	private WebElement click_enter_user_full_name;

	@FindBy(xpath = "//div[@id='cdk-overlay-2']//form//input[@placeholder='Enter Your Name']")
	private WebElement enter_user_full_name;

	@FindBy(xpath = "//div[@id='cdk-overlay-2']//form//mat-label[contains(.,'Email ID')]")
	private WebElement click_enter_user_email_id;

	@FindBy(xpath = "//div[@id='cdk-overlay-2']//form//input[@placeholder='Enter Email ID']")
	private WebElement enter_user_email_id;

	@FindBy(xpath = "//form//div[@class='gender-flex']//label[contains(.,'Male')]")
	private WebElement user_selects_male_gender;

	@FindBy(xpath = "//form//div[@class='gender-flex']//label[contains(.,'Female')]")
	private WebElement user_selects_female_gender;

	@FindBy(xpath = "//div[@id='cdk-overlay-2']//button[contains(.,'Submit')]")
	private WebElement user_clicks_submit_button;

	public void the_user_fills_the_share_details_form_if_it_is_displayed(String gender) {
//		executionDelay(2);
		try {
			String expectedText = "So that we can share them with your astrologer while you connect them for consultation";

			if (isElementVisible(opens_share_detail_from, 3)) {
				String actualText = opens_share_detail_from.getText();

				if (expectedText.equals(actualText)) {
//					System.out.println("--Share Details popup is visible. Filling the form...");

					waitForElementToBeClickable(click_enter_user_full_name, 10).click();
//					waitForElementToBeClickable(enter_user_full_name, 10).sendKeys(name);
					enter_user_full_name
							.sendKeys(readDataFromJson(TestDataPaths.LOGIN_PATH, "Login", "name", "fullName"));

					waitForElementToBeClickable(click_enter_user_email_id, 2).click();
//					waitForElementToBeClickable(enter_user_email_id, 2).sendKeys(email);
					enter_user_email_id
							.sendKeys(readDataFromJson(TestDataPaths.LOGIN_PATH, "Login", "validEmail", "email"));

					if (gender.equalsIgnoreCase("Male")) {
						waitForElementToBeClickable(user_selects_male_gender, 3).click();
					} else if (gender.equalsIgnoreCase("Female")) {
						waitForElementToBeClickable(user_selects_female_gender, 3).click();
					}

					waitForElementToBeClickable(user_clicks_submit_button, 3).click();
				} else {
					System.out.println("--Text mismatch. Popup may be incorrect.");
				}
			} else {
//				System.out.println("--Share Details popup not visible. Skipping form fill.");
			}
		} catch (Exception e) {
			System.out.println("--Exception in filling Share Details: " + e.getMessage());
		}
	}

//After click on Proceed to Pay and user gets Saved Cards Option
	@FindBy(xpath = "//div[@id='content']//article[contains(.,'Saved Cards')]")
	private WebElement available_saved_cards_options;

	@FindBy(xpath = "//div[@id='content']//img[@alt='new card plus']")
	private WebElement the_user_clicks_on_add_new_card;

	public void the_user_clicks_on_add_new_card_if_saved_cards_are_displayed() {
		// executionDelay(2);
		try {
			if (isElementVisible(available_saved_cards_options, 3)) {
				waitForElementToBeClickable(the_user_clicks_on_add_new_card, 2).click();
				// executionDelay(1);
			} else {
//				System.out.println("--Add New Card button not visible, skipping...");
			}
		} catch (Exception e) {
			System.out.println("--Error in clicking Add New Card: " + e.getMessage());
		}
	}

//Enter Card Details	
	@FindBy(xpath = "//div[@id='content']//input[@placeholder='Enter card number here']")
	private WebElement enter_card_number;

	@FindBy(xpath = "//div[@id='content']//input[@placeholder='MM/YY']")
	private WebElement enter_expiry_month_and_year_of_card;

	@FindBy(xpath = "//div[@id='content']//input[@placeholder='CVV']")
	private WebElement enter_card_cvv;

	@FindBy(xpath = "//div[@id='content']//article[contains(.,'PROCEED TO PAY')]")
	private WebElement click_on_proceed_to_pay_button;

	public void the_user_enters_credit_card_credentials(String cardKey) {
		if (isElementVisible(enter_card_number, 5)) {
			waitForElementToBeClickable(enter_card_number, 5).click();
			enter_card_number.sendKeys(readDataFromJson(TestDataPaths.RECHARGE_PATH, "Recharge", cardKey, "number"));

			waitForElementToBeClickable(enter_expiry_month_and_year_of_card, 5).click();
			enter_expiry_month_and_year_of_card
					.sendKeys(readDataFromJson(TestDataPaths.RECHARGE_PATH, "Recharge", cardKey, "monthYear"));

			waitForElementToBeClickable(enter_card_cvv, 5).click();
			enter_card_cvv.sendKeys(readDataFromJson(TestDataPaths.RECHARGE_PATH, "Recharge", cardKey, "cvv"));

			waitForElementToBeClickable(click_on_proceed_to_pay_button, 5).click();
		}
	}

	/*
	 * public void the_user_enters_invalid_credit_card_credentials() { if
	 * (isElementVisible(enter_card_number, 5)) {
	 * 
	 * waitForElementToBeClickable(enter_card_number, 5).click();
	 * enter_card_number.sendKeys( readDataFromJson(TestDataPaths.RECHARGE_PATH,
	 * "Recharge", "invalidCardDetails", "number"));
	 * 
	 * waitForElementToBeClickable(enter_expiry_month_and_year_of_card, 5).click();
	 * enter_expiry_month_and_year_of_card.sendKeys(
	 * readDataFromJson(TestDataPaths.RECHARGE_PATH, "Recharge",
	 * "invalidCardDetails", "monthYear"));
	 * 
	 * waitForElementToBeClickable(enter_card_cvv, 5).click(); enter_card_cvv
	 * .sendKeys(readDataFromJson(TestDataPaths.RECHARGE_PATH, "Recharge",
	 * "invalidCardDetails", "cvv"));
	 * 
	 * waitForElementToBeClickable(click_on_proceed_to_pay_button, 5).click(); } }
	 * 
	 * public void the_user_enters_valid_credit_card_credentials() { if
	 * (isElementVisible(enter_card_number, 5)) {
	 * waitForElementToBeClickable(enter_card_number, 5).click();
	 * enter_card_number.sendKeys( readDataFromJson(TestDataPaths.RECHARGE_PATH,
	 * "Recharge", "krishanValidCardDetails", "number"));
	 * 
	 * waitForElementToBeClickable(enter_expiry_month_and_year_of_card, 5).click();
	 * enter_expiry_month_and_year_of_card.sendKeys(
	 * readDataFromJson(TestDataPaths.RECHARGE_PATH, "Recharge",
	 * "krishanValidCardDetails", "monthYear"));
	 * 
	 * waitForElementToBeClickable(enter_card_cvv, 5).click();
	 * enter_card_cvv.sendKeys( readDataFromJson(TestDataPaths.RECHARGE_PATH,
	 * "Recharge", "krishanValidCardDetails", "cvv"));
	 * 
	 * waitForElementToBeClickable(click_on_proceed_to_pay_button, 5).click(); } }
	 */

//if secure your card pop-up appears
	@FindBy(xpath = "//div[@testid='msg_sub_header']//article")
	private WebElement secure_your_card_popup_appear_or_not;

	@FindBy(xpath = "//div[@testid='msg_text']//article[contains(.,'Opt out for now')]")
	private WebElement user_clicks_on_otp_send_for_now;

	public void the_user_clicks_on_send_otp_for_now() {
		String expectedText = "As per RBI guidelines, an additional layer of security is required to save your card";
		isElementVisible(secure_your_card_popup_appear_or_not, 5);
		String actualText = secure_your_card_popup_appear_or_not.getText();
		if (expectedText.equals(actualText)) {
			waitForElementToBeClickable(user_clicks_on_otp_send_for_now, 5).click();
		}
	}

//if select a currency to pay page open
	@FindBy(xpath = "//div[@class='payment-containerDcc']//p")
	private WebElement is_select_a_currency_to_pay_appears;

	@FindBy(xpath = "//div[@class='currency-options']//div")
	private List<WebElement> list_of_currency_options;

	@FindBy(xpath = "//div[@class='payment-containerDcc']//p[contains(.,'Paying in INR')]")
	private WebElement indian_currency_is_selected;

	@FindBy(xpath = "//div[@class='button-container']//button")
	private WebElement user_clicks_on_pay_button;

//if Address verification pop-up appear after select a currency to pay
	@FindBy(xpath = "//h4[@class='form-title otpTitle mb-1']//span")
	private WebElement is_address_verification_page_or_not;

	@FindBy(xpath = "//form[@class='form-gp']//input[@id='firstName']")
	private WebElement enter_user_first_name;

	@FindBy(xpath = "//form[@class='form-gp']//input[@id='lastName']")
	private WebElement enter_user_last_name;

	@FindBy(xpath = "//form[@class='form-gp']//input[@id='email']")
	private WebElement enter_user_email_id_on_currency_select_page;

	@FindBy(xpath = "//form[@class='form-gp']//input[@id='address1']")
	private WebElement enter_user_address;

	@FindBy(xpath = "//form[@class='form-gp']//input[@id='country']")
	private WebElement enter_user_country;

	@FindBy(xpath = "//form[@class='form-gp']//input[@id='city']")
	private WebElement enter_user_city;

	@FindBy(xpath = "//form[@class='form-gp']//input[@id='pincode']")
	private WebElement enter_user_pincode;

	@FindBy(xpath = "//form[@class='form-gp']//input[@id='state']")
	private WebElement enter_user_state;

	@FindBy(xpath = "//form[@class='form-gp']//button//div")
	private WebElement user_clicks_pay_button;

//if payment failed pop-up appear before astroyogi fail page appear(NOT USING)
	@FindBy(xpath = "")
	private WebElement is_payment_failed_page_appears;

	@FindBy(xpath = "//div[@class='modal-content']//button")
	private WebElement user_clicks_cross_button_after_failed_transaction;

	@FindBy(xpath = "")
	private WebElement user_clicks_okay_button_to_cancel_payment_page;

	public void the_user_selects_currency_if_page_is_displayed() {

		String expectedText = "Select a currency to pay in";
		if (isElementVisible(is_select_a_currency_to_pay_appears, 5)) {
			String actualText = is_select_a_currency_to_pay_appears.getText();
			if (expectedText.equals(actualText)) {
//				System.out.println("Currency selection page is visible. Looping through currencies...");

				for (WebElement currency : list_of_currency_options) {
					try {
						waitForElementToBeClickable(currency, 3).click();
						executionDelay(1); // Allow UI to update

						// Check if "Paying in INR" is now visible
						if (isElementVisible(indian_currency_is_selected, 2)) {
//							System.out.println("INR currency selected. Proceeding to pay...");
							waitForElementToBeClickable(user_clicks_on_pay_button, 3).click();

							// Address verification page
							String expectedTextOnAddressPage = "Address Verification";
							if (isElementVisible(is_address_verification_page_or_not, 5)) {
								String actualTextOnAddressPageString = is_address_verification_page_or_not.getText();
								if (expectedTextOnAddressPage.equals(actualTextOnAddressPageString)) {
									waitForElementToBeClickable(enter_user_first_name, 10).click();
									enter_user_first_name.sendKeys(
											readDataFromJson(TestDataPaths.LOGIN_PATH, "Login", "name", "firstName"));

									waitForElementToBeClickable(enter_user_last_name, 3).click();
									enter_user_last_name.sendKeys(
											readDataFromJson(TestDataPaths.LOGIN_PATH, "Login", "name", "lastName"));

									waitForElementToBeClickable(enter_user_email_id_on_currency_select_page, 3).click();
									enter_user_email_id_on_currency_select_page.sendKeys(
											readDataFromJson(TestDataPaths.LOGIN_PATH, "Login", "validEmail", "email"));

									waitForElementToBeClickable(enter_user_address, 3).click();
									enter_user_address.sendKeys(readDataFromJson(TestDataPaths.LOGIN_PATH, "Login",
											"houseAddress", "address"));

									waitForElementToBeClickable(enter_user_country, 3).click();
									enter_user_country.sendKeys(readDataFromJson(TestDataPaths.LOGIN_PATH, "Login",
											"houseAddress", "country"));

									waitForElementToBeClickable(enter_user_city, 3).click();
									enter_user_city.sendKeys(readDataFromJson(TestDataPaths.LOGIN_PATH, "Login",
											"houseAddress", "city"));

									waitForElementToBeClickable(enter_user_pincode, 3).click();
									enter_user_pincode.sendKeys(readDataFromJson(TestDataPaths.LOGIN_PATH, "Login",
											"houseAddress", "pincode"));

									waitForElementToBeClickable(enter_user_state, 3).click();
									enter_user_state.sendKeys(readDataFromJson(TestDataPaths.LOGIN_PATH, "Login",
											"houseAddress", "state"));

									waitForElementToBeClickable(user_clicks_pay_button, 3).click();
								} else {
									System.out.println("Address verification text not matched.");
								}
							} else {
								System.out.println("Address verification page not visible.");
							}
							break;

						} else {
							System.out.println("This is not INR. Trying next currency.");
						}
					} catch (Exception e) {
						System.out.println("Failed to click/select a currency: " + e.getMessage());
					}
				}

			} else {
				System.out.println("Currency selection text doesn't match. Skipping.");
			}
		} else

		{
//			System.out.println("Currency selection container not visible. Skipping.");
		}
	}

	@FindBy(xpath = "//div[@id='divPaymentFail']/h2")
	private WebElement open_payment_failure_page;

	public void the_user_should_be_redirected_to_the_payment_failure_page() {
		String expectedText = "Payment Failed!";
		isElementVisible(open_payment_failure_page, 30);
		String actualText = open_payment_failure_page.getText();

		if (expectedText.equals(actualText)) {
//			System.out.println("--Redirected to the Payment Failure Page.");
		} else {
//			System.out.println("--Not redirected to the Payment Failure Page. Found: " + actualText);
		}
	}

//Using in TC_002

	@FindBy(xpath = "//div[@class='offer_applied' and contains(.,'Offer applied successfully')]")
	private WebElement is_offer_applied_successefully_visible_or_not;

	@FindBy(xpath = "//div[@class='recharge_cashback_amount_recharge']//button")
	private WebElement user_clicks_on_close_icon_to_remove_applied_offer;

	@FindBy(id = "txcoupon")
	private WebElement user_enters_coupon_in_input_coupon;

	public void the_user_enters_a_valid_coupon_code_in_the_coupon_section() {
		if (isElementVisible(is_offer_applied_successefully_visible_or_not, 5)) {
			waitForElementToBeClickable(user_clicks_on_close_icon_to_remove_applied_offer, 5).click();
		}
		waitForElementToBeClickable(user_enters_coupon_in_input_coupon, 5).click();
		user_enters_coupon_in_input_coupon
				.sendKeys(readDataFromJson(TestDataPaths.RECHARGE_PATH, "Recharge", "coupon", "demoCoupon"));
	}

	@FindBy(id = "btnaply")
	private WebElement user_clicks_to_apply_the_coupon;

	public void the_user_clicks_on_the_coupon_apply_button() {
		executionDelay(2);
		waitForElementToBeClickable(user_clicks_to_apply_the_coupon, 5).click();
		;
	}

	@FindBy(xpath = "(//div[@testid='nvb_upi']//article[contains(.,'UPI')])[2]")
	private WebElement user_clicks_upi_section;

	public void the_user_selects_the_upi_payment_option() {
		WebElement element = waitForElementToBeClickable(user_clicks_upi_section, 5);
		if (element != null) {
			element.click();
		} else {
			System.out.println("UPI option not found or not clickable.");
		}
	}

	@FindBy(xpath = "//div[@testid='con_upicollect']//input")
	private WebElement user_enters_valid_upi_id;

	public void enter_upi_details_by_key(String upiKey) {
		waitForElementToBeClickable(user_enters_valid_upi_id, 5).click();
		user_enters_valid_upi_id.sendKeys(upiKey);

		/*
		 * Enable this and hide above line if you want to pick data from feature file
		 * String upiValue = readDataFromJson(TestDataPaths.RECHARGE_PATH, "Recharge",
		 * "upiId", upiKey); user_enters_valid_upi_id.sendKeys(upiValue);
		 */
	}

	@FindBy(xpath = "//div[@testid='btn_pay']//article")
	private WebElement the_user_clicks_on_the_verify_and_pay_button;

	public void the_user_clicks_on_the_verify_and_pay_button() {
		waitForElementToBeClickable(the_user_clicks_on_the_verify_and_pay_button, 5).click();
	}

	@FindBy(xpath = "//div[@id='divpaymentSuccess']/h2")
	private WebElement recharge_successful_page;

	public void the_user_should_be_redirected_to_the_payment_success_page() {
		String expectedText = "Recharge successful !";
		isElementVisible(recharge_successful_page, 30);
		String actualText = recharge_successful_page.getText();
		if (expectedText.equals(actualText)) {
//			System.out.println("--Redirected to the Payment success Page.");
		} else {
//			System.out.println("--Not redirected to the Payment success Page. Found: " + actualText);
		}
	}

	@FindBy(xpath = "//div[@testid='con_savedpaymentoptions']//article[contains(.,'Saved Cards')]")
	private WebElement saved_cards_page_appear_or_not;

	@FindBy(xpath = "//div[@testid='con_savedpaymentoptions']//img[@testid='img_right']")
	private List<WebElement> list_of_saved_card_options;

	@FindBy(xpath = "//div[@testid='msg_second_line']//article[contains(.,'XXXX-XXXXXXXX-1651')]")
	private WebElement credit_card_is_selected;

	@FindBy(xpath = "//input[@testid='edt_cvv']")
	private WebElement user_enters_cvv_of_this_card;

	@FindBy(xpath = "//div[@testid='btn_pay']//div[@testid='msg_text']")
	private WebElement user_clicks_on_proceed_to_pay_button_on_saved_card_page;

	public void the_user_selects_saved_credit_card() {
		if (isElementVisible(saved_cards_page_appear_or_not, 5)) {

			if (isElementVisible(credit_card_is_selected, 5)) {
				waitForElementToBeClickable(credit_card_is_selected, 3).click();

				if (isElementVisible(user_enters_cvv_of_this_card, 5)) {
					user_enters_cvv_of_this_card.sendKeys(
							readDataFromJson(TestDataPaths.RECHARGE_PATH, "Recharge", "invalidCardDetails", "cvv"));
					waitForElementToBeClickable(user_clicks_on_proceed_to_pay_button_on_saved_card_page, 5).click();
					return;
				}
			}
		}

	}

// Case 1: IDFC OTP Page
	@FindBy(id = "otpValue") // krishan card input id = otpPin-input
	private WebElement user_enters_receive_otp_on_idfc;

	@FindBy(id = "submitBtn") // krishan card proceed button id = submitBtn3
	private WebElement user_clicks_on_submit_button_on_idfc;

// Case 2: Razorpay OTP Page
	@FindBy(xpath = "//form[@name='otpForm']//input")
	private WebElement user_enters_receive_otp_on_razorpay;

	@FindBy(id = "submit-action")
	private WebElement user_clicks_on_submit_button__on_razorpay;

	public void the_user_enters_receive_otp_on_mobile_number() {
		try {
			// Case 1: IDFC OTP Page
			if (isElementVisible(user_enters_receive_otp_on_idfc, 5)) {
				waitForElementToBeClickable(user_enters_receive_otp_on_idfc, 3).click();
				executionDelay(10); // Let user enter OTP manually
				waitForElementToBeClickable(user_clicks_on_submit_button_on_idfc, 3).click();
				return;
			}
			// Case 2: Razorpay OTP Page
			else if (isElementVisible(user_enters_receive_otp_on_razorpay, 5)) {
				waitForElementToBeClickable(user_enters_receive_otp_on_razorpay, 3).click();
				executionDelay(10); // user enters manual otp for payment
				waitForElementToBeClickable(user_clicks_on_submit_button__on_razorpay, 3).click();
				return;
			} else {
				System.out.println("No known OTP screen found.");
			}
		} catch (Exception e) {
			System.out.println("Error while handling OTP screen: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void the_user_clicks_on_back_arrow_of_chrome_browser() {
		TestBase.navigateBack();
	}

}
