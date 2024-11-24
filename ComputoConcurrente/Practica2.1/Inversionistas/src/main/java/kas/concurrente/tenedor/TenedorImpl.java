package kas.concurrente.tenedor;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Clase que implementa el tenedor
 */
public class TenedorImpl implements Tenedor {

    private int id;
    private int vecesTomado;
    private ReentrantLock petersonLock;

    /**
     * Un tenedor consta de un identificador, 
     * el número de veces que ha sido tomado y un candado.
     * @param id el identificador del candado.
     */
    public TenedorImpl(int id){
        this.id = id;
        this.vecesTomado = 0;
        this.petersonLock = new ReentrantLock();
    }

    @Override
    public void tomar() {    
        this.petersonLock.lock();
        this.vecesTomado++;
    }
    //SOLTAMOS EL LOCK SOLO SI ESTA USADO
    @Override
    public void soltar() {        
        if (this.petersonLock.isLocked()) {
            this.petersonLock.unlock();
        }
    }

    @Override
    public int getId() {
       return this.id;
    }

    @Override
    public int getVecesTomado() {
        return this.vecesTomado;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setVecesTomado(int vecesTomado) {
        this.vecesTomado = vecesTomado;
    }
    
}
