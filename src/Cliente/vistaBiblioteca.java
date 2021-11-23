package Cliente;

import java.util.ArrayList;
import java.util.Scanner;

public class vistaBiblioteca {

	private static final int CPOR_ISBN = 1, CPOR_TITULO = 2, CPOR_AUTOR = 3, NEW_LIBRO = 4, SALIR = 0;

	public void splitChorro() {

		ArrayList<Libro> libros = new ArrayList<Libro>();
		String chorro = "978-84-8314,Codigo Limpio,Robert C. Martin,47.45/978-84-8315,Clean Architecture,Robert C. Martin,31.37/978-84-8316,Patrones de Diseño,Erich Gamma,39.76/978-84-8317,Refactoring,Fowler Martin,49.39/978-84-8318,The Pragmatic Programmer,David Thomas,42.58/978-84-8319,Estructura de datos, Mark Weiss,54.95/465465,ellibrodeprueba,juanjo,60.52";

		String[] librosInfo = chorro.split("/");

		for (String libro : librosInfo) {
			String[] dataLibro = libro.split(",");
			libros.add(new Libro(dataLibro[0], dataLibro[1], dataLibro[2], Double.parseDouble(dataLibro[3])));
		}

		for (Libro libro : libros) {
			System.out.println(libro.getIsbn() + " | " + libro.getAutor() + " | " + libro.getTitulo() + " | "
					+ libro.getPrecio() + " | ");
		}
	}

	public static void main(String[] args) {
		mostrarMenu();
	}

	private static void mostrarMenu() {
		Scanner in = new Scanner(System.in);
		System.out.println("----------BIBLIOTECA----------\n");
		System.out.println("1. Consulta de libro por isbn");
		System.out.println("2. Consulta de libro por titulo");
		System.out.println("3. Consulta de libro por autor");
		System.out.println("4. Añadir libro a biblioteca");
		System.out.println("0. Salir de la aplicacion\n");
		System.out.println("Elige tu opcion:");
		int op = in.nextInt();
		ejecutarOpcion(op);

	}

	private static void ejecutarOpcion(int op) {
		switch (op) {
		case CPOR_ISBN: {

			
			break;
		}
		case CPOR_TITULO: {

			
			break;
		}
		case CPOR_AUTOR: {

			
			break;
		}
		case NEW_LIBRO: {

			
			break;
		}
		default:
			mostrarMenu();
		}

	}

}
