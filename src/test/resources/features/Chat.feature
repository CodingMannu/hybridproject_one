@ChatModule
Feature: Chat functionality

Background:
   Given User is logged in with "Manoj"
   Given User clicks on "Consult Now"
   Given User searches astrologer "Anchal Sahu"  
	 Given User selects the astrologer from search results "Anchal Sahu"
   Given Astrologer profile open
   
@CHAT_TC_001
Scenario: Initiate the chat from astrologer profile
	When User clicks on chat button if astrologer is not offline
	And User fill the share your details form
	And Consultation started after validate current balance and max duration is shown
	#And User clicks on chat cta for initiate the chat
	#Then Validate one last step popup appear
	
	
	
@CHAT_TC_002
Scenario: User accepts the chat after accepted from astrologer side
	When User clicks on chat button if astrologer is not offline
	And User fill the share your details form
	And Consultation started after validate current balance and max duration is shown
	And User clicks on chat cta for initiate the chat
	Then Validate one last step popup appear
	And User accepts the chat after accepted by astrologer
	Then Validate the chating page is visible
	And User sends the text ">> Hy Sir"
	And User sends the text ">> User sends the text"
	And User sends the recording after record
	And User sends the text ">> Automation completed for chat"
	And User clicks on end button to end the chat
	Then Validate thank you popup after end the chat
	

@CHAT_TC_003
Scenario: User rejects the chat after accepted from astrologer side
	Then User clicks on chat button if astrologer is not offline
	And User rejects the chat after accepted by astrologer
	