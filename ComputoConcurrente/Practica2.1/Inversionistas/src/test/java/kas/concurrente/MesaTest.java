package kas.concurrente;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.Semaphore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kas.concurrente.inversionista.Inversionista;
import kas.concurrente.inversionista.InversionistaFiltro;
import kas.concurrente.tenedor.Tenedor;
import kas.concurrente.tenedor.TenedorImpl;

public class MesaTest {
    static final int TAM_MESA = 6;
    static final int DIEZ_SEC = 10000;
    // static final int DIEZ_SEC = 100;
    static int VECES_COMIDOD_ESPERADO = 600;

    Inversionista[] inversionistas;
    Thread[] hilos;
    Tenedor[] tenedores;

    Semaphore semaforo;

    @BeforeEach
    void setUp(){
        inicializaTenedores();
    }

    void inicializaTenedores(){
        tenedores = new Tenedor[TAM_MESA];
        for(int i = 0; i < TAM_MESA; i++){
            tenedores[i] = new TenedorImpl(i);
        }
    }

    @Test
    void todosHanIntentadoComerFiltro() throws InterruptedException{
        inicializaInversionistasYCubierto(InversionistaFiltro.class);

        boolean losTenedoresFueronTomadosAlMenosUnaVez = true;

        for(Tenedor t: tenedores){
            losTenedoresFueronTomadosAlMenosUnaVez = losTenedoresFueronTomadosAlMenosUnaVez && t.getVecesTomado() > 0;
        }
    }

    @Test
    void todosComieronFiltro() throws InterruptedException{
        inicializaInversionistasYCubierto(InversionistaFiltro.class);

        boolean comieronTodasLasVeces = true;
        for(Inversionista in : inversionistas){
            System.out.println(in.getVecesComido());
            comieronTodasLasVeces = comieronTodasLasVeces && (in.getVecesComido() >= VECES_COMIDOD_ESPERADO && 
            in.getVecesComido() <= in.getTenedorIzq().getVecesTomado() && 
            in.getVecesComido() <= in.getTenedorDer().getVecesTomado());
        }

        assertTrue(comieronTodasLasVeces);
    }

    void inicializaInversionistasYCubierto(Class<? extends Inversionista> clazz) throws InterruptedException{
        inicializaInversionistas(clazz);
        initHilos();
        ejecutaHilos();
    }

    void inicializaInversionistas(Class<? extends Inversionista> clazz){
        semaforo = new Semaphore(TAM_MESA-1);
        inversionistas = new Inversionista[TAM_MESA];

        try{
            Class[] cArg = new Class[] {Semaphore.class};
            for(int i = 0; i < TAM_MESA; ++i){
                inversionistas[i] = new InversionistaFiltro(semaforo);
                inversionistas[i].setId(i);
                inversionistas[i].setTenedorIzq(tenedores[i]);
                inversionistas[i].setTenedorDer(tenedores[(i+1)%TAM_MESA]);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    void initHilos(){
        hilos = new Thread[TAM_MESA];
        for(int i = 0; i< TAM_MESA; ++i){
            hilos[i] = new Thread(inversionistas[i],""+i);
        }
    }

    void ejecutaHilos() throws InterruptedException {
        for(Thread t : hilos){
            t.start();
        }

        Thread.sleep(DIEZ_SEC);

        for(Thread t : hilos){
            t.interrupt();
        }

        for(Thread t : hilos){
            t.join();
        }
    }
}
