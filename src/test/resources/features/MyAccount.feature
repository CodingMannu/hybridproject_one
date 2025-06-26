@MyAccount
Feature: My Account 
  
	@MYACCOUNT_TC_001
	Scenario: Validate all filters in my Account
		When User clicks on menu button
		And User clicks on my account option
		And User clicks all filters
		And User clicks on clear filter
		And User clicks on menu button
		Then User clicks on logout option
