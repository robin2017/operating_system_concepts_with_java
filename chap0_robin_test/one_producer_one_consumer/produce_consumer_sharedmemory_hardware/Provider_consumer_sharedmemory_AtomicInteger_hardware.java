package one_producer_one_consumer.produce_consumer_sharedmemory_hardware;

import sleep.SleepUtilities;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Provider_consumer_sharedmemory_AtomicInteger_hardware{
    public static void main(String[] args){

        Buffer server = new BoundedBuffer();

        // now create the producer and consumer threads
        Thread producerThread = new Thread(new Producer(server));
        Thread consumerThread = new Thread(new Consumer(server));

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
    private volatile AtomicInteger count;

    private int in;   // points to the next free position in the buffer
    private int out;  // points to the next full position in the buffer
    private Object[] buffer;

    public BoundedBuffer()
    {
        // buffer is initially empty
        count = new AtomicInteger(0);
        in = 0;
        out = 0;
        buffer = new Object[BUFFER_SIZE];
    }

    // producer calls this method
    public void insert(Object item) {
        while (count.get() == BUFFER_SIZE)
            ; // do nothing

        // add an item to the buffer
        count.getAndIncrement();
        buffer[in] = item;
        in = (in + 1) % BUFFER_SIZE;

        if (count.get() == BUFFER_SIZE)
            System.out.println("Producer Entered " + item + " Buffer FULL");
        else
            System.out.println("Producer Entered " + item + " Buffer Size = " +  count);
    }

    // consumer calls this method
    public Object remove() {
        Object item;

        while (count.get() == 0)
            ; // do nothing

        // remove an item from the buffer
        count.getAndDecrement();
        item = buffer[out];
        out = (out + 1) % BUFFER_SIZE;

        if (count.get() == 0)
            System.out.println("Consumer Consumed " + item + " Buffer EMPTY");
        else
            System.out.println("Consumer Consumed " + item + " Buffer Size = " + count);

        return item;
    }

}

/*3、生产者*/
class Producer implements Runnable
{
    //缓冲区是生产者、消费者共享的区域，是他们的成员变量
    private Buffer buffer;
    public Producer(Buffer b) {
        buffer = b;
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

            buffer.insert(message);
        }
    }

}

/*4、消费者*/
class Consumer implements Runnable
{
    public Consumer(Buffer b) {
        buffer = b;
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

            message = (Date)buffer.remove();
        }
    }
    //缓冲区是生产者、消费者共享的区域，是他们的成员变量
    private Buffer buffer;
}
