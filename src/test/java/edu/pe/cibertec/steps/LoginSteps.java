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
        driver = AppiumConfig.getDriver();
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        assertNotNull(driver, "El driver no fue inicializado");
        assertNotNull(loginPage, "LoginPage no fue inicializada");
        assertNotNull(homePage, "HomePage no fue inicializada");
    }

    @After
    public void tearDown(){
        AppiumConfig.quitDriver();
        driver = null;
        loginPage = null;
        homePage = null;
    }

    @Given("que el usuario esta en la pantalla de login")
    public void queElUsuarioEstaEnLaPantallaDeLogin(){
        assertTrue(loginPage.isLoginPageDisplayed(), "No se está en la pantalla de login");
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
        try {
            Thread.sleep(2000); // Esperar a que cargue la pantalla
            assertTrue(homePage.isHomePageDisplayed(),
                    "No se pudo acceder a la pantalla principal");
        } catch (Exception e) {
            fail("Error al validar acceso a pantalla principal: " + e.getMessage());
        }
    }

    @Then("deberia ver un mensaje de error")
    public void deberiaVerUnMensajeDeError(){
        try {
            Thread.sleep(1500); // Esperar a que aparezca el error
            assertFalse(homePage.isHomePageDisplayed(),
                    "No debería haber accedido a la pantalla principal");
            assertTrue(loginPage.isErrorMessageDisplayed(),
                    "No se muestra mensaje de error");
        } catch (Exception e) {
            fail("Error al validar mensaje de error: " + e.getMessage());
        }
    }
}