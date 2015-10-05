package ch04.ex04_04;

/**
 * 要素の重複を許さないオブジェクトの集まりを示すインターフェース
 * @author Ken
 * 
 * @param <E> 要素のオブジェクトの型
 */
public interface Set <E> extends Collection <E> {

	/**
	 * オブジェクトの集まりに特定のオブジェクトを加える。
	 * 同値となるオブジェクトがある場合、追加に失敗する。
	 * 
	 * @param element
	 *            集まりに加えたいオブジェクト
	 * @return オブジェクトの追加に成功した場合true、失敗した場合falseを返す
	 */
	@Override
	public boolean add(E element);
}
