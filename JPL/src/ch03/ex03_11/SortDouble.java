package ch03.ex03_11;

/* 
 * curMetrics.probeCnt, curMetrics.compareCnt, curMetrics.swapCntに関して
 * 境界値チェックを行っていない点がセキュリティホールとなる。
 * doSortの実装でLong.MAX_VALUE回以上、probe, compareまたはswapが呼ぶことで
 * 整数オーバフローを起こし、不正なメトリクス値を設定することが可能である。
 * 対策としてLong.MAX_VALUE回以上、probe, compareまたはswapが呼ばれそうになった際に
 * 例外を送出して不正なメトリクス値の設定を防ぐ
 */
abstract class SortDouble {

	private double[] values;
	private final SortMetrics curMetrics = new SortMetrics();

	public final SortMetrics sort(double[] data) {
		values = data;
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

	protected final double probe(int i) {
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
		double d1 = values[i];
		double d2 = values[j];
		if (d1 == d2)
			return 0;
		else
			return (d1 < d2 ? -1 : 1);
	}

	protected final void swap(int i, int j) {
		if (curMetrics.swapCnt == Long.MAX_VALUE) {
			throw new IllegalStateException(
					"curMetrics.swapCnt is Long.MAX_VALUE");
		}
		curMetrics.swapCnt++;
		double tmp = values[i];
		values[i] = values[j];
		values[j] = tmp;
	}

	protected abstract void doSort();
}
