package kas.concurrente.candadosImpl;

import kas.concurrente.candado.Lock;

/**
 * Clase que implementa el candado usando el Legendario
 * algoritmo de PeterGod.
 * @version 1.0
 * @author Kassandra Mirael
 */
public class PetersonLock implements Lock {

    volatile boolean[] flag = new boolean[2];
    volatile int victima;

    @Override
    public void lock() {
        int id = Integer.valueOf(Thread.currentThread().getName());        
        flag[id] = true; // Mostramos interes en el recurso
        victima = id; // Nos hacemos la victima
        while(victima == id && flag[1-id]) {}; // Esperamos mientras el otro interesado y sea la victima.
    }

    @Override
    public void unlock() {
        int id = Integer.valueOf(Thread.currentThread().getName());
        flag[id] = false;
    }
    
}
