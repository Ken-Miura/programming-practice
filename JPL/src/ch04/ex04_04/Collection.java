package ch04.ex04_04;

/**
 * オブジェクトの集まりを示すインターフェース
 * 
 * @author Ken
 *
 * @param <E>
 *            オブジェクトの型
 */
public interface Collection<E> extends Iterable<E> {

	/**
	 * オブジェクトの集まりに特定のオブジェクトを加える
	 * 
	 * @param element
	 *            集まりに加えたいオブジェクト
	 * @return オブジェクトの追加に成功した場合true、失敗した場合falseを返す
	 */
	public boolean add(E element);

	/**
	 * オブジェクトの集まりから特定のオブジェクトを除去する
	 * 
	 * @param element
	 *            集まりから除去したいオブジェクト
	 * @return オブジェクトの除去に成功した場合true、失敗した場合falseを返す
	 */
	public boolean remove(E element);

	/**
	 * オブジェクトの集まりからすべてのオブジェクトを除去する
	 * 
	 * @return すべてのオブジェクトの除去に成功した場合true、失敗した場合falseを返す
	 */
	public boolean removeAll();

	/**
	 * オブジェクトの集まりに特定のオブジェクトが含まれているかを調べる
	 * @param element オブジェクトの集まりに含まれているかを確認したいオブジェクト
	 * @return 引数のオブジェクトが含まれている場合true、含まれていない場合falseを返す
	 */
	public boolean contains (E element);
	
	/**
	 * オブジェクトの集まりが何もないかどうかを調べる
	 * @return オブジェクトの集まりが何もない場合true、失敗した場合falseを返す
	 */
	public boolean isEmpty();

	/**
	 * オブジェクトの集まりの中にあるオブジェクト数を返す
	 * @return オブジェクト数
	 */
	public int size();
	
	@Override
	public boolean equals (Object o);
	
	@Override
	public int hashCode ();
}
