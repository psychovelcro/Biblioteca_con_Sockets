package Servidor.testDao;

import java.util.List;

import Servidor.modelo.DAO.BibliotecaDaoImpl;
import Servidor.modelo.entidad.Libro;

public class TestDao {

	public static void main(String[] args) {
		Libro libro = new Libro();
//		libro.setIsbn("978-84-8314");
//		libro.setTitulo("Codigo Limpio");
//		libro.setAutor("Robert C. Martin");
//		libro.setPrecio(47.45);
//
//		Libro libro2 = new Libro();
//		libro2.setIsbn("978-84-8315");
//		libro2.setTitulo("Clean Architecture");
//		libro2.setAutor("Robert C. Martin");
//		libro2.setPrecio(31.37);
//		
//		Libro libro3 = new Libro();
//		libro3.setIsbn("978-84-8316");
//		libro3.setTitulo("Patrones de Diseño");
//		libro3.setAutor("Erich Gamma");
//		libro3.setPrecio(39.76);
//		
//		Libro libro4 = new Libro();
//		libro4.setIsbn("978-84-8317");
//		libro4.setTitulo("Refactoring");
//		libro4.setAutor("Fowler Martin");
//		libro4.setPrecio(49.39);
//		
//		Libro libro5 = new Libro();
//		libro5.setIsbn("978-84-8318");
//		libro5.setTitulo("The Pragmatic Programmer");
//		libro5.setAutor("David Thomas");
//		libro5.setPrecio(42.58);

		BibliotecaDaoImpl biblioteca = new BibliotecaDaoImpl();

//		boolean alta = biblioteca.alta(libro5);
//		if (alta) {
//			System.out.println("El libro se ha dado de alta");
//		} else {
//			System.out.println("El libro NO se ha dado de alta");
//		}

		// listar todos los libros
//		List<Libro> listaLibros = 	biblioteca.listar();
//		for(Libro l: listaLibros){
//			System.out.println(l);
//
//		List<Libro> listaLibros = biblioteca.getByAuthor("Robert C. Martin");
//		for (Libro l : listaLibros) {
//			System.out.println(l);
//
//		}
		
		System.out.println(biblioteca.getByTitle("Codigo Limpio"));
		System.out.println(biblioteca.getByIsbn("978-84-8317"));
		
		
		
	}
}
