package reentrantlock_semaphore_condition.reentrantlocktest;

import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest2 {
    private static Lock fairLock = new ReentrantLock2(true);
    private static Lock unfairLock = new ReentrantLock2(false);

    public static void main(String[] args) {
        new ReentrantLockTest2().fair();
    }

    public void fair() {
        System.out.println("fair version");
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Job(fairLock)) {
                public String toString() {
                    return getName();
                }
            };
            thread.setName("" + i);
            thread.start();
        }
        // sleep 5000ms
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void unfair() {
        System.out.println("unfair version");
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Job(unfairLock)) {
                public String toString() {
                    return getName();
                }
            };
            thread.setName("" + i);
            thread.start();
        }
        // sleep 5000ms
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

class Job implements Runnable {
    private Lock lock;

    public Job(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            lock.lock();
            try {
                System.out.println("Lock by:"
                        + Thread.currentThread().getName() + " and "
                        + ((ReentrantLock2) lock).getQueuedThreads()
                        + " waits.");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlock();
            }
        }
    }
}

class ReentrantLock2 extends ReentrantLock {
    // Constructor Override

    private static final long serialVersionUID = 1773716895097002072L;

    public ReentrantLock2(boolean b) {
        super(b);
    }

    public Collection<Thread> getQueuedThreads() {
        return super.getQueuedThreads();
    }
}