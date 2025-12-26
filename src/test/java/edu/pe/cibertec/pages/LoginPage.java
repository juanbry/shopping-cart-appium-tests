package edu.pe.cibertec.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {

    private AndroidDriver driver;

    public LoginPage(AndroidDriver driver){
        this.driver = driver;
    }
    private WebElement getEmailField(){
        return  driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().className(\"android.widget.EditText\").instance(0)"
                )
        );
    }

    private WebElement getPasswordField(){
        return  driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().className(\"android.widget.EditText\").instance(1)"
                )
        );
    }

    private WebElement getLoginButton(){
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Iniciar Sesión\")"
                )
        );
    }


    public void enterEmail(String email){
        getEmailField().click();
        getEmailField().clear();
        getEmailField().sendKeys(email);
    }

    public void enterPassword(String password)
    {
        getPasswordField().click();
        getPasswordField().clear();
        getPasswordField().sendKeys(password);
    }

    public void clickLoginButton (){
        try {
            System.out.println("⌨️ Ocultando teclado virtual...");
            driver.hideKeyboard();
            Thread.sleep(500);

            System.out.println("🔍 Buscando botón 'Iniciar Sesión'...");
            WebElement loginButton = getLoginButton();
            System.out.println("✅ Botón 'Iniciar Sesión' encontrado");

            loginButton.click();
            System.out.println("✅ Click en botón 'Iniciar Sesión' ejecutado");
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("❌ Error al presionar el botón de login: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("No se pudo hacer clic en el botón de login", e);
        }
    }

    public void login(String email, String password){
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isLoginPageDisplayed() {
        try {
            return getEmailField().isDisplayed() &&
                   getPasswordField().isDisplayed() &&
                   getLoginButton().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isErrorMessageDisplayed() {
        try {
            WebElement errorMessage = driver.findElement(
                    AppiumBy.androidUIAutomator(
                            "new UiSelector().textMatches(\".*[Ee]rror.*|.*[Ii]ncorrect.*|.*[Ii]nválid.*|.*[Ff]alló.*\").className(\"android.widget.TextView\")"
                    )
            );
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessageText() {
        try {
            WebElement errorMessage = driver.findElement(
                    AppiumBy.androidUIAutomator(
                            "new UiSelector().textMatches(\".*[Ee]rror.*|.*[Ii]ncorrect.*|.*[Ii]nválid.*|.*[Ff]alló.*\").className(\"android.widget.TextView\")"
                    )
            );
            return errorMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }
}

