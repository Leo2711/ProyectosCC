package kas.concurrente;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import kas.concurrente.modelos.Estacionamiento;

/**
 * Clase principal, la usaran para SUS pruebas
 * Pueden modigicar los valores estaticos para ver como funciona
 * NO USEN VALORES EXTREMEDAMENTE ALTOS, puede alentar mucho su compu
 * Usa 2 objetos, uno del tipo FiltroModificado
 * Y otro de tipo estacionamiento (Salvo que le quieran meter tercer piso)
 * PUNTO EXTRA SI IMPLEMENTAN 5 PISOS AL ESTACIONAMIENTO
 * @author Kassandra Mirael
 * @version 1.0
 */
public class Main implements Runnable{
    // Usamos un valor medianamente pequeño para que pase las pruebas.
    // Además, usamos el mismo valor en LUGAR, deben coincidir para que funcione correctamente.
    public static final int NUM_CARROS = 1000;
    public static final int NUM_CARROS_PERMITIDOS = 50;
    Semaphore semaforo;
    Estacionamiento es;

    /**
     * Metodo constructor
     * Se inicializa el Filtro Modificado con _______
     * Se inicalizco el Estacionamiento con _______
     */
    public Main(){        
        semaforo =  new Semaphore(NUM_CARROS_PERMITIDOS);
        es = new Estacionamiento(NUM_CARROS_PERMITIDOS);
    }

    /**
     * Una documentacion del main xD
     * Paso 0: Lee estas instrucciones
     * Paso 1: Crea el Objeto de tipo main
     * Paso 2: Crea Una estructura de datos que contenga a nuestros hilos
     * Paso 3: Genera con un ciclo, el cual inialice un numero igual de NUM_CARROS
     * Paso 4: No olvides agregarlos a la estructura e inicializarlos
     * Paso 5: Finalmente has un Join a tus hilos
     * @param args Los Argumentos
     * @throws InterruptedException Por si explota su compu al ponerle medio millon de hilos xD
     */
    public static void main(String[] args) throws InterruptedException{        
        Main m = new Main();
        List<Thread> hilos = new ArrayList<>();

        for (int i = 0; i < NUM_CARROS; i++) {            
            Thread t = new Thread(m, ""+i);
            hilos.add(t);
            t.start();
        }

        for (Thread t : hilos) {
            t.join();
        }
    }

    /**
     * Aqui esta su primer seccion crítica
     * Paso 1: Keep calm and ...
     * Paso 2: Beware with the concurrent code
     * Paso 3: Obten el ID de tu hilo
     * Paso 4: TU CARRO (HILO) ENTRARA AL ESTACIONAMIENTO
     */
    @Override
    public void run(){
        int nombre = Integer.parseInt(Thread.currentThread().getName());
        try {
            semaforo.acquire();
            es.entraCarro(nombre);
        } catch (Exception e) {
            e.printStackTrace();
        }
        semaforo.release();
    }
}
