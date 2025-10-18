

| Navegation   | Navegacion |
| ------------- | ------------- |
| [Installation](#Set-Up) | [Instalacion](#Configuracion) | 
| [Project Structure  ](./docs/project-structure.md) | [Estructura del Projecto](./docs/project-structure.md) |
| [Git Workflow](./docs/git-workflow.md) | [Flujo del Git](./docs/git-workflow.md) |
| [Database](./docs/database.md) | [Base de Datos](./docs/database.md)  |


---- 
# Nix Docs English

Nix Docs is project focus in solving the big documentation gap that the Nix Ecosystem has in a beginner friendly way.

## Set Up 
### Prerequisites

- **Tomcat 11.0.11** 
- **JAVA/JDK/OPENJDK 21** 
- **PostgreSQL**  

### Installation 

1. **Clone the repository** 
```bash
git clone https://github.com/K1-mikaze/Nix-Docs
cd nix-docs
```

2. **Configure environment variables** 

You need to create a `.env` file next to your project `src` folder, with the database credentials like the following example: (Remember to add this file to your `.gitignore`)

```.env
#Required
DB_URL=jdbc:postgresql://localhost:5432/example
DB_USERNAME=user_name
DB_PASSWORD=database_passwordj
EMAIL_ADDRESS=example@gmail.com
EMAIL_PASSWORD=email_password

#Optional
EMAIL_HOST=email_host
DB_DRIVER=database_driver
DB_MAX_POOL_SIZE=pool_size
DB_CONNECTION_TIMEOUT=database_timeout
```

3. **Build and deploy** 

- Configure your Tomcat 11.0.11 server
- Run the application


## Usage

### Application Routes

After starting the application, you can access the following endpoints:

| Page | URL |
|--------|-----
| HomePage | `http://localhost:8080/NixDocs/app/home` o `http://localhost:8080/NixDocs`  |
| Login | `http://localhost:8080/NixDocs/app/login` |
| Register | `http://localhost:8080/NixDocs/app/register` |

---

# Nix Docs Español

Nix Docs es un proyecto enfocado en resolver la gran brecha de documentación que tiene el Ecosistema Nix de una manera amigable para principiantes.

## Configuracion 

### Prerequsitos

- **Tomcat 11.0.11** 
- **JAVA/JDK/OPENJDK 21** 
- **PostgreSQL**  

### Instalacion 

1. **Clona el repositorio** 
```bash
git clone https://github.com/K1-mikaze/Nix-Docs
cd nix-docs
```

2. **Configura las variables de enforno** 

You need to create a `.env` file next to your project `src` folder, with the database credentials like the following example:

Necesitas crear un archivo llamado **.env**  al lado del la carpeta `src` con las credenciales de tu base de datos como en este ejemplo: (Recuerda añadir este archivo a tu `.gitignore`) 

```.env
#Required
DB_URL=jdbc:postgresql://localhost:5432/ejemplo
DB_USERNAME=nombre_usuario
DB_PASSWORD=contraseña_base_datos
EMAIL_ADDRESS=ejemplo@gmail.com
EMAIL_PASSWORD=contraseña

#Optional
EMAIL_HOST=email_host
DB_DRIVER=controlador_base_datos
DB_MAX_POOL_SIZE=pool_size
DB_CONNECTION_TIMEOUT=database_timeout
```

3. **Contruyelo y despliegalo** 

- Configura tu servidor Tomcat 

- Corre la Aplicacion

## Uso

### Rutas Disponibles

Una vez que la aplicación esté en ejecución, puedes acceder a las siguientes rutas:

| Página | URL |
|--------|-----
| Página Principal | `http://localhost:8080/NixDocs/app/home` o `http://localhost:8080/NixDocs`  |
| Inicio de Sesión | `http://localhost:8080/NixDocs/app/login` |
| Registro de Usuario | `http://localhost:8080/NixDocs/app/register` |


