package kas.concurrente;

import java.util.concurrent.atomic.AtomicReference;

public class MCSLock implements Lock {

    class QNode{
        volatile boolean locked = false;
        QNode next = null;
    }

    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myNode;

    public MCSLock() {
        this.tail = new AtomicReference<QNode>(null);
        this.myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
    }

    @Override
    public void lock() {
        QNode qnode = myNode.get();
        QNode pred = tail.getAndSet(qnode);
        if (pred != null) {
            qnode.locked = true;
            pred.next = qnode;
            // wait until predecessor gives up the lock
            while (qnode.locked) {
                // Hot fix : Usar yield() para ceder el tiempo de CPU
                Thread.yield();
            }
        }
    }

    @Override
    public void unlock() {
        QNode qnode = myNode.get();
        if (qnode.next == null) {
            if (tail.compareAndSet(qnode, null)) {
                return;
            }
            // wait until predecessor fills in its next field
            while (qnode.next == null) {
                // Hot fix : Usar yield() para ceder el tiempo de CPU
                Thread.yield();
            }
        }
        qnode.next.locked = false;
        qnode.next = null;
    }

}
