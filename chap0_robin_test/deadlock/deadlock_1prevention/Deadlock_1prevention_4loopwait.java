package deadlock.deadlock_1prevention;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by robin on 2017/5/3.
 */

public class Deadlock_1prevention_4loopwait
{
    public static void main(String arg[]) {
        MyReentrantLock lockX = new MyReentrantLock(1);
        MyReentrantLock lockY = new MyReentrantLock(2);

        Thread threadA = new Thread(new A(lockX,lockY));
        Thread threadB = new Thread(new B(lockX,lockY));

        threadA.start();
        threadB.start();
    }
}

class MyReentrantLock extends ReentrantLock{
    private int No;
    public MyReentrantLock(int n){
        this.No=n;
    }
    public int getNo() {
        return No;
    }
}

class A implements Runnable
{
    private MyReentrantLock first, second;

    public A(MyReentrantLock first, MyReentrantLock second) {
        this.first = first;
        this.second = second;
    }

    public void run() {
        try {
            first.lock();
            System.out.println("Thread A got first lock.");
            // do something

            try {
                Thread.sleep( ((int)(3*Math.random()))*1000);
            }
            catch (InterruptedException e) {}

            if(first.getNo()>second.getNo()){
                first.unlock();
                System.out.println("A释放大号的资源");
            }
            second.lock();
            System.out.println("Thread A got second lock.");
            // do something

        }
        finally {
            first.unlock();
            second.unlock();
        }
    }
}

class B implements Runnable
{
    private MyReentrantLock first, second;

    public B(MyReentrantLock first, MyReentrantLock second) {
        this.first = first;
        this.second = second;
    }

    public void run() {
        try {
            second.lock();
            System.out.println("Thread B got second lock.");
            // do something

            try {
                Thread.sleep( ((int)(3*Math.random()))*1000);
            }
            catch (InterruptedException e) {}


            if(second.getNo()>first.getNo()){
                second.unlock();
                System.out.println("B释放大号的资源");
            }

            first.lock();
            System.out.println("Thread B got first lock.");
            // do something

        }
        finally {
            if(second.isLocked())
                second.unlock();
            first.unlock();
        }
    }
}





