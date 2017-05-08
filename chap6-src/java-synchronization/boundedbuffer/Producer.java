/**
 * This is the producer thread for the bounded buffer problem.
 */

import sleep.SleepUtilities;

import java.util.*;

public class Producer implements Runnable {
	private Buffer buffer;

	public Producer(Buffer b) {
		buffer = b;
	}

	public void run() {
		Date message;

		while (true) {
			System.out.println("Producer napping");
			SleepUtilities.nap();

			// produce an item & enter it into the buffer
			message = new Date();
			System.out.println("Producer produced " + message);

			buffer.insert(message);
		}
	}

}
