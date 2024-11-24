package kas.concurrrente.candadosImpl;

import kas.concurrrente.candado.Lock;

public class Bakery implements Lock {

    boolean[] flag;
    int[] label;
    int capacity;

    public Bakery(int capacity) {
        this.capacity = capacity;
        this.flag = new boolean[capacity];
        this.label = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            flag[i] = false;
            label[i] = 0;
        }
    }

    public void lock() {
        int me = getThreadId();
        flag[me] = true;

        int max = 0;
        for (int i = 0; i < capacity; i++) {
            int ticket = label[i];
            max = Math.max(max, ticket);
        }
        label[me] = max + 1;
        flag[me] = false;

        for (int i = 0; i < capacity; i++) {
            if (i != me) {
                while (label[i] != 0 && (label[i] < label[me] || (label[i] == label[me] && i < me))) {
                    // Wait
                    Thread.yield();
                }
            }
        }
    }

    public void unlock() {
        int me = getThreadId();
        label[me] = 0;
    }

    private int getThreadId() {
        String t = Thread.currentThread().getName().replaceAll("\\D+", "");
        return t.equals("") ? 0 : Integer.parseInt(t);
    }
}
