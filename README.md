# Shopping Cart — Mobile Automation Suite

**Project:** shopping-cart-appium-tests

**Descripción corta:** Suite E2E móvil: Appium + Cucumber + JUnit5 para la app Shopping Cart.

## Contenido
- Pruebas BDD (Cucumber) para Catálogo, Checkout y Logout
- Page Object Model (POM) con Page classes y Steps
- Reportes con Serenity
- Plan de rendimiento en JMeter (archivo `.jmx` en /jmeter)

---

## Requisitos
- Java 21
- Maven
- Node.js + npm
- Appium (`npm i -g appium`) o `npx appium`
- Android Studio (AVD Manager) / Android SDK
- Apache JMeter (para pruebas de rendimiento)

## Preparación rápida
1. Crear/arrancar un AVD en Android Studio (API 30+ recomendado).
2. Instalar el APK en el emulador:
   ```bash
   adb install -r ./apk/shopping-cart.apk
   ```
3. Iniciar Appium:
   ```bash
   npx appium
   ```
4. Configurar variables de entorno si es necesario: `JAVA_HOME`, `ANDROID_HOME`.

## Ejecutar tests
1. Desde la raíz del proyecto:
   ```bash
   mvn test
   ```
2. Ver los reportes de Serenity en: `target/site/serenity`.

## JMeter (Pruebas de rendimiento)
- Archivo de ejemplo: `jmeter/shopping-cart-perf.jmx`
- Thread Group configurado: 10 usuarios × 5 iteraciones
- Peticiones: GET `/posts` y `/users` a `https://jsonplaceholder.typicode.com`
- Listeners recomendados: View Results Tree, Summary Report

## Estructura importante
- `src/test/resources/features` — archivos `.feature`
- `src/test/java/edu/pe/cibertec/pages` — Page Objects
- `src/test/java/edu/pe/cibertec/steps` — Step Definitions

## Notas finales
- Asegúrate de que el emulador esté corriendo y Appium activo antes de ejecutar las pruebas.
- Si deseas que haga el push y cree el PR, confirmame acceso o métodos de autenticación (token/SSH).

---

© Proyecto de automatización — implementado con Appium, Cucumber y JUnit5.
