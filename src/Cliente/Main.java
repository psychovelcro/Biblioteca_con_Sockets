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
		consultasRandom.add("");
		consultasRandom.add("");
		consultasRandom.add("");
		consultasRandom.add("");
		consultasRandom.add("");
		consultasRandom.add("");

		
	}

}
