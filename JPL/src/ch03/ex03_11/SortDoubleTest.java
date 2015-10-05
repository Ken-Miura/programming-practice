package ch03.ex03_11;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

public class SortDoubleTest {

	@Test(expected = IllegalStateException.class)
	public void 繰り返し回数最大のときにprobeを呼ぶと例外を送出する() {
		SortDouble sortDouble = new SortDouble() {
			@Override
			protected void doSort() {
				probe(0);
			}
		};
		Class<?> clazz = sortDouble.getClass().getSuperclass();
		Field field1 = null;
		Field field2 = null;
		SortMetrics sortMetrics = new SortMetrics();
		sortMetrics.probeCnt = Long.MAX_VALUE;
		double[] data = new double[] { 1.0, 2.0 };
		try {
			field1 = clazz.getDeclaredField("curMetrics");
			field1.setAccessible(true);
			field1.set(sortDouble, sortMetrics);
			field2 = clazz.getDeclaredField("values");
			field2.setAccessible(true);
			field2.set(sortDouble, data);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		sortDouble.doSort();
	}

	@Test(expected = IllegalStateException.class)
	public void 繰り返し回数最大のときにcompareを呼ぶと例外を送出する() {
		SortDouble sortDouble = new SortDouble() {
			@Override
			protected void doSort() {
				compare(0, 1);
			}
		};
		Class<?> clazz = sortDouble.getClass().getSuperclass();
		Field field1 = null;
		Field field2 = null;
		SortMetrics sortMetrics = new SortMetrics();
		sortMetrics.compareCnt = Long.MAX_VALUE;
		double[] data = new double[] { 1.0, 2.0 };
		try {
			field1 = clazz.getDeclaredField("curMetrics");
			field1.setAccessible(true);
			field1.set(sortDouble, sortMetrics);
			field2 = clazz.getDeclaredField("values");
			field2.setAccessible(true);
			field2.set(sortDouble, data);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		sortDouble.doSort();
	}

	@Test(expected = IllegalStateException.class)
	public void 繰り返し回数最大のときにswapを呼ぶと例外を送出する() {
		SortDouble sortDouble = new SortDouble() {
			@Override
			protected void doSort() {
				swap(0, 1);
			}
		};
		Class<?> clazz = sortDouble.getClass().getSuperclass();
		Field field1 = null;
		Field field2 = null;
		SortMetrics sortMetrics = new SortMetrics();
		sortMetrics.swapCnt = Long.MAX_VALUE;
		double[] data = new double[] { 1.0, 2.0 };
		try {
			field1 = clazz.getDeclaredField("curMetrics");
			field1.setAccessible(true);
			field1.set(sortDouble, sortMetrics);
			field2 = clazz.getDeclaredField("values");
			field2.setAccessible(true);
			field2.set(sortDouble, data);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		sortDouble.doSort();
	}
}
