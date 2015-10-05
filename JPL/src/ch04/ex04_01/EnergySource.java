package ch04.ex04_01;

/**
 * 動力源を表すインターフェース
 * @author Ken
 *
 */
public interface EnergySource {

	/**
	 * 動力源が空かどうかを調べるメソッド
	 * @return 動力源が空の場合、trueを返す。そうでない場合、falseを返す。
	 */
	public boolean empty();
	
	/**
	 * 動力源の残量を確認する
	 * @return 動力源の残量
	 */
	public int observeRemainingEnergy();

	/**
	 * 動力源からエネルギーを取得するメソッド
	 * @param energy 取得したいエネルギー量
	 * @return 実際に取得できたエネルギー量
	 */
	public int retrieve(int energy);
	
	/**
	 * 動力源にエネルギーを補充するメソッド
	 * @param energy 補充するエネルギー量
	 */
	public void fill(int energy);
}
