package ch04.ex04_03;

/* LinkedListはインターフェースであるべきではない。
 * LinkedListを継承したクラスは、どのクラスも包含するオブジェクトと次のリストをメンバ保持する必要がある。
 * 共通部分は親クラスにまとめるべきだと考えられるが、インタフェースはメンバを持つことができない。
 * よってLinkedListはクラスで表現すべきだと考えられる。
 */
/**
 * リスト構造の一つの要素を示すインターフェース
 */
public interface LinkedList extends Cloneable {

	/**
	 * 包含しているオブジェクトを取得する
	 * @return 包含されているオブジェクト
	 */
	public Object getElement();

	/**
	 * リストの要素にオブジェクトを包含する
	 * @param element 包含したいオブジェクト
	 */
	public void setElement(Object element);

	/**
	 * リストの次の要素を取得する
	 * @return リストの次の要素 
	 */
	public LinkedList getNext();

	/**
	 * リストの次の要素を設定する
	 * @param element リストの次の要素
	 */	
	public void setNext(LinkedList next);

	/**
	 * リストを複製する。包含している要素は複製しない。
	 */	
	public LinkedList clone();
}
