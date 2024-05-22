package com.aluracursos.LiterAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="autor_id")
    private Long Id;
    @Column(name="nombre")
    private String nombre;
    private Integer fechaDeNacimiento;
    private Integer fechaDeDefuncion;

    @ManyToMany(mappedBy = "autores")
    public List<Libro> libros =new ArrayList<>();


    public Autor(String nombre, Integer fechaDeNacimiento, Integer fechaDeDefuncion) {
        this.nombre = nombre;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.fechaDeDefuncion = fechaDeDefuncion;
    }

    public Integer getFechaDeDefuncion() {
        return fechaDeDefuncion;
    }

    public void setFechaDeDefuncion(Integer fechaDeDefuncion) {
        this.fechaDeDefuncion = fechaDeDefuncion;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Autor() {};


        @Override
    public String toString() {
        return "nombre=    " + nombre +
                "fecha de nacimiento=   " + fechaDeNacimiento+
                "fecha de fallecimeinto " + fechaDeDefuncion +
                "id libro=   " + getId();
    }


}


