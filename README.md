
# 📚 Sistema de Gestión Bibliotecaria - Java Swing

Este proyecto es una aplicación de escritorio robusta desarrollada en **Java SE**, diseñada para automatizar y optimizar el control de inventario, socios y transacciones dentro de una biblioteca. Cuenta con una interfaz gráfica moderna, intuitiva y consistente que facilita el flujo de trabajo del personal bibliotecario.

---

## 🚀 Características Principales

* **Interfaz de Usuario Consistente:** Diseño simétrico con paneles de navegación superior (`JTabbedPane`), tablas de visualización interactiva a la izquierda (`JTable`) y formularios de acción rápida a la derecha.
* **Control Total (CRUD):** Gestión completa de registros (Crear, Leer, Actualizar, Borrar) para los módulos base de inventario y usuarios.
* **Lógica Transaccional de Negocio:** El sistema valida automáticamente la disponibilidad de stock, calcula fechas límite de entrega y organiza la lista de espera para libros agotados.
* **Seguridad Básica:** Confirmación de acciones críticas (como eliminación de registros y cierre de sesión) mediante cuadros de diálogo integrados (`JOptionPane`).

---

## 📂 Estructura del Proyecto (Módulos de Navegación)

El sistema se organiza en las siguientes secciones a través de su menú de pestañas:

* **Libros:** Gestión del catálogo bibliográfico (Título, Editorial, Año, Categoría y control de `Copias Disponibles`).
* **Lectores:** Registro y actualización de los datos de contacto de los socios de la biblioteca (Nombre, Dirección, Teléfono, Correo).
* **Préstamos:** Módulo operativo para registrar la salida de libros. Resta automáticamente una unidad al stock disponible del libro seleccionado y calcula la fecha de vencimiento.
* **Reservas:** Lista de espera exclusiva para usuarios que necesitan apartar libros cuyo stock actual es cero ($0$).
* **Salir:** Botón de desconexión segura con un mensaje informativo de confirmación antes de destruir la sesión de la aplicación.

---

## 🧰 Tecnologías Usadas

* **Lenguaje de Programación:** Java SE (Standard Edition) 24.
* **Diseño de Interfaz Gráfica (GUI):** Java Swing & AWT (Abstract Window Toolkit).
* **Base de Datos:** MySQL / MariaDB (o SQLite según configuración de persistencia).
* **Conectividad:** JDBC (Java Database Connectivity) Driver.
* **Patrón de Diseño:** Arquitectura orientada a objetos con separación de responsabilidades (Vistas, Modelos y Controladores DAO).

---

## 🏗️ Modelo de la Base de Datos (DER)

El sistema se fundamenta en un modelo relacional que asegura la integridad de los datos en cada transacción:

```mermaid
erDiagram
    LIBRO ||--o{ PRESTAMO : "se registra en"
    USER ||--o{ PRESTAMO : "realiza"
    BIBLIOTECARIO ||--o{ PRESTAMO : "autoriza"
    PRESTAMO ||--o| MULTA : "puede generar"
    PRESTAMO ||--o| RESERVA : "se origina de"

    LIBRO {
        int IdLibro PK
        string Titulo
        string Autor
        int AñoPubl
        string Categoria
        int CopiasDispo
        string ISBN
    }
    USER {
        int IdUser PK
        string Nombre
        string correo
        string Celular
        string Direccion
    }
    PRESTAMO {
        int IdPrestamo PK
        int IdUser FK
        int IdBibliotecario FK
        int IdLibro FK
        date fecha_prestamo
        date fecha_retorno
        date fecha_vencimiento
    }
    MULTA {
        int IdMulta PK
        int IdPrestamo FK
        double valor
        date FechaVencimiento
    }
    RESERVA {
        int IdReserva PK
        int IdPrestamo FK
        date FechaReserva
    }
    BIBLIOTECARIO {
        int IdBibliotecario PK
        string correo
        string celular
    }
