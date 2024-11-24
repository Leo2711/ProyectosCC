package kas.concurrrente.stamped;

import java.util.Date;

/**
 * Clase genérica StampedSnap.
 */
public class StampedSnap<T> {
    
    // Etiqueta o stamp asociado.
    private long stamp;
    // La marca de tiempo en la que se creó.
    private Date timestamp;
    // El valor principal de tipo T.
    private T value;
    // Conjunto de snaps relacionados.
    private T[] snap;

    /**
     * Constructor sin snaps.
     * Crea un StampedSnap con estos valores y establece la marca de tiempo actual.
     * @param value su valor.
     */
    public StampedSnap(T value){
        this.stamp = 0;
        this.value = value;
        this.snap = null;
        this.timestamp = new Date(System.currentTimeMillis());
    }

    /**
     * Constructor con snaps.
     * Crea un StampedSnap con estos valores y establece la marca de tiempo actual.
     * @param label una etiqueta,
     * @param value un valor,
     * @param snap conjunto de snaps.
     */
    public StampedSnap(long label, T value, T[] snap){
        this.stamp = label;
        this.value = value;
        this.snap = snap;
        this.timestamp = new Date(System.currentTimeMillis());
    }

    /**
     * Metodo que obtiene la etiqueta o stamp asociado.
     * 
     * @return La etiqueta.
     */
    public long getStamp(){
        return stamp;
    }

    /**
     * Metodo que obtiene la marca de tiempo en la que se creó.
     * 
     * @return La fecha de creación.
     */
    public Date getTimestamp(){
        return timestamp;
    }

    /**
     * Metodo que obtiene el valor genérico.
     * 
     * @return el valor de tipo T.
     */
    public T getValue(){
        return value;
    }

    /**
     * Metodo que obtiene el conjunto de snaps.
     * 
     * @return el conjunto de snaps genéricos.
     */
    public T[] getSnap(){
        return snap;
    }

    /**
     * Método que establece la etiqueta del objeto.
     * 
     * @param stamp La etiqueta.
     */
    public void setStamp(long stamp){
        this.stamp = stamp;
    }

    /**
     * Metodo que actualiza el TimeStamp.
     */
    public void setTimestamp() {
        this.timestamp = new Date(System.currentTimeMillis());
    }

    /**
     * Metodo que establece el valor del StampedSnap.
     * 
     * @param value El valor genérico.
     */
    public void setValue(T value){
        this.value = value;
    }

    /**
     * Metodo que establece el valor del snap.
     * 
     * @param snap El snap de tipo genérico.
     */
    public void setSnap(T[] snap){
        this.snap = snap;
    }
}
