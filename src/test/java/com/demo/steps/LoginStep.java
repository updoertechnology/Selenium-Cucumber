package com.demo.steps;

import com.demo.TestData.TestData;
import com.demo.pages.LoginPage;
import com.demo.utils.GenericWrappers;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class LoginStep extends LoginPage {
    @Given("User is on Login Page")
    public void userIsOnLoginPage() {
        new GenericWrappers().openURL();
    }

    @Then("User Login with the Valid Credential")
    public void userLoginWithTheValidCredential() {
        enterEmail();
        enterPassword();
        clickSignInButton();
    }

    @Then("User Login with the InValid Credential")
    public void userLoginWithTheInValidCredential() {
        enterInvalidEmail();
        enterInvalidPassword();
        clickSignInButton();
    }

    @Then("Verify the Error Message")
    public void verifyTheErrorMessage() {
        isElementTextMatch(invalidLoginErrorMsg, TestData.InvalidLoginMessage);
    }
}
