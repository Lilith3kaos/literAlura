package com.aluracursos.LiterAlura.principal;

import com.aluracursos.LiterAlura.model.*;
import com.aluracursos.LiterAlura.repository.AutorRepository;
import com.aluracursos.LiterAlura.repository.LibroRepository;
import com.aluracursos.LiterAlura.service.ConsumoAPI;
import com.aluracursos.LiterAlura.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;


public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private LibroRepository repositorioL;
    private AutorRepository repositorioA;
    private List<Libro> libros;
    private List<Autor> autores;


    public Principal(LibroRepository libroRepositorio, AutorRepository autorRepositorio) {
        this.repositorioL = libroRepositorio;
        this.repositorioA = autorRepositorio;
    }


    public void muestraElMenu() {

        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar Libro en el servidor Gutendex
                    2 - Listar Libros registrados 
                    3 - Listar Autores registrados
                    4 - Listar Autores vivos por año
                    5 - Listar Libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroTitulo();
                    break;
                case 2:
                    consultarBaseLocal();
                    break;
                case 3:
                    consultarBaseLocalAutores();
                    break;
                case 4:
                    listarAutoresxAnio();
                    break;
                case 5:
                    listarLibrosxIdioma();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibroTitulo() {
        System.out.println("Ingresa el nombre/titulo libro :");
        var tituloLibro = teclado.next();
        teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));

        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()) {
            System.out.println("Libro encontrado");
            System.out.println(libroBuscado.get());
            DatosLibro libroEncontrado = libroBuscado.get();
            List<DatosAutor> autores = libroEncontrado.autor();
            // autores.forEach(System.out::println);
            Libro libro = new Libro(libroEncontrado);
            libro.setAutores(autores);
            repositorioL.save(libro);
            for (DatosAutor datosAutor : autores) {
                Autor autor = new Autor(datosAutor.nombre(), datosAutor.fechaDeNacimiento(), datosAutor.fechaDeDefuncion());
                repositorioA.save(autor);
            }

        } else {
            System.out.println("Libro no encontrado");
        }
    }

    private void consultarBaseLocal() {
        libros = repositorioL.findAll();
        for (Libro libro : libros) {
            System.out.println(libro);
        }
    }

    private void consultarBaseLocalAutores() {
        autores = repositorioA.findAll();
        for (Autor autor : autores) {
            System.out.println(autor);
        }

    }

    private void listarAutoresxAnio() {
        System.out.println("Ingresa el año:");
        int anioAutor = Integer.valueOf(teclado.next());
        teclado.nextLine();
        // Obtener todos los autores de la base de datos
        List<Autor> autores = repositorioA.findAll();

        // Filtrar los autores que están vivos en el año especificado
        List<Autor> autoresVivos = autores.stream()
                .filter(autor -> autor.getFechaDeNacimiento() <= anioAutor) // Nacieron antes o en el año especificado
                .filter(autor -> autor.getFechaDeDefuncion() == null || autor.getFechaDeDefuncion() > anioAutor) // No fallecieron o fallecieron después del año especificado
                .collect(Collectors.toList());

        // Imprimir los autores vivos en el año especificado
        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anioAutor);
        } else {
            System.out.println("Autores vivos en el año " + anioAutor + ":");
            autoresVivos.forEach(System.out::println);
        }
    }
    private void listarLibrosxIdioma(){
        System.out.println("Ingresa el idioma:");
        String idioma = teclado.next();
        teclado.nextLine();
        List<Libro> libroidioma = repositorioL.findByIdioma(idioma);
        for (Libro libro : libroidioma) {
            System.out.println(libro);
        }

    }
}
