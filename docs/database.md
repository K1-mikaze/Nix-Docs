[test](#Base-De-Datos) 
# Database

This PostgreSQL database powers the NixDocs documentation platform, providing a robust foundation for user management, content storage, and system compatibility tracking.

## Overview
The database consists of 8 main tables that handle user accounts, content management, feedback systems, and cross-platform compatibility for Nix ecosystem documentation.

### Database Tables

You can find a copy of the database inside the folder `docs/files/NixDocs-Database.sql` 

> **Core User Management**

 **USERS** Stores active user accounts with verification status.
```sql
CREATE TABLE USERS(
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  username VARCHAR(50) NOT NULL,
  email VARCHAR(320) UNIQUE NOT NULL,
  password TEXT NOT NULL,
  verified BOOL NOT NULL,
  creation TIMESTAMP DEFAULT NOW()  
);
```

**DELETED_USERS** Archives deleted user accounts for data preservation.

```sql
CREATE TABLE DELETED_USERS(
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  username VARCHAR(50) NOT NULL,
  email VARCHAR(320) UNIQUE NOT NULL,
  password TEXT NOT NULL,
  deletion TIMESTAMP DEFAULT NOW()
);
```
 **VERIFICATION_CODES** Manages email verification codes for account activation.

```sql
CREATE TABLE VERIFICATION_CODES(
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL,  
  verification_code VARCHAR(6),
  FOREIGN KEY(user_id) REFERENCES USERS(id)
);
```
**LOGS** Tracks user activity and access patterns.

```sql
CREATE TABLE LOGS(
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL,  
  entry TIMESTAMP DEFAULT NOW(),
  FOREIGN KEY(user_id) REFERENCES USERS(id)
);
```

> **Content Management**

**PAGES** Stores documentation page metadata.

```sql
CREATE TABLE PAGES (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  page_name VARCHAR(100) NOT NULL,
  description VARCHAR(200)
);
```

**CONTENTS** Holds the actual content for documentation pages.

```sql
CREATE TABLE CONTENTS(
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  page_id UUID NOT NULL,  
  content_name VARCHAR(50),
  content TEXT NOT NULL,
  FOREIGN KEY(page_id) REFERENCES PAGES(id)
);
```

> **Engagement & Compatibility**

**FEEDBACKS** Collects user feedback (likes/dislikes) for content.

```sql
CREATE TABLE FEEDBACKS(
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  page_id UUID NOT NULL,  
  user_id UUID NOT NULL,  
  is_like BOOL NOT NULL,  
  FOREIGN KEY(user_id) REFERENCES USERS(id),
  FOREIGN KEY(page_id) REFERENCES PAGES(id)
);
```


**COMPATIBILITIES** Tracks system compatibility for documentation across Nix ecosystems.

```sql
CREATE TYPE system_type AS ENUM ('NIXPKGS', 'NIXOS', 'HOME-MANAGER');

CREATE TABLE COMPATIBILITIES(
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  page_id UUID NOT NULL,
  system system_type NOT NULL,
  version VARCHAR(10) NOT NULL,
  FOREIGN KEY(page_id) REFERENCES PAGES(id)
);
```
### Key Features
#### Security & Data Integrity
- UUID Primary Keys: Secure, non-sequential identifiers for all records

- Password Hashing: Secure password storage (assumes application-level hashing)

- Email Uniqueness: Prevents duplicate accounts

- Foreign Key Constraints: Maintains referential integrity

#### Temporal Tracking

- Automatic Timestamps: creation, deletion, and entry fields track all events

- User Activity Logging: Comprehensive audit trail capabilities

####  Nix-Specific Features

- Multi-System Support: Tracks compatibility across:

**NIXPKGS - Nix packages**

**NIXOS - NixOS system configurations**

**HOME-MANAGER - User environment management**

- Version Compatibility: Records supported versions for each documentation page

#### User Experience

- Email Verification: Secure account activation flow

- Feedback System: User-driven content quality signals

- Soft Deletes: Preserves data while supporting account deletion

----

# Base de Datos
Esta base de datos PostgreSQL impulsa la plataforma de documentación NixDocs, proporcionando una base sólida para la gestión de usuarios, almacenamiento de contenido y seguimiento de compatibilidad entre sistemas.

## Visión General
La base de datos consta de 8 tablas principales que manejan cuentas de usuario, gestión de contenido, sistemas de retroalimentación y compatibilidad multiplataforma para la documentación del ecosistema Nix.

### Tablas de la Base de Datos

Puedes encontrar una copia de la base de datos en la carpeta `docs/files/NixDocs-Database.sql`

> **Gestión Principal de Usuarios**

**USERS** Almacena cuentas de usuario activas con estado de verificación.
```sql
CREATE TABLE USERS(
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  username VARCHAR(50) NOT NULL,
  email VARCHAR(320) UNIQUE NOT NULL,
  password TEXT NOT NULL,
  verified BOOL NOT NULL,
  creation TIMESTAMP DEFAULT NOW()  
);
```

**DELETED_USERS** Archiva cuentas de usuario eliminadas para preservación de datos.

```sql
CREATE TABLE DELETED_USERS(
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  username VARCHAR(50) NOT NULL,
  email VARCHAR(320) UNIQUE NOT NULL,
  password TEXT NOT NULL,
  deletion TIMESTAMP DEFAULT NOW()
);

```
**VERIFICATION_CODES** Gestiona códigos de verificación por email para activación de cuentas.

```sql
CREATE TABLE VERIFICATION_CODES(
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL,  
  verification_code VARCHAR(6),
  FOREIGN KEY(user_id) REFERENCES USERS(id)
);
```

**LOGS** Rastrea la actividad y patrones de acceso de los usuarios.

```sql
CREATE TABLE LOGS(
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL,  
  entry TIMESTAMP DEFAULT NOW(),
  FOREIGN KEY(user_id) REFERENCES USERS(id)
);
```

> **Gestión de Contenido**

**PAGES** Almacena metadatos de páginas de documentación.

```sql
CREATE TABLE PAGES (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  page_name VARCHAR(100) NOT NULL,
  description VARCHAR(200)
);
```

**CONTENTS** Contiene el contenido real para las páginas de documentación.

```sql
CREATE TABLE CONTENTS(
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  page_id UUID NOT NULL,  
  content_name VARCHAR(50),
  content TEXT NOT NULL,
  FOREIGN KEY(page_id) REFERENCES PAGES(id)
);
```

> **Compromiso y Compatibilidad**

**FEEDBACKS** Recopila retroalimentación de usuarios (me gusta/no me gusta) para el contenido.

```sql
CREATE TABLE FEEDBACKS(
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  page_id UUID NOT NULL,  
  user_id UUID NOT NULL,  
  is_like BOOL NOT NULL,  
  FOREIGN KEY(user_id) REFERENCES USERS(id),
  FOREIGN KEY(page_id) REFERENCES PAGES(id)
);
```

**COMPATIBILITIES** Rastrea la compatibilidad del sistema para documentación a través de ecosistemas Nix.

```sql
CREATE TYPE system_type AS ENUM ('NIXPKGS', 'NIXOS', 'HOME-MANAGER','NIX-SHELL','FLAKES');

CREATE TABLE COMPATIBILITIES(
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  page_id UUID NOT NULL,
  system system_type NOT NULL,
  version VARCHAR(10) NOT NULL,
  FOREIGN KEY(page_id) REFERENCES PAGES(id)
);
```

### Características Principales
#### Seguridad e Integridad de Datos

- Claves Primarias UUID: Identificadores seguros y no secuenciales para todos los registros

- Cifrado de Contraseñas: Almacenamiento seguro de contraseñas (asume cifrado a nivel de aplicación)

- Unicidad de Email: Previene cuentas duplicadas

- Restricciones de Clave Externa: Mantiene la integridad referencial

#### Seguimiento Temporal

- Marcas de Tiempo Automáticas: Los campos creation, deletion y entry rastrean todos los eventos

- Registro de Actividad de Usuario: Capacidades completas de trail de auditoría

#### Características Específicas de Nix

- Soporte Multi-Sistema: Rastrea compatibilidad a través de:

**NIXPKGS - Paquetes Nix**

**NIXOS - Configuraciones del sistema NixOS**

**HOME-MANAGER - Gestión del entorno de usuario**

- Compatibilidad de Versiones: Registra versiones compatibles para cada página de documentación

#### Experiencia de Usuario

- Verificación por Email: Flujo seguro de activación de cuentas

- Sistema de Retroalimentación: Señales de calidad de contenido impulsadas por usuarios

- Eliminaciones Suaves: Preserva datos mientras permite la eliminación de cuentas
