package classical_synchronization_problem_synchronized.producer_consumer;

import sleep.SleepUtilities;

import java.util.Date;

/**
 * Created by robin on 2017/5/2.
 */
public class Producer_consumer {
    public static void main(String args[]) {
        Buffer server = new BoundedBuffer();

        Thread producerThread = new Thread(new Producer(server));
        Thread consumerThread = new Thread(new Consumer(server));

        producerThread.start();
        consumerThread.start();
    }
}


interface Buffer
{
    public abstract void insert(Object item);
    public abstract Object remove();
}
class BoundedBuffer implements Buffer {
    private static final int BUFFER_SIZE = 5;
    private int count; // number of items in the buffer
    private int in; // points to the next free position in the buffer
    private int out; // points to the next full position in the buffer
    private Object[] buffer;
    public BoundedBuffer() {
        count = 0;in = 0;out = 0;
        buffer = new Object[BUFFER_SIZE];
    }
    public synchronized void insert(Object item) {
        while (count == BUFFER_SIZE) {
            try {wait();} catch (InterruptedException e) {
            }
        }
        ++count;
        buffer[in] = item;
        in = (in + 1) % BUFFER_SIZE;
        if (count == BUFFER_SIZE)
            System.out.println("Producer Entered " + item + " Buffer FULL");
        else
            System.out.println("Producer Entered " + item + " Buffer Size = " + count);
        notify();
    }
    public synchronized Object remove() {
        Object item;

        while (count == 0) {
            try {wait();} catch (InterruptedException e) {
            }
        }
        --count;
        item = buffer[out];
        out = (out + 1) % BUFFER_SIZE;
        if (count == 0)
            System.out.println("Consumer Consumed " + item + " Buffer EMPTY");
        else
            System.out.println("Consumer Consumed " + item + " Buffer Size = " + count);
        notify();
        return item;
    }
}
class Consumer implements Runnable {
    private Buffer buffer;
    public Consumer(Buffer b) {
        buffer = b;
    }
    public void run() {
        Date message;
        while (true) {
            System.out.println("Consumer napping");
            SleepUtilities.nap();
            // consume an item from the buffer
            System.out.println("Consumer wants to consume.");
            message = (Date) buffer.remove();
        }
    }
}

class Producer implements Runnable {
    private Buffer buffer;
    public Producer(Buffer b) {
        buffer = b;
    }
    public void run() {
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
