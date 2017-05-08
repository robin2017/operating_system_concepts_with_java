package classical_synchronization_problem_semaphore._unconsumption_computer_print;

import sleep.SleepUtilities;

import java.util.concurrent.Semaphore;

/**
 * Created by robin on 2017/4/27.
 */
public class Computer_print {
    public static void main(String[] args){


        Thread[] threads=new Thread[3];
        Semaphore semaphore=new Semaphore(1);//只有一台打印机
        for(int i=0;i<3;i++){
            threads[i]=new Thread(new Worker(semaphore,"Worker"+i));
        }
        for(int i=0;i<3;i++)
            threads[i].start();
    }
}

class Worker implements Runnable{
    private Semaphore sem;
    private String name;
    public Worker(Semaphore semaphore,String str){
        this.sem=semaphore;
        this.name=str;
    }
    @Override
    public void run() {
        for(int i=0;i<10;i++){
            //先计算
            compute(name);

            //后打印
            try {
                sem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            print(name);
            sem.release();
        }
    }

    private void compute(String name){
        System.out.println(name + "--->start ---> compute");
        SleepUtilities.nap(2);
        System.out.println(name + "--->end   ---> compute");

    }
    private void print(String name){
        System.out.println(name + "--->start --->print");
        SleepUtilities.nap(2);
        System.out.println(name + "--->end   --->print");
    }

}
