package ch04.ex04_04;

/**
 * 順序付けされたオブジェクトの集まりのインターフェース
 * @author Ken
 *
 * @param <E> 要素のオブジェクトの型
 */
public interface List <E> extends Collection<E> {

	/**
	 * 指定した位置にオブジェクトを追加する
	 * @param element 追加したいオブジェクト 
	 * @param index 追加したい位置を示すインデックス
	 * @return オブジェクトの追加に成功した場合true、失敗した場合falseを返す
	 */
	public boolean add(E element, int index);
	
	/**
	 * 指定した位置のオブジェクトを除去する
	 * @param element 除去したいオブジェクト
	 * @param index 除去したい位置を示すインデックス
	 * @return オブジェクトの除去に成功した場合true、失敗した場合falseを返す
	 */
	public boolean remove(E element, int index);
}
