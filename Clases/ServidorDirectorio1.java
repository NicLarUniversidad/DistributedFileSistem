package Contenido;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

//El servidor de directorios debe poder encolar las peticiones de lo usuarios que desean realizar alguna operación sobre el mismo archivo, puede ser una cola por archivo
//El servidor de directorios debe conocer a los archivod que contiene
//El servidor de directorios1 debe poder actualizar al servidor de directorios2 que actuará cono servidor de respaldo, ya sea por sobrecarga de consultas o caida del servidor primario.
//El servidor de directorios debe poder crear un nuevo directorio

public class ServidorDirectorio1 implements Runnable {

	int port;
	private ArrayList<String> colaDeMensajes;
	
	public ServidorDirectorio1(int port) {
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
				ThreadServidorDirectorio1 hs = new ThreadServidorDirectorio1(cliente,colaDeMensajes);
				Thread servidorThreadD = new Thread(hs);
				servidorThreadD.start();
			}
		} catch (IOException e) {
			System.out.println("Puerto en uso");
		}
	}
	
	public static void main(String[] args) {
		//Para el ejemplo utilizo puerto 6002
		ServidorDirectorio1 servidor = new ServidorDirectorio1(6002);
	}

}
