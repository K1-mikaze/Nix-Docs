# Nix Docs — Actualizaciones y Arquitectura (Front Controller)

Este documento resume las actualizaciones recientes y la nueva arquitectura aplicada al proyecto Nix Docs para centralizar el manejo de solicitudes utilizando el patrón Front Controller sin frameworks.

## Resumen de Cambios Clave

- Patrón Front Controller implementado con un único servlet `FrontControllerServlet` mapeado a `'/app/*'`.
- Delegación a controladores POJO:
  - `Controller` (interfaz base con `processGet` y `processPost`).
  - `LoginController` (métodos `showLoginForm(req, resp)` y `handleLogin(req, resp)`).
  - `RegisterController` (stub inicial, muestra formulario de registro).
  - `HomeController` (renderiza la página principal).
- `web.xml` actualizado para registrar `FrontControllerServlet` y mapear `'/app/*'`, sustituyendo el mapeo anterior a `Main`.
- Inicialización de Thymeleaf asegurada en `FrontControllerServlet.init()` y mediante `ThymeleafContextListener`.
- Autenticación: inicio de sesión funcionando con sesiones (ID, username, email) y sin almacenar contraseñas.
- Formularios y vistas ajustadas para usar rutas del Front Controller (`th:action="@{/app/login}"`, etc.).
- Mensajería en vistas (éxito/error) con estilos de alertas.

## Estructura de Código Añadida

```
src/main/java/com/nixdocs/controller/
  ├── FrontControllerServlet.java
  └── pojo/
      ├── Controller.java
      ├── LoginController.java
      ├── RegisterController.java
      └── HomeController.java

src/main/webapp/WEB-INF/web.xml (actualizado)
```

### FrontControllerServlet

- URL patrón: `@WebServlet(name = "frontControllerServlet", urlPatterns = {"/app/*"})`.
- Inicializa Thymeleaf: `ThymeleafUtil.initialize(getServletContext(), false)`.
- Analiza `request.getPathInfo()` para determinar la acción (`login`, `register`, `home`).
- Delegación según método HTTP a `Controller.processGet(...)` o `Controller.processPost(...)`.

### Interfaz `Controller`

- Define los métodos:
  - `void processGet(HttpServletRequest req, HttpServletResponse resp)`
  - `void processPost(HttpServletRequest req, HttpServletResponse resp)`

### `LoginController`

- `showLoginForm(req, resp)`: renderiza la plantilla `signIn` con `pageTitle` y mensajes de sesión (`errorMessage`, `successMessage`).
- `handleLogin(req, resp)`: valida credenciales, busca usuario por email/username, verifica contraseña y crea sesión (sin guardar contraseña). Redirige a `'/app/home'`.
- Manejo de excepción checked de Thymeleaf con `try/catch` y re-lanzado como `ServletException`.

### `RegisterController`

- `processGet(...)`: renderiza la plantilla `createAccount`.
- `processPost(...)`: pendiente de implementar (actualmente responde con `501 Not Implemented`).

### `HomeController`

- `processGet(...)`: renderiza la plantilla `index`.
- `processPost(...)`: redirige a `'/app/home'`.

## Configuración de Vistas (Thymeleaf)

- Prefijo de plantillas: `"/template/"`.
- Sufijo: `".html"`.
- Ejemplos:
  - `ThymeleafUtil.renderTemplate(request, response, "signIn", variables);`
  - `ThymeleafUtil.renderTemplate(request, response, "index", variables);`

## Rutas Principales

- `GET /app/home` → HomeController (plantilla `index`).
- `GET /app/login` → LoginController.showLoginForm (plantilla `signIn`).
- `POST /app/login` → LoginController.handleLogin (crea sesión y redirige).
- `GET /app/register` → RegisterController.processGet (plantilla `createAccount`).
- `POST /app/register` → RegisterController.processPost (pendiente implementar).
- Recursos estáticos: `/static/core/*`, `/static/image/*`, `/static/style/*` (servlet `StaticResourceServlet`).

## Cambios en Plantillas

- `template/signIn.html`:
  - `th:action="@{/app/login}"` en el formulario.
  - Corrección del título: `th:text="${pageTitle}"`.
  - Bloques para mensajes: `errorMessage` y `successMessage`.

- `template/createAccount.html`:
  - Bloques para mensajes de error/éxito.
  - Ajustes de enlaces y recursos usando `th:href` y `th:src`.

## Estilos

- `static/style/signIn.css`:
  - Clases `.alert`, `.alert-error`, `.alert-success` para mensajes.

## Sesiones

- Atributos de sesión tras login:
  - `userId`, `username`, `email`, `authenticated=true`.
- Nunca se guarda la contraseña en sesión.

## Migración y Limpieza

- `web.xml` ahora registra `FrontControllerServlet` en `'/app/*'`.
- Recomendado:
  - Retirar o deshabilitar servlets anotados (`@WebServlet`) redundantes como `SignIn`/`CreateAccount` si migran a POJOs.
  - Actualizar enlaces en todas las páginas para apuntar a `@{/app/...}`.

## Próximos Pasos

- Implementar `RegisterController.processPost(...)` con validaciones, persistencia y mensajería.
- Añadir fragmentos Thymeleaf reutilizables (por ejemplo `header.html`) y lógica condicional según estado de sesión.
- Tests de integración básicos para rutas `/app/*` y sesiones.

---

Última actualización: refactor a Front Controller y login con Thymeleaf.