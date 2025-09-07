# Banco API JAVA

API REST para la gestión de un sistema bancario, desarrollada con Java y Spring Boot.

## Tabla de Contenidos

- [Requisitos Previos](#requisitos-previos)
- [Instalación y Levantamiento Local](#instalación-y-levantamiento-local)
- [Configuración de la Base de Datos](#configuración-de-la-base-de-datos)
- [Endpoints de la API](#endpoints-de-la-api)

## Requisitos Previos

Para poder ejecutar este proyecto, necesitarás tener instalado el siguiente software en tu máquina:

- **Java Development Kit (JDK)**: Versión 11 o superior.
- **Apache Maven**: Para la gestión de dependencias y la compilación del proyecto.
- **SQL Server**: Como sistema de gestión de bases de datos.
- **Git**: Para clonar el repositorio.
- **Un IDE de tu preferencia**: Como IntelliJ IDEA, Eclipse o VS Code.

## Instalación y Levantamiento Local

Sigue estos pasos para configurar y ejecutar el proyecto en tu entorno local:

1.  **Clona el repositorio:**
    ```bash
    git clone [https://github.com/draco1513/banco-api_JAVA.git](https://github.com/draco1513/banco-api_JAVA.git)
    ```

2.  **Navega al directorio del proyecto:**
    ```bash
    cd banco-api_JAVA
    ```

3.  **Configura la conexión a SQL Server:**
    - Abre el archivo `src/main/resources/application.properties`.
    - Modifica las siguientes propiedades para que coincidan con tu configuración de SQL Server:
      ```properties
      spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=banco_db;encrypt=true;trustServerCertificate=true
      spring.datasource.username=tu_usuario
      spring.datasource.password=tu_contraseña
      spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
      ```
    > **Nota:** Se han añadido los parámetros `encrypt=true` y `trustServerCertificate=true` a la URL de conexión, ya que son comúnmente necesarios para las versiones más recientes del driver de SQL Server para evitar problemas de conexión SSL.

4.  **Instala las dependencias del proyecto:**
    ```bash
    mvn clean install
    ```

5.  **Ejecuta la aplicación:**
    ```bash
    mvn spring-boot:run
    ```

    Una vez ejecutado, la API estará disponible en `http://localhost:8080`.

## Configuración de la Base de Datos

Para crear la base de datos y las tablas necesarias en SQL Server, ejecuta el script que se proporciona [aquí](#archivo-databasesql-para-sql-server).

## Archivo `database.sql` para SQL Server

A continuación, se presenta el script SQL que debes ejecutar en tu servidor de **SQL Server** para crear la base de datos y las tablas:

```sql
-- Creación de la base de datos
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'banco_db')
BEGIN
    CREATE DATABASE banco_db;
END
GO

USE banco_db;
GO

-- Creación de la tabla de clientes
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='clientes' and xtype='U')
BEGIN
    CREATE TABLE clientes (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        nombre NVARCHAR(255) NOT NULL,
        email NVARCHAR(255) UNIQUE NOT NULL
        -- Agrega aquí otros campos relevantes para los clientes
    );
END
GO

-- Creación de la tabla de cuentas
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='cuentas' and xtype='U')
BEGIN
    CREATE TABLE cuentas (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        numero_cuenta NVARCHAR(255) UNIQUE NOT NULL,
        saldo DECIMAL(18, 2) NOT NULL,
        cliente_id BIGINT,
        FOREIGN KEY (cliente_id) REFERENCES clientes(id)
        -- Agrega aquí otros campos relevantes para las cuentas
    );
END
GO

-- Creación de la tabla de transferencias
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='transferencias' and xtype='U')
BEGIN
    CREATE TABLE transferencias (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        cuenta_origen_id BIGINT,
        cuenta_destino_id BIGINT,
        monto DECIMAL(18, 2) NOT NULL,
        fecha DATETIME NOT NULL,
        FOREIGN KEY (cuenta_origen_id) REFERENCES cuentas(id),
        FOREIGN KEY (cuenta_destino_id) REFERENCES cuentas(id)
        -- Agrega aquí otros campos relevantes para las transferencias
    );
END
GO

```sql

## Endpoints de la API

A continuación, se describen los principales endpoints de la API:

- `GET /clientes`: Obtiene una lista de todos los clientes.
- `GET /clientes/{id}`: Obtiene un cliente por su ID.
- `POST /clientes`: Crea un nuevo cliente.
- `PUT /clientes/{id}`: Actualiza un cliente existente.
- `DELETE /clientes/{id}`: Elimina un cliente.
- `GET /cuentas`: Obtiene una lista de todas las cuentas.
- `POST /transferencias`: Realiza una nueva transferencia.
