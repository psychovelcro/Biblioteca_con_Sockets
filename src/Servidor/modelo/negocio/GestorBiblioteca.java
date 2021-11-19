package Servidor.modelo.negocio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Servidor.modelo.DAO.BibliotecaDaoImpl;
import Servidor.modelo.DAO.interfaces.BibliotecaDao;
import Servidor.modelo.entidad.Libro;

public class GestorBiblioteca implements Runnable {

	private String isbn, titulo, autor;
	private double precio;

	BibliotecaDao bibliotecaDao = new BibliotecaDaoImpl();

	private Thread hilo;
	private static int numCliente = 0;
	private Socket socketAlCliente;

	public GestorBiblioteca(Socket socketAlCliente) {
		numCliente++;
		hilo = new Thread(this, "Cliente_" + numCliente);
		this.socketAlCliente = socketAlCliente;
		hilo.start();
	}

	public int alta(Libro libro) {

		if (libro.getIsbn().length() >= 5 && libro.getTitulo().length() >= 5 && libro.getAutor().length() >= 5
				&& libro.getPrecio() >= 0) {
			bibliotecaDao.alta(libro);
			return 1;
		} else {
			return 11;
		}
	}

	public boolean baja(String isbn) {
		boolean baja = bibliotecaDao.baja(isbn);
		return baja;
	}

	public boolean modificar(Libro libro, String isbn) {
		if (libro.getIsbn().length() >= 5 && libro.getTitulo().length() >= 5 && libro.getAutor().length() >= 5
				&& libro.getPrecio() >= 0) {
			bibliotecaDao.modificar(libro, isbn);
			return true;
		} else
			return false;

	}

	public Libro getByTitle(String titulo) {
		Libro libro = bibliotecaDao.getByTitle(titulo);
		return libro;
	}

	public Libro getByIsbn(String Isbn) {
		Libro libro = bibliotecaDao.getByIsbn(Isbn);
		return libro;
	}

	public List<Libro> listar() {
		List<Libro> listaLibros = bibliotecaDao.listar();
		return listaLibros;
	}

	public List<Libro> getByAuthor(String Autor) {
		List<Libro> listaLibros = bibliotecaDao.getByAuthor(Autor);
		return listaLibros;
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

					String[] backString = frontString.split(",");
					String bbddresponse = "";
					Libro bbddBookReturned = null;
					List<Libro> bbddBookList = null;

					if (backString[0].equals("alta")) {

						Libro libro = new Libro();
						libro.setIsbn(backString[1]);
						libro.setTitulo(backString[2]);
						libro.setAutor(backString[3]);
						libro.setPrecio(Double.parseDouble(backString[4]));
						if (alta(libro) == 1) {
							bbddresponse = "200";
							System.out.println("200: OK. Alta de libro realizada con exito");
						} else {
							bbddresponse = "400";
							System.out.println("400: Bad request. Alta de libro no ha podido ser realizada");
						}

					}

					if (backString[0].equals("baja")) {
						baja(backString[1]);
						if (baja(backString[1])) {
							bbddresponse = "400";
							System.out.println("400: Bad request. Error en Baja de libro");

						} else {
							bbddresponse = "200";
							System.out.println("200: OK. Baja de libro realizada con exito");
						}
					}

					if (backString[0].equals("modificar")) {

						Libro libro = new Libro();
						libro.setIsbn(backString[1]);
						libro.setTitulo(backString[2]);
						libro.setAutor(backString[3]);
						libro.setPrecio(Double.parseDouble(backString[4]));

						if (modificar(libro, backString[1])) {
							bbddresponse = "200";
							System.out.println("200: OK. Modificacion de libro realizada con exito");
						} else {
							bbddresponse = "400";
							System.out.println("400: Bad request. Error en modificacion de libro");
						}
					}

					if (backString[0].equals("portitulo")) {

						titulo = backString[1];

						bbddBookReturned = getByTitle(titulo);

						if (getByTitle(titulo) != null) {

							bbddresponse = bbddBookReturned.getIsbn() + ",";
							bbddresponse += bbddBookReturned.getTitulo() + ",";
							bbddresponse += bbddBookReturned.getAutor() + ",";
							bbddresponse += bbddBookReturned.getPrecio();

							System.out.println("OK.200.");
						} else {
							bbddresponse = "404";
							System.out.println("404. Not Found");
						}

					}

					if (backString[0].equals("porautor")) {

						autor = backString[1];

						bbddBookList = getByAuthor(autor);

						if (getByAuthor(autor) != null) {

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

					}

					if (backString[0].equals("porisbn")) {

						isbn = backString[1];

						bbddBookReturned = getByIsbn(isbn);

						if (getByIsbn(isbn) != null) {

							bbddresponse = bbddBookReturned.getIsbn() + ",";
							bbddresponse += bbddBookReturned.getTitulo() + ",";
							bbddresponse += bbddBookReturned.getAutor() + ",";
							bbddresponse += bbddBookReturned.getPrecio();
							System.out.println("OK.200.");
						} else {
							bbddresponse = "404. NOT FOUND";
							System.out.println("404. NOT FOUND");
						}

					}

					if (backString[0].equals("listar")) {

						bbddBookList = listar();

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

}
