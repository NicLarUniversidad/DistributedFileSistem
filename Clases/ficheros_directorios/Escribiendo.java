package ficheros_directorios;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Escribiendo {

    public void escribir(String ruta_archivo) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Ingrese el texto con el que desea crear el archivo");
        String frase = br.readLine();
        //String frase= "Esto es un ejemplo. Espero que funcione bien";

        try{

            FileWriter escritura= new FileWriter(ruta_archivo);

            for(int i=0; i<frase.length();i++){
                escritura.write(frase.charAt(i));
            }

            escritura.close();
            System.out.println("Archivo creado correctamente");


        }catch(IOException e){

        }

    }
}
