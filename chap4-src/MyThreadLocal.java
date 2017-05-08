import sleep.SleepUtilities;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by robin on 2017/1/20.
 */
public class MyThreadLocal {
    public static void main(String[] args){
        test4();
    }
    public static void test1(){//这样的线程，数据共享了
        Thread th=new Thread(new MyTask());
        Thread th1=new Thread(new MyTask());
        th.start();
        th1.start();
    }
    public static void test2(){//这样的线程不能批处理
        Thread th=new MyThread();
        Thread th1=new MyThread();
        th.start();
        th1.start();
    }

    public static void test3(){//这样的线程可以批处理，放入线程池
        Thread th=new Thread(new MyTask_ThreadLocal());
        Thread th1=new Thread(new MyTask_ThreadLocal());
        th.start();
        th1.start();
    }
    public static void test4(){ //批处理
        ExecutorService pool= Executors.newCachedThreadPool();
        for(int i=0;i<3;i++)
            pool.execute(new MyTask_ThreadLocal());
    }

}
class MyTask implements Runnable{
    public static Integer id;
    @Override
    public void run() {
        id=(int) (Math.random() * 9);
        SleepUtilities.nap(3);
        System.out.println(Thread.currentThread().getName() + "   :   " + id);
    }
}

class MyTask_ThreadLocal implements Runnable{
    public static ThreadLocal<Integer> id=new ThreadLocal<Integer>();
    @Override
    public void run() {
        id.set((int) (Math.random() * 9));
        SleepUtilities.nap(3);
        System.out.println(Thread.currentThread().getName()+"   :   "+id.get());
    }
}

class MyThread extends Thread{
    private Integer id;
    @Override
    public void run() {
        id=(int) (Math.random() * 9);
        SleepUtilities.nap(3);
        System.out.println(Thread.currentThread().getName()+"   :   "+id);
    }
}
