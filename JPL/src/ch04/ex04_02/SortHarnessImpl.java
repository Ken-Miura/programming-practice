package ch04.ex04_02;

import java.util.Comparator;
import java.util.Objects;

abstract class SortHarnessImpl<T> implements SortHarness <T> {

	private T[] values;
	private Comparator<T> comparator;
	private final SortMetrics curMetrics = new SortMetrics();

	@Override
	public final SortMetrics sort(T[] data, Comparator<T> comparator) {
		Objects.requireNonNull(data, "data is null");
		Objects.requireNonNull(comparator, "comparator is null");
		values = data;
		this.comparator = comparator;
		curMetrics.init();
		doSort();
		return getMetrics();
	}

	public final SortMetrics getMetrics() {
		return curMetrics.clone();
	}

	protected final int getDataLength() {
		return values.length;
	}

	protected final T probe(int i) {
		if (curMetrics.probeCnt == Long.MAX_VALUE) {
			throw new IllegalStateException(
					"curMetrics.probeCnt is Long.MAX_VALUE");
		}
		curMetrics.probeCnt++;
		return values[i];
	}

	protected final int compare(int i, int j) {
		if (curMetrics.compareCnt == Long.MAX_VALUE) {
			throw new IllegalStateException(
					"curMetrics.compareCnt is Long.MAX_VALUE");
		}
		curMetrics.compareCnt++;
		T t1 = values[i];
		T t2 = values[j];
		return comparator.compare(t1, t2);
	}

	protected final void swap(int i, int j) {
		if (curMetrics.swapCnt == Long.MAX_VALUE) {
			throw new IllegalStateException(
					"curMetrics.swapCnt is Long.MAX_VALUE");
		}
		curMetrics.swapCnt++;
		T tmp = values[i];
		values[i] = values[j];
		values[j] = tmp;
	}

	protected abstract void doSort();

}
