package Servidor.modelo.DAO.interfaces;

import java.util.List;

import Servidor.modelo.entidad.Libro;

public interface BibliotecaDao {

	boolean alta(Libro libro);

	boolean baja(String isbn);

	boolean modificar(Libro libro);

	Libro obtener(int id);

	List<Libro> listar();

}
