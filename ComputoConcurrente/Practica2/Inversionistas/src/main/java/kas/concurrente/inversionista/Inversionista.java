package kas.concurrente.inversionista;

import kas.concurrente.tenedor.Tenedor;

/**
 * Clase abstracta que modela al inversionista.
 * El inversionista tiene 2 tenedores a sus lados.
 * El inversionista posee un ID para que se pueda identificar.
 * El inversionista tiene una variable que indica el numero de veces que ha comido.
 * @version 1.0
 * @author Kassandra Mirael
 */
public abstract class Inversionista implements Runnable {

    protected int id;
    protected Tenedor tenedorIzq;
    protected Tenedor tenedorDer;
    protected int vecesComido;
 


    @Override
    public void run() {                
        /**
         * El inversionista debe pensar y entrar a la mesa un periodo de veces
         * puesto en el test, agrega el valor aqui.
         */ 
        
            // Inicializamos el inversionistas en donde primero piensan
            // Y despues entran a la mesa
        for (int i = 0; i < 700; i++) {
            try {
                piensa();
                entraALaMesa();
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
            
        }
        
    }

    /**
     * Metodo que nos permite entrar a la mesa.
     * El inversionista por fin dejo de pensar y de escribir en su
     * servilleta y se digna en entrar.
     * PRIMERO toma los tenedores.
     * DESPUES come.
     * FINALMENTE los suelta para que los demas los puedan usar.
     * @throws InterruptedException <Escribe porque se lanzaria esta exception>
     */
    public void entraALaMesa() throws InterruptedException{
        tomaTenedores();
        try {
            come();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
        //sueltaTenedores(); En este caso no afecta, dado que mas adelante hacemos la sobrescritura, pero pues esta es la otra manera
        this.tenedorDer.soltar();
        this.tenedorIzq.soltar();
    }

    /**
     * Una vez que termino de pensar sobre finanzas el inversionista
     * se prepara para comer.
     * El inversionista le toma un par de milisegundos comer.
     * ESTA ES LA SECCION CRITICA, SIGNIFICA PELIGRO
     * Incrementa el numero de veces que ha comido.
     * @throws InterruptedException <Escribe porque se lanzaria esta exception>
     */
    public void come() throws InterruptedException{
        int ID = Integer.valueOf(Thread.currentThread().getName());
        if(ID == 4){
            this.tenedorIzq.tomar();
            this.tenedorIzq.tomar();
        }else{
            this.tenedorDer.tomar();
            this.tenedorIzq.tomar();
        }
        //Simulamos que el inversionista come, y aumentamos las veces que ha comido
        Thread.sleep(generaTiempoDeEspera());
        this.vecesComido +=1;
        // El interruptedException ocurre por el sleep y puede que sea interrumpido por otro hilo
    }

    /**
     * Metodo que hace que el inversionista piense.
     * El inversionista pensara por una par de milisegundos.
     * Esto pasa antes de que tome sus tenedores.
     * @throws InterruptedException <Escribe porque se lanzaria esta exception>
     */
    public void piensa() throws InterruptedException {
        //Simulacion de que piensa
        Thread.sleep(generaTiempoDeEspera());
        // El interruptedException ocurre por el sleep y puede que sea interrumpido por otro hilo

    }

    /**
     * Metodo que nos permite tomar los tenedores.
     * Los toma uno por uno.
     */
    public  void tomaTenedores(){
        int id = Integer.valueOf(Thread.currentThread().getName());
        if(id == 4){
            getTenedorDer().tomar();
            getTenedorIzq().tomar();
        } else {
            getTenedorIzq().tomar();
            getTenedorDer().tomar();
        }
    }

    /**
     * Metodo que hace que el inversionista suelte ambos tenedores una vez
     * que terminara de comer. 
     * De esta manera otro los puede usar.
     * Suelta los tenedores uno por uno.
     */
    public  void sueltaTenedores(){
        getTenedorIzq().soltar();
        getTenedorDer().soltar();
    }
    
    /**
     * Metodo que genera un numero pseudoaleatorio entre 1 y 10
     * @return El tiempo de espera
     */
    protected long generaTiempoDeEspera(){
        double i=Math.random()*10.0;
        return (long)i ;
    }

    /**
     * Regresa el identificador del invesionista.
     * @return número que identifica al inversionista.
     */
    public int getId(){
        return this.id;
    }

    /**
     * Establece el identificador del inversionista.
     * @param id el número con que identificaremos al invesionista.
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Regresa el tenedor a la izquierda del inversionista.
     * @return tenedor izquierdo.
     */
    public Tenedor getTenedorIzq(){
        return this.tenedorIzq;
    }

    /**
     * Establece el tenedor izquierdo del inversionista.
     * @param t el tenedor a establecer.
     */
    public void setTenedorIzq(Tenedor t){
        this.tenedorIzq = t;
    }

    /**
     * Regresa el tenedor a la derecha del inversionista.
     * @return tenedor derecho.
     */
    public Tenedor getTenedorDer(){
        return tenedorDer;
    }

    /**
     * Establece el tenedor derecho del inversionista.
     * @param t el tenedor a establer.
     */
    public void setTenedorDer(Tenedor t){
        this.tenedorDer = t;
    }

    /**
     * Regresa el numero de veces que ha comido el inversionista.
     * @return el numero de veces que ha comido el inversionista.
     */
    public int getVecesComido(){
        return vecesComido;
    }

    /**
     * Establece el número de veces que ha comido el inversionista.
     * @param vecesComido el numero de veces que ha comido el inversionista.
     */
    public void setVecesComido(int vecesComido){
        this.vecesComido = vecesComido;
    }
}
