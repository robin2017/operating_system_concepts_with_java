package one_producer_one_consumer.produce_consumer_messagepassing;

import sleep.SleepUtilities;

import java.util.Date;
import java.util.Vector;

/**
 * Created by robin on 2017/4/25.
 */
public class Provider_consumer_messagepassing {
    public static void main(String[] args){
        // first create the message buffer
        Queue mailBox = new MessageQueue();

        // now create the producer and consumer threads
        Thread producerThread = new Thread(new Producer(mailBox));
        Thread consumerThread = new Thread(new Consumer(mailBox));

        producerThread.start();
        consumerThread.start();
    }
}

/*1、队列，即为缓冲区*/
 interface Queue
{
    public  abstract void send(Object message);
    public  abstract Object receive();
}

/*2、队列的实现，为无限缓冲区，vector实现*/
 class MessageQueue implements Queue
{
    private Vector queue;

    public MessageQueue() {
        queue = new Vector();
    }
    public void send(Object item) {
        queue.addElement(item);
    }

   /*
    * This implements a non-blocking receive
    */

    public Object receive() {
        if (queue.size() == 0)
            return null;
        else
            return queue.remove(0);
    }
}

/*3、生产者*/
class Producer implements Runnable
{
    private  Queue mbox;
    public Producer(Queue m)
    {
        mbox = m;
    }

    public void run()
    {
        Date message;

        while (true) {

            SleepUtilities.nap();
            message = new Date();
            System.out.println("Producer produced " + message);
            // produce an item & enter it into the buffer
            mbox.send(message);
        }
    }
}

/*4、消费者*/
class Consumer implements Runnable
{
    public Consumer(Queue m) {
        mbox = m;
    }

    public void run() {
        Date message;

        while (true)
        {
            SleepUtilities.nap();

            // consume an item from the buffer
            System.out.println("Consumer wants to consume.");
            message = (Date)mbox.receive();
            if (message != null)
                System.out.println("Consumer consumed " + message);
        }
    }

    private  Queue mbox;
}


