-- Script para crear la tabla de usuarios en PostgreSQL
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Índices para mejorar el rendimiento de búsquedas frecuentes
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);

-- Comentario sobre la elección de UUID vs BIGSERIAL:
/*
   Se eligió UUID en lugar de BIGSERIAL por las siguientes razones:
   1. Seguridad: Los UUIDs no son secuenciales, lo que dificulta adivinar IDs de usuarios
   2. Distribución: Facilita la futura distribución de datos entre múltiples servidores
   3. Privacidad: No revela información sobre el tamaño de la base de usuarios
   4. Consistencia: Permite generar IDs en el cliente antes de insertar en la base de datos
*/