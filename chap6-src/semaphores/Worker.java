/**
 * Worker.java
 * 
 * This thread is used to demonstrate the operation of a semaphore.
 * 
 */

public class Worker implements Runnable {

	private Semaphore sem;

	private String name;

	public Worker(Semaphore sem, String name) {
		this.name = name;
		this.sem = sem;
	}

	public void run() {
		while (true) {
			sem.acquire();
			MutualExclusionUtilities.criticalSection(name);
			sem.release();
			MutualExclusionUtilities.nonCriticalSection(name);
		}
	}

}
