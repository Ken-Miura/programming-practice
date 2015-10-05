package ch04.ex04_02;

import java.util.Comparator;

/**
 * オブジェクトをソートするためのインターフェース
 * 
 * @author Ken
 *
 * @param <T> ソートしたい型
 */
public interface SortHarness <T> {

	/**
	 * オブジェクトの配列をソートする
	 * @param data ソートしたいオブジェクトの配列
	 * @param comparator オブジェクトの比較基準
	 * @return ソートし終えるまでにオブジェクトを探査、比較、交換した回数を保持するオブジェクト
	 * @throws NullPointerException 引数にnullがあった場合スローする
	 */
	public SortMetrics sort(T[] data, Comparator<T> comparator);
}
