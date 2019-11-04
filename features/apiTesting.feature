Feature: APITesting Feature

  Background: User generates access Token
    Given User generates access Token for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    
  Scenario: Create Campaign using privatePurl by createPurlApi
    And Generate Unique Purl Name using %{GD_PURL_NAME}
    When Call master template API and get templateId using %{GD_Token}
    When Generate purl by createpurl API using %{GD_Token}, %{GD_NEW_PURLNAME}, %{GD_PRIVATE_PURL} and %{GD_templateId}
    Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_PRIVATE_PURL}
    Then Call master template API and get EtemplateId using %{GD_Token}
    When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
    When Create campaign by using %{GD_Token}, %{GD_PURLID}, %{GD_CAMPAIGN_NAME}, %{GD_ETEMPLATE_ID}, %{GD_MASTER_TEMPLATE_ID} and %{GD_CUSTOMER_EMAIL}
    Then Verify campaignDetails by campaignById Api using %{GD_Token} and %{GD_CAMPAIGNID}
    When Verify campaign get associated with PurlID using %{GD_Token}, %{GD_PURLID} and %{GD_CAMPAIGNID}
    Then Delete purl which can not be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_MESSAGE} and %{GD_PURL_ERR_CODE} 
    Given User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
    Then Campaign should not be found using %{GD_Token}, %{GD_CAMPAIGNID}, %{GD_ERROR_CODE} and %{GD_ERROR_MESSAGE}
    
  Scenario: Create Campaign using publicPurl by createPurlApi
    When Call master template API and get templateId using %{GD_Token}
    And Generate Unique Purl Name using %{GD_PURL_NAME}
    When Generate purl by createpurl API using %{GD_Token}, %{GD_NEW_PURLNAME}, %{GD_PUBLIC_PURL} and %{GD_templateId}
    Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_PUBLIC_PURL}
    Then Call master template API and get EtemplateId using %{GD_Token}
    When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
    When Create campaign by using %{GD_Token}, %{GD_PURLID}, %{GD_CAMPAIGN_NAME}, %{GD_ETEMPLATE_ID}, %{GD_MASTER_TEMPLATE_ID} and %{GD_CUSTOMER_EMAIL}
    Then Verify campaignDetails by campaignById Api using %{GD_Token} and %{GD_CAMPAIGNID}
    When Verify campaign get associated with PurlID using %{GD_Token}, %{GD_PURLID} and %{GD_CAMPAIGNID}
    Then Delete purl which can not be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_MESSAGE} and %{GD_PURL_ERR_CODE} 
    Given User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
    Then Campaign should not be found using %{GD_Token}, %{GD_CAMPAIGNID}, %{GD_ERROR_CODE} and %{GD_ERROR_MESSAGE}
     
  Scenario: Change PurlId status draft and savepurlApi
    When Call master template API and get templateId using %{GD_Token}
    When Generate draft purl using savepurlAPI %{GD_Token} and %{GD_PURL_NAME}
    Then Verify purlID using %{GD_Token} and %{GD_PURLID}
    Then Verify status of purl as draft using %{GD_Token} and %{GD_PURLID}
    Then Change status of purl as private using %{GD_Token} and %{GD_PURLID}
    Then Verify status of purl as private using %{GD_Token} and %{GD_PURLID}
    Then Change status of purl as draft using %{GD_Token} and %{GD_PURLID}
    Then Verify status of purl as draft using %{GD_Token} and %{GD_PURLID}
    Then Change status of purl as public using %{GD_Token} and %{GD_PURLID}
    Then Verify status of purl as public using %{GD_Token} and %{GD_PURLID}
    Then Change status of purl as draft using %{GD_Token} and %{GD_PURLID}
    Then Verify status of purl as draft using %{GD_Token} and %{GD_PURLID}

  Scenario: Create Campaign using private purl by savePurlApi
    When Call master template API and get templateId using %{GD_Token}
    When Generate draft purl using savepurlAPI %{GD_Token} and %{GD_PURL_NAME} 
    Then Verify purlID using %{GD_Token} and %{GD_PURLID}
    Then Verify status of purl as draft using %{GD_Token} and %{GD_PURLID}
    Then Change status of purl as private using %{GD_Token} and %{GD_PURLID}
    Then Verify status of purl as private using %{GD_Token} and %{GD_PURLID}
    Then Call master template API and get EtemplateId using %{GD_Token}
    When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
    When Create campaign by using %{GD_Token}, %{GD_PURLID}, %{GD_CAMPAIGN_NAME}, %{GD_ETEMPLATE_ID}, %{GD_MASTER_TEMPLATE_ID} and %{GD_CUSTOMER_EMAIL}
    Then Verify campaignDetails by campaignById Api using %{GD_Token} and %{GD_CAMPAIGNID}
    When Verify campaign get associated with PurlID using %{GD_Token}, %{GD_PURLID} and %{GD_CAMPAIGNID}
    Then Delete purl which can not be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_MESSAGE} and %{GD_PURL_ERR_CODE} 
    Given User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
    Then Campaign should not be found using %{GD_Token}, %{GD_CAMPAIGNID}, %{GD_ERROR_CODE} and %{GD_ERROR_MESSAGE}
    
  Scenario: Create Campaign using publicPurl by SavePurlApi
    When Call master template API and get templateId using %{GD_Token}
    When Generate draft purl using savepurlAPI %{GD_Token} and %{GD_PURL_NAME}
    Then Verify purlID using %{GD_Token} and %{GD_PURLID}
    Then Verify status of purl as draft using %{GD_Token} and %{GD_PURLID}
    Then Change status of purl as public using %{GD_Token} and %{GD_PURLID}
    Then Verify status of purl as public using %{GD_Token} and %{GD_PURLID}
    Then Call master template API and get EtemplateId using %{GD_Token}
    When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
    When Create campaign by using %{GD_Token}, %{GD_PURLID}, %{GD_CAMPAIGN_NAME}, %{GD_ETEMPLATE_ID}, %{GD_MASTER_TEMPLATE_ID} and %{GD_CUSTOMER_EMAIL}
    Then Verify campaignDetails by campaignById Api using %{GD_Token} and %{GD_CAMPAIGNID}
    And Verify campaignList should contain campaignId campaignName using %{GD_Token}
    When Verify campaign get associated with PurlID using %{GD_Token}, %{GD_PURLID} and %{GD_CAMPAIGNID}
    Then Delete purl which can not be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_MESSAGE} and %{GD_PURL_ERR_CODE} 
   Given User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
    Then Campaign should not be found using %{GD_Token}, %{GD_CAMPAIGNID}, %{GD_ERROR_CODE} and %{GD_ERROR_MESSAGE}
    And Verify campaignList should not contain campaignId campaignName using %{GD_Token}
    
   Scenario: Change PurlID status public and verify by savepurlApi
     When Call master template API and get templateId using %{GD_Token}
     When Generate draft purl using savepurlAPI %{GD_Token} and %{GD_PURL_NAME}
     Then Verify purlID using %{GD_Token} and %{GD_PURLID}
     Then Verify status of purl as draft using %{GD_Token} and %{GD_PURLID}
     Then Change status of purl as public using %{GD_Token} and %{GD_PURLID}
     Then Verify status of purl as public using %{GD_Token} and %{GD_PURLID}
     Given User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
     Then Verify purlID using %{GD_Token} and %{GD_PURLID}
     Then Verify status of purl as public using %{GD_Token} and %{GD_PURLID}

   Scenario: Change PurlID status private and verify savepurlApi
     When Call master template API and get templateId using %{GD_Token}
     When Generate draft purl using savepurlAPI %{GD_Token} and %{GD_PURL_NAME}
     Then Verify purlID using %{GD_Token} and %{GD_PURLID}
     Then Verify status of purl as draft using %{GD_Token} and %{GD_PURLID}
     Then Change status of purl as private using %{GD_Token} and %{GD_PURLID}
     Then Verify status of purl as private using %{GD_Token} and %{GD_PURLID}
    
  Scenario: Verify PurlID by savepurlApi
    When Call master template API and get templateId using %{GD_Token}
    When Generate draft purl using savepurlAPI %{GD_Token} and %{GD_PURL_NAME}
    Then Verify purlID using %{GD_Token} and %{GD_PURLID}
    Then Verify status of purl as draft using %{GD_Token} and %{GD_PURLID}
                 
  Scenario: Campaign should not be created using draft purl by savePurlApi
    When Call master template API and get templateId using %{GD_Token}
    When Generate draft purl using savepurlAPI %{GD_Token} and %{GD_PURL_NAME}
    Then Verify purlID using %{GD_Token} and %{GD_PURLID}
    Then Verify status of purl as draft using %{GD_Token} and %{GD_PURLID}
    Then Call master template API and get EtemplateId using %{GD_Token}
    When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
    Then Campaign should not be created with invalid data using %{GD_Token}, %{GD_PURLID}, %{GD_ETEMPLATE_ID}, %{GD_CUSTOMER_EMAIL} and %{GD_MASTER_TEMPLATE_ID}
    
