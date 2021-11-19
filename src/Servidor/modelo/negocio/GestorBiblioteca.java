package Servidor.modelo.negocio;

import java.util.List;

import Servidor.modelo.DAO.BibliotecaDaoImpl;
import Servidor.modelo.DAO.interfaces.BibliotecaDao;
import Servidor.modelo.entidad.Libro;

public class GestorBiblioteca {

	private String isbn, titulo, autor;
	private double precio;

	BibliotecaDao bibliotecaDao = new BibliotecaDaoImpl();

	public int alta(Libro libro) {

		if (libro.getTitulo().length() >= 5 && libro.getAutor().length() >= 5 && libro.getPrecio() >= 0) {
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

}
