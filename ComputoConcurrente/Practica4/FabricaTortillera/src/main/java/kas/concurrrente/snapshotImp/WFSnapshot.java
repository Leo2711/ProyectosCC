package kas.concurrrente.snapshotImp;

import kas.concurrrente.snapshot.Snapshot;
import kas.concurrrente.stamped.StampedSnap;

/**
 * Clase que implementa un Snapshot
 * 
 * @author Kassandra Mirael
 * @version 1.0
 */
public class WFSnapshot<T> implements Snapshot<T> {
    private StampedSnap<T>[] aTable;

    /**
     * MEtodo constructor
     * 
     * @param capacity La capacidad
     * @param init     El valor de inicio por celda
     */
    public WFSnapshot(int capacity, T init) {
        aTable = (StampedSnap<T>[]) new StampedSnap[capacity];
        for (int i = 0; i < capacity; i++) {
            aTable[i] = new StampedSnap<T>(init);
        }
    }

    @Override
    public void update(T value) {
        int me = Integer.parseInt(Thread.currentThread().getName());
        T[] snap = scan();
        StampedSnap<T> oldValue = aTable[me];
        StampedSnap<T> newValue = new StampedSnap<T>((oldValue.getStamp())+1, value, snap);
        aTable[me] = newValue;
    }

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
            T[] result = (T[]) new Integer[aTable.length];
            for (int j = 0; j < aTable.length; j++) {
                result[j] = newCopy[j].getValue();
            }
            return result;
        }
    }

    /**
     * Metodo que obtiene una copia de los valores del arreglo
     * 
     * @return La copia de los valores del arreglo
     */
    private StampedSnap<T>[] collect() {
        StampedSnap<T>[] copy = (StampedSnap<T>[]) new StampedSnap[aTable.length];
        for (int j = 0; j < aTable.length; j++) {
            copy[j] = aTable[j];
        }
        return copy;
    }

    public StampedSnap<T>[] getATable() {
        return aTable;
    }

    /**
     * Metodo que obtiene el valor de acuerdo a la posicion aTable
     * 
     * @param pos La posicion de aTable
     * @return El valor
     */
    public T getStampedSnap(int pos) {
        return (T) aTable[pos].getValue();
    }

    @Override
    public void clear(int i, T init) {
        T[] snap = scan();
        StampedSnap<T> oldValue = aTable[i];
        StampedSnap<T> newValue = new StampedSnap<T>((oldValue.getStamp())+1, init, snap);
        aTable[i] = newValue;
    }
}
