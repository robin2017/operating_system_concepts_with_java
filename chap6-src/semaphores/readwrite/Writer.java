/**
 * Writer.java
 *
 * A writer to the database.
 *
 */
public class Writer implements Runnable
{
   private RWLock server;
   private int       writerNum;

   public Writer(int w, RWLock db) {
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
