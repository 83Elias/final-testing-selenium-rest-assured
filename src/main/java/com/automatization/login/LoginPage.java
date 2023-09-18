package com.automatization.login;

import com.automatization.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {


    public LoginPage(WebDriver driver) {
        super(driver);
    }


    public void pressButtonLogin() throws InterruptedException {
        driver.findElement(By.xpath("//input[@value='Log In']")).click();
        Thread.sleep(1000);
    }

    public void setFieldLoginForm(String username, String pass) throws InterruptedException {
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passField = driver.findElement(By.name("password"));

        usernameField.sendKeys(username);
        passField.sendKeys(pass);
    }

}
