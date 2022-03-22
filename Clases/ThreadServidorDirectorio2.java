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


public class ThreadServidorDirectorio2 implements Runnable {
	Socket cliente;
	private ArrayList<String> colaDeMensajes;
	
	public ThreadServidorDirectorio2(Socket cliente,ArrayList<String> colaDeMensajes) {
		this.cliente=cliente;
		this.colaDeMensajes=colaDeMensajes;
	}
	
	public void run() {
		try {
			BufferedReader entrada = new BufferedReader (new InputStreamReader(cliente.getInputStream()));
			PrintStream salida = new PrintStream (this.cliente.getOutputStream(),true);
			
			while(!cliente.isClosed()) {
				String opcion = entrada.readLine();
					
				if (opcion.substring(opcion.indexOf("|")+1, opcion.length()).equals("SEND")) {
					
					//Leo del cliente y guardo en la cola
					String mensEnv = entrada.readLine();
					colaDeMensajes.add(mensEnv);
					
				}else if (opcion.substring(opcion.indexOf("|")+1, opcion.length()).equals("RECV")) {
					//Extraigo el ID del cliente que realiza la peticion
					String srcId = opcion.substring(0, opcion.indexOf("|"));
					if (!this.colaDeMensajes.isEmpty()) {
						for (String str: colaDeMensajes) {
							//Recorro la cola y comparo el ID del cliente con los destinos de los mensajes
							//Envio mensaje al cliente si es igual
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
			this.cliente.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}



