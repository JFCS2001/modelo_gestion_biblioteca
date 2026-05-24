/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Object.Bibliotecario;
import java.sql.*;
import conexion.conexion_sqlite;

/**
 *
 * @author Juan
 */
public class BibliotecarioController {
    public boolean insertar(Bibliotecario b) {
        String sql = "INSERT INTO Bibliotecario(nombre, correo, celular, usuario, clave) VALUES(?,?,?,?,?)";
        try (Connection conn = conexion_sqlite.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, b.getNombre());
            pstmt.setString(2, b.getCorreo());
            pstmt.setString(3, b.getCelular());
            pstmt.setString(4, b.getUsuario());
            pstmt.setString(5, b.getClave());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error Bibliotecario: " + e.getMessage());
            return false;
        }
    }
    public boolean validarLogin(String usuario, String clave) {
        String sql = "SELECT * FROM Bibliotecario WHERE usuario = ? AND clave = ?";
        try (Connection conn = conexion_sqlite.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario);
            pstmt.setString(2, clave);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Retorna true si encontró coincidencia, false si no
            }
        } catch (SQLException e) {
            System.err.println("Error en login: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Bibliotecario b) {
        String sql = "UPDATE Bibliotecario SET nombre = ?, correo = ?, celular = ?, clave = ? WHERE IdBibliotecario = ?";
        try (Connection conn = conexion_sqlite.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, b.getNombre());
            pstmt.setString(2, b.getCorreo());
            pstmt.setString(3, b.getCelular());
            pstmt.setString(4, b.getClave());
            pstmt.setInt(5, b.getId());
            
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar bibliotecario: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idBibliotecario) {
        String sql = "DELETE FROM Bibliotecario WHERE IdBibliotecario = ?";
        try (Connection conn = conexion_sqlite.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idBibliotecario);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar bibliotecario: " + e.getMessage());
            return false;
        }
    }
}
