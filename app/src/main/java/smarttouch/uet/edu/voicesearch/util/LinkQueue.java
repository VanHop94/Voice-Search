package smarttouch.uet.edu.voicesearch.util;

/**
 * Created by VanHop on 4/11/2016.
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class LinkQueue<T> {
    private int maxQueueSize = 1000000;
    private ArrayBlockingQueue<T> queueData = new ArrayBlockingQueue<T>(maxQueueSize);
    private AtomicInteger miQueueSize = new AtomicInteger();
    private Object mutex;
    private String mstrQueueName = "Link Queue";

    private boolean mbLocked = false;

    public LinkQueue() {
        mutex = this;
    }

    // public LinkQueue(int maxSize)
    // {
    // maxQueueSize = maxSize;
    // mutex = this;
    // }
    public LinkQueue(int maxSize, String strQueueName) {
        mstrQueueName = strQueueName;
        maxQueueSize = maxSize;
        mutex = this;
    }

    /**
     * Appends an element to the end of the queue. If the queue
     * <p/>
     * has set limit on maximum elements and there is already specified
     * <p/>
     * max count of elements in the queue throws IndexOutOfBoundsException.
     * <p/>
     * notify to all waiting object
     */
    public void enqueueNotify(T objMsg) {
        if (!mbLocked) {
            if ((maxQueueSize > 0) && (miQueueSize.intValue() >= maxQueueSize)) {
                System.out.println("Queue is full. Element not added. Queue name (" + mstrQueueName + ")");
                return;
            }

            synchronized (queueData) {
                queueData.add(objMsg);
            }
            miQueueSize.incrementAndGet();

            synchronized (mutex) {
                mutex.notify();
            }
        }
    }

    public T dequeueWait(int iSecondTimeout) {
        T objMsg = objMsg = poolFirstSync();

        if (objMsg == null) {
            for (int i = 0; i < iSecondTimeout; i++) {
                synchronized (mutex) {
                    try {
                        mutex.wait(1000);
                    } catch (InterruptedException e) {
                    }
                }
                objMsg = poolFirstSync();

                if (objMsg != null) {
                    miQueueSize.decrementAndGet();
                    return objMsg;
                }
            }
        }
        if (objMsg != null) {
            //System.out.println("ra");
            miQueueSize.decrementAndGet();
        }

        return objMsg;
    }

    private T poolFirstSync() {
        synchronized (queueData) {
            try {
                return queueData.poll();

            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Current count of the elements in the queue.
     */
    public int getSize() {
        return miQueueSize.intValue();
    }

    /**
     * If there is no element in the queue.
     */
    public boolean isEmpty() {
        synchronized (mutex) {
            return queueData.isEmpty();
        }
    }

    public void notify2Closed() {
        synchronized (mutex) {
            mutex.notifyAll();
        }
    }

    public void setLock(boolean bLocked) {
        mbLocked = bLocked;
    }

    public boolean remove(T objMsg) {
        synchronized (mutex) {
            return queueData.remove(objMsg);
        }

    }

    public void clear() {
        synchronized (queueData) {
            queueData.clear();
            miQueueSize.set(0);
        }
    }
}
