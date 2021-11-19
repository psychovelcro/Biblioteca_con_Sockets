package Servidor.modelo.negocio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

import Servidor.modelo.entidad.Libro;

public class Hilo_Biblioteca implements Runnable {

	private String isbn, titulo, autor, bbddresponse;
	private double precio;
	private String[] backString;
	private Libro bbddBookReturned;
	List<Libro> bbddBookList;

	private Thread hilo;
	private static int numCliente = 0;
	private Socket socketAlCliente;

	public Hilo_Biblioteca(Socket socketAlCliente) {
		numCliente++;
		hilo = new Thread(this, "Cliente_" + numCliente);
		this.socketAlCliente = socketAlCliente;
		hilo.start();
	}

	@Override
	public void run() {

		System.out.println("Estableciendo comunicacion con " + hilo.getName());
		PrintStream salida = null;
		InputStreamReader entrada = null;
		BufferedReader entradaBuffer = null;

		try {
			// Salida del servidor al cliente
			salida = new PrintStream(socketAlCliente.getOutputStream());
			// Entrada del servidor al cliente
			entrada = new InputStreamReader(socketAlCliente.getInputStream());
			entradaBuffer = new BufferedReader(entrada);

			String frontString = "";
			boolean continuar = true;

			// Procesaremos entradas hasta que el texto del cliente sea FIN
			while (continuar) {
				frontString = entradaBuffer.readLine();

				if (frontString.trim().equalsIgnoreCase("FIN")) {
					// Mandamos la se�al de "0" para que el cliente sepa que vamos a cortar
					// la comunicacion
					salida.println("OK");
					System.out.println(hilo.getName() + " ha cerrado la comunicacion");
					continuar = false;
				} else {

					/*
					 * Codificacion: Para hacer un Alta: alta,isbn,titulo,autor,precio Para hacer
					 * una Baja: baja,isbn Para modificar: modificar,isbn,titulo,autor,precio Para
					 * Listar por titulo: portitulo,titulo Para listar por Autor: porautor,autor
					 * Para listar por isbn: porisbn,isbn Para listar bbdd completa: listar Responde
					 * con libro o array de libros libro = isbn + titulo + autor + precio + /
					 */

					backString = frontString.split(",");
					bbddresponse = "";
					bbddBookReturned = null;
					bbddBookList = null;
					GestorBiblioteca gestor = new GestorBiblioteca();

					if (backString[0].equals("alta")) {
						altaRequested();
					}
					if (backString[0].equals("baja")) {
						bajaRequested();
					}
					if (backString[0].equals("modificar")) {
						modificarRequested();
					}
					if (backString[0].equals("portitulo")) {
						portituloRequested();
					}
					if (backString[0].equals("porautor")) {
						porautorRequested();
					}
					if (backString[0].equals("porisbn")) {
						porisbnRequested();
					}
					if (backString[0].equals("listar")) {
						listarRequested();
					}
					salida.println(bbddresponse);
				}
			}
			// Cerramos el socket
			socketAlCliente.close();

		} catch (IOException e) {
			System.err.println("HiloBiblioteca: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("HiloBiblioteca: Error");
			e.printStackTrace();
		}

	}

	public String altaRequested() {
		GestorBiblioteca gestor = new GestorBiblioteca();
		Libro libro = new Libro();
		libro.setIsbn(backString[1]);
		libro.setTitulo(backString[2]);
		libro.setAutor(backString[3]);
		libro.setPrecio(Double.parseDouble(backString[4]));
		System.out.println(libro);
		if (gestor.alta(libro) == 1) {
			bbddresponse = "200";
			System.out.println("200: OK. Alta de libro realizada con exito");
		} else {
			bbddresponse = "400";
			System.out.println("400: Bad request. Alta de libro no ha podido ser realizada");
		}
		return bbddresponse;

	}

	public String bajaRequested() {
		GestorBiblioteca gestor = new GestorBiblioteca();

		gestor.baja(backString[1]);
		if (gestor.baja(backString[1])) {
			bbddresponse = "400";
			System.out.println("400: Bad request. Error en Baja de libro");

		} else {
			bbddresponse = "200";
			System.out.println("200: OK. Baja de libro realizada con exito");
		}

		return bbddresponse;

	}

	public String modificarRequested() {
		GestorBiblioteca gestor = new GestorBiblioteca();

		Libro libro = new Libro();
		libro.setIsbn(backString[1]);
		libro.setTitulo(backString[2]);
		libro.setAutor(backString[3]);
		libro.setPrecio(Double.parseDouble(backString[4]));

		if (gestor.modificar(libro, backString[1])) {
			bbddresponse = "200";
			System.out.println("200: OK. Modificacion de libro realizada con exito");
		} else {
			bbddresponse = "400";
			System.out.println("400: Bad request. Error en modificacion de libro");
		}

		return bbddresponse;

	}

	public String portituloRequested() {
		GestorBiblioteca gestor = new GestorBiblioteca();

		titulo = backString[1];

		bbddBookReturned = gestor.getByTitle(titulo);

		if (gestor.getByTitle(titulo) != null) {

			bbddresponse = bbddBookReturned.getIsbn() + ",";
			bbddresponse += bbddBookReturned.getTitulo() + ",";
			bbddresponse += bbddBookReturned.getAutor() + ",";
			bbddresponse += bbddBookReturned.getPrecio();

			System.out.println("OK.200.");
		} else {
			bbddresponse = "404";
			System.out.println("404. Not Found");
		}

		return bbddresponse;

	}

	public String porautorRequested() {
		GestorBiblioteca gestor = new GestorBiblioteca();

		autor = backString[1];

		bbddBookList = gestor.getByAuthor(autor);

		if (gestor.getByAuthor(autor) != null) {

			for (int i = 0; i < bbddBookList.size(); i++) {

				bbddBookReturned = bbddBookList.get(i);

				bbddresponse += bbddBookReturned.getIsbn() + ",";
				bbddresponse += bbddBookReturned.getTitulo() + ",";
				bbddresponse += bbddBookReturned.getAutor() + ",";
				bbddresponse += bbddBookReturned.getPrecio();

				bbddresponse += i < bbddBookList.size() - 1 ? "/" : "";

			}
			System.out.println("OK.200.");

		} else {
			bbddresponse = "404";
			System.out.println("404. NOT FOUND");
		}

		return bbddresponse;

	}

	public String porisbnRequested() {
		GestorBiblioteca gestor = new GestorBiblioteca();

		isbn = backString[1];

		bbddBookReturned = gestor.getByIsbn(isbn);

		if (gestor.getByIsbn(isbn) != null) {

			bbddresponse = bbddBookReturned.getIsbn() + ",";
			bbddresponse += bbddBookReturned.getTitulo() + ",";
			bbddresponse += bbddBookReturned.getAutor() + ",";
			bbddresponse += bbddBookReturned.getPrecio();
			System.out.println("OK.200.");
		} else {
			bbddresponse = "404. NOT FOUND";
			System.out.println("404. NOT FOUND");
		}

		return bbddresponse;

	}

	public String listarRequested() {
		GestorBiblioteca gestor = new GestorBiblioteca();
		bbddBookList = gestor.listar();

		if (bbddBookList != null) {

			for (int i = 0; i < bbddBookList.size(); i++) {

				bbddBookReturned = bbddBookList.get(i);

				bbddresponse += bbddBookReturned.getIsbn() + ",";
				bbddresponse += bbddBookReturned.getTitulo() + ",";
				bbddresponse += bbddBookReturned.getAutor() + ",";
				bbddresponse += bbddBookReturned.getPrecio();

				bbddresponse += i < bbddBookList.size() - 1 ? "/" : "";

			}
			System.out.println("OK.200.");

		} else {
			System.out.println("404. NOT FOUND");
			bbddresponse = "404";
		}

		return bbddresponse;

	}

}
