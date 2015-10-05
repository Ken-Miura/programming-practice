package ch04.ex04_04;

/**
 * キューを示すオブジェクトの集まりを示すインターフェース
 * 
 * @author Ken
 *
 * @param <E>
 *            要素のオブジェクトの型
 */
public interface Queue<E> extends Collection<E> {

	/**
	 * キューにオブジェクトを追加する
	 * @param element 追加したいオブジェクト
	 */
	public void push(E element);

	/**
	 * キューからオブジェクトを取得し、除去する
	 * @return 取得または除去したいオブジェクト
	 */
	public E pop();
}
