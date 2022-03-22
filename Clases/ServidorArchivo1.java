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




public class ServidorArchivo1 implements Runnable {
	int port;
	private ArrayList<String> colaDeMensajes;
	
	public ServidorArchivo1(int port) {
		this.port=port;
		this.colaDeMensajes= new ArrayList<String>();
		this.StartServer();
	}

	public void StartServer() {
		try {
			ServerSocket ss = new ServerSocket(port);
			//Servidor Escuchando puerto ingresado
			System.out.println("---Servidor de Directorio 1 iniciado, escuchando en puerto "+port+"---");
			while (true) {
				Socket ServidorDirectorio1 = ss.accept();
				System.out.println("ServidorDirectorio 1 conectado: "+ServidorDirectorio1.getInetAddress().getCanonicalHostName()+" : "+ServidorDirectorio1.getPort());
				ThreadServidorArchivo1 hs = new ThreadServidorArchivo1(ServidorDirectorio1,colaDeMensajes);
				Thread servidorThreadA = new Thread(hs);
				servidorThreadA.start();
			}
		} catch (IOException e) {
			System.out.println("Puerto en uso");
		}
	}
	
	public static void main(String[] args) {
		//Para el ejemplo utilizo puerto 6000
		ServidorArchivo1 servidor = new ServidorArchivo1(6000);
	}

}


/*public class ServidorArchivo1 implements Runnable{
	
	private final static Logger log = LoggerFactory.getLogger(ServidorArchivo1.class);
	private int intentos=0;
	private int puerto;
	private ArrayList<String> recursos;
	private ServerSocket ss;
	private Socket s;
	private String archivo;
	
	
public ServidorArchivo1(int puerto,ArrayList<String> recursos, String carpComp){
		iniciar(puerto);
		this.puerto = puerto;
		this.recursos = recursos;
		this.archivo = carpComp;
	}
	
	public void iniciar(int puerto) {
		try {							
			ss = new ServerSocket(puerto);
		} catch (IOException e) {
			intentos++;
			iniciar(++puerto);
			log.info("Error al iniciar el servidor! ");
			log.info("Numero de intentos: "+intentos);
			e.getMessage();
		}
	}
	
	public void run() {
		String packetName = ServidorArchivo1.class.getSimpleName().toString()+"-";
		MDC.put("log.name",packetName);
		try {
			log.info("Se ha iniciado el servidor en el cliente");
			
			while(true) {
				s = ss.accept();
				ThreadServidorArchivo1 servidor = new ThreadServidorArchivo1(s,recursos,archivo);
				Thread servidorThread = new Thread(servidor);
				servidorThread.start();
			}
	} catch (IOException e) {
		log.info("Puerto en uso");
	}
		MDC.remove(packetName);
	}

}*/
