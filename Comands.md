POSTGRESQL VERSIÓN: postgresql-18.0-1-windows-x64-binaries

* Inicializar la base de datos
    
   1 . initdb -D "C:\Program Files (x86)\postgresql-18.0-1-windows-x64-binaries\pgsql\data" -U postgres -W

* Iniciar el servidor de PostgreSQL

    1. pg_ctl -D "C:\Program Files (x86)\postgresql-18.0-1-windows-x64-binaries\pgsql\data" -l logfile start

    2. psql -U postgres





