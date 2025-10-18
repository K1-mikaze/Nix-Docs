| Navegation | Navegacion |
| -------------- | --------------- |
| [Git Worflow](#Git-Workflow) | [Flujo de trabajo de Git](#Flujo-de-trabajo-de-Git) |


# Git Workflow

The Git Workflow of Nix Docs is defined in a special way, and has many different branches that can be sorted into Long-Running branches and Short-Running branches. The characteristic of Long-Running branches is that they only get new code using Merge and these branches are never deleted.

## Long-Running Branches

**main** Production branch containing stable, client-facing code

- Only receives code via merge from develop

- Represents the current production state

**merge** Staging branch for final testing before production

- Receives code via merge from short-running branches

- Used for integration testing

## Short-Running Branches

**front-end** - This branch is specialized in the front-end: HTML, CSS, TypeScript, and JavaScript.

**back-end** - This branch is specialized in the back-end: Java, Servlet, and Databases connections.

----------

# Flujo de trabajo de Git

Nix Docs implementa un flujo de trabajo de Git particular, organizado en diferentes ramas que se categorizan como de Larga Duración y Corta Duración. Las ramas de Larga Duración se distinguen porque únicamente incorporan código mediante operaciones de Merge y nunca son eliminadas.

## Ramas de Larga Duración

**main** - Rama de producción que contiene código estable para el cliente

- Solo recibe código mediante merge desde develop
- Representa el estado actual de producción

**merge** - Rama de staging para pruebas finales antes de producción

- Recibe código mediante merge desde las ramas de corta duración
- Utilizada para pruebas de integración

## Ramas de Corta Duración

**front-end** - Esta rama está especializada en el front-end: HTML, CSS, TypeScript y JavaScript.

**back-end** - Esta rama está especializada en el back-end: Java, Servlet y conexiones a bases de datos.


