@login
Feature: Login en la applicacion Shopping Cart
  Como usuario de la aplicacion
  Quiero poder iniciar sesion con mis credenciales
  Para acceder a las funcionalidades de la tienda

  @smoke @happy-path
  Scenario: Login existoso con usuario admin
    Given que el usuario esta en la pantalla de login
    When ingresa el email "admin@test.com"
    And ingresa el password "123456"
    And hacer clic en el boton login
    Then deberia acceder a la pantalla principal

  @happy-path
  Scenario: Login existoso con usuario regular
    Given que el usuario esta en la pantalla de login
    When ingresa el email "user1@test.com"
    And ingresa el password "password1"
    And hacer clic en el boton login
    Then deberia acceder a la pantalla principal

  @negative
  Scenario: Login fallido con password incorrecto
    Given que el usuario esta en la pantalla de login
    When ingresa el email "admin@test.com"
    And ingresa el password "wrongpass"
    And hacer clic en el boton login
    Then deberia ver un mensaje de error


  @negative
  Scenario: Login fallido con usuario no registrado
    Given que el usuario esta en la pantalla de login
    When ingresa el email "noexiste@test.com"
    And ingresa el password "123456"
    And hacer clic en el boton login
    Then deberia ver un mensaje de error