Scenario: Campaign should not be created by private purl of user2 by savePurlApi
    When Call master template API and get templateId using %{GD_Token}
    When Generate draft purl using savepurlAPI %{GD_Token} and %{GD_PURL_NAME}
    Then Verify purlID using %{GD_Token} and %{GD_PURLID}
    Then Verify status of purl as draft using %{GD_Token} and %{GD_PURLID}
    Then Change status of purl as private using %{GD_Token} and %{GD_PURLID}
    Then Verify status of purl as private using %{GD_Token} and %{GD_PURLID}
    Given User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
    Then Call master template API and get EtemplateId using %{GD_Token}
    When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
    Then Campaign should not be created with invalid data using %{GD_Token}, %{GD_PURLID}, %{GD_ETEMPLATE_ID}, %{GD_CUSTOMER_EMAIL} and %{GD_MASTER_TEMPLATE_ID}

  Scenario: Campaign should not be created by draft purl of user2 by savePurlApi
    When Call master template API and get templateId using %{GD_Token}
    When Generate draft purl using savepurlAPI %{GD_Token} and %{GD_PURL_NAME}
    Then Verify purlID using %{GD_Token} and %{GD_PURLID}
    Then Verify status of purl as draft using %{GD_Token} and %{GD_PURLID}
    Given User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
    Then Call master template API and get EtemplateId using %{GD_Token}
    When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
    Then Campaign should not be created with invalid data using %{GD_Token}, %{GD_PURLID}, %{GD_ETEMPLATE_ID}, %{GD_CUSTOMER_EMAIL} and %{GD_MASTER_TEMPLATE_ID}

  Scenario: Create Campaign using publicPurl by user2 createPurlApi
    And Generate Unique Purl Name using %{GD_PURL_NAME}
    When Call master template API and get templateId using %{GD_Token}
    When Generate purl by createpurl API using %{GD_Token}, %{GD_NEW_PURLNAME}, %{GD_PUBLIC_PURL} and %{GD_templateId}
    Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_PUBLIC_PURL}
    Given User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
    Then Call master template API and get EtemplateId using %{GD_Token}
    When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
    When Create campaign by using %{GD_Token}, %{GD_PURLID}, %{GD_CAMPAIGN_NAME}, %{GD_ETEMPLATE_ID}, %{GD_MASTER_TEMPLATE_ID} and %{GD_CUSTOMER_EMAIL}
    Then Verify campaignDetails by campaignById Api using %{GD_Token} and %{GD_CAMPAIGNID}
    When Verify campaign get associated with PurlID using %{GD_Token}, %{GD_PURLID} and %{GD_CAMPAIGNID}
    Then Delete purl which can not be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_MESSAGE} and %{GD_PURL_ERR_CODE} 
    Given User generates access Token for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then Campaign should not be found using %{GD_Token}, %{GD_CAMPAIGNID}, %{GD_ERROR_CODE} and %{GD_ERROR_MESSAGE}

  Scenario: Campaign should not be created using draft Purl by createPurlApi
    And Generate Unique Purl Name using %{GD_PURL_NAME}
    When Call master template API and get templateId using %{GD_Token}
    When Generate purl by createpurl API using %{GD_Token}, %{GD_NEW_PURLNAME}, %{GD_DRAFT_PURL} and %{GD_templateId}
    Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_DRAFT_PURL}
    Then Call master template API and get EtemplateId using %{GD_Token}
    When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
   Then Campaign should not be created with invalid data using %{GD_Token}, %{GD_PURLID}, %{GD_ETEMPLATE_ID}, %{GD_CUSTOMER_EMAIL} and %{GD_MASTER_TEMPLATE_ID}
    

  Scenario: Campaign should not be created by private purl of user2 createPurlApi
    And Generate Unique Purl Name using %{GD_PURL_NAME}
    When Call master template API and get templateId using %{GD_Token}
    When Generate purl by createpurl API using %{GD_Token}, %{GD_NEW_PURLNAME}, %{GD_PRIVATE_PURL} and %{GD_templateId}
    Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_PRIVATE_PURL}
    Given User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
    Then Call master template API and get EtemplateId using %{GD_Token}
    When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
    Then Campaign should not be created with invalid data using %{GD_Token}, %{GD_PURLID}, %{GD_ETEMPLATE_ID}, %{GD_CUSTOMER_EMAIL} and %{GD_MASTER_TEMPLATE_ID}
    

  Scenario: Create Campaign using publicPurl by user2 savePurlApi
    When Call master template API and get templateId using %{GD_Token}
    When Generate draft purl using savepurlAPI %{GD_Token} and %{GD_PURL_NAME}
    Then Verify purlID using %{GD_Token} and %{GD_PURLID}
    Then Verify status of purl as draft using %{GD_Token} and %{GD_PURLID}
    Then Change status of purl as public using %{GD_Token} and %{GD_PURLID}
    Then Verify status of purl as public using %{GD_Token} and %{GD_PURLID}
    Given User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
    Then Call master template API and get EtemplateId using %{GD_Token}
    When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
    When Create campaign by using %{GD_Token}, %{GD_PURLID}, %{GD_CAMPAIGN_NAME}, %{GD_ETEMPLATE_ID}, %{GD_MASTER_TEMPLATE_ID} and %{GD_CUSTOMER_EMAIL}
    When Verify campaign get associated with PurlID using %{GD_Token}, %{GD_PURLID} and %{GD_CAMPAIGNID}
    Then Delete purl which can not be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_MESSAGE} and %{GD_PURL_ERR_CODE} 
   Given User generates access Token for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then Campaign should not be found using %{GD_Token}, %{GD_CAMPAIGNID}, %{GD_ERROR_CODE} and %{GD_ERROR_MESSAGE}

  Scenario: Campaign should not be created using draft Purl user2 by Create PurlApi
    And Generate Unique Purl Name using %{GD_PURL_NAME}
    When Call master template API and get templateId using %{GD_Token}
    When Generate purl by createpurl API using %{GD_Token}, %{GD_NEW_PURLNAME}, %{GD_DRAFT_PURL} and %{GD_templateId}  
    Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_DRAFT_PURL}
    Given User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
    Then Call master template API and get EtemplateId using %{GD_Token}
    When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
    Then Campaign should not be created with invalid data using %{GD_Token}, %{GD_PURLID}, %{GD_ETEMPLATE_ID}, %{GD_CUSTOMER_EMAIL} and %{GD_MASTER_TEMPLATE_ID}
    

  Scenario: A public purl can be associated with multiple campaigns by different users
    And Generate Unique Purl Name using %{GD_PURL_NAME}
    When Call master template API and get templateId using %{GD_Token}
    When Generate purl by createpurl API using %{GD_Token}, %{GD_NEW_PURLNAME}, %{GD_PUBLIC_PURL} and %{GD_templateId}  
    Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_PUBLIC_PURL}
    Then Call master template API and get EtemplateId using %{GD_Token}
    When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
    When Create campaign by using %{GD_Token}, %{GD_PURLID}, %{GD_CAMPAIGN_NAME}, %{GD_ETEMPLATE_ID}, %{GD_MASTER_TEMPLATE_ID} and %{GD_CUSTOMER_EMAIL}
    Then Verify campaignDetails by campaignById Api using %{GD_Token} and %{GD_CAMPAIGNID}
    When Verify campaign get associated with PurlID using %{GD_Token}, %{GD_PURLID} and %{GD_CAMPAIGNID}
    Then Save multiple Campaigns data in dictionary
    Then Delete purl which can not be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_MESSAGE} and %{GD_PURL_ERR_CODE} 
    Given User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
    Then Campaign should not be found using %{GD_Token}, %{GD_CAMPAIGNID}, %{GD_ERROR_CODE} and %{GD_ERROR_MESSAGE}
    Then Call master template API and get EtemplateId using %{GD_Token}
    When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
    When Create campaign by using %{GD_Token}, %{GD_PURLID}, %{GD_CAMPAIGN_NAME}, %{GD_ETEMPLATE_ID}, %{GD_MASTER_TEMPLATE_ID} and %{GD_CUSTOMER_EMAIL}
    Then Verify campaignDetails by campaignById Api using %{GD_Token} and %{GD_CAMPAIGNID}
    When Verify multiple campaigns data by searchCampaignByPurlID using %{GD_Token} and %{GD_PURLID}
    Then Delete purl which can not be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_MESSAGE} and %{GD_PURL_ERR_CODE} 
    Given User generates access Token for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then Campaign should not be found using %{GD_Token}, %{GD_CAMPAIGNID}, %{GD_ERROR_CODE} and %{GD_ERROR_MESSAGE}

  Scenario: A private purl can be associated with multiple campaigns by single user
    And Generate Unique Purl Name using %{GD_PURL_NAME}
    When Call master template API and get templateId using %{GD_Token}
    When Generate purl by createpurl API using %{GD_Token}, %{GD_NEW_PURLNAME}, %{GD_PRIVATE_PURL} and %{GD_templateId}
    Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_PRIVATE_PURL}
    Then Call master template API and get EtemplateId using %{GD_Token}
     When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
    When Create campaign by using %{GD_Token}, %{GD_PURLID}, %{GD_CAMPAIGN_NAME}, %{GD_ETEMPLATE_ID}, %{GD_MASTER_TEMPLATE_ID} and %{GD_CUSTOMER_EMAIL}
    Then Verify campaignDetails by campaignById Api using %{GD_Token} and %{GD_CAMPAIGNID}
    When Verify campaign get associated with PurlID using %{GD_Token}, %{GD_PURLID} and %{GD_CAMPAIGNID}
    Then Save multiple Campaigns data in dictionary
    Then Delete purl which can not be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_MESSAGE} and %{GD_PURL_ERR_CODE}
    Then Call master template API and get EtemplateId using %{GD_Token}
    When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
    When Create campaign by using %{GD_Token}, %{GD_PURLID}, %{GD_CAMPAIGN_NAME}, %{GD_ETEMPLATE_ID}, %{GD_MASTER_TEMPLATE_ID} and %{GD_CUSTOMER_EMAIL}
    Then Verify campaignDetails by campaignById Api using %{GD_Token} and %{GD_CAMPAIGNID}
    When Verify multiple campaigns data by searchCampaignByPurlID using %{GD_Token} and %{GD_PURLID}
    Then Delete purl which can not be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_MESSAGE} and %{GD_PURL_ERR_CODE} 
    
  Scenario: An email template can be used for multiple campaigns by multiple user
    When Call master template API and get templateId using %{GD_Token}
    Then Call master template API and get EtemplateId using %{GD_Token}
    When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
    And Generate Unique Purl Name using %{GD_PURL_NAME}
    When Generate purl by createpurl API using %{GD_Token}, %{GD_NEW_PURLNAME}, %{GD_PUBLIC_PURL} and %{GD_templateId}  
    Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_PUBLIC_PURL}
    When Create campaign by using %{GD_Token}, %{GD_PURLID}, %{GD_CAMPAIGN_NAME}, %{GD_ETEMPLATE_ID}, %{GD_MASTER_TEMPLATE_ID} and %{GD_CUSTOMER_EMAIL}
    Then Verify campaignDetails by campaignById Api using %{GD_Token} and %{GD_CAMPAIGNID}
    When Verify campaign get associated with PurlID using %{GD_Token}, %{GD_PURLID} and %{GD_CAMPAIGNID}
    Then Save multiple Campaigns data in dictionary
    Then Delete purl which can not be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_MESSAGE} and %{GD_PURL_ERR_CODE} 
    Given User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
    Then Campaign should not be found using %{GD_Token}, %{GD_CAMPAIGNID}, %{GD_ERROR_CODE} and %{GD_ERROR_MESSAGE}
    When Create campaign by using %{GD_Token}, %{GD_PURLID}, %{GD_CAMPAIGN_NAME}, %{GD_ETEMPLATE_ID}, %{GD_MASTER_TEMPLATE_ID} and %{GD_CUSTOMER_EMAIL}
    Then Verify campaignDetails by campaignById Api using %{GD_Token} and %{GD_CAMPAIGNID}
    When Verify multiple campaigns data by searchCampaignByPurlID using %{GD_Token} and %{GD_PURLID}
    Then Delete purl which can not be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_MESSAGE} and %{GD_PURL_ERR_CODE} 
   Given User generates access Token for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then Campaign should not be found using %{GD_Token}, %{GD_CAMPAIGNID}, %{GD_ERROR_CODE} and %{GD_ERROR_MESSAGE}

  Scenario: An email template can be used for multiple campaigns by single user
    Then Call master template API and get EtemplateId using %{GD_Token}
    When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
    And Generate Unique Purl Name using %{GD_PURL_NAME}
    When Call master template API and get templateId using %{GD_Token}
    When Generate purl by createpurl API using %{GD_Token}, %{GD_NEW_PURLNAME}, %{GD_PRIVATE_PURL} and %{GD_templateId}
    Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_PRIVATE_PURL}
    When Create campaign by using %{GD_Token}, %{GD_PURLID}, %{GD_CAMPAIGN_NAME}, %{GD_ETEMPLATE_ID}, %{GD_MASTER_TEMPLATE_ID} and %{GD_CUSTOMER_EMAIL}
    Then Verify campaignDetails by campaignById Api using %{GD_Token} and %{GD_CAMPAIGNID}
    When Verify campaign get associated with PurlID using %{GD_Token}, %{GD_PURLID} and %{GD_CAMPAIGNID}
    Then Save multiple Campaigns data in dictionary
    Then Delete purl which can not be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_MESSAGE} and %{GD_PURL_ERR_CODE} 
    When Create campaign by using %{GD_Token}, %{GD_PURLID}, %{GD_CAMPAIGN_NAME}, %{GD_ETEMPLATE_ID}, %{GD_MASTER_TEMPLATE_ID} and %{GD_CUSTOMER_EMAIL}
    Then Verify campaignDetails by campaignById Api using %{GD_Token} and %{GD_CAMPAIGNID}
    When Verify multiple campaigns data by searchCampaignByPurlID using %{GD_Token} and %{GD_PURLID}
    Then Delete purl which can not be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_MESSAGE} and %{GD_PURL_ERR_CODE} 

  Scenario: Compare purlTemplate and savepurl content
    When Call master template API and get templateId using %{GD_Token}
    When Generate draft purl using savepurlAPI %{GD_Token} and %{GD_PURL_NAME}
    When Save Template Data using token %{GD_Token} and templateId %{GD_templateId}
    Then Call searchpurl and verify details from PurlTemplate using %{GD_Token} and %{GD_PURLID}

  Scenario: Campaign should not be created for invalid email template
    And Generate Unique Purl Name using %{GD_PURL_NAME}
    When Call master template API and get templateId using %{GD_Token}
    When Generate purl by createpurl API using %{GD_Token}, %{GD_NEW_PURLNAME}, %{GD_PUBLIC_PURL} and %{GD_templateId}  
    Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_PUBLIC_PURL}
   Then Campaign should not be created with invalid data using %{GD_Token}, %{GD_PURLID}, %{GD_INVALID_ETEMPLATE_ID}, %{GD_CUSTOMER_EMAIL} and %{GD_MASTER_TEMPLATE_ID}
  
  Scenario: Campaign should not be created for invalid purlID
    Then Call master template API and get EtemplateId using %{GD_Token}
    When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
    Then Campaign should not be created with invalid data using %{GD_Token}, %{GD_INVALID_PURLID},, %{GD_ETEMPLATE_ID}, %{GD_CUSTOMER_EMAIL} and %{GD_MASTER_TEMPLATE_ID}
    
       
  Scenario: Clone a public purl created by createPurl API using savePurl API
    And Generate Unique Purl Name using %{GD_PURL_NAME}
    When Call master template API and get templateId using %{GD_Token}
    When Generate purl by createpurl API using %{GD_Token}, %{GD_NEW_PURLNAME}, %{GD_PUBLIC_PURL} and %{GD_templateId}
    Then Clone purl using savePurl Api using %{GD_Token} and %{GD_PURLID}
    Then Search and Verify cloned purl details get matched using %{GD_Token} and %{GD_PURLID}
    
  Scenario: Clone a private purl created by createPurl API using savePurl API
    And Generate Unique Purl Name using %{GD_PURL_NAME}
    When Call master template API and get templateId using %{GD_Token}
    When Generate purl by createpurl API using %{GD_Token}, %{GD_NEW_PURLNAME}, %{GD_PRIVATE_PURL} and %{GD_templateId}
    Then Clone purl using savePurl Api using %{GD_Token} and %{GD_PURLID}
    Then Search and Verify cloned purl details get matched using %{GD_Token} and %{GD_PURLID}
       
   Scenario: Clone a draft purl created by createPurl API using savePurl API
     And Generate Unique Purl Name using %{GD_PURL_NAME}
     When Call master template API and get templateId using %{GD_Token}
     When Generate purl by createpurl API using %{GD_Token}, %{GD_NEW_PURLNAME}, %{GD_DRAFT_PURL} and %{GD_templateId}
     And Generate Unique Purl Name using %{GD_PURL_NAME}
    Then Clone a cloned purl or from draft purl by savePurl Api using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_CODE} and %{GD_PURL_ERR_MESSAGE}
    Then Change status of purl as private using %{GD_Token} and %{GD_PURLID}
    Then Clone purl using savePurl Api using %{GD_Token} and %{GD_PURLID}
    Then Search and Verify cloned purl details get matched using %{GD_Token} and %{GD_PURLID}
    
  Scenario: Clone a public purl created by savePurl API using savePurl API
    When Call master template API and get templateId using %{GD_Token}
    When Generate draft purl using savepurlAPI %{GD_Token} and %{GD_PURL_NAME}
    Then Change status of purl as public using %{GD_Token} and %{GD_PURLID}
    Then Clone purl using savePurl Api using %{GD_Token} and %{GD_PURLID}
    Then Search and Verify cloned purl details get matched using %{GD_Token} and %{GD_PURLID}
    Then Change status of purl as private using %{GD_Token} and %{GD_PURLID}
    Then Clone purl using savePurl Api using %{GD_Token} and %{GD_PURLID}
    Then Search and Verify cloned purl details get matched using %{GD_Token} and %{GD_PURLID}
        
  Scenario: Clone a private purl created by savePurl API using savePurl API
    When Call master template API and get templateId using %{GD_Token}
    When Generate draft purl using savepurlAPI %{GD_Token} and %{GD_PURL_NAME}
    Then Change status of purl as private using %{GD_Token} and %{GD_PURLID}
    Then Clone purl using savePurl Api using %{GD_Token} and %{GD_PURLID}
    Then Search and Verify cloned purl details get matched using %{GD_Token} and %{GD_PURLID}
    And Generate Unique Purl Name using %{GD_PURL_NAME}
    Then Clone a cloned purl or from draft purl by savePurl Api using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_CODE} and %{GD_PURL_ERR_MESSAGE}
    Given Update purl name and purlType public using %{GD_Token} and %{GD_PURLID} and %{GD_NEW_PURLNAME} and %{GD_PUBLIC_PURL}
    Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_PUBLIC_PURL}
    Then Clone purl using savePurl Api using %{GD_Token} and %{GD_PURLID}
    Then Search and Verify cloned purl details get matched using %{GD_Token} and %{GD_PURLID}
        
 Scenario: Clone a draft purl created by savePurl API using savePurl API
      When Call master template API and get templateId using %{GD_Token}
      When Generate draft purl using savepurlAPI %{GD_Token} and %{GD_PURL_NAME}
      And Generate Unique Purl Name using %{GD_PURL_NAME}
      And Clone a cloned purl or from draft purl by savePurl Api using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_CODE} and %{GD_PURL_ERR_MESSAGE}
     Then Change status of purl as public using %{GD_Token} and %{GD_PURLID}
     Then Clone purl using savePurl Api using %{GD_Token} and %{GD_PURLID}
     Then Search and Verify cloned purl details get matched using %{GD_Token} and %{GD_PURLID}
       
   Scenario: Create purlId and verify data details get matched
      And Generate Unique Purl Name using %{GD_PURL_NAME}
      When Call master template API and get templateId using %{GD_Token}
      When Generate purlId using %{GD_Token}, %{GD_NEW_PURLNAME} and %{GD_PUBLIC_PURL}
      Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_PUBLIC_PURL}
      And Delete purl and verify response purl should be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_SUCCESS_MESSAGE} and %{GD_PURL_SUCCESS_CODE}
      #Then Delete purl which can not be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_MESSAGE} and %{GD_PURL_ERR_CODE} 
      
   
   Scenario: Create purl with duplicate purl name and verify purl should not be created
      And Generate Unique Purl Name using %{GD_PURL_NAME}
      When Call master template API and get templateId using %{GD_Token}
      When Generate purlId using %{GD_Token}, %{GD_NEW_PURLNAME} and %{GD_PUBLIC_PURL}
      Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_PUBLIC_PURL} 
      Then Create purl with duplicate purl name and verify purl should not be created using %{GD_Token}, %{GD_NEW_PURLNAME} and %{GD_PUBLIC_PURL}  
      
   Scenario: Create public purl and verify with another user account purl should be visible but can not be deleted by other another
        And Generate Unique Purl Name using %{GD_PURL_NAME}
        When Call master template API and get templateId using %{GD_Token}
        Given Generate purlId purlType using %{GD_Token}, %{GD_NEW_PURLNAME} and %{GD_PUBLIC_PURL}
        Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_PUBLIC_PURL}
        And Verify purlList should contain purlId purlName using %{GD_Token}
        Given User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
        And Search and verify public purl should be visible to everyone using %{GD_Token} and %{GD_PURLID}
        And Verify purlList should contain purlId purlName using %{GD_Token}
        Then Delete purl of other user and verify purl should not be deleted using %{GD_Token} and %{GD_PURLID}
       
   Scenario: Create private purl verify in purl purlList and verify purl purlist and delete action from another user account should not be visible
        And Generate Unique Purl Name using %{GD_PURL_NAME}
        When Call master template API and get templateId using %{GD_Token}    
        Given Generate purlId purlType using %{GD_Token}, %{GD_NEW_PURLNAME} and %{GD_PRIVATE_PURL}
        Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_PRIVATE_PURL}
        And Verify purlList should contain purlId purlName using %{GD_Token}
        Given User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
        Then Login with another user and search for private or draft purl and verify purl should not be visible using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_CODE} and %{GD_PURL_MESSAGE}
        And Verify purlList should not contain purlId and purlName using %{GD_Token}
        Then Delete purl of other user and verify purl should not be deleted using %{GD_Token} and %{GD_PURLID}
        Given User generates access Token for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
        And Delete purl and verify response purl should be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_SUCCESS_MESSAGE} and %{GD_PURL_SUCCESS_CODE}
       
  Scenario: Create draft purl and search and verify purl purlList delete action from another user account purl should not be visible then delete from purl owner account
	   And Generate Unique Purl Name using %{GD_PURL_NAME}
	   When Call master template API and get templateId using %{GD_Token}
	   Given Generate purlId purlType using %{GD_Token}, %{GD_NEW_PURLNAME} and %{GD_DRAFT_PURL}
	   Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_DRAFT_PURL} 
	   And Verify purlList should contain purlId purlName using %{GD_Token}
	   Then User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
	   Then Login with another user and search for private or draft purl and verify purl should not be visible using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_CODE} and %{GD_PURL_MESSAGE}
	   And Verify purlList should not contain purlId and purlName using %{GD_Token}
	   Then Delete purl of other user and verify purl should not be deleted using %{GD_Token} and %{GD_PURLID}
	   Given User generates access Token for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
	   And Delete purl and verify response purl should be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_SUCCESS_MESSAGE} and %{GD_PURL_SUCCESS_CODE}
	   
   Scenario: Create public purl then login with another user account and clone from public purl then verify another user can clone from other user public purl
	   And Generate Unique Purl Name using %{GD_PURL_NAME}
	   When Call master template API and get templateId using %{GD_Token}
	   Given Generate purlId purlType using %{GD_Token}, %{GD_NEW_PURLNAME} and %{GD_PUBLIC_PURL}
	   Then User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
	   And Generate Unique Purl Name using %{GD_PURL_NAME}
	   Then Login with other user and clone_Purl using %{GD_Token} and %{GD_PURLID}
	   Then Search and verify cloned purl and purlType should be draft by default using %{GD_Token}, %{GD_PURLID} and %{GD_DRAFT_PURL}
	   And Verify purlList should contain purlId purlName using %{GD_Token}
	   And Delete purl and verify response purl should be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_SUCCESS_MESSAGE} and %{GD_PURL_SUCCESS_CODE}
	    
   Scenario: Create emailTemplate and verify eTemplateId
    Then Call master template API and get EtemplateId using %{GD_Token}
        When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
	         
   Scenario: Create purl purlType draft and update purlType public then verify purl should be visible to other user account then delete 
	    And Generate Unique Purl Name using %{GD_PURL_NAME}
        When Call master template API and get templateId using %{GD_Token}       
        Given Generate purlId purlType using %{GD_Token}, %{GD_NEW_PURLNAME} and %{GD_DRAFT_PURL}
        Then Generate Unique Purl Name using %{GD_PURL_NAME}
        Given Update purl name and purlType public using %{GD_Token} and %{GD_PURLID} and %{GD_NEW_PURLNAME} and %{GD_PUBLIC_PURL}
        Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_PUBLIC_PURL}
	    And Verify purlList should contain purlId purlName using %{GD_Token}
	   	Then User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
	    And Search and verify public purl should be visible to everyone using %{GD_Token} and %{GD_PURLID}
	    And Verify purlList should contain purlId purlName using %{GD_Token}
	    Then Delete purl of other user and verify purl should not be deleted using %{GD_Token} and %{GD_PURLID}
	    Given User generates access Token for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
	    And Delete purl and verify response purl should be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_SUCCESS_MESSAGE} and %{GD_PURL_SUCCESS_CODE}
	    	
   Scenario: Create purl purlType draft and update purl name and purlType private then verify purl should not be visible to another user account
	    And Generate Unique Purl Name using %{GD_PURL_NAME}
        When Call master template API and get templateId using %{GD_Token}
        Given Generate purlId purlType using %{GD_Token}, %{GD_NEW_PURLNAME} and %{GD_DRAFT_PURL}
        Given Update purlType private using %{GD_Token} and %{GD_PURLID} and %{GD_NEW_PURLNAME} and %{GD_PRIVATE_PURL}
	    Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_PRIVATE_PURL}
	    And Verify purlList should contain purlId purlName using %{GD_Token}
	    Then User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
	    Then Login with another user and search for private or draft purl and verify purl should not be visible using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_CODE} and %{GD_PURL_MESSAGE}
	    And Verify purlList should not contain purlId and purlName using %{GD_Token}
	    
  Scenario: Create purl purlType private then update purlType public and verify purl should be visible to another account
        And Generate Unique Purl Name using %{GD_PURL_NAME}
        When Call master template API and get templateId using %{GD_Token}
        Given Generate purlId purlType using %{GD_Token}, %{GD_NEW_PURLNAME} and %{GD_PRIVATE_PURL}
        Given Update purl name and purlType public using %{GD_Token} and %{GD_PURLID} and %{GD_NEW_PURLNAME} and %{GD_PUBLIC_PURL}
	    Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_PUBLIC_PURL}
	    Then User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
	    And Search and verify public purl should be visible to everyone using %{GD_Token} and %{GD_PURLID}
	    And Verify purlList should contain purlId purlName using %{GD_Token}

    Scenario: Create purl purlType public and update purl name and purlType draft then verify purl should not be visible to another user account
	   And Generate Unique Purl Name using %{GD_PURL_NAME}
	   When Call master template API and get templateId using %{GD_Token}
	   Given Generate purlId purlType using %{GD_Token}, %{GD_NEW_PURLNAME} and %{GD_PUBLIC_PURL}
       Then Generate Unique Purl Name using %{GD_PURL_NAME}
       Given Update purlType draft from public using %{GD_Token} and %{GD_PURLID} and %{GD_NEW_PURLNAME} and %{GD_DRAFT_PURL}
	   Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_DRAFT_PURL}
	   And Verify purlList should contain purlId purlName using %{GD_Token}
	   Then User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
	   Then Login with another user and search for private or draft purl and verify purl should not be visible using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_CODE} and %{GD_PURL_MESSAGE}
	   And Verify purlList should not contain purlId and purlName using %{GD_Token}
	
	Scenario: Create purl purlType public and create emailTemplateId then associate with campaign then update purlType and verify purl type can not be updated 
        And Generate Unique Purl Name using %{GD_PURL_NAME}
        When Call master template API and get templateId using %{GD_Token}
        Given Generate purlId purlType using %{GD_Token}, %{GD_NEW_PURLNAME} and %{GD_PUBLIC_PURL}
        Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_PUBLIC_PURL}
        Then Call master template API and get EtemplateId using %{GD_Token}
        When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
        When Create campaign by using %{GD_Token}, %{GD_PURLID}, %{GD_CAMPAIGN_NAME}, %{GD_ETEMPLATE_ID}, %{GD_MASTER_TEMPLATE_ID} and %{GD_CUSTOMER_EMAIL}
        Then Verify campaignDetails by campaignById Api using %{GD_Token} and %{GD_CAMPAIGNID}	    
        And Generate Unique Purl Name using %{GD_PURL_NAME}
        When Verify update purl with purlId using %{GD_Token}, %{GD_PURLID}, %{GD_NEW_PURLNAME}, %{GD_PRIVATE_PURL}, %{GD_PURL_OWNER} and %{GD_templateId}
        Then Delete purl which can not be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_MESSAGE} and %{GD_PURL_ERR_CODE} 
        
   	Scenario: Create purl purlType Public then login with other user and associate public purlId with campaign and change purlType private from first user
        And Generate Unique Purl Name using %{GD_PURL_NAME}
        When Call master template API and get templateId using %{GD_Token}
        Given Generate purlId purlType using %{GD_Token}, %{GD_NEW_PURLNAME} and %{GD_PUBLIC_PURL}
         When Call master template API and get EtemplateId using %{GD_Token}
        Then Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
	   	Then User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
	    When Create campaign by using %{GD_Token}, %{GD_PURLID}, %{GD_CAMPAIGN_NAME}, %{GD_ETEMPLATE_ID}, %{GD_MASTER_TEMPLATE_ID} and %{GD_CUSTOMER_EMAIL}
        Then Verify campaignDetails by campaignById Api using %{GD_Token} and %{GD_CAMPAIGNID}	    
        Given User generates access Token for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
        When Call master template API and get templateId using %{GD_Token}
        When Verify update purl with purlId using %{GD_Token}, %{GD_PURLID}, %{GD_NEW_PURLNAME}, %{GD_PRIVATE_PURL}, %{GD_PURL_OWNER} and %{GD_templateId}
        Then Delete purl which can not be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_MESSAGE} and %{GD_PURL_ERR_CODE} 
        
  Scenario: Create purl purlType public and associate public purlId with campaign then update purl name from another user account and verify purl name should not be updated
       And Generate Unique Purl Name using %{GD_PURL_NAME}
       When Call master template API and get templateId using %{GD_Token}
       Given Generate purlId purlType using %{GD_Token}, %{GD_NEW_PURLNAME} and %{GD_PUBLIC_PURL}
       When Call master template API and get EtemplateId using %{GD_Token}
       Then Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
	   When Create campaign by using %{GD_Token}, %{GD_PURLID}, %{GD_CAMPAIGN_NAME}, %{GD_ETEMPLATE_ID}, %{GD_MASTER_TEMPLATE_ID} and %{GD_CUSTOMER_EMAIL}
	   And Generate Unique Purl Name using %{GD_PURL_NAME}
	   Then User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
	   When Call master template API and get templateId using %{GD_Token}
	   When Verify update purl with purlId using %{GD_Token}, %{GD_PURLID}, %{GD_NEW_PURLNAME}, %{GD_PUBLIC_PURL},  and %{GD_templateId}
       Then Delete purl which can not be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_MESSAGE} and %{GD_PURL_ERR_CODE} 

        
 Scenario: Create purl purlType public and associate public purlId with campaign then update purl name from purl owner account
        And Generate Unique Purl Name using %{GD_PURL_NAME}
        When Call master template API and get templateId using %{GD_Token}
        Given Generate purlId purlType using %{GD_Token}, %{GD_NEW_PURLNAME} and %{GD_PUBLIC_PURL}
        Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_PUBLIC_PURL}
                When Call master template API and get EtemplateId using %{GD_Token}
        
        Then Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
	    When Create campaign by using %{GD_Token}, %{GD_PURLID}, %{GD_CAMPAIGN_NAME}, %{GD_ETEMPLATE_ID}, %{GD_MASTER_TEMPLATE_ID} and %{GD_CUSTOMER_EMAIL}
	    And Generate Unique Purl Name using %{GD_PURL_NAME}
        Given Update purl name and purlType public using %{GD_Token} and %{GD_PURLID} and %{GD_NEW_PURLNAME} and %{GD_PUBLIC_PURL}
        Then Search and Verify data details get matched using %{GD_Token}, %{GD_PURLID} and %{GD_PUBLIC_PURL}
        Then Delete purl which can not be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_MESSAGE} and %{GD_PURL_ERR_CODE}  
	 
   Scenario: Create purl purlType draft and update purlType private from other user account then verify purl should not be updated
	    And Generate Unique Purl Name using %{GD_PURL_NAME}
	    When Call master template API and get templateId using %{GD_Token}
	    Given Generate purlId purlType using %{GD_Token}, %{GD_NEW_PURLNAME} and %{GD_DRAFT_PURL}
        Then User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
        When Call master template API and get templateId using %{GD_Token}
        When Verify update purl with purlId using %{GD_Token}, %{GD_PURLID}, %{GD_NEW_PURLNAME}, %{GD_PRIVATE_PURL} and %{GD_templateId}  
	    	
   Scenario: Create purl purlType private and update purlType public from other user account and verify purl should not be updated
	    And Generate Unique Purl Name using %{GD_PURL_NAME}
	    When Call master template API and get templateId using %{GD_Token}
	    Given Generate purlId purlType using %{GD_Token}, %{GD_NEW_PURLNAME} and %{GD_PRIVATE_PURL}
        Then User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
        When Call master template API and get templateId using %{GD_Token}
        When Verify update purl with purlId using %{GD_Token}, %{GD_PURLID}, %{GD_NEW_PURLNAME}, %{GD_PUBLIC_PURL} and %{GD_templateId}    
              
    Scenario: Create purl purlType public and update purlType private from other user account then verify purl should not be updated
	    And Generate Unique Purl Name using %{GD_PURL_NAME}
	    When Call master template API and get templateId using %{GD_Token}
	    Given Generate purlId purlType using %{GD_Token}, %{GD_NEW_PURLNAME} and %{GD_PUBLIC_PURL}
        Then User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
        When Call master template API and get templateId using %{GD_Token}
        When Verify update purl with purlId using %{GD_Token}, %{GD_PURLID}, %{GD_NEW_PURLNAME}, %{GD_PRIVATE_PURL} and %{GD_templateId}     
        
   Scenario: Create purl purlType public and associate public purlId with campaign then update purlType draft from other user and verify purl should not be updated
        And Generate Unique Purl Name using %{GD_PURL_NAME}
        When Call master template API and get templateId using %{GD_Token}        
	    Given Generate purlId purlType using %{GD_Token}, %{GD_NEW_PURLNAME} and %{GD_PUBLIC_PURL}
	    When Call master template API and get EtemplateId using %{GD_Token}
	    
        Then Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
        When Create campaign by using %{GD_Token}, %{GD_PURLID}, %{GD_CAMPAIGN_NAME}, %{GD_ETEMPLATE_ID}, %{GD_MASTER_TEMPLATE_ID} and %{GD_CUSTOMER_EMAIL}
	     When Verify update purl with purlId using %{GD_Token}, %{GD_PURLID}, %{GD_NEW_PURLNAME}, %{GD_DRAFT_PURL} and %{GD_templateId}
	    And Generate Unique Purl Name using %{GD_PURL_NAME}
	    Then User generates access Token for %{GD_SECOND_EMAIL_ADDRESS} and %{GD_SECOND_PASSWORD}
	    When Call master template API and get templateId using %{GD_Token}
	    Then Update purl name purlType draft of other user and verify purl should not be updated using %{GD_Token} and %{GD_PURLID} and %{GD_NEW_PURLNAME} and %{GD_DRAFT_PURL}
        Then Delete purl which can not be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_MESSAGE} and %{GD_PURL_ERR_CODE}  
                      
    Scenario: Create purl purlType private and associate PurlId with campaign then update purlType public from same user account
	    And Generate Unique Purl Name using %{GD_PURL_NAME}
	    When Call master template API and get templateId using %{GD_Token}
	    When Generate purl by createpurl API using %{GD_Token}, %{GD_NEW_PURLNAME}, %{GD_PRIVATE_PURL} and %{GD_templateId}
    Then Call master template API and get EtemplateId using %{GD_Token}
	    When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
	    When Create campaign by using %{GD_Token}, %{GD_PURLID}, %{GD_CAMPAIGN_NAME}, %{GD_ETEMPLATE_ID}, %{GD_MASTER_TEMPLATE_ID} and %{GD_CUSTOMER_EMAIL}	    
        Then Verify campaignDetails by campaignById Api using %{GD_Token} and %{GD_CAMPAIGNID}
	    When Verify update purl with purlId using %{GD_Token}, %{GD_PURLID}, %{GD_NEW_PURLNAME}, %{GD_PUBLIC_PURL} and %{GD_templateId} 
	    Then Delete purl which can not be deleted using %{GD_Token}, %{GD_PURLID}, %{GD_PURL_ERR_MESSAGE} and %{GD_PURL_ERR_CODE}  
	    
	    
	Scenario: Verify create purl api with invalid purlOwner
	      And Generate Unique Purl Name using %{GD_PURL_NAME}
	      When Call master template API and get templateId using %{GD_Token}
	      And Verify create purl api with invalid purlOwner using %{GD_Token}, %{GD_NEW_PURLNAME}, %{GD_PUBLIC_PURL}, %{GD_INVALID_PURL_OWNER} and %{GD_ERROR_MESSAGE}
	    
	    
   Scenario: Verify create purl api with invalid slideOwner
	     And Generate Unique Purl Name using %{GD_PURL_NAME}
	     When Call master template API and get templateId using %{GD_Token}
	     And Verify create purl api with invalid slideOwner using %{GD_Token}, %{GD_NEW_PURLNAME}, %{GD_PUBLIC_PURL}, %{GD_INVALID_SLIDE_OWNER} and %{GD_ERROR_MESSAGE}
	    	  
   Scenario: verify Create campaign with duplicate campaign name
        And Generate Unique Purl Name using %{GD_PURL_NAME}
        When Call master template API and get templateId using %{GD_Token}
        When Generate purl by createpurl API using %{GD_Token}, %{GD_NEW_PURLNAME}, %{GD_PUBLIC_PURL} and %{GD_templateId}
    Then Call master template API and get EtemplateId using %{GD_Token}
        When Create email templateID using %{GD_Token}, %{GD_TEMPLATE_NAME} and %{GD_EtemplateId}
        When Create campaign by using %{GD_Token}, %{GD_PURLID}, %{GD_CAMPAIGN_NAME}, %{GD_ETEMPLATE_ID}, %{GD_MASTER_TEMPLATE_ID} and %{GD_CUSTOMER_EMAIL}	    
	    Then Create campaign with duplicate campaign name by using %{GD_Token}, %{GD_CAMPAIGN_NAME}, %{GD_PURLID}, %{GD_ETEMPLATE_ID}, %{GD_MASTER_TEMPLATE_ID}, %{GD_CUSTOMER_EMAIL}, %{GD_ERROR_MESSAGE} and %{GD_ERROR_CODE}
        	       	 
  Scenario: Verify create purl api with invalid templateId
	     And Generate Unique Purl Name using %{GD_PURL_NAME}
	     When Call master template API and get templateId using %{GD_Token}
	     Then Create purl with invalid templateId using %{GD_Token}, %{GD_NEW_PURLNAME}, %{GD_PUBLIC_PURL}, %{GD_INVALID_TEMPLATEID}, %{GD_ERROR_MESSAGE} and %{GD_ERROR_CODE}
	     
	Scenario: Update purl with invalid templateId 
	    And Generate Unique Purl Name using %{GD_PURL_NAME}
        When Call master template API and get templateId using %{GD_Token}
        When Generate purl by createpurl API using %{GD_Token}, %{GD_NEW_PURLNAME}, %{GD_PUBLIC_PURL} and %{GD_templateId}
        When Verify update purl with purlId using %{GD_Token}, %{GD_PURLID}, %{GD_NEW_PURLNAME}, %{GD_PUBLIC_PURL} and %{GD_INVALID_TEMPLATEID}
        
    Scenario: Verify create purl by savepurlApi with invalid templateId
      When Call master template API and get templateId using %{GD_Token}
      When Generate purl by savepurlApi with invalid templateId using %{GD_Token}, %{GD_INVALID_TEMPLATEID}, %{GD_ERROR_MESSAGE} and %{GD_ERROR_CODE}
       
   Scenario: Search for invalid purlId and verify
       Given Search for invalid purlId using %{GD_Token}, %{GD_INVALID_PURLID}, %{GD_ERROR_MESSAGE} and %{GD_ERROR_CODE}
       
   Scenario: Update purl with invalid purlId 
      	 And Generate Unique Purl Name using %{GD_PURL_NAME}
      	 When Call master template API and get templateId using %{GD_Token}
         When Verify update purl with purlId using %{GD_Token}, %{GD_INVALID_PURLID}, %{GD_NEW_PURLNAME}, %{GD_PUBLIC_PURL} and %{GD_templateId}
                 
    Scenario: Clone purl with duplicate purl name
     And Generate Unique Purl Name using %{GD_PURL_NAME}
     When Call master template API and get templateId using %{GD_Token}
     When Generate purl by createpurl API using %{GD_Token}, %{GD_NEW_PURLNAME}, %{GD_PUBLIC_PURL} and %{GD_templateId}
     Then Clone purl with duplicate purl name using %{GD_Token}, %{GD_PURLID}, %{GD_ERROR_CODE} and %{GD_ERROR_MESSAGE}
     
   Scenario: Update purl with invalid purlOwner
      	  And Generate Unique Purl Name using %{GD_PURL_NAME}
      	  When Call master template API and get templateId using %{GD_Token}
      	  When Generate purl by createpurl API using %{GD_Token}, %{GD_NEW_PURLNAME}, %{GD_PUBLIC_PURL} and %{GD_templateId}
         When Verify update purl with purlId using %{GD_Token}, %{GD_PURLID}, %{GD_NEW_PURLNAME}, %{GD_PUBLIC_PURL}, %{GD_INVALID_PURL_OWNER} and %{GD_templateId}
    

        	
