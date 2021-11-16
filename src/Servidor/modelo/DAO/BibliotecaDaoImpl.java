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
	private String name, company;
	private int id;
	private double price;

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modificar(Libro libro) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Libro obtener(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Libro> listar() {
		// TODO Auto-generated method stub
		return null;
	}

}
