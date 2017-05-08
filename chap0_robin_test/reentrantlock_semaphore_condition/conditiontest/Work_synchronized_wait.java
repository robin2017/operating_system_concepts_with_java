package reentrantlock_semaphore_condition.conditiontest;

import sleep.SleepUtilities;

/**
 * Created by robin on 2017/5/3.
 */
public class Work_synchronized_wait
    {
        public static void main(String args[]) {
            Working2 pile = new Working2();

        Thread[] bees = new Thread[5];

        for (int i = 1; i <= 5; i++)
            bees[i-1] = new Thread(new Worker2(pile, "Worker " + (new Integer(i)).toString(), i) );

        for (int i = 1; i <= 5; i++)
            bees[i-1].start();
    }
    }


class Worker2 implements Runnable {
    private Working2 pile;

    private String name;

    private int num;

    public Worker2(Working2 p, String n, int i) {
        pile = p;
        name = n;
        num = i;
    }

    public void run() {
        for(int i=0;i<3;i++){
            SleepUtilities.nap();
            pile.doWork(num);
        }
    }

}


class Working2 {
    private int turn = 1;
    // myNumber is the number of the thread that wishes to do some work
    public synchronized void doWork(int myNumber) {
        while (turn != myNumber) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        // do some work for awhile
        System.out.println("Worker " + myNumber + " will do some work");
        SleepUtilities.nap();
        // ok, we're finished. Now indicate to the next waiting
        // thread that it is their turn to do some work.
        System.out.println("Worker " + myNumber + " is done working");
        if (turn < 5)
            ++turn;
        else
            turn = 1;
        // change this to notifyAll() to see it run correctly!
        notify();
    }
}
