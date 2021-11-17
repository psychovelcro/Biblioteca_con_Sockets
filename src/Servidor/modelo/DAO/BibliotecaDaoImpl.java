package Servidor.modelo.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Servidor.modelo.DAO.interfaces.BibliotecaDao;
import Servidor.modelo.entidad.Libro;

public class BibliotecaDaoImpl implements BibliotecaDao {

	private Connection conexion;
	private String isbn, titulo, autor;
	private int id;
	private double precio;

	public boolean abrirConexion() {
		String url = "jdbc:mysql://localhost:3306/Biblioteca";
		String usuario = "root";
		String password = "";
		try {
			conexion = DriverManager.getConnection(url, usuario, password);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean cerrarConexion() {
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean alta(Libro libro) {
		if (!abrirConexion()) {
			return false;
		}
		boolean alta = true;

		String query = "insert into BIBLIOTECA (ISBN,TITULO,AUTOR,PRECIO) " + " values(?,?,?,?)";
		try {
			// preparamos la query con valores parametrizables(?)
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, libro.getIsbn());
			ps.setString(2, libro.getTitulo());
			ps.setString(3, libro.getAutor());
			ps.setDouble(4, libro.getPrecio());

			int numeroFilasAfectadas = ps.executeUpdate();
			if (numeroFilasAfectadas == 0) {
				alta = false;
			}
		} catch (SQLException e) {
			System.out.println("alta -> Error al insertar: " + libro);
			alta = false;
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}

		return alta;
	}

	@Override
	public boolean baja(String isbn) {
		if (!abrirConexion()) {
			return false;
		}

		boolean borrado = true;
		String query = "delete from BIBLIOTECA where ISBN = ?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);

			ps.setString(1, isbn);

			int numeroFilasAfectadas = ps.executeUpdate();
			if (numeroFilasAfectadas == 0)
				borrado = false;
		} catch (SQLException e) {
			System.out.println("baja -> No se ha podido dar de baja" + " el libro con isbn: " + isbn);
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		return borrado;
	}

	@Override
	public boolean modificar(Libro libro) {
		if (!abrirConexion()) {
			return false;
		}
		boolean modificado = true;
		String query = "update Biblioteca set TITULO=?, AUTOR=? " + "PRECIO=? WHERE ISBN=?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);

			ps.setString(1, libro.getTitulo());
			ps.setString(2, libro.getAutor());
			ps.setDouble(3, libro.getPrecio());
			ps.setString(4, libro.getIsbn());

			int numeroFilasAfectadas = ps.executeUpdate();
			if (numeroFilasAfectadas == 0)
				modificado = false;
			else
				modificado = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("modificar -> error al modificar el " + " libro " + libro);
			modificado = false;
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}

		return modificado;
	}

	@Override
	public List<Libro> listar() {
		if (!abrirConexion()) {
			return null;
		}
		Libro libro = null;
		List<Libro> listaLibros = new ArrayList<>();

		String query = "select ISBN, TITULO, AUTOR, PRECIO from BIBLIOTECA";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				libro = new Libro();
				libro.setIsbn(rs.getString(1));
				libro.setTitulo(rs.getString(2));
				libro.setAutor(rs.getString(3));
				libro.setPrecio(rs.getDouble(4));

				listaLibros.add(libro);

			}
		} catch (SQLException e) {
			System.out.println("listar -> error al obtener los  " + "videojuegos");
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}

		return listaLibros;
	}

	@Override
	public List<Libro> getByAuthor(String Autor) {
		if (!abrirConexion()) {
			return null;
		}
		Libro libro = null;

		List<Libro> listaLibros = new ArrayList<>();

		String query = "select ISBN, TITULO, AUTOR, PRECIO from BIBLIOTECA WHERE AUTOR=?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, Autor);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				libro = new Libro();
				libro.setIsbn(rs.getString(1));
				libro.setTitulo(rs.getString(2));
				libro.setAutor(rs.getString(3));
				libro.setPrecio(rs.getDouble(4));

				listaLibros.add(libro);
			}

		} catch (SQLException e) {
			System.out.println("obtener -> error al obtener los libros " + "del autor " + autor);
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}

		return listaLibros;
	}

	@Override
	public Libro getByIsbn(String Isbn) {
		if (!abrirConexion()) {
			return null;
		}
		Libro libro = null;

		String query = "select ISBN, TITULO, AUTOR, PRECIO from BIBLIOTECA WHERE Isbn=?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, Isbn);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				libro = new Libro();
				libro.setIsbn(rs.getString(1));
				libro.setTitulo(rs.getString(2));
				libro.setAutor(rs.getString(3));
				libro.setPrecio(rs.getDouble(4));
			}
		} catch (SQLException e) {
			System.out.println("obtener -> error al obtener el libro con Isbn: " + isbn);
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}

		return libro;
	}

	@Override
	public Libro getByTitle(String titulo) {
		if (!abrirConexion()) {
			return null;
		}
		Libro libro = null;

		String query = "select ISBN, TITULO, AUTOR, PRECIO from BIBLIOTECA WHERE TITULO=?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, titulo);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				libro = new Libro();
				libro.setIsbn(rs.getString(1));
				libro.setTitulo(rs.getString(2));
				libro.setAutor(rs.getString(3));
				libro.setPrecio(rs.getDouble(4));
			}
		} catch (SQLException e) {
			System.out.println("obtener -> error al obtener el " + "videojuego con id " + id);
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}

		return libro;
	}

}
