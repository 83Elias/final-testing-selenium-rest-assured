package com.automatization.register;

import com.automatization.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegisterPage extends BasePage {


    public RegisterPage(WebDriver driver) {
        super(driver);
    }


    public void setFieldsFormRegistration(
            String firstName, String lastName, String addreess, String city, String state,String zipCode,
        String phone,String ssn,String username,String pass,String repeatPass){

        WebElement customerFirstName = driver.findElement(By.id("customer.firstName"));
        WebElement customerLastName = driver.findElement(By.id("customer.lastName"));
        WebElement customerAddress= driver.findElement(By.id("customer.address.street"));
        WebElement customerCity = driver.findElement(By.id("customer.address.city"));
        WebElement customerState = driver.findElement(By.id("customer.address.state"));
        WebElement customerZipCode = driver.findElement(By.id("customer.address.zipCode"));
        WebElement customerPhone = driver.findElement(By.id("customer.phoneNumber"));
        WebElement customerSsn = driver.findElement(By.id("customer.ssn"));
        WebElement customerUsername = driver.findElement(By.id("customer.username"));
        WebElement customerPass = driver.findElement(By.id("customer.password"));
        WebElement customerRepeatPass = driver.findElement(By.id("repeatedPassword"));

        customerFirstName.sendKeys(firstName);
        customerLastName.sendKeys(lastName);
        customerAddress.sendKeys(addreess);
        customerCity.sendKeys(city);
        customerState.sendKeys(state);
        customerZipCode.sendKeys(zipCode);
        customerPhone.sendKeys(phone);
        customerSsn.sendKeys(ssn);
        customerUsername.sendKeys(username);
        customerPass.sendKeys(pass);
        customerRepeatPass.sendKeys(repeatPass);

    }

    public void pressButtonRegister() {
        driver.findElement(By.xpath("//a[normalize-space()='Register']")).click();
    }

    public void sendDataForm() throws InterruptedException {
        driver.findElement(By.xpath("//input[@value='Register']")).click();
        Thread.sleep(2000);
    }

    public String getSuccessMessage() {
        return driver.findElement(By.xpath("//p[contains(text(),'Your account was created successfully. You are now')]")).getText();
    }

}
