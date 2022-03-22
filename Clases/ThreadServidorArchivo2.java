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


public class ThreadServidorArchivo2 implements Runnable {
	Socket ServidorArchivo2;
	private ArrayList<String> colaDeMensajes;
	
	public ThreadServidorArchivo2(Socket ServidorArchivo2,ArrayList<String> colaDeMensajes) {
		this.ServidorArchivo2=ServidorArchivo2;
		this.colaDeMensajes=colaDeMensajes;
	}
	
	public void run() {
		try {
			BufferedReader entrada = new BufferedReader (new InputStreamReader(ServidorArchivo2.getInputStream()));
			PrintStream salida = new PrintStream (this.ServidorArchivo2.getOutputStream(),true);
			
			while(!ServidorArchivo2.isClosed()) {
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
			this.ServidorArchivo2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}





/*public class ThreadServidorArchivo1 implements Runnable{
	
	private final Logger log = LoggerFactory.getLogger(ThreadServidorArchivo1.class);
	private PrintWriter salida;
	private BufferedReader entrada;
	private String mensaje;
	private ArrayList<String> recursosD;
	private String carpComp;
	private Socket s;
	
	
	
	public ThreadServidorArchivo1(Socket s,ArrayList<String> recursos, String archivo) {
		this.s = s;
		this.recursosD = recursos;
		this.carpComp= archivo;
		
		try {
			this.salida = new PrintWriter (s.getOutputStream(), true);
			this.entrada = new BufferedReader (new InputStreamReader (s.getInputStream()));
			salida.flush();
		} catch (IOException e) {
			e.getMessage();
		}
		
	};
			
		public String buscar(String recu) {
			int i = 0;
			while(i<recursosD.size()) {
				if(recu.equalsIgnoreCase(recursosD.get(i))) {
					log.info("Recursos: "+recursosD.get(i));
					recu = recursosD.get(i);
					i = recursosD.size();
				}
				i++;
			}
		return recu;
		}
			
		
		public void enviar(){
			try {
				DataOutputStream dataOut = new DataOutputStream(s.getOutputStream());
				String nombreA = this.entrada.readLine();
				nombreA = buscar(nombreA);
				String rutaA = carpComp+System.getProperty("file.separator")+nombreA;
				File archivo = new File(rutaA);
				FileInputStream fileIn = new FileInputStream(rutaA);
				BufferedInputStream bis = new BufferedInputStream(fileIn);
				BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream());
				
				int tamanioArch = (int) archivo.length();
				dataOut.writeInt(tamanioArch);
			
				byte[] buffer = new byte[tamanioArch];
			
				bis.read(buffer);
			
				for(int i=0;i<buffer.length;i++) {
					bos.write(buffer[i]);
				}
				
				fileIn.close();
				bis.close();
				bos.close();
				
			} catch (IOException e) {
			}
		}
		
		
		public void run() {
				String packetName=ThreadServidorArchivo1.class.getSimpleName().toString()+"-"+Thread.currentThread().getId();
				MDC.put("log.name",packetName);
				log.info("Cliente conectado!");
				log.info("Ip: "+s.getInetAddress()+"puerto:."+s.getPort());
				try {
					int option = 0;
					while (option!=3) {
						mensaje = this.entrada.readLine();
						mensaje.trim();
						option = Integer.parseInt(mensaje); 
						
						switch (option) {
							case 1:
								enviar();
								break;
							case 2:
								
								break;
							case 3:
								log.info("El cliente se ha desconectado");
								s.close();
								break;
						}
					};
					
				} catch (Exception e) {
			
				};
				
			};

}*/
