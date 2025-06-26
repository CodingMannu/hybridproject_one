package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.CommonObject;
import utils.TestContextSetup;

public class CommonStepDefs {

	TestContextSetup testContextSetup;
	CommonObject commonObject;

	public CommonStepDefs(TestContextSetup testContextSetup) {
		this.testContextSetup = testContextSetup;
		this.commonObject = testContextSetup.pageObjectManager.getCommonObject();
	}

	@When("User clicks on {string}")
	public void userClicksOnNavOption(String optionName) {
		commonObject.clickNavOption(optionName);
	}

	@When("User clicks on the home page logo")
	public void user_clicks_on_the_home_page_logo() {
		commonObject.user_clicks_on_the_home_page_logo();
	}

	@Then("User should be redirected to the home page")
	public void user_should_be_redirected_to_the_home_page() {
		commonObject.user_should_be_redirected_to_the_home_page();
	}

	@Then("Home page title should be {string}")
	public void home_page_title_should_be(String homePageTitle) {
		commonObject.home_page_title_should_be(homePageTitle);
	}

	@When("User searches astrologer {string}")
	public void userSearchesAstrologerByName(String astrologerName) {
		commonObject.enterAstrologerName(astrologerName);
	}

	@When("User selects the astrologer from search results {string}")
	public void userSelectsAstrologerFromSearchResults(String astrologerName) {
		commonObject.selectAstrologerFromSearchResults(astrologerName);
	}

	@Then("Astrologer profile open")
	public void verifyAstrologerProfileIsOpen() {
		commonObject.verifyAstrologerProfileIsOpen();
	}

	@Then("User fill the share your details form")
	public void userFillShareYourDetailsForm() {
		commonObject.waitForShareYourDetailsPopup();
	}

	@When("Consultation started after validate current balance and max duration is shown")
	public void user_current_balance_and_maximum_duration_should_be_displayed() {
		commonObject.verifyConsultationStarted();
	}
	
	@Then("Validate one last step popup appear")
	public void validate_one_last_step_popup_appear() {
		commonObject.validateOneLastStepPopupAppear();
	}

}
