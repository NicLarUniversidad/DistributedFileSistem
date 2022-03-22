package Contenido;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Conexion implements Runnable{
	
		
		private BufferedReader entrada;
		private PrintWriter salida;
		
		private final static Logger log = LoggerFactory.getLogger(Conexion.class);
			
			public void run() {
				String aux;
				while (true) {
					try {
						Thread.sleep(10000);
						Cliente.salida.println("4");
						aux = Cliente.entrada.readLine();
						
							if(aux==null) {
								log.info("Error, se ha caido la conexion con el server master");
								if(Cliente.conectarse(0)) {
									Cliente.notificar();
								}
							}
					} catch (IOException e) {
						Cliente.conectarse(0);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}	
			}

}



