package kas.concurrrente;

import java.util.Scanner;

import kas.concurrrente.prodcons.ProductorConsumidor;
import kas.concurrrente.snapshot.Snapshot;
import kas.concurrrente.snapshotImp.WFSnapshot;

public class Main implements Runnable {
    
    private Snapshot<Integer> almacen;
    private Scanner lector;
    private boolean continuar;

    public Main(int capacidad) {
        this.almacen = new WFSnapshot<Integer>(capacidad, 0);
        lector = new Scanner(System.in);
    }

    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("Verificador")) {
            System.out.println("Dentro verificador");
            while (continuar) {
                System.out.println("1 para continuar, otro numero para terminar");
                continuar = (lector.nextInt() == 1);
            }
            lector.close();
        } else {
            while (continuar) {
                System.out.println("HILO: " + Thread.currentThread().getName());
                almacen.update(1);
            }
        }
    }

    public static void imprime(Integer[] almacen) {
        for (int i = 0; i < almacen.length; ++i) {
            System.out.println("Lugar: " + i + " con almacenamineto de " + almacen[i]);
        }
    }

    public Snapshot<Integer> getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Snapshot<Integer> almacen) {
        this.almacen = almacen;
    }

    public static void main(String[] args) throws InterruptedException {
        ProductorConsumidor prodCons = new ProductorConsumidor(10, 100);
        Thread[] productores = new Thread[10];
        Thread consumidor = new Thread(prodCons,"Consumidor");
        Thread consumidor2 = new Thread(prodCons,"Consumidor");
        for (int i = 0; i < productores.length; i++) {
            productores[i] = new Thread(prodCons, "" + i);
        }
        

        for (int i = 0; i < productores.length; i++) {
            productores[i].start();
            
        }
        consumidor.start();
        for (int i = 0; i < productores.length; i++) {
            productores[i].join();
        }
        consumidor.join();
        prodCons.imprime();
        consumidor2.start();
        consumidor2.join();
        prodCons.imprime();

        /* 
        System.out.println("Practica 4 xd");
        Main m = new Main(10);

        Thread[] productores = new Thread[10];
        ProductorConsumidor pr = new ProductorConsumidor(10, 25);
        Thread produc = new Thread(pr,"");
        Thread verificador = new Thread(m, "Verificador");

        for (int i = 0; i < productores.length; i++) {
            productores[i] = new Thread(m, "" + i);
        }

        for (int i = 0; i < productores.length; i++) {
            productores[i].start();
        }

        for (int i = 0; i < productores.length; i++) {
            productores[i].join();
        }

        verificador.join();
        imprime(m.getAlmacen().scan());
        */

    }
}