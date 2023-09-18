
# Parcial  Testing

Tenga en cuenta lo siguente en ela rchivo


ParcialFinalTesting.java:

esta todo lo relaciado a pruebas de selenium para el front end

RestAssuredFinalTesting:

al igual que el anterior este archivo es exclusivo solo para pruebas con assured para 
back



# Recomendaciones

# Para RestAssuredFinalTesting (prueba de Back End)

usted va a encontrar 3 variables definidas
```
    private  static String youUserLogin="jgoen22";
    private  static String yourPassUser="jgoen22";
    private static  String accountType="1";
```
en las 2 primeras variable youUserLogin y yourPassUser, son sus credenciales de inicio 
de sesion, por favor use esta url para generar un usuario y cambiar dichos valores si desea
o simplemente genere una cuenta con dichos valores

https://parabank.parasoft.com/parabank/register.htm

el codigo en dicho archivo  esta automatizado en el beforeEach encontrara
varios llamados:

```
           // SET CUSTOMER LOGIN
        Response response = given()
                .when()
                .get("https://parabank.parasoft.com/parabank/services/bank/login/{username}/{password}",youUserLogin,yourPassUser);

        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Dirigaje a la siguente pagina https://parabank.parasoft.com/parabank/register.htm  para crear una nueva cuenta y coloque su usuario y pass en las variables anteriores");
        }

        XmlPath xmlPath = response.xmlPath();
        customerId = xmlPath.getString("customer.id");
```

El primer llamado tomara sus variables  youUserLogin y yourPassUser , para iniciar login
y obtener el customerId el cual se le asignara una variable que se usa en otros test

```
        // GET CUSTOMER ACCOUNT
        Response responseAccount = given()
                .when()
                .get("https://parabank.parasoft.com/parabank/services/bank/customers/{customerId}/accounts",customerId);
        XmlPath xmlPathAccount = responseAccount.xmlPath();

        if (responseAccount.getStatusCode() != 200) {
            throw new RuntimeException("No se pudo Obtener el accountId del cliente, esto puede deberse a que el usuario fue eliminado intentelo de nuevo creando el usuario,, https://parabank.parasoft.com/parabank/register.htm");
        }
        accountId = xmlPathAccount.getString("accounts.account[0].id");
```
este llamado obtendra la cuenta principal del usuario una vez fue creado y este se 
asignara en una varaible accountId para su uso posterior en otros test


```
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
```
en este llamado se va a generar una nueva cuenta para asignarle a la variable secondAccountId
esto con el fin de tener 2 accountId para la prueba de trasnferencia.

teniendo en cuenta lo anterior usted solo debe configurar el usuario de inicio de sesion
mencionado en la primera parte  y ejecutar los test, ya que esto esta configurado
para funcionar automatizamente.


# ParcialFinalTesting (Pruebas de Front end)

cuando entre al archivo  ParcialFinalTesting se encontrara con la siguente
variable

```
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ParcialFinalTesting
```
como lo puede observar los test tienen una anotacion @Order() le cual indicara
el orden de ejecucion. por favor no cambie el orden de ejecucion ya que  puede causar error en los test
debido  a que todos se benefician del inicio de sesion y de algunos valores.


es recomendable ejecutar la clase test para ejecutar todos los test al mismo tiempo
si usted desea de igual manera ejecutar test por test individualmente asegurese de lo siguente:

# Recomendacion para ejecutar Test Individualmente

```
    private static String userToLogin;
    private static String accountId;
    private final String defaultPassword = "jgoen22";
```

1# genere un un usuario  en  https://parabank.parasoft.com/parabank/register.htm 
con su username y pass, en la variable  **userToLogin**  asignale el valor del
usuario que usted creo como se hace  en la variable defaultPassword.
si su password es distinta a  jgoen22, por favor cambielo


2# la variable accountId es la variable de la cuenta nueva que se obtiene en 
la siguente pagina  https://parabank.parasoft.com/parabank/openaccount.htm
al igual que userToLogin usted debe setearla como en el ejemplo de defaultPassword

nota: si asignarle el valor a dichas variable no le funciona el test
asegurese que el user no fue borrado y en caso que no dirijase al test
y asigle el valor manualmente donde se llamen dichas variables

