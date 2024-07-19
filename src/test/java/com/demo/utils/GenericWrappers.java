package com.demo.utils;

import com.cucumber.listener.Reporter;
import cucumber.api.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class GenericWrappers extends Base implements Wrappers, Wrappers.SelectDropDown{

    public static Logger logger = Logger.getLogger(String.valueOf(GenericWrappers.class));
    public static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
    public static Configuration config = null;
    private String parallelExecution;
    public static String appUrl, globalUserName, globalPassword;
    private String browser;
    public int implicit=60;
    public static int  explicit=60;
    private Select select;

    public GenericWrappers() {

        ConfigurationFactory factory = new ConfigurationFactory("src/test/configFile/config.xml");
        try {
            config = factory.getConfiguration();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        browser = config.getString("browser").toLowerCase();
     //   browser = System.getProperty("browser").toLowerCase();
        implicit = Integer.parseInt(config.getString("implicit").toLowerCase());
        explicit = Integer.parseInt(config.getString("explicit").toLowerCase());
        parallelExecution = config.getString("parallelExecution");
    }

    public void invokeApp(){

        launchWinBrowser();
        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
        //setImplicit(implicit);
//        getUrl(setUrl());
    }
    public void openURL(){
        getUrl(setUrl());
    }
    public void refresh(){

        getDriver().navigate().refresh();
    }

    public String setUrl(){

            appUrl = config.getString("url");
            globalUserName = config.getString("username");
            globalPassword = config.getString("password");

        return appUrl;
    }

    public void launchWinBrowser() {
        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setCapability("build", "demo");
        capability.setCapability("network", true);
        capability.setCapability("video", true);
        capability.setCapability("console", true);
        capability.setCapability("visual", true);
        capability.setCapability("terminal", true);
        if (parallelExecution.equalsIgnoreCase("true")) {
            if (browser.toLowerCase().equals(Variable.CHROME)) {
                capability.setCapability(CapabilityType.BROWSER_NAME, "chrome");
                capability.setCapability(CapabilityType.VERSION, "latest");

            } else if (browser.toLowerCase().equals(Variable.FIREFOX)){
                capability.setCapability(CapabilityType.BROWSER_NAME, "firefox");
                capability.setCapability(CapabilityType.VERSION, "latest");

            }

        } else if (parallelExecution.equalsIgnoreCase("false")) {
            if (browser.toLowerCase().equals(Variable.CHROME)) {
                String osname = (System.getProperty("os.name"));
                if (isTextContain(osname, ("Mac"))) {
                	WebDriverManager.chromedriver().setup();
                	Map<String, Object> prefs = new HashMap<>();
        			prefs.put("profile.default_content_setting_values.notifications", 2);
        			ChromeOptions options = new ChromeOptions();
        			options.setExperimentalOption("prefs", prefs);

        	        driver.set(new ChromeDriver(options));
                	
                	//System.setProperty("webdriver.chrome.driver", "./driver/chromedriver");
                    //driver.set(new ChromeDriver());
                } else if (isTextContain(osname, ("Windows"))) {
                    WebDriverManager.chromedriver().setup();
                    Map<String, Object> prefs = new HashMap<>();
                    prefs.put("profile.default_content_setting_values.notifications", 2);
                    ChromeOptions options = new ChromeOptions();
                    options.setExperimentalOption("prefs", prefs);
                    driver.set(new ChromeDriver(options));
                } else if (isTextContain(osname, ("Linux"))) {
                    System.setProperty("webdriver.chrome.driver", "./driver/chromedriverlinux");
                    driver.set(new ChromeDriver());
                }
            }
            else if (browser.toLowerCase().equals(Variable.FIREFOX)) {
                String osname = (System.getProperty("os.name"));
                if (isTextContain(osname, ("Mac"))) {
                    System.setProperty("webdriver.gecko.driver", "./driver/geckodriver");
                    driver.set(new FirefoxDriver());
                } else if (isTextContain(osname, ("Windows"))) {
                    System.setProperty("webdriver.gecko.driver", "./driver/geckodriver.exe");
                    driver.set(new FirefoxDriver());
                } else if (isTextContain(osname, ("Linux"))) {
                    System.setProperty("webdriver.gecko.driver", "./driver/geckodriverlinux");
                    driver.set(new FirefoxDriver());
                }
            }
        }
    }

    public static RemoteWebDriver getDriver(){

        return driver.get();
    }

    public void getUrl(String url){

        getDriver().navigate().to(url);
    }

    public void setImplicit(int timeOut){

        getDriver().manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
    }


    public int setDefaultExplicit(){

        return Integer.parseInt("60");
    }

    public int setDefaultImplicit(){

        return Integer.parseInt("60");
    }

    public static WebDriverWait webDriverWait(){

        return new WebDriverWait(getDriver(), explicit);
    }

    public void quitBrowser() {
        getDriver().quit();
        driver.remove();
    }

    public void closeBrowser() {
        getDriver().close();
    }

    public void mouseOver(WebElement element) {
        waitVisibilityOfElement(element);
        new Actions(getDriver()).moveToElement(element).build().perform();

    }
    public void mouseOverClick(WebElement element) {
        waitVisibilityOfElement(element);
        new Actions(getDriver()).moveToElement(element).click(element).build().perform();
    }

    public void mouseOver(List<WebElement> element, int index) {
        waitVisibilityOfElement(element.get(index));
        new Actions(getDriver()).moveToElement(element.get(index)).build().perform();
    }
    public void mouseRightClick(WebElement element){
        waitVisibilityOfElement(element);
        new Actions(getDriver()).contextClick(element).perform();
    }
    public void mouseDoubleClick(WebElement element){
        waitVisibilityOfElement(element);
        new Actions(getDriver()).doubleClick(element).perform();
    }
    public void doubleClickWithJS(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].dispatchEvent(new MouseEvent('dblclick', { bubbles: true }));", element);
        waitFor(2000);
    }
    public String getText(WebElement element){

        waitVisibilityOfElement(element);
        highLighterMethod(getDriver(), element);
        //scrollToElement(element);
        logger.info("Element Text - " + element.getText());
        return element.getText();
    }

    public boolean isTextMatch(String actual, String expected) {

        logger.info("Actual Value - "+ actual + "\n" +"Expected Value - " + expected);
        return actual.equalsIgnoreCase(expected);
    }

    public boolean isElementTextMatch(WebElement actualElement, String expected){

        return isTextContain(getText(actualElement), expected);
    }

    public boolean isTextContain(String actual, String expected) {

        logger.info("Actual text - " + actual + "\n" + "Expected text - " + expected);
        return actual.contains(expected);
    }

    public void waitFor(int sleepTime){
        try {

            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }

    public void clickButton(WebElement element) {
        scrollToElement(element);
        waitVisibilityOfElement(element);
        waitElementToBeClickable(element);
        element.click();
    }

    public void clickButtonWithOutScroll(WebElement element) {

        waitVisibilityOfElement(element);
        waitElementToBeClickable(element);
        highLighterMethod(getDriver(),element);
        element.click();
    }

    public void click(WebElement element){
        highLighterMethod(getDriver(),element);
        element.click();
    }

    public void scrollToTop() {

        JavascriptExecutor js =  (JavascriptExecutor)getDriver();
        js.executeScript("window.scrollTo(0,0)");
        waitFor(1000);
    }
    public void scrollToBottom() {
        ((JavascriptExecutor) getDriver())
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
        waitFor(3000);
    }
    public void clickWithJS(WebElement element){
        JavascriptExecutor js =  (JavascriptExecutor)getDriver();
        js.executeScript("arguments[0].click();", element);
        waitFor(2000);
    }
    public void enterTextWithJS(WebElement element, String enter){
        JavascriptExecutor js =  (JavascriptExecutor)getDriver();
        js.executeScript("arguments[0].value='"+enter+"';", element);
    }

    public By locateXpath(String xpath){

        return By.xpath(xpath);
    }

    public By locateCss(String css){

        return By.cssSelector(css);
    }

    public void clickButton(WebElement scrollToElement, WebElement clickElement) {

        waitFor(1000);
        scrollToElement(scrollToElement);
        waitVisibilityOfElement(clickElement);
        waitElementToBeClickable(clickElement);
        clickElement.click();
    }

    public void clickDropDown(WebElement element, String xpath) {

        waitFor(1000);
        waitPresenceOfElementLocated(locateCss(xpath));
        element.click();
    }

    public void enterText(WebElement element, String ... textValue) {
        //scrollToElement(element);
        waitVisibilityOfElement(element);
        element.clear();
        logger.info("Entered Text - " + textValue);
        element.sendKeys(textValue);
    }
    public void enterTextAppend(WebElement element, String textValue){
        waitElementToBeClickable(element);
        logger.info("Entered Text - " + textValue);
        element.sendKeys(Keys.chord(Keys.ARROW_RIGHT));
        for (char c : textValue.toCharArray()) {
            String charAsString = String.valueOf(c);
            element.sendKeys(charAsString);
            waitFor(100);
        }

    }
    public void enterTextAndEnter(WebElement element, String ... textValue) {
        //scrollToElement(element);
        waitVisibilityOfElement(element);
        element.clear();
        logger.info("Entered Text - " + textValue);
        element.sendKeys(Keys.ENTER);
    }

    public void enterTextWithoutScroll(WebElement element, String textValue) {
        waitElementToBeClickable(element);
        element.clear();
        logger.info("Entered Text - " + textValue);
        element.sendKeys(textValue);
    }
    public void enterTextAndClearAll(WebElement element, String textValue){
        waitElementToBeClickable(element);
        logger.info("Entered Text - " + textValue);
        element.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
        element.sendKeys(textValue);
    }
    public void enterTabKey(WebElement element){
        waitElementToBeClickable(element);
        element.sendKeys(Keys.TAB);
    }
    public void slowType(WebElement element, String text) {
        waitElementToBeClickable(element);
        logger.info("Entered Text - " + text);
        for (char c : text.toCharArray()) {
            String charAsString = String.valueOf(c);
            element.sendKeys(charAsString);
            waitFor(100);
        }
    }

    public void waitVisibilityOfElement(WebElement element) {

        webDriverWait().until(ExpectedConditions.visibilityOf(element));
    }

    public void waitElementToBeClickable(WebElement element) {
        webDriverWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitPresenceOfElementLocated(By by) {
        webDriverWait().until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void switchToParentWindow() {
        Set<String> winHandles = getDriver().getWindowHandles();
        for (String wHandle : winHandles) {
            getDriver().switchTo().window(wHandle);
            break;
        }
    }

    public void switchToLastWindow() {
        Set<String> winHandles = getDriver().getWindowHandles();
        for (String wHandle : winHandles) {
            getDriver().switchTo().window(wHandle);
        }
    }
    public String getWindowTitle(){
        return getDriver().getWindowHandle();
    }
    public void SwitchToParentWindow(String windowName){
        getDriver().switchTo().window(windowName);
    }

    public void highLighterMethod(WebDriver driver, WebElement element){

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 2px solid blue;');", element);
    }

    public boolean isElementDisplayed(List<WebElement> elements) {

        return !elements.isEmpty();
    }

    public boolean isElementDisplayed(WebElement element){
        boolean flag;
        try {
            setImplicit(10);
            //scrollToElement(element);
            highLighterMethod(getDriver(), element);
            element.isDisplayed();
            flag = true;
        }catch (Exception e){
            flag = false;
        }
        //logger.info("Is element " + element + " displayed - "+flag);
        return flag;
    }

    public boolean isElementDisplayedWithoutScroll(WebElement element){

        boolean flag;
        try {

            waitFor(1000);
            setImplicit(10);
            highLighterMethod(getDriver(), element);
            element.isDisplayed();
            flag = true;
        }catch (Exception e){
            flag = false;
        }
        logger.info("Is element " + element + " displayed - "+flag);
        return flag;
    }

    public boolean isElementEnabled(WebElement element){

        boolean flag;
        try {

            waitFor(1000);
            setImplicit(10);
            scrollToElement(element);
            highLighterMethod(getDriver(), element);
            element.isEnabled();
            flag = true;
        }catch (Exception e){

            setImplicit(implicit);
            flag = false;
        }
        logger.info("Is element " + element + " enabled - "+flag);
        return flag;
    }

    public boolean isEnabled(WebElement element){

        logger.info("Is element " + element + "enabled - "+element.isEnabled());
        return element.isEnabled();
    }

    public String getAttributeValue(WebElement element, String attributeName){
        waitVisibilityOfElement(element);
        logger.info("Attribute Value - "+element.getAttribute(attributeName));
        return element.getAttribute(attributeName);
    }

    public void takeSnap(Scenario scenario) throws IOException {

        String scrname = scenario.getId().replace(";","").replace("-","");
        File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        org.apache.commons.io.FileUtils.copyFile(scrFile,
                new File(getCurrentDir() + "/target/FailureScreenShots/" + scrname + ".png"));
        System.out.println("GenericWrappers.takeSnap() -"+scrname);
        System.out.println("inside screenshot");
        final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        scenario.embed(screenshot, "image/png"); // ... and embed it in the report.
        Reporter.addScreenCaptureFromPath(
                "./FailureScreenShots/"+scrname+".png");
        // getDriver().quit();
    }

    public static String getCurrentDir(){
        String currentDir = System.getProperty("user.dir");
        currentDir = currentDir.replace('\\', '/');
        return currentDir;
    }

    public void scrollToElement(WebElement element) {
        waitFor(5000);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    public void scrollByPixel() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,300)", "");

    }

    public void scrollToPixel() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,700)", "");

    }

    public void scrollByPixelTop() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,-400)", "");
    }
    public void setZoomLevel(double zoomLevel) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("document.body.style.zoom='" + zoomLevel + "'");
    }
    public String getInnerText(WebElement elem) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String text = (String) js.executeScript("return arguments[0].value", elem);
        return text;
    }

    public Select selectDropdown(WebElement element){

        select = new Select(element);
        return select;
    }

    public void selectByIndex(WebElement element, int index) {
        selectDropdown(element).selectByIndex(index);
    }

    public void SelectByValue(WebElement element, String value) {
        selectDropdown(element).selectByValue(value);
    }

    public void SelectByVisibleText(WebElement element, String text) {
        selectDropdown(element).selectByVisibleText(text);
    }





    public String getPageTitle() {

        waitFor(2000);
        return getDriver().getTitle();
    }

}
