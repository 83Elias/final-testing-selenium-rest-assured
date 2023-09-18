package com.automatization.openAccount;

import com.automatization.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class OpenNewAccountPage extends BasePage {


    public OpenNewAccountPage(WebDriver driver) {
        super(driver);
    }


    public void pressButtonOpenNewAccount() throws InterruptedException {
        driver.findElement(By.xpath("//a[normalize-space()='Open New Account']")).click();
        Thread.sleep(1000);
    }

    public void selectTypeAccount(String accountType) throws InterruptedException {
        WebElement checkboxTypeAccount = driver.findElement(By.id("type"));
        Select select = new Select(checkboxTypeAccount);
        select.selectByVisibleText(accountType);
    }

    public void createNewAccount() throws InterruptedException {
        driver.findElement(By.xpath("//input[@value='Open New Account']")).click();
        Thread.sleep(1000);
    }

    public String getAccountId() throws InterruptedException {
        return driver.findElement(By.id("newAccountId")).getText();
    }

    public String getSuccessMessageOpenNewAccount() throws InterruptedException {
        return driver.findElement(By.xpath("//p[normalize-space()='Congratulations, your account is now open.']")).getText();
    }


    public void setFieldLoginForm(String username, String pass) throws InterruptedException {
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passField = driver.findElement(By.name("password"));

        usernameField.sendKeys(username);
        passField.sendKeys(pass);
    }

}
