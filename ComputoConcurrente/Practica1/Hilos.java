import java.util.List;
import java.util.ArrayList;

/**
 * Clase que ejemplifica los Hilos implementando Runnable
 * @author Kassandra Mirael
 * @version 1.2
 */
public class Hilos implements Runnable {

    @Override
    public void run() { // Sobrescribimos el metodo run        
        int ID = Integer.parseInt(Thread.currentThread().getName());
        if(ID == 1){
            System.out.println("Soy el hilo 1");
        }else{
            System.out.println("Hola soy el: Hilo "+ Thread.currentThread().getName()); // Pedimos el nombre del hilo pidiendo primero que se seleccione el Hilo
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Hilos h = new Hilos(); // Se crea una instancia de la clase
        List<Thread> list = new ArrayList<>(); // Creamos una lista para nuestros hilos

        for (int i = 1; i <= 10; i++) {
            Thread t = new Thread(h,String.valueOf(i)); // Creamos un hilo, le pasamos de parametro la instancia de la clase y un nombre
            t.start(); // Se inicializan los hilos para comenzar su ejecucion
        }        
        for (Thread t : list) {
            t.join(); // Detenemos la ejecución de los Hilos para evitar goteras
        }        
    }
}
