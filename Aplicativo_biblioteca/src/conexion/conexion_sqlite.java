/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

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

        try (Connection conn = getConnection(); 
             Statement stmt = conn.createStatement()) {
            
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
}