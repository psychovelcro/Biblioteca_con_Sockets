package Servidor.modelo.negocio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

import Servidor.modelo.DAO.BibliotecaDaoImpl;
import Servidor.modelo.DAO.interfaces.BibliotecaDao;
import Servidor.modelo.entidad.Libro;

public class GestorBiblioteca implements Runnable {
	// Hilo
	// socket con metodos del dao

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

		if (libro.getTitulo().length() >= 5 && libro.getPrecio() >= 0) {
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

	public boolean modificar(Libro libro) {
		boolean modificar = bibliotecaDao.modificar(libro);
		return modificar;
	}

	public Libro getByTitle(String titulo) {
		Libro libro = bibliotecaDao.getByTitle(titulo);
		return libro;
	}

	public Libro getByIsbn(String Isbn) {
		Libro libro = bibliotecaDao.getByTitle(Isbn);
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
					 * Codificacion: 
					 * Para hacer un Alta:
					 * alta,isbn,titulo,autor,precio
					 * Para hacer una Baja:
					 * baja,isbn
					 * Para modificar:
					 * modificar,titulo,autor,precio,sibn
					 * Para Listar por titulo:
					 * portitulo,titulo
					 * Para listar por Autor:
					 * porautor,autor
					 * Para listar por isbn:
					 * porisbn,isbn
					 * Para listar bbdd completa:
					 * listar
					 * Responde con libro o array de libros
					 * libro = isbn + titulo + autor + precio
					 */

					String[] backString = frontString.split(",");
					String bbddresponse = null;

					if (backString[0].equals("alta")) {

					}

					if (backString[0].equals("baja")) {

					}

					if (backString[0].equals("modificar")) {

					}

					if (backString[0].equals("portitulo")) {

					}
					
					if (backString[0].equals("porautor")) {

					}
					
					if (backString[0].equals("porisbn")) {

					}
					
					if (backString[0].equals("listar")) {

					}

				
					salida.println(bbddresponse);
				}
			}
			// Cerramos el socket
			socketAlCliente.close();
		
		} catch (IOException e) {
			System.err.println("HiloContadorLetras: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("HiloContadorLetras: Error");
			e.printStackTrace();
		}

	}

}
