package edu.pe.cibertec.steps;

import edu.pe.cibertec.config.AppiumConfig;
import edu.pe.cibertec.pages.CatalogPage;
import edu.pe.cibertec.pages.HomePage;
import edu.pe.cibertec.pages.LoginPage;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class CatalogSteps {

    private AndroidDriver driver;
    private CatalogPage catalogPage;
    private LoginPage loginPage;
    private HomePage homePage;

    @Before("@catalogo")
    public void setUp() {
        System.out.println("=== Iniciando prueba de Catálogo ===");
        driver = AppiumConfig.getDriver();
        catalogPage = new CatalogPage(driver);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        System.out.println("Driver inicializado correctamente");
    }

    @After("@catalogo")
    public void tearDown() {
        System.out.println("=== Finalizando prueba de Catálogo ===");
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

    @When("navega al catalogo de productos")
    public void navegaAlCatalogoDeProductos() {
        System.out.println("Step: Navegando al catálogo de productos");

        try {
            catalogPage.navigateToCatalog();

            Thread.sleep(1000);

            System.out.println("Navegación al catálogo completada (ya estamos en la página de inicio)");
        } catch (Exception e) {
            fail("Error al navegar al catálogo: " + e.getMessage());
        }
    }

    @Then("deberia ver la lista de productos disponibles")
    public void deberiaVerLaListaDeProductosDisponibles() {
        System.out.println("Step: Verificando lista de productos disponibles");

        assertTrue(catalogPage.isCatalogPageDisplayed(),
                "La página del catálogo no está visible");

        assertTrue(catalogPage.isProductListDisplayed(),
                "No se encontraron productos en el catálogo");

        int productCount = catalogPage.getProductCount();
        assertTrue(productCount > 0,
                "La lista de productos está vacía. Productos encontrados: " + productCount);

        System.out.println("Validación exitosa: Se encontraron " + productCount + " productos");
    }

    @Given("que el usuario esta en el catalogo")
    public void queElUsuarioEstaEnElCatalogo() {
        System.out.println("Step: Usuario debe estar en el catálogo");

        try {
            if (!homePage.isHomePageDisplayed()) {
                System.out.println("Usuario no logueado, realizando login...");
                loginPage.login("admin@test.com", "123456");
                Thread.sleep(2000);
                assertTrue(homePage.isHomePageDisplayed(),
                        "El usuario no pudo iniciar sesión correctamente");
                System.out.println("✅ Login exitoso verificado");
            }

            assertTrue(catalogPage.isCatalogPageDisplayed(),
                    "El usuario no está en la página del catálogo");

            System.out.println("Usuario en el catálogo confirmado");
        } catch (Exception e) {
            fail("Error al verificar que el usuario está en el catálogo: " + e.getMessage());
        }
    }

    @When("busca el producto {string}")
    public void buscaElProducto(String productName) {
        System.out.println("Step: Buscando producto '" + productName + "'");

        try {
            catalogPage.searchProduct(productName);

            Thread.sleep(2000);

            System.out.println("Búsqueda realizada para: " + productName);
        } catch (Exception e) {
            fail("Error al buscar el producto: " + e.getMessage());
        }
    }

    @Then("deberia ver productos que contengan {string}")
    public void deberiaVerProductosQueContengan(String productName) {
        System.out.println("Step: Verificando que los productos contienen '" + productName + "'");

        assertTrue(catalogPage.productsContainText(productName),
                "No se encontraron productos que contengan '" + productName + "'");

        int productCount = catalogPage.getProductCount();
        assertTrue(productCount > 0,
                "No hay productos en los resultados de búsqueda");

        System.out.println("Validación exitosa: Se encontraron " + productCount +
                " productos que contienen '" + productName + "'");
    }

    @When("filtra los productos por la categoria {string}")
    public void filtraLosProductosPorLaCategoria(String category) {
        System.out.println("Step: Filtrando productos por categoría '" + category + "'");

        try {
            catalogPage.filterByCategory(category);

            Thread.sleep(2000);

            System.out.println("Filtro aplicado para categoría: " + category);
        } catch (Exception e) {
            fail("Error al filtrar por categoría: " + e.getMessage());
        }
    }

    @Then("deberia ver solo productos de la categoria {string}")
    public void deberiaVerSoloProductosDeLaCategoria(String category) {
        System.out.println("Step: Verificando que los productos son de la categoría '" + category + "'");

        int productCount = catalogPage.getProductCount();
        assertTrue(productCount > 0,
                "No se encontraron productos de la categoría '" + category + "'");

        assertTrue(catalogPage.allProductsAreFromCategory(category),
                "No todos los productos pertenecen a la categoría '" + category + "'");

        System.out.println("Validación exitosa: Se encontraron " + productCount +
                " productos de la categoría '" + category + "'");
    }
}

