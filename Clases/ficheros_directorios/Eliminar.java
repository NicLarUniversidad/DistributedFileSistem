package ficheros_directorios;

import java.io.File;

public class Eliminar {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        File ruta= new File("C:" + File.separator + "Users" + File.separator + "LENOVO" + File.separator + "Desktop" + File.separator + "java_ser"+ File.separator + "prueba_texto.txt");

        ruta.delete();


    }
}
