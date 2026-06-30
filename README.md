
# Trabajo-Practico-Integrador-Programacion-2-TUP-UTN
# Food Store – Sistema de Gestión de Pedidos

Aplicación de consola desarrollada en Java 21 con persistencia en MySQL mediante JDBC puro.
Permite gestionar categorías, productos, usuarios y pedidos desde un menú interactivo.

---

## Requisitos previos

- Java 21 o superior
- MySQL 8.0 o superior
- NetBeans IDE (recomendado) o cualquier IDE compatible con Java
- MySQL Connector/J (driver JDBC) — Descargar mediante link: https://dev.mysql.com/downloads/connector/j/

---

## 1. Crear la base de datos

Abrí MySQL Workbench o tu cliente de MySQL preferido y ejecutá:

```sql
CREATE DATABASE foodstore;
USE foodstore;
```

Luego ejecutá el archivo `schema.sql` incluido en la raíz del proyecto:

```sql
SOURCE /ruta/al/proyecto/schema.sql;
```

O abrí el archivo en MySQL Workbench y ejecutalo desde ahí.

Esto va a crear las tablas `categoria`, `producto`, `usuario`, `pedido` y `detalle_pedido`,
e insertar datos de prueba para poder testear el sistema desde el primer arranque.

---

## 2. Configurar la conexión

Abrí el archivo `src/main/resources/persistence.properties` y completá con tus datos:

```properties
db.url=jdbc:mysql://localhost:3306/foodstore
db.usuario=root
db.password=tu_password_aqui
db.driver=com.mysql.cj.jdbc.Driver
```

---

## 3. Agregar el driver MySQL al proyecto

### En NetBeans:

1. Clic derecho sobre el proyecto → **Properties**
2. **Libraries** → **Add JAR/Folder**
3. Seleccioná el archivo `mysql-connector-j-X.X.X.jar` que descargaste
4. Aceptar

### En Maven (si usás `pom.xml`):

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.3.0</version>
</dependency>
```

---

## 4. Ejecutar el proyecto

### Desde NetBeans:

1. Clic derecho sobre el proyecto → **Run** (o `F6`)
2. La clase principal es `Main.java`

### Desde la terminal:

```bash
javac -cp ".;mysql-connector-j-X.X.X.jar" -d out src/**/*.java
java  -cp ".;out;mysql-connector-j-X.X.X.jar" Main
```

> En Linux/Mac reemplazá `;` por `:` en el classpath.

---

## 5. Estructura del proyecto

```
src/
    ├── foodstore/
    │   └── Consola.java
    │   └── Main.java
    │   └── MenuCategoria.java
    │   └── MenuPedido.java
    │   └── MenuPrincipal.java
    │   └── MenuProducto.java
    │   └── MenuUsuario.java
    ├── foodstore.config/
    │   └── ConexionDB.java
    ├── foodstore.dao/
    │   ├── IBaseDAO.java
    │   ├── CategoriaDAO.java
    │   ├── ProductoDAO.java
    │   ├── UsuarioDAO.java
    │   └── PedidoDAO.java
    ├── foodstore.entities/
    │   ├── Base.java
    │   ├── Calculable.java
    │   ├── Categoria.java
    │   ├── Producto.java
    │   ├── Usuario.java
    │   ├── DetallePedido.java
    │   └── Pedido.java
    ├── foodstore.enums/
    │   ├── Rol.java
    │   ├── Estado.java
    │   └── FormaPago.java
    ├── foodstore.exception/
    │   └── ValidacionException.java
    ├── foodstore.service/
    │   ├── CategoriaService.java
    |   ├── DetallePedidoService.java
    │   ├── GenercService.java
    │   ├── ProductoService.java
    │   ├── UsuarioService.java
    │   └── PedidoService.java
    ├── lib/
    │   ├── Schema.sql
    │   └── mysql-connector-j-9.7.0.jar
    ├── resources/
        └── db.properties
README.md
```

---

## 6. Links

- Repositorio: https://github.com/santistebanpilar-frm/Trabajo-Practico-Integrador-Programacion-2-TUP-UTN
- Informe: https://docs.google.com/document/d/1HKgtPcNXCqfHOQTdXxvAn4IsU1GUYOwEGsLWJjRFkng/edit?usp=sharing 

---

## Autores

|       Nombre       |     Legajo   |
|--------------------|--------------|
| Caceres Julieta    |     53.908   |
| Maigua Matias      |     53.899   | 
| Santisteban Pilar  |     53.949   | 




