package Cliente;

public class ClienteHilo implements Runnable {
private String consulta;
private  SocketCliente sock;


	public ClienteHilo(String consulta, SocketCliente sock) {
	this.consulta = consulta;
	this.sock = sock;
}



	@Override
	public void run() {
		
		System.out.println(sock.enviarChorro(consulta));
		
		
	}

}
