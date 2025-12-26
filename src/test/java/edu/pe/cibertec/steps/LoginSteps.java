package edu.pe.cibertec.steps;

import edu.pe.cibertec.config.AppiumConfig;
import edu.pe.cibertec.pages.HomePage;
import edu.pe.cibertec.pages.LoginPage;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class LoginSteps {

    private static AndroidDriver driver;
    private static LoginPage loginPage;
    private static HomePage homePage;

    @Before
    public void setUp(){
        System.out.println("INICIANDO DRIVER y PAGE OBJECTS");
        driver = AppiumConfig.getDriver();
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        System.out.println("DRIVER INICIADO: " + (driver!=null));
        System.out.println("LOGIN PAGE INICIADO:" + (loginPage!=null));
    }

    @After
    public void tearDown(){
        System.out.println("CERRANDO DRIVER");
        AppiumConfig.quitDriver();
        driver = null;
        loginPage = null;
        homePage = null;
    }

    @Given("que el usuario esta en la pantalla de login")
    public void queElUsuarioEstaEnLaPantallaDeLogin(){
        System.out.println("USUARIO EN PANTALLA LOGIN");
    }

    @When("ingresa el email {string}")
    public void ingresaElEmail(String email){
        loginPage.enterEmail(email);
    }

    @And("ingresa el password {string}")
    public void ingresElPassword(String password){
        loginPage.enterPassword(password);
    }

    @And("hacer clic en el boton login")
    public void haceClicEnElBotonLogin(){
        loginPage.clickLoginButton();
    }

    @Then("deberia acceder a la pantalla principal")
    public void deberiaAccederALaPantallaPrincipal(){
        System.out.println("VALIDANDO ACCESO PANTALLA PRINCIPAL");
        try {
            Thread.sleep(2000);
            assertTrue(homePage.isHomePageDisplayed(),
                    "No se pudo acceder a la pantalla principal");
            System.out.println("✅ Acceso a pantalla principal validado");
        } catch (Exception e) {
            fail("Error al validar acceso a pantalla principal: " + e.getMessage());
        }
    }

    @Then("deberia ver un mensaje de error")
    public void deberiaVerUnMensajeDeError(){
        System.out.println("VALIDANDO MENSAJE DE ERROR");
        try {
            Thread.sleep(1500); 
            assertFalse(homePage.isHomePageDisplayed(),
                    "No debería haber accedido a la pantalla principal");
            assertTrue(loginPage.isErrorMessageDisplayed(),
                    "No se muestra mensaje de error");
            System.out.println("Mensaje de error validado");
        } catch (Exception e) {
            fail("Error al validar mensaje de error: " + e.getMessage());
        }
    }
}