package edu.pe.cibertec.steps;

import edu.pe.cibertec.config.AppiumConfig;
import edu.pe.cibertec.pages.CartPage;
import edu.pe.cibertec.pages.CheckoutPage;
import edu.pe.cibertec.pages.HomePage;
import edu.pe.cibertec.pages.LoginPage;
import edu.pe.cibertec.pages.ProductDetailPage;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutSteps {

    private AndroidDriver driver;
    private CheckoutPage checkoutPage;
    private CartPage cartPage;
    private HomePage homePage;
    private LoginPage loginPage;
    private ProductDetailPage productDetailPage;

    // HOOKS

    @Before("@checkout")
    public void setUp() {
        System.out.println("=== Iniciando prueba de Checkout ===");
        driver = AppiumConfig.getDriver();
        checkoutPage = new CheckoutPage(driver);
        cartPage = new CartPage(driver);
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        productDetailPage = new ProductDetailPage(driver);
        System.out.println("Driver inicializado correctamente para Checkout");
    }

    @After("@checkout")
    public void tearDown() {
        System.out.println("=== Finalizando prueba de Checkout ===");
        if (driver != null) {
            try {
                System.out.println("Estado final de la aplicación capturado");
            } catch (Exception e) {
                System.err.println("Error al capturar estado final: " + e.getMessage());
            }
            AppiumConfig.quitDriver();
            System.out.println("Driver cerrado correctamente");
        }
    }


    @Given("que el usuario tiene productos en el carrito")
    public void queElUsuarioTieneProductosEnElCarrito() {
        System.out.println("Step: Usuario debe tener productos en el carrito");

        try {
            if (!homePage.isHomePageDisplayed()) {
                loginPage.login("admin@test.com", "123456");
                Thread.sleep(2000);
            }

            String productName = "Laptop HP Pavilion";

            assertTrue(homePage.isProductDisplayed(productName),
                    "El producto no está disponible en la página principal");

            homePage.clickProduct(productName);
            Thread.sleep(1500);

            assertTrue(productDetailPage.isProductDetailDisplayed(),
                    "No se pudo acceder a la página de detalle del producto");

            productDetailPage.ClickAddToCart();
            Thread.sleep(1500);

            assertTrue(productDetailPage.isProductAddedMessageDisplayed(),
                    "No se muestra el mensaje de producto agregado al carrito");

            System.out.println("Producto agregado al carrito exitosamente");

            productDetailPage.goBack();
            Thread.sleep(1000);

            homePage.clickCartTab();
            Thread.sleep(2000);

            assertTrue(cartPage.isCartDisplayed(), "No se pudo acceder al carrito");
            assertTrue(cartPage.hasProductsInCart(), "El carrito no tiene productos");

            System.out.println("Usuario tiene productos en el carrito confirmado");
        } catch (Exception e) {
            fail("Error al preparar productos en el carrito: " + e.getMessage());
        }
    }

    @When("procede al checkout")
    public void procedeAlCheckout() {
        System.out.println("Step: Procediendo al checkout");

        try {
            cartPage.clickProceedToCheckout();
            Thread.sleep(2000);

            assertTrue(checkoutPage.isCheckoutPageDisplayed(),
                    "No se pudo acceder a la página de checkout");

            System.out.println("Navegación a checkout exitosa");
        } catch (Exception e) {
            fail("Error al proceder al checkout: " + e.getMessage());
        }
    }

    @When("ingresa los datos de envio")
    public void ingresaLosDatosDeEnvio() {
        System.out.println("Step: Ingresando datos de envío");

        try {
            checkoutPage.enterShippingAddress("La Esperanza");
            Thread.sleep(1000);

            checkoutPage.selectPaymentMethod("contraentrega");
            Thread.sleep(1000);

            System.out.println("Datos de envío ingresados: La Esperanza - Contraentrega");
        } catch (Exception e) {
            fail("Error al ingresar datos de envío: " + e.getMessage());
        }
    }

    @When("confirma la compra")
    public void confirmaLaCompra() {
        System.out.println("Step: Confirmando la compra");

        try {
            checkoutPage.confirmPurchase();
            Thread.sleep(3000);

            System.out.println("Compra confirmada");
        } catch (Exception e) {
            fail("Error al confirmar la compra: " + e.getMessage());
        }
    }

    @Then("deberia ver el mensaje de compra existosa")
    public void deberiaVerElMensajeDeCompraExitosa() {
        System.out.println("Step: Verificando mensaje de compra/pedido exitoso");

        try {
            Thread.sleep(2000);
            assertTrue(checkoutPage.isSuccessMessageDisplayed(),
                    "No se muestra el mensaje de compra/pedido exitoso");

            String successMessage = checkoutPage.getSuccessMessageText();
            assertNotNull(successMessage, "El mensaje de éxito está vacío");

            System.out.println("📝 Mensaje encontrado: " + successMessage);

            String messageLower = successMessage.toLowerCase();
            assertTrue(messageLower.contains("confirmado") ||
                       messageLower.contains("exitoso") ||
                       messageLower.contains("exitosa"),
                    "El mensaje no contiene 'confirmado/exitoso/exitosa': " + successMessage);

            System.out.println("✅ Validación exitosa: Mensaje de pedido/compra mostrado");
            System.out.println("✅ Mensaje completo: '" + successMessage + "'");
        } catch (AssertionError e) {
            System.err.println("❌ Error en validación: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            fail("Error al verificar mensaje de éxito: " + e.getMessage());
        }
    }

    @Given("que el usuario tiene el carrito vacio")
    public void queElUsuarioTieneElCarritoVacio() {
        System.out.println("Step: Usuario debe tener el carrito vacío");

        try {
            if (!homePage.isHomePageDisplayed()) {
                loginPage.login("admin@test.com", "123456");
                Thread.sleep(2000);
            }

            homePage.clickCartTab();
            Thread.sleep(2000);
            assertTrue(cartPage.isCartDisplayed(), "No se pudo acceder al carrito");

            assertTrue(cartPage.isCartEmpty(), "El carrito no está vacío");

            System.out.println("Usuario tiene el carrito vacío confirmado");
        } catch (Exception e) {
            fail("Error al verificar carrito vacío: " + e.getMessage());
        }
    }

    @When("intenta proceder al checkout")
    public void intentaProceberAlCheckout() {
        System.out.println("Step: Intentando proceder al checkout con carrito vacío");

        try {
            try {
                cartPage.clickProceedToCheckout();
                Thread.sleep(1500);
            } catch (Exception e) {
                System.out.println("Botón de checkout no disponible (esperado con carrito vacío)");
            }

            System.out.println("Intento de proceder al checkout realizado");
        } catch (Exception e) {
            System.out.println("No se pudo proceder al checkout (esperado): " + e.getMessage());
        }
    }

    @Then("deberia ver mensaje de carrito vacio")
    public void deberiaVerMensajeDeCarritoVacio() {
        System.out.println("Step: Verificando mensaje de carrito vacío");

        assertTrue(cartPage.isCartEmpty(),
                "No se muestra el mensaje de carrito vacío");

        assertFalse(checkoutPage.isCheckoutPageDisplayed(),
                "No debería poder acceder al checkout con carrito vacío");

        System.out.println("Validación exitosa: Mensaje de carrito vacío mostrado");
    }

    @When("intenta confirmar la compra sin ingresar direccion")
    public void intentaConfirmarLaCompraSinIngresarDireccion() {
        System.out.println("Step: Intentando confirmar compra sin dirección");

        try {

            checkoutPage.clearShippingAddress();
            Thread.sleep(500);

            System.out.println("Campo de dirección limpiado");

            checkoutPage.selectPaymentMethod("contraentrega");
            Thread.sleep(1000);

            checkoutPage.confirmPurchase();
            Thread.sleep(2000);

            System.out.println("Intento de confirmar compra sin dirección realizado");
        } catch (Exception e) {
            System.out.println("Error al intentar confirmar (esperado): " + e.getMessage());
        }
    }

    @Then("deberia ver mensaje de error de direccion requerida")
    public void deberiaVerMensajeDeErrorDeDireccionRequerida() {
        System.out.println("Step: Verificando mensaje de error de dirección requerida");

        assertTrue(checkoutPage.isErrorMessageDisplayed() ||
                   checkoutPage.isAddressFieldEmpty(),
                "No se muestra mensaje de error o validación de dirección");

        assertFalse(checkoutPage.isSuccessMessageDisplayed(),
                "No debería mostrar mensaje de éxito sin dirección");

        System.out.println("Validación exitosa: No se permite checkout sin dirección");
    }
}

