package com.saiit.SeliniumLoginJenkins;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SauceDemoLoginTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void validLoginTest() {

        driver.findElement(By.id("user-name")).sendKeys("standard_user");

        driver.findElement(By.id("password")).sendKeys("secret_sauce");

        driver.findElement(By.id("login-button")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.urlContains("inventory"));

        String url = driver.getCurrentUrl();

        Assert.assertTrue(url.contains("inventory"));

        System.out.println("Valid Login Test Passed");
    }

    @Test
    public void invalidLoginTest() {

        driver.findElement(By.id("user-name")).sendKeys("standard_user");

        driver.findElement(By.id("password")).sendKeys("wrongpassword");

        driver.findElement(By.id("login-button")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement error = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//h3[contains(text(),'Epic sadface')]")));

        String errorMessage = error.getText();

        Assert.assertTrue(errorMessage.contains("Username and password do not match"));

        System.out.println("Invalid Login Test Passed");
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }

    }
}