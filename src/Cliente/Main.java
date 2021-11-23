package Cliente;

public class Main {

	public static void main(String[] args) {
		
		
		SocketCliente sc= new SocketCliente();
		
		for (int i = 0; i < 10; i++) {
			ClienteHilo cHilo= new ClienteHilo("", sc);
			Thread c = new Thread(cHilo);
			c.start();
		}
		
	}

}
