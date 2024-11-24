package Ejemplo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.Scanner;

/*
 * Problema Readers and Writers
 * Supongamos que una base de datos se comparte entre varios procesos simultáneos. 
 * Algunos de estos procesos sólo quieren leer la base de datos, mientras que otros 
 * quieren actualizarla (es decir, leer y escribir). Distinguimos entre estos dos tipos 
 * de procesos denominando a los primeros lectores y a los segundos escritores.
 * Parámetros del problema: 

    -Un conjunto de datos se comparte entre varios procesos. 
     
    -Una vez que un escritor está listo, realiza su escritura. Sólo un escritor puede escribir a la vez. 
     
    -Si un proceso está escribiendo, ningún otro proceso puede leerlo. 
     
    -Si al menos un lector está leyendo, ningún otro proceso puede escribir. 
     
    -Los lectores no pueden escribir y sólo pueden leer.
 */

public class ReadersWriters implements Runnable {
    private boolean writer;
    private int id;
    private int limite;
    private Random rd;
    private String file;

    public ReadersWriters(boolean writer, int id, int limite, String file){
        this.writer = writer;
        this.id = id;
        this.limite = limite;
        rd = new Random();
        this.file = file;
    }

    @Override
    public void run() {
        for (int i = 0; i < limite; i++) {
            if(writer){
                write();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                if(rd.nextBoolean())
                    read();
            } else {
                read();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    private synchronized void write() {
        // Generamos texto random.
        byte[] array = new byte[11]; 
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        String texto = String.format("Hilo #%d: ",id) + generatedString + "\n";
        // Lo ponemos en el archivo "file"
        try {
            FileWriter myWriter = new FileWriter(file, true);
            myWriter.write(texto);
            myWriter.close();
            System.out.println(String.format("-Hilo #%d: Yo escritor magico he escrito mis pensamientos.", id));
        } catch (IOException e) {
            System.out.println(String.format("-Hilo #%d: He fallado y no pude escribir esta vez", id));
        e.printStackTrace();
        }
    }
    
    private synchronized void read() {
        //leemos el archivo.
        try {
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            System.out.println(String.format("-Hilo #%d: Estoy leyendo: ", id));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
            System.out.println(String.format("-Hilo #%d: Ya deje de leer: ", id));
        } catch (FileNotFoundException e) {
            System.out.println(String.format("-Hilo #%d: He fallado y no pude leer los textos sagrados esta vez", id));
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Random rd = new Random();
        for (int i = 0; i < 10; i++) {
            ReadersWriters rw = new ReadersWriters(rd.nextBoolean(), i, (rd.nextInt() % 5)+1, args[0]);
            Thread h = new Thread(rw);
            h.start();
            try {
                h.join();
            } catch (InterruptedException e) {
            }
        }
    }
}
