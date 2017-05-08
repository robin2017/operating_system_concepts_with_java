package classical_synchronization_problem_semaphore._unconsumption_philosopher_eat;

import sleep.SleepUtilities;

import java.util.concurrent.Semaphore;

/**
 * Created by robin on 2017/4/27.
 */
public class Philosopher_eat {
    public static final int SIZE=5;
    public static void main(String[] args){
        Chop[] chops=new Chop[SIZE];

        Thread[] philosophers=new Thread[5];

        for(int i=0;i<SIZE;i++)
            chops[i]=new Chop();
        for(int i=0;i<SIZE;i++){
            philosophers[i]=new Thread(new Philosopher(i,chops));
        }
        for(int i=0;i<SIZE;i++)
            philosophers[i].start();
    }
}

class Chop{
    private Semaphore sem;
    public Chop(){
        sem=new Semaphore(1);
    }
    public void takeChop() throws InterruptedException {
        sem.acquire();
    }

    public void dropChop(){
        sem.release();
    }
}

class Philosopher implements Runnable{
    private static final int SIZE=Philosopher_eat.SIZE;
    private int number;
    private Chop lchop,rchop;
    Philosopher(int number,Chop[] chops){
        this.number=number;
        lchop=chops[number];
        rchop=chops[(number+1)%SIZE];
    }

    @Override
    public void run() {
        if(number%2==0){
            try {
                lchop.takeChop();
                rchop.takeChop();
                eat(number);
                rchop.dropChop();
                lchop.dropChop();


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            try {
                rchop.takeChop();
                lchop.takeChop();
                eat(number);
                lchop.dropChop();
                rchop.dropChop();


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
    private void eat(int number){
        System.out.println(number+" start eat");
        SleepUtilities.nap(3);
        System.out.println(number + " end eat");
    }
}
