import java.util.concurrent.*;

public class TPExample
{

	public static void main(String[] args) {
		ExecutorService pool = java.util.concurrent.Executors.newCachedThreadPool();

		for (int i = 0; i < 5; i++)
			// just for kicks, use a thread pool
			pool.execute(new Task());
		
		// sleep for 5 seconds
		try { Thread.sleep(5000); } catch (InterruptedException ie) { }

		pool.shutdown();
	}
}
