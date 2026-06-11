/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Object.Prestamo;
import conexion.conexion_sqlite;
import java.sql.*;

/**
 *
 * @author Juan
 */
public class PrestamoController {

    public boolean registrarPrestamo(Prestamo p) {
        String sql = "INSERT INTO Prestamo(IdUser, IdBibliotecario, IdLibro, fecha_retorno) VALUES(?,?,?,?)";
        try (Connection conn = conexion_sqlite.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, p.getIdUser());
            pstmt.setInt(2, p.getIdBibliotecario());
            pstmt.setInt(3, p.getIdLibro());
            pstmt.setString(4, p.getFechaRetorno()); // Formato YYYY-MM-DD

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar préstamo: " + e.getMessage());
            return false;
        }
    }

    public java.util.List<Prestamo> listarPrestamos() {
        java.util.List<Prestamo> lista = new java.util.ArrayList<>();
        String sql = "SELECT * FROM Prestamo";

        try (Connection conn = conexion_sqlite.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Prestamo(
                        rs.getInt("IdPrestamo"),
                        rs.getInt("IdUser"),
                        rs.getInt("IdBibliotecario"),
                        rs.getInt("IdLibro"),
                        rs.getString("fecha_prestamo"),
                        rs.getString("fecha_retorno")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar préstamos: " + e.getMessage());
        }
        return lista;
    }

    public boolean registrarDevolucion(int idPrestamo, String fechaHoy) {
        String sql = "UPDATE Prestamo SET fecha_retorno = ? WHERE IdPrestamo = ?";
        try (Connection conn = conexion_sqlite.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fechaHoy);
            pstmt.setInt(2, idPrestamo);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar devolución: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idPrestamo) {
        String sql = "DELETE FROM Prestamo WHERE IdPrestamo = ?";
        try (Connection conn = conexion_sqlite.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idPrestamo);
            int filasAfectadas = pstmt.executeUpdate();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar préstamo: " + e.getMessage());
            return false;
        }
    }
}
