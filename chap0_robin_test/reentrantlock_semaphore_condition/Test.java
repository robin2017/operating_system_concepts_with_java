package reentrantlock_semaphore_condition;

import java.util.concurrent.locks.Lock;

/**
 * Created by robin on 2017/5/3.
 */
public class Test {
}

class Owner implements Runnable{
    private Lock Ownee;

    @Override
    public void run() {

    }
    int count=0;
    int SIZE=0;

    public synchronized void method(){
        while(count==SIZE){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
