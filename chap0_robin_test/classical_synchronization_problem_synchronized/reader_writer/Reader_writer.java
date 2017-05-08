package classical_synchronization_problem_synchronized.reader_writer;

import sleep.SleepUtilities;

/**
 * Created by robin on 2017/5/2.
 */
public class Reader_writer {
    public static final int NUM_OF_READERS = 3;
    public static final int NUM_OF_WRITERS = 2;

    public static void main(String args[])
    {
        DBOperator server = new DBOperator();

        Thread[] readerArray = new Thread[NUM_OF_READERS];
        Thread[] writerArray = new Thread[NUM_OF_WRITERS];

        for (int i = 0; i < NUM_OF_READERS; i++) {
            readerArray[i] = new Thread(new Reader(i, server));
            readerArray[i].start();
        }

        for (int i = 0; i < NUM_OF_WRITERS; i++) {
            writerArray[i] = new Thread(new Writer(i, server));
            writerArray[i].start();
        }
    }
}

interface Operator
{
    public abstract void acquireReadLock(int readerNum);
    public abstract void acquireWriteLock(int writerNum);
    public abstract void releaseReadLock(int readerNum);
    public abstract void releaseWriteLock(int writerNum);
}

class DBOperator implements Operator
{
    // the number of active readers
    private int readerCount;

    // flag to indicate whether the database is in use
    private boolean dbWriting;

    public DBOperator()
    {
        readerCount = 0;
        dbWriting = false;
    }

    // reader will call this when they start reading
    public synchronized void acquireReadLock(int readerNum)
    {
        while (dbWriting == true)
        {
            try { wait(); }
            catch(InterruptedException e) { }
        }

        ++readerCount;

        System.out.println("Reader " + readerNum + " is reading. Reader count = " + readerCount);
    }

    // reader will call this when they finish reading
    public synchronized void releaseReadLock(int readerNum)
    {
        --readerCount;

        // if I am the last reader tell all others
        // that the database is no longer being read
        if (readerCount == 0)
            notify();

        System.out.println("Reader " + readerNum + " is done reading. Reader count = " + readerCount);
    }

    // writer will call this when they start writing
    public synchronized void acquireWriteLock(int writerNum) {
        while (readerCount > 0 || dbWriting == true) {
            try { wait(); }
            catch(InterruptedException e) {}
        }

        // once there are either no readers or writers
        // indicate that the database is being written
        dbWriting = true;

        System.out.println("writer " + writerNum + " is writing.");
    }

    // writer will call this when they start writing
    public synchronized void releaseWriteLock(int writerNum)
    {
        dbWriting = false;

        System.out.println("writer " + writerNum + " is done writing.");

        /**
         * This must be notifyAll()  as there may be more than
         * one waiting reader to read the database and we must
         * notify ALL of them.
         */
        notifyAll();
    }
}

class Reader implements Runnable
{

    private DBOperator db;
    private int       readerNum;

    public Reader(int readerNum, DBOperator db) {
        this.readerNum = readerNum;
        this.db = db;
    }

    public void run() {
        while (true) {
            SleepUtilities.nap();

            System.out.println("reader " + readerNum + " wants to read.");
            db.acquireReadLock(readerNum);

            // you have access to read from the database
            // let's read for awhile .....
            SleepUtilities.nap();

            db.releaseReadLock(readerNum);
        }
    }
    ;
}

class Writer implements Runnable
{
    private DBOperator server;
    private int       writerNum;

    public Writer(int w, DBOperator db) {
        writerNum = w;
        server = db;
    }

    public void run() {
        while (true)
        {
            SleepUtilities.nap();

            System.out.println("writer " + writerNum + " wants to write.");
            server.acquireWriteLock(writerNum);

            // you have access to write to the database
            // write for awhile ...
            SleepUtilities.nap();

            server.releaseWriteLock(writerNum);
        }
    }


}



