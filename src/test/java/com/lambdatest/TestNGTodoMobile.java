package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNGTodoMobile {

    private RemoteWebDriver driver1;
    private RemoteWebDriver driver2;
    private String Status = "failed";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "Your LT Username" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "Your LT AccessKey" : System.getenv("LT_ACCESS_KEY");
        ;
        String hub = "@hub.lambdatest.com/wd/hub";

        DesiredCapabilities caps1 = new DesiredCapabilities();
        caps1.setCapability("platformName", "android");
        caps1.setCapability("deviceName", "Pixel 4a");
        caps1.setCapability("platformVersion", "11");
        // caps.setCapability("isRealMobile", true);
        caps1.setCapability("build", "TestNG With Java");
        caps1.setCapability("name", m.getName() + this.getClass().getName());
        caps1.setCapability("plugin", "git-testng");


        DesiredCapabilities caps2 = new DesiredCapabilities();
        caps2.setCapability("platform", "MacOS Catalina");
        caps2.setCapability("browserName", "Safari");
        caps2.setCapability("version", "latest");
        caps2.setCapability("build", "TestNG With Java");
        caps2.setCapability("name", m.getName() + " - " + this.getClass().getName());
        caps2.setCapability("plugin", "git-testng");


        driver1 = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps1);
        driver2 = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps2);
    }

    @Test
    public void basicTest() throws InterruptedException {
        String spanText;
        System.out.println("Loading Url");
        Thread.sleep(100);
        driver1.get("https://lambdatest.github.io/sample-todo-app/");

        driver2.get("https://google.com");
        Thread.sleep(300);


        spanText = driver1.findElementByXPath("/html/body/div/div/h2").getText();
        Assert.assertEquals("LambdaTest Sample App", spanText);
        Status = "passed";
        Thread.sleep(800);

        System.out.println("TestFinished");
        System.out.println(spanText);


        driver2.get("https://google.com");
        Thread.sleep(300);

        System.out.println("Entering Text");
        driver2.findElement(By.id("APjFqb")).sendKeys(spanText);

        driver2.findElement(By.xpath("(//input[@class='gNO89b'])[1]")).click();

        Thread.sleep(150);

        System.out.println("TestFinished");


    }

    @AfterMethod
    public void tearDown() {
        driver1.executeScript("lambda-status=" + Status);
        driver2.executeScript("lambda-status=" + Status);
        driver1.quit();
        driver2.quit();

    }

}
