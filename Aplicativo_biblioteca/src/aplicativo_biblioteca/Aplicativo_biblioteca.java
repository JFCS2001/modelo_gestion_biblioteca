/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package aplicativo_biblioteca;

import javax.swing.SwingUtilities;
import vista_biblioteca.Loguin;
import conexion.conexion_sqlite;

/**
 *
 * @author Juan
 */
public class Aplicativo_biblioteca {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        conexion_sqlite.estructuraTablas();
        
        conexion_sqlite.poblarDatosPrueba();
        
        SwingUtilities.invokeLater(() -> new Loguin().setVisible(true));
    }
    
}
