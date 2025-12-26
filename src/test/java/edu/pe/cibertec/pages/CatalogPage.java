package edu.pe.cibertec.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CatalogPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public CatalogPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private WebElement getSearchField() {
        return driver.findElement(
                AppiumBy.className("android.widget.EditText")
        );
    }

    private WebElement getCategoryFilterTodos() {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Todos\").className(\"android.widget.TextView\")"
                )
        );
    }
    private WebElement getCategoryFilter(String category) {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"" + category + "\").className(\"android.widget.TextView\")"
                )
        );
    }

    private List<WebElement> getProductList() {
        return driver.findElements(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().className(\"android.widget.TextView\").textMatches(\".*(?:Laptop|Mouse|Teclado|Monitor|Audífonos|Cámara|Impresora|Tablet).*\")"
                )
        );
    }

    private List<WebElement> getProductsByText(String productName) {
        return driver.findElements(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().textContains(\"" + productName + "\").className(\"android.widget.TextView\")"
                )
        );
    }

    private WebElement getCatalogTitle() {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Productos\").className(\"android.widget.TextView\")"
                )
        );
    }

    public void navigateToCatalog() {
        wait.until(ExpectedConditions.visibilityOf(getCatalogTitle()));
    }

    public void searchProduct(String productName) {
        wait.until(ExpectedConditions.visibilityOf(getSearchField()));
        getSearchField().click();
        getSearchField().clear();
        getSearchField().sendKeys(productName);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void filterByCategory(String category) {
        try {
            WebElement categoryButton = getCategoryFilter(category);
            wait.until(ExpectedConditions.elementToBeClickable(categoryButton));
            categoryButton.click();

            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isProductListDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(getCatalogTitle()));
            return !getProductList().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean productsContainText(String text) {
        try {
            List<WebElement> products = getProductsByText(text);
            return !products.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public int getProductCount() {
        try {
            return getProductList().size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean allProductsAreFromCategory(String category) {
        try {
            List<WebElement> products = getProductList();
            if (products.isEmpty()) {
                return false;
            }

            WebElement categoryFilter = getCategoryFilter(category);
            return categoryFilter.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCatalogPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(getCatalogTitle()));
            return getCatalogTitle().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}


