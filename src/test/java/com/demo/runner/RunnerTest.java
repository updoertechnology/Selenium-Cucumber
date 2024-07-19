package com.demo.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.Reporter;

@RunWith(Cucumber.class)
@CucumberOptions(
        //strict = (true),
        features = {"./src/test/resources/"},
        tags = "@Test",
        glue = {""},
        plugin = {"pretty", "html:target/cucumber/report.html", "json:target/cucumber/report.json"},
      //  plugin = { "com.vimalselvam.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/advanced-report.html/cucumber-html-reports/report.html"},
        //monochrome = true,
        dryRun = false)

public class RunnerTest {
    @AfterClass
    public static void setup() {
        try{
            Reporter.loadXMLConfig("./src/main/resources/extent-config.xml");
            Reporter.setSystemInfo("user", System.getProperty("user.name"));
            Reporter.setSystemInfo("os", "Mac");
            Reporter.setTestRunnerOutput("Sample test runner output message");
        }catch(NullPointerException e){
        }}
}
