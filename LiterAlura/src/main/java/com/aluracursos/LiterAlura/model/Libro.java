package com.aluracursos.LiterAlura.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="libro_id")
    private Long Id;
    @Column(unique = true)
    private String titulo;
    private String idioma;
    private Double numeroDeDescargas;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "libro_autor", joinColumns = @JoinColumn(name = "libro_id"), inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private List<Autor> autores= new ArrayList<>();

    public Libro() {
        // Constructor sin argumentos
    }
    public Libro(DatosLibro datosLibro){
        this.titulo= datosLibro.titulo();
        this.idioma= datosLibro.idiomas().toString();
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();

    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<DatosAutor> autores) {
        autores.forEach(a -> new Autor());
        this.autores = getAutores();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        return  "titulo=" + titulo +
                "autor='" + autores +
                "idioma=" + idioma+
                "numeroDescargas=" + numeroDeDescargas;

    }
}
