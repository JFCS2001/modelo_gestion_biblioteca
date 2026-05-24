/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import conexion.conexion_sqlite;
import Object.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juan
 */
public class LibroController {
    
    public boolean insertar(Libro libro) {
        String sql = "INSERT INTO Libro(titulo, autor, anio_publ, categoria, cantidad, isbn) VALUES(?,?,?,?,?,?)";
        try (Connection conn = conexion_sqlite.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, libro.getTitulo());
            pstmt.setString(2, libro.getAutor());
            pstmt.setInt(3, libro.getAnio());
            pstmt.setString(4, libro.getCategoria());
            pstmt.setInt(5, libro.getCantidad());
            pstmt.setString(6, libro.getIsbn());
            
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
            return false;
        }
    }

    
    public List<Libro> listar() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM Libro";
        try (Connection conn = conexion_sqlite.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                lista.add(new Libro(
                    rs.getInt("IdLibro"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getInt("anio_publ"),
                    rs.getString("categoria"),
                    rs.getInt("cantidad"),
                    rs.getString("isbn")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }

    
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Libro WHERE IdLibro = ?";
        try (Connection conn = conexion_sqlite.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }
}
