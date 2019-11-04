Feature: Critical Business Journeys

	Background: User landed on homepage
		Given User is on / Page
		
	Scenario: Create Account in STP buy a ticket SSO to NAM Ticket and Invoice available
		Given User creates stp account
		And User buys a GA ticket with Payment Plan using %{GD_CardType} type card with card number %{GD_CardNumber}, CVV %{GD_CVV}, Expiry %{GD_ExpiryDate}
		Then Thank you page is displayed
		Given Invoice Id is generated
		And User navigates to NAM from STP
		And User navigates to "/invoice#/%{GD_STP_INVOICE_ID}" from NAM
		Then User logged in successfully
		And Pending invoice found on NAM with id %{GD_STP_INVOICE_ID} and amount due %{GD_STP_INVOICE_AMT_DUE}
		Given User navigates to /tickets from NAM
		When Events page is displayed
		Then Verify events summary for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Given User navigates to "/tickets#/%{ENV_event_id}" from NAM
		When Tickets page is displayed
		Then Verify tickets count based on status

	Scenario: Reclaim ticket after Email ID change in CAM
		Given User creates account using ats with events
		And User Generate ticket ID for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		And Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		And Send Ticket using %{GD_TransferTicketID}
		And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then User logged in successfully
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_AccountName} and %{GD_AccountId}
		When manage profile with %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then NAM homepage is displayed
		Given User navigates to /user/logout from NAM
		When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then User logged in successfully
		Given User navigates to "/tickets#/%{GD_Event_Id}" from NAM	
		When User clicks on manage tickets using %{GD_TransferTicketID}
		And User clicks on Reclaim
		Then Verify success screen
		When User click on done
		Then Verify ticket status - No Status for ticketId %{GD_TransferTicketID}
		And Verify ticket flags for %{GD_TransferTicketID}, %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		
	Scenario: Claim ticket after Email ID change in CAM
  	Given User is on / Page
    And User creates account using ats with events
		And User Generate ticket ID for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		And Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		And Send Ticket using %{GD_TransferTicketID}
		And User generate TransferId for %{GD_TransferTicketID} 		
		And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then User logged in successfully
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_AccountName} and %{GD_AccountId}
		When manage profile with %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then NAM homepage is displayed
		Given User navigates to "/ticket/claim?%{GD_TransferID}" from NAM
		And Save %{GD_NEW_EMAIL_ADDRESS} into EMAIL_ADDRESS
		And Save %{GD_NEW_PASSWORD} into PASSWORD
		When User verify congratulation message	
		Then User creates account 
		And User click on Claim link for ticketId %{GD_TransferTicketID} and %{GD_TransferID} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		And Verify ticket status - No Status for ticketId %{GD_TransferTicketID}
		And Verify ticket flags for %{GD_TransferTicketID}, %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}	
		Given Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Given Save %{GD_AccountName} into AccountName
		When User Logout and login again using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		Then Verify User claimed the ticket using %{GD_TransferTicketID} and %{GD_AccountName}
		
	Scenario: Send ticket after Email ID change in CAM
    Given User creates account using ats with events
    And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    And User is on / Page
		When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then User logged in successfully
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_AccountName} and %{GD_AccountId}
		When manage profile with %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then NAM homepage is displayed
		Given User navigates to /user/logout from NAM
		When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then User logged in successfully
		Given User Generate ticket ID for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    And Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		And User navigates to "/tickets#/%{GD_Event_Id}" from NAM	
		When User clicks on Send tickets
		And User selects the seat using %{GD_TransferTicketID}
		And User clicks on continue
		Then Verify Event Details using %{GD_TransferTicketID}
		When User clicks on continue
		And User saves claimLink
    And User enters transfer Name 
    And User click on Transfer button
    Then Verify ticket listing page display
    And Save the state of ticket for %{GD_TransferTicketID}
    When User Logout and login again using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    Then Save the Status of ticket using %{GD_TransferTicketID}
    Given Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    Then Verify the status of Send ticket %{GD_Status}
    And Verify the state of the ticket %{GD_State}
		And Verify ticket flags for %{GD_TransferTicketID}, %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		And User deletes transfer using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD} for ClaimLink %{GD_ClaimLink}
		
	Scenario: Sell ticket after Email ID change in CAM
  	Given User creates account using ats with events
		And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Given User is on / Page
		When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then User logged in successfully
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_AccountName} and %{GD_AccountId}
		When manage profile with %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then NAM homepage is displayed
		Given User navigates to /user/logout from NAM
		When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then User logged in successfully
		Given User gets Resale details of ticket for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		And Save Event Id for ticket Id %{GD_ResaleTicketID}
		And User navigates to "/tickets#/%{GD_Resale_Event_Id}" from NAM	
		When User clicks on Sell tickets
		And User selects the seat using %{GD_ResaleTicketID}
		And User clicks on continue
		Then User enters Earning price
		When User clicks on continue
		Then User selects Seller Credit
		And Verify User edit Seller profile for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		When User clicks on continue
		Then Verify The Event Name
		When User clicks on continue
		Then Verify ticket listing page display
		And Save the state of ticket for %{GD_ResaleTicketID}
		When User Logout and login again using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then Save the Status of ticket using %{GD_ResaleTicketID}
		Given Save ticket flags for ticket Id %{GD_ResaleTicketID} using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then Verify the status of Sell ticket %{GD_Status}
    And Verify the state of the ticket %{GD_State}
		And Verify ticket flags for %{GD_ResaleTicketID}, %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		When User gets Posting ID using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD} for %{GD_ResaleTicketID}
		Then User deletes postingId using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD} for %{GD_PostingId}
			
	Scenario: Edit Posting Seller Credit after Email ID change in CAM
    Given User creates account using ats with events
    And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    And User gets Resale details of ticket for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    And User sells ticket using API for %{GD_ResaleTicketID}
    And Save Event Id for ticket Id %{GD_ResaleTicketID}
    And User is on / Page
		When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    Then User logged in successfully
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_AccountName} and %{GD_AccountId}
		When manage profile with %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then NAM homepage is displayed
		Given User navigates to /user/logout from NAM
		When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then User logged in successfully
    Given User navigates to "/tickets#/%{GD_Resale_Event_Id}" from NAM
    When User clicks on Edit or Cancel ticket for %{GD_ResaleTicketID}
    Then User clicks on Edit Posting
    And User enters Earning price
		When User clicks on continue
		Then User selects Seller Credit
		And Verify User edit Seller profile for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    When User clicks on continue
		Then Verify The Event Name
		When User clicks on continue
		Then Verify ticket listing page display
		And Save State for ticketId %{GD_ResaleTicketID} using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		When User Logout and login again using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then Save the Status of ticket using %{GD_ResaleTicketID}
		Then Verify the status of Sell ticket %{GD_Status}
    And Verify the state of the ticket %{GD_State}
		When User clicks on Edit or Cancel ticket for %{GD_ResaleTicketID}
    Then User clicks on Edit Posting
    When User gets Posting ID using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD} for %{GD_ResaleTicketID}
		Then User deletes postingId using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD} for %{GD_PostingId} 
	    
	Scenario: Edit Posting Bank Account after Email ID change in CAM
    Given User creates account using ats with events
    And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    And User gets Resale details of ticket for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    And User sells ticket using API for %{GD_ResaleTicketID}
    And Save Event Id for ticket Id %{GD_ResaleTicketID}
    And User is on / Page
		When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    Then User logged in successfully
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_AccountName} and %{GD_AccountId}
		When manage profile with %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then NAM homepage is displayed
		Given User navigates to /user/logout from NAM
		When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then User logged in successfully
    Given User navigates to "/tickets#/%{GD_Resale_Event_Id}" from NAM
    When User clicks on Edit or Cancel ticket for %{GD_ResaleTicketID}
    Then User clicks on Edit Posting
    And User enters Earning price
		When User clicks on continue
		Then User select Bank Account
		And Verify User edit Bank Account profile for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    When User clicks on continue
		Then Verify The Event Name
		When User clicks on continue
		Then Verify ticket listing page display
		And Save State for ticketId %{GD_ResaleTicketID} using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		When User Logout and login again using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then Save the Status of ticket using %{GD_ResaleTicketID}
		Then Verify the status of Sell ticket %{GD_Status}
    And Verify the state of the ticket %{GD_State}
		When User clicks on Edit or Cancel ticket for %{GD_ResaleTicketID}
    Then User clicks on Edit Posting
    When User gets Posting ID using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD} for %{GD_ResaleTicketID}
		Then User deletes postingId using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD} for %{GD_PostingId}
		  
	Scenario: Cancel Posting after Email ID change in CAM
    Given User creates account using ats with events
    And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    And User gets Resale details of ticket for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    And Save ticket flags for ticket Id %{GD_ResaleTicketID} using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    And User sells ticket using API for %{GD_ResaleTicketID}
    And Save Event Id for ticket Id %{GD_ResaleTicketID}
    And User is on / Page
		When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    Then User logged in successfully
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_AccountName} and %{GD_AccountId}
		When manage profile with %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then NAM homepage is displayed
		Given User navigates to /user/logout from NAM
		When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then User logged in successfully
    Given User navigates to "/tickets#/%{GD_Resale_Event_Id}" from NAM
    When User clicks on Edit or Cancel ticket for %{GD_ResaleTicketID}
    Then User cancels posting
    And Verify The Event Name
    When user click on confirm button
    Then Verify success screen
    When User click on done
    Then Verify ticket listing page display
    And Verify pending action get removed for %{GD_ResaleTicketID} using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		When User Logout and login again using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then Verify ticket status - No Status for ticketId %{GD_ResaleTicketID}
		And Verify ticket flags for %{GD_ResaleTicketID}, %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		When User gets Posting ID using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD} for %{GD_ResaleTicketID}
		Then User deletes postingId using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD} for %{GD_PostingId}
		
   Scenario: Render Barcode after Email ID Change in CAM
    Given User is enabled on mobile
    And User creates account using ats with events
    And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    And User is on / Page
		When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then User logged in successfully
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_AccountName} and %{GD_AccountId}
		When manage profile with %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then NAM homepage is displayed
		Given User navigates to /user/logout from NAM
		When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then User logged in successfully
		Given User fetches Render details for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		And Save Render Event Id for %{GD_RenderTicketID}
    And Save ticket flags for ticket Id %{GD_RenderTicketID} using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		And User navigates to "/tickets#/%{GD_Event_Id}" from NAM
    When Verify user clicks on Scan Barcode for %{GD_RenderTicketID}
    Then User click on render Barcode using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD} for %{GD_RenderTicketID}
    And Verify Barcode gets display
    
	Scenario: A new user logs-in to NAM, SSO to CAM and buys a ticket, navigates back to NAM, new Ticket and Invoice should be visible
		Then User creates account
		Given Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		And User navigates to /classic-amgr?redirect_url=buy/browse from NAM
		Then Classic AMGR is displayed with correct %{GD_AccountName} and %{GD_AccountId}
		When User buys a ticket with Payment Plan using %{GD_CardType} type card with card number %{GD_CardNumber}, CVV %{GD_CVV}, Expiry %{GD_ExpiryDate}
		Given User navigates to / from NAM
		Then SSO done successfully to NAM with correct %{GD_AccountName} and %{GD_AccountId}
		Given User navigates to "/invoice#/%{GD_INVOICE_NUMBER}/1" from NAM
		Then Pending invoice found on NAM with id %{GD_INVOICE_NUMBER} and amount due %{GD_AMT_DUE}
		Given User navigates to /tickets from NAM
		When Events page is displayed
		Then Verify events summary for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Given User clicked on event %{GD_EVENT_NAME}
		When Tickets page is displayed
		Then Verify event %{GD_EVENT_ID} ticket - %{GD_SEAT_DETAILS} is displayed
		Given User navigates to / from NAM
		Then User logged in successfully
		And Verify events details on dashboard for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Given User navigates to /classic-amgr?redirect_url=buy/browse from NAM
		Then Classic AMGR is displayed with correct %{GD_AccountName} and %{GD_AccountId}
		When User buys a ticket with Full Payment using %{GD_CardType} type card with card number %{GD_CardNumber}, CVV %{GD_CVV}, Expiry %{GD_ExpiryDate}
		Given User navigates to / from NAM
		Then SSO done successfully to NAM with correct %{GD_AccountName} and %{GD_AccountId}
		Given User navigates to /tickets from NAM
		When Events page is displayed
		Then Verify events summary for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Given User clicked on event %{GD_EVENT_NAME}
		When Tickets page is displayed
		Then Verify event %{GD_EVENT_ID} ticket - %{GD_SEAT_DETAILS} is displayed
		Given User navigates to / from NAM
		Then User logged in successfully
		And Verify events details on dashboard for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		
	Scenario: An existing user logs-in to NAM, SSO to CAM and buys a ticket, navigates back to NAM, latest Ticket and Invoice should be visible in NAM
		When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		Then User logged in successfully
		Given Customer details are fetched for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		And User navigates to /classic-amgr?redirect_url=buy/browse from NAM
		Then Classic AMGR is displayed with correct %{GD_AccountName} and %{GD_AccountId}
		When User buys a ticket with Payment Plan using %{GD_CardType} type card with card number %{GD_CardNumber}, CVV %{GD_CVV}, Expiry %{GD_ExpiryDate}
		Given User navigates to / from NAM
		Then SSO done successfully to NAM with correct %{GD_AccountName} and %{GD_AccountId}
		Given User navigates to "/invoice#/%{GD_INVOICE_NUMBER}/1" from NAM
		Then Pending invoice found on NAM with id %{GD_INVOICE_NUMBER} and amount due %{GD_AMT_DUE}
		Given User navigates to /tickets from NAM
		When Events page is displayed
		Then Verify events summary for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		Given User clicked on event %{GD_EVENT_NAME}
		When Tickets page is displayed
		Then Verify event %{GD_EVENT_ID} ticket - %{GD_SEAT_DETAILS} is displayed
		Given User navigates to / from NAM
		Then User logged in successfully
		And Verify events details on dashboard for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		Given User navigates to /classic-amgr?redirect_url=buy/browse from NAM
		Then Classic AMGR is displayed with correct %{GD_AccountName} and %{GD_AccountId}
		When User buys a ticket with Full Payment using %{GD_CardType} type card with card number %{GD_CardNumber}, CVV %{GD_CVV}, Expiry %{GD_ExpiryDate}
		Given User navigates to / from NAM
		Then SSO done successfully to NAM with correct %{GD_AccountName} and %{GD_AccountId}
		Given User navigates to /tickets from NAM
		When Events page is displayed
		Then Verify events summary for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		Given User clicked on event %{GD_EVENT_NAME}
		When Tickets page is displayed
		Then Verify event %{GD_EVENT_ID} ticket - %{GD_SEAT_DETAILS} is displayed
		Given User navigates to / from NAM
		Then User logged in successfully
		And Verify events details on dashboard for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}