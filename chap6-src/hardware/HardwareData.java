/**
 * HardwareData.java
 *
 * Generic value structure that is used by the test-and-set and swap
 * instructions for hardware solutions of the critical section problem.
 *
 * Note these solutions are NOT thread-safe and are intended entirely
 * for purposes of demonstrating the get-and-set functionality provided
 * by certain architectures.
 *
 * Thread safety would require these operations perform atomically.
 *
 */


public class HardwareData
{
	private boolean value;
	
	public HardwareData(boolean data) {
		this.value = data;
	}

	public boolean get() {
		return value;
	}

	public void set(boolean data) {
		this.value = data;
	}

	public boolean getAndSet(boolean data) {
		boolean oldValue = this.value;
		this.value = data;

		return oldValue;
	}

	/**
	 * swap other with this.
	 */
	public void swap(HardwareData other) {
		boolean temp = this.get();

		this.set(other.get());
		other.set(temp);
	}
}
