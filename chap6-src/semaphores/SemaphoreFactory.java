/**
 * SemaphoreFactory.java
 *
 * This program uses a semaphore as a means of handling synchronization.
 * It creates 5 threads and a thread can perform its critical section
 * only if it is able to complete a acquire() operation on the sempahore.
 */

public class SemaphoreFactory
{
   public static void main(String args[]) {
      Semaphore sem = new Semaphore(1);

      Thread[] bees = new Thread[5];

      for (int i = 0; i < 5; i++)
         bees[i] = new Thread(new Worker(sem, "Worker " + (new Integer(i)).toString() ));

      for (int i = 0; i < 5; i++)
         bees[i].start();
   }
}

