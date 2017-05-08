package reentrantlock_semaphore_condition.reentrantlocktest;

/**
 * Created by robin on 2017/5/3.
 */
public class ReentrantLockTest3 {
    public static void main(String[] args) {
        new ReentrantLockTest3().unfair();
    }


    private static Object unfairLock = new Object();

    public void unfair() {
        System.out.println("unfair version");
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new MyJob(unfairLock)) {
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

class MyJob implements Runnable {
    private Object lock;

    public MyJob(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            synchronized (lock) {
                System.out.println("Lock by:"
                        + Thread.currentThread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
