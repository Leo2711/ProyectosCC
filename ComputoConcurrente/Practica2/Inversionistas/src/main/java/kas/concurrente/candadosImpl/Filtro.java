package kas.concurrente.candadosImpl;

import kas.concurrente.candados.Semaphore;

/**
 * Clase que modela el Algoritmo del Filtro Modificado
 * @version 1.0
 * @author Kassandra Mirael
 */
public class Filtro implements Semaphore {

    private volatile int[] level;
    private volatile int[] victima;
    private int maxLevel;
    private int permitCS;
    private int hilos;

    /**
     * Constructor del Filtro
     * @param hilos El numero de Hilos Permitidos
     * @param maxHilosConcurrentes EL numero de hilos concurrentes simultaneos
     */
    public Filtro(int hilos, int maxHilosConcurrentes) {
        this.hilos = hilos;
        maxLevel = hilos -1;
        level = new int[hilos];
        victima = new int[hilos - maxHilosConcurrentes +1];//Dependiendo la implementacion, se pone +1, 0 o -1
        for (int i = 0; i < hilos; i++) {
            level[i] = 0;
        }
        /*
        for (int i = 0; i < hilos; i++) { No es necesaria esta parte
            victima[i] = -1;
        } */ 
        permitCS = maxHilosConcurrentes; //Esta variable a veces puede estar de adorno, en este caso, es asi (con esta implementacion, luego les enseño otra)
    }
    @Override
    public int getPermitsOnCriticalSection() {
        // Dudoso
        return permitCS;
    }

    @Override
    public void acquire() {
        int id = Integer.valueOf(Thread.currentThread().getName());
        /*for (int i = 1; i <= maxLevel; i++) {
            // Itentamos el nivel 1
            level[id] = i;
            victima[i] = id;
            // Iteramos mientras existan conflictos
            for (int k = 0; k <= maxLevel; k++) {
                if (k != id)
                    while ((level[k] >= i && victima[i] == id)) {};
            }
        }
        permitCS--;
        if (permitCS > 0 ){               
            level[id] = 0;
        }*/
        for(int L=1; L< victima.length; L++){
            level[id] = L;
            victima[L] = id;
            for(int k = 0; k< hilos; ++k){
                while((k!=id) && level[k]>=L && victima[L]==id);        
            }
        }

    }

    @Override
    public void release() {
        permitCS++;//no afectara a futuro, pero pues hay condiciones de carrera
        int id = Integer.valueOf(Thread.currentThread().getName());
        level[id] = 0;
    }
}