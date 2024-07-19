package com.demo.pagefactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.demo.utils.GenericWrappers;

public class Login extends GenericWrappers {
    @FindBy(id = "user-email")
    public WebElement txt_Username;
    @FindBy(id = "password")
    public static WebElement txt_Password;
    @FindBy(id = "submitBtn")
    public WebElement btn_SignIn;

    @FindBy(css = "div[class=\"card-body text-center\"] h3")
    public WebElement invalidLoginErrorMsg;
}

