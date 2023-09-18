package com.automatization.accountDetail;

import com.automatization.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class AccountDetail extends BasePage {


    public AccountDetail(WebDriver driver) {
        super(driver);
    }



    public void pressButtonGo() throws InterruptedException {
        driver.findElement(By.xpath("//input[@value='Go']")).click();
        Thread.sleep(1000);
    }

    public String getTitleAccountDetail() throws InterruptedException {
        return driver.findElement(By.xpath("//h1[normalize-space()='Account Details']")).getText();
    }


}
