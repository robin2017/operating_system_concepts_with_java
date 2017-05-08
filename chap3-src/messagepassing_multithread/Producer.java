
import sleep.SleepUtilities;

import java.util.*;

class Producer implements Runnable
{
   public Producer(Channel m)
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
   
   private  Channel mbox;
}
