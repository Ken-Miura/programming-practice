package ch03.ex03_12;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.InternalError;

final class SortMetrics implements Cloneable {

	public long probeCnt, compareCnt, swapCnt;

	public void init() {
		probeCnt = compareCnt = swapCnt = 0;
	}

	@Override
	public String toString() {
		return probeCnt + " probes " + compareCnt + " compares " + swapCnt
				+ " swaps";
	}
	
	public SortMetrics clone () {
		try {
			return (SortMetrics) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
	}
}
