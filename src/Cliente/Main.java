package Cliente;

import java.util.ArrayList;

public class Main {
	private static ArrayList<String> consultasRandom;
	
	public static void main(String[] args) {
		
		rellenarConsultas();
	
		SocketCliente sc= new SocketCliente();
		
		for (int i = 0; i < 10; i++) {
			ClienteHilo cHilo= new ClienteHilo("", sc);
			Thread c = new Thread(cHilo);
			c.start();
		}
		
	}

	
	private static int randomNumber() {
		
		
		
		return 0;
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
