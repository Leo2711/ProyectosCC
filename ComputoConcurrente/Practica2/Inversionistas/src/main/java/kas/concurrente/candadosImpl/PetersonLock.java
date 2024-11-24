package kas.concurrente.candadosImpl;

import kas.concurrente.candados.Lock;

/**
 * Clase que implementa el candado usando el Legendario
 * algoritmo de PeterGod.
 * @version 1.0
 * @author Kassandra Mirael
 */
public class PetersonLock implements Lock {

    private volatile boolean[] flag;
    private volatile int victima;

    public PetersonLock(){
        flag = new boolean[2];
    }

    @Override
    public void lock() {
        int i = Integer.valueOf(Thread.currentThread().getName());
        int id = i % 2;
        flag[id] = true;
        victima = id;
        while(victima == id && flag[1-id]);
    }

    @Override
    public void unlock() {
        int i = Integer.valueOf(Thread.currentThread().getName());
        int id = i % 2;
        flag[id] = false;
    }
}