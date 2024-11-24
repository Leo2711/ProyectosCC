package kas.concurrente.inversionista;



import kas.concurrente.candados.Semaphore;

/**
 * Clase que modela al inversionista, pero esta vez
 * usando el filtro.
 * @version 1.1
 * @author Kassandra Mirael
 */
public class InversionistaFiltro extends Inversionista {

    private Semaphore semaforo;

    public InversionistaFiltro(Semaphore semaforo) {
        super();
        this.semaforo = semaforo;
    }

    @Override
    public void entraALaMesa() throws InterruptedException{
        semaforo.acquire();//esto esta bien
        //super.entraALaMesa();//En teoria tambien, pero te dejo esta otra manera    
        tomaTenedores();
        this.vecesComido += 1;//revisar donde se inicializa, para que no nos marque error.
        Thread.currentThread().sleep(generaTiempoDeEspera());
        sueltaTenedores(); 
        semaforo.release();//esto igual
    }

    @Override
    public void tomaTenedores() {
        getTenedorIzq().tomar();
        getTenedorDer().tomar();
    } 
}
