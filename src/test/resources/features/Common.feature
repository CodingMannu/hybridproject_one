@CommonMethod
Feature: Common Flow Reuse

Background: 
	Given User is logged in with "Manoj"

@CM_001
	Scenario: Click on nav manu options
	When User clicks on "Home"
	When User clicks on "Consult Now"
	#When User clicks on "Horoscope"
	#When User clicks on "Year 2025"
	#When User clicks on "Panchang"
	#When User clicks on "Kundli"
	#When User clicks on "Numerology"
	#When User clicks on "Tarot"
	#When User clicks on "Free Readings"
	#When User clicks on "Yogi Live"
	#When User clicks on "Video"
	#When User clicks on "Blog"
	#When User clicks on "Session Booking"
	#When User clicks on "Yogi Mall"
	
@CM_002
  Scenario: Redirection to home page by click logo
  When User clicks on the home page logo 
  Then User should be redirected to the home page
  And Home page title should be "Get Accurate and Free Astrology Predictions Today - Astroyogi"
	

@CM_003
	Scenario: Navigate to astrologer profile
  When User clicks on "Consult Now"
  And User searches astrologer "Astro Manoj"  
	And User selects the astrologer from search results "Astro Manoj"
	Then Astrologer profile open


@CM_004
	Scenario: Fill ther Share Your Details form
 	When User fill the share your details form

 @CM_005
 Scenario: Start consultation after fill the form
  When Consultation started after validate current balance and max duration is shown
  Then Validate one last step popup appear

  
  
  
  
  