package com.demo.pages;

import org.openqa.selenium.support.PageFactory;

import com.demo.pagefactory.Login;

public class LoginPage extends Login {
    public LoginPage(){PageFactory.initElements(getDriver(), this);
    }
    public void enterEmail() {
        enterTextWithoutScroll(txt_Username,globalUserName);
    }
    public void enterPassword()
    {
        enterTextWithoutScroll(txt_Password, globalPassword);
    }
    public void clickSignInButton() {
        clickButtonWithOutScroll(btn_SignIn);
        waitFor(4000);
    }

    public void enterInvalidEmail() {
        enterTextWithoutScroll(txt_Username, config.getString("invalidUsername"));
    }
    public void enterInvalidPassword()
    {
        enterTextWithoutScroll(txt_Password, config.getString("invalidPassword"));
    }


}