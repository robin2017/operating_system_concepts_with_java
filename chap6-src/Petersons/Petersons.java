/**
 * Petersons.java
 *
 * This program implements strict alternation as a means of handling synchronization.
 *
 * Note - Using an array for the two flag variables would be preferable, however  we must
 * declare the data as volatile and volatile does not extend to arrays.
 */


 public class Petersons implements MutualExclusion
 {
   private volatile int turn;
   private volatile boolean flag0;
   private volatile boolean flag1;

   public Petersons() {
	flag0 = false;
	flag1 = false;

	turn = 0;
   }

   public void entrySection(int t) {
		int other = 1 - t;

		if (t == 0) {
			turn = other;
			flag0 = true;
			while ( (flag1 == true) && (turn == other) )
				Thread.yield();
		}
		else {
			turn = other;
			flag1 = true;
			while ( (flag0 == true) && (turn == other) )
				Thread.yield();
		}
   }

   public void exitSection(int t) {
		if(t == 0)
			flag0 = false;
		else
			flag1 = false;
   }
}
