package kas.concurrente.candadosImpl;

import kas.concurrente.candado.Semaphore;

/**
 * Clase que modela el Algoritmo del Filtro Modificado
 * @version 1.0
 * @author Kassandra Mirael
 */
public class Filtro implements Semaphore {

    private volatile int[] level;
    private volatile int[] victima;
    private volatile boolean[] enCS;
    private int maxLevel;
    private int permitCS;
    private int concurrentes;

    /**
     * Constructor del Filtro
     * @param hilos El numero de Hilos Permitidos
     * @param maxHilosConcurrentes EL numero de hilos concurrentes simultaneos
     */
    public Filtro(int hilos, int maxHilosConcurrentes) {
        enCS = new boolean[hilos];
        maxLevel = hilos -1;
        level = new int[hilos];
        victima = new int[hilos];
        for (int i = 0; i < hilos; i++) {
            level[i] = 0;
            enCS[i] = false;        
            victima[i] = -1;
        }
        permitCS = 0; 
        concurrentes = maxHilosConcurrentes;
    }

    @Override
    public int getPermitsOnCriticalSection() {        
        return permitCS;
    }

    @Override
    public void acquire() {
        int id = getThreadId();
        for (int i = 1; i <= maxLevel; i++) {
            // Itentamos el nivel 1
            level[id] = i;
            victima[i] = id;
            // Iteramos mientras existan conflictos
            for (int k = 0; k <= maxLevel; k++) {
                if (k != id)
                    while ((level[k] >= i && victima[i] == id)) {};
            }
        }
        permitCS++;
        int vecino1 = (id + 1) % (maxLevel+1);
        int vecino2 = id - 1;
        if(vecino2 < 0)
            vecino2 = 0;
        if (permitCS < concurrentes && !enCS[vecino1] && !enCS[vecino2]){
            enCS[id] = true;
            level[id] = 0;
        }
    }

    @Override
    public void release() {
        permitCS--;
        int id = getThreadId();
        enCS[id] = false;
        level[id] = 0;
    }

    private int getThreadId() {
        String t = Thread.currentThread().getName().replaceAll("\\D+", "");
        return t.equals("") ? 0 : Integer.parseInt(t);
    }
    
}
