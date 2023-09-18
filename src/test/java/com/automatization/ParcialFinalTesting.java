package com.automatization;


import com.automatization.accountDetail.AccountDetail;
import com.automatization.accountOverview.AccountOverviewPage;
import com.automatization.home.HomePage;
import com.automatization.login.LoginPage;
import com.automatization.openAccount.OpenNewAccountPage;
import com.automatization.register.RegisterPage;
import com.automatization.transferFounds.TransferFundsPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.time.Duration;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ParcialFinalTesting {

    private WebDriver driver;

    private HomePage homePage;

    private RegisterPage registerPage;

    private OpenNewAccountPage openNewAccountPage;

    private AccountOverviewPage accountOverviewPage;

    private TransferFundsPage transferFundsPage;

    private AccountDetail accountDetail;

    private LoginPage loginPage;
    private static String userToLogin;

    private static String accountId;
    private final String defaultPassword = "jgoen22";

    @BeforeEach
    void initApp() throws IOException {
        Resource chromeDriverResource = new ClassPathResource("chromedriverv16.exe");
        File chromeDriverFile = chromeDriverResource.getFile();

        System.setProperty("webdriver.chrome.driver", chromeDriverFile.getAbsolutePath());
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        driver = new ChromeDriver();
        registerPage=new RegisterPage(driver);
        homePage = new HomePage(driver);
        openNewAccountPage = new OpenNewAccountPage(driver);
        loginPage= new LoginPage(driver);
        accountOverviewPage= new AccountOverviewPage(driver);
        transferFundsPage= new TransferFundsPage(driver);
        accountDetail= new AccountDetail(driver);
    }


    @Test
    @Order(1)
    void registerNewCustomer() throws InterruptedException {
        homePage.openHomePage();

        String title = homePage.getTitle();
        String username = homePage.generateRandomUser();
        userToLogin = username;

        final String EXPECT_TITLE = "ParaBank | Welcome | Online Banking";
        final String EXPECT_MESSAGE = "Your account was created successfully. You are now logged in.";
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        registerPage.pressButtonRegister();
        registerPage.setFieldsFormRegistration(
                "Jhon", "Doe", "CR 34", "polonia", "kurbenist", "090", "48859932", "48859932", username, defaultPassword, defaultPassword);

        System.out.println("creeat user: " + userToLogin);
        registerPage.sendDataForm();

        final String message = registerPage.getSuccessMessage();
        Thread.sleep(2000);

        Assertions.assertEquals(EXPECT_TITLE, title);
        Assertions.assertEquals(EXPECT_MESSAGE, message);
    }

    @Test
    @Order(2)
    void openNewAccount() throws InterruptedException {
        homePage.openHomePage();
        String title = homePage.getTitle();

        final String EXPECT_TITLE = "ParaBank | Welcome | Online Banking";
        final String EXPECT_MESSAGE = "Congratulations, your account is now open.";
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        loginPage.setFieldLoginForm(userToLogin,defaultPassword);
        loginPage.pressButtonLogin();

        System.out.println("login with user " + userToLogin);

        openNewAccountPage.pressButtonOpenNewAccount();
        openNewAccountPage.selectTypeAccount("SAVINGS");
        openNewAccountPage.createNewAccount();
        accountId = openNewAccountPage.getAccountId();

        final String message = openNewAccountPage.getSuccessMessageOpenNewAccount();

        Assertions.assertEquals(EXPECT_TITLE, title);
        Assertions.assertEquals(EXPECT_MESSAGE, message);
    }

    @Test
    @Order(3)
    void accountsOverview() throws InterruptedException {
        homePage.openHomePage();
        String title = homePage.getTitle();

        final String EXPECT_TITLE = "ParaBank | Welcome | Online Banking";
        final String EXPECT_MESSAGE = "*Balance includes deposits that may be subject to holds";
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        loginPage.setFieldLoginForm(userToLogin,defaultPassword);
        loginPage.pressButtonLogin();

        System.out.println("login with user " + userToLogin);

        accountOverviewPage.pressButtonOpenAccountOverView();

        final String message = accountOverviewPage.getAccountOverViewInformativeText();

        Assertions.assertEquals(EXPECT_TITLE, title);
        Assertions.assertEquals(EXPECT_MESSAGE, message);
    }

    @Test
    @Order(4)
    void transferFunds() throws InterruptedException {
        homePage.openHomePage();
        String title = homePage.getTitle();

        final String EXPECT_TITLE = "ParaBank | Welcome | Online Banking";
        final String EXPECT_MESSAGE = "Transfer Complete!";
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        loginPage.setFieldLoginForm(userToLogin,defaultPassword);
        loginPage.pressButtonLogin();

        System.out.println("login with user " + userToLogin);

        transferFundsPage.pressButtonTransferFunds();
        transferFundsPage.setFieldsTransferFundsByForm("300",accountId);
        transferFundsPage.pressButtonTransfer();
        final String message = transferFundsPage.getSuccessMessage();

        Assertions.assertEquals(EXPECT_TITLE, title);
        Assertions.assertEquals(EXPECT_MESSAGE, message);
    }

    @Test
    @Order(5)
    void OverViewTransfers() throws InterruptedException {
        homePage.openHomePage();
        String title = homePage.getTitle();

        final String EXPECT_TITLE = "ParaBank | Welcome | Online Banking";
        final String EXPECT_MESSAGE = "*Balance includes deposits that may be subject to holds";
        final String EXPECT_TITLE_WINDOWS = "Account Details";
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        loginPage.setFieldLoginForm(userToLogin,defaultPassword);
        loginPage.pressButtonLogin();

        System.out.println("login with user " + userToLogin);

        final String message = accountOverviewPage.getAccountOverViewInformativeText();
        accountOverviewPage.openDetailAccount(accountId);

        final String message_windows = accountDetail.getTitleAccountDetail();

        accountDetail.pressButtonGo();

        Assertions.assertEquals(EXPECT_TITLE, title);
        Assertions.assertEquals(EXPECT_MESSAGE, message);
        Assertions.assertEquals(EXPECT_TITLE_WINDOWS, message_windows);
    }


    @AfterEach
    void cleanWindows() {
        driver.quit();
    }

}
