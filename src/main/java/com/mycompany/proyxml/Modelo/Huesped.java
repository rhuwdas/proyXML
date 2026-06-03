package com.mycompany.proyxml.Modelo;
public class Huesped {
    int id;
    String nombre, apellido, procedencia;

    public Huesped() {
    }

    public Huesped(int id, String nombre, String apellido, String procedencia) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.procedencia = procedencia;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }
    
}
