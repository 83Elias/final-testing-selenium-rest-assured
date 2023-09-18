package com.automatization.transferFounds;

import com.automatization.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class TransferFundsPage extends BasePage {


    public TransferFundsPage(WebDriver driver) {
        super(driver);
    }


    public void pressButtonTransferFunds() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.xpath("//a[normalize-space()='Transfer Funds']")).click();
        Thread.sleep(1000);
    }

    public void setFieldsTransferFundsByForm(String amount,String accountId) throws InterruptedException {
        WebElement amountToTranfer = driver.findElement(By.id("amount"));
        amountToTranfer.sendKeys(amount);

        WebElement checkboxToAccount = driver.findElement(By.id("toAccountId"));
        Select select = new Select(checkboxToAccount);
        select.selectByVisibleText(accountId);
    }

    public void pressButtonTransfer() throws InterruptedException {
        driver.findElement(By.xpath("//input[@value='Transfer']")).click();
        Thread.sleep(1000);
    }

    public String getSuccessMessage() throws InterruptedException {
        return driver.findElement(By.xpath("//h1[@class='title']")).getText();
    }


}
