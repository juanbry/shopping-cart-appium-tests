package edu.pe.cibertec.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public CheckoutPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private WebElement getCheckoutTitle() {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Checkout\").className(\"android.widget.TextView\")"
                )
        );
    }

    private WebElement getShippingAddressTitle() {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Dirección de envío\").className(\"android.widget.TextView\")"
                )
        );
    }

    private WebElement getAddressField() {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().className(\"android.widget.EditText\").instance(0)"
                )
        );
    }

    private WebElement getCityField() {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().className(\"android.widget.EditText\").instance(1)"
                )
        );
    }

    // Campo de código postal
    private WebElement getPostalCodeField() {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().className(\"android.widget.EditText\").instance(2)"
                )
        );
    }

    private WebElement getPaymentMethodTitle() {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Método de pago\").className(\"android.widget.TextView\")"
                )
        );
    }

    private WebElement getCreditCardRadioButton() {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Tarjeta de crédito/débito\").className(\"android.widget.TextView\")"
                )
        );
    }

    private WebElement getYapeRadioButton() {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Yape\").className(\"android.widget.TextView\")"
                )
        );
    }

    private WebElement getCashOnDeliveryRadioButton() {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Contraentrega\").className(\"android.widget.TextView\")"
                )
        );
    }

    private WebElement getCardNumberField() {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().className(\"android.widget.EditText\").instance(3)"
                )
        );
    }

    private WebElement getExpiryDateField() {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().className(\"android.widget.EditText\").instance(4)"
                )
        );
    }

    // Campo de CVV
    private WebElement getCvvField() {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().className(\"android.widget.EditText\").instance(5)"
                )
        );
    }

    private WebElement getOrderSummaryTitle() {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Resumen del pedido\").className(\"android.widget.TextView\")"
                )
        );
    }

    private WebElement getSubtotalValue() {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Subtotal\").className(\"android.widget.TextView\")"
                )
        ).findElement(AppiumBy.xpath("following-sibling::android.widget.TextView[1]"));
    }

    private WebElement getTotalValue() {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Total\").className(\"android.widget.TextView\")"
                )
        ).findElement(AppiumBy.xpath("following-sibling::android.widget.TextView[1]"));
    }

    // Botón de confirmar compra
    private WebElement getConfirmPurchaseButton() {
        // Buscar directamente por el TextView "Confirmar Compra"
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Confirmar Compra\").className(\"android.widget.TextView\")"
                )
        );
    }

    private WebElement getSuccessMessage() {
        try {
            return driver.findElement(
                    AppiumBy.androidUIAutomator(
                            "new UiSelector().textMatches(\".*[Pp]edido [Cc]onfirmado.*|.*[Cc]ompra [Ee]xitosa.*|.*[Pp]edido [Ee]xitoso.*\").className(\"android.widget.TextView\")"
                    )
            );
        } catch (Exception e) {
            return driver.findElement(
                    AppiumBy.androidUIAutomator(
                            "new UiSelector().textMatches(\".*confirmado.*|.*exitoso.*\").className(\"android.widget.TextView\")"
                    )
            );
        }
    }

    private WebElement getErrorMessage() {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().textContains(\"error\").className(\"android.widget.TextView\")"
                )
        );
    }

    public void enterShippingAddress(String address) {
        wait.until(ExpectedConditions.visibilityOf(getShippingAddressTitle()));

        // Ingresar dirección
        getAddressField().click();
        getAddressField().clear();
        getAddressField().sendKeys(address);
    }

    public void clearShippingAddress() {
        wait.until(ExpectedConditions.visibilityOf(getShippingAddressTitle()));

        getAddressField().click();
        getAddressField().clear();
    }

    public void enterShippingAddressComplete(String address, String city, String postalCode) {
        wait.until(ExpectedConditions.visibilityOf(getShippingAddressTitle()));

        getAddressField().click();
        getAddressField().clear();
        getAddressField().sendKeys(address);

        getCityField().click();
        getCityField().clear();
        getCityField().sendKeys(city);

        getPostalCodeField().click();
        getPostalCodeField().clear();
        getPostalCodeField().sendKeys(postalCode);
    }

    public void selectPaymentMethod(String paymentMethod) {
        wait.until(ExpectedConditions.visibilityOf(getPaymentMethodTitle()));

        switch (paymentMethod.toLowerCase()) {
            case "tarjeta":
            case "credito":
            case "debito":
                getCreditCardRadioButton().click();
                break;
            case "yape":
                getYapeRadioButton().click();
                break;
            case "contraentrega":
            case "efectivo":
                getCashOnDeliveryRadioButton().click();
                break;
            default:
                getCreditCardRadioButton().click();
        }
    }

    // Ingresar datos de tarjeta
    public void enterCardDetails(String cardNumber, String expiryDate, String cvv) {
        wait.until(ExpectedConditions.visibilityOf(getCardNumberField()));

        getCardNumberField().click();
        getCardNumberField().clear();
        getCardNumberField().sendKeys(cardNumber);

        getExpiryDateField().click();
        getExpiryDateField().clear();
        getExpiryDateField().sendKeys(expiryDate);

        getCvvField().click();
        getCvvField().clear();
        getCvvField().sendKeys(cvv);
    }

    public void confirmPurchase() {
        try {
            System.out.println("🔍 Intentando confirmar compra...");

            try {
                driver.findElement(
                        AppiumBy.androidUIAutomator(
                                "new UiScrollable(new UiSelector().scrollable(true))" +
                                ".scrollIntoView(new UiSelector().text(\"Confirmar Compra\"))"
                        )
                );
                System.out.println("Scroll hacia 'Confirmar Compra' completado");
            } catch (Exception scrollEx) {
                System.out.println("scrollIntoView falló, intentando swipe manual...");
                int startX = 540;
                int startY = 1800;
                int endY = 600;

                for (int i = 0; i < 5; i++) {
                    driver.findElement(
                            AppiumBy.androidUIAutomator(
                                    "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"
                            )
                    );
                    Thread.sleep(500);
                }
            }

            Thread.sleep(1500);

            WebElement confirmButton = driver.findElement(
                    AppiumBy.androidUIAutomator(
                            "new UiSelector().text(\"Confirmar Compra\").className(\"android.widget.TextView\")"
                    )
            );

            System.out.println("✅ Botón 'Confirmar Compra' encontrado");

            confirmButton.click();

            System.out.println("✅ Click en botón 'Confirmar Compra' realizado exitosamente");
        } catch (Exception e) {
            System.err.println("❌ Error al hacer clic en 'Confirmar Compra': " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("No se pudo hacer clic en el botón de confirmar compra", e);
        }
    }

    public boolean isCheckoutPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(getCheckoutTitle()));
            return getCheckoutTitle().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(getSuccessMessage()));
            return getSuccessMessage().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getSuccessMessageText() {
        try {
            wait.until(ExpectedConditions.visibilityOf(getSuccessMessage()));
            return getSuccessMessage().getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return getErrorMessage().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAddressFieldEmpty() {
        try {
            String addressText = getAddressField().getText();
            return addressText == null || addressText.isEmpty() || addressText.equals("Dirección");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTotalDisplayed() {
        try {
            return getTotalValue().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public String getTotalAmount() {
        try {
            wait.until(ExpectedConditions.visibilityOf(getTotalValue()));
            return getTotalValue().getText();
        } catch (Exception e) {
            return "";
        }
    }
}

