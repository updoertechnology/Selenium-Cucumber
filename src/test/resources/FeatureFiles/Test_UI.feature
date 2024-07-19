@Test
Feature: demo Test Cases

#  Background:
#    Given User is on Login Page
#    Then User Login with the Valid Credential

  @TC_01
  Scenario: Verify that user can login through valid credentials
    Given User is on Login Page
    Then User Login with the Valid Credential

  @TC_02
  Scenario: Verify that user can login through Invalid credentials
    Given User is on Login Page
    And User Login with the InValid Credential
    Then Verify the Error Message

  @TC_03
  Scenario: Verify that user can login through Invalid credentials
    Given User is on Login Page
    And User Login with the Valid Credential
    And User Navigate to Register   Page
    And User Enter Name and Click on Register
    And User Click on Check-in box
    And User enters Information
    And User Click on Connect to Assistant button
    Then Verify the "Waiting for Assistant" message






