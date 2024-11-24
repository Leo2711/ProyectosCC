package kas.concurrrente.snapshotImp;

import java.util.Arrays;

import kas.concurrrente.snapshot.Snapshot;
import kas.concurrrente.stamped.StampedSnap;

/**
 * Implementación de un Snapshot Wait Free.
*/
public class WFSnapshot<T> implements Snapshot<T> {

    // Arreglo parametrizado de objetos StampedSnap.
    private StampedSnap<T>[] aTable;

    /**
     * Metodo constructor.
     * 
     * @param capacity La capacidad.
     * @param init     El valor de inicio por celda.
     */
    public WFSnapshot(int capacity, T init) {
        aTable = (StampedSnap<T>[]) new StampedSnap[capacity];
        for (int i = 0; i < capacity; i++) {
            aTable[i] = new StampedSnap<T>(init);
        }
    }

    /**
     * Metodo que escribe el valor v en el registro.
     * del proceso que realiza la llamada.
     * 
     * @param value La variable a escribir en el arreglo.
     */
    @Override
    public void update(T value) {
        int me = getThreadId();
        T[] snap = scan();
        StampedSnap<T> oldValue = aTable[me];
        StampedSnap<T> newValue = new StampedSnap<T>((oldValue.getStamp()) + 1, value, snap);
        aTable[me] = newValue;
    }

    /**
     * Metodo que construye una vista instantanea del arreglo de registros.
     * 
     * @return El arreglo de registros.
     */
    @Override
    public T[] scan() {
        StampedSnap<T>[] oldCopy;
        StampedSnap<T>[] newCopy;

        boolean[] moved = new boolean[aTable.length];
        oldCopy = collect();
        collect: while (true) {
            newCopy = collect();
            for (int j = 0; j < aTable.length; j++) {
                if (oldCopy[j].getStamp() != newCopy[j].getStamp()) {
                    if (moved[j]) {
                        return oldCopy[j].getSnap();
                    } else {
                        moved[j] = true;
                        oldCopy = newCopy;
                        continue collect;
                    }
                }
            }
            T[] result = (T[]) new Object[aTable.length];
            for (int j = 0; j < aTable.length; j++) {
                result[j] = newCopy[j].getValue();
            }
            return result;
        }
    }

    /**
     * Metodo que obtiene una copia de los valores del arreglo.
     * 
     * @return La copia de los valores del arreglo.
     */
    private StampedSnap<T>[] collect() {
        StampedSnap<T>[] copy = (StampedSnap<T>[]) new StampedSnap[aTable.length];
        for (int j = 0; j < aTable.length; j++) {
            copy[j] = aTable[j];
        }
        return copy;
    }

    /**
     * Metodo que devuelve una referencia al arreglo aTable.
     * 
     * @return arreglo aTable
     */
    public StampedSnap<T>[] getATable() {
        return aTable;
    }

    /**
     * Metodo que obtiene el valor de acuerdo a la posicion aTable.
     * 
     * @param pos La posicion de aTable.
     * @return El valor.
     */
    public T getStampedSnap(int pos) {
        return (T) aTable[pos].getValue();
    }

    /**
     * Metodo que obtiene el identificador del hilo actual.
     * 
     * @return entero que representa al hilo actual.
     */
    private int getThreadId() {
        String t = Thread.currentThread().getName().replaceAll("\\D+", "");
        return t.equals("") ? 0 : Integer.parseInt(t);
    }

    /**
     * Metodo que obtiene la representación en cadena de un WFSnapshot, 
     * mostrando el estado de cada celda en aTable.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("WFSnapshot:\n");
        for (int i = 0; i < aTable.length; i++) {
            sb.append("Thread ").append(i).append(": Stamp=").append(aTable[i].getStamp());
            sb.append(", Value=").append(aTable[i].getValue());
            sb.append(", Snapshot=").append(Arrays.toString(aTable[i].getSnap())).append("\n");
        }
        return sb.toString();
    }
}
