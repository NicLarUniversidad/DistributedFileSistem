package Contenido;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;


public class ThreadServidorArchivoCopia2 implements Runnable {
	Socket ServidorDirectorio2;
	private ArrayList<String> colaDeMensajes;
	
	public ThreadServidorArchivoCopia2(Socket ServidorDirectorio2,ArrayList<String> colaDeMensajes) {
		this.ServidorDirectorio2=ServidorDirectorio2;
		this.colaDeMensajes=colaDeMensajes;
	}
	
	public void run() {
		try {
			BufferedReader entrada = new BufferedReader (new InputStreamReader(ServidorDirectorio2.getInputStream()));
			PrintStream salida = new PrintStream (this.ServidorDirectorio2.getOutputStream(),true);
			
			while(!ServidorDirectorio2.isClosed()) {
				String opcion = entrada.readLine();
					
				if (opcion.substring(opcion.indexOf("|")+1, opcion.length()).equals("SEND")) {
					
					//Leo del server y guardo en la cola
					String mensEnv = entrada.readLine();
					colaDeMensajes.add(mensEnv);
					
				}else if (opcion.substring(opcion.indexOf("|")+1, opcion.length()).equals("RECV")) {
					//Extraigo el ID del server que realiza la peticion
					String srcId = opcion.substring(0, opcion.indexOf("|"));
					if (!this.colaDeMensajes.isEmpty()) {
						for (String str: colaDeMensajes) {
							//Recorro la cola y comparo el ID del server con los destinos de los mensajes
							//Envio mensaje al server si es igual
							String destinoID = str.substring(0, str.indexOf("|"));
							String MsgSEND = str.substring(str.indexOf("|")+1, str.length());
							if (destinoID.equals(srcId)) {
								salida.println(MsgSEND);
							}
							System.out.println("Mensaje leido: "+MsgSEND);
						}
					}
					String Final = "No.mas.mensajes.";
					salida.println(Final);	
					System.out.println("Fin de mensajes");
				}
			}
			this.ServidorDirectorio2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


