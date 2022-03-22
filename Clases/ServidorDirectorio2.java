package Contenido;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class ServidorDirectorio2 implements Runnable {

	int port;
	private ArrayList<String> colaDeMensajes;
	
	public ServidorDirectorio2(int port) {
		this.port=port;
		this.colaDeMensajes= new ArrayList<String>();
		this.StartServer();
	}

	public void StartServer() {
		try {
			ServerSocket ss = new ServerSocket(port);
			//Servidor Escuchando puerto ingresado
			System.out.println("---Servidor de directorio1 iniciado, escuchando en puerto "+port+"---");
			while (true) {
				Socket cliente = ss.accept();
				System.out.println("Cliente conectado: "+cliente.getInetAddress().getCanonicalHostName()+" : "+cliente.getPort());
				HiloServidor hs = new HiloServidor(cliente,colaDeMensajes);
				Thread HSthread = new Thread(hs);
				HSthread.start();
			}
		} catch (IOException e) {
			System.out.println("Puerto en uso");
		}
	}
	
	public static void main(String[] args) {
		//Para el ejemplo utilizo puerto 6003
		ServidorDirectorio2 servidor = new ServidorDirectorio3(6003);
	}

}
