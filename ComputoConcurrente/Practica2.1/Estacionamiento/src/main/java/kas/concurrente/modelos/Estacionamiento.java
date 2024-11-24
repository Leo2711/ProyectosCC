package kas.concurrente.modelos;

import java.util.Random;

/**
 * En esta clase se simula el estacionamiento en si
 * Posee un arreglo de tipo Lugar
 * Posee un entero de lugaresDisponibles
 * @author Kassandra Mirael
 * @version 1.0
 */
public class Estacionamiento {

    Lugar[] lugares;
    int capacidad;
    int lugaresDisponibles;

    /**
     * Metodo constructor
     * @param capacidad La capacidad del estacionamiento
     */
    public Estacionamiento(int capacidad){
        this.capacidad = capacidad;
        this.lugares = new Lugar[capacidad];
        inicializaLugares();
        this.lugaresDisponibles = this.capacidad;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public Lugar[] getLugares() {
        return lugares;
    }

    public void setLugares(Lugar[] lugares) {
        this.lugares = lugares;
    }

    public int getLugaresDisponibles() {
        return lugaresDisponibles;
    }

    public void setLugaresDisponibles(int lugaresDisponibles) {
        this.lugaresDisponibles = lugaresDisponibles;
    }

    /**
     * Metodo que nos indica si esta lleno el estacionamiento
     * @return true si esta lleno, false en otro caso
     */
    public boolean estaLleno(){
        return lugaresDisponibles == 0;
    }

    /**
     * Metodo que inicaliza los lugares del arreglo
     * Este es un método optativo
     */
    public void inicializaLugares(){
        for (int i = 0; i < capacidad; i++) {
            lugares[i] = new Lugar(i);
        }
    }

    /**
     * Metodo en el que se simula la entrada de un carro
     * Imprime un texto que dice que el carro a entrado de color AZUL
     * @param nombre El nombre del carro
     * @throws InterruptedException Si llega a fallar
     */
    public void entraCarro(int nombre) throws InterruptedException{
        String texto = String.format("El carro %d a entrado.", nombre);
        System.out.println("\u001B[31m" + texto + "\u001B[0m");                        
        int lugar = obtenLugar();
        asignaLugar(lugar);
    }

    /**
     * Metodo que asigna el lugar, una vez asignado ESTACIONA su nave
     * @param lugar El lugar que correspone
     * @throws InterruptedException
     */
    public void asignaLugar(int lugar) throws InterruptedException {         
        System.out.println("Estacionando la nave... " + lugar);                  
        lugares[lugar].estaciona();
        this.lugaresDisponibles--;
    }

    /**
     * Se obtiene un lugar de forma pseudoAleatoria
     * @return Retorna el indice del lugar
     */
    public int obtenLugar(){
        Random random = new Random();
        int lugar = random.nextInt(capacidad);
        return lugar;
    }
}