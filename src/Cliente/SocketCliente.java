package Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

//En este ejemplo vamos a ver como podemos recoger dos numeros desde una aplicacion
//cliente y mandarselos al sevidor para que nos devuelva una respuesta con 
//el resultado de la suma de ambos.
public class SocketCliente {
	
	//IP y Puerto a la que nos vamos a conectar
	public static final int PUERTO = 2017;
	public static final String IP_SERVER = "localhost";
	private String chorro,respuestaChorro;
	
	
	
	
	public SocketCliente(String chorro) {
		this.chorro = chorro;
		this.respuestaChorro="";
	}




	public String enviarChorro() {
		System.out.println("        APLICACI�N CLIENTE         ");
		System.out.println("-----------------------------------");
		
		
		InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER, PUERTO);
		
		
		try (Scanner sc = new Scanner(System.in);
			Socket socketAlServidor = new Socket()){
					
		
			System.out.println("CLIENTE: Introduzca los numeros a sumar");
			String numero1 = sc.nextLine();//supongamos el numero 3
			String numero2 = sc.nextLine();//supongamos el numero 4
			
			
			String operandos = numero1 + "-" + numero2;//3-4
			
		
			System.out.println("CLIENTE: Esperando a que el servidor acepte la conexi�n");
			socketAlServidor.connect(direccionServidor);			
			System.out.println("CLIENTE: Conexion establecida... a " + IP_SERVER 
					+ " por el puerto " + PUERTO);	
			
			
			
			//Creamos el objeto que nos permite mandar informacion al servidor
			PrintStream salida = new PrintStream(socketAlServidor.getOutputStream());
			
			
			
			
			salida.println(chorro);//3-4
			
			InputStreamReader entrada = new InputStreamReader(socketAlServidor.getInputStream());		
			BufferedReader bf = new BufferedReader(entrada);	
			System.out.println("CLIENTE: Esperando al resultado del servidor...");
			
			
			
			
			respuestaChorro = bf.readLine();//7	
			System.out.println("CLIENTE: El resultado de la suma es: " + respuestaChorro);//7
			
			
			
		} catch (UnknownHostException e) {
			System.err.println("CLIENTE: No encuentro el servidor en la direcci�n" + IP_SERVER);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("CLIENTE: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("CLIENTE: Error -> " + e);
			e.printStackTrace();
		}
		return respuestaChorro;
		
		
	}

}