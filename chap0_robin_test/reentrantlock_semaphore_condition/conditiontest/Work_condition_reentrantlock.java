package reentrantlock_semaphore_condition.conditiontest;

import sleep.SleepUtilities;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by robin on 2017/5/3.
 */
public class Work_condition_reentrantlock
{
    public static void main(String args[]) {
        Working1 pile = new Working1();

        Thread[] bees = new Thread[5];

        for (int i = 0; i < 5; i++)
            bees[i] = new Thread(new Worker1(pile, "Worker1 " + (new Integer(i)).toString(), i) );

        for (int i = 0; i < 5; i++)
            bees[i].start();
    }
}
class Worker1 implements Runnable {
    private Working1 pile;

    private String name;

    private int num;

    public Worker1(Working1 p, String n, int i) {
        pile = p;
        name = n;
        num = i;
    }

    public void run() {
        for(int i=0;i<3;i++){
            SleepUtilities.nap();
            pile.Working1(num);
        }
    }

}
class Working1
{
    private int turn;
    private Lock lock;
    private Condition[] condVars;
    public Working1() {
        turn = 0;
        lock = new ReentrantLock();
        condVars = new Condition[5];
        for (int i = 0; i < 5; i++)
            condVars[i] = lock.newCondition();
    }
    // myNumber is the number of the thread that wishes to do some work
    public void Working1(int myNumber) {
        //lock.lock();
        try {
            // if it's not my turn, then wait
            // until I'm signaled
            if (myNumber != turn)
                condVars[myNumber].await();
            // do some work for awhile
            System.out.println("Worker1 " + myNumber + " will do some work");
            SleepUtilities.nap();
            // now signal to the next waiting thread
            turn = (turn + 1) % 5;
            condVars[turn].signal();
        }
        catch (InterruptedException ie) { }
        finally {
            lock.unlock();
        }
    }
}
