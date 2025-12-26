package edu.pe.cibertec.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public ProfilePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private WebElement getProfileTab() {
        try {
            return driver.findElement(
                    AppiumBy.androidUIAutomator(
                            "new UiSelector().text(\"Perfil\").className(\"android.widget.TextView\")"
                    )
            );
        } catch (Exception e) {
            return driver.findElement(
                    AppiumBy.androidUIAutomator(
                            "new UiSelector().resourceId(\"*:id/navigation_bar_item_icon_view\").instance(3)"
                    )
            );
        }
    }

    private WebElement getProfileTitle() {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Perfil\").className(\"android.widget.TextView\")"
                )
        );
    }

    private WebElement getLogoutButtonInitial() {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().className(\"android.widget.Button\").instance(2)"
                )
        );
    }

    private WebElement getLogoutButtonConfirm() {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().className(\"android.widget.Button\").instance(1)"
                )
        );
    }
    private WebElement getUserEmail() {
        return driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().className(\"android.widget.TextView\").textMatches(\".*@.*\")"
                )
        );
    }

    public void clickProfileTab() {
        try {
            System.out.println("👆 Haciendo clic en tab Perfil...");

            WebElement profileTab = wait.until(ExpectedConditions.elementToBeClickable(getProfileTab()));
            System.out.println("✅ Tab Perfil encontrado y clickable");

            profileTab.click();
            System.out.println("✅ Click en tab Perfil realizado");

            Thread.sleep(2000);
            System.out.println("⏳ Esperando 2 segundos para que cargue la página de perfil...");

        } catch (Exception e) {
            System.err.println("❌ Error al hacer clic en tab Perfil: " + e.getMessage());
            throw new RuntimeException("No se pudo hacer clic en el tab de Perfil", e);
        }
    }

    public void clickLogoutButton() {
        try {
            System.out.println("🔍 Iniciando proceso de Cerrar Sesión...");
            System.out.println("===========================================");

            try {
                driver.findElement(
                        AppiumBy.androidUIAutomator(
                                "new UiScrollable(new UiSelector().scrollable(true))" +
                                ".scrollIntoView(new UiSelector().className(\"android.widget.Button\").instance(2))"
                        )
                );
                System.out.println("✅ Scroll completado");
            } catch (Exception scrollEx) {
                System.out.println("⚠️ No se necesitó scroll");
            }

            Thread.sleep(1500);

            System.out.println("👆 PASO 1: Haciendo clic en botón inicial (instance 2)...");

            WebElement initialButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.androidUIAutomator(
                            "new UiSelector().className(\"android.widget.Button\").instance(2)"
                    )
            ));

            System.out.println("✅ Botón inicial encontrado");
            System.out.println("📝 Clase: " + initialButton.getAttribute("class"));
            System.out.println("📝 Texto: " + initialButton.getText());
            System.out.println("📝 Enabled: " + initialButton.isEnabled());
            System.out.println("📝 Displayed: " + initialButton.isDisplayed());

            wait.until(ExpectedConditions.elementToBeClickable(initialButton));
            initialButton.click();
            System.out.println("✅✅✅ CLICK EN BOTÓN INICIAL REALIZADO - Diálogo debe estar abierto");

            Thread.sleep(2000);

            System.out.println("👆 PASO 2: Haciendo clic en botón de confirmación (instance 1)...");

            WebElement confirmButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.androidUIAutomator(
                            "new UiSelector().className(\"android.widget.Button\").instance(1)"
                    )
            ));

            System.out.println("✅ Botón de confirmación encontrado");
            System.out.println("📝 Clase: " + confirmButton.getAttribute("class"));
            System.out.println("📝 Texto: " + confirmButton.getText());
            System.out.println("📝 Enabled: " + confirmButton.isEnabled());
            System.out.println("📝 Displayed: " + confirmButton.isDisplayed());

            wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
            confirmButton.click();
            System.out.println("✅✅✅ CLICK EN BOTÓN DE CONFIRMACIÓN REALIZADO");

            Thread.sleep(2000);

            System.out.println("===========================================");
            System.out.println("🎉 Proceso de Cerrar Sesión completado exitosamente");
            System.out.println("===========================================");

        } catch (Exception e) {
            System.err.println("===========================================");
            System.err.println("❌ ERROR al hacer clic en Cerrar Sesión: " + e.getMessage());
            e.printStackTrace();
            System.err.println("===========================================");
            throw new RuntimeException("No se pudo completar el proceso de cerrar sesión", e);
        }
    }


    public boolean isProfilePageDisplayed() {
        try {
            System.out.println("🔍 Verificando si la página de perfil está visible...");

            try {
                WebElement emailElement = driver.findElement(
                        AppiumBy.androidUIAutomator(
                                "new UiSelector().className(\"android.widget.TextView\").textMatches(\".*@.*\")"
                        )
                );
                boolean emailVisible = emailElement.isDisplayed();
                System.out.println("✅ Email del usuario encontrado: " + emailElement.getText());
                System.out.println("✅ Página de perfil confirmada");
                return emailVisible;
            } catch (Exception e1) {
                System.out.println("⚠️ Email no encontrado, intentando buscar por botón de logout...");

                try {
                    WebElement logoutBtn = driver.findElement(
                            AppiumBy.androidUIAutomator(
                                    "new UiSelector().className(\"android.widget.Button\").instance(2)"
                            )
                    );
                    boolean btnVisible = logoutBtn.isDisplayed();
                    System.out.println("✅ Botón de logout encontrado");
                    System.out.println("✅ Página de perfil confirmada");
                    return btnVisible;
                } catch (Exception e2) {
                    System.out.println("⚠️ Botón de logout no encontrado, intentando buscar por texto 'Perfil'...");

                    try {
                        WebElement perfilText = driver.findElement(
                                AppiumBy.androidUIAutomator(
                                        "new UiSelector().className(\"android.widget.TextView\").textMatches(\".*[Pp]erfil.*\")"
                                )
                        );
                        boolean textVisible = perfilText.isDisplayed();
                        System.out.println("✅ Texto 'Perfil' encontrado");
                        System.out.println("✅ Página de perfil confirmada");
                        return textVisible;
                    } catch (Exception e3) {
                        System.err.println("❌ No se pudo verificar la página de perfil con ninguna estrategia");
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error al verificar página de perfil: " + e.getMessage());
            return false;
        }
    }

    public boolean isUserEmailDisplayed() {
        try {
            return getUserEmail().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserEmailText() {
        try {
            wait.until(ExpectedConditions.visibilityOf(getUserEmail()));
            return getUserEmail().getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isLogoutButtonDisplayed() {
        try {
            return getLogoutButtonInitial().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLogout() {
        clickLogoutButton();
    }

    public boolean isLogoutButtonVisible() {
        return isLogoutButtonDisplayed();
    }

    public String getCurrentUsername() {
        return getUserEmailText();
    }
}

