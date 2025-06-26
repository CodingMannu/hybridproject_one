package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.MyAccountObject;
import pageObjects.LoginObject;
import utils.TestContextSetup;

public class MyAccountStepDefs {

	TestContextSetup testContextSetup;
	MyAccountObject myAccountObject;
	LoginObject loginObject;

	public MyAccountStepDefs(TestContextSetup testContextSetup) {
		this.testContextSetup = testContextSetup;
		this.myAccountObject = testContextSetup.pageObjectManager.getFilter();
	}
	
	
	// TC_001
	@When("User clicks on menu button")
	public void user_clicks_on_menu_button() {
		myAccountObject.user_clicks_on_menu_button();
	}

	@When("User clicks on my account option")
	public void user_clicks_on_my_account_option() {
		myAccountObject.user_clicks_on_my_account_option();
	}

	@Then("User clicks all filters")
	public void user_clicks_all_filters() {
		myAccountObject.user_clicks_all_filters();
	}

	@Then("User clicks on clear filter")
	public void user_clicks_on_clear_filter() {
		myAccountObject.user_clicks_on_clear_filter();
	}

	@Then("User clicks on logout option")
	public void user_clicks_on_logout_option() {
		myAccountObject.user_clicks_on_logout_option();
	}


}
