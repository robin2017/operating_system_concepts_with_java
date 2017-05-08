package one_producer_one_consumer.produce_consumer_sharedmemory_peterson_deadlock;

import sleep.SleepUtilities;

import java.util.Date;

public class Provider_consumer_sharedmemory_peterson_deadlock{
    public static void main(String[] args){
        //初始值随意，反正都会被修改的
        Integer permit=0;
        //初始值为false，表示两个进程都不想进入临界区
        Boolean want[]=new Boolean[2];
        want[0]=false;
        want[1]=false;

        Buffer server = new BoundedBuffer();

        // now create the producer and consumer threads
        Thread producerThread = new Thread(new Producer(server,permit,want));
        Thread consumerThread = new Thread(new Consumer(server,permit,want));

        producerThread.start();
        consumerThread.start();
    }
}

/*1、缓冲区，驻留在共享内存中的*/
interface Buffer
{
    public abstract void insert(Object item);
    public abstract Object remove();
}

/*2、缓冲区的实现，实现两个方法，为有限缓冲区，大小为3*/
class BoundedBuffer implements Buffer
{
    private static final int   BUFFER_SIZE = 3;
    private volatile int count;
    private int in;   // points to the next free position in the buffer
    private int out;  // points to the next full position in the buffer
    private Object[] buffer;
    public BoundedBuffer()
    {
        count = 0;
        in = 0;
        out = 0;
        buffer = new Object[BUFFER_SIZE];
    }
    public void insert(Object item) {
        while (count == BUFFER_SIZE) ; // do nothing
        ++count;
        buffer[in] = item;
        in = (in + 1) % BUFFER_SIZE;
        if (count == BUFFER_SIZE)
            System.out.println("Producer Entered " + item + " Buffer FULL");
        else
            System.out.println("Producer Entered " + item + " Buffer Size = " +  count);
    }
    public Object remove() {
        Object item;
        while (count == 0) ; // do nothing
        --count;
        item = buffer[out];
        out = (out + 1) % BUFFER_SIZE;
        if (count == 0)
            System.out.println("Consumer Consumed " + item + " Buffer EMPTY");
        else
            System.out.println("Consumer Consumed " + item + " Buffer Size = " + count);
        return item;
    }
}

/*3、生产者,进程为0*/
class Producer implements Runnable
{
    //缓冲区是生产者、消费者共享的区域，是他们的成员变量
    private Integer permit;
    private Boolean want[];
    private Buffer buffer;
    public Producer(Buffer b,Integer p,Boolean w[]) {
        buffer = b;
        permit=p;
        want=w;
    }

    public void run()
    {
        Date message;

        while (true) {
            System.out.println("Producer napping");
            SleepUtilities.nap();

            // produce an item & enter it into the buffer
            message = new Date();
            System.out.println("Producer produced " + message);


            System.out.println("producer-->1");
            //这是进入区
            want[0]=true;
            permit=1;
            while(want[1]&&permit==1);
            System.out.println("producer-->2");
            //这是临界区
            buffer.insert(message);
            System.out.println("producer-->3");
            //这是退出区
            want[0]=false;
            System.out.println("producer-->4");
        }
    }

}

/*4、消费者，进程为1*/
class Consumer implements Runnable
{
    //缓冲区是生产者、消费者共享的区域，是他们的成员变量
    private Integer permit;
    private Boolean want[];
    private Buffer buffer;
    public Consumer(Buffer b,Integer p,Boolean w[]) {
        buffer = b;
        permit=p;
        want=w;
    }

    public void run()
    {
        Date message;

        while (true)
        {
            System.out.println("Consumer napping");
            SleepUtilities.nap();

            // consume an item from the buffer
            System.out.println("Consumer wants to consume.");
            System.out.println("consumer-->1");
            //这是进入区
            want[1]=true;
            permit=0;
            while(want[0]&&permit==0);
            System.out.println("consumer-->2");
            //这是临界区
            message = (Date)buffer.remove();
            System.out.println("consumer-->3");
            //这是退出区
            want[1]=false;
            System.out.println("consumer-->4");
        }
    }

}
