/**
 * DoWork.java
 *
 * This method is used to distinguish between the notify()
 * and notifyAll() methods. Run the program using notify().
 * The program will shortly hang as the incorrect thread
 * (i.e. the thread whose turn is not next!) receives the notification.
 * After that, change the call to notify() to notifyAll(). The
 * program should run correctly using notifyAll().
 *
 * Figure 7.32
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Sixth Edition
 * Copyright John Wiley & Sons - 2003.
 */

import sleep.SleepUtilities;

import java.util.concurrent.locks.*;

public class DoWork
{
	private int turn;
	private Lock lock;
	private Condition[] condVars;

	public DoWork() {
		turn = 0;
		lock = new ReentrantLock();
		condVars = new Condition[5];

		for (int i = 0; i < 5; i++)
			condVars[i] = lock.newCondition();
	}


	// myNumber is the number of the thread that wishes to do some work
	public void doWork(int myNumber) {
		lock.lock();

		try {
			// if it's not my turn, then wait 
			// until I'm signaled
			if (myNumber != turn)
				condVars[myNumber].await();

			// do some work for awhile
			System.out.println("Worker " + myNumber + " will do some work");
			SleepUtilities.nap();

			// now signal to the next waiting thread
			turn = (turn + 1) % 5;
			condVars[turn].signal();
		}
		catch (InterruptedException ie) { }
		finally {
			lock.unlock();
		}
	}

}
