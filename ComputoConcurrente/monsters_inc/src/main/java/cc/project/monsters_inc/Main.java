package cc.project.monsters_inc;

import java.util.concurrent.locks.ReentrantLock;

import cc.project.monsters_inc.modelo.Puerta.TipoPuerta;
import cc.project.monsters_inc.zonas.FabricaPuertas;

public class Main implements Runnable {
    
    // Los diseñadores de puertas son hilos
    // el almacen puede ser como un WFSnapshot alamcenando las puertas en un aTable
    private static FabricaPuertas fabricaPuertas;
    // Capacidad de 4 a 10 veces el num de monstruos.
    private static int capacidadAlmacen = 100;
    private ReentrantLock lock;

    public Main() { 
        this.lock = new ReentrantLock();
    }

    private int getThreadId() {
        String t = Thread.currentThread().getName().replaceAll("\\D+", "");
        return t.equals("") ? 0 : Integer.parseInt(t);
    }

    @Override
    public void run() {         
        int id = getThreadId();
        System.out.println("El diseñador de puertas " + id + " ingresó a la fabrica.");
        lock.lock();
        try {
            System.out.println("Trabajando...");
            fabricaPuertas.fabricar(TipoPuerta.NORMAL);
        } finally {
            lock.unlock();
        }

        // Simula el tiempo que un diseñador pasa en la fabrica
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Simula la salida de un diseñador
        System.out.println("El dieñador de puertas" + id + " salió de la fabrica.");
    }

    public static void main(String[] args) throws InterruptedException {
        fabricaPuertas = new FabricaPuertas(capacidadAlmacen);        

        Thread[] disenadores = new Thread[10];
        for (int i = 0; i < disenadores.length; i++) {
            disenadores[i] = new Thread(new Main());
        }

        for (Thread thread : disenadores) {
            thread.start();
        }

        for (Thread thread : disenadores) {
            thread.join();
        }

        System.out.println("Almacen:");
        System.out.println(fabricaPuertas.getAlmacen().toString());
    }
}