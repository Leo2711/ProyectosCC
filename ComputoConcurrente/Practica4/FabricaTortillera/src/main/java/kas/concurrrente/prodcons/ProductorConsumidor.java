package kas.concurrrente.prodcons;
import kas.concurrrente.snapshot.Snapshot;
import kas.concurrrente.snapshotImp.WFSnapshot;
import kas.concurrrente.stamped.StampedSnap;

public class ProductorConsumidor implements Runnable {

    WFSnapshot<Integer> snapshot;
    private final Integer MAX_TORTILLAS;
    private final Integer MAX_TORTILLAS_CONS;
    
    private Integer tortillasConsumidas;

    public ProductorConsumidor(int capacity, Integer totalDeToritillas){
        snapshot = new WFSnapshot<Integer>(capacity, 0);
        MAX_TORTILLAS = totalDeToritillas;
        MAX_TORTILLAS_CONS = MAX_TORTILLAS * 8;
        tortillasConsumidas = 0;

    }

    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("Consumidor")){
            while(tortillasConsumidas < MAX_TORTILLAS_CONS){
                Integer[] arrTortillas = snapshot.scan();
                for (int i = 0; i < arrTortillas.length; i++) {
                    int tortillasConsumir = snapshot.getStampedSnap(i);
                    if(tortillasConsumir < 3){
                        continue;
                    }
                    tortillasConsumidas += 3;

                    snapshot.clear(i, tortillasConsumir-3);
                    if(tortillasConsumidas >= MAX_TORTILLAS_CONS){
                        break;
                    }

                }
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                boolean hayTortillas = false;
                for (int i = 0; i < arrTortillas.length; i++) {
                    int tortillasConsumir = snapshot.getStampedSnap(i);
                    if(tortillasConsumir != 0 && tortillasConsumir > 0){
                        hayTortillas = true;
                    }
                }
                if(!hayTortillas)
                    break;

            }
            System.out.println(String.format("Tengo todas mis %d tortillas", tortillasConsumidas));
            tortillasConsumidas = 0;
        }else {
            while(true){
                int me = Integer.parseInt(Thread.currentThread().getName());
                Integer tortillasAlAlmacen = snapshot.getStampedSnap(me);
                tortillasAlAlmacen += 1;
                if (tortillasAlAlmacen >= MAX_TORTILLAS)
                    break;
                snapshot.update(tortillasAlAlmacen);
                try {
                    Thread.currentThread().sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            
        }
    }
    
    
    public void imprime() {
        Integer[] almacen = snapshot.scan();
        for (int i = 0; i < almacen.length; ++i) {
            System.out.println("Lugar: " + i + " con almacenamineto de " + almacen[i]);
        }
    }

    
    
}
