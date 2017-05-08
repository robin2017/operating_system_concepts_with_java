package classical_synchronization_problem_synchronized;

/**
 * Created by robin on 2017/5/2.
 */
public class Test {

    public static void main(String[] args){
        Ownee ownee=new Ownee();
        Thread thread=new Thread(new Owner(ownee));
        thread.start();
    }
}
//拥有者  线程
class Owner implements Runnable{
    private Ownee ownee;
    public Owner(Ownee o){
         this.ownee=o;
    }
    @Override
    public void run() {
        ownee.method1();
    }
}
//被拥有者
class Ownee{
    public synchronized void method1(){
        System.out.println("method1");
        method1();
    }
    public synchronized void method2(){
        System.out.println("method2");
    }
}
//
//
//class Buffer{
//
//    private Object[] buffer;
//    private Object mutexLock=new Object();
//    public synchronized void insert1(Object obj){
//        dothing();
//    }
//
//    public void insert2(Object obj){
//        dootherthing();
//        synchronized (mutexLock) {
//            docriticalthing();
//        }
//    }
//
//    private void dootherthing() {}
//    private void docriticalthing() {}
//    private void dothing() {}
//}