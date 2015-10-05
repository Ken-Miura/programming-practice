package ch03.ex03_06;

/**
 * 動力源を表す抽象クラス
 * @author Ken
 *
 */
public abstract class EnergySource {

	/**
	 * 動力源が空かどうかを調べるメソッド
	 * @return 動力源が空の場合、trueを返す。そうでない場合、falseを返す。
	 */
	public abstract boolean empty();
	
	/**
	 * 動力源の残量を確認する
	 * @return 動力源の残量
	 */
	public abstract int observeRemainingEnergy();

	/**
	 * 動力源からエネルギーを取得するメソッド
	 * @param energy 取得したいエネルギー量
	 * @return 実際に取得できたエネルギー量
	 */
	public abstract int retrieve(int energy);
	
	/**
	 * 動力源にエネルギーを補充するメソッド
	 * @param energy 補充するエネルギー量
	 */
	public abstract void fill(int energy);
}
