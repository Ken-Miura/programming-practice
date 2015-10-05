package ch03.ex03_05;

public class LoopOverheadBenchmark extends Benchmark {

	@Override
	void benchmark() {
		// Do nothing
	}

	public final long loopOverhead(int count) {
		long start = System.nanoTime();
		for (int i = 0; i < count; i++)
			;
		return (System.nanoTime() - start);
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Please input loop count as a argument.");
			return;
		}

		int count = 0;
		try {
			count = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.err.println("Please input number as a argument.");
			return;
		}
		long time = new LoopOverheadBenchmark().loopOverhead(count);
		System.out.println("loop count: " + count + ", loop overhead: " + time
				+ " nanoseconds");
	}
}
