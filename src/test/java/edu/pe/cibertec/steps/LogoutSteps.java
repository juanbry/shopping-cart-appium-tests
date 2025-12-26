package edu.pe.cibertec.steps;

import edu.pe.cibertec.config.AppiumConfig;
import edu.pe.cibertec.pages.HomePage;
import edu.pe.cibertec.pages.LoginPage;
import edu.pe.cibertec.pages.ProfilePage;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class LogoutSteps {

    private AndroidDriver driver;
    private ProfilePage profilePage;
    private HomePage homePage;
    private LoginPage loginPage;

    @Before("@logout")
    public void setUp() {
        System.out.println("=== Iniciando prueba de Logout ===");
        driver = AppiumConfig.getDriver();
        profilePage = new ProfilePage(driver);
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        System.out.println("Driver inicializado correctamente para Logout");
    }

    @After("@logout")
    public void tearDown() {
        System.out.println("=== Finalizando prueba de Logout ===");
        if (driver != null) {
            AppiumConfig.quitDriver();
            System.out.println("Driver cerrado correctamente");
        }
    }


    @When("hace clic en el menu de usuario")
    public void haceClicEnElMenuDeUsuario() {
        System.out.println("Step: Haciendo clic en el menú de usuario/perfil");

        try {
            profilePage.clickProfileTab();
            Thread.sleep(1500);

            assertTrue(profilePage.isProfilePageDisplayed(),
                    "No se pudo acceder a la página de perfil");

            System.out.println("✅ Página de perfil abierta correctamente");
        } catch (Exception e) {
            fail("Error al acceder al menú de usuario: " + e.getMessage());
        }
    }

    @When("hace clic en cerrar sesion")
    public void haceClicEnCerrarSesion() {
        System.out.println("Step: Haciendo clic en cerrar sesión");
        System.out.println("===========================================");

        try {
            System.out.println("Verificando si el botón de logout es visible...");
            boolean isVisible = profilePage.isLogoutButtonDisplayed();
            System.out.println("Botón de logout visible: " + isVisible);

            assertTrue(isVisible, "El botón de cerrar sesión no está visible");

            System.out.println("Llamando a profilePage.clickLogoutButton()...");
            profilePage.clickLogoutButton();

            System.out.println("Esperando 2 segundos después del click...");
            Thread.sleep(2000);

            System.out.println("Click en cerrar sesión completado");
            System.out.println("===========================================");
        } catch (Exception e) {
            System.err.println("ERROR en haceClicEnCerrarSesion: " + e.getMessage());
            e.printStackTrace();
            fail("Error al hacer clic en cerrar sesión: " + e.getMessage());
        }
    }

    @Then("deberia regresar a la pantalla de login")
    public void deberiaRegresarALaPantallaDeLogin() {
        System.out.println("Step: Verificando regreso a la pantalla de login");

        try {
            Thread.sleep(1500);

            assertFalse(homePage.isHomePageDisplayed(),
                    "No se cerró la sesión correctamente, aún estamos en la página principal");

            assertTrue(loginPage.isLoginPageDisplayed(),
                    "No se regresó a la pantalla de login");

            System.out.println("Validación exitosa: Se regresó a la pantalla de login");
            System.out.println("Logout completado correctamente");
        } catch (Exception e) {
            fail("Error al verificar la pantalla de login: " + e.getMessage());
        }
    }
}

