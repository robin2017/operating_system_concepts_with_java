package classical_synchronization_problem_semaphore._unconsumption_reader_writer;

import sleep.SleepUtilities;

import java.util.concurrent.Semaphore;

/**
 * Created by robin on 2017/4/27.
 */
public class Reader_writer {
    public static void main(String[] args){
        Thread[] readers=new Thread[5];
        Thread[] writers=new Thread[3];
        Operator op=new DBOperator();
        for(int i=0;i<5;i++)
            readers[i]=new Thread(new Reader(op,"reader"+i));
        for(int i=0;i<3;i++)
            writers[i]=new Thread(new Writer(op,"writer"+i));

        for(int i=0;i<3;i++)
            writers[i].start();
       // SleepUtilities.nap();
        for(int i=0;i<5;i++)
            readers[i].start();

    }
}

interface Operator{
    public abstract void startRead() throws InterruptedException;
    public abstract void endRead() throws InterruptedException;
    public abstract void startWrite() throws InterruptedException;
    public abstract void endWrite();
}

class DBOperator implements Operator{
    private Semaphore db;
    private Semaphore mutex;
    private int readerNumber;
    public DBOperator(){
        db=new Semaphore(1);
        mutex=new Semaphore(1);
        readerNumber=0;
    }

    @Override
    public void startRead() throws InterruptedException {
        mutex.acquire();

        if(readerNumber==0)
            db.acquire();
        System.out.println("startReader");
        readerNumber++;
        mutex.release();
    }

    @Override
    public void endRead() throws InterruptedException {
        mutex.acquire();
        System.out.println("endReader");
        readerNumber--;
        if(readerNumber==0)
            db.release();
        mutex.release();
    }

    @Override
    public void startWrite() throws InterruptedException {
        db.acquire();
        System.out.println("startWrite");
    }

    @Override
    public void endWrite() {
        System.out.println("endWrite");
        db.release();
    }
}
class Reader implements Runnable{
    private Operator op;
    private String name;
    public Reader(Operator operator,String str){
        this.op=operator;
        this.name=str;
    }
    @Override
    public void run() {
        try {
            op.startRead();
            SleepUtilities.nap(1);
            op.endRead();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class Writer implements Runnable{
    private Operator op;
    private String name;
    public Writer(Operator operator,String str){
        this.op=operator;
        this.name=str;
    }
    @Override
    public void run() {
        try {
            op.startWrite();
            SleepUtilities.nap(3);
            op.endWrite();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}