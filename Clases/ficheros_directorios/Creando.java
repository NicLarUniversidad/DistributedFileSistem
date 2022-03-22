package ficheros_directorios;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Creando {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Ingrese el nombre que desea para el nuevo archivo");
        String nombre = br.readLine();
        System.out.println("Ingrese la extension del archivo");
        String extension = null;
        int opc = 0;
        while(opc!=1 && opc!=2 && opc!=3) {
            try {
                Scanner scan = new Scanner(System.in);
                System.out.println("*****Extensiones válidas******");
                System.out.println("1. .txt      ");
                System.out.println("2. .docx     ");
                System.out.println("3. .xlsx     ");
                System.out.println("-----------------------------");
                System.out.println("Opcion======>");
                opc = scan.nextInt();
                System.out.println();
            }catch (InputMismatchException ex) {
                System.out.println("Error!");
            }
                if (opc == 1) {
                    extension = ".txt";
                } else if (opc == 2) {
                    extension = ".docx";
                } else if (opc == 3) {
                    extension = ".xlsx";
                } else {
                    System.out.println("Ingrese una opcion valida");
                }
            }

        File ruta= new File("C:" + File.separator + "Users" + File.separator + "LENOVO" + File.separator + "Desktop" + File.separator + "java_ser" + File.separator + nombre+ extension);

        //ruta.mkdir();  //crea una carpeta

        String archivo_destino= ruta.getAbsolutePath();

        try {
            ruta.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

            Escribiendo accede_es = new Escribiendo();
            accede_es.escribir(archivo_destino);
    }
}