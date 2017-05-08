

public class InterruptibleThread implements Runnable 
{
	/**
	 * This thread will continue to run as long
	 * as it is not interrupted.
	 */
	public void run() {
		while (true) {
			/**
			 * do some work for awhile
			 */

			if (Thread.currentThread().isInterrupted()) {
				System.out.println("I'm interrupted!");
				break;
			}
		}
		// clean up and terminate
	}
}
