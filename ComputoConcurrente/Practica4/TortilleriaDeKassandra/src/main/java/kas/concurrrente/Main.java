package kas.concurrrente;

import kas.concurrrente.candadosImpl.Bakery;
import kas.concurrrente.snapshotImp.WFSnapshot;

/**
 * Clase que simula la interacción de varios clientes en una tortillería.
 * Utiliza dos mecanismos importantes: 
 * el algoritmo de Snapshot Wait Free y 
 * la implementación de un candado "Bakery".
 */
public class Main implements Runnable {

    
    // WFSnapshot de tipo Integer para realizar snaps del estado.
    private WFSnapshot<Integer> wfsnapshot;
    // Identificador único para cada cliente.
    private int id;
    // Instancia de Bakery para garantizar la exclusión mutua.
    private Bakery bakeryLock;

    /**
     * Metodo constructor que asigna a los atributos correspondientes.
     * @param wfsnapshot los snaps
     * @param id un identificador
     * @param bakeryLock el candado
     */
    public Main(WFSnapshot<Integer> wfsnapshot, int id, Bakery bakeryLock) {
        this.wfsnapshot = wfsnapshot;
        this.id = id;
        this.bakeryLock = bakeryLock;
    }

    /**
     * Metodo que se ejecutará cuando se inicie un hilo. 
     * En este caso, un cliente ingresa a la tortillería, 
     * adquiere un bloqueo de tipo Bakery, 
     * actualiza el estado del WFSnapshot, 
     * simula un tiempo de espera en la tortillería y lo despacha.
     */
    @Override
    public void run() {        
        System.out.println("El cliente " + id + " ingresó a la tortillería.");
        bakeryLock.lock();
        try {
            wfsnapshot.update(id);
        } finally {
            bakeryLock.unlock();
        }

        // Simula el tiempo que un cliente pasa en la tortillería
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Simula el despacho de un cliente
        System.out.println("Cliente " + id + " despachado.");
    }

    /**
     * Metodo prinicpal, crea varios hilos para representar a los clientes. 
     * Se inicilizan y se espera a que los clientes terminen. 
     * Luego, se muestra el estado final de los clientes en la tortillería con el WFSnapshot.
     * @param args La entrada de nuestro programa.
     */
    public static void main(String[] args) {
        int numClientes = 5;

        WFSnapshot<Integer> wfsnapshot = new WFSnapshot<>(numClientes, 0);
        Bakery bakeryLock = new Bakery(numClientes);
        Thread[] clientes = new Thread[numClientes];

        for (int i = 0; i < numClientes; i++) {
            clientes[i] = new Thread(new Main(wfsnapshot, i, bakeryLock));
        }

        for (Thread thread : clientes) {
            thread.start();
        }        
        for (Thread thread : clientes) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Muestra el estado final del WFSnapshot
        System.out.println("Estado final del WFSnapshot:");
        System.out.println(wfsnapshot);
    }
}
