package com.automatization;

import com.automatization.accountDetail.AccountDetail;
import com.automatization.accountOverview.AccountOverviewPage;
import com.automatization.home.HomePage;
import com.automatization.login.LoginPage;
import com.automatization.openAccount.OpenNewAccountPage;
import com.automatization.register.RegisterPage;
import com.automatization.transferFounds.TransferFundsPage;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RestAssuredFinalTesting {

    private  static String youUserLogin="jgoen22";
    private  static String yourPassUser="jgoen22";
    private static  String accountType="1";
    private static String accountId;
    private static String secondAccountId;
    private  static String customerId;


    @BeforeEach
    void initApp() throws IOException {

        // SET CUSTOMER LOGIN
        Response response = given()
                .when()
                .get("https://parabank.parasoft.com/parabank/services/bank/login/{username}/{password}",youUserLogin,yourPassUser);

        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Dirigaje a la siguente pagina https://parabank.parasoft.com/parabank/register.htm  para crear una nueva cuenta y coloque su usuario y pass en las variables anteriores");
        }

        XmlPath xmlPath = response.xmlPath();
        customerId = xmlPath.getString("customer.id");

        // GET CUSTOMER ACCOUNT
        Response responseAccount = given()
                .when()
                .get("https://parabank.parasoft.com/parabank/services/bank/customers/{customerId}/accounts",customerId);
        XmlPath xmlPathAccount = responseAccount.xmlPath();

        if (responseAccount.getStatusCode() != 200) {
            throw new RuntimeException("No se pudo Obtener el accountId del cliente, esto puede deberse a que el usuario fue eliminado intentelo de nuevo creando el usuario,, https://parabank.parasoft.com/parabank/register.htm");
        }
        accountId = xmlPathAccount.getString("accounts.account[0].id");

        // Generate SecondAccount ID
        Response responseNewAccount = given()
                .queryParam("customerId",customerId)
                .queryParam("newAccountType",accountType)
                .queryParam("fromAccountId",accountId)
                .when().post("https://parabank.parasoft.com/parabank/services/bank/createAccount");

        if (responseNewAccount.getStatusCode() != 200) {
            throw new RuntimeException("No fue posible generar el segundo accountId para el test, esto se debe a que el usuario pudo ser eliminado registrese de nuevo aqui: https://parabank.parasoft.com/parabank/register.htm");
        }
        XmlPath xmlPathNewAccount = responseNewAccount.xmlPath();
        secondAccountId = xmlPathNewAccount.getString("account.id");
        System.out.println("segundo "+ secondAccountId);
    }


    @Test
    void givenOverviewPageWhereClientIsLogged() {

        RestAssured.baseURI = "https://parabank.parasoft.com";

        Response loginResponse = given()
                .contentType(ContentType.URLENC)
                .formParam("username", "jgoen22")
                .formParam("password", "jgoen22")
                .when()
                .post("/parabank/login.htm")
                .then()
                .statusCode(302)
                .extract().response(); // Captura la respuesta para obtener las cookies

        // Obtiene las cookies de la respuesta de inicio de sesión
        String cookies =loginResponse.getDetailedCookie("JSESSIONID").getValue(); // Reemplaza "nombre_cookie" con el nombre de la cookie que necesitas

        System.out.println("Cookie "+cookies);
        given()
                .cookie("JSESSIONID", cookies)
                .when()
                .get("/parabank/overview.htm")
                .then()
                .statusCode(200);
    }

    @Test
    void whenCustomerAccessToRegisterPageShouldThenReturnOkStatus() {
        given()
                .when().get("https://parabank.parasoft.com/parabank/register.htm")
                .then()
                .assertThat()
                .statusCode(equalTo(200));

    }

    @Test
    void whenCustoemrIdAndAccountTypeAndAccounIdIsNotNUllShouldThenReturn200Status() {
        given()
                .queryParam("customerId",customerId)
                .queryParam("newAccountType",accountType)
                .queryParam("fromAccountId",accountId)
                .when().post("https://parabank.parasoft.com/parabank/services/bank/createAccount")
                .then()
                .assertThat()
                .statusCode(equalTo(200));

    }

    @Test
    void transferFunds() {
        given()
                .queryParam("fromAccountId",accountId)
                .queryParam("toAccountId",secondAccountId)
                .queryParam("amount","0")
                .when().post("https://parabank.parasoft.com/parabank/services/bank/transfer")
                .then()
                .assertThat()
                .statusCode(equalTo(200));
    }

    @Test
    void accountDetail() {
        given()
                .when().get("https://parabank.parasoft.com/parabank/services/bank/accounts/{accountId}/transactions/month/{month}/type/{type}",accountId,"All","All")
                .then()
                .assertThat()
                .statusCode(equalTo(200));

    }


}
