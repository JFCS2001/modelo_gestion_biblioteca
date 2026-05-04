/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Object;

/**
 *
 * @author Juan
 */
public class Prestamo {

    private int id;
    private int idUser;
    private int idBibliotecario;
    private int idLibro;
    private String fechaPrestamo;
    private String fechaRetorno;

    public Prestamo(int id, int idUser, int idBibliotecario, int idLibro, String fechaPrestamo, String fechaRetorno) {
        this.id = id;
        this.idUser = idUser;
        this.idBibliotecario = idBibliotecario;
        this.idLibro = idLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaRetorno = fechaRetorno;
    }

    public int getId() {
        return id;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getIdBibliotecario() {
        return idBibliotecario;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public String getFechaRetorno() {
        return fechaRetorno;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setIdBibliotecario(int idBibliotecario) {
        this.idBibliotecario = idBibliotecario;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public void setFechaRetorno(String fechaRetorno) {
        this.fechaRetorno = fechaRetorno;
    }

}
