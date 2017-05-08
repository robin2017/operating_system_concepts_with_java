import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by robin on 2017/1/25.
 */
public class MatrixMulti {

    final static int ROW=3;
    final static int COL=3;
    //NUM代表了线程的数量，如果太大，可能线程之间也需要相互等待，不能完全一起执行。
    final static int NUM=ROW*COL;
    final static int RC=2;

    final static int[][] a={{1,4},{2,5},{3,6}};
    final static int[][] b={{8,7,6},{5,4,3}};
    static int[][] c=new int[3][3];
    static int k=0;
    //主线程为main线程，子线程为自定义的线程类对象，可以组成一个数组
    static Thread[] workers=new Thread[NUM];
    public static void main(String[] args){
        test2();

        print(c,3,3);
    }

    //如果矩阵很大，建立的线程数太大，会耗费太多资源的
    public static void test1(){
        for(int i=0;i<ROW;i++)
            for(int j=0;j<COL;j++) {
                workers[k] = new Thread(new WorkerThread(i, j, a, b, c));
                workers[k++].start();
            }
        for(int i=0;i<NUM;i++){
            try{
                workers[i].join();
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }
    }
    //这里使用java的线程池技术，建立的线程数量==需要的数量，这个实际中可能不符合
    //这里没有设置等待吗？？

    //如果线程池的数量少于MN,则可以看成生产者消费者问题，可以将线程的处理能力和产生能力分开，好
    public static void test2(){
        ExecutorService service= Executors.newFixedThreadPool(4);
        long start,end;
        start=System.nanoTime();
        for(int i=0;i<ROW;i++)
            for(int j=0;j<COL;j++)
                service.execute(new WorkerThread(i, j, a, b, c));
        end=System.nanoTime();
        System.out.println(end-start);
        //while(service.isTerminated());

    }
    public static void test3(){
        for(int i=0;i<ROW;i++)
            for(int j=0;i<COL;j++)
                for(int k=0;k<RC;k++)
                    c[i][j]+=a[i][k]+b[k][j];
    }

    private static void print(int[][] c,int a,int b) {
        for(int i=0;i<a;i++) {
            for (int j = 0; j < b; j++)
                System.out.print(c[i][j]+" ");
            System.out.println();
        }
    }
}
class WorkerThread implements Runnable{
    private int row;
    private int col;
    private int[][] a;
    private int[][] b;
    private int[][] c;

    public WorkerThread(int row,int col,int[][] a,int[][] b,int[][] c) {
        this.col=col;
        this.row=row;
        this.a=a;
        this.b=b;
        this.c=c;
    }
    public void run(){
        for(int i=0;i<MatrixMulti.RC;i++)
            c[row][col]+=a[row][i]*b[i][col];
    }
}
