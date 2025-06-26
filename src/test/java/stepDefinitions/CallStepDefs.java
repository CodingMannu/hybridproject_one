package stepDefinitions;

import io.cucumber.java.en.Then;
import pageObjects.CallModuleObject;
import utils.TestContextSetup;

public class CallStepDefs {

	TestContextSetup testContextSetup;
	CallModuleObject callModuleObject;

	public CallStepDefs(TestContextSetup testContextSetup) {
		this.testContextSetup = testContextSetup;
		this.callModuleObject = testContextSetup.pageObjectManager.getCallModuleObject();
	}
	
	@Then("User clicks on call button if astrologer is not offline")
	public void user_clicks_on_call_button_if_astrologer_is_not_offline() {
		callModuleObject.click_on_call_button_if_astrologer_is_not_offline();
	}
	
	@Then("User clicks on call cta for initiate the call")
	public void user_clicks_on_call_cta_for_initiate_the_call() {
		callModuleObject.click_call_cta_for_initiate_call();
	}
	
	@Then("Thanks you page open after call initiated")
	public void thanks_you_page_open_after_call_initiated() {
		callModuleObject.thanks_you_page_open_after_call_initiated();
	}
	
	

}
