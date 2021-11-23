package Cliente;

import java.util.ArrayList;
import java.util.Scanner;

public class vistaBiblioteca {

	private static final int CPOR_ISBN = 1, CPOR_TITULO = 2, CPOR_AUTOR = 3, NEW_LIBRO = 4, TODOSLOS_LIBROS = 5,
			MODIFICAR_LIB = 6, ELIMINAR_LIB = 7, SALIR = 0;
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
	
	public static void operationNoReturn(String callChorroType) {
		
		sock.setChorro(callChorroType);
		String chorro = sock.enviarChorro();
		
	}

	public static void main(String[] args) {
		mostrarMenu();
	}

	public static void mostrarLibros(ArrayList<Libro> lista) {

		for (Libro libro : lista) {
			System.out.println(
					libro.getIsbn() + " | " + libro.getAutor() + " | " + libro.getTitulo() + " | " + libro.getPrecio());
		}

	}

	private static void mostrarMenu() {

		System.out.println("----------BIBLIOTECA----------\n");
		System.out.println("1. Consulta de libro por isbn");
		System.out.println("2. Consulta de libro por titulo");
		System.out.println("3. Consulta de libro por autor");
		System.out.println("4. Añadir libro a biblioteca");
		System.out.println("5. Consultar TODOS los libros");
		System.out.println("6. MODIFICAR libro");
		System.out.println("7. ELIMINAR libro");
		System.out.println("0. Salir de la aplicacion\n");
		System.out.println("Elige tu opcion:");
		int op = in.nextInt();
		ejecutarOpcion(op);

	}

	private static void ejecutarOpcion(int op) {
		switch (op) {
		case CPOR_ISBN: {

			buscarPor("ISBN", "porisbn");

			break;
		}
		case CPOR_TITULO: {
			buscarPor("TITULO", "portitulo");

			break;
		}
		case CPOR_AUTOR: {
			buscarPor("AUTOR", "porautor");

			break;
		}
		case NEW_LIBRO: {
			operationNoReturn(introducirLibro());
			
			break;
		}

		case TODOSLOS_LIBROS: {

			mostrarLibros(splitChorro("listar"));
			break;
		}
		case MODIFICAR_LIB: {
			operationNoReturn(ModificarLibro());

			
			break;
		}
		case ELIMINAR_LIB: {
			
			operationNoReturn(eliminarLibro());

			break;
		}
		default:
			mostrarMenu();
		}

	}

	private static String eliminarLibro() {
		System.out.println("Introduzca ISBN del libro que quiere eliminar");
		String isbn= in.nextLine();
		return "baja,"+isbn;
		
	}

	private static String ModificarLibro() {
		System.out.println("Introduzca ISBN");
		String isbn= in.nextLine();
		System.out.println("Introduzca TITULO");
		String titulo= in.nextLine();

		System.out.println("Introduzca AUTOR");
		String autor= in.nextLine();

		System.out.println("Introduzca PRECIO");
		Double precio= in.nextDouble();
		
		return "modificcar,"+titulo+","+autor+","+precio+","+isbn;
	}

	private static String introducirLibro() {

		System.out.println("Introduzca ISBN");
		String isbn= in.nextLine();
		System.out.println("Introduzca TITULO");
		String titulo= in.nextLine();

		System.out.println("Introduzca AUTOR");
		String autor= in.nextLine();

		System.out.println("Introduzca PRECIO");
		Double precio= in.nextDouble();
		
		return "alta,"+isbn+","+titulo+","+autor+","+precio;

		
		
	}

	public static void buscarPor(String aBuscar, String por) {

		System.out.println("Introduzca el " + aBuscar + " a buscar:");
		String busqueda = in.next();
		mostrarLibros(splitChorro(por + "," + busqueda));

	}

}
