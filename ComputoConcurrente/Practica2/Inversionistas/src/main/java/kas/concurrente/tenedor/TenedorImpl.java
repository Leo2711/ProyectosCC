package kas.concurrente.tenedor;

import kas.concurrente.candados.Lock;
import kas.concurrente.candadosImpl.PetersonLock;

/**
 * Clase que implementa el tenedor
 */
public class TenedorImpl implements Tenedor {

    private int id;
    private int vecesTomado;
    private Lock petersonLock;

    /**
     * Un tenedor consta de un identificador, 
     * el número de veces que ha sido tomado y un candado.
     * @param id el identificador del candado.
     */
    public TenedorImpl(int id){
        this.id = id;
        this.vecesTomado = 0;
        this.petersonLock = new PetersonLock();
    }

    @Override
    public void tomar() {    
        this.petersonLock.lock();
        this.vecesTomado++;
    }

    @Override
    public void soltar() {        
        this.petersonLock.unlock();
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
