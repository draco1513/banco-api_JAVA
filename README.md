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

-- Crear la base de datos
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'banco_db')
BEGIN
CREATE DATABASE banco_db;
GO

-- Usar la base de datos
USE banco_db;
GO

-- Tabla: persona
CREATE TABLE persona (
    id BIGINT PRIMARY KEY,
    nombre NVARCHAR(255),
    genero NVARCHAR(50),
    edad INT,
    identificacion NVARCHAR(20),
    direccion NVARCHAR(255),
    telefono NVARCHAR(20)
);
GO

-- Tabla: cliente
CREATE TABLE cliente (
    id BIGINT PRIMARY KEY,
    cliente_id BIGINT FOREIGN KEY REFERENCES persona(id),
    contrasena NVARCHAR(255),
    estado BIT
);
GO

-- Tabla: cuenta
CREATE TABLE cuenta (
    id BIGINT PRIMARY KEY,
    numero_cuenta NVARCHAR(20),
    tipo_cuenta NVARCHAR(20),
    saldo_inicial DECIMAL(19,2),
    estado BIT,
    cliente_id BIGINT FOREIGN KEY REFERENCES cliente(id)
);
GO

-- Tabla: movimiento
CREATE TABLE movimiento (
    id BIGINT PRIMARY KEY,
    fecha DATETIME2(7),
    tipo_movimiento NVARCHAR(20),
    valor DECIMAL(19,2),
    saldo DECIMAL(19,2),
    cuenta_id BIGINT FOREIGN KEY REFERENCES cuenta(id)
);
GO

-- Tabla: transferencia
CREATE TABLE transferencia (
    id BIGINT PRIMARY KEY,
    cuenta_origen_id BIGINT,
    cuenta_destino_id BIGINT,
    monto DECIMAL(19,2),
    fecha DATETIME2(7),
    FOREIGN KEY (cuenta_origen_id) REFERENCES cuenta(id),
    FOREIGN KEY (cuenta_destino_id) REFERENCES cuenta(id)
);
GO


```

## Endpoints de la API

A continuación, se describen los principales endpoints de la API:

- `GET /clientes`: Obtiene una lista de todos los clientes.
- `GET /clientes/{id}`: Obtiene un cliente por su ID.
- `POST /clientes`: Crea un nuevo cliente.
- `PUT /clientes/{id}`: Actualiza un cliente existente.
- `DELETE /clientes/{id}`: Elimina un cliente.
- `GET /cuentas`: Obtiene una lista de todas las cuentas.
- `POST /transferencias`: Realiza una nueva transferencia.
