package com.automatization.home;

import com.automatization.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void openHomePage() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public void clickMyAccount() {
        driver.findElement(By.xpath("//a[@title='My Account']")).click();
    }

    public String generateRandomUser(){return "test" + System.currentTimeMillis(); }

    public void clickRegister() {
        driver.findElement(By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']//a[contains(text(),'Register')]")).click();
    }
}
