
class MutableInteger
{
	private int value;

	public int get() {
		return value;
	}

	public void set(int sum) {
		this.value = sum;
	}
}

class Summation implements Runnable
{
	private int upper;
	private MutableInteger sumValue;

	public Summation(int upper, MutableInteger sumValue) {
		if (upper < 0)
			throw new IllegalArgumentException();

		this.upper = upper;
		this.sumValue = sumValue;
	}

	public void run() {
		int sum = 0;

		for (int i = 0; i <= upper; i++)
			sum += i;

		sumValue.set(sum);
	}
}

public class Driver
{
	public static void main(String[] args1) {
		String[] args={"3"};
		if (args.length != 1) {
			System.err.println("Usage Driver <integer>");
			System.exit(0);
		}

		MutableInteger sumObject = new MutableInteger();
		int upper = Integer.parseInt(args[0]);
		
		Thread worker = new Thread(new Summation(upper, sumObject));
		worker.start();
		try {
			worker.join();
		} catch (InterruptedException ie) { }
		System.out.println("The value of " + upper + " is " + sumObject.get());
	}
}
