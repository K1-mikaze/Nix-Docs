| Navegation | Navegacion |
| -------------- | --------------- |
| [Project Structure](#Project-Structure) | [Estructura del Proyecto](#Estructura-del-proyecto) |
| [Folder Structure](##Folder-Structure) | [Estructura de Carpetas](#Estructura-de-Carpetas) |


# Project Structure

![Project Structure](./docs/images/project-structure.png) 

## Folder Structure

**controller**  Contains servlets that handle HTTP requests and coordinate between the view and model layers.

**model**  Houses classes representing data entities and the application's business logic.

**repository**  Implements the Repository pattern for data access, providing an abstraction layer over persistence.

**util/database**  Manages database connection configuration, connection pooling, and basic database operations.

**util/environment**  Provides utilities for environment variable management and application configuration.

**util/templateEngine**  Configures and manages the Thymeleaf template engine for view rendering.

**util/email**  Configures and manages the email service.

**util/validator**  Contain pattern validations.

**webapp/template**  Contains HTML templates with Thymeleaf syntax for application views.

**webapp/static**  Hosts static files such as CSS, JavaScript, TypeScript, images, and other frontend resources.

**docs**  Project documentation including README, installation guides, and technical documentation.

**test/java**  Suite of unit and integration tests to validate code functionality.


--------

# Estructura del proyecto 

![Arbol de archivos](./docs/images/project-structure.png) 

## Estructura de Carpetas

**controller**  Contiene los servlets que manejan las peticiones HTTP y coordinan entre la vista y el modelo.

**model**  Alberga las clases que representan las entidades de datos y la lógica de negocio de la aplicación.

**repository**  Implementa el patrón Repository para el acceso a datos, proporcionando una abstracción sobre la capa de persistencia.

**util/database**  Gestiona la configuración de conexión a la base de datos, pooling de conexiones y operaciones básicas de BD.

**util/environment**  Proporciona utilidades para la gestión de variables de entorno y configuración de la aplicación.

**util/templateEngine**  Configura y maneja el motor de plantillas Thymeleaf para el renderizado de vistas.

**util/email**  Configura y maneja el servicio de envio de emails.

**util/validator**  Contiene  patrones de validacion.

**webapp/template**  Contiene las plantillas HTML con sintaxis Thymeleaf para las vistas de la aplicación.

**webapp/static**  Aloja archivos estáticos como CSS, JavaScript, TypeScript, imágenes y otros recursos del frontend.

**docs**  Documentación del proyecto incluyendo README, guías de instalación y documentación técnica.

**test/java**  Suite de pruebas unitarias e integrales para validar el correcto funcionamiento del código.
