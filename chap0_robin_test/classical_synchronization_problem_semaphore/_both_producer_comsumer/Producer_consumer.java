package classical_synchronization_problem_semaphore._both_producer_comsumer;

import sleep.SleepUtilities;

import java.util.Date;
import java.util.concurrent.Semaphore;

/**
 * Created by robin on 2017/4/27.
 */
public class Producer_consumer {
    public static void main(String[] args){
        Buffer buffer=new BoundedBuffer();

        Thread prodecer=new Thread(new Producer(buffer));
        Thread consumer=new Thread(new Consumer(buffer));
        prodecer.start();
        consumer.start();
    }
}

interface Buffer
{
    public abstract void insert(Object item) throws InterruptedException;
    public abstract Object remove() throws InterruptedException;
}

class BoundedBuffer implements Buffer{
    private static final int BUFFER_SIZE=5;
    private Object[] buffer;
    private int in,out;
    private Semaphore sem_buffer; //不可消耗
    private Semaphore sem_empty;  //可消耗，生产者拥有
    private Semaphore sem_product; //可消耗，消费者拥有
    public BoundedBuffer(){
        in=0;
        out=0;
        buffer=new Object[BUFFER_SIZE];
        sem_buffer=new Semaphore(1);
        sem_empty=new Semaphore(BUFFER_SIZE);
        sem_product=new Semaphore(0);
    }
    public void insert(Object item) throws InterruptedException {
        //进入区
        sem_empty.acquire(); //生产者的一个资源被占用了，少了空位
        sem_buffer.acquire();
        //插入动作，临界区
        buffer[in]=item;
        in=(in+1)%BUFFER_SIZE;
        System.out.println("insert"+(in-out+BUFFER_SIZE)%BUFFER_SIZE);
        //退出区
        sem_buffer.release();
        sem_product.release(); //消费者的一个资源释放了，多了产品
    }
    public Object remove() throws InterruptedException {
        //进入区
        sem_product.acquire();
        sem_buffer.acquire();
        //临界区
        Object item=buffer[out];
        out=(out+1)%BUFFER_SIZE;
        System.out.println("remove"+(in-out+BUFFER_SIZE)%BUFFER_SIZE);
        //退出区
        sem_buffer.release();
        sem_empty.release();
        return item;
    }
}

class Producer implements Runnable{
    private Buffer buffer;
    public Producer(Buffer bu){
        this.buffer=bu;
    }
    @Override
    public void run() {
        for(int i=0;i<10;i++){
            SleepUtilities.nap();
            try {
                buffer.insert(new Date());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable{
    private Buffer buffer;
    public Consumer(Buffer bu){
        this.buffer=bu;
    }

    @Override
    public void run() {
        for(int i=0;i<10;i++){
            SleepUtilities.nap(1);
            try {
                buffer.remove();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}