package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.HomePageObject;
import utils.TestContextSetup;

public class HomePageStepDefs {

	TestContextSetup testContextSetup;
	HomePageObject homePageObject;

	public HomePageStepDefs(TestContextSetup testContextSetup) {
		this.testContextSetup = testContextSetup;
		this.homePageObject = testContextSetup.pageObjectManager.getHomePage();
	}

}
