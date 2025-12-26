package edu.pe.cibertec.steps;

import edu.pe.cibertec.config.AppiumConfig;
import edu.pe.cibertec.pages.HomePage;
import edu.pe.cibertec.pages.LoginPage;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.Given;

import static org.junit.jupiter.api.Assertions.*;

public class CommonSteps {

    @Given("que el usuario esta logueado en la aplicacion")
    public void queElUsuarioEstaLogueadoEnLaAplicacion() {
        System.out.println("🔄 Step: Usuario iniciando sesión en la aplicación");

        try {
            AndroidDriver driver = AppiumConfig.getDriver();
            LoginPage loginPage = new LoginPage(driver);
            HomePage homePage = new HomePage(driver);

            String email = "admin@test.com";
            String password = "123456";

            System.out.println("📝 Ingresando credenciales...");
            loginPage.login(email, password);

            System.out.println("⏳ Esperando carga de la página principal...");
            Thread.sleep(3000);

            boolean isHomeDisplayed = homePage.isHomePageDisplayed();
            System.out.println("🏠 ¿Página de inicio visible? " + isHomeDisplayed);

            assertTrue(isHomeDisplayed,
                    "El usuario no pudo iniciar sesión correctamente");

            System.out.println("✅ Login exitoso verificado");
        } catch (Exception e) {
            System.out.println("❌ Error en login: " + e.getMessage());
            e.printStackTrace();
            fail("Error al realizar login: " + e.getMessage());
        }
    }
}