package Contenido;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

//El servidor de archivos debe poder modificar un archivo, verificando el tamaño del mismo
//El servidor de archivos debe poder borrar un archivo
//El servidor de archivos debe poder crear un archivo, verificando el tamaño del mismo
//El servidor de archivos debe poder llamar al servidor de archivos secundario si se encuentra con muchas peticiones 
//(El servidor de archivos debe realizar una copia del mismo al terminar de realizar alguna de las operaciones anteriores??)




public class ServidorArchivo2 implements Runnable {
	int port;
	private ArrayList<String> colaDeMensajes;
	
	public ServidorArchivo2(int port) {
		this.port=port;
		this.colaDeMensajes= new ArrayList<String>();
		this.StartServer();
	}

	public void StartServer() {
		try {
			ServerSocket ss = new ServerSocket(port);
			//Servidor Escuchando puerto ingresado
			System.out.println("---Servidor de directorio 2 iniciado, escuchando en puerto "+port+"---");
			while (true) {
				Socket ServidorDirectorio2 = ss.accept();
				System.out.println("Servidor de directorio 2 conectado: "+ServidorDirectorio2.getInetAddress().getCanonicalHostName()+" : "+ServidorDirectorio2.getPort());
				HiloServidor hs = new HiloServidor(ServidorDirectorio2,colaDeMensajes);
				Thread HSthread = new Thread(hs);
				HSthread.start();
			}
		} catch (IOException e) {
			System.out.println("Puerto en uso");
		}
	}
	
	public static void main(String[] args) {
		//Para el ejemplo utilizo puerto 6001
		ServidorArchivo2 servidor = new ServidorArchivo2(6001);
	}

}

