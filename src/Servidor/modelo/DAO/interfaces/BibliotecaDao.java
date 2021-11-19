package Servidor.modelo.DAO.interfaces;

import java.util.List;

import Servidor.modelo.entidad.Libro;

public interface BibliotecaDao {

	boolean alta(Libro libro);

	boolean baja(String isbn);

	boolean modificar(Libro libro, String isbn);

	Libro getByTitle(String titulo);
	
	Libro getByIsbn(String Isbn);

	List<Libro> listar();
	
	List <Libro> getByAuthor(String Autor);

}
