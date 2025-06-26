package cucumberoptions;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { "src/test/resources/features" },

		glue = { "stepDefinitions", "appHooks" }, dryRun = false, monochrome = true, plugin = { "pretty",
				"json:target/jsonReports/cucumber-report.json",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"rerun:target/failed_scenarios.txt", "html:target/HtmlReports/report.html", }, 
//		tags = "@TC_021"
		tags = "@TC_021"
//		tags = "@TC_021 or @WRPassed_002"
//		tags = "@TC_021 or @WRPassed_003"
//		tags = "@TC_021 or @WRPassed_005"
)

public class FirefoxRunner extends AbstractTestNGCucumberTests {
	@Override
	@DataProvider(parallel = false)
	public Object[][] scenarios() {
		return super.scenarios();
	}
}