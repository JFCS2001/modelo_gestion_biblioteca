/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import Object.Bibliotecario;
import Object.Cliente;
import Object.Libro;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Juan
 */
public class conexion_sqlite {

    private static final String URL = "jdbc:sqlite:biblioteca.bd";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver no encontrado: " + e);
        }
        return DriverManager.getConnection(URL);
    }

    public static void estructuraTablas() {
        String sqlBibliotecario = "CREATE TABLE IF NOT EXISTS Bibliotecario ("
                + "IdBibliotecario INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre TEXT NOT NULL,"
                + "correo TEXT UNIQUE,"
                + "celular TEXT,"
                + "usuario TEXT UNIQUE NOT NULL,"
                + "clave TEXT NOT NULL);";

        String sqlLibro = "CREATE TABLE IF NOT EXISTS Libro ("
                + "IdLibro INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "titulo TEXT NOT NULL,"
                + "autor TEXT NOT NULL,"
                + "anio_publ INTEGER,"
                + "categoria TEXT,"
                + "cantidad INTEGER DEFAULT 1,"
                + "isbn TEXT UNIQUE);";

        String sqlCliente = "CREATE TABLE IF NOT EXISTS Cliente ("
                + "IdCliente INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Nombre TEXT NOT NULL,"
                + "correo TEXT UNIQUE,"
                + "telefono TEXT,"
                + "direccion TEXT,"
                + "cantidad_multa REAL DEFAULT 0.0);";

        String sqlPrestamo = "CREATE TABLE IF NOT EXISTS Prestamo ("
                + "IdPrestamo INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "IdUser INTEGER,"
                + "IdBibliotecario INTEGER,"
                + "IdLibro INTEGER,"
                + "fecha_prestamo DATE DEFAULT CURRENT_DATE,"
                + "fecha_retorno DATE,"
                + "FOREIGN KEY(IdUser) REFERENCES Cliente(IdCliente),"
                + "FOREIGN KEY(IdBibliotecario) REFERENCES Bibliotecario(IdBibliotecario),"
                + "FOREIGN KEY(IdLibro) REFERENCES Libro(IdLibro));";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

            stmt.execute("PRAGMA foreign_keys = ON;");

            stmt.execute(sqlBibliotecario);
            stmt.execute(sqlLibro);
            stmt.execute(sqlCliente);
            stmt.execute(sqlPrestamo);

            System.out.println("Base de datos e hilos de tablas inicializados correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al crear la estructura: " + e.getMessage());
        }
    }

    public static void poblarDatosPrueba() {
        System.out.println("Insertando datos quemados de prueba...");

        controller.BibliotecarioController bController = new controller.BibliotecarioController();
        controller.LibroController lController = new controller.LibroController();
        controller.ClienteController cController = new controller.ClienteController();
        controller.PrestamoController pController = new controller.PrestamoController();

        Bibliotecario biblio = new Bibliotecario(0, "Juan Felipe", "juan@uts.edu.co", "3151234567", "admin", "1234");
        bController.insertar(biblio);

        Libro libro1 = new Libro(0, "Cien años de soledad", "Gabriel García Márquez", 1967, "Novela", 5, "978-0307474728");
        Libro libro2 = new Libro(0, "Don Quijote de la Mancha", "Miguel de Cervantes", 1605, "Clásico", 3, "978-8424938093");
        lController.insertar(libro1);
        lController.insertar(libro2);

        Cliente cliente1 = new Cliente(0, "Camila Gómez", "camila@gmail.com", "3209876543", "Calle 10 #15-20", 0.0);
        cController.insertar(cliente1);

        System.out.println("¡Datos de prueba guardados con éxito en biblioteca.bd!");
    }
}
