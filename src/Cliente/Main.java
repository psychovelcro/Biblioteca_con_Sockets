package Cliente;

import java.util.ArrayList;
import java.util.Random;

public class Main {
	private static ArrayList<String> consultasRandom;
	
	public static void main(String[] args) {
		
		rellenarConsultas();
	
		SocketCliente sc= new SocketCliente();
		
		for (int i = 0; i < 10; i++) {
			
			ClienteHilo cHilo= new ClienteHilo(consultasRandom.get(randomNumber(0, consultasRandom.size())), sc);
			
			Thread c = new Thread(cHilo);
			c.start();
		}
		
	}

	
	private static int randomNumber(int low,int high) {
		Random r = new Random();
		return r.nextInt(high-low) + low;
	}

	
	private static void rellenarConsultas() {
		consultasRandom = new ArrayList<>();
		consultasRandom.add("listar");
		consultasRandom.add("alta,63749-342-223,El pastor,Pedro Sanchez,47.89");
		consultasRandom.add("baja,63749-342-223");
		consultasRandom.add("modificar,La cabra 2,Pable henandez,56.8,63749-342-223");
		consultasRandom.add("portitulo,Los 3 cerdos");
		consultasRandom.add("porautor,Pablo hernandez");
		consultasRandom.add("porisbn,63749-342-223");
		consultasRandom.add("fin");


		
	}

}
