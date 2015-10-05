package ch03.ex03_12;

import java.util.Comparator;

public class TestSort {

	static String[] testData = { "aa", "a", "aaa", "a" };

	/* テスト用メソッド */
	public static void main(String[] args) {
		SortHarness<String> sort = new SimpleSortHarness<>();
		SortMetrics sortMetrics = sort.sort(testData, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.length() - o2.length();
			}
		});
		System.out.println("Metrics: " + sortMetrics);
		for (int i = 0; i < testData.length; i++) {
			System.out.println("\t" + testData[i]);
		}
	}

}
