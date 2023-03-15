# 🐕 Mundo Peludo - Pet Shop System

Sistema de gestión para pet shop con arquitectura MVC en Java Swing.

## 📋 Descripción

Mundo Peludo es una aplicación de escritorio desarrollada en Java que permite gestionar las operaciones de una tienda de mascotas, incluyendo clientes, productos, órdenes de venta y usuarios del sistema.

## 🗂️ Estructura del Proyecto

```
MundoPeludo/
├── src/
│   ├── Controlador/        # Lógica de control (MVC)
│   │   ├── LoginControl.java
│   │   ├── ClienteControl.java
│   │   ├── ProductoControl.java
│   │   ├── OrdenControl.java
│   │   ├── CategoriaControl.java
│   │   └── UsuarioControl.java
│   ├── Modelo/             # Acceso a datos y conexión BD
│   │   ├── Conexion.java
│   │   ├── ClienteModel.java
│   │   ├── ProductoModel.java
│   │   ├── OrdenModel.java
│   │   └── UsuarioModel.java
│   ├── Vista/              # Interfaces gráficas (Swing)
│   ├── MVC/                # Clase principal
│   │   └── MVC.java
│   └── Img/                # Recursos gráficos
├── lib/
│   ├── itextpdf-5.5.13.2.jar
│   └── mysql-connector-j-8.0.33.jar
└── bdmundopeludo.sql       # Script de base de datos
```

## 🛠️ Tecnologías

- **Lenguaje:** Java 8+
- **Interfaz gráfica:** Java Swing (Absolute Layout)
- **Base de datos:** MySQL 8.0
- **Generación de PDF:** iTextPDF 5.5.13
- **Conector BD:** MySQL Connector/J 8.0.33
- **IDE:** Apache NetBeans 19

## ⚙️ Módulos del Sistema

| Módulo | Descripción |
|--------|-------------|
| 🔐 Login | Autenticación de usuarios |
| 👥 Clientes | Gestión de clientes |
| 📦 Productos | Control de inventario |
| 🛒 Órdenes | Registro de ventas |
| 📄 Detalle Orden | Detalle de cada venta |
| 🏷️ Categorías | Categorías de productos |
| 👤 Usuarios | Administración de usuarios |

## 🚀 Instalación y Ejecución

### Requisitos previos
- Java JDK 8 o superior
- MySQL Server 8.0
- Apache NetBeans 19

### Paso 1 — Clonar el repositorio
```bash
git clone https://github.com/YoshiiStar/ProyectoPetshop.git
```

### Paso 2 — Configurar la base de datos
1. Iniciar MySQL Server
2. Importar el archivo `bdmundopeludo.sql` en MySQL Workbench o phpMyAdmin
3. Verificar que la base de datos `bdmundopeludo` fue creada correctamente

### Paso 3 — Configurar la conexión
Editar el archivo `src/Modelo/Conexion.java`:
```java
String usuario = "root";
String password = "tu_contraseña";
```

### Paso 4 — Abrir en NetBeans
1. `File → Open Project` → seleccionar carpeta `MundoPeludo`
2. Agregar los JARs en `Project Properties → Libraries`:
   - `lib/itextpdf-5.5.13.2.jar`
   - `lib/mysql-connector-j-8.0.33.jar`
3. Ejecutar con **F6**

## 🔑 Credenciales de prueba

| Usuario | Contraseña |
|---------|------------|
| `win` | `123456` |
| `cgomez` | `petshop456` |
| `mrodriguez` | `petshop789` |

## 🗄️ Base de Datos

```
bdmundopeludo/
├── categoria
├── cliente
├── producto
├── orden
├── detalle_orden
└── usuario
```

