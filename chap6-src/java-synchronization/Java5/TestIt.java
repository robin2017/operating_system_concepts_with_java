/**
 * TestIt.java
 *
 */

public class TestIt
{
   public static void main(String args[]) {
      DoWork pile = new DoWork();

      Thread[] bees = new Thread[5];

      for (int i = 0; i < 5; i++)
         bees[i] = new Thread(new Worker(pile, "Worker " + (new Integer(i)).toString(), i) );

      for (int i = 0; i < 5; i++)
         bees[i].start();
   }
}

