package Cliente;

import java.util.ArrayList;


public class vistaBiblioteca {

	public void splitChorro() {
		
		ArrayList <Libro> libros = new ArrayList <Libro>();
		String chorro="978-84-8314,Codigo Limpio,Robert C. Martin,47.45/978-84-8315,Clean Architecture,Robert C. Martin,31.37/978-84-8316,Patrones de Diseño,Erich Gamma,39.76/978-84-8317,Refactoring,Fowler Martin,49.39/978-84-8318,The Pragmatic Programmer,David Thomas,42.58/978-84-8319,Estructura de datos, Mark Weiss,54.95/465465,ellibrodeprueba,juanjo,60.52";
			
		 String [] librosInfo = chorro.split("/");
		 
		 
		 for (String libro : librosInfo) {
			 String [] dataLibro= libro.split(",");
			 libros.add(new Libro(dataLibro[0],dataLibro[1],dataLibro[2],Double.parseDouble(dataLibro[3])));
		}
		
		 
		 for (Libro libro : libros) {
			 System.out.println(libro.getIsbn()+" | "+libro.getAutor()+" | "+libro.getTitulo()+" | "+libro.getPrecio()+" | ");
		}
	}

}
