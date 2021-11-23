package Cliente;

import java.util.ArrayList;
import java.util.Scanner;

public class vistaBiblioteca {

	private static final int CPOR_ISBN = 1, CPOR_TITULO = 2, CPOR_AUTOR = 3, NEW_LIBRO = 4, TODOSLOS_LIBROS = 5,
			SALIR = 0;
	private static Scanner in = new Scanner(System.in);
	private static SocketCliente sock = new SocketCliente();

	public static ArrayList<Libro> splitChorro(String callChorroType) {
		
		ArrayList<Libro> libros = new ArrayList<Libro>();
		sock.setChorro(callChorroType);
		String chorro = sock.enviarChorro();
		String[] librosInfo = chorro.split("/");

		for (String libro : librosInfo) {
			String[] dataLibro = libro.split(",");
			libros.add(new Libro(dataLibro[0], dataLibro[1], dataLibro[2], Double.parseDouble(dataLibro[3])));
		}
		
		
		return libros;

		
	}

	public static void main(String[] args) {
		mostrarMenu();
	}
	
	public static void mostrarLibros(ArrayList<Libro> lista) {
		
		for (Libro libro : lista) {
			System.out.println(libro.getIsbn() + " | " + libro.getAutor() + " | " + libro.getTitulo() + " | "
					+ libro.getPrecio());
		}
		
	}

	private static void mostrarMenu() {
		
		System.out.println("----------BIBLIOTECA----------\n");
		System.out.println("1. Consulta de libro por isbn");
		System.out.println("2. Consulta de libro por titulo");
		System.out.println("3. Consulta de libro por autor");
		System.out.println("4. Añadir libro a biblioteca");
		System.out.println("5. Consultar TODOS los libros");
		System.out.println("0. Salir de la aplicacion\n");
		System.out.println("Elige tu opcion:");
		int op = in.nextInt();
		ejecutarOpcion(op);

	}

	private static void ejecutarOpcion(int op) {
		switch (op) {
		case CPOR_ISBN: {
			
			System.out.println("Introduzca el ISBN a buscar:");
			String ISBN= in.nextLine();
			mostrarLibros(splitChorro(ISBN));

			break;
		}
		case CPOR_TITULO: {
			System.out.println("Introduzca el TITULO a buscar:");
			String TITULO= in.nextLine();
			mostrarLibros(splitChorro(TITULO));
			break;
		}
		case CPOR_AUTOR: {
			System.out.println("Introduzca el AUTOR a buscar:");
			String AUTOR= in.nextLine();
			mostrarLibros(splitChorro(AUTOR));
			break;
		}
		case NEW_LIBRO: {

			break;
		}

		case TODOSLOS_LIBROS: {
			
			mostrarLibros(splitChorro("todos"));
			break;
		}
		default:
			mostrarMenu();
		}

	}

}
