package com.automatization.accountOverview;

import com.automatization.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class AccountOverviewPage extends BasePage {


    public AccountOverviewPage(WebDriver driver) {
        super(driver);
    }


    public void pressButtonOpenAccountOverView() throws InterruptedException {
        driver.findElement(By.xpath("//a[normalize-space()='Accounts Overview']")).click();
        Thread.sleep(1000);
    }

    public void openDetailAccount(String accountId) throws InterruptedException {
        driver.findElement(By.xpath("//td/a[contains(@href, 'activity.htm?id=" + accountId + "')]")).click();
        Thread.sleep(2000);
    }



    public String getAccountOverViewInformativeText() throws InterruptedException {
        return driver.findElement(By.xpath("//td[contains(text(),'*Balance includes deposits that may be subject to ')]")).getText();
    }




}
