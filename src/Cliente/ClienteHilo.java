package Cliente;

import java.util.ArrayList;

public class ClienteHilo implements Runnable {
private String consulta;
private  SocketCliente sock;


	public ClienteHilo(String consulta, SocketCliente sock) {
	this.consulta = consulta;
	this.sock = sock;
}

	@Override
	public void run() {
		System.out.println("******Hilo con consulta: "+ consulta +" ************");
	String chorro=	sock.enviarChorro(consulta);
		
		
		if(chorro.length()>5) {
			System.out.println("Libros obtenidos de la consulta: \n");
			for (Libro Libro : splitChorro(chorro)) {
				
				System.out.println(Libro.getIsbn()+" | "+Libro.getTitulo()+" | "+Libro.getAutor()+" | "
						+Libro.getPrecio()+" euros.");
				
			}
			
		}
		else {
			System.out.println("Operacion hecha con exito ("+chorro+")");
		}
		System.out.println("-----------------------------------------------------");
		
		
		
		
		
		
	}
	
	
	public static ArrayList<Libro> splitChorro(String chorro) {

		ArrayList<Libro> libros = new ArrayList<Libro>();
		String[] librosInfo = chorro.split("/");

		for (String libro : librosInfo) {
			String[] dataLibro = libro.split(",");
			libros.add(new Libro(dataLibro[0], dataLibro[1], dataLibro[2], Double.parseDouble(dataLibro[3])));
		}

		return libros;

	}

}
